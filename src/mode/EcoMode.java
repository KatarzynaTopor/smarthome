package mode;

import devices.Light;
import devices.Thermostat;
import devices.DoorLock;

public class EcoMode implements ModeStrategy {
    @Override
    public void applyMode(Light light, Thermostat thermostat, DoorLock doorLock) {
        light.turnOff();
        thermostat.setTemperature(20);
        System.out.println("Eco Mode: Lights OFF, Thermostat at 20°C");
    }

    @Override
    public String getModeName() {
        return "Eco Mode";
    }
}
