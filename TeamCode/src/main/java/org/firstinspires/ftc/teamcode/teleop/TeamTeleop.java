package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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

        boolean bot_mode = true;

        long last_t = System.currentTimeMillis()/1000;

        int dropSlideState = 0;

        TeamHardware robot = new TeamHardware(hardwareMap, telemetry, this);
        MotorData motorData = robot.getMotorData();

        robot.init_teleop();

        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                try {
                    leftX1 = Range.clip(gamepad1.left_stick_x, -1, 1);
                    leftY1 = -Range.clip(gamepad1.left_stick_y, -1, 1);
                    leftY2 = -Range.clip(gamepad2.left_stick_y, -1, 1);
                    rightX1 = Range.clip(gamepad1.right_stick_x, -1, 1);
                    rightY1 = -Range.clip(gamepad1.right_stick_y, -1, 1);

                    if(leftX1 < DataHolder.JoyL1_Deadzone){
                        leftX1 = 0;
                    }
                    if(leftY1 < DataHolder.JoyL1_Deadzone){
                        leftY1 = 0;
                    }
                    if(rightX1 < DataHolder.JoyR1_Deadzone){
                        rightX1 = 0;
                    }
                    if(rightY1 < DataHolder.JoyR1_Deadzone){
                        rightY1 = 0;
                    }

                    rightTrigger = Range.clip(gamepad2.right_trigger, -1, 1);
                    leftTrigger = -Range.clip(gamepad2.left_trigger, -1, 1);
                    triggers_value = rightTrigger + leftTrigger;

                    robot.setMotors(leftX1, leftY1, rightX1);

                    robot.manualLinearSlides(triggers_value, leftY2);

                    robot.moveClaws(gamepad2.a, gamepad2.b, gamepad2.x);

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

                    //Calculate RPM of Motors
                    //leftBackRPM = motorData.getBackLeft().getVelocity();
                    //That returned in ticks per second
                    //leftBackRPM = (leftBackRPM / TeamHardware.COUNTS_PER_MOTOR_REV) * 60;
                    //And now it's in RPM
                    intakeLinearSlideRPM = (motorData.getintakeLinearSlide().getVelocity() / 288) * 60;
                    dropLinearSlideRPM = (motorData.getdropLinearSlide().getVelocity() / 288) * 60;

                    //telemetry is the screen with debug info in Driver Station
                    //telemetry.addData("GAMEPAD1", "Front %f,  Right %f, Turn %f", leftY1, leftX1, rightX1);
                    //telemetry.addData("RPM", "LEFTFRONT %f, RIGHTRONT %f, LEFTBACK %f, RIGHTBACK %f", leftFrontRPM, rightFrontRPM, leftBackRPM, rightBackRPM);
                    telemetry.addData("RPM", "intakeLinearSlide %f, dropLinearSlide %f", intakeLinearSlideRPM, dropLinearSlideRPM);
                    telemetry.update();
                }catch(Exception e){
                    telemetry.addData("TELEOP 1:", "%s", e.toString());
                    telemetry.update();
                    RobotLog.ee("Lunatech", e, "TELEOP 1");
                }
                idle();
            }
        }
    }
}