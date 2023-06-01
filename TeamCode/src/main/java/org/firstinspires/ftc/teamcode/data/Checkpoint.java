package org.firstinspires.ftc.teamcode.data;

public class Checkpoint {
    int leftEncoderTicks;
    int rightEncoderTicks;
    int rearEncoderTicks;

    public Checkpoint(int leftEncoderTicks, int rightEncoderTicks, int rearEncoderTicks) {
        this.leftEncoderTicks = leftEncoderTicks;
        this.rightEncoderTicks = rightEncoderTicks;
        this.rearEncoderTicks = rearEncoderTicks;
    }

    public Checkpoint() {
    }

    public int getLeftEncoderTicks() {
        return leftEncoderTicks;
    }

    public void setLeftEncoderTicks(int leftEncoderTicks) {
        this.leftEncoderTicks = leftEncoderTicks;
    }

    public int getRightEncoderTicks() {
        return rightEncoderTicks;
    }

    public void setRightEncoderTicks(int rightEncoderTicks) {
        this.rightEncoderTicks = rightEncoderTicks;
    }

    public int getRearEncoderTicks() {
        return rearEncoderTicks;
    }

    public void setRearEncoderTicks(int rearEncoderTicks) {
        this.rearEncoderTicks = rearEncoderTicks;
    }
}
