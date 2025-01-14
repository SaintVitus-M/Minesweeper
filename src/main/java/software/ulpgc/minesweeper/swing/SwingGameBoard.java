package software.ulpgc.minesweeper.swing;

import software.ulpgc.minesweeper.architecture.io.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class SwingGameBoard extends JPanel {

    private final int BLOCK_SIZE = 15;
    private final int COVER_FOR_BLOCK = 10;
    private final int MARK_FOR_BLOCK = 10;
    private final int EMPTY_BLOCK = 0;
    private final int MINE_BLOCK = 9;
    private final int COVERED_MINE_BLOCK = MINE_BLOCK + COVER_FOR_BLOCK;
    private final int MARKED_MINE_BLOCK = COVERED_MINE_BLOCK + MARK_FOR_BLOCK;
    private final Icon DEAD_ICON = new ImageIcon("src/main/resources/game/dead.png");
    private final Icon SMILE_ICON = new ImageIcon("src/main/resources/game/smile.png");

    private final int nRows;
    private final int nCols;

    private final int[] fields;
    private boolean inGame;
    private final int numMines;
    private int minesLeft;
    private Image[] img;

    private final int allBlocks;
    private final JLabel statusbar;
    private final JButton faceButton;

    public SwingGameBoard(int nRows, int nCols, int numMines, JLabel statusbar, JButton faceButton) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.numMines = numMines;
        this.statusbar = statusbar;
        this.faceButton = faceButton;
        this.allBlocks = nRows * nRows;
        this.fields = new int[allBlocks];
        initBoard();
    }

    private void initBoard() {
        int boardWidth = nCols * BLOCK_SIZE + 1;
        int boardHeight = nCols * BLOCK_SIZE + 1;
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        this.img = new Image[13];       // Image array for the 13 images located in the resources directory.
        for (int i = 0; i < img.length; i++) {
            img[i] = ImageLoader.getImage(i);
        }
        faceButton.setIcon(SMILE_ICON);
        addMouseListener(createMouseListener());
        setNewGame();
    }

    private void setNewGame() {
        var random = new Random();

        inGame = true;
        minesLeft = numMines;

        for (int i = 0; i < allBlocks; i++) {
            fields[i] = COVER_FOR_BLOCK;
        }

        statusbar.setText(Integer.toString(minesLeft));

        setAllMines(random);
    }

    private void setAllMines(Random random) {
        int i = 0;
        while (i < numMines) {

            int minePos = (int) (allBlocks * random.nextDouble());

            if ((minePos < allBlocks)
                    && (fields[minePos] != COVERED_MINE_BLOCK)) {

                int current_col = minePos % nCols;
                fields[minePos] = COVERED_MINE_BLOCK;
                i++;

                if (current_col > 0) {
                    addMineCountOnLeftSide(minePos);
                }

                addMineCountOnCenterSide(minePos);

                if (current_col < (nCols - 1)) {
                    addMineCountOnRightSide(minePos);
                }
            }
        }
    }

    private void addMineCountOnRightSide(int minePos) {
        int block = minePos - nCols + 1;
        if (block >= 0) {
            addMineCount(block);
        }
        block = minePos + nCols + 1;
        if (block < allBlocks) {
            addMineCount(block);
        }
        block = minePos + 1;
        if (block < allBlocks) {
            addMineCount(block);
        }
    }

    private void addMineCountOnCenterSide(int minePos) {
        int block = minePos - nCols;
        if (block >= 0) {
            addMineCount(block);
        }

        block = minePos + nCols;
        if (block < allBlocks) {
            addMineCount(block);
        }
    }

    private void addMineCountOnLeftSide(int minePos) {
        int block = minePos - 1 - nCols;
        if (block >= 0) {
            addMineCount(block);
        }
        block = minePos - 1;
        if (block >= 0) {
            addMineCount(block);
        }

        block = minePos + nCols - 1;
        if (block < allBlocks) {
            addMineCount(block);
        }
    }

    private void addMineCount(int block) {
        if (fields[block] != COVERED_MINE_BLOCK) {
            fields[block] += 1;
        }
    }

    private void findEmptyBlocks(int position) {
        int current_col = position % nCols;

        if (current_col > 0) {
            searchLeftSide(position);
        }

        searchCenterSide(position);

        if (current_col < (nCols - 1)) {
            searchRightSide(position);
        }
    }

    private void searchRightSide(int position) {
        int block = position - nCols + 1;
        if (block >= 0) {
            uncoverBlock(block);
        }

        block = position + nCols + 1;
        if (block < allBlocks) {
            uncoverBlock(block);
        }

        block = position + 1;
        if (block < allBlocks) {
            uncoverBlock(block);
        }
    }

    private void searchCenterSide(int position) {
        int block = position - nCols;
        if (block >= 0) {
            uncoverBlock(block);
        }

        block = position + nCols;
        if (block < allBlocks) {
            uncoverBlock(block);
        }
    }

    private void searchLeftSide(int position) {
        int block = position - nCols - 1;
        if (block >= 0) {
            uncoverBlock(block);
        }

        block = position - 1;
        if (block >= 0) {
            uncoverBlock(block);
        }

        block = position + nCols - 1;
        if (block < allBlocks) {
            uncoverBlock(block);
        }
    }

    private void uncoverBlock(int block) {
        if (fields[block] > MINE_BLOCK) {
            fields[block] -= COVER_FOR_BLOCK;
            if (fields[block] == EMPTY_BLOCK) {
                findEmptyBlocks(block);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int uncover = 0;
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                int cell = fields[(i * nCols) + j];
                if (inGame && cell == MINE_BLOCK) {
                    inGame = false;
                }
                int drawCover = 10;
                int drawMark = 11;
                if (!inGame) {
                    if (cell == COVERED_MINE_BLOCK) {
                        cell = 9;
                    } else if (cell == MARKED_MINE_BLOCK) {
                        cell = drawMark;
                    } else if (cell > COVERED_MINE_BLOCK) {
                        cell = 12;
                    } else if (cell > MINE_BLOCK) {
                        cell = drawCover;
                    }
                } else {
                    if (cell > COVERED_MINE_BLOCK) {
                        cell = drawMark;
                    } else if (cell > MINE_BLOCK) {
                        cell = drawCover;
                        uncover++;
                    }
                }
                g.drawImage(img[cell], (j * BLOCK_SIZE),
                        (i * BLOCK_SIZE), this);
            }
        }
        if (uncover == 0 && inGame) {
            inGame = false;
            statusbar.setText("Game won");

        } else if (!inGame) {
            statusbar.setText("Game lost");
            faceButton.setIcon(DEAD_ICON);
        }
    }

    private MouseListener createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (inGame) {
                    int x = e.getX();
                    int y = e.getY();

                    boolean doRepaint = false;

                    int blockRow = y / BLOCK_SIZE;
                    int blockCol = x / BLOCK_SIZE;

                    int blockPos = (blockRow * nCols) + blockCol;

                    if((x < nCols * BLOCK_SIZE) && (y < nRows * BLOCK_SIZE)) {
                        if(e.getButton() == MouseEvent.BUTTON3) {
                            if(fields[blockPos] > MINE_BLOCK) {
                                doRepaint = true;
                                if(fields[blockPos] <= COVERED_MINE_BLOCK) {
                                    if(minesLeft > 0) {
                                        fields[blockPos] += MARK_FOR_BLOCK;
                                        minesLeft--;
                                        statusbar.setText(Integer.toString(minesLeft));
                                    } else {
                                        statusbar.setText("No flags left!");
                                    }
                                } else {
                                    fields[blockPos] -= MARK_FOR_BLOCK;
                                    minesLeft++;
                                    statusbar.setText(Integer.toString(minesLeft));
                                }
                            }
                        } else {
                            if(fields[blockPos] > COVERED_MINE_BLOCK) return;
                            if((fields[blockPos] > MINE_BLOCK) && (fields[blockPos] < MARKED_MINE_BLOCK)) {
                                fields[blockPos] -= COVER_FOR_BLOCK;
                                doRepaint = true;
                                if(fields[blockPos] == MINE_BLOCK) {
                                    inGame = false;
                                }
                                if(fields[blockPos] == EMPTY_BLOCK) {
                                    findEmptyBlocks(blockPos);
                                }
                            }
                        }
                        if(doRepaint) {
                            repaint();
                        }
                    }
                }
            }
        };
    }
}
