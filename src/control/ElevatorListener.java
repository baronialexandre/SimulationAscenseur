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
        int y = elevatorSimulator.getY();
        if(y%100 == 0) {
            System.out.println("Un étage a été franchis");
            controlCommand.setCurrentFloor(elevatorSimulator.getY()/100);
        }
    }
}