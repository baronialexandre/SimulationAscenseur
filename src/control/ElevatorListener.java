package control;

import ui.ElevatorViewPanel;

public class ElevatorListener extends Thread {
    private ElevatorSimulator elevatorSimulator;
    private ControlCommand controlCommand;
    private ElevatorViewPanel elevatorViewPanel;

    ElevatorListener(ElevatorSimulator elevatorSimulator, ControlCommand controlCommand, ElevatorViewPanel elevatorViewPanel) {
        this.elevatorSimulator = elevatorSimulator;
        this.controlCommand = controlCommand;
        this.elevatorViewPanel = elevatorViewPanel;
    }

    @Override
    public void run() {
        for(;;) {
            int y = 0;
            synchronized (elevatorSimulator) {
                if (y != elevatorSimulator.getY()){
                    y = elevatorSimulator.getY();
                    elevatorViewPanel.ascenseur.setValue(y);
                }
            }
            if (y % 100 == 0) {
                //System.out.println("Un étage a été franchis"); //todo: is working => on capte bien l'étage et ça renseigne bien controlCommand
                controlCommand.setCurrentFloor(y / 100);
            }
        }
    }
}