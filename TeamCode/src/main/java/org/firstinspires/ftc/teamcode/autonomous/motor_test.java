//Before use set config to 'test'

package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.data.TeamHardware;

@Autonomous(name = "Lunatech-motortest", group = "Testers")
public class motor_test extends LinearOpMode {

    private DcMotorEx motor;
    private SignalSleeveDetectorMain signalSleeveDetector;
    int signal_sleeve = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotorEx.class, "motor");

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
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        if(opModeIsActive()){
            while (opModeIsActive()){
                //motor.setPower(1);
                //telemetry.addData("Velocity Calc: ", (314 * TeamHardware.COUNTS_PER_MOTOR_REV) / 60);

                signal_sleeve = signalSleeveDetector.getDetected_tag();
                telemetry.addData("APRIL TAG Signal Sleeve: ", "%s", String.valueOf((signal_sleeve)));
                telemetry.update();

                automotors(1);
                telemetry.addData("Actual Velocity: ", motor.getVelocity());
                telemetry.addData("RPM: ", (motor.getVelocity() / TeamHardware.COUNTS_PER_MOTOR_REV) * 60);
                telemetry.update();
            }
        }
    }

    void automotors(int level){
        switch (level){
            case 0:
                motor.setTargetPosition(-motor.getTargetPosition());
                motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                motor.setPower(1);
                break;

            case 1:
                motor.setTargetPosition(-motor.getTargetPosition() + 520);
                motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                motor.setPower(1);
                break;

            case 2:
                motor.setTargetPosition(-motor.getTargetPosition() + 1030);
                motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                motor.setPower(1);
                break;

            case 3:
                motor.setTargetPosition(-motor.getTargetPosition() + 1540);
                motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                motor.setPower(1);
                break;
        }
    }
}
