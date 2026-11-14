/** The farmer tried to buy something but does not have enough gold. */
public class NotEnoughGoldException extends FarmException {
    public NotEnoughGoldException(int need, int have) {
        super("Not enough gold (need " + need + "G, you have " + have + "G)");
    }
}