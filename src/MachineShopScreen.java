import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
 
/** The machine shop: same look and controls as the seed shop. Buying hands the machine to PlaceMachineScreen. */
public class MachineShopScreen implements Screen {
    private static final int MAX_QUANTITY = 99;
 
    private final GamePanel game;
    private final FarmScreen farm;
    private final Machine.Kind[] kinds = Machine.Kind.values();
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
            case KeyEvent.VK_UP:    case KeyEvent.VK_W: row = (row - 1 + kinds.length) % kinds.length; break;
            case KeyEvent.VK_DOWN:  case KeyEvent.VK_S: row = (row + 1) % kinds.length; break;
            case KeyEvent.VK_LEFT:  case KeyEvent.VK_A: quantity = Math.max(1, quantity - 1); break;
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D: quantity = Math.min(MAX_QUANTITY, quantity + 1); break;
            case KeyEvent.VK_ENTER: case KeyEvent.VK_SPACE: buy(); break;
            case KeyEvent.VK_ESCAPE: game.setScreen(farm); break;
            default: break;
        }
    }
 
    /** Pays, then lets the player carry the new machine to where it should stand. */
    private void buy() {
        Machine.Kind kind = kinds[row];
        int total = kind.getPrice() * quantity;
        try {
            farm.getFarmer().spendGold(total);
        } catch (FarmException e) {
            status = e.getMessage();
            return;
        }
        farm.floatAtFacing("-" + total + "G", new Color(255, 140, 120));
        game.setScreen(new PlaceMachineScreen(game, farm, kind, quantity));
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
        for (int i = 0; i < kinds.length; i++) {
            Machine.Kind kind = kinds[i];
            int y = top + 110 + i * 30;
            if (i == row) {
                g.setColor(Ui.HIGHLIGHT);
                g.fillRoundRect(left + 18, y - 19, width - 36, 27, 8, 8);
            }
            g.setColor(Ui.INK);
            g.drawString(String.format("%-14s %4dG %2d days  %-15s %5dG %+5dG", kind.getName(), kind.getPrice(),
                    kind.getDays(), kind.getProduct(), kind.getSellPrice(), kind.getProfit()), left + 30, y);
        }
 
        Ui.centered(g, "Quantity  < " + quantity + " >    Total " + kinds[row].getPrice() * quantity + "G", 17, top + 268, Ui.INK);
        Ui.centered(g, status, 13, top + 305, Ui.WARNING);
        Ui.centered(g, "UP/DOWN select   LEFT/RIGHT amount   ENTER buy   ESC leave", 12, top + 380, Ui.WOOD);
    }
}
