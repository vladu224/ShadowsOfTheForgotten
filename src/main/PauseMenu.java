package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;

public class PauseMenu extends JDialog {

    public PauseMenu(JFrame parent, GamePanel gamePanel) {
        super(parent, false); // Modal dialog

        setSize(400, 400);
        setLocationRelativeTo(parent);
        setUndecorated(true);

        // Listen for ESC to close
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();

        BackgroundPanel panel = new BackgroundPanel(getClass().getResource("/menu/background1.png"));
        panel.setLayout(new GridLayout(4, 1, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        panel.setOpaque(false);

        JButton resumeButton = createButton("Resume");
        JButton saveButton = createButton("Save Game");
        JButton exitButton = createButton("Exit to Menu");

        resumeButton.addActionListener(e -> {
            playClickSound();
            dispose();
        });

        saveButton.addActionListener(e -> {
            playClickSound();
            DatabaseManager db = new DatabaseManager();
            db.savePlayer(gamePanel.player, gamePanel.ui);
            JOptionPane.showMessageDialog(this, "Joc salvat cu succes!");
        });

        exitButton.addActionListener(e -> {
            playClickSound();
            dispose();
            parent.dispose(); // close game window
            new GameMenu().setVisible(true);
        });

        panel.add(resumeButton);
        panel.add(saveButton);
        panel.add(exitButton);

        setContentPane(panel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(30, 60));
        button.setBackground(new Color(100, 100, 100));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setContentAreaFilled(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(true);
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
                JOptionPane.showMessageDialog(this, "Volum invalid. Trebuie între 0 și 100.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valoare invalidă.");
        }
    }

    private void playClickSound() {
        try {
            File soundFile = new File("res/menu/clickSound.wav");
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
}
