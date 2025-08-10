package Explorer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import DriveTrain.DriveTrainController;
import DriveTrain.Electronics.BaseSensors;
import DriveTrain.Electronics.Gyro;
import SortingPucks.ColorDefine.ColorDetective;
import SortingPucks.Sorting.SortingAndKeep;
import SortingPucks.SortingAndUnloading;


public class Explorer {

    public Gyro gyro;

    public DriveTrainController driveTrainController;

    public SortingAndKeep sortingAndKeep;

    public LinearOpMode linearOpMode;

    public ColorDetective colorDetective;

    public SortingAndUnloading sortingAndUnloading;



    public Explorer(LinearOpMode linearOpMode){
        this.linearOpMode = linearOpMode;
        gyro = new Gyro(this);
        driveTrainController = new DriveTrainController(this);
        sortingAndKeep = new SortingAndKeep(this);
        colorDetective = new ColorDetective(this);
        sortingAndUnloading = new SortingAndUnloading(this);
    }

    public void init(){
        BaseSensors.init(linearOpMode.hardwareMap);
        BaseSensors.init(linearOpMode.hardwareMap);

    }

    public void update(){
        gyro.update();
        driveTrainController.update();
    }

}
