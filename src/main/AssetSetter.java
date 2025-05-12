package main;

import object.OBJ_Book;
import object.OBJ_Diamond;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // KEYS

        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 14 * gp.tileSize;
        gp.obj[0].worldY = 39 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 90 * gp.tileSize;
        gp.obj[1].worldY = 50 * gp.tileSize;

        gp.obj[2] = new OBJ_Key();
        gp.obj[2].worldX = 66 * gp.tileSize;
        gp.obj[2].worldY = 39 * gp.tileSize;

        gp.obj[3] = new OBJ_Key();
        gp.obj[3].worldX = 29 * gp.tileSize;
        gp.obj[3].worldY = 46 * gp.tileSize;

        gp.obj[4] = new OBJ_Key();
        gp.obj[4].worldX = 78 * gp.tileSize;
        gp.obj[4].worldY = 58 * gp.tileSize;

        gp.obj[5] = new OBJ_Key();
        gp.obj[5].worldX = 39 * gp.tileSize;
        gp.obj[5].worldY = 62 * gp.tileSize;

        gp.obj[6] = new OBJ_Key();
        gp.obj[6].worldX = 11 * gp.tileSize;
        gp.obj[6].worldY = 62 * gp.tileSize;

        gp.obj[7] = new OBJ_Key();
        gp.obj[7].worldX = 74 * gp.tileSize;
        gp.obj[7].worldY = 47 * gp.tileSize;

        gp.obj[8] = new OBJ_Key();
        gp.obj[8].worldX = 54 * gp.tileSize;
        gp.obj[8].worldY = 51 * gp.tileSize;

        gp.obj[9] = new OBJ_Key();
        gp.obj[9].worldX = 48 * gp.tileSize;
        gp.obj[9].worldY = 42 * gp.tileSize;


        // DIAMONDS

        gp.obj[10] = new OBJ_Diamond();
        gp.obj[10].worldX = 81 * gp.tileSize;
        gp.obj[10].worldY = 39 * gp.tileSize;

        gp.obj[11] = new OBJ_Diamond();
        gp.obj[11].worldX = 29 * gp.tileSize;
        gp.obj[11].worldY = 39 * gp.tileSize;

        gp.obj[12] = new OBJ_Diamond();
        gp.obj[12].worldX = 10 * gp.tileSize;
        gp.obj[12].worldY = 53 * gp.tileSize;

        gp.obj[13] = new OBJ_Diamond();
        gp.obj[13].worldX = 53 * gp.tileSize;
        gp.obj[13].worldY = 39  * gp.tileSize;

        gp.obj[14] = new OBJ_Diamond();
        gp.obj[14].worldX = 74 * gp.tileSize;
        gp.obj[14].worldY = 60 * gp.tileSize;

        gp.obj[15] = new OBJ_Diamond();
        gp.obj[15].worldX = 62 * gp.tileSize;
        gp.obj[15].worldY = 61 * gp.tileSize;

        gp.obj[16] = new OBJ_Diamond();
        gp.obj[16].worldX = 26 * gp.tileSize;
        gp.obj[16].worldY = 58 * gp.tileSize;

        gp.obj[17] = new OBJ_Diamond();
        gp.obj[17].worldX = 39 * gp.tileSize;
        gp.obj[17].worldY = 51 * gp.tileSize;

        gp.obj[18] = new OBJ_Diamond();
        gp.obj[18].worldX = 45 * gp.tileSize;
        gp.obj[18].worldY = 57 * gp.tileSize;

        gp.obj[19] = new OBJ_Diamond();
        gp.obj[19].worldX = 59 * gp.tileSize;
        gp.obj[19].worldY = 46 * gp.tileSize;

///
        gp.obj[20] = new OBJ_Book();
        gp.obj[20].worldX = 88 * gp.tileSize;
        gp.obj[20].worldY = 40 * gp.tileSize;

    }
}
