package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.SignalSleeveDetectorMain;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "Lunatech-ENCODERTEST", group = "MainCode")
public class test extends LinearOpMode {
    SignalSleeveDetectorMain signalSleeveDetector;
    int signal_sleeve = 0;

    @Override
    public void runOpMode() {
        signalSleeveDetector = new SignalSleeveDetectorMain(hardwareMap, telemetry, 5000);
        signalSleeveDetector.init();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        signal_sleeve = signalSleeveDetector.getDetected_tag();
        telemetry.addData("APRIL TAG Signal Sleeve: ", "%s", String.valueOf((signal_sleeve)));
        telemetry.update();

        Trajectory myTrajectory1 = null;
        Trajectory myTrajectory2 = null;
        Trajectory myTrajectory3 = null;
        switch (signal_sleeve) {
            case 1:
                myTrajectory1 = drive.trajectoryBuilder(new Pose2d())
                        .strafeRight(5)
                        .build();
                myTrajectory2 = drive.trajectoryBuilder(new Pose2d())
                        .forward(50)
                        .build();
                myTrajectory3 = drive.trajectoryBuilder(new Pose2d())
                        .strafeLeft(20)
                        .build();
                break;

            case 2:
                break;

            case 3:
                break;
        }

        drive.followTrajectory(myTrajectory1);
        drive.followTrajectory(myTrajectory2);
        drive.followTrajectory(myTrajectory3);
    }
}