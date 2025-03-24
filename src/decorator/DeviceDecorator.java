package decorator;

import devices.Device;

public abstract class DeviceDecorator implements Device {
    protected Device device;

    public DeviceDecorator(Device device) {
        this.device = device;
    }

    @Override
    public void turnOn() { device.turnOn(); }

    @Override
    public void turnOff() { device.turnOff(); }

    @Override
    public String getStatus() { return device.getStatus(); }
}
