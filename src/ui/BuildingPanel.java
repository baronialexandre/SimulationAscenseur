package ui;

import javax.swing.*;
import java.awt.*;

public class BuildingPanel extends JPanel
{
    public BuildingPanel(int nbEtage)
    {
        super(new BorderLayout());

        JLabel north = new JLabel("Hotel");
        this.add(north, BorderLayout.NORTH);

        JPanel floors = new JPanel();
        floors.setLayout(new BoxLayout(floors, BoxLayout.PAGE_AXIS));
        for(int i = nbEtage-1; i>=0; i--)
            floors.add(new FloorPanel(i,nbEtage));
        this.add(floors, BorderLayout.CENTER);

        JPanel elevatorView = new ElevatorViewPanel(nbEtage);
        this.add(elevatorView, BorderLayout.WEST);


        this.setBackground(new Color(221, 147, 218));
    }
}
