package composite;

import devices.Device;

//pojedyncze urzadzenie
public class Leaf extends Component {
    private String name;
    private Device device;

    public Leaf(String name, Device device) {
        this.name = name;
        this.device = device;
    }

    @Override
    public void operate() {
        device.turnOn();
        System.out.println(name + " turned ON");
    }

    @Override
    public String getName() {
        return name;
    }

    public Device getDevice() {
        return device;
    }
}
