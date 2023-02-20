package org.firstinspires.ftc.teamcode.data;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class MotorData {
    public DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;
    private DcMotorEx linearSlide1;
    private DcMotorEx linearSlide2;

    public MotorData(DcMotorEx frontLeft, DcMotorEx frontRight, DcMotorEx backLeft, DcMotorEx backRight, DcMotorEx linearSlide1, DcMotorEx linearSlide2) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.linearSlide1 = linearSlide1;
        this.linearSlide2 = linearSlide2;
    }

    public MotorData(DcMotorEx frontLeft, DcMotorEx frontRight, DcMotorEx backLeft, DcMotorEx backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
    }

    public DcMotorEx getFrontLeft() {
        return frontLeft;
    }

    public void setFrontLeft(DcMotorEx frontLeft) {
        this.frontLeft = frontLeft;
    }

    public DcMotorEx getFrontRight() {
        return frontRight;
    }

    public void setFrontRight(DcMotorEx frontRight) {
        this.frontRight = frontRight;
    }

    public DcMotorEx getBackLeft() {
        return backLeft;
    }

    public void setBackLeft(DcMotorEx backLeft) {
        this.backLeft = backLeft;
    }

    public DcMotorEx getBackRight() {
        return backRight;
    }

    public void setBackRight(DcMotorEx backRight) {
        this.backRight = backRight;
    }

    public DcMotorEx getLinearSlide1() {
        return linearSlide1;
    }

    public void setLinearSlide1(DcMotorEx linearSlide1) {
        this.linearSlide1 = linearSlide1;
    }

    public DcMotorEx getLinearSlide2() {
        return linearSlide2;
    }

    public void setLinearSlide2(DcMotorEx linearSlide2) {
        this.linearSlide2 = linearSlide2;
    }
}
