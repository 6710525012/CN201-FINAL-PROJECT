import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
 
/**
 * After buying, the new machine floats on the farm. Move it with the arrow keys (hold to keep sliding)
 * and press SPACE to install it. ESC gives up and refunds whatever has not been installed yet.
 * Like the shops, the game clock waits while this screen is open.
 */
public class PlaceMachineScreen implements Screen {
    private static final int FIRST_REPEAT = 12;   // ticks before a held key starts sliding the machine
    private static final int REPEAT = 4;          // ticks between slides after that
 
    private final GamePanel game;
    private final FarmScreen farm;
    private final Machine.Kind kind;
    private int remaining;
    private final int total;
    private int col, row;
    private int cooldown;
    private String status = "";
 
    public PlaceMachineScreen(GamePanel game, FarmScreen farm, Machine.Kind kind, int quantity) {
        this.game = game;
        this.farm = farm;
        this.kind = kind;
        this.remaining = quantity;
        this.total = quantity;
        // start in front of the farmer, kept on the map
        col = clamp(farm.getFarmer().facingCol(), 1, Farm.COLS - Machine.SIZE - 1);
        row = clamp(farm.getFarmer().facingRow(), 1, Farm.ROWS - Machine.SIZE - 1);
    }
 
    private static int clamp(int value, int low, int high) {
        return Math.max(low, Math.min(high, value));
    }
 
    private void move(int dx, int dy) {
        col = clamp(col + dx, 0, Farm.COLS - Machine.SIZE);
        row = clamp(row + dy, 0, Farm.ROWS - Machine.SIZE);
        status = "";
    }
 
    @Override
    public void keyPressed(int code) {
        switch (code) {
            case KeyEvent.VK_UP:    case KeyEvent.VK_W: move(0, -1); cooldown = FIRST_REPEAT; break;
            case KeyEvent.VK_DOWN:  case KeyEvent.VK_S: move(0, 1);  cooldown = FIRST_REPEAT; break;
            case KeyEvent.VK_LEFT:  case KeyEvent.VK_A: move(-1, 0); cooldown = FIRST_REPEAT; break;
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D: move(1, 0);  cooldown = FIRST_REPEAT; break;
            case KeyEvent.VK_SPACE: case KeyEvent.VK_ENTER: install(); break;
            case KeyEvent.VK_ESCAPE: cancel(); break;
            default: break;
        }
    }
 
    @Override
    public void update() {
        Direction held = heldDirection();
        if (held == null) {
            cooldown = 0;
            return;
        }
        if (cooldown > 0) cooldown--;
        if (cooldown == 0) {
            move(held.dx, held.dy);
            cooldown = REPEAT;
        }
    }
 
    private Direction heldDirection() {
        if (game.isHeld(KeyEvent.VK_UP, KeyEvent.VK_W)) return Direction.UP;
        if (game.isHeld(KeyEvent.VK_DOWN, KeyEvent.VK_S)) return Direction.DOWN;
        if (game.isHeld(KeyEvent.VK_LEFT, KeyEvent.VK_A)) return Direction.LEFT;
        if (game.isHeld(KeyEvent.VK_RIGHT, KeyEvent.VK_D)) return Direction.RIGHT;
        return null;
    }
 
    private boolean canInstallHere() {
        for (int r = 0; r < Machine.SIZE; r++) {
            for (int c = 0; c < Machine.SIZE; c++) {
                if (!farm.canPlaceMachineAt(col + c, row + r)) return false;
            }
        }
        return true;
    }
 
    private void install() {
        if (!canInstallHere()) {
            status = "Can't install here - it needs a free 2x2 patch of grass";
            return;
        }
        try {
            farm.getFarm().addMachine(MachineFactory.create(kind, col, row));
        } catch (FarmException e) {
            status = e.getMessage();
            return;
        }
        remaining--;
        if (remaining > 0) {
            status = "Installed! " + remaining + " more to place";
        } else {
            farm.say(kind.getName() + " installed!");
            game.setScreen(farm);
        }
    }
 
    private void cancel() {
        int refund = remaining * kind.getPrice();
        Farmer farmer = farm.getFarmer();
        farmer.setGold(farmer.getGold() + refund);
        farm.say("Cancelled - refunded " + refund + "G");
        game.setScreen(farm);
    }
 
    @Override
    public void draw(Graphics2D g) {
        farm.draw(g);
        // the footprint: green where it fits, red where something is in the way
        for (int r = 0; r < Machine.SIZE; r++) {
            for (int c = 0; c < Machine.SIZE; c++) {
                boolean ok = farm.canPlaceMachineAt(col + c, row + r);
                g.setColor(ok ? new Color(80, 220, 90, 120) : new Color(230, 60, 50, 140));
                g.fillRect((col + c) * Farm.TILE, (row + r) * Farm.TILE, Farm.TILE, Farm.TILE);
            }
        }
        Composite normal = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f));
        Machine.draw(g, kind, col * Farm.TILE, row * Farm.TILE);
        g.setComposite(normal);
 
        // instructions at the bottom of the map
        int boxHeight = 44, y = Ui.MAP_HEIGHT - boxHeight - 6;
        g.setColor(new Color(0, 0, 0, 215));
        g.fillRoundRect(10, y, Ui.WIDTH - 20, boxHeight, 10, 10);
        int number = total - remaining + 1;
        Ui.centered(g, "Place your " + kind.getName() + (total > 1 ? "  (" + number + " of " + total + ")" : ""),
                14, y + 18, Ui.HIGHLIGHT);
        if (!status.isEmpty()) {
            Ui.centered(g, status, 12, y + 36, new Color(255, 150, 130));
        } else {
            Ui.centered(g, "ARROWS move   SPACE install   ESC cancel and refund", 12, y + 36, Color.WHITE);
        }
    }
}
