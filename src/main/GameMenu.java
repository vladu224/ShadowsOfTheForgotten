package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import javax.sound.sampled.*;

public class GameMenu extends JFrame {

    public GameMenu() {
        setTitle("Cartea Umbrelor");

        GamePanel gp = new GamePanel(); // to get size constants
        setSize(gp.screenWidth, gp.screenHeight);
        setResizable(false);
        setUndecorated(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panou cu fundal imagine
        BackgroundPanel panel = new BackgroundPanel(getClass().getResource("/menu/background1.png"));
        panel.setLayout(new GridLayout(5, 1, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(70, 150, 60, 150));
        panel.setOpaque(false);

        // Butoane
        JButton startButton = createButton("START");
        JButton loadButton = createButton("Load Game");
        JButton leaderboardButton = createButton("Leaderboard");
        JButton exitButton = createButton("EXIT");

        // Acțiuni cu sunet click
//        startButton.addActionListener(e -> {
//            playClickSound();
//            JOptionPane.showMessageDialog(this, "Joc nou început!");
//        });
        startButton.addActionListener(e -> {
            playClickSound();

            // Ask for player name first
            String playerName = JOptionPane.showInputDialog(this, "Enter your player name:", "Player Name", JOptionPane.PLAIN_MESSAGE);

            if (playerName != null && !playerName.trim().isEmpty()) {
                dispose(); // Close the menu

                // Launch the game window with player name passed to GamePanel
                SwingUtilities.invokeLater(() -> {
                    JFrame window = new JFrame();
                    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    window.setResizable(true);
                    window.setTitle("Cartea Umbrelor");

                    GamePanel gamePanel = new GamePanel();
                    gamePanel.player.setName(playerName.trim());  // set the player name here!

                    window.add(gamePanel);
                    window.pack();
                    window.setLocationRelativeTo(null);
                    window.setVisible(true);

                    gamePanel.setupGame();
                    gamePanel.startGameThread();
                });
            } else {
                // Name was empty or user pressed cancel
                JOptionPane.showMessageDialog(this,
                        "Player name cannot be empty.",
                        "Invalid Name",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        loadButton.addActionListener(e -> {
            playClickSound();
            dispose(); // Close the menu

            SwingUtilities.invokeLater(() -> {
                JFrame window = new JFrame();
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setResizable(true);
                window.setTitle("Cartea Umbrelor");

                GamePanel gamePanel = new GamePanel();
                window.add(gamePanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);

                // Setup the game first
                gamePanel.loadGame();

                // Load data from database
                DatabaseManager dbManager = new DatabaseManager();
                dbManager.loadPlayer(gamePanel.player, gamePanel.ui);  // <- This line loads saved state
                //dbManager.loadObjects(Arrays.asList(gamePanel.obj));

                // Then start the game
                gamePanel.startGameThread();
            });
        });

        leaderboardButton.addActionListener(e -> {
            playClickSound();
            showLeaderboard();
        });


        exitButton.addActionListener(e -> {
            playClickSound();
            System.exit(0);
        });

        // Adaugă butoane
        panel.add(startButton);
        panel.add(loadButton);
        panel.add(leaderboardButton);
        panel.add(exitButton);

        setContentPane(panel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(30, 60));
        button.setBackground(new Color(100, 100, 100));  // gri
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setContentAreaFilled(false);  // Hover transparent
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(true);  // revine la gri
            }
        });

        return button;
    }

    private void showSettings() {
        String volumeStr = JOptionPane.showInputDialog(this, "Setează volumul (0 - 100):");
        try {
            int volume = Integer.parseInt(volumeStr);
            if (volume >= 0 && volume <= 100) {
                JOptionPane.showMessageDialog(this, "Volumul a fost setat la " + volume + "%.");
            } else {
                JOptionPane.showMessageDialog(this, "Volum invalid. Trebuie să fie între 0 și 100.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valoare invalidă.");
        }
    }

    // Fundal imagine
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(URL imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Sunet la click
    private void playClickSound() {
        try {
            File soundFile = new File("res/menu/clickSound.wav"); // modifică dacă e altă locație
            if (soundFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Sunet click nu a fost găsit.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showLeaderboard() {
        DatabaseManager dbManager = new DatabaseManager();
        java.util.List topPlayers = dbManager.getTopPlayers();  // raw list, avoids generics issue

        if (topPlayers == null || topPlayers.size() == 0) {
            JOptionPane.showMessageDialog(this, "No completed games yet.", "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String leaderboardText = "Top 5 Fastest Players:\n\n";
        for (int i = 0; i < topPlayers.size(); i++) {
            String playerInfo = (String) topPlayers.get(i);
            leaderboardText += (i + 1) + ". " + playerInfo + "\n";
        }

        JOptionPane.showMessageDialog(this, leaderboardText, "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }




}