package software.ulpgc.minesweeper.architecture.io;

import javax.swing.*;
import java.awt.*;

public class ImageLoader {
    public static final String PATH = "src/main/resources/game/";

    public static Image getImage(int i) {
        return new ImageIcon(PATH + i + ".png").getImage();
    }
}
