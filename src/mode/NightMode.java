package mode;

import devices.Light;
import devices.Thermostat;
import devices.DoorLock;

public class NightMode implements ModeStrategy {
    @Override
    public void applyMode(Light light, Thermostat thermostat, DoorLock doorLock) {
        light.turnOff();
        thermostat.setTemperature(18);
        doorLock.turnOn();
        System.out.println("Night Mode: Lights OFF, Thermostat at 18°C, Doors LOCKED");
    }

    @Override
    public String getModeName() {
        return "Night Mode";
    }
}
