import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/** Bakes one turnip into one turnip cake. */
public class CakeMaker extends Machine {
    public CakeMaker(int col, int row) {
        super(Kind.CAKE, col, row);
    }

    @Override
    protected void drawArt(Graphics2D g, int x, int y) {

        // flour sack and barrel on either side
        sack(g, x + 3, y + 34, 14, 18);
        barrel(g, x + 50, y + 36, 12, 18);
        // brick base with a few mortar lines
        box(g, x + 13, y + 36, 38, 16, new Color(196, 124, 72));
        g.setColor(new Color(150, 90, 50));
        g.drawLine(x + 13, y + 44, x + 50, y + 44);
        g.drawLine(x + 26, y + 36, x + 26, y + 44);
        g.drawLine(x + 39, y + 44, x + 39, y + 52);
        // cream dome with a little chimney
        g.setColor(CREAM);
        g.fillArc(x + 15, y + 10, 34, 52, 0, 180);
        g.setColor(CREAM.darker());
        g.drawArc(x + 15, y + 10, 34, 52, 0, 180);
        g.setColor(new Color(80, 100, 140));
        g.fillPolygon(new int[] {x + 32, x + 36, x + 28}, new int[] {y + 1, y + 11, y + 11}, 3);
        // blue iron door
        g.setColor(new Color(150, 80, 45));
        g.fillRoundRect(x + 24, y + 31, 16, 20, 12, 12);
        g.setColor(new Color(72, 104, 160));
        g.fillRoundRect(x + 26, y + 33, 12, 18, 10, 10);
        g.setColor(new Color(40, 50, 70));
        g.fillOval(x + 30, y + 43, 4, 4);
        // wooden paddle leaning on the oven, and the bench in front
        g.setColor(new Color(130, 85, 50));
        g.setStroke(new BasicStroke(3));
        g.drawLine(x + 47, y + 30, x + 52, y + 56);
        g.setStroke(new BasicStroke(1));
        box(g, x + 18, y + 55, 28, 7, BENCH);
    }
}
