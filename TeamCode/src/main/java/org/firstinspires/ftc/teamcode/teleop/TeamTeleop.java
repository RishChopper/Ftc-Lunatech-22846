package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.autonomous.SignalSleeveDetectorMain;
import org.firstinspires.ftc.teamcode.data.MotorData;
import org.firstinspires.ftc.teamcode.data.TeamHardware;

@TeleOp(name = "Lunatech-TeleOp", group = "MainCode")
public class TeamTeleop extends LinearOpMode {

  private TeamHardware robot;
  private MotorData motorData;
  private SignalSleeveDetectorMain signalSleeveDetector;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    double leftX1;
    double leftY1;
    double rightX1;
    double rightY1;

    double triggers_value;
    double rightTrigger;
    double leftTrigger;

    boolean a, b, x, y;

    double leftFrontRPM;
    double rightFrontRPM;
    double leftBackRPM;
    double rightBackRPM;
    double linearSlide1RPM;
    double linearSlide2RPM;

    robot = new TeamHardware(hardwareMap,telemetry);
    signalSleeveDetector = new SignalSleeveDetectorMain(hardwareMap, telemetry);
    motorData = robot.getMotorData();

    robot.init_teleop();

    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {

        if (!gamepad1.atRest()) { // Only checks wheels & trigger
          try {

            leftX1 = Range.clip(gamepad1.left_stick_x, -1, 1);
            leftY1 = -Range.clip(gamepad1.left_stick_y, -1, 1);
            rightX1 = -Range.clip(gamepad1.right_stick_x, -1, 1);
            rightY1 = Range.clip(gamepad1.right_stick_y, -1, 1);

            a = gamepad1.a;
            b = gamepad1.b;
            x = gamepad1.x;
            y = gamepad1.y;

            rightTrigger = Range.clip(gamepad1.right_trigger, -1, 1);
            leftTrigger = -Range.clip(gamepad1.left_trigger, -1, 1);
            triggers_value = rightTrigger + leftTrigger;

            //Set motor power:
            robot.setMotors(leftX1, leftY1, -rightX1);
            //robot.moveMotors("FORWARD", leftY1);

            robot.moveLinearSlides(triggers_value);

            /*if (a && b){
              robot.setServoPosition("Servo0", 0);
            }else if (a){
              robot.setServoPosition("Servo0", 180);
            } else if (b) {
              robot.setServoPosition("Servo0", 0);
            }*/

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
            telemetry.update();
            }

          catch(Exception e){
            telemetry.addData("TELEOP 1:", "%s", e.toString());
            telemetry.update();
            RobotLog.ee("Lunatech", e, "TELEOP 1");
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