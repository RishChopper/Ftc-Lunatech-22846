package org.firstinspires.ftc.teamcode.data;

import android.media.audiofx.DynamicsProcessing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightBlinker;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TeamHardware {
    private DcMotorEx motorLeftFront;
    private DcMotorEx motorRightFront;
    private DcMotorEx motorLeftBack;
    private DcMotorEx motorRightBack;
    private DcMotorEx LinearSlide1;
    private DcMotorEx LinearSlide2;
    private DcMotorEx motor;
    private Servo Servo0;
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
    final double POWER_LINEAR_SLIDE_1 = 1.0;
    final double POWER_LINEAR_SLIDE_2 = 1.0;
    private double r, robotAngle, v1, v2, v3, v4;

    public static final double COUNTS_PER_MOTOR_REV = 537.6898396; //Gobilda 5202 Motor Encoder 19.2:1	((((1+(46/17))) * (1+(46/11))) * 28)
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.937 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.14158);
    static final double     MAX_DRIVE_SPEED         = 0.4;
    static final double     COUNTS_PER_DEGREE       = 22* (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.14158* 90);


    public TeamHardware (HardwareMap hwMap, Telemetry tmry) {
        hardwareMap = hwMap;
        telemetry = tmry;
        runtime = new ElapsedTime();
        Servo0 = hardwareMap.get(Servo.class, "Servo0");
        motor = hardwareMap.get(DcMotorEx.class, "motor");
    }

    /*public TeamHardware (HardwareMap hwMap, Telemetry tmry){
        hardwareMap = hwMap;
        telemetry = tmry;
        runtime = new ElapsedTime();
        motorLeftFront = hardwareMap.get(DcMotorEx.class, "motorLeftFront");
        motorRightFront = hardwareMap.get(DcMotorEx.class, "motorRightFront");
        motorLeftBack = hardwareMap.get(DcMotorEx.class, "motorLeftBack");
        motorRightBack = hardwareMap.get(DcMotorEx.class, "motorRightBack");
        LinearSlide1 = hardwareMap.get(DcMotorEx.class, "LinearSlide1");
        LinearSlide2 = hardwareMap.get(DcMotorEx.class, "LinearSlide2");
        Servo0 = hardwareMap.get(Servo.class, "Servo0");
    }*/

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

        LinearSlide1.setDirection(DcMotorEx.Direction.FORWARD);
        LinearSlide1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        LinearSlide1.setPower(0.0);
        LinearSlide1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        LinearSlide2.setDirection(DcMotorEx.Direction.FORWARD);
        LinearSlide2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        LinearSlide2.setPower(0.0);
        LinearSlide2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        Servo0.setPosition(0);
    }

    public void init_Servo0(){
        Servo0.setPosition(0);
    }
    public void init_motor(){
        motor.setDirection(DcMotorEx.Direction.REVERSE);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motor.setPower(0.0);
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void init_auto(LinearOpMode opmode) {
        myOpMode = opmode;
        motorLeftFront.setDirection(DcMotorEx.Direction.REVERSE);
        motorLeftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorLeftFront.setPower(0.0);
        motorLeftFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        motorRightFront.setDirection(DcMotorEx.Direction.FORWARD);
        motorLeftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorRightFront.setPower(0.0);
        motorRightFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        motorLeftBack.setDirection(DcMotorEx.Direction.REVERSE);
        motorLeftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorLeftBack.setPower(0.0);
        motorLeftBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftBack.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        motorRightBack.setDirection(DcMotorEx.Direction.FORWARD);
        motorLeftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorRightBack.setPower(0.0);
        motorRightBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorRightBack.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        LinearSlide1.setDirection(DcMotorEx.Direction.FORWARD);
        LinearSlide1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        LinearSlide1.setPower(0.0);
        LinearSlide1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        LinearSlide2.setDirection(DcMotorEx.Direction.FORWARD);
        LinearSlide2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        LinearSlide2.setPower(0.0);
        LinearSlide2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        Servo0.setPosition(0);

/*        telemetry.addData("Starting at",  "%7d: %7d: %7d: %7d",
                motorLeftFront.getCurrentPosition(),
                motorRightFront.getCurrentPosition(),
                motorLeftBack.getCurrentPosition(),
                motorRightBack.getCurrentPosition());
        telemetry.update();*/
    }

    public void LinearSlide(int id, double power){
        switch(id){
            case 0:
                LinearSlide1.setPower(power * POWER_LINEAR_SLIDE_1 * POWER_CHASSIS);
                break;
            case 1:
                LinearSlide2.setPower(power * POWER_LINEAR_SLIDE_2 * POWER_CHASSIS);
                break;
            default:
        }
    }

    public void setMotors(double x, double y, double rot)  //sets the motor speeds given an x, y and rotation value
    {
        try {
            r = Math.hypot(x, y);
            robotAngle = Math.atan2(y, x) - Math.PI / 4;

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
            RobotLog.ee("SMTECH", e, "setMotors");
        }
    }

    public void setMotor(double power){
        motor.setPower(power);
        telemetry.addData("RPM: ", (motor.getVelocity() / TeamHardware.COUNTS_PER_MOTOR_REV) * 60);
        telemetry.update();
    }

    public MotorData getMotorData(){
        MotorData motorData = new MotorData(motorLeftFront, motorRightFront, motorLeftBack, motorRightBack);
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
                stopChassisMotors();
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
                stopChassisMotors();
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

    /*public void setServo0Power(double power){
        Servo0.setPower(power);
    }*/

    public void setServoPosition(String servo, double degrees){
        switch(servo){
            case "Servo0":
                Servo0.setPosition(degrees);
                break;

            default:
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
    public void stopChassisMotors(){
        motorLeftFront.setPower(0);
        motorRightFront.setPower(0);
        motorLeftBack.setPower(0);
        motorRightBack.setPower(0);

        LinearSlide1.setPower(0);
        LinearSlide2.setPower(0);
        Servo0.setPosition(0);
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

            LinearSlide1.setPower(0);
            LinearSlide2.setPower(0);
            Servo0.setPosition(0);
        }
        catch(Exception e){
            myOpMode.telemetry.addData("Exception stopChassis", e.toString());
            myOpMode.telemetry.update();
            RobotLog.ee("SMTECH", e, "exception in stopChassis()");
        }
    }

    void stopGamepad1Motors(){
        stopChassisMotors();
    }

}
