//Before use set config to 'test'

package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.data.TeamHardware;

@Autonomous(name = "Lunatech-motortest", group = "Testers")
public class motor_test extends LinearOpMode {

    private DcMotorEx motor;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotorEx.class, "motor");

        motor.setDirection(DcMotorEx.Direction.FORWARD);
        motor.setPower(0.0);
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        if(opModeIsActive()){
            while (opModeIsActive()){
                motor.setPower(1.0);
                telemetry.addData("RPM: ", (motor.getVelocity() / TeamHardware.COUNTS_PER_MOTOR_REV) * 60);
                telemetry.update();
            }
        }
    }
}
