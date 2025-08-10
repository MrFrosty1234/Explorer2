package DriveTrain;

import static java.lang.Math.abs;
import static java.lang.Math.random;
import static java.lang.Math.signum;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import DriveTrain.Electronics.BaseMotors;
import DriveTrain.Electronics.BaseSensors;
import Explorer.Explorer;
import Utilities.PID;

@Config

public class DriveTrainController {
    public DcMotor motorR;
    public DcMotor motorL;

    PID pid;

    PID pidMove;

    ElapsedTime ti;

    DriveTrainListener driveTrainListener;

    Explorer explorer;

    public static double kPXAngle = 0.01;
    public static double kDXAngle = 0;
    public static double kIXAngle = 0;

    public static double kPX = 0.5;
    public static double kPD = 0;
    public static double kPI = 0;

    double stableAngle = 0;

    States states;

    public DriveTrainController(Explorer explorer){
        this.explorer = explorer;

        BaseMotors.init(explorer.linearOpMode.hardwareMap);
        BaseSensors.init(explorer.linearOpMode.hardwareMap);

        motorL = BaseMotors.leftMotor;

        motorR = BaseMotors.rightMotor;

        driveTrainListener = new DriveTrainListener(explorer);

        pid = new PID(kPXAngle, kDXAngle, kIXAngle);

        pidMove = new PID(kPX, kPD, kPI);
    }

    public boolean turn(double angle){

        if(ti.seconds() < 5 && driveTrainListener.getAngle() < angle){

            double err = angle - driveTrainListener.getAngle();

            double power =  pid.update(err);

            if(abs(power) > 1)
                power = 1 * signum(power);
            motorR.setPower(power);
            motorL.setPower(power);
            FtcDashboard.getInstance().getTelemetry().addData("powa", power);
            FtcDashboard.getInstance().getTelemetry().addData("gyro", explorer.gyro.getAngle());
            FtcDashboard.getInstance().getTelemetry().update();
            return false;

        }
        BaseMotors.stop();
        ti.reset();

        states = States.MOVE_TO_WALL;
        stableAngle = driveTrainListener.getAngle();
        return true;
    }

    public boolean moveToWall() {

        if (!driveTrainListener.getButtonsValue() && ti.seconds() < 3) {
            double err = stableAngle - driveTrainListener.getAngle();

            double power = pidMove.update(err);

            motorR.setPower(power);
            motorL.setPower(power);
            return false;
        }
        BaseMotors.stop();
        ti.reset();
        states = States.MOVE_WIH_TIMER;
        stableAngle = driveTrainListener.getAngle();
        return true;
    }

    public boolean moveWithTimers(double time){

        if(ti.seconds() < time){
            double err = stableAngle - driveTrainListener.getAngle();

            double power = pidMove.update(err);

            motorR.setPower(power);
            motorL.setPower(power);
            return false;
        }
        BaseMotors.stop();
        ti.reset();
        states = States.TURN;
        stableAngle = driveTrainListener.getAngle();
        return true;

    }

    public void update(){
        switch (states){
            case TURN:
                double angle = random();
                if(abs(angle) < 60)
                    angle = 60;
                if(abs(angle) > 100)
                    angle = 100;
                turn(angle);
            case MOVE_TO_WALL:
                moveToWall();
            case MOVE_WIH_TIMER:
                moveWithTimers(1);
        }
    }

}
