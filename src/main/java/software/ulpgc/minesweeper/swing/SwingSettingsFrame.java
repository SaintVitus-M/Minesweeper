package software.ulpgc.minesweeper.swing;

import software.ulpgc.minesweeper.architecture.control.Command;
import software.ulpgc.minesweeper.architecture.control.OpenSettingsCommand;
import software.ulpgc.minesweeper.architecture.control.SetNewGameCommand;
import software.ulpgc.minesweeper.architecture.view.BoardDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SwingSettingsFrame extends JFrame implements ActionListener {
    private final Color BACKGROUND_COLOR =  new Color(0xF59F0F);

    private final ButtonGroup buttonGroup;
    private String selectedGameSetting = "";
    private final JLabel status = new JLabel("");
    private final JButton faceButton = createFaceButton();
    private final BoardDisplay boardDisplay = createBoardDisplay();
    private final Map<String, Command> commands;

    private BoardDisplay createBoardDisplay() {
        return new SwingBoardDisplay(status, faceButton);
    }

    private JButton createFaceButton() {
        JButton faceButton = new JButton(new ImageIcon("src/main/resources/game/smile.png"));
        faceButton.setPreferredSize(new Dimension(24, 24));
        faceButton.setBorderPainted(false);
        faceButton.setFocusPainted(false);
        faceButton.setContentAreaFilled(false);
        faceButton.addActionListener(_ -> commands.get("play").execute());
        return faceButton;
    }

    private Command createPlayCommand() {
        return new SetNewGameCommand(boardDisplay, this);
    }

    private Command createOpenSettingsCommand() {
        return new OpenSettingsCommand(this);
    }

    public SwingSettingsFrame() {
        this.commands = new HashMap<>();
        commands.put("play" ,createPlayCommand());
        commands.put("settings", createOpenSettingsCommand());
        this.buttonGroup = new ButtonGroup();
        setLayout(new GridLayout(5, 1, 20, 0));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(createLogo());
        add(createEasyDifficultyPanel());
        add(createMediumDifficultyPanel());
        add(createHardDifficultyPanel());
        add(createPlayButton());
        setResizable(false);
        pack();
    }

    private Component createLogo() {
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(BACKGROUND_COLOR);
        logoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        logoPanel.add(new JLabel(new ImageIcon("src/main/resources/mine-logo.png")));
        return logoPanel;
    }

    private Component createEasyDifficultyPanel() {
        JPanel easyPanel = new JPanel();
        easyPanel.setLayout(new BoxLayout(easyPanel, BoxLayout.Y_AXIS));
        easyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JRadioButton easyModeButton = new JRadioButton("Easy mode");
        easyModeButton.setBackground(BACKGROUND_COLOR);
        easyModeButton.addActionListener(this);
        easyModeButton.setActionCommand("easy");
        buttonGroup.add(easyModeButton);
        JLabel easyRowsNum = new JLabel("Rows: 8");
        JLabel easyColsNum = new JLabel("Columns: 8");
        JLabel easyMinesNum = new JLabel("Mines: 10");
        easyPanel.add(easyModeButton);
        easyPanel.add(easyRowsNum);
        easyPanel.add(easyColsNum);
        easyPanel.add(easyMinesNum);
        easyPanel.setBackground(BACKGROUND_COLOR);
        return easyPanel;
    }

    private Component createMediumDifficultyPanel() {
        JPanel mediumPanel = new JPanel();
        mediumPanel.setLayout(new BoxLayout(mediumPanel, BoxLayout.Y_AXIS));
        mediumPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JRadioButton mediumModeButton = new JRadioButton("Medium mode");
        mediumModeButton.setBackground(BACKGROUND_COLOR);
        mediumModeButton.addActionListener(this);
        mediumModeButton.setActionCommand("medium");
        buttonGroup.add(mediumModeButton);
        JLabel mediumRowsNum = new JLabel("Rows: 16");
        JLabel mediumColsNum = new JLabel("Columns: 16");
        JLabel mediumMinesNum = new JLabel("Mines: 40");
        mediumPanel.add(mediumModeButton);
        mediumPanel.add(mediumRowsNum);
        mediumPanel.add(mediumColsNum);
        mediumPanel.add(mediumMinesNum);
        mediumPanel.setBackground(BACKGROUND_COLOR);
        return mediumPanel;
    }

    private Component createHardDifficultyPanel() {
        JPanel hardPanel = new JPanel();
        hardPanel.setLayout(new BoxLayout(hardPanel, BoxLayout.Y_AXIS));
        hardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JRadioButton hardModeButton = new JRadioButton("Hard mode");
        hardModeButton.setBackground(BACKGROUND_COLOR);
        hardModeButton.addActionListener(this);
        hardModeButton.setActionCommand("hard");
        buttonGroup.add(hardModeButton);
        JLabel hardRowsNum = new JLabel("Rows: 30");
        JLabel hardColsNum = new JLabel("Columns: 30");
        JLabel hardMinesNum = new JLabel("Mines: 150");
        hardPanel.add(hardModeButton);
        hardPanel.add(hardRowsNum);
        hardPanel.add(hardColsNum);
        hardPanel.add(hardMinesNum);
        hardPanel.setBackground(BACKGROUND_COLOR);
        return hardPanel;
    }

    private Component createPlayButton() {
        JButton playButton = new JButton("Play!");
        playButton.addActionListener(_ -> {
            commands.get("play").execute();
            dispose();
            new SwingMainFrame(boardDisplay, this, status, faceButton, commands).setVisible(true);
        });
        return playButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectedGameSetting = e.getActionCommand();
    }

    public String getSelectedGameSetting() {
        return selectedGameSetting;
    }
}
