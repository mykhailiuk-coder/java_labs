import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String f1 = "F1.txt";
        String f2 = "F2.txt";

        ArrayList<String> lines1 = new ArrayList<>();
        Scanner input1 = new Scanner(new File(f1));

        while (input1.hasNextLine()) {
            lines1.add(input1.nextLine());
        }
        input1.close();

        ArrayList<String> words1 = new ArrayList<>();
        for (String line : lines1) {
            line = line.trim();
            if (line.isEmpty()) continue;

            List<String> reading_words1 = new ArrayList<>(Arrays.asList(line.split(" ")));
            for  (String word : reading_words1) {
                words1.add(word);
            }
        }
        System.out.println("\n");

        ArrayList<String> lines2 = new ArrayList<>();
        Scanner input2 = new Scanner(new File(f2));

        while (input2.hasNextLine()) {
            lines2.add(input2.nextLine());
        }
        input2.close();

        List<String> words2 = new ArrayList<>();
        for (String line : lines2) {
            line = line.trim();
            if (line.isEmpty()) continue;

            List<String> reading_words2 = new ArrayList<>(Arrays.asList(line.split(" ")));
            for (String word : reading_words2) {
                words2.add(word);
            }
        }

        System.out.println(words1);
        System.out.println(words2);

        Map<String, Integer> wordsNumbers = new HashMap<>();

        for (String word : words1) {
            wordsNumbers.put(word, 0);
        }

        for (String word1 : words1) {
            for (String word2 : words2) {
                if (word1.equals(word2)) {
                    wordsNumbers.compute(word1, (k, occurences) -> occurences + 1);
                }
            }
        }
        System.out.println(wordsNumbers);
    }
}
