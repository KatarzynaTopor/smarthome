package command;


//uruchamia polecenia
public class Invoker {

    //parametryzujemy zadania
    private Command command;

    public void setCommand(Command command) { this.command = command; }

    public void pressButton() { command.execute(); }
}
