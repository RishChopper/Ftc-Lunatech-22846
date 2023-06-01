package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.sun.tools.javac.comp.Check;

import org.firstinspires.ftc.teamcode.data.Checkpoint;
import org.firstinspires.ftc.teamcode.data.DataHolder;
import org.firstinspires.ftc.teamcode.data.MotorData;
import org.firstinspires.ftc.teamcode.data.TeamHardware;

import java.util.ArrayList;
import java.util.List;


@Autonomous(name = "Lunatech-AutoRight", group = "MainCode")
public class TeamAutoRight extends LinearOpMode {
    TeamHardware robot;
    SignalSleeveDetectorMain signalSleeveDetector;
    DataHolder dataHolder = new DataHolder();

    int signal_sleeve = 0;
    Checkpoint checkpointMidToPole = new Checkpoint(91964, 91964, 0);
    Checkpoint checkpointPole = new Checkpoint();
    Checkpoint checkpointMidToStack = new Checkpoint();
    Checkpoint checkpointStack = new Checkpoint();
    Checkpoint checkpointP1 = new Checkpoint();
    Checkpoint[] checkpoints = new Checkpoint[5];

    @Override
    public void runOpMode() {
        checkpoints[0] = checkpointMidToPole;
        checkpoints[1] = checkpointPole;
        checkpoints[2] = checkpointMidToStack;
        checkpoints[3] = checkpointStack;
        checkpoints[4] = checkpointP1;

        robot = new TeamHardware(hardwareMap, telemetry, this);
        signalSleeveDetector = new SignalSleeveDetectorMain(hardwareMap, telemetry, 5000);
        signalSleeveDetector.init();
        robot.init_auto(this);
        robot.moveClaws(false, false);

        waitForStart();

        try {
            signal_sleeve = signalSleeveDetector.getDetected_tag();
            telemetry.addData("APRIL TAG Signal Sleeve: ", "%s", String.valueOf((signal_sleeve)));
            telemetry.update();


        }catch (Exception e){
            telemetry.addData("CRITICAL_ERROR_AUTONOMOUS_2: ", "%s", e.toString());
            telemetry.update();
            RobotLog.ee("Lunatech", e, "AUTO 2");
        }
    }
}
