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

    public ElapsedTime ti = new ElapsedTime();

    DriveTrainSensorsListener driveTrainSensorsListener;

    Explorer explorer;

    public static double kPXAngle = 0.005;
    public static double kDXAngle = 0;
    public static double kIXAngle = 0;

    public static double kPX = 0.5;
    public static double kPD = 0;
    public static double kPI = 0;

    double stableAngle = 0;

    public States states;

    public DriveTrainController(Explorer explorer){
        this.explorer = explorer;

        BaseMotors.init(explorer.linearOpMode.hardwareMap);
        BaseSensors.init(explorer.linearOpMode.hardwareMap);

        motorR = BaseMotors.rightMotor;
        motorL = BaseMotors.leftMotor;

        driveTrainSensorsListener = new DriveTrainSensorsListener(explorer);

        pid = new PID(kPXAngle, kDXAngle, kIXAngle);

        pidMove = new PID(kPX, kPD, kPI);


    }

    public boolean turn(double angle){

        double err = angle - driveTrainSensorsListener.getAngle();

        if(ti.seconds() < 2 && abs(err) > 5){

            err = angle - driveTrainSensorsListener.getAngle();

            double power =  pid.update(err);

            if(abs(power) > 0.8)
                power = 0.8 * signum(power);
            motorR.setPower(-power);
            motorL.setPower(power);
            FtcDashboard.getInstance().getTelemetry().addData("powa", power);
            FtcDashboard.getInstance().getTelemetry().addData("gyro", explorer.gyro.getAngle());
            FtcDashboard.getInstance().getTelemetry().update();
            return false;

        }
        BaseMotors.stop();
        states = States.MOVE_TO_WALL;
        ti.reset();


        return true;
    }

    public boolean moveToWall() {

        if(driveTrainSensorsListener.rightBut.getState() && driveTrainSensorsListener.leftBut.getState() && ti.seconds() < 3 ) {
            motorR.setPower(1);
            motorL.setPower(1);
            return false;
        }
        BaseMotors.stop();
        ti.reset();
        states = States.MOVE_WIH_TIMER;
        return true;
    }

    public boolean moveWithTimers(double time, double orientation){

        if(ti.seconds() < time){

            motorR.setPower(0.5 * signum(orientation));
            motorL.setPower(0.5 * signum(orientation));
            FtcDashboard.getInstance().getTelemetry().addData("ti", ti.seconds());
            FtcDashboard.getInstance().getTelemetry().update();

            return false;
        }
        BaseMotors.stop();
        ti.reset();
        states = States.TURN;
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
                turn(abs(angle));

            case MOVE_TO_WALL:
                moveToWall();
            case MOVE_WIH_TIMER:
                moveWithTimers(1, -1);
        }
    }

}
