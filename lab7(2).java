import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class BaseAI<T> extends Thread {
    protected volatile boolean running = true;
    protected List<T> objects;

    public BaseAI(List<T> objects) {
        this.objects = objects;
    }

    protected abstract void processObject(T obj);

    @Override
    public void run() {
        while (true) {
            synchronized (objects) {
                for (T obj : objects) {
                    if (running) {
                        processObject(obj);
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseAI() { running = false; }
    public void resumeAI() { running = true; }
}

class MetropolisAI extends BaseAI<Metropolis> {

    public MetropolisAI(List<Metropolis> metros) {
        super(metros);
    }

    @Override
    protected void processObject(Metropolis metro) {
        Random rand = new Random();
        metro.setPopulation(metro.getPopulation() + rand.nextInt(1000));
        metro.setGdp(metro.getGdp() + rand.nextInt(5000));

        System.out.println(
                "AI updated: " + metro.getName() +
                        ", Pop: " + metro.getPopulation() +
                        ", GDP: " + metro.getGdp()
        );
    }
}

class AppUI extends JFrame {

    private MetropolisAI metropolisAI;

    public AppUI() {
        setTitle("AI Controller");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.FlowLayout());

        List<Metropolis> metros = new ArrayList<>();
        metros.add(new Metropolis());
        metros.get(0).setName("Kyiv");
        metros.get(0).setPopulation(3000000);
        metros.get(0).setGdp(10000000);

        metropolisAI = new MetropolisAI(metros);
        metropolisAI.start();

        JButton pauseButton = new JButton("Pause AI");
        pauseButton.addActionListener(e -> metropolisAI.pauseAI());
        add(pauseButton);

        JButton resumeButton = new JButton("Resume AI");
        resumeButton.addActionListener(e -> metropolisAI.resumeAI());
        add(resumeButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppUI::new);
    }
}
