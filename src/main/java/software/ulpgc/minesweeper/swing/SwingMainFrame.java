package software.ulpgc.minesweeper.swing;
import software.ulpgc.minesweeper.architecture.control.Command;
import software.ulpgc.minesweeper.architecture.view.BoardDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class SwingMainFrame extends JFrame {
    private final Map<String, Command> commands;

    public SwingMainFrame(BoardDisplay display, SwingSettingsFrame settingsFrame, JLabel status, JButton faceButton, Map<String, Command> commands) throws HeadlessException {
        this.commands = commands;
        setTitle("Minesweeper Game");
        setLocationRelativeTo(settingsFrame);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());
        add((Component) display, BorderLayout.CENTER);
        add(new SwingTopPanel(faceButton), BorderLayout.NORTH);
        add(createStatusPanel(status), BorderLayout.SOUTH);
        setResizable(false);
        pack();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem settingsItem = new JMenuItem("Settings");
        settingsItem.addActionListener(_ -> {
            commands.get("settings").execute();
            this.dispose();
        });
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(_ -> System.exit(0));
        gameMenu.add(settingsItem);
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);
        return menuBar;
    }

    private Component createStatusPanel(JLabel gameStatus) {
        JPanel statusPanel = new JPanel();
        statusPanel.add(gameStatus, JLabel.CENTER);
        return statusPanel;
    }
}
