import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

public class CalculatorApp implements ActionListener {

    private JFrame frame;
    private JTextField textField;
    private JButton[] numberButtons;
    private JPanel panel;
    private LinkedHashMap<String, JButton> functionButtons;

    public CalculatorApp(CalculatorBuilder builder) {
        this.frame = builder.getFrame();
        this.textField = builder.getTextField();
        this.numberButtons = builder.getNumberBtns();
        this.functionButtons = builder.getFunctionBtns();
        this.panel = builder.getPanel();
        setupUI();
		setEventListeners();
    }

    private void setupUI() {
        frame.add(panel);
        frame.add(functionButtons.get(CalculatorConfig.BUTTON_SYMBOL.DELETE.getSymbol()));
        frame.add(functionButtons.get(CalculatorConfig.BUTTON_SYMBOL.CLEAR.getSymbol()));
        frame.add(textField);
        frame.setVisible(true);
    }

	private void setEventListeners() {
		for (String btn : functionButtons.keySet())
            functionButtons.get(btn).addActionListener(e -> actionPerformed(e));
		for (int i = 0; i < numberButtons.length; i++) 
			numberButtons[i].addActionListener(e -> actionPerformed(e));
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < CalculatorConfig.NO_OF_DIGITS; i++) {
            if (e.getSource() == numberButtons[i]) {
                textField.setText(textField.getText().concat(String.valueOf(i)));
                return;
            }
        }
        if (e.getSource() == functionButtons.get(CalculatorConfig.BUTTON_SYMBOL.DECIMAL.getSymbol()))
          	textField.setText(CalculatorUtils.writeDecimalPoint(e, textField.getText()));
        else if (e.getSource() == functionButtons.get(CalculatorConfig.BUTTON_SYMBOL.CLEAR.getSymbol()))
        	textField.setText("");
        else if (e.getSource() == functionButtons.get(CalculatorConfig.BUTTON_SYMBOL.DELETE.getSymbol()))
            textField.setText(CalculatorUtils.handleDelPress(e, textField.getText()));
		else if(e.getSource() == functionButtons.get(CalculatorConfig.BUTTON_SYMBOL.EQUALS.getSymbol()))
			textField.setText(CalculatorUtils.evaluteExp(textField.getText()));
		else
			textField.setText(CalculatorUtils.writeOperators(e, textField.getText(), functionButtons));
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
		CalculatorApp calci = new CalculatorBuilder()
                .setFrame()
                .setTextField()
                .setFunctionButtons()
                .setNumberButtons()
                .setPanel()
                .build();
    }
}
