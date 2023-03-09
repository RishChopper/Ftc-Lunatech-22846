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

    int signal_sleeve = 0;

    @Override
    public void runOpMode() {


        robot = new TeamHardware(hardwareMap, telemetry, this);
        motorData = robot.getMotorData();
        signalSleeveDetector = new SignalSleeveDetectorMain(hardwareMap, telemetry);
        robot.init_auto(this);
        robot.moveClaw(2);
        sleep(500);

        waitForStart();

        try {
            signal_sleeve = signalSleeveDetector.getDetected_tag();
            telemetry.addData("Signal Sleeve: ", "%s", String.valueOf((signal_sleeve)));
            telemetry.update();

            robot.autoLinearSlides("lil_up");
            robot.encoderDrive(1, DataHolder.MOVEDIR.FRONT, -1, 5);
            robot.encoderDrive(1, DataHolder.MOVEDIR.RIGHT, -20, 5);
            robot.autoLinearSlides("up");
            robot.encoderDrive(1, DataHolder.MOVEDIR.FRONT, -60, 5);
            robot.encoderTurn(1, DataHolder.MOVEDIR.ROTATE_LEFT, -42, 5);
            robot.encoderDrive(1, DataHolder.MOVEDIR.FRONT, -10, 5);
            //drop pre-loaded cone
            robot.moveClaw(1);
            sleep(500);
            robot.autoLinearSlides("down");
            robot.encoderTurn(1, DataHolder.MOVEDIR.ROTATE_LEFT, -42, 5);
            robot.encoderDrive(1, DataHolder.MOVEDIR.LEFT, -10, 5);

            if(signal_sleeve != 3){
                //PARK
                //DECIDE WHERE TO GO IN var - int signal_sleeve
                switch(signal_sleeve){
                    case 1:
                        //GO TO SPOT 1
                        robot.encoderDrive(1.0, DataHolder.MOVEDIR.FRONT, -40, 5);
                        break;

                    case 3:
                        //GO TO SPOT 3
                        robot.encoderDrive(1.0, DataHolder.MOVEDIR.FRONT, -20, 5);
                        break;
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
