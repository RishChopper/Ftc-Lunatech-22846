package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.data.MotorData;
import org.firstinspires.ftc.teamcode.data.TeamHardware;

@TeleOp(name = "Lunatech-TeleopTest", group = "Testers")
public class test_teleop extends LinearOpMode {
    DcMotorEx motor;

    @Override
    public void runOpMode() {
        double leftX1;
        double leftY1;
        double leftY2;
        double rightX1;

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

        motor = hardwareMap.get(DcMotorEx.class, "motor");
        motor.setDirection(DcMotorEx.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motor.setPower(0.0);
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {

                //if (!gamepad1.atRest()) { // Only checks wheels & trigger
                try {
                    //=========================================================================================
                    //                                      ASSIGNMENT
                    //=========================================================================================
                    leftX1 = Range.clip(gamepad1.left_stick_x, -1, 1);
                    leftY1 = -Range.clip(gamepad1.left_stick_y, -1, 1);
                    leftY2 = -Range.clip(gamepad2.left_stick_y, -1, 1);
                    rightX1 = -Range.clip(gamepad1.right_stick_x, -1, 1);
                    rightTrigger = Range.clip(gamepad2.right_trigger, -1, 1);
                    leftTrigger = -Range.clip(gamepad2.left_trigger, -1, 1);
                    triggers_value = rightTrigger + leftTrigger;
                    //=========================================================================================
                    //                                      BOT MODE
                    //=========================================================================================
                    if(bot_mode){
                        telemetry.addData("BOT_MODE", "Manual");
                    }else {
                        telemetry.addData("BOT_MODE", "Assisted");
                    }

                    //=========================================================================================
                    //                                  SET POWER TO MOTORS
                    //=========================================================================================

                    if (bot_mode){
                        if (gamepad2.dpad_left){
                            bot_mode = false;
                        } else if (gamepad2.dpad_right) {
                            bot_mode = true;
                        }

                        motor.setPower(triggers_value);
                    }else {
                        if (gamepad2.dpad_left){
                            bot_mode = false;
                        } else if (gamepad2.dpad_right) {
                            bot_mode = true;
                        }

                        if (System.currentTimeMillis() - last_t >= 500) {
                            if (gamepad2.left_bumper) {
                                dropSlideState--;
                                last_t = System.currentTimeMillis();
                            } else if (gamepad2.right_bumper) {
                                dropSlideState++;
                                last_t = System.currentTimeMillis();
                            }
                        }

                        if (dropSlideState <= 0) {
                            dropSlideState = 0;
                        } else if (dropSlideState >= 3) {
                            dropSlideState = 3;
                        }

                        automotors(dropSlideState);

                        telemetry.addData("Drop Slide State", dropSlideState);
                    }
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

    void automotors(int level){
        switch (level){
            case 0:
                motor.setTargetPosition(0);
                motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                motor.setPower(1);
                break;

            case 1:
                motor.setTargetPosition(1540);
                motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                motor.setPower(1);
                break;

            case 2:
                motor.setTargetPosition(2850);
                motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                motor.setPower(1);
                break;

            case 3:
                motor.setTargetPosition(4050);
                motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                motor.setPower(1);
                break;
        }
    }
}