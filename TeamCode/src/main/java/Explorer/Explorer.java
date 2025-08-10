package Explorer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import DriveTrain.DriveTrainController;
import DriveTrain.Electronics.Gyro;

public class Explorer {

    public Gyro gyro;

    public DriveTrainController driveTrainController;

    public LinearOpMode linearOpMode;



    public Explorer(LinearOpMode linearOpMode){
        this.linearOpMode = linearOpMode;
        gyro = new Gyro(this);
        driveTrainController = new DriveTrainController(this);
    }

    public void update(){
        gyro.update();
        driveTrainController.update();
    }

}
