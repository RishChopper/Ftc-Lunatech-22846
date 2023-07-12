package org.firstinspires.ftc.teamcode.autonomous;

import android.util.Log;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.hardware.LightSensor;

import org.firstinspires.ftc.teamcode.data.DataHolder;
import org.firstinspires.ftc.teamcode.data.MotorData;
import org.firstinspires.ftc.teamcode.data.TeamHardware;


@Autonomous(name = "Lunatech-AutoLeft", group = "MainCode")
public class TeamAutoLeft extends LinearOpMode {
    private TeamHardware robot;
    private SignalSleeveDetectorMain signalSleeveDetector;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    MultipleTelemetry mulTelemetry;

    private DcMotorEx intakeSlide;

    int signal_sleeve = 0;

    @Override
    public void runOpMode () {
        mulTelemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        robot = new TeamHardware(hardwareMap, mulTelemetry, this);
        signalSleeveDetector = new SignalSleeveDetectorMain(hardwareMap, telemetry, 5000);
        signalSleeveDetector.init();
        robot.init_auto(this);
        intakeSlide = robot.getIntakeTiltMech();
        intakeSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        dashboard.getTelemetry().addData("rpm", intakeSlide.getVelocity());
        dashboard.getTelemetry().update();

        waitForStart();

        try {
            /*while (opModeIsActive()){
                signal_sleeve = signalSleeveDetector.getDetected_tag();
                Log.d("APRIL TAG", "Detected id: " + signal_sleeve);
                telemetry.addData("Signal Sleeve: ", "%s", String.valueOf((signal_sleeve)));
                telemetry.update();
            }

            sleep(50000);
            */

            intakeSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            /** Procedure:
             * 1) flip drop grabber and open
             * 2) Pickup cone thru intake grabby
             * 3) Move Intake grabby over
             * 4) Engage drop grabby and release intake grabby
             * 5) Rotate drop grabby and Move intake grabby bak at the same time
             * 6) Drop cone
             * Repeat
             */

            //1
            //telemetry.addData("rpm", intakeSlide.getVelocity());
            //dashboard.getTelemetry().addData("rpm", intakeSlide.getVelocity());
            //dashboard.getTelemetry().update();
            //telemetry.update();

            //2
            robot.moveClaws("Scorpion", false, true, "Extend");

            boolean tilt_motion = robot.autointakeTiltMech(true, 3);
            while (!tilt_motion){
                tilt_motion = robot.autointakeTiltMech(true, 1);
                telemetry.addData("Seq", "Waiting for motion to complete");
                telemetry.update();
            }
            telemetry.addData("Seq", "Motion finished");
            telemetry.update();

            //3
            robot.moveClaws("Scorpion", true, false, "Contract");
            sleep(300);

            tilt_motion = robot.autointakeTiltMech(false, 3);
            while (!tilt_motion){
                tilt_motion = robot.autointakeTiltMech(false, 3);
                telemetry.addData("Seq", "Waiting for motion to complete");
                telemetry.update();
            }
            telemetry.addData("Seq", "Motion finished");
            telemetry.update();

            robot.moveClaws("Scorpion", false, true, "Contract");
            sleep(200);

            tilt_motion = robot.autointakeTiltMech(true, 2);
            sleep(200);
            while (!tilt_motion){
                tilt_motion = robot.autointakeTiltMech(true, 0);
                robot.moveClaws("Scorpion", false, true, "Extend");
                telemetry.addData("Seq", "Waiting for motion to complete");
                telemetry.update();
            }
            telemetry.addData("Seq", "Motion finished");
            telemetry.update();

            robot.moveClaws("Scorpion", true, false, "Contract");



            /*

            //4
            robot.autointakeTiltMech(false, 0);
            while (intakeSlide.isBusy()){
                telemetry.addData("Intake Mech State:", "In Motion");
                telemetry.update();
            }
            telemetry.addData("Intake Mech State:", "At rest");
            telemetry.update();

            //5
            robot.moveClaws("Scorpion", false, true, true);

            */


            /*switch(signal_sleeve){
                case 1:
                    robot.encoderDrive(0.5, DataHolder.MOVEDIR.LEFT, -34, 5);
                    break;
                case 3:
                    //GO TO SPOT 3
                    robot.encoderDrive(0.5, DataHolder.MOVEDIR.RIGHT, -10, 5);
                    break;

                case 2:
                    //GO TO SPOT 2
                    robot.encoderDrive(0.5, DataHolder.MOVEDIR.LEFT, -10, 5);
                    break;
             }*/


             //AUTONOMOUS ENDS

        }catch (Exception e){
            telemetry.addData("CRITICAL_ERROR_AUTONOMOUS_2: ", "%s", e.toString());
            telemetry.update();
            RobotLog.ee("Lunatech", e, "AUTO 2");
        }
    }
}
