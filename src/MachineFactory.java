/** The one place that turns a machine kind into the right machine object (Factory pattern). */
public class MachineFactory {
    public static Machine create(Machine.Kind kind, int col, int row) {
        switch (kind) {
            case CAKE:   return new CakeMaker(col, row);
            case MASHER: return new PotatoMasher(col, row);
            case JAM:    return new JamMaker(col, row);
            default: throw new IllegalArgumentException("unknown machine: " + kind);
        }
    }
}
