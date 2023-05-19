package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.hardware.LightSensor;

import org.firstinspires.ftc.teamcode.data.DataHolder;
import org.firstinspires.ftc.teamcode.data.MotorData;
import org.firstinspires.ftc.teamcode.data.TeamHardware;


@Autonomous(name = "Lunatech-AutoRight", group = "MainCode")
public class TeamAutoRight extends LinearOpMode {
    //private TeamHardware robot;
    private SignalSleeveDetectorMain signalSleeveDetector;

    int signal_sleeve = 0;

    @Override
    public void runOpMode() {
        //robot = new TeamHardware(hardwareMap, telemetry, this);
        signalSleeveDetector = new SignalSleeveDetectorMain(hardwareMap, telemetry, 5000);
        signalSleeveDetector.init();
        //robot.init_auto(this);
        //robot.moveClaws(false, false);

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
