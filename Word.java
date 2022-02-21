import java.io.*;
import java.util.ArrayList;
/**
 * Represents a Word for the player to guess.
 * Words are selected randomly from the restricted words list,
 * and are given a symbol array. The symbol array informs the
 * player on the accuracy of each letter of their guess.
 * <p>
 * Symbol Array meanings:
 * <ul>
 * <li>'!': The letter is in the correct location
 * <li>'?': The letter is present in the word but not in the correct location
 * <li>'-': The letter is not present in the word
 */
public class Word {
    /** File path for the restricted words file */
    private final String RESTRICTED_WORDS_FILE_PATH = "wordfiles/restrictedwords.txt";
    /** File path for the all words file */
    private final String ALL_WORDS_FILE_PATH = "wordfiles/restrictedwords.txt";
    /** Word to be guessed by the player */
    private String word;
    /** 
     * Contains characters representing the similarity between the player's guess and the word.
     * <ul>
     * <li>'!': The letter is in the correct location
     * <li>'?': The letter is present in the word but not in the correct location
     * <li>'-': The letter is not present in the word
     */
    private char[] symbols;
    /** Represents all 26 letters.
     * Maintains list of player's guessed letters. */
    private Letters letters;

    /** @return The word to be guessed by the player. */
    public String getWord() { return word; }
    /** @return Symbols for the player's most recent guess. */
    public char[] getSymbols() { return symbols; }
    /** @return Symbols for which letters the player has guessed. */
    public char[] getLetterSymbols() { return letters.symbols; }

    /** 
     * Constructor for Word object.
     * Generates a new word to be guessed on initialization.
     */
    public Word() {
        word = generateWord();
        letters = new Letters();
    }

    /**
     * Determines if the player's guess is considered a valid word.
     * If so, it sets new symbols and updates letters.
     * @param guess word that is guessed by the player
     * @return Whether or not the guess was a valid word
     */
    public boolean guess(String guessedWord) {
        guessedWord = guessedWord.toLowerCase();
        if (!isInDictionary(guessedWord)) {
            return false;
        }
        symbols = getNewSymbols(guessedWord);
        letters.updateSymbols(guessedWord, symbols);
        return true;
    }

    /**
     * Checks the symbols array to determine if the latest guess was correct
     * @return Whether or not the symbols array is all '!'
     */
    public boolean isCorrect() {
        for (char c : symbols) {
            if (c != '!') {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets Symbols as formatted string
     * @return formatted symbols string
     */
    public String symbolsToString() {
        String ret = "[ ";
        for(char c : symbols) {
            ret += c + " ";
        }
        ret += "]";
        return ret;
    }

    /** 
     * Helper function for guess().
     * Generates a random word from the restricted word file.
     * @return Symbols array for the given guess
     */
    private char[] getNewSymbols(String guessedWord) { 
        char[] newSymbols = new char[5];
        boolean[] beenUsed = {false, false, false, false, false};
        for (int i = 0; i < guessedWord.length(); i++) {
            if (guessedWord.charAt(i) == word.charAt(i)) {
                newSymbols[i] = '!';
                beenUsed[i] = true;
            }
        }
        for (int i = 0; i < guessedWord.length(); i++) {
            if (newSymbols[i] == '!') continue;
            int index = indexOfEqualUnusedChar(guessedWord.charAt(i), beenUsed);
            if (index == -1) {
                newSymbols[i] = '-';
            } else {
                newSymbols[i] = '?';
                beenUsed[index] = true;
            }
        }
        return newSymbols;
    }

    /**
     * Helper function for getNewSymbols().
     * Determines if c is in the word and if it hasn't already been used.
     * @param c character to search for
     * @param beenUsed which characters in word have been used
     * @return index of character in word equal to c that hasn't been used
     */
    private int indexOfEqualUnusedChar(char c, boolean[] beenUsed) {
        for (int i = 0; i < 5; i++) {
            if (c == word.charAt(i) && beenUsed[i] == false) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Generates a random word from the restricted word file.
     * @return Generated word
     */
    private String generateWord() {
        String newWord = null;
        ArrayList<String> words = new ArrayList<String>(6000);
        try {
            String line;
            BufferedReader br = new BufferedReader(
                new FileReader(RESTRICTED_WORDS_FILE_PATH));
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
            br.close();
        } catch (Exception e) {
            System.err.println(e);
            return "";
        }
        int randNum = (int) (Math.random() * words.size());
        newWord = words.get(randNum);
        return newWord;
    }

    /**
     * Determines if a word is contained in the all words file.
     * @param str Word to search for
     * @return Whether or not the word is contained in the all words file
     */
    private boolean isInDictionary(String str) {
        try {
            String line;
            BufferedReader br = new BufferedReader(
                new FileReader(ALL_WORDS_FILE_PATH));
            while ((line = br.readLine()) != null) {
                if (line.equals(str)) {
                    br.close();
                    return true;
                }
            }
            br.close();
            return false;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
}
