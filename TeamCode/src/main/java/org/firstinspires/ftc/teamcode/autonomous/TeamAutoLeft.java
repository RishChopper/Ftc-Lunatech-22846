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
    private SignalSleeveDetectorMain signalSleeveDetector;

    int signal_sleeve = 0;

    @Override
    public void runOpMode() {
        robot = new TeamHardware(hardwareMap, telemetry, this);
        //signalSleeveDetector = new SignalSleeveDetectorMain(hardwareMap, telemetry);
        robot.init_auto(this);
        //robot.moveClaw(2);
        sleep(500);

        waitForStart();

        try {
            //signal_sleeve = signalSleeveDetector.getDetected_tag();
            telemetry.addData("Signal Sleeve: ", "%s", String.valueOf((signal_sleeve)));
            telemetry.update();

            //robot.autoLinearSlides("up");
            robot.encoderDrive(0.5, DataHolder.MOVEDIR.RIGHT, -3, 5);
            robot.encoderDrive(0.5, DataHolder.MOVEDIR.FRONT, -65, 5);
            robot.encoderDrive(0.5, DataHolder.MOVEDIR.RIGHT, -14, 5);
            robot.encoderDrive(0.5, DataHolder.MOVEDIR.FRONT, -2, 5);
            //robot.autoLinearSlides("down");
            //drop pre-loaded cone
            //robot.moveClaw(1);
            sleep(500);

            robot.encoderDrive(0.5, DataHolder.MOVEDIR.BACK, -6, 5);

            switch(signal_sleeve){
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
            }


            //AUTONOMOUS ENDS

        }catch (Exception e){
            telemetry.addData("CRITICAL_ERROR_AUTONOMOUS_2: ", "%s", e.toString());
            telemetry.update();
            RobotLog.ee("Lunatech", e, "AUTO 2");
        }
    }
}
