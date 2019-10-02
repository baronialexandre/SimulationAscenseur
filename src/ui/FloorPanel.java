package ui;

import javax.swing.*;
import java.awt.*;

public class FloorPanel extends JPanel {
    public FloorPanel(int floorId, int nbEtage) {
        super();
        this.add(new JLabel(String.valueOf(floorId)));
        if(!(floorId == nbEtage-1))
            this.add(new JButton("/\\"));
        if(!(floorId == 0))
            this.add(new JButton("\\/"));
        this.setBackground(new Color(250/nbEtage*(nbEtage-floorId),250/nbEtage*floorId,255));
    }
}
