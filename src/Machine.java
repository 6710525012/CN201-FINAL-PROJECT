import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/** A machine standing on the farm. It covers SIZE x SIZE tiles, with (col, row) as its top-left tile. */
public abstract class Machine {
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

    protected Machine(Kind kind, int col, int row) {
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

    /** One night passes. The weather is the new day's weather. */
    public void newDay(Weather weather) {
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
        MachineFactory.create(kind, 0, 0).drawArt(g, x, y);
    }

    /** Each machine draws its own picture inside a 64 x 64 square with (x, y) its top-left corner. */
    protected abstract void drawArt(Graphics2D g, int x, int y);

    /** How many products one batch makes. Most machines make one. */
    public int getOutputAmount() { return 1; }

    // ---- colours the machines share -------------------------------------------

    protected static final Color BENCH = new Color(110, 70, 45);
    protected static final Color WOOD = new Color(176, 112, 60);
    protected static final Color STEEL = new Color(196, 204, 216);
    protected static final Color RED = new Color(214, 44, 34);
    protected static final Color CREAM = new Color(246, 238, 214);

    // ---- little shared pieces ---------------------------------------------------

    /** The stone floor under every machine. */
    private static void pad(Graphics2D g, int x, int y) {
        g.setColor(new Color(224, 202, 152));
        g.fillRoundRect(x + 1, y + 30, 62, 33, 14, 14);
        g.setColor(new Color(196, 172, 120));
        int[][] stones = {{6, 36}, {22, 41}, {42, 36}, {50, 47}, {9, 51}, {30, 56}};
        for (int[] s : stones) g.fillRoundRect(x + s[0], y + s[1], 10, 6, 4, 4);
    }

    protected static void box(Graphics2D g, int x, int y, int w, int h, Color fill) {
        g.setColor(fill);
        g.fillRect(x, y, w, h);
        g.setColor(fill.darker());
        g.drawRect(x, y, w - 1, h - 1);
    }

    protected static void sack(Graphics2D g, int x, int y, int w, int h) {
        g.setColor(new Color(176, 118, 80));
        g.fillRoundRect(x, y + 5, w, h - 5, 8, 8);
        g.setColor(new Color(130, 84, 54));
        g.drawRoundRect(x, y + 5, w, h - 5, 8, 8);
        g.setColor(new Color(240, 236, 222));
        g.fillOval(x + 2, y, w - 4, 10);
    }

    protected static void barrel(Graphics2D g, int x, int y, int w, int h) {
        g.setColor(new Color(168, 106, 58));
        g.fillRoundRect(x, y, w, h, 4, 4);
        g.setColor(new Color(186, 192, 204));
        g.fillRect(x, y + h / 4, w, 2);
        g.fillRect(x, y + h * 3 / 4 - 2, w, 2);
        g.setColor(new Color(240, 236, 222));
        g.fillOval(x + 1, y - 2, w - 2, 6);
    }

    protected static void knob(Graphics2D g, int x, int y) {
        g.setColor(new Color(150, 150, 205));
        g.fillOval(x, y, 7, 7);
    }
}
