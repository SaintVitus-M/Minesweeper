package software.ulpgc.minesweeper.architecture.control;

import software.ulpgc.minesweeper.architecture.view.BoardDisplay;
import software.ulpgc.minesweeper.swing.SwingSettingsFrame;

public class SetNewGameCommand implements Command {
    private final BoardDisplay ContentDisplay;
    private final SwingSettingsFrame settingsDialog;

    public SetNewGameCommand(BoardDisplay display, SwingSettingsFrame dialog) {
        this.ContentDisplay = display;
        this.settingsDialog = dialog;
    }

    @Override
    public void execute() {
        ContentDisplay.showNewGame(settingsDialog.getSelectedGameSetting());
    }
}
