package software.ulpgc.minesweeper;

import software.ulpgc.minesweeper.swing.SwingMainFrame;

import java.awt.*;

public class App {
    public static void main() {
        EventQueue.invokeLater(() -> new SwingMainFrame().setVisible(true));
    }
}