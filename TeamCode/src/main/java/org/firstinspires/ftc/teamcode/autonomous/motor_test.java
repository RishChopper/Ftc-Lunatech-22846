//Before use set config to 'test'

package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.data.TeamHardware;

@Autonomous(name = "Lunatech-motortest", group = "Testers")
public class motor_test extends LinearOpMode {

    private DcMotorEx motor;
    private TouchSensor limitSwitch;
    private SignalSleeveDetectorMain signalSleeveDetector;
    int signal_sleeve = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotorEx.class, "motor");
        limitSwitch = hardwareMap.get(TouchSensor.class, "limit_switch");

//        motor.setDirection(DcMotorEx.Direction.FORWARD);
//        motor.setPower(0);
//        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        signalSleeveDetector = new SignalSleeveDetectorMain(hardwareMap, telemetry, 5000);
        signalSleeveDetector.init();

        motor.setDirection(DcMotorEx.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motor.setPower(0.0);
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        while (opModeInInit()){
            telemetry.addData("Encoder: ", motor.getCurrentPosition());
            telemetry.addData("Limit Switch State: ", limitSwitch.isPressed());
            telemetry.update();
        }

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //motor.setPower(1);
                //telemetry.addData("Velocity Calc: ", (314 * TeamHardware.COUNTS_PER_MOTOR_REV) / 60);

                //signal_sleeve = signalSleeveDetector.getDetected_tag();
                //telemetry.addData("APRIL TAG Signal Sleeve: ", "%s", String.valueOf((signal_sleeve)));
                //telemetry.update();

                motor.setPower(1.0);
                motor.setTargetPosition(2040);

                telemetry.addData("Actual Velocity: ", motor.getVelocity());
                telemetry.addData("RPM: ", (motor.getVelocity() / TeamHardware.COUNTS_PER_MOTOR_REV) * 60);
                telemetry.update();
            }
        }
    }
}