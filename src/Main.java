import main.GameMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(()-> {
            GameMenu menu = new GameMenu();
            menu.setVisible(true);
        });
    }
}