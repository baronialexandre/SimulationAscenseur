package ui.listeners;

import control.ControlCommand;
import ui.LogPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartActionListener implements ActionListener
{
    private static ControlCommand controlCommand;
    private final LogPanel logPanel;

    public RestartActionListener(LogPanel logPanel){
        this.logPanel = logPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        logPanel.logTextArea.append("Redémarrage \n");
        controlCommand.restart();
    }

    public static void setControlCommand(ControlCommand newControlCommand)
    {
        controlCommand = newControlCommand;
    }
}
