package DriveTrain;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import DriveTrain.Electronics.BaseSensors;
import DriveTrain.Electronics.Gyro;
import Explorer.Explorer;
import Utilities.I2cPort;

@Config

public class DriveTrainSensorsListener {

    Explorer explorer;

    public DigitalChannel rightBut;
    public DigitalChannel leftBut;

    public IMU gyro;

    I2cPort sonar;


    public DriveTrainSensorsListener(Explorer explorer){
        this.explorer = explorer;

        rightBut = BaseSensors.rightButton;
        leftBut = BaseSensors.leftButton;
        sonar = BaseSensors.sonar;
        gyro = explorer.linearOpMode.hardwareMap.get(IMU.class,"imu");

    }

    public void init(){
        BaseSensors.init(explorer.linearOpMode.hardwareMap);
    }

    public boolean getButtonsValue(){
        return rightBut.getState() && leftBut.getState();
    }

    public double getAngle(){
        return gyro.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public double getDist(){
        BaseSensors.sonar.write((byte) 5);
        return sonar.read();

    }

}
