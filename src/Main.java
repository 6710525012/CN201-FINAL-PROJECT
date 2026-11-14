import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // the one word after "Main" picks the market price mode, e.g. java -cp out Main festival
        PriceStrategy strategy = PriceStrategyFactory.create(args.length > 0 ? args[0] : null);
        // Swing is single-threaded: build the window on its event thread
        SwingUtilities.invokeLater(() -> {
            GamePanel panel = new GamePanel(strategy);
            JFrame frame = new JFrame("Little Farm - CN201 [" + strategy.getLabel() + "]");
            frame.add(panel);
            frame.pack();
            frame.setResizable(true);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            panel.requestFocusInWindow();
            panel.start();
        });
    }
}