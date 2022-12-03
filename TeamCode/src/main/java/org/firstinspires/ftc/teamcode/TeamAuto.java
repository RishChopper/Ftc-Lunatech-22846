package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

@Autonomous(name="Auto", group="TeamX")
public class TeamAuto extends LinearOpMode {

    private TeamHardware robot;

    @Override
    public void runOpMode() {
        robot = new TeamHardware(hardwareMap,telemetry);
        robot.init_auto(this);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        robot.encoderDrive(5, DataHolder.MOVEDIR.FRONT_RIGHT, 0, 5);
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}