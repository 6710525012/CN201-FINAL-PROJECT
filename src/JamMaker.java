import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/** Cooks one strawberry into one jar of jam; on a rainy night the fire goes out and it waits. */
public class JamMaker extends Machine {
    public JamMaker(int col, int row) {
        super(Kind.JAM, col, row);
    }

    /** Rain puts the fire out, so a rainy night does not count. */
    @Override
    public void newDay(Weather weather) {
        if (weather.watersCrops()) return;
        super.newDay(weather);
    }

    @Override
    protected void drawArt(Graphics2D g, int x, int y) {

        barrel(g, x + 1, y + 32, 12, 16);
        barrel(g, x + 51, y + 38, 12, 16);
        // big wooden vat with steel bands, jam on top, and a whisk
        box(g, x + 26, y + 18, 26, 30, WOOD);
        g.setColor(new Color(186, 192, 204));
        g.fillRect(x + 26, y + 25, 26, 3);
        g.fillRect(x + 26, y + 39, 26, 3);
        g.setColor(new Color(136, 76, 36));
        g.fillOval(x + 25, y + 13, 28, 10);
        g.setColor(new Color(170, 100, 50));
        g.fillOval(x + 29, y + 15, 20, 6);
        g.setColor(new Color(170, 178, 192));
        g.setStroke(new BasicStroke(2));
        g.drawArc(x + 30, y + 8, 8, 12, 0, 180);
        g.drawArc(x + 40, y + 8, 8, 12, 0, 180);
        g.setStroke(new BasicStroke(1));
        g.setColor(STEEL);
        g.fillOval(x + 35, y + 2, 8, 8);
        // red frame around the vat
        g.setColor(RED);
        g.setStroke(new BasicStroke(3));
        g.drawLine(x + 25, y + 30, x + 25, y + 6);
        g.drawLine(x + 25, y + 6, x + 52, y + 6);
        g.drawLine(x + 52, y + 6, x + 52, y + 20);
        g.setStroke(new BasicStroke(1));
        // red tank with a steel cap and a tap
        g.setColor(new Color(210, 52, 50));
        g.fillOval(x + 6, y + 17, 20, 25);
        g.setColor(new Color(150, 30, 30));
        g.drawOval(x + 6, y + 17, 20, 25);
        box(g, x + 11, y + 12, 10, 6, STEEL);
        box(g, x + 9, y + 38, 5, 9, STEEL);
        // a jar of jam on a mat, and the bench in front
        box(g, x + 14, y + 48, 14, 7, new Color(96, 64, 44));
        g.setColor(new Color(210, 232, 244));
        g.fillRoundRect(x + 18, y + 43, 7, 10, 3, 3);
        g.setColor(RED);
        g.fillRect(x + 19, y + 47, 5, 5);
        box(g, x + 26, y + 54, 28, 7, BENCH);
    }
}
