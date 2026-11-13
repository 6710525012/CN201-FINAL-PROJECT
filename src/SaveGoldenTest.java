import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Golden-output test for refactoring 1.4.
 * It writes a fixed save file, loads it into a new farm, saves it again, and prints every line.
 * The printed output must be exactly the same before and after the refactoring.
 */
public class SaveGoldenTest {
    public static void main(String[] args) throws Exception {
        String file = "golden_test_save.txt";
        Files.write(Paths.get(file), Arrays.asList(
                "little-farm save 1",
                "day 7",
                "weather RAINY",
                "gold 1234",
                "earned 900",
                "can 3",
                "seeds Turnip 4",
                "seeds Potato 2",
                "seeds Strawberry 0",
                "basket Turnip 5",
                "basket Turnip Cake 2",
                "villager 3 2",
                "request Potato 2 560 5",
                "machine CAKE 9 6 1",
                "machine JAM 12 6",
                "soil 5 6 Turnip 1 wet",
                "soil 6 6 - 0 dry"));

        Farm farm = new Farm();
        Farmer farmer = new Farmer(2, 4, 0);
        GameClock clock = new GameClock();
        SaveGame save = new SaveGame(file);
        save.loadInto(farm, farmer, clock);

        System.out.println("day=" + clock.getDay() + " weather=" + clock.getWeather());
        System.out.println("gold=" + farmer.getGold() + " basket=" + farmer.getBasket());
        System.out.println("machines=" + farm.getMachines().size());
        System.out.println("--- saved again ---");
        save.save(farm, farmer, clock);
        for (String line : Files.readAllLines(Paths.get(file))) {
            System.out.println(line);
        }
        Files.delete(Paths.get(file));
    }
}