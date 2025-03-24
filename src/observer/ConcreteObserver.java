package observer;

public class ConcreteObserver implements Observer {
    private String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String event) {
        System.out.println(name + " received security alert: " + event);
    }
}
