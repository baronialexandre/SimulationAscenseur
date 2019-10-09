package ui;

import control.ControlCommand;
import ui.listeners.EmergencyActionListener;
import ui.listeners.FloorCallListener;
import ui.listeners.RestartActionListener;

import javax.swing.*;
import java.awt.*;

public class KeyboardPanel extends JPanel
{
    public ControlCommand controlCommand;

    public KeyboardPanel(int nbEtage)
    {
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

        for (int i = 0; i < nbEtage; i++)
        {
            buttonArray[i] = "" + i;
            tab_button[i] = new JButton(buttonArray[i]);
            tab_button[i].addActionListener(new FloorCallListener(i));
            floor.add(tab_button[i]);
        }
        this.add(floor);


        JButton emergencyButton = new JButton("Arrêt d'Urgence");
        emergencyButton.setSize(12, 12);
        emergencyButton.addActionListener(new EmergencyActionListener());
        this.add(emergencyButton);

        JButton restartButton = new JButton("Redémarrer");
        restartButton.addActionListener(new RestartActionListener());

        this.add(restartButton);
    }
}
