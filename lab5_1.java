import java.io.*;
import java.util.*;

public class Main {

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

    private static ArrayList<Place> readPlaces(BufferedReader reader) throws IOException {
        ArrayList<Place> list = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(" ");
            if (parts.length < 9) continue;
            Place p = new Place();
            try {
                p.setName(parts[0]);
                p.setCountry(parts[1]);
                p.setPopulation(Integer.parseInt(parts[2]));
                p.setArea(Integer.parseInt(parts[3]));
                p.setAdministrativeCenter(parts[4]);
                p.setLanguage(parts[5]);
                p.setType(parts[6]);
                p.setCoordinates(new int[]{Integer.parseInt(parts[7]), Integer.parseInt(parts[8])});
                list.add(p);
            } catch (NumberFormatException e) {
                System.out.println("Помилка парсингу Place: " + line);
            }
        }
        return list;
    }

    private static ArrayList<City> readCities(BufferedReader reader) throws IOException {
        ArrayList<City> list = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(" ");
            if (parts.length < 11) continue;

            City c = new City();
            try {
                c.setName(parts[0]);
                c.setCountry(parts[1]);
                c.setPopulation(Integer.parseInt(parts[2]));
                c.setArea(Integer.parseInt(parts[3]));
                c.setAdministrativeCenter(parts[4]);
                c.setLanguage(parts[5]);
                c.setType(parts[6]);
                c.setCoordinates(new int[]{Integer.parseInt(parts[7]), Integer.parseInt(parts[8])});
                c.setFoundedYear(Integer.parseInt(parts[9]));
                c.setIsCapital(Boolean.parseBoolean(parts[10]));
                list.add(c);
            } catch (NumberFormatException e) {
                System.out.println("Помилка парсингу City: " + line);
            }
        }
        return list;
    }

    private static ArrayList<Metropolis> readMetropolises(BufferedReader reader) throws IOException {
        ArrayList<Metropolis> list = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");

            if (parts.length < 13) {
                System.out.println("! Пропущено рядок Metropolis (невірний формат, < 13 полів): [" + line + "]");
                continue;
            }

            Metropolis m = new Metropolis();
            try {
                m.setName(parts[0]);
                m.setCountry(parts[1]);
                m.setPopulation(Integer.parseInt(parts[2]));
                m.setArea(Integer.parseInt(parts[3]));
                m.setAdministrativeCenter(parts[4]);
                m.setLanguage(parts[5]);
                m.setType(parts[6]);
                m.setCoordinates(new int[]{Integer.parseInt(parts[7]), Integer.parseInt(parts[8])});
                m.setFoundedYear(Integer.parseInt(parts[9]));
                m.setIsCapital(Boolean.parseBoolean(parts[10]));
                m.setGdp(Long.parseLong(parts[11]));
                m.setHasSubway(Boolean.parseBoolean(parts[12]));
                list.add(m);
            } catch (Exception e) {
                System.out.println("! Помилка парсингу Metropolis: [" + line + "]");
                System.out.println("  (" + e.getMessage() + ")");
            }
        }
        return list;
    }

    static void printData(String title, ArrayList<? extends Region> data) {
        System.out.println("\n--- " + title + " ---");
        if (data.isEmpty()) {
            System.out.println("Дані відсутні.");
            return;
        }
        for (Region item : data) {
            item.show();
        }
    }

    private static void writeToFile(String filename, ArrayList<Region> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("--- Зведений Відсортований Список (за населенням) ---\n");
            for (Region item : data) {
                writer.write(String.format("Class: %-10s | Name: %-15s | Population: %d\n",
                        item.getClass().getSimpleName(), item.getName(), item.getPopulation()));
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Place> places;
        ArrayList<City> cities;
        ArrayList<Metropolis> metropolises;

        try (
                BufferedReader places_input = getBufferedReader(scanner, "Шлях до файлу 'places' (Enter для places.txt): ", "places.txt");
                BufferedReader cities_input = getBufferedReader(scanner, "Шлях до файлу 'cities' (Enter для cities.txt): ", "cities.txt");
                BufferedReader metropolises_input = getBufferedReader(scanner, "Шлях до файлу 'metropolises' (Enter для metropolises.txt): ", "metropolises.txt")
        ) {
            places = readPlaces(places_input);
            cities = readCities(cities_input);
            metropolises = readMetropolises(metropolises_input);
        } catch (IOException e) {
            System.out.println("Критична помилка читання: " + e.getMessage());
            scanner.close();
            return;
        }

        printData("Прочитані 'Places'", places);
        printData("Прочитані 'Cities'", cities);
        printData("Прочитані 'Metropolises'", metropolises);

        places.sort(null);
        cities.sort(null);
        metropolises.sort(null);

        printData("'Places' (відсортовано за назвою)", places);
        printData("'Cities' (відсортовано за назвою)", cities);
        printData("'Metropolises' (відсортовано за назвою)", metropolises);

        System.out.println("\n--- Додавання нових записів ---");
        try {
            System.out.println("Введіть новий City (11 полів, через пробіл):");
            System.out.println("(Приклад: Tokio Japan 20000000 40000 Kioto Japanese Capital 67 45 840 true)");
            String cityLine = scanner.nextLine();
            cities.add(new City() {{
                String[] parts = cityLine.split(" ");
                setName(parts[0]); setCountry(parts[1]); setPopulation(Integer.parseInt(parts[2]));
                setArea(Integer.parseInt(parts[3])); setAdministrativeCenter(parts[4]);
                setLanguage(parts[5]); setType(parts[6]);
                setCoordinates(new int[]{Integer.parseInt(parts[7]), Integer.parseInt(parts[8])});
                setFoundedYear(Integer.parseInt(parts[9])); setIsCapital(Boolean.parseBoolean(parts[10]));
            }});
            System.out.println("City додано.");

            System.out.println("\nВведіть новий Place (9 полів, через пробіл):");
            System.out.println("(Приклад: CentralPark USA 10000 341 CentralPark English park 40 -73)");
            String placeLine = scanner.nextLine();

            places.add(new Place() {{
                String[] parts = placeLine.split(" ");
                if (parts.length < 9) throw new IllegalArgumentException("Недостатньо даних для Place");
                setName(parts[0]);
                setCountry(parts[1]);
                setPopulation(Integer.parseInt(parts[2]));
                setArea(Integer.parseInt(parts[3]));
                setAdministrativeCenter(parts[4]);
                setLanguage(parts[5]);
                setType(parts[6]);
                setCoordinates(new int[]{Integer.parseInt(parts[7]), Integer.parseInt(parts[8])});
            }});
            System.out.println("Place додано.");


            System.out.println("\nВведіть новий City (11 полів, через пробіл):");
            System.out.println("(Приклад: Kyiv Ukraine 3000000 839 Kyiv Ukrainian city 50 30 860 true)");

            cities.add(new City() {{
                String[] parts = cityLine.split(" ");
                if (parts.length < 11) throw new IllegalArgumentException("Недостатньо даних для City");

                setName(parts[0]); setCountry(parts[1]); setPopulation(Integer.parseInt(parts[2]));
                setArea(Integer.parseInt(parts[3])); setAdministrativeCenter(parts[4]);
                setLanguage(parts[5]); setType(parts[6]);
                setCoordinates(new int[]{Integer.parseInt(parts[7]), Integer.parseInt(parts[8])});
                setFoundedYear(Integer.parseInt(parts[9])); setIsCapital(Boolean.parseBoolean(parts[10]));
            }});
            System.out.println("City додано.");


            System.out.println("\nВведіть новий Metropolis (13 полів, через пробіл):");
            System.out.println("(Приклад: Tokyo Japan 37000000 2194 Tokyo Japanese city 35 139 1457 true 4000000000000 true)");
            String metroLine = scanner.nextLine();

            metropolises.add(new Metropolis() {{
                String[] parts = metroLine.split(" ");
                if (parts.length < 13) throw new IllegalArgumentException("Недостатньо даних для Metropolis");

                setName(parts[0]); setCountry(parts[1]); setPopulation(Integer.parseInt(parts[2]));
                setArea(Integer.parseInt(parts[3])); setAdministrativeCenter(parts[4]);
                setLanguage(parts[5]); setType(parts[6]);
                setCoordinates(new int[]{Integer.parseInt(parts[7]), Integer.parseInt(parts[8])});
                setFoundedYear(Integer.parseInt(parts[9])); setIsCapital(Boolean.parseBoolean(parts[10]));
                setGdp(Long.parseLong(parts[11])); setHasSubway(Boolean.parseBoolean(parts[12]));
            }});
            System.out.println("Metropolis додано.");

        } catch (Exception e) {
            System.out.println("Помилка вводу даних: " + e.getMessage() + ". Запис не додано.");
        }

        places.sort(null);
        cities.sort(null);
        metropolises.sort(null);

        printData("'Places' (з доданим, сортовано за назвою)", places);
        printData("'Cities' (з доданим, сортовано за назвою)", cities);
        printData("'Metropolises' (з доданим, сортовано за назвою)", metropolises);

        cities.sort(null);
        printData("'Cities' (з доданим, сортовано за назвою)", cities);

        ArrayList<Region> allObjects = new ArrayList<>();
        allObjects.addAll(places);
        allObjects.addAll(cities);
        allObjects.addAll(metropolises);

        System.out.println("\n***************************************************");
        System.out.println("Створено зведений список 'ArrayList<Region>'. Всього об'єктів: " + allObjects.size());
        System.out.println("***************************************************");

        allObjects.sort(new RegionPopulationComparator());
        printData("ЗВЕДЕНИЙ СПИСОК (відсортовано за населенням)", allObjects);

        try {
            writeToFile("output.txt", allObjects);
            System.out.println("\nЗведений список успішно записано у 'output.txt'");
        } catch (IOException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }

        scanner.close();
    }
}
