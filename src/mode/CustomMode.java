package mode;

import devices.Light;
import devices.Thermostat;
import devices.DoorLock;

public class CustomMode implements ModeStrategy {
    private int customTemperature;
    private boolean lightState;
    private boolean doorLocked;

    public CustomMode(int customTemperature, boolean lightState, boolean doorLocked) {
        this.customTemperature = customTemperature;
        this.lightState = lightState;
        this.doorLocked = doorLocked;
    }

    @Override
    public void applyMode(Light light, Thermostat thermostat, DoorLock doorLock) {
        if (lightState) {
            light.turnOn();
        } else {
            light.turnOff();
        }

        thermostat.setTemperature(customTemperature);

        if (doorLocked) {
            doorLock.turnOn();
        } else {
            doorLock.turnOff();
        }

        System.out.println("Custom Mode Activated: Light: " + (lightState ? "ON" : "OFF")
                + ", Temp: " + customTemperature + "°C, Door: " + (doorLocked ? "LOCKED" : "UNLOCKED"));
    }

    @Override
    public String getModeName() {
        return "Custom Mode";
    }
}
