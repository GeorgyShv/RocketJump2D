package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 30;
    private int playerAction = RUNNING;
    private int playerDir = -1;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;

    // Проба поворота изображения за курсором
    private Point imageCenter;
    private int xDir = 0;
    private int yDir = 0;
    private double angle = 1;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 128, 128, null);

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

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if(attacking)
            playerAction = JUMPING;

        if(startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if(left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if(right && !left) {
            x += playerSpeed;
            moving = true;
        }

        if(up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if(down && !up) {
            y += playerSpeed;
            moving = true;
        }
    }

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/WholeAnimationScenaries.png");

        try {
            BufferedImage img = ImageIO.read(is);

            animations = new BufferedImage[7][7];

            for(int j = 0; j< animations.length; j++)
                for (int i=0; i < animations.length; i++)
                    animations[j][i] = img.getSubimage(i * 64, j * 64, 64, 64);

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

    public void resetDirBooleans() {
        left = false;
        right = false;
        down = false;
        up = false;
    }

    public void setAttack(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
