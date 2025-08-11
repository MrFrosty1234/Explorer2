package OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cAddr;

import DriveTrain.States;
import Explorer.Explorer;
import Utilities.I2cPort;


@TeleOp

public class OpModeRomochkaBlue extends LinearOpMode {

    Explorer explorer = null;
    public static double sepraratorMove;



    public void runOpMode() {


        explorer = new Explorer(this);


        I2cPort sonar = hardwareMap.get(I2cPort.class, "sonar");
        sonar.initWithI2cAddress(I2cAddr.create7bit(5));

        waitForStart();

        boolean zalupa = false;

        explorer.driveTrainController.states = States.MOVE_TO_WALL;

        explorer.driveTrainController.ti.reset();

        boolean  pipiska = true;
        boolean count = true;
        while (opModeIsActive()) {

          /*  explorer.colorDetective.ourColor = 2;
            explorer.colorDetective.notOurColor = 1;
            FtcDashboard.getInstance().getTelemetry().addData("DIST", explorer.driveTrainSensorsListener.getDist());
            FtcDashboard.getInstance().getTelemetry().addData("state", explorer.driveTrainController.states);
            FtcDashboard.getInstance().getTelemetry().addData("color",explorer.colorDetective.puckDetect());
            FtcDashboard.getInstance().getTelemetry().addData("red", explorer.colorDetective.colorPuckDetectiveSensor.red());
            FtcDashboard.getInstance().getTelemetry().addData("blue", explorer.colorDetective.colorPuckDetectiveSensor.blue());
            FtcDashboard.getInstance().getTelemetry().addData("green", explorer.colorDetective.colorPuckDetectiveSensor.green());
            FtcDashboard.getInstance().getTelemetry().update();
           // explorer.sortingAndUnloading.sorting();
            //explorer.sortingAndUnloading.getOut();
            //explorer.sortingAndKeep.brushOn();

            explorer.update();

           */

        //    explorer.driveTrainController.moveWithTimers(1, -1);

            sonar.write((byte) 5);
            byte data  = sonar.read();

            // explorer.colorDetective.sorting();
          //  explorer.colorDetective.getOut();

            FtcDashboard.getInstance().getTelemetry().addData("dist",data);

            FtcDashboard.getInstance().getTelemetry().addData("red", explorer.colorDetective.colorPuckDetectiveSensor.red());
            FtcDashboard.getInstance().getTelemetry().addData("blue", explorer.colorDetective.colorPuckDetectiveSensor.blue());
            FtcDashboard.getInstance().getTelemetry().addData("green", explorer.colorDetective.colorPuckDetectiveSensor.green());


            FtcDashboard.getInstance().getTelemetry().addData("puck",explorer.colorDetective.puckDetect());
            FtcDashboard.getInstance().getTelemetry().addData("field",explorer.colorDetective.fieldDetect());
            FtcDashboard.getInstance().getTelemetry().addData("state", explorer.driveTrainController.states);
            FtcDashboard.getInstance().getTelemetry().addData("gyro", explorer.driveTrainSensorsListener.getAngle());
            FtcDashboard.getInstance().getTelemetry().update();


             explorer.update();
        }
    }
}