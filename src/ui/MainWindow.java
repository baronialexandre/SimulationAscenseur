package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame
{
    public MainWindow(int nbEtage)
    {
        super("Simulateur Ascenseur");
        JPanel mainPane = new JPanel(new GridLayout(1,2));

        JPanel leftPane = new BuildingPanel(nbEtage);
        mainPane.add(leftPane);

        JPanel rightPane = new ElevatorPanel(nbEtage);
        mainPane.add(rightPane);

        this.setSize(1280,720);
        this.setContentPane(mainPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
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

        floorChoice.setVisible(true);

        int floors = (int)floorSpinner.getValue();

        new MainWindow(floors);
    }

}
