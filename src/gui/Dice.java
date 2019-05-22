package gui;

import javax.swing.*;
import java.awt.*;

public class Dice implements Icon {
    private Image dice1;
    private Image dice2;
    final Image[] dices = {new ImageIcon("graphics/d1.png").getImage(), new ImageIcon("graphics/d2.png").getImage(),
                            new ImageIcon("graphics/d3.png").getImage(), new ImageIcon("graphics/d4.png").getImage(),
                             new ImageIcon("graphics/d5.png").getImage(), new ImageIcon("graphics/d6.png").getImage()};

    public Dice(){
        // Inicjuje kości z wyrzuconymi szóstkami przed rozpoczęciem gry
        dice1 = dices[5];
        dice2 = dices[5];
    }

    public void setDice(int throw1, int throw2){
        dice1 = dices[throw1 - 1];
        dice2 = dices[throw2 - 1];
    }

    public int getIconHeight() {return 112; }
    public int getIconWidth() {
        return 112;
    }

    public void paintIcon(Component c, Graphics g, int x, int y){
        // Rysuje wynik rzutu koścmi
        g.drawImage(dice1, 170, 350, null);
        g.drawImage(dice2, 630, 350, null);
    }

}
