package software.ulpgc.minesweeper.swing;
import software.ulpgc.minesweeper.architecture.view.BoardDisplay;

import javax.swing.*;

public class SwingBoardDisplay extends JPanel implements BoardDisplay {
    private final JLabel status;
    private final JButton faceButton;

    public SwingBoardDisplay(JLabel status, JButton faceButton) {
        this.status = status;
        this.faceButton = faceButton;
    }

    @Override
    public void showNewGame(String difficulty) {
        removeAll();
        switch (difficulty) {
            case "easy":
                add(new SwingGameBoard(8, 8, 10, status, faceButton));
                break;
            case "medium":
                add(new SwingGameBoard(16, 16, 40, status, faceButton));
                break;
            case "hard":
                add(new SwingGameBoard(30, 30, 150, status, faceButton));
                break;
            default:
                System.err.print("invalid mode");
        }
        revalidate();
    }
}
