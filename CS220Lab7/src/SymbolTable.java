import java.util.HashMap;

public class SymbolTable {
    private static HashMap<String, Integer> theTable;

    private static final String INITIAL_VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_.$:";
    private static final String ALL_VALID_CHARS = INITIAL_VALID_CHARS + "0123456789";


    static {
        theTable = new HashMap<>();
        // to add pairs (e.g. "R0", 0) to the table, we use the `put` method
        theTable.put("R0", 0);
        theTable.put("R1", 1);
        theTable.put("R2", 2);
        theTable.put("R3", 3);
        theTable.put("R4", 4);
        theTable.put("R5", 5);
        theTable.put("R6", 6);
        theTable.put("R7", 7);
        theTable.put("R8", 8);
        theTable.put("R9", 9);
        theTable.put("R10", 10);
        theTable.put("R11", 11);
        theTable.put("R12", 12);
        theTable.put("R13", 13);
        theTable.put("R14", 14);
        theTable.put("R15", 15);
        theTable.put("SCREEN", 16384);
        theTable.put("KBD", 24576);

        // we need to include these extra entries, even though we don't use them here
        theTable.put("SP", 0);     // stack pointer
        theTable.put("LCL", 1);    // base address of local segment
        theTable.put("ARG", 2);    // base address of argument segment
        theTable.put("THIS", 3);   // base address of this segment
        theTable.put("THAT", 4);   // base address of that segment
    }
    //Method

    private static boolean isValidName(String symbol) {
        // e.g symbol sum
        // loop through each character in the symbol

        String validChars = INITIAL_VALID_CHARS;

        for(int i = 0; i < symbol.length(); i++) {
            if(!validChars.contains(symbol.substring(i, i+1))) {
                return false;
            }
            validChars = ALL_VALID_CHARS;
        }

        return true;


    }

    public static boolean add (String symbol, int address) {
        // we can't add under 2 circumstance:
        // if the symbol is not a valid name - or if
        // the symbol already exist
        if(!isValidName(symbol) || theTable.containsKey(symbol)) {
            return false;
        }
        theTable.put(symbol, address);

        return true;
    }

    public static int getAddress(String symbol) {
        //if the symbol is not in the table, return -1
        if(!theTable.containsKey(symbol)) {
            return -1;
        }
        return theTable.get(symbol);
    }

}