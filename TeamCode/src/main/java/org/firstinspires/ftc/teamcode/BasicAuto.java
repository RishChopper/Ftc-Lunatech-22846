package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="BasicAuto", group="Lunatech")
public class BasicAuto extends LinearOpMode {

    private TeamHardware robot;

    @Override
    public void runOpMode() {
        robot = new TeamHardware(hardwareMap,telemetry);
        robot.init_auto(this);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        robot.encoderDrive(1, DataHolder.MOVEDIR.FRONT, 22.75, 5);

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}