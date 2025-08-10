import static com.qualcomm.hardware.ams.AMSColorSensor.AMS_TCS34725_ADDRESS;

import static java.lang.Math.round;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.adafruit.AdafruitI2cColorSensor;
import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDeviceWithParameters;

import java.lang.reflect.Field;

@Config

public class ColorDetective {

    AdafruitI2cColorSensor colorFieldSensor;
    AdafruitI2cColorSensor colorPuckDetectiveSensor;

     explorer;

    public int ourColor = 0;

    public int notOurColor = 0;

    public ColorDetective(Explorer robot) {
        explorer = robot;
        colorFieldSensor = fix(explorer.linearOpMode.hardwareMap.get(AdafruitI2cColorSensor.class, "fieldSensor"));
        colorPuckDetectiveSensor = fix(explorer.linearOpMode.hardwareMap.get(AdafruitI2cColorSensor.class, "puckSensor"));
    }

    public int puckDetect() {
        if (colorPuckDetectiveSensor.red() > colorPuckDetectiveSensor.green() || colorPuckDetectiveSensor.blue() > colorPuckDetectiveSensor.green()) {

            if (colorPuckDetectiveSensor.red() > colorPuckDetectiveSensor.blue())
                return 1;
            if (colorPuckDetectiveSensor.blue() > colorPuckDetectiveSensor.red())
                return 2;
        }
        return 0;
    }

   public int fieldDetect() {
        if (colorFieldSensor.red() < 350 && colorFieldSensor.blue() < 350 && colorFieldSensor.green() < 350 ) {
            if (colorFieldSensor.red() > colorFieldSensor.blue())
                return 1;
            if (colorFieldSensor.blue() > colorFieldSensor.red())
                return 2;
        }
        return 0;
    }

    public boolean statePuck = false;
    public double time = System.currentTimeMillis() / 1000.0;


    public boolean sorting() {
        double t = System.currentTimeMillis() / 1000.0;
        int color = puckDetect();
        int field = fieldDetect();
        if (statePuck) {
            statePuck = explorer.sortingAndKeep.separatorPosition(0);
            return false;
        }
        if (t - time > 1 && field == 0) {
            t = System.currentTimeMillis() / 1000.0;
            if (color == ourColor) {
                statePuck = explorer.sortingAndKeep.separatorPosition(1);
                time = t;
                return true;
            }
            if (color == notOurColor) {
                statePuck = explorer.sortingAndKeep.separatorPosition(-1);
                time = t;
                return true;
            }
        }
        statePuck = explorer.sortingAndKeep.separatorPosition(0);
        return false;

    }

    public boolean getOut() {
        int color = fieldDetect();
        if (color == 0 || color == notOurColor) {
            explorer.sortingAndKeep.closeServo();
            explorer.sortingAndKeep.separatorPosition(0);
            return false;
        }
        if (color == ourColor) {
            explorer.sortingAndKeep.separatorPosition(0);
            explorer.sortingAndKeep.openServo();
            return true;
        }
        return false;
    }


    public static AdafruitI2cColorSensor fix(AdafruitI2cColorSensor sensor) {
        try {
            AMSColorSensor.class.getDeclaredField("AMS_TCS34725_ID").setAccessible(true);

            AMSColorSensor.Parameters parameters = new AMSColorSensor.Parameters(AMS_TCS34725_ADDRESS, 0x4D);

            Field paramField = I2cDeviceSynchDeviceWithParameters.class.getDeclaredField("parameters");

            paramField.setAccessible(true);

            try {
                paramField.set(sensor, parameters);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            sensor.initialize();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("color sensor hack not successful");
        }

        return sensor;
    }

}
