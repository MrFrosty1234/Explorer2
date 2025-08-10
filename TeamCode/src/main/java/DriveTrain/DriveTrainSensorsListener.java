package DriveTrain;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import DriveTrain.Electronics.BaseSensors;
import DriveTrain.Electronics.Gyro;
import Explorer.Explorer;
import Utilities.I2cPort;

@Config

public class DriveTrainSensorsListener {

    Explorer explorer;

    public DigitalChannel rightBut;
    public DigitalChannel leftBut;

    public Gyro gyro;

    I2cPort sonar;


    public DriveTrainSensorsListener(Explorer explorer){
        this.explorer = explorer;

        rightBut = BaseSensors.rightButton;
        leftBut = BaseSensors.leftButton;
        sonar = BaseSensors.sonar;

    }

    public void init(){
        BaseSensors.init(explorer.linearOpMode.hardwareMap);
    }

    public boolean getButtonsValue(){
        return rightBut.getState() && leftBut.getState();
    }

    public double getAngle(){
        return gyro.getAngle();
    }

    public double getDist(){
        BaseSensors.sonar.write((byte) 5);
        return sonar.read();

    }

}
