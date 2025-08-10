package DriveTrain.Electronics;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

@Config


public class BaseSensors {

   public static DigitalChannel rightButton;
   public static DigitalChannel leftButton;

   public static IMU gyro;

    public static void init(HardwareMap hardwareMap){
        rightButton =  hardwareMap.get(DigitalChannel.class, "rightButton");

        leftButton = hardwareMap.get(DigitalChannel.class, "leftButton");

        gyro = hardwareMap.get(IMU.class, "gyro");
    }

}
