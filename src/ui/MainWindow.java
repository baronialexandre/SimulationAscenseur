package ui;

import control.ControlCommand;
import ui.listeners.EmergencyActionListener;
import ui.listeners.FloorCallListener;
import ui.listeners.RestartActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame
{
    private MainWindow(int nbEtage)
    {
        super("Simulateur Ascenseur");

        JPanel mainPane = new JPanel(new GridLayout(1,2));

        LogPanel logPanel = new LogPanel();

        JPanel leftPane = new BuildingPanel(nbEtage, logPanel);
        mainPane.add(leftPane);

        JPanel rightPane = new ElevatorPanel(nbEtage, logPanel);
        mainPane.add(rightPane);

        //this.setSize(820,460);
        this.setSize(1280,720);
        this.setContentPane(mainPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        ControlCommand controlCommand = new ControlCommand(nbEtage, (ElevatorViewPanel)((BuildingPanel) leftPane).elevatorView);

        FloorCallListener.setControlCommand(controlCommand);
        EmergencyActionListener.setControlCommand(controlCommand);
        RestartActionListener.setControlCommand(controlCommand);

    }

    public static void main(String[] args)
    {
        // Fenêtre pour le choix du nombre d'étages
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(5, 2, 20, 1);
        JSpinner floorSpinner = new JSpinner(spinnerNumberModel);
        floorSpinner.setSize(50,50);

        JButton okButton = new JButton("OK");
        JLabel choiceLabel = new JLabel("Choisissez le nombre d'étages du bâtiment.");

        JDialog floorChoice = new JDialog(null,"Nombre d'étages", Dialog.ModalityType.APPLICATION_MODAL);
        floorChoice.setSize(350,200);

        okButton.addActionListener(e -> floorChoice.dispose());
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.PAGE_AXIS));
        boxPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        floorChoice.add(boxPanel);
        boxPanel.add(choiceLabel);
        boxPanel.add(floorSpinner);
        boxPanel.add(okButton);

        floorChoice.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        floorChoice.setLocationRelativeTo(null);
        floorChoice.setVisible(true);


        int floors = (int)floorSpinner.getValue();

        new MainWindow(floors);
    }

}
