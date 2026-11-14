/** Hard times: everything sells for 30% less. */
public class HardTimesPrice implements PriceStrategy {
    @Override
    public int priceFor(int basePrice) {
        return (basePrice * 70 + 50) / 100;   // integer maths, rounded to the nearest G
    }

    @Override
    public String getLabel() {
        return "Hard Times -30%";
    }
}
