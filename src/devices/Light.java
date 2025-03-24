package devices;

public class Light implements Device {
    private boolean isOn = false;

    @Override
    public void turnOn() { isOn = true; System.out.println("Light is ON"); }
    @Override
    public void turnOff() { isOn = false; System.out.println("Light is OFF"); }
    @Override
    public String getStatus() { return isOn ? "ON" : "OFF"; }
}
