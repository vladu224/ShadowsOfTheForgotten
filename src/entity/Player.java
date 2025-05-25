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

    public String name = "";

    private BufferedImage climb1, climb2;
    private int climbCounter = 0;
    private int climbNum = 1;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;
    public int hasDiamond = 0;
    public int hasBook = 0;

    public int level = 1;

    private final int maxCoyoteTime = 6; // around 100 ms if running at 60 FPS

    private boolean jumpKeyReleased = true;
    private boolean wasOnLadderLastFrame = false;

    public int startX;
    public int startY;
    public boolean onLadder = false;

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
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 50;
        worldY = gp.tileSize * 250;
        preciseY = worldY;
        speed = 5;
        direction = "right";

        startX = worldX;
        startY = worldY;
    }
    public void resetPosition() {
        worldX = 50 * gp.tileSize;
        worldY = 250 * gp.tileSize;
        preciseY = worldY;
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

            climb1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Orin_Climb_1.png")));
            climb2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Orin_Climb_2.png")));

        } catch (IOException e) {
            System.out.println("Error loading images!");
            e.printStackTrace();
        }
    }

    public boolean isOnGround() {
        worldY += 2;
        collisionOn = false;
        gp.cChecker.checkTile(this);
        worldY -= 2;
        return collisionOn;
    }

    public void update() {

        boolean jumpInput = kh.spacePressed || ch.spacePressed;
        int coyoteTimer = 0;
        boolean canJump = !jumping && jumpKeyReleased && (isOnGround() || onLadder);

        // Ladder detection
        int col = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
        int row = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
        int currentTile = gp.tileM.mapTileNum[col][row];
        boolean isTouchingLadder = (currentTile == 10);

        // Check the tile below the player
        int tileBelow = gp.tileM.mapTileNum[col][row + 1];
        if (tileBelow == 11) {
            worldX = gp.tileSize * 9;
            worldY = gp.tileSize * 208;
            preciseY = worldY;
            velocityY = 0;
            jumping = false;
            System.out.println("☠ Touched spike below! Respawning...");
        }


        // Fix ladder logic: only leave ladder if not touching ladder OR player presses left/right AND player is on ground (not jumping)
        if (!isTouchingLadder || ((kh.leftPressed || kh.rightPressed) && !jumping)) {
            onLadder = false;
        }

        if (isTouchingLadder) {
            onLadder = true;
            jumping = false;

            // Ladder climbing logic (as you had it)
            if (kh.upPressed) {
                worldY -= 4;
                preciseY -= 4;
                collisionOn = false;
                gp.cChecker.checkTile(this);

                if (collisionOn) {
                    worldY += 4;
                    preciseY += 4;
                    jumping = true;
                    jumpStartY = preciseY;
                }

                // Simulate bounce when reaching top of ladder
                int tileAbove = gp.tileM.mapTileNum[col][row - 1];
                if (tileAbove != 10 && tileAbove != 9) {
                    int bounceHeight = 16; // You can tweak this for a stronger/smoother bounce
                    worldY -= bounceHeight;
                    preciseY -= bounceHeight;

                    onLadder = false;
                    wasOnLadderLastFrame = false;
                }

            } else if (kh.downPressed) {
                worldY += 4;
                preciseY += 4;
                int tileBelowX = (worldX + solidArea.x + solidArea.width ) / gp.tileSize;
                int tileBelowY = (worldY + solidArea.y + solidArea.height) / gp.tileSize;

                if (tileBelowY < gp.tileM.mapTileNum[0].length) {
                    int tileNum = gp.tileM.mapTileNum[tileBelowX][tileBelowY];
                    boolean tileBelowIsSolid = gp.tileM.tile[tileNum].collision;

                    if (tileBelowIsSolid) {
                        worldY -= 4;
                        preciseY -= 4;
                    }
                }
            }




            // Animate climb
            climbCounter++;
            if (climbCounter > 10) {
                climbNum = (climbNum == 1) ? 2 : 1;
                climbCounter = 0;
            }

            velocityY = 0;
        } else {
            // Not on ladder
            if (!jumping && !isOnGround()) {
                jumping = true;
                velocityY = 0;
                jumpStartY = preciseY;
            }
        }

        // Ladder exit collision resolution (keep as you had it)
        if (wasOnLadderLastFrame && !onLadder) {
            collisionOn = false;
            gp.cChecker.checkTile(this);

            if (collisionOn) {
                if (velocityY > 0) {
                    worldY -= gp.tileSize / 2;
                } else {
                    worldY += gp.tileSize / 2;
                }
                preciseY = worldY;
                velocityY = 0;
                jumping = false;
            }
        }

        wasOnLadderLastFrame = onLadder;

        // Jump initiation
        if (jumpInput && canJump) {
            jumping = true;
            jumpStartY = preciseY;
            velocityY = jumpSpeed;
            jumpFrameIndex = 0;
            jumpCounter = 0;
            jumpKeyReleased = false;

            if (onLadder) {
                onLadder = false;
            }
        }

        // HORIZONTAL MOVEMENT
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
            // Always revert horizontal position on collision, even mid-air
            worldX = oldX;
        }

        // VERTICAL MOVEMENT - JUMPING / FALLING
        if (jumping) {
            velocityY += gravity;
            double MAX_FALL_SPEED = 10.0;
            if (velocityY > MAX_FALL_SPEED) velocityY = (int) MAX_FALL_SPEED;

            int directionY = (int) Math.signum(velocityY);

            for (int i = 0; i < Math.abs(velocityY); i++) {
                worldY += directionY;
                preciseY = worldY;
                collisionOn = false;
                gp.cChecker.checkTile(this);

                if (collisionOn) {
                    if (directionY < 0) {
                        // Hitting ceiling
                        worldY -= directionY;
                        preciseY = worldY;
                        velocityY = 0;
                        break;
                    } else {
                        // Landing on ground
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

            jumpCounter++;
            if (jumpCounter % 6 == 0 && jumpFrameIndex < 6) {
                jumpFrameIndex++;
            }
        }

        // If player is not jumping but not on ground — start falling
        if (!jumping && !isOnGround()) {
            jumping = true;
            velocityY = 0;
            jumpStartY = preciseY;
        }

        // On ground fix Y and velocity
        if (!jumping && isOnGround()) {
            preciseY = worldY;
            velocityY = 0;
        }

        // Animation
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        if (!kh.spacePressed && !ch.spacePressed) {
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
                    gp.obj[i].collected = true;
                    gp.dbManager.saveSingleObject(gp.obj[i]);
                    gp.obj[i] = null;
                    break;
                case "Diamond":
                    gp.playSE(3);
                    hasDiamond++;
                    gp.obj[i].collected = true;
                    gp.dbManager.saveSingleObject(gp.obj[i]);
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
                            worldX = gp.tileSize * 50;
                            worldY = gp.tileSize * 250;
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
                case "Final_Book":
                    if(hasKey == 10 && hasDiamond == 10) {
                        gp.playSE(4);
                        //hasBook++;
                        hasKey = 0; hasDiamond = 0;
                        gp.obj[i] = null;
                        gp.ui.gameFinished = true;
                        gp.dbManager.saveToLeaderboard(gp.player, gp.ui);
                        gp.stopMusic();
                        gp.playSE(6);
                    }
                    else if(hasKey == 10 && hasDiamond < 10) {
                        gp.ui.showMessage((10 - hasDiamond) + " diamonds left to unlock the final book");
                    }
                    else if(hasDiamond == 10 && hasKey < 10) {
                        gp.ui.showMessage((10 - hasKey) + " keys left to unlock the final book");
                    }
                    else {
                        gp.ui.showMessage((10 - hasKey) + " keys and " + (10 - hasDiamond) + " diamonds left to unlock the final book");
                    }
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {

        int playerWidth = 50;
        int playerHeight = 50;

        BufferedImage image = null;

        if(onLadder) {
            image = (climbNum == 1) ? climb1 : climb2;
        }
        else if (jumping) {
            int index = Math.min(jumpFrameIndex, 5);
            image = (Objects.equals(direction, "right")) ? jumpSprites0[index] : jumpSprites1[index];
        } else {
            if (Objects.equals(direction, "right")) {
                image = (spriteNum == 1) ? right2 : right3;
            } else {
                image = (spriteNum == 1) ? left2 : left3;
            }
        }


        int screenYOffset = (int) (worldY - preciseY);

        //g2.setColor(Color.BLUE);
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y - screenYOffset, solidArea.width, solidArea.height);

        g2.drawImage(image, screenX, screenY - screenYOffset, playerWidth, playerHeight, null);
    }
}