package ui.listeners;

import control.ControlCommand;
import ui.LogPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmergencyActionListener implements ActionListener
{
    private static ControlCommand controlCommand;
    private LogPanel logPanel;

    public EmergencyActionListener(LogPanel logPanel){
        this.logPanel = logPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        logPanel.logTextArea.append("Arrêt d'Urgence \n");
        controlCommand.emergency();
    }

    public static void setControlCommand(ControlCommand newControlCommand)
    {
        controlCommand = newControlCommand;
    }
}
