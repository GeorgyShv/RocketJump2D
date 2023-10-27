package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public GamePanel() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 1, 0));
        g.fillRect(1, 10, 200, 50);
    }
}
