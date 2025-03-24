package decorator;

import devices.Light;
import java.util.Random;

public class MotionSensorDecorator extends DeviceDecorator {
    public MotionSensorDecorator(Light light) {
        super(light);
    }

    public void detectMotion() {
        Random random = new Random();
        boolean motionDetected = random.nextBoolean();

        if (motionDetected) {
            System.out.println("Motion detected! Alarm triggered!");
            turnOn();
        } else {
            System.out.println("No motion detected. Everything is calm.");
        }
    }
}
