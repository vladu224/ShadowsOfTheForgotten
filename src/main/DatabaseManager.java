package main;

import entity.Player;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import object.SuperObject;  // Assuming your object class is in entity package

public class DatabaseManager {

    public static final String DB_URL = "jdbc:sqlite:savegame.db";

    public DatabaseManager() {
        createTables();
    }

    private void createTables() {
        String playerTableSQL = "CREATE TABLE IF NOT EXISTS player (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "worldX INTEGER, " +
                "worldY INTEGER, " +
                "level INTEGER, " +
                "keys INTEGER, " +
                "diamonds INTEGER, " +
                "timePlayed REAL" +
                ");";

        String objectTableSQL = "CREATE TABLE IF NOT EXISTS game_object (" +
                "objectX INTEGER, " +
                "objectY INTEGER, " +
                "type TEXT, " +
                "collected BOOLEAN" +
                ");";

        String leaderboardTableSQL = "CREATE TABLE IF NOT EXISTS leaderboard (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "timePlayed REAL" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(playerTableSQL);
            stmt.execute(objectTableSQL);
            stmt.execute(leaderboardTableSQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save player data and play time.
     * Uses id = 1 as a single save slot.
     */
    public void savePlayer(Player player, UI ui) {
        String sql = "REPLACE INTO player (id, name, worldX, worldY, keys, diamonds, timePlayed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, 1); // single save slot id
            pstmt.setString(2, player.name);
            pstmt.setInt(3, player.worldX);
            pstmt.setInt(4, player.worldY);
            pstmt.setInt(5, player.hasKey);
            pstmt.setInt(6, player.hasDiamond);
            pstmt.setFloat(7, ui.getPlayTime());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load player data and play time.
     * If no save found, player and UI remain unchanged.
     */
    public void loadPlayer(Player player, UI ui) {
        String sql = "SELECT * FROM player WHERE id = 1";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                player.name = rs.getString("name");
                player.worldX = rs.getInt("worldX");
                player.worldY = rs.getInt("worldY");
                player.hasKey = rs.getInt("keys");
                player.hasDiamond = rs.getInt("diamonds");
                float playTime = rs.getFloat("timePlayed");
                ui.setPlayTime(playTime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save all objects data.
     * Clears the table and inserts all current objects.
     */
    public void saveObjects(List<SuperObject> objects) {
        String deleteSQL = "DELETE FROM game_object";
        String insertSQL = "INSERT INTO game_object (objectX, objectY, type, collected) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement deleteStmt = conn.createStatement();
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            // Clear old objects
            deleteStmt.executeUpdate(deleteSQL);

            // Insert all objects
            for (SuperObject obj : objects) {
                insertStmt.setInt(1, obj.worldX);
                insertStmt.setInt(2, obj.worldY);
                insertStmt.setString(3, obj.name);
                insertStmt.setBoolean(4, obj.collected);
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load objects data.
     * Updates the collected status of matching objects by position and type.
     */
    public void loadObjects(List<SuperObject> objects) {
        String sql = "SELECT * FROM game_object";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int x = rs.getInt("objectX");
                int y = rs.getInt("objectY");
                String type = rs.getString("type");
                boolean collected = rs.getBoolean("collected");

                // Find matching object in the current list and update collected flag
                for (SuperObject obj : objects) {
                    if (obj.worldX == x && obj.worldY == y && obj.name.equals(type)) {
                        obj.collected = collected;
                        break;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveSingleObject(SuperObject object) {
        String selectSQL = "SELECT COUNT(*) FROM game_object WHERE objectX = ? AND objectY = ? AND type = ?";
        String insertSQL = "INSERT INTO game_object (objectX, objectY, type, collected) VALUES (?, ?, ?, ?)";
        String updateSQL = "UPDATE game_object SET collected = ? WHERE objectX = ? AND objectY = ? AND type = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
            selectStmt.setInt(1, object.worldX);
            selectStmt.setInt(2, object.worldY);
            selectStmt.setString(3, object.name);
            ResultSet rs = selectStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            selectStmt.close();

            if (count == 0) {
                PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
                insertStmt.setInt(1, object.worldX);
                insertStmt.setInt(2, object.worldY);
                insertStmt.setString(3, object.name);
                insertStmt.setBoolean(4, object.collected);
                insertStmt.executeUpdate();
                insertStmt.close();
            } else {
                PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
                updateStmt.setBoolean(1, object.collected);
                updateStmt.setInt(2, object.worldX);
                updateStmt.setInt(3, object.worldY);
                updateStmt.setString(4, object.name);
                updateStmt.executeUpdate();
                updateStmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveToLeaderboard(Player player, UI ui) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO leaderboard (name, timePlayed) VALUES (?, ?)")) {
            pstmt.setString(1, player.name);
            pstmt.setFloat(2, ui.getPlayTime());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTopPlayers() {
        List<String> players = new ArrayList<>();
        String sql = "SELECT name, timePlayed FROM leaderboard WHERE timePlayed > 0 ORDER BY timePlayed ASC LIMIT 5";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                float time = rs.getFloat("timePlayed");
                players.add(name + " - " + formatDuration((int) time));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    private String formatDuration(int seconds) {
        int mins = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }

}
