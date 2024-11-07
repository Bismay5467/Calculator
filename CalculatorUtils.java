import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.JButton;

final public class CalculatorUtils {
  	static String writeDecimalPoint(ActionEvent e, String textField) {
		String currentText = textField.trim();
		if(currentText.length() == 0)
			return "0".concat(CalculatorConfig.BUTTON_SYMBOL.DECIMAL.getSymbol());
		if(currentText.concat(CalculatorConfig.BUTTON_SYMBOL.DECIMAL.getSymbol()).contains(".."))
			return currentText;
		String lastChar = String.valueOf(currentText.charAt(currentText.length() - 1));
		if(Arrays.asList(CalculatorConfig.OPERATORS_LABEL).contains(lastChar))
			return currentText + "0".concat(CalculatorConfig.BUTTON_SYMBOL.DECIMAL.getSymbol());
		return currentText.concat(CalculatorConfig.BUTTON_SYMBOL.DECIMAL.getSymbol());
	} 

	static String handleDelPress(ActionEvent e, String currentText) {
		if(currentText.length() == 0) 
			return currentText;
		return currentText.substring(0, currentText.length() - 1);
	}

	static String writeOperators(ActionEvent e, String textField, LinkedHashMap<String, JButton> functionButtons) {
		String currentText = textField.trim();
    	String lastChar = currentText.isEmpty() ? "" : currentText.substring(currentText.length() - 1);
    	String subSymbol = CalculatorConfig.BUTTON_SYMBOL.SUB.getSymbol();
		if ((currentText.isEmpty() && e.getSource() != functionButtons.get(subSymbol)) 
			|| lastChar.equals(CalculatorConfig.BUTTON_SYMBOL.DECIMAL.getSymbol())) 
        	return currentText;
    	if (Arrays.asList(CalculatorConfig.OPERATORS_LABEL).contains(lastChar)) {
        	return lastChar.equals(subSymbol) || e.getSource() != functionButtons.get(subSymbol)
            	? currentText
            	: currentText + subSymbol;
    	}
    	return functionButtons.entrySet().stream()
        	.filter(entry -> e.getSource() == entry.getValue() 
				&& Arrays.asList(CalculatorConfig.OPERATORS_LABEL).contains(entry.getKey()))
        	.map(Map.Entry::getKey)
        	.findFirst()
        	.map(opString -> currentText + opString)
        	.orElse(currentText);
	}

	private static boolean isNumber(String number) {
		try {
			Double.parseDouble(number);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

	private static int precedence(char operator) {
        if (operator == '+' || operator == '-') 
			return 1;
        if (operator == '*' || operator == '/') 
			return 2;
        return -1;
    }

	private static double applyOperation(double a, double b, char operator) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': 
				if(b == 0.0D)
					throw new ArithmeticException("Division by zero not allowed");
				return a / b;
            default: throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

	static String evaluteExp(String exp) {
		try {
			exp = exp.replaceAll(" ", "");
        	exp = exp.replaceAll("(?<=\\d)(?=[+\\-*/])", " ");
        	exp = exp.replaceAll("(?<=[+\\-*/])(?=\\d)", " ");
        	String[] tokens = exp.split(" ");
			Stack<Double> numbers = new Stack<>();
			Stack<Character> operators = new Stack<>();
			for(String token : tokens) {
				if(isNumber(token))
					numbers.push(Double.parseDouble(token));
				else {
					while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token.charAt(0))) {
						double b = numbers.pop();
						double a = numbers.pop();
						char op = operators.pop();
						numbers.push(applyOperation(a, b, op));
                	}
                	operators.push(token.charAt(0));
            	}
			}
			while (!operators.isEmpty()) {
            	double b = numbers.pop();
            	double a = numbers.pop();
            	char op = operators.pop();
            	numbers.push(applyOperation(a, b, op));
        	}
			return String.valueOf(numbers.pop());
		} catch (Exception e) {
			System.out.println("Error evaluating expression: " + e.getMessage());
			return "Err";
		}
	}
}
