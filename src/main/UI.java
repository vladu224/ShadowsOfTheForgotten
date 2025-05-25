package main;

import object.OBJ_Diamond;
import object.OBJ_Key;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font arial_20, arial_40, arial_100B;
    BufferedImage keyImage;
    BufferedImage diamondImage;
    BufferedImage clock;
    Graphics2D g2;

    //private boolean gameFinished = false;
    private boolean redirectScheduled = false;

    //    private long gameFinishedStartTime = 0;
//    private boolean timerStarted = false;
    private boolean pauseMenuVisible = false;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;

    public float playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {

        this.gp = gp;

        try {

            clock = ImageIO.read(getClass().getResourceAsStream("/objects/Clock.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }

        arial_20 = new Font("Arial", Font.PLAIN, 20);
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_100B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        OBJ_Diamond diamond = new OBJ_Diamond();
        keyImage = key.image;
        diamondImage = diamond.image;
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gameFinished) {

            // Start the timer only once
            gameFinished = true;

            // Draw your congratulations messages (your existing code)
            g2.setFont(arial_40);
            g2.setColor(Color.yellow);
            String text = "You got to the final book!";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth / 2 - textLength / 2;
            int y = gp.screenHeight / 2 - gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Your time is: " + dFormat.format(playTime) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + gp.tileSize * 4;
            g2.drawString(text, x, y);

            g2.setFont(arial_100B);
            g2.setColor(Color.white);
            text = "CONGRATULATIONS!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + gp.tileSize * 2;
            g2.drawString(text, x, y);

            if (!redirectScheduled) {
                redirectScheduled = true;

                // Schedule going back to main menu after 10 seconds
                Timer timer = new Timer(10000, e -> {
                    Window window = SwingUtilities.getWindowAncestor(gp);
                    if (window instanceof JFrame) {
                        window.dispose(); // close game window
                    }

                    SwingUtilities.invokeLater(() -> new GameMenu().setVisible(true));
                });
                timer.setRepeats(false);
                timer.start();
            }
        }

        else {

            if(gp.gameState == gp.playState) {

                g2.setFont(arial_20);
                g2.setColor(Color.white);
                g2.drawImage(keyImage, gp.tileSize / 4, gp.tileSize / 4, gp.tileSize, gp.tileSize,null);
                g2.drawString("  " + gp.player.hasKey + " / 10", 50, 45);
                g2.drawImage(diamondImage, gp.tileSize / 4, gp.tileSize + gp.tileSize / 4, gp.tileSize, gp.tileSize,null);
                g2.drawString("  " + gp.player.hasDiamond + " / 10", 50, 90);

                // MESSAGE
                if(messageOn) {

                    g2.setFont(g2.getFont().deriveFont(20F));
                    g2.drawString(message, 6 * gp.tileSize, 4 * gp.tileSize);

                    messageCounter++;

                    if(messageCounter > 300) {
                        messageCounter = 0;
                        messageOn = false;
                    }
                }

                // TIME
                playTime += (double) 1/60;
                g2.drawImage(clock, 15 * gp.tileSize, gp.tileSize / 4, gp.tileSize, gp.tileSize,null);
                g2.drawString(": " + dFormat.format(playTime) , gp.tileSize * 16, 45);

            }
            if(gp.gameState == gp.pauseState) {
                drawPauseScreen();


            }

        }
    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";

        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }
    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;

    }

    public float getPlayTime() {
        return playTime;
    }

    public void setPlayTime(float playTime) {
        this.playTime = playTime;
    }
}



