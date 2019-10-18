package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ElevatorPanel extends JPanel {
    public JSlider ascenseur;

    public ElevatorPanel(int nbEtage) {
        super(new BorderLayout());
        ascenseur = new JSlider();
        ascenseur.setOrientation(SwingConstants.VERTICAL);
        ascenseur.setMinimum(0);
        ascenseur.setMaximum(nbEtage*100);
        ascenseur.setValue(50);
        ascenseur.setEnabled(false);
        //ascenseur.setMinorTickSpacing(50);
        //ascenseur.setMajorTickSpacing(100);
        ascenseur.setPaintTicks(true);
        /*
        ascenseur.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println(ascenseur.getValue());
            }
        });*/

        this.add(ascenseur);
        //this.setSize(100,720);
    }
}
