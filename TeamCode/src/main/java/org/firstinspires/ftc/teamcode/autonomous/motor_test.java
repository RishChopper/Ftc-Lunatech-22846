//Before use set config to 'test'

package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.data.TeamHardware;

@Autonomous(name = "Lunatech-motortest", group = "Testers")
public class motor_test extends LinearOpMode {

    private DcMotorEx motor;
    private TeamHardware robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new TeamHardware(hardwareMap, telemetry, this);
        motor = robot.getIntakeTiltMech();

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        while (opModeIsActive()){
            telemetry.addData("Encoder: Tilt Mech", motor.getCurrentPosition());
            telemetry.update();
        }
    }
}