package software.ulpgc.minesweeper.swing;

import javax.swing.*;
import java.awt.*;

public class SwingMainFrame extends JFrame {
    private final JLabel gameStatus = new JLabel("");

    public SwingMainFrame() throws HeadlessException {
        setTitle("Minesweeper Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new SwingGameBoard(16, 16, 40, gameStatus));
        add(createStatusPanel(), BorderLayout.SOUTH);
        setResizable(false);
        pack();
    }

    private Component createStatusPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.add(gameStatus, JLabel.CENTER);
        return statusPanel;
    }
}
