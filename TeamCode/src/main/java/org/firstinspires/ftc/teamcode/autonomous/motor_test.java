package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.data.TeamHardware;

@TeleOp(name = "Lunatech-motortest", group = "MainCode")
public class motor_test extends LinearOpMode {

    private TeamHardware robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new TeamHardware(hardwareMap,telemetry);
        robot.init_motor();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        if(opModeIsActive()){
            while (opModeIsActive()){
                robot.setMotor(1.0);
            }
        }

        telemetry.addData("Motor Test", "Complete");
        telemetry.update();
    }
}
