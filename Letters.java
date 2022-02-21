/**
 * Used in a Word object.
 * Represents all 26 letters and how
 * each letter has been used while
 * guessing a given word.
 * A Letters object will be maintained
 * by its corresponding Word object.
 */
public class Letters {
    /** All 26 letters of the alphabet. */
    public static final char[] letters = getLetters();
    /**
     * 26 symbols representing which letter the player has guessed.
     * <ul>
     * <li>' ': Letter has not been guessed.
     * <li>'-': Letter has been guessed and is not in the word.
     * <li>'?': Letter has been guessed but it's location is unknown.
     * <li>'!': Letter has been guessed and it's location is known.
     */
    public char[] symbols = new char[26];

    public Letters() {
        initSymbols();
    }

    public void updateSymbols(String guess, char[] wordSymbols) {
        for (int i = 0; i < guess.length(); i++) {
            int index = guess.charAt(i) - 97;
            symbols[index] = determineReplacementSymbol(symbols[index], wordSymbols[i]);
        }
    }

    private char determineReplacementSymbol(char curr, char next) {
        if (curr == ' ' || next == '!') {
            return next;
        } else if (curr == '!' || curr == '?' || next == ' ') {
            return curr;
        } else {
            return next;
        }
    }

    private static char[] getLetters() {
        char[] chars = new char[26];
        for (int i = 0; i < 26; i++) {
            chars[i] = (char)(i+97);
        }
        return chars;
    }

    private void initSymbols() {
        for (int i = 0; i < 26; i++) {
            symbols[i] = ' ';
        }
    }
}
