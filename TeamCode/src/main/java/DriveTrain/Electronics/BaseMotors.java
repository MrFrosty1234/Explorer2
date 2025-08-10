package DriveTrain.Electronics;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config

public class BaseMotors {
    public static DcMotor rightMotor;
    public static DcMotor leftMotor;

    public static void init(HardwareMap hardwareMap){
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");

        reset();
    }

    public static void reset() {
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public static void stop(){
        rightMotor.setPower(0);
        leftMotor.setPower(0);
    }


}



