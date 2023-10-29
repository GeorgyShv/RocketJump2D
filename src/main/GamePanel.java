package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = RUNNING;
    private int playerDir = -1;
    private boolean moving = false;
    private Point imageCenter;
    private int xDir = 0;
    private int yDir = 0;
    private double angle = 1;

    public GamePanel() {

        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimations();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    private void loadAnimations() {
        animations = new BufferedImage[7][7];

        for(int j = 0; j< animations.length; j++)
            for (int i=0; i < animations.length; i++) {
                animations[j][i] = img.getSubimage(i*64, j*64, 64, 64);
            }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/WholeAnimationScenaries.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;

    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setGunDir(int x, int y) {
        this.xDir = x;
        this.yDir = y;
    }

    private void updateAnimationTick() {


        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction))
                aniIndex = 0;
        }

    }

    private void setAnimation() {
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }

    private void updatePos() {
        if (moving) {
            switch(playerDir) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateAnimationTick();
        setAnimation();
        updatePos();
        g.drawImage(animations[playerAction][aniIndex], (int) xDelta, (int) yDelta, 128, 128, null);

        /*
        imageCenter = new Point(32+128, 32+128);
        double dx = xDir - imageCenter.x;
        double dy = yDir - imageCenter.y;
        angle = Math.toDegrees(Math.atan2(dy,dx));
         */

        //Graphics2D g2d = (Graphics2D)g;
        //AffineTransform rotateImg = new AffineTransform();
        //rotateImg.rotate(Math.toRadians(angle), imageCenter.x, imageCenter.y);
        //g2d.setTransform(rotateImg);
        //g2d.drawImage(img, (int) xDelta, (int) yDelta, 128, 128, null);
        //g2d.dispose();

    }
}
