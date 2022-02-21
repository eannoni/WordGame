package FileManipulation;
import java.util.ArrayList;
import java.io.*;
import java.util.Collections;
/**
 * ListTrimmer.java
 * creates a new file "veryrestrictedwords.txt"
 * this new file makes sure that all words in restrictedwords.txt are
 * also present in allwords.txt by removing extra words
 */
public class ListTrimmer {
    public static void main(String[] args) {
        ArrayList<String> bigList = new ArrayList<String>();
        ArrayList<String> smallList = new ArrayList<String>();
        ArrayList<String> newList;
        String line;
        try {
            // build big list from all words
            BufferedReader brAll = new BufferedReader(
                new FileReader("allwords.txt"));
            while((line = brAll.readLine()) != null) {
                bigList.add(line);
            }
            brAll.close();

            // build small list from restricted words
            BufferedReader brRestricted = new BufferedReader(
                new FileReader("restrictedwords.txt"));
            while((line = brRestricted.readLine()) != null) {
                smallList.add(line);
            }
            brRestricted.close();

            // build new list from trimming small list
            newList = trimList(bigList, smallList);

            // write veryrestrictedwords.txt
            BufferedWriter bw = new BufferedWriter(
                new FileWriter("veryrestrictedwords.txt"));
            for(String word : newList) {
                bw.write(word + "\n");
            }
            bw.close();
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Takes in two lists, a large and small
     * Creates new list where all small words are contained in large words list
     * @return new trimmed list
     */
    public static ArrayList<String> trimList(ArrayList<String> bigList, ArrayList<String> smallList) {
        int removalCount = 0;
        ArrayList<String> removedWords = new ArrayList<String>();
        ArrayList<String> newList = new ArrayList<String>();
        Collections.sort(bigList);
        Collections.sort(smallList);
        int bigPtr = 0;
        int smallPtr = 0;
        while (bigPtr < bigList.size() && smallPtr < smallList.size()) {
            String bigWord = bigList.get(bigPtr);
            String smallWord = smallList.get(smallPtr);
            if (bigWord.compareTo(smallWord) < 0) { // big word not in small word list (OK)
                bigPtr++;
            } else if (bigWord.compareTo(smallWord) > 0) { // small word not in big word list (BAD)
                removalCount++;
                removedWords.add(smallWord);
                smallPtr++;
            } else { // equal
                newList.add(smallWord);
                bigPtr++;
                smallPtr++;
            }
        }
        System.out.println(removalCount + " words removed:");
        for(String word : removedWords) {
            System.out.println(word);
        }
        return newList;
    }
}
