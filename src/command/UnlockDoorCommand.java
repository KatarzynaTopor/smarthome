package command;

import devices.DoorLock;

public class UnlockDoorCommand implements Command {
    private DoorLock doorLock;

    public UnlockDoorCommand(DoorLock doorLock) {
        this.doorLock = doorLock;
    }

    @Override
    public void execute() { doorLock.turnOff(); }

}
