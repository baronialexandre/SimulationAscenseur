package ui.listeners;

import control.ControlCommand;
import ui.LogPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FloorCallListener implements ActionListener
{
    private static ControlCommand controlCommand;
    private LogPanel logPanel;
    private int floor;

    public FloorCallListener(int floor, LogPanel logPanel)
    {
        this.floor = floor;
        this.logPanel = logPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand()) {
            case "\\/":
                logPanel.addMessage("Appel à l'étage " + floor + " en descente \n");
                controlCommand.addCallDown(floor);
                break;
            case "/\\":
                logPanel.addMessage("Appel à l'étage " + floor + " en montée \n");
                controlCommand.addCallUp(floor);
                break;
            default:
                logPanel.addMessage("Appel de l'elevator vers l'étage " + floor +" \n");
                controlCommand.addCall(floor);
                break;
        }
    }

    public static void setControlCommand(ControlCommand newControlCommand)
    {
        controlCommand = newControlCommand;
    }
}
