package ui;

import control.ControlCommand;
import control.strategy.AdvancedControlStrategy;
import control.strategy.BasicControlStrategy;
import control.strategy.ControlStrategy;
import control.strategy.RandomControlStrategy;
import ui.listeners.EmergencyActionListener;
import ui.listeners.FloorCallListener;
import ui.listeners.RestartActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame
{
    private static int nbFloors;
    private static ControlStrategy controlStrategy;

    private MainWindow()
    {
        super("Simulateur Ascenseur");

        JPanel mainPane = new JPanel(new GridLayout(1,2));

        LogPanel logPanel = new LogPanel();

        BuildingPanel leftPane = new BuildingPanel(nbFloors, logPanel);
        mainPane.add(leftPane);

        ElevatorPanel rightPane = new ElevatorPanel(nbFloors, logPanel);
        mainPane.add(rightPane);

        this.setSize(1400,810);
        this.setContentPane(mainPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        ControlCommand controlCommand = new ControlCommand(nbFloors, (ElevatorViewPanel) leftPane.elevatorView, controlStrategy);

        FloorCallListener.setControlCommand(controlCommand);
        EmergencyActionListener.setControlCommand(controlCommand);
        RestartActionListener.setControlCommand(controlCommand);

    }

    /**
     * Fenêtre popup pour paramétrer le nombre d'étages à simuler.
     */
    private static void floorChoicePopup()
    {
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(5, 2, 20, 1);
        JSpinner floorSpinner = new JSpinner(spinnerNumberModel);
        floorSpinner.setSize(50,50);

        JButton okButton = new JButton("OK");
        JLabel choiceLabel = new JLabel("Choisissez le nombre d'étages du bâtiment.");

        JDialog floorChoice = new JDialog(null,"Nombre d'étages", Dialog.ModalityType.APPLICATION_MODAL);
        floorChoice.setSize(350,200);

        JComboBox<String> controlStrategies = new JComboBox<>();
        controlStrategies.addItem("Advanced");
        controlStrategies.addItem("Basic");
        controlStrategies.addItem("Random");

        okButton.addActionListener(e -> floorChoice.dispose());
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.PAGE_AXIS));
        boxPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        boxPanel.add(choiceLabel);
        boxPanel.add(floorSpinner);
        boxPanel.add(controlStrategies);
        boxPanel.add(okButton);
        floorChoice.add(boxPanel);

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


        nbFloors = (int)floorSpinner.getValue();
        System.out.println((String)controlStrategies.getSelectedItem());
        switch ((String)controlStrategies.getSelectedItem()){
            case "Advanced":
                controlStrategy = new AdvancedControlStrategy();
                break;
            case "Basic":
                controlStrategy = new BasicControlStrategy();
                break;
            case "Random":
                controlStrategy = new RandomControlStrategy();
                break;
        }
    }

    public static void main(String[] args)
    {
        floorChoicePopup();
        new MainWindow();
    }

}
