package ui;

import control.ControlCommand;
import ui.listeners.EmergencyActionListener;
import ui.listeners.FloorCallListener;
import ui.listeners.RestartActionListener;

import javax.swing.*;
import java.awt.*;

/*todo: Check :
*   -> Le listener fonctionne : un cas me gêne :
*      \-> Il peut ne pas bien détecter le changement d'étages
*      \-> j'ai mis un synchronized, mais je ne sais pas si c'est suffisant pour ne pas "sauter" un changement de y
*         \-> En débug j'ai eu des cas ou à chaque itération de listener, y faisait y:97 ; y:98 ; y:101
*             Donc sautait un modulo de 100 et sautait donc un étage...
*   -> L'appel à l'étage 1 fonctionne mais l'ascenseur ne repart pas
* */


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

        floorChoice.setVisible(true);

        int floors = (int)floorSpinner.getValue();

        new MainWindow(floors);
    }

}
