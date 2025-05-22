import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogSensor;

@TeleOp
public class SonarTestPls extends LinearOpMode {
    AnalogInput sonar;
    
    public void runOpMode() throws InterruptedException {
        sonar = hardwareMap.get(AnalogInput.class, "sonar");
        waitForStart();
        while(opModeIsActive()){
            if(sonar.getVoltage() > 0.2)
                telemetry.addData("yes", 1);
            else
                telemetry.addData("no",1);
            telemetry.update();
        }
    }
}
