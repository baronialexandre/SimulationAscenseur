package ui;

import javax.swing.*;
import java.awt.*;

public class ElevatorPanel extends JPanel {
    public JSlider elevator;

    public ElevatorPanel(int nbEtage) {
        super(new BorderLayout());
        elevator = new JSlider();
        elevator.setOrientation(SwingConstants.VERTICAL);
        elevator.setMinimum(0);
        elevator.setMaximum(nbEtage*100);
        elevator.setValue(50);
        elevator.setEnabled(false);
        elevator.setForeground(new Color(255, 255, 255));
        elevator.setBackground(new Color(45, 45, 45));
        this.add(elevator);
    }
}
