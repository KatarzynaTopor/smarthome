package decorator;

import devices.Light;

public class DimmableLightDecorator extends DeviceDecorator {
    private int brightness;

    public DimmableLightDecorator(Light light) {
        super(light);
        this.brightness = 100; // Domyślna jasność 100%
    }

    public void dim(int brightness) {
        if (brightness < 0 || brightness > 100) {
            System.out.println("Invalid brightness level. Please set a value between 0 and 100.");
            return;
        }
        this.brightness = brightness;
        System.out.println("Light dimmed to " + brightness + "%");
    }

    public int getBrightness() {
        return brightness;
    }
}
