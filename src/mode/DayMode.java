package mode;

import devices.Light;
import devices.Thermostat;
import devices.DoorLock;

public class DayMode implements ModeStrategy {
    @Override
    public void applyMode(Light light, Thermostat thermostat, DoorLock doorLock) {
        light.turnOn();
        thermostat.setTemperature(22);
        doorLock.turnOff();
        System.out.println("Day Mode: Lights ON, Thermostat at 22°C, Doors UNLOCKED");
    }

    @Override
    public String getModeName() {
        return "Day Mode";
    }
}
