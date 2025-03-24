package command;

import devices.DoorLock;

public class LockDoorCommand implements Command {
    private DoorLock doorLock;

    public LockDoorCommand(DoorLock doorLock) {
        this.doorLock = doorLock;
    }

    @Override
    public void execute() {
        doorLock.turnOn();
    }


}
