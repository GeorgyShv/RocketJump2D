package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    public GamePanel() {

        mouseInputs = new MouseInputs();
        addKeyListener(new KeyboardInputs());
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(230, 90, 10));
        g.fillRect(1, 10, 200, 50);

    }
}
