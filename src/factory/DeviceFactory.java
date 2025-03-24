package factory;

import devices.Device;
import devices.Light;
import devices.Thermostat;
import devices.DoorLock;


//umozliwia tworzenie urzadzen na podstawie ich typu
public class DeviceFactory {
    public static Device createDevice(String type) {
        switch (type) {
            case "Light": return new Light();
            case "Thermostat": return new Thermostat();
            case "DoorLock": return new DoorLock();
            default: throw new IllegalArgumentException("Unknown device type");
        }
    }
}
