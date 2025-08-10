package DriveTrain;

import android.provider.ContactsContract;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;

import DriveTrain.Electronics.BaseSensors;
import DriveTrain.Electronics.Gyro;
import Explorer.Explorer;

@Config

public class DriveTrainListener {

    Explorer explorer;

    public DigitalChannel rightBut;
    public DigitalChannel leftBut;

    public Gyro gyro;



    ///to do write ultrasonic
    public DriveTrainListener(Explorer explorer){
        this.explorer = explorer;

        rightBut = BaseSensors.rightButton;
        leftBut = BaseSensors.leftButton;

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
}
