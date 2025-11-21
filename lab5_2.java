import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class linesComparator implements Comparator<String> {
    @Override
    public int compare(String line1, String line2) {
        return line1.compareTo(line2);
    }
}

public class task2 {
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
                    System.out.println("Помилка: Файл '" + path + "' не знайдено або він недоступний. Спробуйте ще раз.");
                    continue;
                }
                return new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                System.out.println("Помилка (FileNotFound): " + e.getMessage() + ". Спробуйте ще раз.");
            }
        }
    }
    private static ArrayList<String> readAllLines(BufferedReader reader) throws IOException {
        ArrayList<String> list = new ArrayList<>();

        String line;

        while ((line = reader.readLine()) != null) {
            list.add(line);
        }

        return list;
    }
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BufferedReader reader = getBufferedReader(scanner, "Input file path: ", "task2.txt");
        ArrayList<String> lines = readAllLines(reader);
        lines.sort(new linesComparator());
        System.out.println(lines);
    }
}
