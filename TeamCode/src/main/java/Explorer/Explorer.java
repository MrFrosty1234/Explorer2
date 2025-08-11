package Explorer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import DriveTrain.DriveTrainController;
import DriveTrain.DriveTrainSensorsListener;
import DriveTrain.Electronics.BaseMotors;
import DriveTrain.Electronics.BaseSensors;
import DriveTrain.Electronics.Gyro;
import SortingPucks.ColorDefine.ColorDetective;
import SortingPucks.Sorting.SortingAndKeep;


public class Explorer {

    public Gyro gyro;

    public DriveTrainController driveTrainController;

    public DriveTrainSensorsListener driveTrainSensorsListener;

    public SortingAndKeep sortingAndKeep;

    public LinearOpMode linearOpMode;

    public ColorDetective colorDetective;





    public Explorer(LinearOpMode linearOpMode){
        this.linearOpMode = linearOpMode;
        gyro = new Gyro(this);
        driveTrainController = new DriveTrainController(this);
        sortingAndKeep = new SortingAndKeep(this);
        colorDetective = new ColorDetective(this);

        driveTrainSensorsListener = new DriveTrainSensorsListener(this);
    }

    public void init(){
        BaseSensors.init(linearOpMode.hardwareMap);
        BaseMotors.init(linearOpMode.hardwareMap);

    }

    public void update(){
        driveTrainController.update();
    }

}
