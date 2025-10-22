import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (
                BufferedReader readerF1 = getBufferedReader(scanner, "Введіть шлях до F1 (список слів) [F1.txt]: ", "F1.txt");
                BufferedReader readerF2 = getBufferedReader(scanner, "Введіть шлях до F2 (текст для пошуку) [F2.txt]: ", "F2.txt");

                BufferedWriter writerF3 = getBufferedWriter(scanner, "Введіть шлях до F3 (для унікальних слів) [F3.txt]: ", "F3.txt")
        ) {

            List<String> wordsToFind = readWordsToFind(readerF1);
            if (wordsToFind.isEmpty()) {
                System.out.println("Файл F1 порожній. Немає слів для пошуку.");
                return;
            }

            String text = readAllText(readerF2);
            if (text.isEmpty()) {
                System.out.println("Файл F2 порожній. Пошук неможливий.");
                return;
            }

            Map<String, Integer> counts = countOccurrences(wordsToFind, text);

            sortAndPrintCounts(counts);

            SortedSet<String> uniqueWords = getUniqueSortedWords(text);
            writeUniqueWordsToFile(writerF3, uniqueWords);

        } catch (IOException e) {
            System.err.println("Сталася неочікувана помилка вводу-виводу: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    /**
     * Запитує у користувача шлях до вхідного файлу, доки не буде введено коректний.
     * Вимога 1, 2, 5 (Буферизований файловий ввід).
     */
    private static BufferedReader getBufferedReader(Scanner scanner, String prompt, String defaultPath) {
        while (true) {
            System.out.print(prompt);
            String path = scanner.nextLine();
            if (path.isEmpty()) {
                path = defaultPath;
            }

            try {
                File file = new File(path);
                if (!file.exists() || !file.canRead()) {
                    System.out.println("Помилка: Файл '" + path + "' не знайдено або він недоступний для читання. Спробуйте ще раз.");
                    continue;
                }
                return new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                System.out.println("Помилка (FileNotFound): " + e.getMessage() + ". Спробуйте ще раз.");
            }
        }
    }

    private static List<String> readWordsToFind(BufferedReader reader) throws IOException {
        List<String> words = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split("\\s+");
            for (String token : tokens) {
                if (!token.isEmpty()) {
                    words.add(token);
                }
            }
        }
        return words;
    }

    private static String readAllText(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append(" ");
        }
        return sb.toString();
    }

    private static Map<String, Integer> countOccurrences(List<String> wordsToFind, String text) {
        Map<String, Integer> counts = new HashMap<>();
        for (String word : wordsToFind) {
            counts.put(word, 0);
        }

        String[] textWords = text.split("[\\s\\p{Punct}]+");

        for (String textWord : textWords) {
            if (counts.containsKey(textWord)) {
                counts.put(textWord, counts.get(textWord) + 1);
            }
        }
        return counts;
    }

    private static void sortAndPrintCounts(Map<String, Integer> counts) {
        System.out.println("\n--- Результати підрахунку (за спаданням) ---");

        if (counts.isEmpty()) {
            System.out.println("Слів для підрахунку не знайдено.");
            return;
        }

        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(counts.entrySet());

        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.printf("%s: %d%n", entry.getKey(), entry.getValue());
        }
    }

    private static BufferedWriter getBufferedWriter(Scanner scanner, String prompt, String defaultPath) {
        while (true) {
            System.out.print(prompt);
            String path = scanner.nextLine();
            if (path.isEmpty()) {
                path = defaultPath;
            }

            try {
                File file = new File(path);
                boolean append = false;

                if (file.exists()) {
                    System.out.print("Файл '" + path + "' вже існує. (1) Перезаписати чи (2) Дописати в кінець? [1]: ");
                    String choice = scanner.nextLine();
                    if (choice.equals("2")) {
                        append = true;
                    }
                } else {
                    System.out.print("Файл '" + path + "' не знайдено. Створити новий файл? (y/n) [y]: ");
                    String choice = scanner.nextLine();
                    if (choice.equalsIgnoreCase("n")) {
                        System.out.println("Операцію скасовано. Спробуйте інший шлях.");
                        continue;
                    }
                }

                return new BufferedWriter(new FileWriter(file, append));
            } catch (IOException e) {
                System.out.println("Помилка при відкритті файлу для запису: " + e.getMessage() + ". Спробуйте ще раз.");
            }
        }
    }

    private static SortedSet<String> getUniqueSortedWords(String text) {
        SortedSet<String> uniqueWords = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        String[] textWords = text.split("[\\s\\p{Punct}]+");

        for (String word : textWords) {
            if (!word.isEmpty()) {
                uniqueWords.add(word);
            }
        }
        return uniqueWords;
    }

    private static void writeUniqueWordsToFile(BufferedWriter writer, Collection<String> uniqueWords) throws IOException {
        int count = 0;
        for (String word : uniqueWords) {
            writer.write(word);
            writer.newLine();
            count++;
        }
        System.out.println("\nУспішно записано " + count + " унікальних слів у вихідний файл.");
    }
}
