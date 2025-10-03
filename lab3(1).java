import static java.lang.Math.pow;
import java.util.Scanner;

class Region {
    String name;
    String country;
    int population;
    int area;
    String administrativeCenter;
    String language;

    Region() {}

    void setName(String name) { this.name = name; }
    String getName() { return this.name; }

    void setCountry(String country) { this.country = country; }
    String getCountry() { return this.country; }

    void setArea(int area) { this.area = area; }
    int getArea() { return this.area; }

    void setPopulation(int population) { this.population = population; }
    int getPopulation() { return this.population; }

    void setAdministrativeCenter(String center) { this.administrativeCenter = center; }
    String getAdministrativeCenter() { return this.administrativeCenter; }

    void setLanguage(String language) { this.language = language; }
    String getLanguage() { return this.language; }

    void show() {
        System.out.println("--------------");
        System.out.println("Region: " + this.name);
        System.out.println("Country: " + this.country);
        System.out.println("Population: " + this.population);
        System.out.println("Area: " + this.area);
        System.out.println("Center: " + this.administrativeCenter);
        System.out.println("Language: " + this.language);
    }
}

class Place extends Region {
    String type; // парк, село, пам’ятка тощо
    int[] coordinates; // {lat, lon}

    Place() {}

    void setType(String type) { this.type = type; }
    String getType() { return this.type; }

    void setCoordinates(int[] coordinates) { this.coordinates = coordinates; }
    int[] getCoordinates() { return this.coordinates; }

    void show() {
        System.out.println("--------------");
        System.out.println("Place: " + this.name);
        System.out.println("Type: " + this.type);
        System.out.println("Population: " + this.population);
        if (coordinates != null) {
            System.out.println("Coordinates: [" + coordinates[0] + ", " + coordinates[1] + "]");
        }
    }
}

class City extends Place {
    int foundedYear;
    boolean isCapital;

    City() {}

    void setFoundedYear(int year) { this.foundedYear = year; }
    int getFoundedYear() { return this.foundedYear; }

    void setIsCapital(boolean isCapital) { this.isCapital = isCapital; }
    boolean getIsCapital() { return this.isCapital; }

    void show() {
        System.out.println("--------------");
        System.out.println("City: " + this.name);
        System.out.println("Population: " + this.population);
        System.out.println("Founded: " + this.foundedYear);
        System.out.println("Capital: " + this.isCapital);
    }
}

class Metropolis extends City {
    long gdp;
    boolean hasSubway;

    Metropolis() {}

    void setGdp(long gdp) { this.gdp = gdp; }
    long getGdp() { return this.gdp; }

    void setHasSubway(boolean hasSubway) { this.hasSubway = hasSubway; }
    boolean getHasSubway() { return this.hasSubway; }

    void show() {
        System.out.println("--------------");
        System.out.println("Metropolis: " + this.name);
        System.out.println("Population: " + this.population);
        System.out.println("GDP: " + this.gdp);
        System.out.println("Subway: " + this.hasSubway);
    }
}

abstract class Body {
    String name;
    Body() {}
    void setName(String name) { this.name = name; }
    String getName() { return this.name; }
    abstract double getArea();
    abstract double getVolume();
}

class Parallelepiped extends Body {
    float a, b, c;

    Parallelepiped() {}

    void setSides(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    double getArea(){
        return 2 * (a * b + a * c + b * c);
    }
    @Override
    double getVolume(){
        return a * b * c;
    }
}

class Ball extends Body {
    float radius;
    double pi = 3.141592;

    Ball() {}

    void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    double getVolume(){
        return 4 * pi * pow(radius, 3) / 3;
    }
    @Override
    double getArea(){
        return 4 * pi * pow(radius, 2);
    }
}

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter task (1 - Geography, 2 - Geometry): ");
        int task = input.nextInt();
        input.nextLine(); // очищення буфера

        switch (task) {
            case 1:
                Metropolis metro = new Metropolis();
                System.out.print("Enter metropolis name: ");
                metro.setName(input.nextLine());
                System.out.print("Enter population: ");
                metro.setPopulation(input.nextInt());
                System.out.print("Enter GDP: ");
                metro.setGdp(input.nextLong());
                System.out.print("Has subway? (true/false): ");
                metro.setHasSubway(input.nextBoolean());
                metro.show();

                break;

            case 2:
                System.out.print("Choose shape (1 - Parallelepiped, 2 - Ball): ");
                int shapeChoice = input.nextInt();

                if (shapeChoice == 1) {
                    Parallelepiped p = new Parallelepiped();
                    System.out.print("Enter sides a, b, c: ");
                    float a = input.nextFloat();
                    float b = input.nextFloat();
                    float c = input.nextFloat();
                    p.setSides(a, b, c);
                    System.out.println("Parallelepiped area: " + p.getArea());
                    System.out.println("Parallelepiped volume: " + p.getVolume());
                } else if (shapeChoice == 2) {
                    Ball b = new Ball();
                    System.out.print("Enter radius: ");
                    float r = input.nextFloat();
                    b.setRadius(r);
                    System.out.println("Ball area: " + b.getArea());
                    System.out.println("Ball volume: " + b.getVolume());
                } else {
                    System.out.println("Invalid choice");
                }
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}
