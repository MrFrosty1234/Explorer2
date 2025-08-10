package Explorer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import DriveTrain.DriveTrainController;
import DriveTrain.Electronics.Gyro;
import SortingPucks.ColorDefine.ColorDetective;
import SortingPucks.Sorting.SortingAndKeep;


public class Explorer {

    public Gyro gyro;

    public DriveTrainController driveTrainController;

    public SortingAndKeep sortingAndKeep;

    public LinearOpMode linearOpMode;

    public ColorDetective colorDetective;



    public Explorer(LinearOpMode linearOpMode){
        this.linearOpMode = linearOpMode;
        gyro = new Gyro(this);
        driveTrainController = new DriveTrainController(this);
        sortingAndKeep = new SortingAndKeep(this);
        colorDetective = new ColorDetective(this);
    }

    public void update(){
        gyro.update();
        driveTrainController.update();
    }

}
