package ui;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel
{
    public TextArea logTextArea;

    LogPanel()
    {
        JPanel outsidePanel = new JPanel();
        JPanel labelPanel = new JPanel();
        JPanel logAreaPanel = new JPanel();
        JLabel title = new JLabel("AFFICHAGE DES LOGS");
        labelPanel.add(title);
        outsidePanel.add(labelPanel,BorderLayout.NORTH);
        outsidePanel.setPreferredSize(new Dimension(400,240));

        logTextArea = new TextArea("" ,10,40, TextArea.SCROLLBARS_NONE);

        logTextArea.setEditable(false);
        logTextArea.setBackground(new Color(255,255,255));
        logTextArea.setForeground(Color.GREEN);
        logTextArea.setBackground(Color.DARK_GRAY);
        logAreaPanel.add(logTextArea);
        outsidePanel.add(logAreaPanel,BorderLayout.SOUTH);
        this.add(outsidePanel);
    }

    public void addMessage(String message)
    {
        logTextArea.append(">> " + message);
    }
}
