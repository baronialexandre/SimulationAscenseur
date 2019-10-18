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
                    //System.out.println(y);
                    elevatorPanel.ascenseur.setValue(y);
                    if ((y - 50) % 100 == 0) {
                        System.out.println("LIS étage n"+ (y-50)/100 + "| y=" + y); //todo: is working => on capte bien l'étage et ça renseigne bien controlCommand
                        controlCommand.setCurrentFloor((y-50)/100);
                    }
                }
            }
        }
    }
}