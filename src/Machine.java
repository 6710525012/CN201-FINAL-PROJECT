import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
 
/** A machine standing on the farm. It covers SIZE x SIZE tiles, with (col, row) as its top-left tile. */
public class Machine {
    public static final int SIZE = 2;   // tiles wide and tall
 
        /** The three machines the shop sells: name, price, days to make, what goes in, product, and what the product sells for. */
    public enum Kind {
        CAKE("Cake Maker", 30, 2, "Turnip", "Turnip Cake", 40),
        MASHER("Potato Masher", 70, 3, "Potato", "Mashed Potato", 90),
        JAM("Jam Maker", 120, 4, "Strawberry", "Strawberry Jam", 150);

        private final String name;
        private final int price, days, sellPrice;
        private final String ingredient, product;

        Kind(String name, int price, int days, String ingredient, String product, int sellPrice) {
            this.name = name;
            this.price = price;
            this.days = days;
            this.ingredient = ingredient;
            this.product = product;
            this.sellPrice = sellPrice;
        }

        public String getName() { return name; }
        public int getPrice() { return price; }
        public int getDays() { return days; }
        public String getIngredient() { return ingredient; }
        public String getProduct() { return product; }
        public int getSellPrice() { return sellPrice; }
        public int getProfit() { return sellPrice - price; }

        /** @return the kind that makes this product, or null if the name is not a product */
        public static Kind forProduct(String product) {
            for (Kind kind : values()) {
                if (kind.product.equals(product)) return kind;
            }
            return null;
        }
    }
 
    private final Kind kind;
    private final int col, row;
    private boolean working = false;   // true from loading until the product is collected
    private int daysLeft = 0;          // nights to go; 0 while working means it is ready
 
    public Machine(Kind kind, int col, int row) {
        this.kind = kind;
        this.col = col;
        this.row = row;
    }
 
    public Kind getKind() { return kind; }
    public int getCol() { return col; }
    public int getRow() { return row; }

        // ---- making things ----------------------------------------------------------

    public boolean isIdle() { return !working; }
    public boolean isWorking() { return working && daysLeft > 0; }
    public boolean isReady() { return working && daysLeft == 0; }
    public int getDaysLeft() { return daysLeft; }

    /** Starts a batch. The caller takes the ingredient out of the basket first. */
    public void load() throws FarmException {
        if (working) throw new FarmException("The " + kind.getName() + " is already busy");
        working = true;
        daysLeft = kind.getDays();
    }

    /** Takes the finished product out. @return the product's name, e.g. "Turnip Cake" */
    public String collect() throws FarmException {
        if (!isReady()) throw new FarmException("The " + kind.getName() + " has nothing ready yet");
        working = false;
        return kind.getProduct();
    }

    /** One night passes. */
    public void newDay() {
        if (working && daysLeft > 0) daysLeft--;
    }

    /** Puts a loaded machine back the way a save file left it. */
    public void restore(int daysLeft) {
        working = true;
        this.daysLeft = Math.max(0, daysLeft);
    }
 
    /** @return true if this machine stands on the tile (c, r) */
    public boolean covers(int c, int r) {
        return c >= col && c < col + SIZE && r >= row && r < row + SIZE;
    }
 
        public void draw(Graphics2D g) {
        int x = col * Farm.TILE, y = row * Farm.TILE;
        draw(g, kind, x, y);
        if (working) drawStatus(g, x, y);
    }

    /** A little badge: the nights still to go, or a yellow "!" when the product is ready. */
    private void drawStatus(Graphics2D g, int x, int y) {
        boolean ready = isReady();
        g.setColor(ready ? new Color(240, 200, 40) : new Color(60, 60, 60, 210));
        g.fillOval(x + 46, y + 2, 16, 16);
        g.setColor(ready ? Color.BLACK : Color.WHITE);
        g.drawString(ready ? "!" : String.valueOf(daysLeft), x + 52, y + 14);
    }
 
    /** Draws a machine with its top-left corner at pixel (x, y); also used for the ghost while placing. */
    public static void draw(Graphics2D g, Kind kind, int x, int y) {
        pad(g, x, y);
        switch (kind) {
            case CAKE:   drawOven(g, x, y); break;
            case MASHER: drawMasher(g, x, y); break;
            case JAM:    drawJamMaker(g, x, y); break;
        }
    }
 
    // ---- the three machines (all drawn inside a 64 x 64 square) -----------------
 
    private static final Color BENCH = new Color(110, 70, 45);
    private static final Color WOOD = new Color(176, 112, 60);
    private static final Color STEEL = new Color(196, 204, 216);
    private static final Color RED = new Color(214, 44, 34);
    private static final Color CREAM = new Color(246, 238, 214);
 
    private static void drawOven(Graphics2D g, int x, int y) {
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
 
    private static void drawMasher(Graphics2D g, int x, int y) {
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
 
    private static void drawJamMaker(Graphics2D g, int x, int y) {
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
 
    // ---- little shared pieces ---------------------------------------------------
 
    /** The stone floor under every machine. */
    private static void pad(Graphics2D g, int x, int y) {
        g.setColor(new Color(224, 202, 152));
        g.fillRoundRect(x + 1, y + 30, 62, 33, 14, 14);
        g.setColor(new Color(196, 172, 120));
        int[][] stones = {{6, 36}, {22, 41}, {42, 36}, {50, 47}, {9, 51}, {30, 56}};
        for (int[] s : stones) g.fillRoundRect(x + s[0], y + s[1], 10, 6, 4, 4);
    }
 
    private static void box(Graphics2D g, int x, int y, int w, int h, Color fill) {
        g.setColor(fill);
        g.fillRect(x, y, w, h);
        g.setColor(fill.darker());
        g.drawRect(x, y, w - 1, h - 1);
    }
 
    private static void sack(Graphics2D g, int x, int y, int w, int h) {
        g.setColor(new Color(176, 118, 80));
        g.fillRoundRect(x, y + 5, w, h - 5, 8, 8);
        g.setColor(new Color(130, 84, 54));
        g.drawRoundRect(x, y + 5, w, h - 5, 8, 8);
        g.setColor(new Color(240, 236, 222));
        g.fillOval(x + 2, y, w - 4, 10);
    }
 
    private static void barrel(Graphics2D g, int x, int y, int w, int h) {
        g.setColor(new Color(168, 106, 58));
        g.fillRoundRect(x, y, w, h, 4, 4);
        g.setColor(new Color(186, 192, 204));
        g.fillRect(x, y + h / 4, w, 2);
        g.fillRect(x, y + h * 3 / 4 - 2, w, 2);
        g.setColor(new Color(240, 236, 222));
        g.fillOval(x + 1, y - 2, w - 2, 6);
    }
 
    private static void knob(Graphics2D g, int x, int y) {
        g.setColor(new Color(150, 150, 205));
        g.fillOval(x, y, 7, 7);
    }
}
