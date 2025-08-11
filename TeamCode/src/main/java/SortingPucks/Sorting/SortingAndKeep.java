package SortingPucks.Sorting;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import Explorer.Explorer;
import Utilities.PID;

@Config

public class SortingAndKeep {

    DcMotor separatorMotor;
    Servo outServo;
    DcMotor brush;

    public static double closeServoPosition = 0.515;
    public static double openServoPosition = 0.44;

    public static double oneSeparatorMove = 220;


    PID pid;

    public static double kPX = 0.0025   ;
    public static double kDX = 0;
    public static double kIX = 0;

    Explorer explorer;

    public SortingAndKeep(Explorer robot) {

        explorer = robot;

        separatorMotor = explorer.linearOpMode.hardwareMap.get(DcMotor.class, "sepMotor");
        outServo = explorer.linearOpMode.hardwareMap.get(Servo.class, "outServo");
        brush = explorer.linearOpMode.hardwareMap.get(DcMotor.class, "brush");

        separatorMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        brush.setDirection(DcMotorSimple.Direction.FORWARD);
        reset();

        pid = new PID(kPX, kDX, kIX);

    }

    public void reset() {
        separatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        separatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public boolean openServo() {
        outServo.setPosition(openServoPosition);
        return true;
    }

    public boolean closeServo() {
        outServo.setPosition(closeServoPosition);
        return true;
    }

    public boolean brushOn(){
        brush.setPower(1);
        return true;
    }

    public boolean bruhsOff(){
        brush.setPower(0);
        return false;
    }


    public double position = 0;
    public double time = System.currentTimeMillis() / 1000.0;
    double err = 0;
    ElapsedTime t = new ElapsedTime();


    public boolean separatorPosition(double orientation) {
        position += oneSeparatorMove * orientation;

        err = position - separatorMotor.getCurrentPosition();


        if (abs(err) > 5 && t.seconds() < 1) {
            err = position - separatorMotor.getCurrentPosition();
            double power = pid.update(err);
            if (abs(power) > 0.3)
                power = 0.3 * signum(power);
            FtcDashboard.getInstance().getTelemetry().addData("powa", power);
            FtcDashboard.getInstance().getTelemetry().update();
            separatorMotor.setPower(power);
            return true;
        }
        separatorMotor.setPower(0);
        t.reset();
        return false;
    }
}


