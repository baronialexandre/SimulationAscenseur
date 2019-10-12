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
        switch (e.getActionCommand()) {
            case "\\/":
                System.out.println("Appel à l'étage " + floor + "en descente");
                controlCommand.addCallDown(floor);
                break;
            case "/\\":
                System.out.println("Appel à l'étage " + floor + "en montée");
                controlCommand.addCallUp(floor);
                break;
            default:
                System.out.println("Appel à l'étage " + floor);
                controlCommand.addCall(floor);
                break;
        }
    }

    public static void setControlCommand(ControlCommand newControlCommand)
    {
        controlCommand = newControlCommand;
    }
}
