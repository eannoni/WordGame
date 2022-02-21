import java.util.Scanner;
import java.util.ArrayList;

public class WordGame {
    static Scanner input = new Scanner(System.in);
    static PlayerStats stats = new PlayerStats();
    static final int NUM_GUESSES_ALLOWED = 6;
    public static void main(String[] args) {
        printWelcomeMessage();
        while(true) {
            playGame();
        }
    }

    public static void playGame() {
        while(true) {
            String repeatMsg = "Enter a number:\n1. Play\n2. View Stats\n3. Quit";
            int choice = getUserInt(1,3, repeatMsg);
            if (choice == 1) {  
                System.out.println("\n");
                round();
            } else if (choice == 2) {
               printStats();
            } else {   
                quitGame();
            }
        }
    }

    public static void round() {
        System.out.println("-------------------- ROUND " + (stats.getGamesPlayed()+1) + " --------------------");
        Word word = new Word();
        String guess;
        ArrayList<String> history = new ArrayList<String>();
        System.out.println("A 5-letter word has been generated.");
        int guessCounter = NUM_GUESSES_ALLOWED;
        boolean gameWon = false;
        while(guessCounter > 0 && !gameWon) {
            System.out.println(guessCounter + (guessCounter == 1? " guess " : " guesses ") + "remaining");
            System.out.println("Enter a 5-letter word to guess: ");
            guess = input.nextLine();
            boolean validGuess = word.guess(guess);
            while (!validGuess) {
                System.out.println("Word not found in the 5-letter word dictionary, try again.");
                guess = input.nextLine();
                validGuess = word.guess(guess);
            }
            gameWon = word.isCorrect();
            updateHistory(history, guess, word.symbolsToString());
            printHistory(history);
            printLetters(word);
            guessCounter--;
        }
        if(gameWon) {
            System.out.println("You Won!");
        } else {
            System.out.println("You lost...");
        }
        System.out.println("The word was " + word.getWord().toUpperCase() + "\n");
        stats.UpdateStats(gameWon);
    }

    public static void updateHistory(ArrayList<String> history, String guess, String symbols) {
        String guessToString = "[ ";
        for(char c : guess.toUpperCase().toCharArray()) {
            guessToString += c + " ";
        }
        guessToString += "]";
        history.add(guessToString);
        history.add(symbols);
    }

    public static void printHistory(ArrayList<String> history) {
        for(String s : history) {
            System.out.println(s);
        }
    }

    public static int getUserInt(int lowest, int highest, String repeatMsg) {
        int choice = 0;
        while (choice < lowest || choice > highest) {
            System.out.println(repeatMsg);
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Non-number input provided. ");
            }
        }
        return choice;
    }

    public static void quitGame() {
        System.out.println("Thanks for playing!");
        stats.saveData();
        input.close();
        System.exit(0);
    }

    public static void printStats() {
        System.out.println("-------------- YOUR STATS --------------");
        System.out.println("Games Played: " + stats.getGamesPlayed());
        System.out.println("Games Won: " + stats.getGamesWon());
        System.out.println("Win Rate: " + String.format("%,.2f",stats.getWinPercent()) + "%");
        System.out.println("Current Win Streak: " + stats.getCurrentStreak());
        System.out.println("Best Win Streak: " + stats.getMaxStreak());
        System.out.println();
    }

    public static void printLetters(Word word) {
        char[] letterSymbols = word.getLetterSymbols();
        System.out.print("[ ");
        for (int i = 0; i < 26; i++) {
            System.out.print(Letters.letters[i] + " ");
        }
        System.out.print("]\n[ ");
        for (int i = 0; i < 26; i++) {
            System.out.print(letterSymbols[i] + " ");
        }
        System.out.println("]\n");
    }

    public static void printWelcomeMessage() {
        String welcomeMsg = 
        "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
        "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
        "//===============================================================\\\\\n" +
        "||                                                               ||\n" +
        "||                                                               ||\n" +
        "||   ||        ||  //===\\\\  //===\\\\  ||==\\\\   ||       ||====[   ||\n" +
        "||   ||        ||  ||   ||  ||   ||  ||   ||  ||       ||        ||\n" +
        "||   ||        ||  ||   ||  ||===//  ||   ||  ||       ||===     ||\n" +
        "||   ||  //\\\\  ||  ||   ||  || \\\\    ||   ||  ||       ||        ||\n" +
        "||    \\\\//  \\\\//   \\\\===//  ||  \\\\_  ||==//   ||====[  ||====[   ||\n" +
        "||                                                               ||\n" +
        "||                     Created By Eli Annoni                     ||\n" +
        "\\\\===============================================================//\n";
        System.out.println(welcomeMsg);
    }
}