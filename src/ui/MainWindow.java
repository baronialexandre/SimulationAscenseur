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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends JFrame
{
    private static int nbFloors;
    private static ControlStrategy controlStrategy;
    private static Map<String,ControlStrategy> strategies = new HashMap<>();

    private MainWindow()
    {
        super("Simulateur d'ascenseur");

        JPanel mainPane = new JPanel(new GridLayout(1,2));

        LogPanel logPanel = new LogPanel();

        LeftPanel leftPanel = new LeftPanel(nbFloors, logPanel);
        mainPane.add(leftPanel);

        RightPanel rightPanel = new RightPanel(nbFloors, logPanel);
        mainPane.add(rightPanel);

        this.setSize(1400,810);
        this.setContentPane(mainPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        ControlCommand controlCommand = new ControlCommand(nbFloors, (ElevatorPanel) leftPanel.elevatorView, controlStrategy);

        FloorCallListener.setControlCommand(controlCommand);
        EmergencyActionListener.setControlCommand(controlCommand);
        RestartActionListener.setControlCommand(controlCommand);

    }

    private static void floorChoicePopup()
    {
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(5, 2, 20, 1);
        JSpinner floorSpinner = new JSpinner(spinnerNumberModel);
        floorSpinner.setSize(50,50);

        JButton okButton = new JButton("OK");
        JLabel floorChoiceLabel = new JLabel("Choisissez le nombre d'étages du bâtiment.");
        JDialog floorChoice = new JDialog(null,"Nombre d'étages", Dialog.ModalityType.APPLICATION_MODAL);
        floorChoice.setSize(350,200);

        JLabel strategyChoiceLabel = new JLabel("Choisissez l'algorithme de l'ascenseur.");
        strategies.put("Avancé", new AdvancedControlStrategy());
        strategies.put("Basique", new BasicControlStrategy());
        strategies.put("Aléatoire", new RandomControlStrategy());
        JComboBox<String> controlStrategies = new JComboBox<>();
        for(String s : strategies.keySet())
            controlStrategies.addItem(s);
        controlStrategies.setSelectedItem("Avancé");

        okButton.addActionListener(e -> floorChoice.dispose());

        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.PAGE_AXIS));
        boxPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        boxPanel.add(floorChoiceLabel);
        boxPanel.add(floorSpinner);
        boxPanel.add(strategyChoiceLabel);
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
        controlStrategy = strategies.get(controlStrategies.getSelectedItem());
    }

    public static void main(String[] args)
    {
        floorChoicePopup();
        new MainWindow();
    }
}
