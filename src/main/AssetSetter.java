package main;

import object.OBJ_Book;
import object.OBJ_Diamond;
import object.OBJ_Final_Book;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // KEYS - LVL1

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


        // DIAMONDS - LVL1

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


        // BOOK - LVL1

        gp.obj[20] = new OBJ_Book();
        gp.obj[20].worldX = 88 * gp.tileSize;
        gp.obj[20].worldY = 40 * gp.tileSize;

        //x: 86;     87;     88;
        //y: 40;     39;     39;

        // KEYS - LVL2

        gp.obj[21] = new OBJ_Key();
        gp.obj[21].worldX = 14 * gp.tileSize;
        gp.obj[21].worldY = 106 * gp.tileSize;

        gp.obj[22] = new OBJ_Key();
        gp.obj[22].worldX = 38 * gp.tileSize;
        gp.obj[22].worldY = 108 * gp.tileSize;

        gp.obj[23] = new OBJ_Key();
        gp.obj[23].worldX = 64 * gp.tileSize;
        gp.obj[23].worldY = 109 * gp.tileSize;

        gp.obj[24] = new OBJ_Key();
        gp.obj[24].worldX = 47 * gp.tileSize;
        gp.obj[24].worldY = 125 * gp.tileSize;

        gp.obj[25] = new OBJ_Key();
        gp.obj[25].worldX = 77 * gp.tileSize;
        gp.obj[25].worldY = 120 * gp.tileSize;

        gp.obj[26] = new OBJ_Key();
        gp.obj[26].worldX = 14 * gp.tileSize;
        gp.obj[26].worldY = 128 * gp.tileSize;

        gp.obj[27] = new OBJ_Key();
        gp.obj[27].worldX = 29 * gp.tileSize;
        gp.obj[27].worldY = 142 * gp.tileSize;

        gp.obj[28] = new OBJ_Key();
        gp.obj[28].worldX = 34 * gp.tileSize;
        gp.obj[28].worldY = 152 * gp.tileSize;

        gp.obj[29] = new OBJ_Key();
        gp.obj[29].worldX = 9 * gp.tileSize;
        gp.obj[29].worldY = 151 * gp.tileSize;

        gp.obj[30] = new OBJ_Key();
        gp.obj[30].worldX = 58 * gp.tileSize;
        gp.obj[30].worldY = 171 * gp.tileSize;


        // DIAMONDS - LVL2

        gp.obj[31] = new OBJ_Diamond();
        gp.obj[31].worldX = 77 * gp.tileSize;
        gp.obj[31].worldY = 162 * gp.tileSize;

        gp.obj[32] = new OBJ_Diamond();
        gp.obj[32].worldX = 18 * gp.tileSize;
        gp.obj[32].worldY = 165 * gp.tileSize;

        gp.obj[33] = new OBJ_Diamond();
        gp.obj[33].worldX = 51 * gp.tileSize;
        gp.obj[33].worldY = 151  * gp.tileSize;

        gp.obj[34] = new OBJ_Diamond();
        gp.obj[34].worldX = 56 * gp.tileSize;
        gp.obj[34].worldY = 140 * gp.tileSize;

        gp.obj[35] = new OBJ_Diamond();
        gp.obj[35].worldX = 81 * gp.tileSize;
        gp.obj[35].worldY = 130 * gp.tileSize;

        gp.obj[36] = new OBJ_Diamond();
        gp.obj[36].worldX = 67 * gp.tileSize;
        gp.obj[36].worldY = 131 * gp.tileSize;

        gp.obj[37] = new OBJ_Diamond();
        gp.obj[37].worldX = 63 * gp.tileSize;
        gp.obj[37].worldY = 117 * gp.tileSize;

        gp.obj[38] = new OBJ_Diamond();
        gp.obj[38].worldX = 90 * gp.tileSize;
        gp.obj[38].worldY = 108 * gp.tileSize;

        gp.obj[39] = new OBJ_Diamond();
        gp.obj[39].worldX = 28 * gp.tileSize;
        gp.obj[39].worldY = 122 * gp.tileSize;

        gp.obj[40] = new OBJ_Diamond();
        gp.obj[40].worldX = 38 * gp.tileSize;
        gp.obj[40].worldY = 113 * gp.tileSize;


        // BOOK - LVL2

        gp.obj[41] = new OBJ_Book();
        gp.obj[41].worldX = 90 * gp.tileSize;
        gp.obj[41].worldY = 134 * gp.tileSize;


        // KEYS - LVL3

        gp.obj[42] = new OBJ_Key();
        gp.obj[42].worldX = 9 * gp.tileSize;
        gp.obj[42].worldY = 223 * gp.tileSize;

        gp.obj[43] = new OBJ_Key();
        gp.obj[43].worldX = 30 * gp.tileSize;
        gp.obj[43].worldY = 216 * gp.tileSize;

        gp.obj[44] = new OBJ_Key();
        gp.obj[44].worldX = 79 * gp.tileSize;
        gp.obj[44].worldY = 208 * gp.tileSize;

        gp.obj[45] = new OBJ_Key();
        gp.obj[45].worldX = 28 * gp.tileSize;
        gp.obj[45].worldY = 234 * gp.tileSize;

        gp.obj[46] = new OBJ_Key();
        gp.obj[46].worldX = 50 * gp.tileSize;
        gp.obj[46].worldY = 238 * gp.tileSize;

        gp.obj[47] = new OBJ_Key();
        gp.obj[47].worldX = 90 * gp.tileSize;
        gp.obj[47].worldY = 233 * gp.tileSize;

        gp.obj[48] = new OBJ_Key();
        gp.obj[48].worldX = 9 * gp.tileSize;
        gp.obj[48].worldY = 256 * gp.tileSize;

        gp.obj[49] = new OBJ_Key();
        gp.obj[49].worldX = 72 * gp.tileSize;
        gp.obj[49].worldY = 265 * gp.tileSize;

        gp.obj[50] = new OBJ_Key();
        gp.obj[50].worldX = 16 * gp.tileSize;
        gp.obj[50].worldY = 288 * gp.tileSize;

        gp.obj[51] = new OBJ_Key();
        gp.obj[51].worldX = 86 * gp.tileSize;
        gp.obj[51].worldY = 286 * gp.tileSize;


//        // DIAMONDS - LVL3

        gp.obj[52] = new OBJ_Diamond();
        gp.obj[52].worldX = 21 * gp.tileSize;
        gp.obj[52].worldY = 208 * gp.tileSize;

        gp.obj[53] = new OBJ_Diamond();
        gp.obj[53].worldX = 50 * gp.tileSize;
        gp.obj[53].worldY = 210 * gp.tileSize;

        gp.obj[54] = new OBJ_Diamond();
        gp.obj[54].worldX = 90 * gp.tileSize;
        gp.obj[54].worldY = 212 * gp.tileSize;

        gp.obj[55] = new OBJ_Diamond();
        gp.obj[55].worldX = 67 * gp.tileSize;
        gp.obj[55].worldY = 229  * gp.tileSize;

        gp.obj[56] = new OBJ_Diamond();
        gp.obj[56].worldX = 48 * gp.tileSize;
        gp.obj[56].worldY = 230 * gp.tileSize;

        gp.obj[57] = new OBJ_Diamond();
        gp.obj[57].worldX = 34 * gp.tileSize;
        gp.obj[57].worldY = 255 * gp.tileSize;

        gp.obj[58] = new OBJ_Diamond();
        gp.obj[58].worldX = 65 * gp.tileSize;
        gp.obj[58].worldY = 246 * gp.tileSize;

        gp.obj[59] = new OBJ_Diamond();
        gp.obj[59].worldX = 9 * gp.tileSize;
        gp.obj[59].worldY = 275 * gp.tileSize;

        gp.obj[60] = new OBJ_Diamond();
        gp.obj[60].worldX = 55 * gp.tileSize;
        gp.obj[60].worldY = 286 * gp.tileSize;

        gp.obj[61] = new OBJ_Diamond();
        gp.obj[61].worldX = 102 * gp.tileSize;
        gp.obj[61].worldY = 288 * gp.tileSize;


        // FINAL BOOK

        gp.obj[62] = new OBJ_Final_Book();
        gp.obj[62].worldX = 116 * gp.tileSize;
        gp.obj[62].worldY = 287 * gp.tileSize;

    }
}
