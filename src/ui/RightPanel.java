package ui;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel
{
    public RightPanel(int floorNb, LogPanel logPanel)
    {
        super(new BorderLayout());
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(boxLayout);

        KeyboardPanel keyboardPanel = new KeyboardPanel(floorNb, logPanel);

        this.add(keyboardPanel);
        this.add(logPanel);
    }
}
