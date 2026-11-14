/** The only place that turns a word from the command line into a PriceStrategy. */
public final class PriceStrategyFactory {
    private PriceStrategyFactory() { }

    /**
     * @param word normal, festival or hard (any letter case); null or empty means normal
     * @return the matching strategy; an unknown word gives normal prices and a warning, never an error
     */
    public static PriceStrategy create(String word) {
        if (word == null || word.trim().isEmpty()) return new NormalPrice();
        switch (word.trim().toLowerCase()) {
            case "normal":   return new NormalPrice();
            case "festival": return new FestivalPrice();
            case "hard":     return new HardTimesPrice();
            default:
                System.out.println("Unknown word '" + word + "' - using normal prices. Try: normal, festival, hard");
                return new NormalPrice();
        }
    }
}
