package FileManipulation;
import java.io.*;

public class ScrabbleParser {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(
                new FileReader("scrabblewords.txt"));
            BufferedWriter bw = new BufferedWriter(
                new FileWriter("scrabblewordstrimmed.txt"));
            String line = null;
            while((line = br.readLine()) != null) {
                if(line.length() == 5) {
                    bw.write(line.toLowerCase() + "\n");
                }
            }
            br.close();
            bw.close();
        } catch (Exception e) {
            return;
        }
    }
}
