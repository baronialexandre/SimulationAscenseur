package ui;

import javax.swing.*;
import java.awt.*;

public class KeyboardPanel extends JPanel{

    public KeyboardPanel(int nbEtage) {


        super(new BorderLayout());
        this.setBackground(new Color(105, 5, 254));

        GridLayout buttonContainer = new GridLayout(1, 3);
        buttonContainer.setHgap(5); //Cinq pixels d'espace entre les colonnes (H comme Horizontal)
        buttonContainer.setVgap(5);


        GridLayout floorButtonContainer = new GridLayout(2, 3);

        this.setLayout(buttonContainer);

        String[] buttonArray = new String[nbEtage];
        JButton[] tab_button = new JButton[buttonArray.length];
        JPanel floor = new JPanel();
        floor.setLayout(floorButtonContainer);


        for (int i = 0; i < nbEtage; i++) {
            buttonArray[i] = "" + i;
            tab_button[i] = new JButton(buttonArray[i]);
            floor.add(tab_button[i]);
        }
        this.add(floor);


        JButton emergencyStop = new JButton("Stop");
        emergencyStop.setSize(12, 12);

        this.add(emergencyStop);

        JButton emergencyKill = new JButton("X");

        this.add(emergencyKill);
    }
}
