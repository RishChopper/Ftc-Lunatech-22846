package org.firstinspires.ftc.teamcode.data;
public class DataHolder
{
            public static final double ONE_BLOCK = -26.50;
            private static int slideState = 0;
            public static final double JoyL1_Deadzone = 0.2;
            public static final double JoyR1_Deadzone = 0.2;
    public enum MOVEDIR
    {
        UNKNOWN,
        FRONT,
        FRONT_RIGHT,
        RIGHT,
        BACK_RIGHT,
        BACK,
        BACK_LEFT,
        LEFT,
        FRONT_LEFT,
        ROTATE_LEFT,
        ROTATE_RIGHT
    }

}