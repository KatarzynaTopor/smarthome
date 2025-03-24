package devices;

public class Thermostat implements Device {
    private int temperature = 22;

    @Override
    public void turnOn() { System.out.println("Thermostat is ON"); }
    @Override
    public void turnOff() { System.out.println("Thermostat is OFF"); }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        System.out.println("Temperature set to: " + temperature + "°C");
    }

    @Override
    public String getStatus() {
        return "Temperature: " + temperature + "°C";
    }
}
