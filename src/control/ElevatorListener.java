package control;

import ui.ElevatorPanel;

public class ElevatorListener extends Thread {
    private ElevatorSimulator elevatorSimulator;
    private ControlCommand controlCommand;
    private ElevatorPanel elevatorPanel;

    ElevatorListener(ElevatorSimulator elevatorSimulator, ControlCommand controlCommand, ElevatorPanel elevatorPanel) {
        this.elevatorSimulator = elevatorSimulator;
        this.controlCommand = controlCommand;
        this.elevatorPanel = elevatorPanel;
    }

    @Override
    public void run() {
        int y = 0;
        for(;;) {
            synchronized (elevatorSimulator) {
                if (y != elevatorSimulator.getY()){
                    y = elevatorSimulator.getY();
                    elevatorPanel.ascenseur.setValue(y);
                    if ((y - 50) % 100 == 0) {
                        controlCommand.setCurrentFloor((y-50)/100);
                    }
                }
            }
        }
    }
}