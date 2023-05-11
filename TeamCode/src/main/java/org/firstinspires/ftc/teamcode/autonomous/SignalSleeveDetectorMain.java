package org.firstinspires.ftc.teamcode.autonomous;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class SignalSleeveDetectorMain
{
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    HardwareMap hardwareMap;
    Telemetry telemetry;

    // Lens intrinsics
    // UNITS ARE PIXELS
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    int left = 4;
    int middle = 5;
    int right = 6;
    int detected_tag = 1;
    long timeout;
    long init_millis;

    AprilTagDetection tagOfInterest = null;

    public SignalSleeveDetectorMain (HardwareMap hwMap, Telemetry tmry, long timeout_millis) {
        hardwareMap = hwMap;
        telemetry = tmry;
        timeout = timeout_millis;
    }

    public void init(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        telemetry.setMsTransmissionInterval(50);
    }

    public int getDetected_tag()
    {
        init_millis = System.currentTimeMillis();

        boolean tagFound = false;

        while (!tagFound)
        {
            if (System.currentTimeMillis() - init_millis >= timeout){
                Log.d("APRIL TAG FAILED TO DETECT", "ERROR: TAG NOT FOUND, DEFAULTING AND PROCEEDING");
                return detected_tag;
            }

            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {

                for(AprilTagDetection tag : currentDetections)
                {
                    Log.d("APRIL TAG DETECTED", "Tag ID: " + tag.id);

                    if(tag.id == left ||tag.id == middle||tag.id == right)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("A Tag is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }

            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");
            }

            telemetry.update();
        }

        /*
         * The START command just came in: now work off the latest snapshot acquired
         * during the init loop.
         */

        /* Update the telemetry */
        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was not in sight :(");
            telemetry.update();
        }

        /* Actually do something useful */
        if (tagOfInterest == null || tagOfInterest.id == left) {
            // left tag detected on signal sleeve
            telemetry.addLine("Left tag detected");

            detected_tag = 1;
        }
        else if(tagOfInterest.id== middle)
        {
            // middle tag detected
            telemetry.addLine("Middle tag detected");

            detected_tag = 2;
        }
        else if (tagOfInterest.id == right){
            // right tag detected
            telemetry.addLine("Right tag detected");

            detected_tag = 3;
        }

        return detected_tag;
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}
