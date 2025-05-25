package main;

import entity.NPC_inamic;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS

    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile

    public final int maxScreenCol = 18;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol = 127;
    public final int maxWorldRow = 300;

//    public final int worldWidth = tileSize * maxWorldCol;
//    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    KeyHandler kh = new KeyHandler(this);
    ControllerHandler ch = new ControllerHandler();
    Sound music = new Sound();
    Sound se = new Sound();
    public DatabaseManager dbManager = new DatabaseManager();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, kh, ch);
    public SuperObject obj[] = new SuperObject[63];
    public NPC_inamic[] npc = new NPC_inamic[50];
    public EnemySetter enemySetter = new EnemySetter(this);


    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    private PauseMenu pauseMenu = null;

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addKeyListener(kh);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        enemySetter.setEnemies();
        player.setDefaultValues();

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                System.out.println("Object " + i + " (" + obj[i].name + ") collected: " + obj[i].collected);
            }
        }

        playMusic(1);
        gameState = playState;
    }
    public void loadGame() {
        aSetter.setObject();  // Initialize all objects first
        enemySetter.setEnemies();
        dbManager.loadPlayer(player, ui);  // Load player data (position, stats, etc)
        dbManager.loadObjects(List.of(obj));  // Load object collected flags

        gameState = playState;
        playMusic(1);
    }
    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        double lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (long) (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        if (kh.escPressed) {
            kh.escPressed = false; // reset flag

            if (gameState == playState) {
                // Pause game and show pause menu
                gameState = pauseState;

                if (pauseMenu == null) {
                    pauseMenu = new PauseMenu((JFrame) SwingUtilities.getWindowAncestor(this), this);
                }
                SwingUtilities.invokeLater(() -> {
                    pauseMenu.setVisible(true);
                });
            }
            else if (gameState == pauseState) {
                // Resume game and hide pause menu
                gameState = playState;

                if (pauseMenu != null) {
                    SwingUtilities.invokeLater(() -> {
                        pauseMenu.setVisible(false);
                        pauseMenu.dispose();
                        pauseMenu = null;
                    });
                }
            }
        }

        if (gameState == playState) {
            ch.update();
            player.update();


            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
            checkPlayerEnemyCollision();
        }
        // else if paused, no game updates to logic
    }
    public void checkPlayerEnemyCollision() {
        Rectangle playerHitbox = new Rectangle(
                player.worldX + player.solidArea.x,
                player.worldY + player.solidArea.y,
                player.solidArea.width,
                player.solidArea.height
        );

        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                Rectangle enemyHitbox = new Rectangle(
                        npc[i].worldX + npc[i].solidArea.x,
                        npc[i].worldY + npc[i].solidArea.y,
                        npc[i].solidArea.width * 2 - npc[i].solidArea.width * 3/4,
                        npc[i].solidArea.height * 2 - npc[i].solidArea.height / 2
                );

                if (playerHitbox.intersects(enemyHitbox)) {
                    System.out.println("Coliziune cu inamic! Resetare poziție.");
                    player.resetPosition();

                }
            }
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // TILE
        tileM.draw(g2);

        // OBJECT
        for(int i = 0; i < obj.length; ++i) {
            if(obj[i] != null && !obj[i].collected) {
                obj[i].draw(g2, this);
            }
            else if(obj[i] != null && obj[i].collected) {
                obj[i] = null;
            }

        }

        // PLAYER
        player.draw(g2);

        // INAMICI
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }

        // UI
        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {

        music.stop();
    }
    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }
}
