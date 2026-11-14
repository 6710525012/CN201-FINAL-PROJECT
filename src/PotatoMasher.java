import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/** Mashes one potato into TWO bowls of mashed potato. */
public class PotatoMasher extends Machine {
    public PotatoMasher(int col, int row) {
        super(Kind.MASHER, col, row);
    }

    /** The masher squeezes enough out of one potato for two bowls. */
    @Override
    public int getOutputAmount() { return 2; }

    @Override
    protected void drawArt(Graphics2D g, int x, int y) {

        sack(g, x + 2, y + 22, 16, 22);
        // table: top, legs, and the red plate the machine is bolted to
        box(g, x + 12, y + 44, 6, 14, new Color(170, 105, 50));
        box(g, x + 46, y + 44, 6, 14, new Color(170, 105, 50));
        box(g, x + 10, y + 36, 44, 8, new Color(206, 134, 64));
        box(g, x + 16, y + 30, 24, 7, RED);
        // dough on top, steel body, two rollers, and the crank
        g.setColor(new Color(244, 244, 176));
        g.fillOval(x + 9, y + 6, 15, 11);
        box(g, x + 19, y + 12, 16, 19, STEEL);
        box(g, x + 14, y + 14, 28, 7, new Color(170, 190, 212));
        box(g, x + 14, y + 22, 28, 7, new Color(146, 172, 200));
        g.setColor(new Color(150, 160, 175));
        g.setStroke(new BasicStroke(2));
        g.drawLine(x + 42, y + 20, x + 49, y + 14);
        g.setStroke(new BasicStroke(1));
        g.setColor(new Color(232, 72, 40));
        g.fillOval(x + 46, y + 9, 8, 8);
        // the chopping arm: a wooden beam with a steel blade, held by two red posts
        g.setColor(new Color(206, 134, 64));
        g.setStroke(new BasicStroke(4));
        g.drawLine(x + 35, y + 40, x + 58, y + 29);
        g.setColor(new Color(212, 220, 230));
        g.setStroke(new BasicStroke(2));
        g.drawLine(x + 37, y + 43, x + 56, y + 34);
        g.setStroke(new BasicStroke(1));
        box(g, x + 55, y + 28, 5, 12, RED);
        knob(g, x + 54, y + 23);
        box(g, x + 32, y + 41, 5, 11, RED);
        knob(g, x + 31, y + 36);
        // a bowl of mashed potato in front
        g.setColor(new Color(240, 196, 30));
        g.fillArc(x + 18, y + 47, 22, 14, 180, 180);
        g.setColor(new Color(232, 235, 170));
        g.fillOval(x + 20, y + 51, 18, 6);
    }
}
