package mode;

import devices.Light;
import devices.Thermostat;
import devices.DoorLock;

public interface ModeStrategy {
    void applyMode(Light light, Thermostat thermostat, DoorLock doorLock);
    String getModeName();
}
