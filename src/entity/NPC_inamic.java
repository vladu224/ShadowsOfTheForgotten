package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NPC_inamic extends Entity {

    GamePanel gp;
    int directionCounter = 0; // contor pentru schimbarea direcției

    public NPC_inamic(GamePanel gp, int worldX, int worldY) {

        this.gp = gp;
        this.worldX = worldX;
        this.worldY = worldY;

        direction = "left";
        speed = 0;

        solidArea = new Rectangle(8, 16, 48, 48);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        spriteNum = 1;
        getEnemyImage();
    }

    private void getEnemyImage() {
        try {
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/inamic_right_1.png")));

            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/inamic_right_2.png")));

            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/inamic_left_1.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/inamic_left_2.png")));
        } catch (IOException e) {
            System.out.println("Error loading enemy images!");
            e.printStackTrace();
        }
    }

    public void update() {
        // Animație
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        // Mișcare reală
        if (direction.equals("left")) {
            worldX -= speed;
        } else if (direction.equals("right")) {
            worldX += speed;
        }

        // Schimbare direcție vizuală la interval
        directionCounter++;
        if (directionCounter > 120) {
            direction = direction.equals("left") ? "right" : "left";
            directionCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (direction.equals("left")) {
            image = (spriteNum == 1) ? left2 : left3;
        } else if (direction.equals("right")) {
            image = (spriteNum == 1) ? right2 : right3;
        }

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        int sizeMultiplier = 2;
        g2.drawImage(image, screenX, screenY, gp.tileSize * sizeMultiplier, gp.tileSize * sizeMultiplier, null);

        // DEBUG: draw hitbox

//        g2.setColor(Color.RED);
//        g2.setStroke(new BasicStroke(2));
//
//        int hitboxX = screenX + solidArea.x;
//        int hitboxY = screenY + solidArea.y;
//        g2.drawRect(hitboxX, hitboxY, solidArea.width * 2 - 3 * solidArea.x / 4, solidArea.height * 2 - solidArea.height / 2);

    }
}