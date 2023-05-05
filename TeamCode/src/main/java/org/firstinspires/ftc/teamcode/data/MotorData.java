package org.firstinspires.ftc.teamcode.data;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class MotorData {
    public DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;
    private DcMotorEx intakeLinearSlide;
    private DcMotorEx dropLinearSlide;
    private Servo intakeClaw;
    private Servo dropClaw;
    private Servo dropClawRotate;

    public MotorData(DcMotorEx frontLeft, DcMotorEx frontRight, DcMotorEx backLeft, DcMotorEx backRight, DcMotorEx intakeLinearSlide, DcMotorEx dropLinearSlide, Servo intakeClaw, Servo dropClaw, Servo dropClawRotate) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.intakeLinearSlide = intakeLinearSlide;
        this.dropLinearSlide = dropLinearSlide;
        this.intakeClaw = intakeClaw;
        this.dropClaw = dropClaw;
        this.dropClawRotate = dropClawRotate;
    }

    public DcMotorEx getFrontLeft() {
        return frontLeft;
    }

    public DcMotorEx getFrontRight() {
        return frontRight;
    }

    public DcMotorEx getBackLeft() {
        return backLeft;
    }

    public DcMotorEx getBackRight() {
        return backRight;
    }

    public DcMotorEx getintakeLinearSlide() {
        return intakeLinearSlide;
    }


    public DcMotorEx getdropLinearSlide() {
        return dropLinearSlide;
    }

    public Servo getIntakeClaw() {
        return intakeClaw;
    }

    public Servo getDropClaw() {
        return dropClaw;
    }

    public Servo getDropClawRotate() {
        return dropClawRotate;
    }
}
