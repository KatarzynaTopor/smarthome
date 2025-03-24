package devices;

public class DoorLock implements Device {
    private boolean isLocked = true;

    @Override
    public void turnOn() { isLocked = true; System.out.println("Door is LOCKED"); }
    @Override
    public void turnOff() { isLocked = false; System.out.println("Door is UNLOCKED"); }
    @Override
    public String getStatus() { return isLocked ? "LOCKED" : "UNLOCKED"; }
}
