import static java.lang.Math.abs;
import static java.lang.Math.random;
import static java.lang.Math.signum;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config

public class DriveTrain {
    PID pid;

    PID pidMove;

    public static double kPXAngle = 0.01;
    public static double kDXAngle = 0;
    public static double kIXAngle = 0;

    public static double kPX = 0.5;
    public static double kPD = 0;
    public static double kPI = 0;

    public static double v = 1;

    public int sum = 0;
    public static int countOfTurn = 0;

    DcMotor leftMotor;
    DcMotor rightMotor;

    Explorer explorer;

    TouchSensor buttonLeft;
    TouchSensor buttonRight;
    AnalogInput sonar;

    State state = State.MOVING_TO_WALL;
    ElapsedTime ti;


    public DriveTrain(Explorer robot) {

        explorer = robot;

        pid = new PID(kPXAngle, kDXAngle, kIXAngle);

        pidMove = new PID(kPX, kPD, kPI);

        leftMotor = robot.linearOpMode.hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = robot.linearOpMode.hardwareMap.get(DcMotor.class, "rightMotor");

        buttonLeft = robot.linearOpMode.hardwareMap.get(TouchSensor.class, "buttonLeft");
        buttonRight = robot.linearOpMode.hardwareMap.get(TouchSensor.class, "buttonRight");

        sonar = robot.linearOpMode.hardwareMap.get(AnalogInput.class, "sonar");

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        ti = new ElapsedTime();

    }

    public void reset() {
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void stop() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }


    public boolean move(double time, double orientation) {
        double err = 0;


        if (ti.seconds() < time) {
            err = ti.seconds();

            double power = pidMove.update(err);

            if (abs(power) > 0.9)
                power = 0.9 * signum(power);

            leftMotor.setPower(power * orientation);
            rightMotor.setPower(power * orientation);

            FtcDashboard.getInstance().getTelemetry().addData("powa", power);
            FtcDashboard.getInstance().getTelemetry().addData("ERR", err);

            FtcDashboard.getInstance().getTelemetry().addData("ti", ti);
            FtcDashboard.getInstance().getTelemetry().addData("time", time);
            FtcDashboard.getInstance().getTelemetry().update();
            return true;
        }

        stop();
        state = State.TURNING;
        ti.reset();
        return false;
    }


    public boolean moveToWall() {

        if (!buttonLeft.isPressed() && (5 - ti.seconds()) > 0) {

            FtcDashboard.getInstance().getTelemetry().addData("time", ti.seconds());
            FtcDashboard.getInstance().getTelemetry().addData("button", buttonLeft.getValue());
            FtcDashboard.getInstance().getTelemetry().update();

            leftMotor.setPower(1);
            rightMotor.setPower(1);
            return true;
        }
        stop();
        ti.reset();
        state = State.MOVING_WITH_ENCODERS;
        return false;
    }


    public boolean turn(double angle) {


        double errZ = angle - explorer.gyro.getAngle();

        if (abs(errZ) > 5 && (1 - ti.seconds()) > 0) {
            //errZ = signum(angle)*(abs(angle) - abs(explorer.gyro.getAngle()));
            errZ = angle - explorer.gyro.getAngle();
            double power = pid.update(errZ);

            leftMotor.setPower(0.6*signum(angle));
            rightMotor.setPower(-0.6*signum(angle));
            FtcDashboard.getInstance().getTelemetry().addData("powa", power);
            FtcDashboard.getInstance().getTelemetry().addData("gyro", explorer.gyro.getAngle());
            FtcDashboard.getInstance().getTelemetry().update();
            return true;
        }
        stop();
        explorer.gyro.reset();
        ti.reset();
        state = State.MOVING_TO_WALL;
        return false;
    }

    public boolean turnRightWheel(double angle) {
        double errZ = angle - explorer.gyro.getAngle();
        errZ = angle - explorer.gyro.getAngle();

        double power = pid.update(errZ);


        if (abs(errZ) > 5) {
            power = 0.9 * signum(power);


            leftMotor.setPower(power);
            FtcDashboard.getInstance().getTelemetry().addData("powa", power);
            FtcDashboard.getInstance().getTelemetry().update();
            return true;
        }
        explorer.gyro.reset();
        ti.reset();
        state = State.MOVING_TO_WALL;
        return false;
    }

    public boolean moveWithSonar() {
        if (sonar.getVoltage() < 0.3 && (5 - ti.seconds()) > 0) {

            FtcDashboard.getInstance().getTelemetry().addData("time", ti.seconds());
            FtcDashboard.getInstance().getTelemetry().addData("soanr voltage", sonar.getVoltage());
            FtcDashboard.getInstance().getTelemetry().update();

            leftMotor.setPower(1);
            rightMotor.setPower(1);
            return true;
        }
        stop();
        ti.reset();
        state = State.TURNING;
        return false;
    }

    public boolean backWithSonar() {
        if (sonar.getVoltage() > 0.3 && (5 - ti.seconds()) > 0) {

            FtcDashboard.getInstance().getTelemetry().addData("time", ti.seconds());
            FtcDashboard.getInstance().getTelemetry().addData("soanr voltage", sonar.getVoltage());
            FtcDashboard.getInstance().getTelemetry().update();

            leftMotor.setPower(-1);
            rightMotor.setPower(-1);
            return true;
        }
        stop();
        ti.reset();
        state = State.TURNING;
        return false;
    }


    public enum State {
        MOVING_WITH_ENCODERS, OFF, MOVING_TO_WALL, TURNING, MOVE_WITH_SONAR
    }

    boolean count = false;

    public void update() {
        switch (state) {
            case OFF:
                stop();
                break;
            case MOVING_TO_WALL:
                moveToWall();
                break;
            case MOVING_WITH_ENCODERS:
                move(0.6, -1);
                break;
            case TURNING:
                double angle = 90;
                if (count)
                    angle = 90;
                if (!count)
                    angle = 150;
                count = !count;
                turn(angle);
                break;
        }
    }
}
