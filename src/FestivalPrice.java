/** Festival market: everything sells for 50% more. */
public class FestivalPrice implements PriceStrategy {
    @Override
    public int priceFor(int basePrice) {
        return (basePrice * 150 + 50) / 100;   // integer maths, rounded to the nearest G
    }

    @Override
    public String getLabel() {
        return "Festival +50%";
    }
}
