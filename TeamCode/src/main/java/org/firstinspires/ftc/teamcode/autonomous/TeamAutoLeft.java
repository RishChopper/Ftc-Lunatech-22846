package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.hardware.LightSensor;

import org.firstinspires.ftc.teamcode.data.DataHolder;
import org.firstinspires.ftc.teamcode.data.MotorData;
import org.firstinspires.ftc.teamcode.data.TeamHardware;


@Autonomous(name = "Lunatech-AutoLeft", group = "MainCode")
public class TeamAutoLeft extends LinearOpMode {
    private TeamHardware robot;
    private MotorData motorData;
    private SignalSleeveDetectorMain signalSleeveDetector;
    private TeamHardware ColorSensor;


    int signal_sleeve = 0;

    @Override
    public void runOpMode() {


        robot = new TeamHardware(hardwareMap, telemetry, this);
        motorData = robot.getMotorData();
        signalSleeveDetector = new SignalSleeveDetectorMain(hardwareMap, telemetry);
        robot.init_auto(this);

        waitForStart();

        try {
            signal_sleeve = signalSleeveDetector.getDetected_tag();
            telemetry.addData("Signal Sleeve: ", "%s", String.valueOf((signal_sleeve)));
            telemetry.update();

            robot.encoderDrive(1.0, DataHolder.MOVEDIR.FRONT, 2*(DataHolder.ONE_BLOCK), 5);
            robot.encoderTurn(1.0, DataHolder.MOVEDIR.ROTATE_LEFT, 90, 5);
            robot.encoderDrive(1.0, DataHolder.MOVEDIR.FRONT, DataHolder.ONE_BLOCK, 5);
            //PICK UP CONE
            robot.moveClaw(1);

            robot.encoderDrive(1.0, DataHolder.MOVEDIR.BACK, DataHolder.ONE_BLOCK, 5);
            robot.encoderTurn(1.0, DataHolder.MOVEDIR.ROTATE_LEFT, 135, 5);
            //DROP CONE
            robot.moveClaw(-1);

            //REPEAT PROCESS AS LONG AS WE WANT

            //RETURN TO PARK
            robot.encoderTurn(1.0, DataHolder.MOVEDIR.ROTATE_RIGHT, 45, 5);
            if(signal_sleeve != 2){
                //PARK
                //DECIDE WHERE TO GO IN var - int signal_sleeve
                switch(signal_sleeve){
                    case 1:
                        //GO TO SPOT 1
                        robot.encoderDrive(1.0, DataHolder.MOVEDIR.RIGHT, DataHolder.ONE_BLOCK, 5);
                        break;

                    case 3:
                        //GO TO SPOT 3
                        robot.encoderDrive(1.0, DataHolder.MOVEDIR.LEFT, DataHolder.ONE_BLOCK, 5);
                        break;

                    default:
                }
            }
            //AUTONOMOUS ENDS

        }catch (Exception e){
            telemetry.addData("CRITICAL_ERROR_AUTONOMOUS_1: ", "%s", e.toString());
            telemetry.update();
            RobotLog.ee("Lunatech", e, "AUTO 1");
        }
    }
}
