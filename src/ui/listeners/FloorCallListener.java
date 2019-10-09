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
        System.out.println("Appel à l'étage " + floor);
        controlCommand.addCall(floor);
    }

    public static void setControlCommand(ControlCommand newControlCommand)
    {
        controlCommand = newControlCommand;
    }
}
