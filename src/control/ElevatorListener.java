package control;

public class ElevatorListener extends Thread {
    private ElevatorSimulator elevatorSimulator;
    private ControlCommand controlCommand;

    ElevatorListener(ElevatorSimulator elevatorSimulator, ControlCommand controlCommand) {
        this.elevatorSimulator = elevatorSimulator;
        this.controlCommand = controlCommand;
    }

    @Override
    public void run() {
        for(;;) {
            int y;
            synchronized (elevatorSimulator) {
                y = elevatorSimulator.getY();
            }
            if (y % 100 == 0) {
                //System.out.println("Un étage a été franchis"); //todo: is working => on capte bien l'étage et ça renseigne bien controlCommand
                controlCommand.setCurrentFloor(y / 100);
            }
        }
    }
}