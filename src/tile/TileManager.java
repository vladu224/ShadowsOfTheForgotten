package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[12];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/LVL1.txt");

    }

    private BufferedImage loadTileImage(String path) throws IOException {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            throw new IOException("Cannot find tile image: " + path);
        }
        return ImageIO.read(is);
    }

    public void getTileImage() {
        try {
            // tile[1]
            tile[1] = new Tile();
            tile[1].image = loadTileImage("/tiles/tile_0_1.png");

            // LVL 1
            tile[2] = new Tile();
            tile[2].image = loadTileImage("/tiles/tile_0_2.png");
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = loadTileImage("/tiles/tile_0_3.png");

            // LVL 2
            tile[4] = new Tile();
            tile[4].image = loadTileImage("/tiles/tile_0_4.png");

            tile[5] = new Tile();
            tile[5].image = loadTileImage("/tiles/tile_0_5.png");
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = loadTileImage("/tiles/tile_0_6.png");
            tile[6].collision = true;

            // LVL 3
            tile[7] = new Tile();
            tile[7].image = loadTileImage("/tiles/tile_0_7.png");

            tile[8] = new Tile();
            tile[8].image = loadTileImage("/tiles/tile_0_8.png");

            tile[9] = new Tile();
            tile[9].image = loadTileImage("/tiles/tile_0_9.png");
            tile[9].collision = true;

            tile[10] = new Tile();
            tile[10].image = loadTileImage("/tiles/tile_0_10.png");

            tile[11] = new Tile();
            tile[11].image = loadTileImage("/tiles/tile_0_11.png");
            tile[11].collision = true;

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {

        try {

            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                String[] numbers = line.split(" ");

                while(col < gp.maxWorldCol) {

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
//                br.close();
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2) {

        int worldRow = 0;
        int worldCol = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

    }
}

