import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedHashMap;

final public class CalculatorBuilder {
    private JFrame frame;
    private JTextField textField;
    private JButton[] numberButtons = new JButton[CalculatorConfig.NO_OF_DIGITS];
    private JButton[] operatorBtns = new JButton[CalculatorConfig.NO_OF_OPERATORS];
    private JPanel panel;
    private LinkedHashMap<String, JButton> functionButtons = new LinkedHashMap<>();

    private Font font = new Font(CalculatorConfig.FONT_NAME, Font.BOLD, 30);

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JButton[] getNumberBtns() {
        return numberButtons;
    }

    public LinkedHashMap<String, JButton> getFunctionBtns() {
        return functionButtons;
    }

    public JPanel getPanel() {
        return panel;
    }

    public CalculatorBuilder setFrame() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);
        return this;
    }

    public CalculatorBuilder setTextField() {
        textField = new JTextField();
        textField.setBounds(50, 25, 300, 50);
        textField.setFont(font);
        textField.setEditable(false);
        return this;
    }

    public CalculatorBuilder setFunctionButtons() {
        int j = 0;
        for (String opString : CalculatorConfig.LABELS_NAME_MAP.keySet()) {
            functionButtons.put(opString, new JButton(opString));
            if (Arrays.asList(CalculatorConfig.OPERATORS_LABEL).contains(opString))
                operatorBtns[j++] = functionButtons.get(opString);
        }

        functionButtons.get(CalculatorConfig.BUTTON_SYMBOL.DELETE.getSymbol()).setBounds(50, 430, 145, 50);
        functionButtons.get(CalculatorConfig.BUTTON_SYMBOL.CLEAR.getSymbol()).setBounds(205, 430, 145, 50);

        for (String btn : functionButtons.keySet()) {
            functionButtons.get(btn).setFont(font);
            functionButtons.get(btn).setFocusable(false);
        }

        return this;
    }

    public CalculatorBuilder setNumberButtons() {
        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(font);
            numberButtons[i].setFocusable(false);
        }
        return this;
    }

    public CalculatorBuilder setPanel() {
        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        for (int i = 1, j = 0; i < numberButtons.length; i++) {
            panel.add(numberButtons[i]);
            if (i % 3 == 0) panel.add(operatorBtns[j++]);
            if (i == numberButtons.length - 1) {
                panel.add(functionButtons.get(CalculatorConfig.BUTTON_SYMBOL.DECIMAL.getSymbol()));
                panel.add(numberButtons[0]);
                panel.add(functionButtons.get(CalculatorConfig.BUTTON_SYMBOL.EQUALS.getSymbol()));
                panel.add(functionButtons.get(CalculatorConfig.BUTTON_SYMBOL.DIV.getSymbol()));
            }
        }

        return this;
    }

    public CalculatorApp build() {
        return new CalculatorApp(this);
    }
}
