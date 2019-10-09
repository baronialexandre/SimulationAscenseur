package ui.listeners;

import control.ControlCommand;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmergencyActionListener implements ActionListener
{
    private static ControlCommand controlCommand;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Arrêt d'Urgence");
        controlCommand.emergency();
    }

    public static void setControlCommand(ControlCommand newControlCommand)
    {
        controlCommand = newControlCommand;
    }
}
