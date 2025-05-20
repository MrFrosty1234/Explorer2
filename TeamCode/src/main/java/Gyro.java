import static com.qualcomm.hardware.rev.RevHubOrientationOnRobot.xyzOrientation;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Gyro {

        public static double xRotation = 0;
        public static double yRotation = 0;
        public static double headingRotation = 0;
        private double angle = 0;
        IMU gyro;
        Explorer explorer;

        public Gyro(Explorer robot){

            explorer = robot;

            gyro = explorer.linearOpMode.hardwareMap.get(IMU.class,"imu");
            Orientation hubRotation = xyzOrientation(xRotation, yRotation, headingRotation);

            RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot
                    (RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.UP);
            gyro.initialize(new IMU.    Parameters(orientationOnRobot));

            reset();
        }
        public void reset(){
            gyro.resetYaw();
        }
        public void update(){
            angle = gyro.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        }
        public double getAngle(){
            return -angle;
        }
}
