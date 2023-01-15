package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="ServoTestAuto", group="Lunatech")
public class ServoTest extends LinearOpMode {

    private TeamHardware robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new TeamHardware(hardwareMap,telemetry);
        robot.init_auto(this);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        robot.setServoPosition("Servo0", 180);
        sleep(2000);
        robot.setServoPosition("Servo0", 0);
        sleep(2000);
        robot.setServoPosition("Servo0", 180);

        telemetry.addData("Servo Test", "Complete");
        telemetry.update();
    }
}
