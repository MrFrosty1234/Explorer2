package OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp

public class OpModeRomochkaBlue extends LinearOpMode {

    Explorer explorer = null;
    public static double sepraratorMove;



    public void runOpMode() {


        explorer = new Explorer(this);


        waitForStart();

        boolean zalupa = false;

        explorer.driveTrain.state = DriveTrain.State.MOVING_TO_WALL;

        explorer.driveTrain.ti.reset();

        while (opModeIsActive()) {

            explorer.colorDetective.ourColor = 2;
            explorer.colorDetective.notOurColor = 1;
            FtcDashboard.getInstance().getTelemetry().addData("color",explorer.colorDetective.puckDetect());
            FtcDashboard.getInstance().getTelemetry().addData("red", explorer.colorDetective.colorPuckDetectiveSensor.red());
            FtcDashboard.getInstance().getTelemetry().addData("blue", explorer.colorDetective.colorPuckDetectiveSensor.blue());
            FtcDashboard.getInstance().getTelemetry().addData("green", explorer.colorDetective.colorPuckDetectiveSensor.green());
            FtcDashboard.getInstance().getTelemetry().update();
            explorer.colorDetective.sorting();
            explorer.colorDetective.getOut();
            explorer.sortingAndKeep.brushOn();

            explorer.update();
        }
    }
}