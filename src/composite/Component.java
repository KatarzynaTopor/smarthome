package composite;

public abstract class Component {
    public void add(Component component) { throw new UnsupportedOperationException(); }
    public void remove(Component component) { throw new UnsupportedOperationException(); }
    public void operate() { throw new UnsupportedOperationException(); }
    public String getName() { throw new UnsupportedOperationException(); }
}
