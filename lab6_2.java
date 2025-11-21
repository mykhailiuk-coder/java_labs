import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

class MyArithmeticException extends ArithmeticException {
    public MyArithmeticException(String message) {
        super(message);
    }
}

class MatrixFrame extends JFrame {

    private JTextField filePathField;
    private JTable table;
    private JLabel resultLabel;

    public MatrixFrame() {
        super("Лабораторна робота №2 — Swing + Обробка виключень");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Файл: "));
        filePathField = new JTextField(25);
        topPanel.add(filePathField);

        JButton loadButton = new JButton("Зчитати");
        topPanel.add(loadButton);

        add(topPanel, BorderLayout.NORTH);

        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        resultLabel = new JLabel("Результат: ", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        loadButton.addActionListener(e -> loadMatrixFromFile());

        setVisible(true);
    }


    private void loadMatrixFromFile() {
        try {
            String path = filePathField.getText();
            File file = new File(path);

            if (!file.exists()) {
                throw new FileNotFoundException("Файл не знайдено!");
            }

            Scanner input = new Scanner(file);

            int n1 = Integer.parseInt(input.nextLine().trim());

            if (n1 > 20) {
                throw new IllegalArgumentException("Error: n1 must be less than 20");
            }

            int[][] A1 = new int[n1][n1];
            int[] modules_column_sums = new int[n1];

            for (int i = 0; i < n1; i++) {

                if (!input.hasNextLine()) {
                    throw new NumberFormatException("Рядок " + (i + 1) + " відсутній у файлі!");
                }

                String line = input.nextLine().trim();

                while (line.isEmpty() && input.hasNextLine()) {
                    line = input.nextLine().trim();
                }

                String[] parts = line.split("\\s+");

                if (parts.length != n1) {
                    throw new NumberFormatException("У рядку " + (i + 1) +
                            " має бути " + n1 + " чисел, а знайдено " + parts.length);
                }

                for (int j = 0; j < n1; j++) {
                    try {
                        A1[i][j] = Integer.parseInt(parts[j]);
                    } catch (Exception ex) {
                        throw new NumberFormatException("Не можу перетворити \"" + parts[j] + "\" у число!");
                    }
                    modules_column_sums[j] += Math.abs(A1[i][j]);
                }
            }

            int max_sum = modules_column_sums[0];
            int max_col_index = 0;

            for (int j = 1; j < n1; j++) {
                if (modules_column_sums[j] > max_sum) {
                    max_sum = modules_column_sums[j];
                    max_col_index = j;
                }
            }

            int minInColumn = A1[0][max_col_index];
            for (int i = 1; i < n1; i++) {
                if (A1[i][max_col_index] < minInColumn) {
                    minInColumn = A1[i][max_col_index];
                }
            }

            if (minInColumn == 0) {
                throw new MyArithmeticException("Власне виключення: мінімальний елемент у колонці = 0!");
            }

            DefaultTableModel model = new DefaultTableModel(n1, n1);
            for (int i = 0; i < n1; i++) {
                for (int j = 0; j < n1; j++) {
                    model.setValueAt(A1[i][j], i, j);
                }
            }
            table.setModel(model);

            resultLabel.setText(
                    "Колонка: " + max_col_index +
                            " | Макс. сума модулів: " + max_sum +
                            " | Мін. елемент: " + minInColumn
            );

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Помилка формату числа: " + ex.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);

        } catch (MyArithmeticException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Власне виключення", JOptionPane.WARNING_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Непередбачена помилка: " + ex, "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }
}

public class task2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MatrixFrame();
        });
    }
}
