package gui;

import jdk.nashorn.internal.parser.Token;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel{
    private Image MonopolyBoard;

    public Board(){
        MonopolyBoard = new ImageIcon("graphics/plansza.jpg").getImage();
    }

    public void paintComponent(Graphics g){
        g.drawImage(MonopolyBoard, 0, 0, null);
    }
}
