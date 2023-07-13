package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.SignalSleeveDetectorMain;
import org.firstinspires.ftc.teamcode.data.TeamHardware;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "Lunatech-ODO-AUTO", group = "MainCode")
public class test extends LinearOpMode {
    SignalSleeveDetectorMain signalSleeveDetector;
    TeamHardware robot;
    int signal_sleeve = 0;

    @Override
    public void runOpMode() {
        signalSleeveDetector = new SignalSleeveDetectorMain(hardwareMap, telemetry, 5000);
        signalSleeveDetector.init();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        robot = new TeamHardware(hardwareMap, telemetry, this);
        robot.init_auto(this);

        robot.moveClaws("Scorpion", false, true, "Extend");

        waitForStart();

        if (isStopRequested()) return;

        signal_sleeve = signalSleeveDetector.getDetected_tag();
        telemetry.addData("APRIL TAG Signal Sleeve: ", "%s", String.valueOf((signal_sleeve)));
        telemetry.update();

        Pose2d startPose = new Pose2d(-63, 40, Math.toRadians(90));
        drive.trajectoryBuilder(startPose, false);

        Trajectory init_to_junction = drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(54, -5), 0)
                //.splineTo(new Vector2d(52, -16.2), 0)
                .build();

        Trajectory junc = drive.trajectoryBuilder(init_to_junction.end())
                        .strafeRight(13.5)
                                .build();

        robot.autoDropLinearSlides(3);
        drive.followTrajectory(init_to_junction);
        drive.followTrajectory(junc);

        while (drive.isBusy()){}

        sleep(200);

        robot.autoDropLinearSlides(4);

        sleep(2000);

        robot.moveClaws("Scorpion", true, false, "Extend");

        sleep (500);

        Trajectory myTrajectory = null;
        switch (signal_sleeve) {
            case 1:
                myTrajectory = drive.trajectoryBuilder(junc.end())
                        .strafeLeft(55)
                        .build();
                break;

            case 2:
                myTrajectory = drive.trajectoryBuilder(junc.end())
                        .strafeLeft(15)
                        .build();

            case 3:
                myTrajectory = drive.trajectoryBuilder(junc.end())
                        .strafeRight(15)
                        .build();
                break;
        }

        drive.followTrajectory(myTrajectory);
    }
}