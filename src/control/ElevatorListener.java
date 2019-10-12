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
        boolean debug = true;
        for(;;) {
            int y = elevatorSimulator.getY();
            if (y % 100 == 0) {
                if(debug) {
                    debug = false;
                    System.out.println("Un étage a été franchis");
                }
                controlCommand.setCurrentFloor(elevatorSimulator.getY() / 100);
            }
            else {
                debug = true;
            }
        }
    }
}