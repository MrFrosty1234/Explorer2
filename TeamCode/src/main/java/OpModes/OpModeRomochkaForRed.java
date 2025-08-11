package OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import DriveTrain.States;
import Explorer.Explorer;

@TeleOp
public class OpModeRomochkaForRed extends LinearOpMode {

    Explorer explorer = null;
    public static double sepraratorMove;



    public void runOpMode() {


        explorer = new Explorer(this);


        waitForStart();

        boolean zalupa = false;

        explorer.driveTrainController.states = States.MOVE_TO_WALL;

        explorer.driveTrainController.ti.reset();

        while (opModeIsActive()) {

            explorer.colorDetective.ourColor = 1;
            explorer.colorDetective.notOurColor = 2;
            FtcDashboard.getInstance().getTelemetry().addData("color",explorer.colorDetective.puckDetect());
            FtcDashboard.getInstance().getTelemetry().addData("red", explorer.colorDetective.colorPuckDetectiveSensor.red());
            FtcDashboard.getInstance().getTelemetry().addData("blue", explorer.colorDetective.colorPuckDetectiveSensor.blue());
            FtcDashboard.getInstance().getTelemetry().addData("green", explorer.colorDetective.colorPuckDetectiveSensor.green());
            FtcDashboard.getInstance().getTelemetry().update();

            explorer.sortingAndKeep.brushOn();

             explorer.update();
        }
    }
}
