/** A market price mode. Every mode must be able to price an item and name itself. */
public interface PriceStrategy {
    /** @return what the market pays for an item whose normal price is basePrice */
    int priceFor(int basePrice);

    /** @return the mode's name, shown in the window title */
    String getLabel();
}
