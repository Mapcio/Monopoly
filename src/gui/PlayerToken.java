package gui;

import javax.swing.*;
import java.awt.*;

public class PlayerToken implements Icon {
    // numer gracza
    private int index;
    // aktualna pozycja pionka
    private int x;
    private int y;
    // przydzielenie koordynatów dla pozycji od 0 do 39
    final int[] boardX = {790,698,626,554,482,410,338,266,194,122,30,30,30,30,30,30,30,30,30,30,30,122,194,266,338,410,482,554,626,698,800,800,800,800,800,800,800,800,800,800};
    final int[] boardY = {800,800,800,800,800,800,800,800,800,800,800,698,626,554,482,410,338,266,194,122,20,20,20,20,20,20,20,20,20,20,20,122,194,266,338,410,482,554,626,698};
    // przesunięcie koordynatów dla gracza o indexie i
    final int[] playerX = {0, 35, 0, 35};
    final int[] playerY = {0, 0, 35, 35};
    // kolor dla gracza o indexie i
    final Color[] color = {Color.BLUE, Color.RED, Color.GREEN, Color.BLACK};

    public PlayerToken(int playerIndex){
        this.index = playerIndex;
        // Ustawia pionek na pozycji Start
        this.x = boardX[0] + playerX[index];
        this.y = boardY[0] + playerY[index];
    }

    public void moveToken(int position){
        // Ustala nową pozycję pionka
        this.x = boardX[position] + playerX[index];
        this.y = boardY[position] + playerY[index];
    }

    public int getIconHeight() {
        return 25;
    }
    public int getIconWidth() {
        return 25;
    }

    public void paintIcon(Component c, Graphics g, int x, int y){
        // Rysuje okrąg dla danego gracza
        g.drawOval(this.x, this.y, 25, 25);
        g.setColor(color[index]);
        g.fillOval(this.x, this.y, 25, 25);
    }
}
