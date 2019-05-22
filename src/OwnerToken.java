import javax.swing.*;
import java.awt.*;

public class OwnerToken implements Icon {
    // przydzielenie koordynatów dla pozycji od 0 do 39
                //  0   1   2   3   4   5    6   7   8    9  10  11   12   13   14   15   16  17  18   19  20  21  22  23   24   25   26   27   28   29  30  31   32  33  34   35  36  37  38  39
    int[] BoardX = {0, 693, 0, 549, 0, 405, 333, 0, 189, 117, 0, 120, 120, 120, 120, 120, 120, 0, 120, 120, 0, 117, 0, 261, 333, 405, 477, 549, 621, 693, 0, 760, 760, 0, 760, 760, 0, 760, 0, 760};
    int[] BoardY = {0, 760, 0, 760, 0, 760, 760, 0, 760, 760, 0, 695, 623, 551, 479, 407, 335, 0, 191, 119, 0, 120, 0, 120, 120, 120, 120, 120, 120, 120, 0, 119, 191, 0, 335, 407, 0, 551, 0, 695};


    final Color[] color = {Color.BLUE, Color.RED, Color.GREEN, Color.BLACK};        // kolor dla gracza o indexie i
    private Properties properties;                                                  // zapewnia dostęp do sprawdzania posesji

    public OwnerToken(Properties properties){
        this.properties = properties;
    }
    public int getIconHeight() { return 880; }
    public int getIconWidth() { return 880; }

    // maluje kolejne pola pod warunkiem że ktoś jest właścicielem
    public void paintIcon(Component c, Graphics g, int x, int y){
        for (int i = 0; i < 10; i++){
            if (properties.getOwner(i) != 4) {
                g.setColor(color[properties.getOwner(i)]);
                g.fillRect(BoardX[i], BoardY[i], 70, 5);
            }
        }

        for (int i = 10; i < 20; i++) {
            if (properties.getOwner(i) != 4) {
                g.setColor(color[properties.getOwner(i)]);
                g.fillRect(BoardX[i], BoardY[i], 5, 70);
            }
        }

        for (int i = 20; i < 30; i++){
            if (properties.getOwner(i) != 4) {
                g.setColor(color[properties.getOwner(i)]);
                g.fillRect(BoardX[i], BoardY[i], 70, 5);
            }
        }

        for (int i = 30; i < 40; i++) {
            if (properties.getOwner(i) != 4) {
                g.setColor(color[properties.getOwner(i)]);
                g.fillRect(BoardX[i], BoardY[i], 5, 70);
            }
        }

    }
}
