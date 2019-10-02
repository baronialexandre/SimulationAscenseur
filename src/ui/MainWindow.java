package ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(int nbEtage){
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

    public static void main(String[] args){
        //fenetre demande nombre etage
        //int nbEtage = new InitialisationWindow();
        //il faut recup l'entier et le passé
        new MainWindow(5);
    }

}
