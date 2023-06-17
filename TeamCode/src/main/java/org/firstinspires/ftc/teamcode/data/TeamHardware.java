package org.firstinspires.ftc.teamcode.data;

import android.media.audiofx.DynamicsProcessing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightBlinker;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Objects;

public class TeamHardware {
    private DcMotorEx motorLeftFront;
    private DcMotorEx motorRightFront;
    private DcMotorEx motorLeftBack;
    private DcMotorEx motorRightBack;
    private DcMotorEx intakeTiltMech;
    private DcMotorEx dropLinearSlide;
    private Servo intakeClaw;
    private Servo dropClaw;
    private Servo dropClawRotate;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    private ElapsedTime runtime;

    private LinearOpMode myOpMode = null;

    final double POWER_CHASSIS = 1.0;
    final double POWER_DRIVE_MOTORS = 1.0;
    final double POWER_MOTOR_LEFT_FRONT = 1.0;
    final double POWER_MOTOR_LEFT_BACK = 1.0;
    final double POWER_MOTOR_RIGHT_FRONT = 1.0;
    final double POWER_MOTOR_RIGHT_BACK = 1.0;
    private double r, robotAngle, v1, v2, v3, v4;

    private static boolean claw_rot_pos = false;

    public static final double COUNTS_PER_MOTOR_REV = 537.7; //Gobilda 5203 Motor Encoder 19.2:1	((((1+(46/17))) * (1+(46/11))) * 28)
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.937 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.14158);
    static final double     MAX_DRIVE_SPEED         = 0.4;
    static final double     COUNTS_PER_DEGREE       = 22* (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.14158* 90);

    public TeamHardware (HardwareMap hwMap, Telemetry tmry, LinearOpMode opMode){
        hardwareMap = hwMap;
        telemetry = tmry;
        myOpMode = opMode;
        runtime = new ElapsedTime();
        motorLeftFront = hardwareMap.get(DcMotorEx.class, "motorLeftFront");
        motorRightFront = hardwareMap.get(DcMotorEx.class, "motorRightFront");
        motorLeftBack = hardwareMap.get(DcMotorEx.class, "motorLeftBack");
        motorRightBack = hardwareMap.get(DcMotorEx.class, "motorRightBack");
        intakeTiltMech = hardwareMap.get(DcMotorEx.class, "intakeSlide");
        dropLinearSlide = hardwareMap.get(DcMotorEx.class, "dropSlide");
        intakeClaw = hardwareMap.get(Servo.class, "intakeClaw");
        dropClaw = hardwareMap.get(Servo.class, "dropClaw");
        dropClawRotate = hardwareMap.get(Servo.class, "dropClawRotate");
        //intakeClaw.getController().pwmEnable();
        //dropClaw.getController().pwmEnable();
        //dropClawRotate.getController().pwmEnable();
    }

    public void callibrate(){
        intakeTiltMech.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeTiltMech.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeTiltMech.setPower(1.0);

        dropLinearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dropLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dropLinearSlide.setPower(1.0);
    }

    public void setIndiPower(int motor, double power){
        switch (motor){
            case 0:
                motorLeftFront.setPower(power);
                break;

            case 1:
                motorRightFront.setPower(power);
                break;

            case 2:
                motorLeftBack.setPower(power);
                break;

            case 3:
                motorRightBack.setPower(power);
                break;
        }
    }

    /* Initialize standard Hardware interfaces */
    public void init_teleop() {
        motorLeftFront.setDirection(DcMotorEx.Direction.REVERSE);
        motorLeftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorLeftFront.setPower(0.0);
        motorLeftFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        motorRightFront.setDirection(DcMotorEx.Direction.FORWARD);
        motorRightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorRightFront.setPower(0.0);
        motorRightFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        motorLeftBack.setDirection(DcMotorEx.Direction.REVERSE);
        motorLeftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorLeftBack.setPower(0.0);
        motorLeftBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        motorRightBack.setDirection(DcMotorEx.Direction.FORWARD);
        motorRightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorRightBack.setPower(0.0);
        motorRightBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        intakeTiltMech.setDirection(DcMotorEx.Direction.FORWARD);
        intakeTiltMech.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intakeTiltMech.setPower(0.0);
        intakeTiltMech.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        dropLinearSlide.setDirection(DcMotorEx.Direction.FORWARD);
        dropLinearSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        dropLinearSlide.setPower(0.0);
        dropLinearSlide.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void init_auto(LinearOpMode opmode) {
        myOpMode = opmode;
        motorLeftFront.setDirection(DcMotorEx.Direction.REVERSE);
        motorLeftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorLeftFront.setPower(0.0);
        motorLeftFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        motorRightFront.setDirection(DcMotorEx.Direction.FORWARD);
        motorLeftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorRightFront.setPower(0.0);
        motorRightFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        motorLeftBack.setDirection(DcMotorEx.Direction.REVERSE);
        motorLeftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorLeftBack.setPower(0.0);
        motorLeftBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        motorRightBack.setDirection(DcMotorEx.Direction.FORWARD);
        motorLeftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorRightBack.setPower(0.0);
        motorRightBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorRightBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        intakeTiltMech.setDirection(DcMotorEx.Direction.FORWARD);
        intakeTiltMech.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intakeTiltMech.setPower(0.0);
        intakeTiltMech.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        dropLinearSlide.setDirection(DcMotorEx.Direction.FORWARD);
        dropLinearSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        dropLinearSlide.setPower(0.0);
        dropLinearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        callibrate();
    }

    public void manualLinearSlides(double madman_power, double scorpion_power){
        if(madman_power <=0.2 && madman_power >= -0.2){
            //madman_power = 0.01;
        }
        dropLinearSlide.setPower(madman_power);
        intakeTiltMech.setPower(scorpion_power);
    }

    public void autoDropLinearSlides(int level){
        switch (level){
            case 0:
                dropLinearSlide.setTargetPosition(0);
                dropLinearSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                dropLinearSlide.setPower(1);
                break;

            case 1:
                dropLinearSlide.setTargetPosition(1540);
                dropLinearSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                dropLinearSlide.setPower(1);
                break;

            case 2:
                dropLinearSlide.setTargetPosition(2850);
                dropLinearSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                dropLinearSlide.setPower(1);
                break;

            case 3:
                dropLinearSlide.setTargetPosition(4050);
                dropLinearSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                dropLinearSlide.setPower(1);
                break;
        }
    }

    public void autointakeTiltMech(boolean extend){
        if (extend){
            intakeTiltMech.setTargetPosition(116);
            intakeTiltMech.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            intakeTiltMech.setPower(1);
        }else{
            intakeTiltMech.setTargetPosition(0);
            intakeTiltMech.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            intakeTiltMech.setPower(1);
        }
    }

    public void moveClaws(String bot_mode, boolean drop_claw_pos, boolean scorp_claw_pos, boolean delta_rot){  //true = open; false = close;
        claw_rot_pos = delta_rot;

        try {
            if(claw_rot_pos){
                dropClawRotate.setPosition(0.7);
            }else {
                dropClawRotate.setPosition(0.05);
            }

            if (drop_claw_pos){
                dropClaw.getController().pwmEnable();
                if (bot_mode.equals("Scorpion")){
                    dropClaw.setPosition(0.4);
                }else {
                    dropClaw.setPosition(0.43);
                }
            }else{
                dropClaw.getController().pwmDisable();
            }

            if (scorp_claw_pos){
                dropClaw.getController().pwmEnable();
                dropClaw.setPosition(0.2);
            }else{
                dropClaw.getController().pwmDisable();
            }
        } catch (Exception e) {
            telemetry.addData("moveClaw", "%s", e.toString());
            telemetry.update();
            RobotLog.ee("Lunatech", e, "moveClaw");
        }
    }

    public void setMotors(double x, double y, double rot)  //sets the motor speeds given an x, y and rotation value
    {
        try {
            r = Math.hypot(x, y); //returns the velocity
            robotAngle = Math.atan2(y, x) - Math.PI / 4; //returns the angle of the velocity against the x axis

            v1 = r * Math.cos(robotAngle) + rot;
            v2 = r * Math.sin(robotAngle) - rot;
            v3 = r * Math.sin(robotAngle) + rot;
            v4 = r * Math.cos(robotAngle) - rot;

            motorLeftFront.setPower(v1 * POWER_CHASSIS * POWER_DRIVE_MOTORS * POWER_MOTOR_LEFT_FRONT);
            motorRightFront.setPower(v2 * POWER_CHASSIS * POWER_DRIVE_MOTORS * POWER_MOTOR_RIGHT_FRONT);
            motorLeftBack.setPower(v3 * POWER_CHASSIS * POWER_DRIVE_MOTORS * POWER_MOTOR_LEFT_BACK);
            motorRightBack.setPower(v4 * POWER_CHASSIS * POWER_DRIVE_MOTORS * POWER_MOTOR_RIGHT_BACK);
        }
        catch(Exception e){
            telemetry.addData("setMotors", "%s", e.toString());
            telemetry.update();
            RobotLog.ee("Lunatech", e, "setMotors");
        }
    }

    public MotorData getMotorData(){
        MotorData motorData = new MotorData(motorLeftFront, motorRightFront, motorLeftBack, motorRightBack, intakeTiltMech, dropLinearSlide, intakeClaw, dropClaw, dropClawRotate);
        return motorData;
    }

    public void encoderDrive(double speed, DataHolder.MOVEDIR dir, double distance,
                             double timeoutS) {

        // Ensure that the opmode is still active
        try {
            if (myOpMode.opModeIsActive()) {
                setChassisTargetPosition(dir, distance);
                beginChassisMotion(speed);
                moveChassisToTarget(dir, timeoutS);
            }
        }
        catch (Exception e) {
            myOpMode.telemetry.addData("Exception encoderDrive", e.toString());
            myOpMode.telemetry.update();
            RobotLog.ee("SMTECH", e, "exception in encoderDrive()");
        }
        myOpMode.sleep(40);   // optional pause after each move
    }

    public void encoderTurn(double speed, DataHolder.MOVEDIR dir, double degrees,
                            double timeoutS) {

        // Ensure that the opmode is still active
        try {
            if (myOpMode.opModeIsActive()) {


                setChassisTurnTargetPosition(dir, degrees);

                beginChassisMotion(speed);
                moveChassisToTarget(dir, timeoutS);
            }
        }
        catch (Exception e) {
            myOpMode.telemetry.addData("Exception encoderTurn", e.toString());
            myOpMode.telemetry.update();
            RobotLog.ee("SMTECH", e, "exception in encoderTurn()");
        }
        myOpMode.sleep(40);   // optional pause after each move
    }

    public void setChassisTurnTargetPosition( DataHolder.MOVEDIR dir, double degrees){
        int targetPos;
        targetPos = (int)(degrees * COUNTS_PER_DEGREE);
        try {
            switch (dir) {
                case ROTATE_LEFT:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition() + targetPos);
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition() - targetPos);
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition() + targetPos);
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition() - targetPos);
                    break;
                case ROTATE_RIGHT:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition() - targetPos);
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition() + targetPos);
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition() - targetPos);
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition() + targetPos);
                    break;
                default:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition());
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition());
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition());
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition());
            }
        }
        catch (Exception e) {
            myOpMode.telemetry.addData("Exception setChassisTurnTargetPosition", e.toString());
            myOpMode.telemetry.update();
            RobotLog.ee("SMTECH", e, "exception in setChassisTurnTargetPosition()");
        }

    }

    void beginChassisMotion(double speed){
        // Turn On RUN_TO_POSITION
        try {
            motorLeftFront.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            motorRightFront.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            motorLeftBack.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            motorRightBack.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            motorLeftFront.setPower(Math.abs(speed) * POWER_MOTOR_LEFT_FRONT * POWER_CHASSIS * POWER_DRIVE_MOTORS);
            motorRightFront.setPower(Math.abs(speed) * POWER_MOTOR_RIGHT_FRONT * POWER_CHASSIS * POWER_DRIVE_MOTORS);
            motorLeftBack.setPower(Math.abs(speed) * POWER_MOTOR_LEFT_BACK * POWER_CHASSIS * POWER_DRIVE_MOTORS);
            motorRightBack.setPower(Math.abs(speed) * POWER_MOTOR_RIGHT_BACK * POWER_CHASSIS * POWER_DRIVE_MOTORS);
        }
        catch (Exception e) {
            myOpMode.telemetry.addData("Exception beginChassisMotion", e.toString());
            myOpMode.telemetry.update();
            RobotLog.ee("SMTECH", e, "exception in beginChassisMotion()");
        }
    }

    void  moveChassisToTarget(DataHolder.MOVEDIR dir, double timeoutS){
        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.

        try {
            while (myOpMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motorLeftFront.isBusy() || motorRightFront.isBusy() ||
                            motorLeftBack.isBusy() || motorRightBack.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", motorLeftFront.getTargetPosition(),
                        motorRightFront.getTargetPosition());
                telemetry.addData("Path2", "%s: Now at %7d :%7d: %7d: %7d",
                    dir.name(),
                    motorLeftFront.getCurrentPosition(),
                    motorRightFront.getCurrentPosition(),
                    motorLeftBack.getCurrentPosition(),
                    motorRightBack.getCurrentPosition());
                myOpMode.telemetry.update();
                myOpMode.sleep(40);
            }
        }
        catch (Exception e) {
            myOpMode.telemetry.addData("Exception moveChassisToTarget", e.toString());
            myOpMode.telemetry.update();
            RobotLog.ee("SMTECH", e, "exception in moveChassisToTarget()");
        }
    }

    public void setChassisTargetPosition( DataHolder.MOVEDIR dir, double distance){
        int targetPos;
        targetPos = (int)(distance * COUNTS_PER_INCH);

        try {
            switch (dir) {
                case BACK:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition() + targetPos);
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition() + targetPos);
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition() + targetPos);
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition() + targetPos);
                    break;
                case FRONT:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition() - targetPos);
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition() - targetPos);
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition() - targetPos);
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition() - targetPos);
                    break;
                case RIGHT:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition() - targetPos);
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition() + targetPos);
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition() + targetPos);
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition() - targetPos);
                    break;
                case LEFT:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition() + targetPos);
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition() - targetPos);
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition() - targetPos);
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition() + targetPos);
                    break;
                case BACK_LEFT:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition() + targetPos);
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition());
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition());
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition() + targetPos);
                    break;
                case FRONT_LEFT:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition());
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition() - targetPos);
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition() - targetPos);
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition());
                    break;
                case BACK_RIGHT:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition());
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition() + targetPos);
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition() + targetPos);
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition());
                    break;
                case FRONT_RIGHT:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition() - targetPos);
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition());
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition());
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition() - targetPos);
                    break;
                default:
                    motorLeftFront.setTargetPosition(motorLeftFront.getCurrentPosition());
                    motorRightFront.setTargetPosition(motorRightFront.getCurrentPosition());
                    motorLeftBack.setTargetPosition(motorLeftBack.getCurrentPosition());
                    motorRightBack.setTargetPosition(motorRightBack.getCurrentPosition());
            }
        }
        catch (Exception e) {
            myOpMode.telemetry.addData("Exception setChassisTargetPosition", e.toString());
            myOpMode.telemetry.update();
            RobotLog.ee("SMTECH", e, "exception in setChassisTargetPosition()");
        }
    }

    void stopChassis() {
        // Stop all motion;
        try {
            motorLeftFront.setPower(0);
            motorRightFront.setPower(0);
            motorLeftBack.setPower(0);
            motorRightBack.setPower(0);

            // Turn off RUN_TO_POSITION
            motorLeftFront.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            motorRightFront.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            motorLeftBack.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            motorRightBack.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            intakeTiltMech.setPower(0);
            dropLinearSlide.setPower(0);

            intakeClaw.getController().pwmDisable();
            dropClaw.getController().pwmDisable();
            dropClawRotate.getController().pwmDisable();
        }
        catch(Exception e){
            myOpMode.telemetry.addData("Exception stopChassis", e.toString());
            myOpMode.telemetry.update();
            RobotLog.ee("Lunatech", e, "exception in stopChassis()");
        }
    }
}