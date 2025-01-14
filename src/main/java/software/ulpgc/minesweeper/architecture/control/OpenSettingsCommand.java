package software.ulpgc.minesweeper.architecture.control;

import software.ulpgc.minesweeper.swing.SwingSettingsFrame;

public class OpenSettingsCommand implements Command {
    private final SwingSettingsFrame settingsFrame;

    public OpenSettingsCommand(SwingSettingsFrame settingsFrame) {
        this.settingsFrame = settingsFrame;
    }

    @Override
    public void execute() {
        settingsFrame.setVisible(true);
    }
}
