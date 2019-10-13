package ui;

import javax.swing.*;
import java.awt.*;

public class ElevatorPanel extends JPanel
{
    public ElevatorPanel(int nbEtage, LogPanel logPanel)
    {
        super(new BorderLayout());
        this.setBackground(new Color(105,5,254));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(boxLayout);

        KeyboardPanel keyboardPanel = new KeyboardPanel(nbEtage, logPanel);

        this.add(keyboardPanel);
        this.add(logPanel);

    }
}
