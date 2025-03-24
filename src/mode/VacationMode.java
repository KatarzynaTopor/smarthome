package mode;

import devices.Light;
import devices.Thermostat;
import devices.DoorLock;

public class VacationMode implements ModeStrategy {
    @Override
    public void applyMode(Light light, Thermostat thermostat, DoorLock doorLock) {
        light.turnOff();
        thermostat.turnOff();
        doorLock.turnOn();
        System.out.println("Vacation Mode: Lights OFF, Thermostat OFF, Doors LOCKED");
    }

    @Override
    public String getModeName() {
        return "Vacation Mode";
    }
}
