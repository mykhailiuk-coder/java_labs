import static java.lang.Math.pow;
import java.util.Scanner;

interface Body {
    String getName();
    double getArea();
    double getVolume();
}

class Parallelepiped implements Body {
    private String name;
    private float a, b, c;

    Parallelepiped(String name, float a, float b, float c) {
        this.name = name;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String getName() { return name; }

    @Override
    public double getArea() {
        return 2 * (a * b + a * c + b * c);
    }

    @Override
    public double getVolume() {
        return a * b * c;
    }
}

class Ball implements Body {
    private String name;
    private float radius;
    private final double pi = 3.141592;

    Ball(String name, float radius) {
        this.name = name;
        this.radius = radius;
    }

    @Override
    public String getName() { return name; }

    @Override
    public double getArea() {
        return 4 * pi * pow(radius, 2);
    }

    @Override
    public double getVolume() {
        return 4 * pi * pow(radius, 3) / 3;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Choose shape (1 - Parallelepiped, 2 - Ball): ");
        int choice = in.nextInt();

        Body shape;

        if (choice == 1) {
            System.out.print("Enter sides a, b, c: ");
            float a = in.nextFloat();
            float b = in.nextFloat();
            float c = in.nextFloat();
            shape = new Parallelepiped("Box", a, b, c);
        } else {
            System.out.print("Enter radius: ");
            float r = in.nextFloat();
            shape = new Ball("Sphere", r);
        }

        System.out.println("--------------");
        System.out.println("Shape: " + shape.getName());
        System.out.println("Area: " + shape.getArea());
        System.out.println("Volume: " + shape.getVolume());
    }
}
