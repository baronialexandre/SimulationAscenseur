package ui;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel
{
    TextArea logTextArea;

    public LogPanel()
    {
        JLabel title = new JLabel("LOGS");
        this.add(title);

        logTextArea = new TextArea("" ,10,40, TextArea.SCROLLBARS_NONE);
        logTextArea.setEditable(false);
        logTextArea.setBackground(new Color(255,255,255));
        this.add(logTextArea);
    }

    public void addMessage(String message)
    {
        logTextArea.append(">> " + message);
    }
}
