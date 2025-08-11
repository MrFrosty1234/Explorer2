package DriveTrain.Electronics;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.IMU;

import Utilities.I2cPort;

@Config


public class BaseSensors {

   public static DigitalChannel rightButton;
   public static DigitalChannel leftButton;
   public static I2cPort sonar;

   public static IMU gyro;

    public static void init(HardwareMap hardwareMap){
        rightButton =  hardwareMap.get(DigitalChannel.class, "rightButton");

        leftButton = hardwareMap.get(DigitalChannel.class, "leftButton");



        sonar = hardwareMap.get(I2cPort.class, "sonar");
        sonar.initWithI2cAddress(I2cAddr.create7bit(5));
    }

}
