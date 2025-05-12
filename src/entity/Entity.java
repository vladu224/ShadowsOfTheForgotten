package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage right2, right3, left2, left3;
    public String direction;

    public BufferedImage[] jumpSprites0 = new BufferedImage[6];
    public BufferedImage[] jumpSprites1 = new BufferedImage[6];

    public float velocityY = 0;
    public final float gravity = 0.5f;
    public final float jumpSpeed = -13.8f;
    public final int maxJumpHeight = 144;
    public float jumpStartY;
    public float preciseY;

    boolean jumping = false;
    int jumpFrameIndex = 0;
    int jumpCounter = 0;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}