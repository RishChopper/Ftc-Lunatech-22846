package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

@TeleOp(name = "Lunatech-TeleOp")
public class TeamTeleop extends LinearOpMode {

  private TeamHardware robot;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    double leftX1;
    double leftY1;
    double rightX1;
    double triggers_value;
    double rightTrigger;
    double leftTrigger;

    robot = new TeamHardware(hardwareMap,telemetry);

    robot.init_teleop();

    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {

        if (!gamepad1.atRest()) { // Only checks wheels & trigger
          try {
            leftX1 = -Range.clip(gamepad1.left_stick_x, -1, 1);
            leftY1 = Range.clip(gamepad1.left_stick_y, -1, 1);
            rightX1 = -Range.clip(gamepad1.right_stick_x, -1, 1);
            rightTrigger = Range.clip(gamepad1.right_trigger, -1, 1);
            leftTrigger = -Range.clip(gamepad1.left_trigger, -1, 1);

            triggers_value = rightTrigger + leftTrigger;

            //Set motor power:
            robot.setMotors(leftX1, leftY1, rightX1);

            robot.LinearSlide(0, triggers_value);

            //telemetry is the screen with debug info in DS
            telemetry.addData("GAMEPAD1", "Front %f,  Right %f, Turn %f", leftY1, leftX1, rightX1);
            telemetry.update();
            }

          catch(Exception e){
            telemetry.addData("TELEOP 1:", "%s", e.toString());
            telemetry.update();
            RobotLog.ee("SMTECH", e, "TELEOP 1");
            }
          }
        else{
          robot.stopChassisMotors();
        }

        idle();
      }
    }
  }
}