import java.io.*;

/**
 * Keeps track of player statistics, reads from and writes to 'stats.txt' file
 */
public class PlayerStats {

    /** File name for where the player stats are permanently stored */
    private static final String FILE_PATH = "data/stats.txt";
    /** Total number of games played by the player */
    private int gamesPlayed = 0;
    /** Total number of games won by the player */
    private int gamesWon = 0;
    /** Player's percentage of games won */
    private double winPercent = 0.0;
    /** Number of consecutive games the player has won */
    private int currentStreak = 0;
    /** Highest streak obtained by the player */
    private int maxStreak = 0;

    /**
     * Constructor for PlayerStats object.
     * Calls loadData() to automatically load from file 'stats.text'
     */
    public PlayerStats() {
        boolean isLoaded = loadData();
        // if save file does not exist or has bad data, rewrite save file
        if(!isLoaded) {
            saveData();
        }
    }

    public int getGamesPlayed() { return gamesPlayed; }
    public int getGamesWon() { return gamesWon; }
    public double getWinPercent() { return winPercent; }
    public int getCurrentStreak() { return currentStreak; }
    public int getMaxStreak() { return maxStreak; }

    /**
     * Updates stats after a player has finished a game
     * @param hasWon true player won the previous game
     */
    public void UpdateStats(boolean hasWon) {
        if (hasWon) {
            gamesWon++;
            currentStreak++;
            if (currentStreak > maxStreak) {
                maxStreak = currentStreak;
            }
        } else {
            currentStreak = 0;
        }
        gamesPlayed++;
        winPercent = (double) gamesWon * 100 / (double) gamesPlayed;
    }

    /**
     * Loads data from 'stats.txt' file
     * @return boolean success flag
     */
    private boolean loadData() {
        try {
            BufferedReader br = new BufferedReader(
                new FileReader(FILE_PATH));
            gamesPlayed = Integer.parseInt(br.readLine());
            gamesWon = Integer.parseInt(br.readLine());
            winPercent = Double.parseDouble(br.readLine());
            currentStreak = Integer.parseInt(br.readLine());
            maxStreak = Integer.parseInt(br.readLine());
            br.close();
        } catch (IOException e) {
            System.err.println("File Error: " + e);
            return false;
        } catch (NumberFormatException e) {
            System.err.println("---------------------------------------------------\n"
            + "ERROR: The save file was corrupted. Save data lost.\n"
            + "---------------------------------------------------");
            return false;
        }
        return true;
    }

    /**
     * Saves data to 'stats.txt' file
     * @return boolean success flag
     */
    public boolean saveData() {
        try {
           BufferedWriter bw = new BufferedWriter(
               new FileWriter(FILE_PATH));
            bw.write(gamesPlayed + "\n");
            bw.write(gamesWon + "\n");
            bw.write(winPercent + "\n");
            bw.write(currentStreak + "\n");
            bw.write(maxStreak + "\n");
            bw.close();
        } catch (Exception e) {
            System.err.println("File Error: " + e);
            return false;
        }
        return true;
    }
}
