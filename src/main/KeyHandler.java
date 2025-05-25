package main;

import main.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, escPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent kh) {

    }

    @Override
    public void keyPressed(KeyEvent kh) {

        int code = kh.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if(code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        if(code == KeyEvent.VK_ESCAPE) {

            escPressed = true;
//            if(gp.gameState == gp.playState) {
//                gp.gameState = gp.pauseState;
//            }
//            else if(gp.gameState == gp.pauseState) {
//                gp.gameState = gp.playState;
//            }
        }
    }

    @Override
    public void keyReleased(KeyEvent kh) {

        int code = kh.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if(code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}
