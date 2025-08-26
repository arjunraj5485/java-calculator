import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };

    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    // operands and operator
    String A = "0";
    String operator = null;
    String B = null;

    public Calculator() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        // add buttons
        for (String value : buttonValues) {
            JButton button = new JButton(value);
            button.setFont(new Font("Arial", Font.PLAIN, 28));
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            if (Arrays.asList(topSymbols).contains(value)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(value)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            // button actions
            button.addActionListener(e -> handleButton(value));
            buttonsPanel.add(button);
        }

        frame.setVisible(true);
    }

    // handle button press
    void handleButton(String value) {
        if (Arrays.asList(rightSymbols).contains(value)) {
            if (value.equals("=")) {
                if (A != null && operator != null) {
                    B = displayLabel.getText();
                    double numA = Double.parseDouble(A);
                    double numB = Double.parseDouble(B);

                    switch (operator) {
                        case "+":
                            displayLabel.setText(removeZeroDecimal(numA + numB));
                            break;
                        case "-":
                            displayLabel.setText(removeZeroDecimal(numA - numB));
                            break;
                        case "×":
                            displayLabel.setText(removeZeroDecimal(numA * numB));
                            break;
                        case "÷":
                            if (numB != 0) {
                                displayLabel.setText(removeZeroDecimal(numA / numB));
                            } else {
                                displayLabel.setText("Error");
                            }
                            break;
                    }
                    clearAll();
                }
            } else { // operator pressed
                if (operator == null) {
                    A = displayLabel.getText();
                    displayLabel.setText("0");
                    B = "0";
                }
                operator = value;
            }
        } else if (Arrays.asList(topSymbols).contains(value)) {
            if (value.equals("AC")) {
                clearAll();
                displayLabel.setText("0");
            } else if (value.equals("+/-")) {
                double numDisplay = Double.parseDouble(displayLabel.getText());
                numDisplay *= -1;
                displayLabel.setText(removeZeroDecimal(numDisplay));
            } else if (value.equals("%")) {
                double numDisplay = Double.parseDouble(displayLabel.getText());
                numDisplay /= 100;
                displayLabel.setText(removeZeroDecimal(numDisplay));
            }
        } else if (value.equals("√")) {
            double numDisplay = Double.parseDouble(displayLabel.getText());
            if (numDisplay >= 0) {
                displayLabel.setText(removeZeroDecimal(Math.sqrt(numDisplay)));
            } else {
                displayLabel.setText("Error");
            }
        } else { // numbers and .
            if (value.equals(".")) {
                if (!displayLabel.getText().contains(".")) {
                    displayLabel.setText(displayLabel.getText() + ".");
                }
            } else if ("0123456789".contains(value)) {
                if (displayLabel.getText().equals("0")) {
                    displayLabel.setText(value);
                } else {
                    displayLabel.setText(displayLabel.getText() + value);
                }
            }
        }
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}