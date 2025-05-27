import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends Frame implements ActionListener {
    private final TextField display;
    private double num1, num2, result;
    private char operator;

    public Calculator() {
        setTitle("Calculator");
        setSize(350, 500);
        setLayout(new BorderLayout());

        Panel displayPanel = new Panel(new BorderLayout());
        displayPanel.setBackground(Color.WHITE);
        displayPanel.setLayout(new BorderLayout());

        display = new TextField("0");
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        displayPanel.add(display, BorderLayout.CENTER);

        add(displayPanel, BorderLayout.NORTH);

        Panel panel = new Panel();
        panel.setLayout(new GridLayout(6, 5, 5, 5));

        String[] buttons = {
            "MC", "MR", "MS", "M+", "M-",
            "<--", "CE", "C", "+/-", "SQRT",
            "7", "8", "9", "/", "%",
            "4", "5", "6", "*", "1/X",
            "1", "2", "3", "-", "=",
            "0", "", ".", "+", ""
        };

        for (String text : buttons) {
            Button button = new Button(text);
            if (!text.equals("")) {
                button.setFont(new Font("Times New Roman", Font.PLAIN, 18));
                button.addActionListener(this);
            } else {
                button.setEnabled(false);
                button.setBackground(Color.WHITE);
            }
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            switch (command) {
                case "C" -> {
                    display.setText("");
                    num1 = num2 = result = 0;
                }
                case "SQRT" -> {
                    num1 = Double.parseDouble(display.getText());
                    display.setText(Double.toString(Math.sqrt(num1)));
                }
                case "+", "-", "*", "/", "%" -> {
                    num1 = Double.parseDouble(display.getText());
                    operator = command.charAt(0);
                    display.setText("");
                }
                case "1/X" -> {
                    num1 = Double.parseDouble(display.getText());
                    display.setText(Double.toString(1 / num1));
                }
                case "=" -> {
                    num2 = Double.parseDouble(display.getText());
                    result = calculate(num1, num2, operator);
                    display.setText(Double.toString(result));
                }
                case "+/-" -> {
                    double temp = Double.parseDouble(display.getText());
                    temp = temp * (-1);
                    display.setText(Double.toString(temp));
                }
                case "<--" -> {
                    String text = display.getText();
                    if (!text.isEmpty()) {
                        display.setText(text.substring(0, text.length() - 1));
                    }
                }
                case "CE" -> display.setText("");
                default -> display.setText(display.getText() + command);
            }
        } catch (NumberFormatException ex) {
            display.setText("Error");
        }
    }

    private double calculate(double num1, double num2, char operator) {
        return switch (operator) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> {
                if (num2 != 0) {
                    yield num1 / num2;
                } else {
                    showMessageDialog(this, "Cannot divide by zero");
                    yield 0;
                }
            }
            case '%' -> num1 * num2 / 100;
            default -> 0;
        };
    }

    private void showMessageDialog(Component parentComponent, String message) {
        Dialog dialog = new Dialog(this, "Message", true);
        dialog.setLayout(new FlowLayout());
        Label label = new Label(message);
        Button okButton = new Button("OK");
        okButton.addActionListener(e -> dialog.setVisible(false));
        dialog.add(label);
        dialog.add(okButton);
        dialog.setSize(200, 100);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
