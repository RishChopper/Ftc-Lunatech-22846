package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.data.DataHolder;
import org.firstinspires.ftc.teamcode.data.MotorData;
import org.firstinspires.ftc.teamcode.data.TeamHardware;

@TeleOp(name = "Lunatech-Teleop", group = "MainCode")
public class TeamTeleop extends LinearOpMode {
    @Override
    public void runOpMode() {
        double leftX1;
        double leftY1;
        double leftY2;
        double rightX2;
        double rightX1;
        double rightY1;

        double triggers_value;
        double rightTrigger;
        double leftTrigger;

        double leftFrontRPM;
        double rightFrontRPM;
        double leftBackRPM;
        double rightBackRPM;

        double intakeLinearSlideRPM;
        double dropLinearSlideRPM;

        String botmode = "Auto";

        long last_t = System.currentTimeMillis()/1000;

        int dropSlideState = 0;

        boolean flip_seq = false;

        Servo intakeClaw;
        Servo dropClaw;
        Servo dropClawRotate;

        DcMotorEx intakeSlide;
        DcMotorEx dropSlide;

        TeamHardware robot = new TeamHardware(hardwareMap, telemetry, this);
        MotorData motorData = robot.getMotorData();

        intakeClaw = hardwareMap.get(Servo.class, "intakeClaw");
        dropClaw = hardwareMap.get(Servo.class, "dropClaw");
        dropClawRotate = hardwareMap.get(Servo.class, "dropClawRotate");

        robot.init_teleop();

        intakeSlide = robot.getIntakeTiltMech();
        intakeSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        dropSlide = robot.getDropLinearSlide();

        Thread t1 = new Thread(() -> {
            robot.moveClaws("Scorpion", false, true, "Extend");

            boolean tilt_motion = robot.autointakeTiltMech(true, 3);
            while (!tilt_motion) {
                tilt_motion = robot.autointakeTiltMech(true, 3);
                telemetry.addData("Seq", "Waiting for motion to complete");
                telemetry.update();
            }
            telemetry.addData("Seq", "Motion finished");
            telemetry.update();

            //3
            while (!gamepad2.x) {
            }
            robot.moveClaws("Scorpion", true, false, "Contract");
            TeamTeleop.this.sleep(300);

            tilt_motion = robot.autointakeTiltMech(false, 3);
            while (!tilt_motion) {
                tilt_motion = robot.autointakeTiltMech(false, 3);
                telemetry.addData("Seq", "Waiting for motion to complete");
                telemetry.update();
            }
            telemetry.addData("Seq", "Motion finished");
            telemetry.update();

            robot.moveClaws("Scorpion", false, true, "Contract");
            TeamTeleop.this.sleep(200);

            //while (!gamepad2.dpad_left) {
            //}

            tilt_motion = robot.autointakeTiltMech(true, 0);
            TeamTeleop.this.sleep(200);
            while (!tilt_motion) {
                tilt_motion = robot.autointakeTiltMech(true, 0);
                robot.moveClaws("Scorpion", false, true, "Extend");
                telemetry.addData("Seq", "Waiting for motion to complete");
                telemetry.update();
            }
            telemetry.addData("Seq", "Motion finished");
            telemetry.update();
        });

        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                try {
                    leftX1 = Range.clip(gamepad1.left_stick_x, -1, 1);
                    leftY1 = -Range.clip(gamepad1.left_stick_y, -1, 1);
                    leftY2 = -Range.clip(gamepad2.left_stick_y, -1, 1);
                    rightX1 = Range.clip(gamepad1.right_stick_x, -1, 1);
                    rightX2 = Range.clip(gamepad2.right_stick_x, -1, 1);
                    rightY1 = -Range.clip(gamepad1.right_stick_y, -1, 1);

                    rightTrigger = Range.clip(gamepad2.right_trigger, -1, 1);
                    leftTrigger = -Range.clip(gamepad2.left_trigger, -1, 1);
                    triggers_value = rightTrigger + leftTrigger;

                    if (rightX2 >= 0.4){
                        botmode = "Manual";
                    }else if(rightX2 <= -0.4){
                        botmode = "Auto";
                    }
                    telemetry.addData("Botmode: ", botmode);
                    if(gamepad2.a) {
                        dropClaw.setPosition(0.4);
                    }else if (gamepad2.b){
                        dropClaw.setPosition(0.5);
                    }
                    if (gamepad2.x){
                        intakeClaw.setPosition(0.52);
                    }else if(gamepad2.y){
                        intakeClaw.setPosition(0.42);
                    }
                    if(gamepad2.dpad_down) {
                        dropClawRotate.setPosition(0.9);//Madman drop_claw measurement
                    }if (gamepad2.dpad_up){
                        dropClawRotate.setPosition(0.2);
                    }

                    robot.setMotors(leftX1, leftY1, rightX1);
                    /*robot.setIndiPower(0, leftX1);
                    robot.setIndiPower(1, leftY1);
                    robot.setIndiPower(2, rightX1);
                    robot.setIndiPower(3, rightY1);*/

                    if (System.currentTimeMillis() - last_t >= 500) {
                        if(gamepad2.left_bumper){
                            dropSlideState--;
                            last_t = System.currentTimeMillis();
                        }else if (gamepad2.right_bumper){
                            dropSlideState++;
                            last_t = System.currentTimeMillis();
                        }
                    }

                    if (dropSlideState <= 0){
                        dropSlideState = 0;
                    }else if(dropSlideState >= 3){
                        dropSlideState = 3;
                    }
                    telemetry.addData("Auto Drop Slide State", dropSlideState);

                    dropSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    robot.manualLinearSlides(triggers_value, leftY2/10);

                    if(botmode.equals("Auto")){
                        //robot.autoDropLinearSlides(dropSlideState);

                        if (!flip_seq && gamepad2.dpad_left){
                            t1.start();
                            flip_seq = true;
                        }

                        if (!t1.isAlive()){
                            flip_seq = false;
                        }
                    }

                    intakeLinearSlideRPM = (motorData.getintakeLinearSlide().getVelocity() / 288) * 60;
                    dropLinearSlideRPM = (motorData.getdropLinearSlide().getVelocity() / 537.6) * 60;
                    //Calculate RPM of Motors
                    leftBackRPM = motorData.getBackLeft().getVelocity();
                    //That returned in ticks per second
                    leftBackRPM = (leftBackRPM / TeamHardware.COUNTS_PER_MOTOR_REV) * 60;
                    //And now it's in RPM
                    rightBackRPM = (motorData.getBackRight().getVelocity() / TeamHardware.COUNTS_PER_MOTOR_REV) * 60;
                    leftFrontRPM = (motorData.getFrontLeft().getVelocity() / TeamHardware.COUNTS_PER_MOTOR_REV) * 60;
                    rightFrontRPM = (motorData.getFrontRight().getVelocity() / TeamHardware.COUNTS_PER_MOTOR_REV) * 60;

                    //telemetry is the screen with debug info in Driver Station
                    //telemetry.addData("GAMEPAD1", "Front %f,  Right %f, Turn %f", leftY1, leftX1, rightX1);
                    telemetry.addData("RPM", "LEFTFRONT %f, RIGHTRONT %f, LEFTBACK %f, RIGHTBACK %f", leftFrontRPM, rightFrontRPM, leftBackRPM, rightBackRPM);
                    telemetry.addData("RPM", "intakeLinearSlide %f, dropLinearSlide %f", intakeLinearSlideRPM, dropLinearSlideRPM);
                    telemetry.update();
                }catch(Exception e){
                    telemetry.addData("TELEOP 1:", "%s", e.toString());
                    telemetry.update();
                    RobotLog.ee("Lunatech", e, "TELEOP 1");
                }
                if (isStopRequested()){
                    t1.interrupt();
                    flip_seq = false;
                }
                idle();
            }
        }
    }
}