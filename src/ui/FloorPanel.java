package ui;

import ui.listeners.FloorCallListener;

import javax.swing.*;
import java.awt.*;

public class FloorPanel extends JPanel
{
    public FloorPanel(int floorId, int nbEtage)
    {
        super();
        this.add(new JLabel(String.valueOf(floorId)));
        if(!(floorId == nbEtage-1))
        {
            JButton upButton = new JButton("/\\");
            upButton.addActionListener(new FloorCallListener(floorId));
            this.add(upButton);
        }
        if(!(floorId == 0))
        {
            JButton downButton = new JButton("\\/");
            downButton.addActionListener(new FloorCallListener(floorId));
            this.add(downButton);
        }
        this.setBackground(new Color(250/nbEtage*(nbEtage-floorId),250/nbEtage*floorId,255));
    }
}
