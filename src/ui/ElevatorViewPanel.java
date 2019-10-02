package ui;

import javax.swing.*;
import java.awt.*;

public class ElevatorViewPanel extends JPanel {
    public ElevatorViewPanel(Dimension size) {
        super(new BorderLayout());
        JScrollBar ascenseur = new JScrollBar();
        ascenseur.setSize(200,200);
        ascenseur.setBackground(new Color(102,102,102));

        //ascenseur.setLocation(0,0);
        this.add(ascenseur);
        this.setSize(100,720);
        this.setBackground(new Color(159,190, 91));
    }
}
