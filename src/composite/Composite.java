package composite;

import java.util.ArrayList;
import java.util.List;

//dla poszczegolnych pokoi
public class Composite extends Component {
    private String name;
    private List<Component> components = new ArrayList<>();

    public Composite(String name) {
        this.name = name;
    }

    @Override
    public void add(Component component) {
        components.add(component);
    }

    @Override
    public void remove(Component component) {
        components.remove(component);
    }

    @Override
    public void operate() {
        System.out.println("Operating in room: " + name);
        for (Component component : components) {
            component.operate();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Component> getComponents() {
        return components;
    }
}
