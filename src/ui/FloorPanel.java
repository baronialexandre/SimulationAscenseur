package ui;

import ui.listeners.FloorCallListener;

import javax.swing.*;
import java.awt.*;

public class FloorPanel extends JPanel
{
    public FloorPanel(int floorId, int nbEtage, LogPanel logPanel)
    {
        super();
        JLabel floorLabel = new JLabel(String.valueOf(floorId));
        if( 255/nbEtage*floorId < 255/2)
            floorLabel.setForeground(new Color(255,255,255));
        this.add(floorLabel);
        if(!(floorId == nbEtage-1))
        {
            JButton upButton = new JButton("/\\");
            upButton.addActionListener(new FloorCallListener(floorId,logPanel));
            this.add(upButton);
        }
        if(!(floorId == 0))
        {
            JButton downButton = new JButton("\\/");
            downButton.addActionListener(new FloorCallListener(floorId,logPanel));
            this.add(downButton);
        }
        this.setBackground(new Color(255/nbEtage*floorId,255/nbEtage*floorId,255/nbEtage*floorId));
    }
}
