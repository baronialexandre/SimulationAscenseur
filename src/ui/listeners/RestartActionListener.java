package ui.listeners;

import control.ControlCommand;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartActionListener implements ActionListener
{
    private static ControlCommand controlCommand;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Redémarrage");
        controlCommand.restart();
    }

    public static void setControlCommand(ControlCommand newControlCommand)
    {
        controlCommand = newControlCommand;
    }
}
