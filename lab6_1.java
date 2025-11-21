import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Анімація");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            String[] options = {"Red", "Green", "Blue"};
            JComboBox<String> dropdown = new JComboBox<>(options);

            MovingTextPanel animationPanel = new MovingTextPanel("Java Swing");

            dropdown.addActionListener(e -> {
                String selected = (String) dropdown.getSelectedItem();
                switch (selected) {
                    case "Red" -> animationPanel.setNextBounceColor(Color.RED);
                    case "Green" -> animationPanel.setNextBounceColor(Color.GREEN);
                    case "Blue" -> animationPanel.setNextBounceColor(Color.BLUE);
                }
            });

            frame.setLayout(new BorderLayout());
            frame.add(dropdown, BorderLayout.NORTH);
            frame.add(animationPanel, BorderLayout.CENTER);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}



class MovingTextPanel extends JPanel implements ActionListener {

    private Timer timer;
    private String text;
    private int x, y;
    private int dx = 2;

    private Color textColor = Color.BLUE;
    private Color nextBounceColor = Color.RED;

    public MovingTextPanel(String text) {
        this.text = text;

        this.x = 0;
        this.y = 150;

        this.timer = new Timer(16, this);
        this.timer.start();
    }

    public void setNextBounceColor(Color color) {
        this.nextBounceColor = color;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += dx;

        if (x + getTextWidth() >= getWidth()) {
            dx = -dx;
            textColor = nextBounceColor;
        }

        if (x <= 0) {
            dx = -dx;
            textColor = nextBounceColor;
        }

        repaint();
    }

    private int getTextWidth() {
        return getFontMetrics(new Font("Arial", Font.BOLD, 24))
                .stringWidth(text);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(textColor);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(text, x, y);
    }
}
