package ui.listeners;

import control.ControlCommand;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FloorCallListener implements ActionListener
{
    private static ControlCommand controlCommand;
    private int floor;

    public FloorCallListener(int floor)
    {
        this.floor = floor;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand() == "\\/") {
            System.out.println("Appel à l'étage " + floor + "en descente");
            controlCommand.addCallDown(floor);
        }
        else if(e.getActionCommand() == "/\\") {
            System.out.println("Appel à l'étage " + floor + "en montée");
            controlCommand.addCallUp(floor);
        } else {
            System.out.println("Appel à l'étage " + floor);
            controlCommand.addCall(floor);
        }
    }

    public static void setControlCommand(ControlCommand newControlCommand)
    {
        controlCommand = newControlCommand;
    }
}
