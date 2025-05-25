package main;

import entity.NPC_inamic;

public class EnemySetter {

    GamePanel gp;

    public EnemySetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setEnemies() {
        //9 106
        //nivel 2
//        gp.npc[0] = new NPC_inamic(gp, 17 * gp.tileSize, 116 * gp.tileSize);
//        gp.npc[1] = new NPC_inamic(gp, 15 * gp.tileSize, 170 * gp.tileSize);
//        gp.npc[2] = new NPC_inamic(gp, 19 * gp.tileSize, 153 * gp.tileSize);
//        gp.npc[3] = new NPC_inamic(gp, 30 * gp.tileSize, 117 * gp.tileSize);
//        gp.npc[4] = new NPC_inamic(gp, 40 * gp.tileSize, 161 * gp.tileSize);
//        gp.npc[5] = new NPC_inamic(gp, 50 * gp.tileSize, 123 * gp.tileSize);
//        gp.npc[6] = new NPC_inamic(gp, 60 * gp.tileSize, 145 * gp.tileSize);
//        gp.npc[7] = new NPC_inamic(gp, 70 * gp.tileSize, 155 * gp.tileSize);
//        gp.npc[8] = new NPC_inamic(gp, 82 * gp.tileSize, 143 * gp.tileSize);
//        gp.npc[9] = new NPC_inamic(gp, 90 * gp.tileSize, 105 * gp.tileSize);
//        gp.npc[10] = new NPC_inamic(gp, 96 * gp.tileSize, 133 * gp.tileSize);
//        gp.npc[11] = new NPC_inamic(gp, 60 * gp.tileSize, 30 * gp.tileSize);


        //nivel 3
        //50 240
        gp.npc[12] = new NPC_inamic(gp, 21 * gp.tileSize, 207 * gp.tileSize);
        gp.npc[13] = new NPC_inamic(gp, 87 * gp.tileSize, 209 * gp.tileSize);
        gp.npc[14] = new NPC_inamic(gp, 80 * gp.tileSize, 223 * gp.tileSize);
        gp.npc[15] = new NPC_inamic(gp, 50 * gp.tileSize, 222 * gp.tileSize);
        gp.npc[16] = new NPC_inamic(gp, 27 * gp.tileSize, 225 * gp.tileSize);
        gp.npc[17] = new NPC_inamic(gp, 85 * gp.tileSize, 234 * gp.tileSize);
        gp.npc[18] = new NPC_inamic(gp, 60 * gp.tileSize, 252 * gp.tileSize);
        gp.npc[19] = new NPC_inamic(gp, 37 * gp.tileSize, 249 * gp.tileSize);
        gp.npc[20] = new NPC_inamic(gp, 21 * gp.tileSize, 253 * gp.tileSize);
        gp.npc[21] = new NPC_inamic(gp, 19 * gp.tileSize, 265 * gp.tileSize);
        gp.npc[22] = new NPC_inamic(gp, 60 * gp.tileSize, 266 * gp.tileSize);
        gp.npc[23] = new NPC_inamic(gp, 73 * gp.tileSize, 263 * gp.tileSize);
        gp.npc[24] = new NPC_inamic(gp, 10 * gp.tileSize, 279 * gp.tileSize);//bun
        gp.npc[25] = new NPC_inamic(gp, 37 * gp.tileSize, 284 * gp.tileSize);
    }
}