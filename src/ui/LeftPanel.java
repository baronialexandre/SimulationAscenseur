package ui;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel
{
    JPanel elevatorView;
    LeftPanel(int floorNb, LogPanel logPanel)
    {
        super(new BorderLayout());

        JLabel north = new JLabel("HOTEL");
        north.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(north, BorderLayout.NORTH);

        JPanel floors = new JPanel();
        floors.setLayout(new BoxLayout(floors, BoxLayout.PAGE_AXIS));
        for(int i = floorNb-1; i>=0; i--)
            floors.add(new FloorPanel(i,floorNb,logPanel));
        this.add(floors, BorderLayout.CENTER);

        elevatorView = new ElevatorPanel(floorNb);
        this.add(elevatorView, BorderLayout.WEST);
    }
}
