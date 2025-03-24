package singleton;

import devices.Device;
import java.util.HashMap;
import java.util.Map;

//zarzadaamy pojedynczym zbiorem urzadzen
public class DeviceManager {
    private static DeviceManager instance;
    private Map<String, Device> devices = new HashMap<>();

    private DeviceManager() {}

    public static DeviceManager getInstance() {
        if (instance == null) instance = new DeviceManager();
        return instance;
    }

    public void addDevice(String name, Device device) {
        devices.put(name, device);
    }

    public Device getDevice(String name) {
        return devices.get(name);
    }
}
