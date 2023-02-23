//Before use set config to 'test'

package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.data.TeamHardware;

@Autonomous(name="Lunatech-ServoTest", group="Testers")
public class ServoTest extends LinearOpMode {

    private Servo servo;

    @Override
    public void runOpMode() throws InterruptedException {


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        telemetry.addData("Servo Test: ", "Empty Program");
        telemetry.update();

        sleep(5000);
    }
}
