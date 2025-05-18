package entity;

import main.ControllerHandler;
import main.GamePanel;
import main.KeyHandler;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler kh;
    ControllerHandler ch;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;
    public int hasDiamond = 0;
    public int hasBook = 0;

    private int coyoteTimer = 0;
    private final int maxCoyoteTime = 6; // around 100 ms if running at 60 FPS

    private boolean jumpKeyReleased = true;

    public Player(GamePanel gp, KeyHandler kh, ControllerHandler ch) {
        this.gp = gp;
        this.kh = kh;
        this.ch = ch;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 25;
        solidArea.y = 20;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 5;
        solidArea.height = 30;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 9;
        worldY = gp.tileSize * 63;
        preciseY = worldY;
        speed = 5;
        direction = "right";
    }

    public void getPlayerImage() {
        try {
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Orin_Right_2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Orin_Right_3.png")));

            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Orin_Left_2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Orin_Left_3.png")));

            int cnt = 0;
            for (int i = 0; i < 6; i += 2) {
                jumpSprites0[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Orin_Jump_R" + cnt + ".png")));
                jumpSprites0[i + 1] = jumpSprites0[i];
                cnt++;
            }
            cnt = 0;
            for (int i = 0; i < 6; i += 2) {
                jumpSprites1[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Orin_Jump_L" + cnt + ".png")));
                jumpSprites1[i + 1] = jumpSprites1[i];
                cnt++;
            }
        } catch (IOException e) {
            System.out.println("Error loading images!");
            e.printStackTrace();
        }
    }

    public boolean isOnGround() {
        worldY += 1;
        collisionOn = false;
        gp.cChecker.checkTile(this);
        worldY -= 1;
        return collisionOn;
    }

    public void update() {

        boolean jumpInput = kh.spacePressed || ch.spacePressed;
        boolean canJump = !jumping && jumpKeyReleased && (isOnGround() || coyoteTimer > 0);

        if (jumpInput && canJump) {
            jumping = true;
            jumpStartY = preciseY;
            velocityY = jumpSpeed;
            jumpFrameIndex = 0;
            jumpCounter = 0;
            jumpKeyReleased = false;
        }

        // Vertical movement (jumping or falling)
        if (jumping) {
            velocityY += gravity;
            double MAX_FALL_SPEED = 10.0;
            if (velocityY > MAX_FALL_SPEED) velocityY = (int) MAX_FALL_SPEED;

            double remaining = velocityY;
            int directionY = (int) Math.signum(remaining);

            for (int i = 0; i < Math.abs(remaining); i++) {
                worldY += directionY;
                preciseY = worldY;
                collisionOn = false;
                gp.cChecker.checkTile(this);

                if (collisionOn) {
                    if (directionY < 0) { // hitting ceiling
                        worldY -= directionY;
                        preciseY = worldY;
                        velocityY = 0;
                        break;
                    } else { // landing
                        worldY -= directionY;
                        preciseY = worldY;
                        velocityY = 0;
                        jumping = false;
                        jumpFrameIndex = 0;
                        jumpCounter = 0;
                        break;
                    }
                }
            }

            // Animate jump
            jumpCounter++;
            if (jumpCounter % 6 == 0 && jumpFrameIndex < 6) {
                jumpFrameIndex++;
            }
        }

        // Horizontal movement
        int oldX = worldX;
        if (kh.leftPressed || ch.leftPressed) {
            worldX -= speed;
            direction = "left";
        } else if (kh.rightPressed || ch.rightPressed) {
            worldX += speed;
            direction = "right";
        }

        // Check horizontal collision
        collisionOn = false;
        gp.cChecker.checkTile(this);

        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        if (collisionOn) {
            worldX = oldX;
        }

        if (!jumping && !isOnGround()) {
            jumping = true;
            velocityY = 0;
            jumpStartY = preciseY;
        }

        if (!jumping && isOnGround()) {
            preciseY = worldY;
            velocityY = 0;
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        if (!kh.spacePressed || !ch.spacePressed) {
            jumpKeyReleased = true;
        }
    }

    public void pickUpObject(int i) {

        if(i != 999) {

            //gp.obj[i] = null;
            String objectName = gp.obj[i].name;

            switch(objectName) {

                case "Key":
                    gp.playSE(2);
                    hasKey++;
                    gp.obj[i] = null;
                    break;
                case "Diamond":
                    gp.playSE(3);
                    hasDiamond++;
                    gp.obj[i] = null;
                    break;
                case "Book":
                    gp.playSE(4);
                    if(hasBook == 0) {
                        if(hasDiamond == 10 && hasKey == 10) {
                            gp.playSE(4);
                            hasBook++;
                            hasKey = 0; hasDiamond = 0;
                            gp.obj[i] = null;
                            worldX = gp.tileSize * 9;
                            worldY = gp.tileSize * 106;

                        }
                        else if(hasKey == 10 && hasDiamond < 10) {
                            gp.ui.showMessage((10 - hasDiamond) + " diamonds left to unlock book");
                        }
                        else if(hasDiamond == 10 && hasKey < 10) {
                            gp.ui.showMessage((10 - hasKey) + " keys left to unlock book");
                        }
                        else {
                            gp.ui.showMessage((10 - hasKey) + " keys and " + (10 - hasDiamond) + " diamonds left to unlock book");
                        }
                    }
                    if(hasBook == 1) {
                        if(hasDiamond == 10 && hasKey == 10) {
                            gp.playSE(4);
                            hasBook++;
                            hasKey = 0; hasDiamond = 0;
                            gp.obj[i] = null;
                            gp.ui.gameFinished = true;
                            gp.stopMusic();
                            gp.playSE(6);
                        }
                        else if(hasKey == 10 && hasDiamond < 10) {
                            gp.ui.showMessage((10 - hasDiamond) + " diamonds left to unlock book");
                        }
                        else if(hasDiamond == 10 && hasKey < 10) {
                            gp.ui.showMessage((10 - hasKey) + " keys left to unlock book");
                        }
                        else {
                            gp.ui.showMessage((10 - hasKey) + " keys and " + (10 - hasDiamond) + " diamonds left to unlock book");
                        }
                    }
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        int playerWidth = 50;
        int playerHeight = 50;

        BufferedImage image = null;

        if (jumping) {
            int index = Math.min(jumpFrameIndex, 5);
            image = (Objects.equals(direction, "right")) ? jumpSprites0[index] : jumpSprites1[index];
        } else {
            if (Objects.equals(direction, "right")) {
                image = (spriteNum == 1) ? right2 : right3;
            } else {
                image = (spriteNum == 1) ? left2 : left3;
            }
        }

        //int screenYOffset = 0;
        //if (jumping) {
        int screenYOffset = (int) (worldY - preciseY);
        //}

        //g2.setColor(Color.BLUE);
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y - screenYOffset, solidArea.width, solidArea.height);

        g2.drawImage(image, screenX, screenY - screenYOffset, playerWidth, playerHeight, null);
    }
}