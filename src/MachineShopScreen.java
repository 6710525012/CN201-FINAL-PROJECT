import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/** The machine shop: same look and controls as the seed shop, listing the machines for sale. */
public class MachineShopScreen implements Screen {
    private static final int MAX_QUANTITY = 99;

    // one entry per machine: name, price, days to make, product, what the product sells for
    private static final String[] NAMES    = {"Cake Maker", "Potato Masher", "Jam Maker"};
    private static final int[]    PRICES   = {30, 70, 120};
    private static final int[]    DAYS     = {2, 3, 4};
    private static final String[] PRODUCTS = {"Turnip Cake", "Mashed Potato", "Strawberry Jam"};
    private static final int[]    SELLS    = {40, 90, 150};

    private final GamePanel game;
    private final FarmScreen farm;
    private int row = 0;
    private int quantity = 1;
    private String status = "";

    public MachineShopScreen(GamePanel game, FarmScreen farm) {
        this.game = game;
        this.farm = farm;
    }

    @Override
    public void keyPressed(int code) {
        switch (code) {
            case KeyEvent.VK_UP:    case KeyEvent.VK_W: row = (row - 1 + NAMES.length) % NAMES.length; break;
            case KeyEvent.VK_DOWN:  case KeyEvent.VK_S: row = (row + 1) % NAMES.length; break;
            case KeyEvent.VK_LEFT:  case KeyEvent.VK_A: quantity = Math.max(1, quantity - 1); break;
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D: quantity = Math.min(MAX_QUANTITY, quantity + 1); break;
            case KeyEvent.VK_ENTER: case KeyEvent.VK_SPACE: buy(); break;
            case KeyEvent.VK_ESCAPE: game.setScreen(farm); break;
            default: break;
        }
    }

    /** Machines have no effect in the game yet, so nothing is charged. Put the real purchase here later. */
    private void buy() {
        status = "Machines aren't available yet.";
    }

    @Override
    public void draw(Graphics2D g) {
        farm.draw(g);
        Ui.dim(g, 150);
        int left = 40, top = 50, width = Ui.WIDTH - 80;
        Ui.paper(g, left, top, width, 400);
        Ui.centered(g, "MACHINE SHOP", 24, top + 38, Ui.WOOD);
        g.setFont(Ui.font(14));
        g.setColor(new Color(170, 120, 20));
        String gold = "Your gold " + farm.getFarmer().getGold() + "G";
        g.drawString(gold, left + width - 20 - g.getFontMetrics().stringWidth(gold), top + 38);

        g.setFont(Ui.font(13));
        g.setColor(Ui.WOOD);
        g.drawString(String.format("%-14s %5s %7s  %-15s %6s %6s", "Machine", "Price", "Time", "Product", "Sells", "Profit"),
                left + 30, top + 80);
        for (int i = 0; i < NAMES.length; i++) {
            int y = top + 110 + i * 30;
            if (i == row) {
                g.setColor(Ui.HIGHLIGHT);
                g.fillRoundRect(left + 18, y - 19, width - 36, 27, 8, 8);
            }
            g.setColor(Ui.INK);
            g.drawString(String.format("%-14s %4dG %2d days  %-15s %5dG %+5dG", NAMES[i], PRICES[i], DAYS[i],
                    PRODUCTS[i], SELLS[i], SELLS[i] - PRICES[i]), left + 30, y);
        }

        Ui.centered(g, "Quantity  < " + quantity + " >    Total " + PRICES[row] * quantity + "G", 17, top + 268, Ui.INK);
        Ui.centered(g, status, 13, top + 305, Ui.WARNING);
        Ui.centered(g, "UP/DOWN select   LEFT/RIGHT amount   ENTER buy   ESC leave", 12, top + 380, Ui.WOOD);
    }
}
