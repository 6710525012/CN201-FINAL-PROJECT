/** The save file has a damaged line. It remembers which line, so the game can tell the player. */
public class SaveFileException extends FarmException {
    private final int lineNumber;

    public SaveFileException(int lineNumber, String message) {
        super("line " + lineNumber + ": " + message);
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() { return lineNumber; }
}