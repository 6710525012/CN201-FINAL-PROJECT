/** Default market: items sell for their normal price. */
public class NormalPrice implements PriceStrategy {
    @Override
    public int priceFor(int basePrice) {
        return basePrice;
    }

    @Override
    public String getLabel() {
        return "Normal";
    }
}
