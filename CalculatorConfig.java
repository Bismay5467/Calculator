import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

final public class CalculatorConfig {
    private static final CalculatorConfig CalculatorConfigInstance = new CalculatorConfig();

    public static final int NO_OF_OPERATORS = 4;
    public static final int NO_OF_DIGITS = 10;
    public static final String FONT_NAME = "Ink Free";
    public static final String[] OPERATORS_LABEL = { "+", "-", "*", "/" };

    public enum BUTTON_SYMBOL {
        ADD("+", "ADD"),
        SUB("-", "SUB"),
        MUL("*", "MUL"),
        DIV("/", "DIV"),
        DECIMAL(".", "DECIMAL"),
        EQUALS("=", "EQUALS"),
        DELETE("Delete", "DELETE"),
        CLEAR("Clear", "CLEAR");

        private final String symbol;
        private final String label;

        BUTTON_SYMBOL(String symbol, String label) {
            this.symbol = symbol;
            this.label = label;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getLabel() {
            return label;
        }
    }

    @SuppressWarnings("unused")
    public static final LinkedHashMap<String, String> LABELS_NAME_MAP = Arrays.stream(BUTTON_SYMBOL.values())
        .collect(Collectors.toMap(
            BUTTON_SYMBOL::getSymbol,
            BUTTON_SYMBOL::getLabel,
            (oldValue, newValue) -> oldValue,
            LinkedHashMap::new
        ));


    private CalculatorConfig() {}

    public static CalculatorConfig getInstance() {
        return CalculatorConfigInstance;
    }
}