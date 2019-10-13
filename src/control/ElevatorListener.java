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
        int y = 0;
        for(;;) {
            synchronized (elevatorSimulator) {
                if (y != elevatorSimulator.getY()){
                    y = elevatorSimulator.getY();
                    //System.out.println(y);
                    elevatorViewPanel.ascenseur.setValue(y);
                    if ((y - 50) % 100 == 0) {
                        System.out.println("étage n"+ (y-50)/100 + "| y=" + y); //todo: is working => on capte bien l'étage et ça renseigne bien controlCommand
                        controlCommand.setCurrentFloor((y-50)/100);
                    }
                }
            }
        }
    }
}