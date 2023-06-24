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
            /** Procedure:
             * 1) Raise slide to intake pos
             * 2) flip drop grabber and open
             * 3) Pickup cone thru intake grabby
             * 4) Move Intake grabby over
             * 5) Engage drop grabby and release intake grabby
             * 6) Rotate drop grabby and Move intake grabby bak at the same time
             * 7) Drop cone
             * Repeat
             */

            //1
            robot.autoDropLinearSlides(4);
            robot.autointakeTiltMech(true, 3);
            //2
            robot.moveClaws("Scorpion", true, true, false);
            //3
            robot.moveClaws("Scorpion", true, false, false);
            //4
            robot.autointakeTiltMech(false, 0);
            //5
            robot.moveClaws("Scorpion", false, true, true);

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
