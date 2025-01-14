package software.ulpgc.minesweeper;

import software.ulpgc.minesweeper.swing.SwingSettingsFrame;

import java.awt.*;

public class App {
    public static void main() {
        EventQueue.invokeLater(() -> new SwingSettingsFrame().setVisible(true));
    }
}