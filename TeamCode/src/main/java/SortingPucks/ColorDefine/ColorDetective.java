package SortingPucks.ColorDefine;

import static com.qualcomm.hardware.ams.AMSColorSensor.AMS_TCS34725_ADDRESS;

import static java.lang.Math.round;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.adafruit.AdafruitI2cColorSensor;
import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDeviceWithParameters;

import java.lang.reflect.Field;

import Explorer.Explorer;

@Config

public class ColorDetective {

    public AdafruitI2cColorSensor colorFieldSensor;
    public AdafruitI2cColorSensor colorPuckDetectiveSensor;

    Explorer explorer;

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
