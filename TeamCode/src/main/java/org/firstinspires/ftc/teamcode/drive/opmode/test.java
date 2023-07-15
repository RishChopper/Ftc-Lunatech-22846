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
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

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

        Trajectory init_mov = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(8)
                .build();

        Trajectory init_to_junction = drive.trajectoryBuilder(init_mov.end())
                .splineTo(new Vector2d(49, -6), 0)
                //.splineTo(new Vector2d(52, -16.2), 0)
                .build();

        Trajectory junc = drive.trajectoryBuilder(init_to_junction.end())
                        .strafeRight(11.5)
                                .build();

        robot.autoDropLinearSlides(3);
        robot.moveClaws("Scorpion", false, false, "Extend");
        drive.followTrajectory(init_mov);
        drive.followTrajectory(init_to_junction);
        drive.followTrajectory(junc);

        while (drive.isBusy()){}

        sleep(200);

        robot.autoDropLinearSlides(4);

        sleep(2000);

        robot.moveClaws("Scorpion", true, false, "Extend");

        sleep (500);

        TrajectorySequence junc_to_stack = drive.trajectorySequenceBuilder(junc.end())
                .lineToLinearHeading(new Pose2d(50, 16, Math.toRadians(180)))
                .build();

        Trajectory myTrajectory = null;
        if (signal_sleeve == 1){
            myTrajectory = drive.trajectoryBuilder(junc.end())
                    .strafeLeft(38)
                    .build();
        } else if (signal_sleeve == 2){
            myTrajectory = drive.trajectoryBuilder(junc.end())
                    .strafeLeft(14)
                    .build();
        } else if (signal_sleeve == 3) {
            myTrajectory = drive.trajectoryBuilder(junc.end())
                    .strafeRight(13)
                    .build();
        }

        //drive.followTrajectorySequence(junc_to_stack);
        drive.followTrajectory(myTrajectory);
    }
}