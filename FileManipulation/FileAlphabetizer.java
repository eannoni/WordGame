package FileManipulation;
import java.io.*;
import java.util.Collections;
import java.util.ArrayList;

public class FileAlphabetizer {
    /**
     * Alphabetizes .txt file and creates new file called "sorted_filename.txt"
     */
    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("Error: too few command line args. Arg1 should be file path and arg2 should be file name");
            return;
        } else if (args.length > 2) {
            System.out.println("Error: too many command line args. Arg1 should be file path and arg2 should be file name");
            return;
        }

        String readFileName = args[0] + args[1];
        String writeFileName =  args[0] + "sorted_" + args[1];
        ArrayList<String> list = new ArrayList<String>();
        String line = null;

        try {
            BufferedReader br = new BufferedReader(
                new FileReader(readFileName));
            while((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();

            Collections.sort(list);

            BufferedWriter bw = new BufferedWriter(
                new FileWriter(writeFileName));
            for(String word : list) {
                bw.write(word + "\n");
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Could not find file, enter file as \"PATH/\" and \"filename.txt\"");
            return;
        }
    }
}
