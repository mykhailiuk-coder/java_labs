import java.util.Random;

class Developer implements Runnable {
    private String name;
    private int changeInterval;
    private double speed;

    public Developer(String name, double speed, int changeInterval) {
        this.name = name;
        this.speed = speed;
        this.changeInterval = changeInterval;
    }

    @Override
    public void run() {
        Random random = new Random();
        double x = 0, y = 0;
        double dx = random.nextDouble() * 2 - 1;
        double dy = random.nextDouble() * 2 - 1;

        while (true) {
            x += dx * speed;
            y += dy * speed;
            System.out.printf("%s at (%.2f, %.2f)%n", name, x, y);

            try {
                Thread.sleep(changeInterval * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            dx = random.nextDouble() * 2 - 1;
            dy = random.nextDouble() * 2 - 1;
        }
    }
}

class Manager implements Runnable {
    private String name;
    private double radius;
    private double speed;

    public Manager(String name, double radius, double speed) {
        this.name = name;
        this.radius = radius;
        this.speed = speed;
    }

    @Override
    public void run() {
        double angle = 0;
        while (true) {
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            System.out.printf("%s at (%.2f, %.2f)%n", name, x, y);

            angle += speed;
            if (angle > 2 * Math.PI) angle -= 2 * Math.PI;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread devThread = new Thread(new Developer("Dev1", 1.0, 2));
        Thread mgrThread = new Thread(new Manager("Manager1", 5.0, 0.2));

        devThread.start();
        mgrThread.start();

        devThread.sleep(3000);
    }
}
