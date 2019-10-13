package ui;

import ui.listeners.EmergencyActionListener;
import ui.listeners.FloorCallListener;
import ui.listeners.RestartActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static java.awt.Color.RED;

public class KeyboardPanel extends JPanel
{

    public KeyboardPanel(int nbEtage)
    {
        super();


        JPanel interiorLiftLabelPanel = new JPanel();
        JPanel outsidePanel= new JPanel();
        JPanel buttonsPanel = new JPanel();
        JPanel floor = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(480,17*nbEtage+60));
        outsidePanel.add(buttonsPanel);

        JLabel interiorLiftLabel = new JLabel("Interieur de l'ascenseur");
        interiorLiftLabelPanel.add(interiorLiftLabel);

        this.add(interiorLiftLabelPanel, BorderLayout.NORTH);
        this.add(outsidePanel,BorderLayout.SOUTH);

        Border blackBottomLine = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY);
        this.setBorder(blackBottomLine);

        GridLayout buttonContainer = new GridLayout(1, 3);
        GridLayout floorButtonContainer = new GridLayout(nbEtage/3+2, nbEtage/8+2);
        buttonsPanel.setLayout(buttonContainer);
        floor.setLayout(floorButtonContainer);

        String[] buttonArray = new String[nbEtage];
        JButton[] tab_button = new JButton[buttonArray.length];

        floor.setPreferredSize(new Dimension(10,10));
        for (int i = 0; i < nbEtage; i++)
        {
            JPanel buttonPanel = new JPanel();
            buttonArray[i] = "" + i;
            tab_button[i] = new JButton(buttonArray[i]);
            tab_button[i].addActionListener(new FloorCallListener(i));
            tab_button[i].setPreferredSize(new Dimension(50,40));
            buttonPanel.add(tab_button[i]);
            floor.add(buttonPanel);
        }
        buttonsPanel.add(floor);

        JPanel stopButtonPanel = new JPanel();
        JButton emergencyButton = new JButton("Arrêt d'Urgence");
        emergencyButton.setPreferredSize(new Dimension(130, 40));
        emergencyButton.setBackground(RED);
        emergencyButton.addActionListener(new EmergencyActionListener());
        buttonsPanel.add(stopButtonPanel);
        stopButtonPanel.add(emergencyButton);



        JPanel restartButtonPanel = new JPanel();
        JButton restartButton = new JButton("Redémarrer");
        restartButton.setPreferredSize(new Dimension(120, 40));
        restartButton.setBackground(Color.GREEN);
        restartButton.addActionListener(new RestartActionListener());

        buttonsPanel.add(restartButtonPanel);
        restartButtonPanel.add(restartButton);
    }
}
