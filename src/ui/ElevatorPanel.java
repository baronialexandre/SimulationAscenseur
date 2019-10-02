package ui;

import javax.swing.*;
import java.awt.*;

public class ElevatorPanel extends JPanel
{
    public ElevatorPanel(int nbEtage)
    {
        super(new BorderLayout());
        this.setBackground(new Color(105,5,254));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);

        // Ajouter votre panel ici

        JPanel logPanel = new JPanel();

        this.add(logPanel);

    }
}
