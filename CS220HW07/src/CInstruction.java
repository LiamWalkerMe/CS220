import java.util.HashMap;
public class CInstruction extends Instruction {
    private String dest;
    private String comp;
    private String jump;

    private static HashMap<String, String> destCodes = new HashMap<>(8);
    private static HashMap<String, String> jumpCodes = new HashMap<>(8);
    private static HashMap<String, String> compCodes = new HashMap<>(34);

    static {
        //DEST CODES //
        destCodes.put("null", "000");
        destCodes.put("M",    "001");
        destCodes.put("D",    "010");
        destCodes.put("MD",   "011");
        destCodes.put("A",    "100");
        destCodes.put("AM",   "101");
        destCodes.put("AD",   "110");
        destCodes.put("AMD",  "111");

        // JMP CODES //
        jumpCodes.put("null", "000");
        jumpCodes.put("JGT", "001");
        jumpCodes.put("JEQ", "010");
        jumpCodes.put("JGE", "011");
        jumpCodes.put("JLT", "100");
        jumpCodes.put("JNE", "101");
        jumpCodes.put("JLE", "110");
        jumpCodes.put("JMP", "111");

        //COMP CODES //

        // a = 0
        compCodes.put("0",    "0101010");
        compCodes.put("1",    "0111111");
        compCodes.put("-1",   "0111010");
        compCodes.put("D",    "0001100");
        compCodes.put("A",    "0110000");
        compCodes.put("!D",   "0001101");
        compCodes.put("!A",   "0110001");
        compCodes.put("-D",   "0001111");
        compCodes.put("-A",   "0110011");
        compCodes.put("D+1",  "0011111");
        compCodes.put("A+1",  "0110111");
        compCodes.put("D-1",  "0001110");
        compCodes.put("A-1",  "0110010");
        compCodes.put("D+A",  "0000010");
        compCodes.put("A+D",  "0000010");
        compCodes.put("D-A",  "0010011");
        compCodes.put("A-D",  "0000111");
        compCodes.put("D&A",  "0000000");
        compCodes.put("A&D",  "0000000");
        compCodes.put("D|A",  "0010101");
        compCodes.put("A|D",  "0010101");

        // a = 1
        compCodes.put("M",    "1110000");
        compCodes.put("!M",   "1110001");
        compCodes.put("-M",   "1110011");
        compCodes.put("M+1",  "1110111");
        compCodes.put("M-1",  "1110010");
        compCodes.put("D+M",  "1000010");
        compCodes.put("M+D",  "1000010");
        compCodes.put("D-M",  "1010011");
        compCodes.put("M-D",  "1000111");
        compCodes.put("D&M",  "1000000");
        compCodes.put("M&D",  "1000000");
        compCodes.put("D|M",  "1010101");
        compCodes.put("M|D",  "1010101");


    }

    // Constructor
    // e.g code == "D-A

    public CInstruction (String code) {
        this.assemblyCode = code;
        parseCodeIntoParts();
    }

    private void parseCodeIntoParts() {
        // e.g D=A -> dest = 'D" , jump = "Null"
        // we need to split the code into dest, comp, jump
        // or another example: D;JNE -> dest = "null" comp = "D", jump = "JNE"

        dest = "null";
        jump = "null";

        int assignmentIndex = assemblyCode.indexOf("=");

        if(assignmentIndex != -1) {
            dest = assemblyCode.substring(0,assignmentIndex).trim();

        }
        int jumpIndex = assemblyCode.indexOf(";");

        if(jumpIndex != -1) {
            jump = assemblyCode.substring(jumpIndex + 1).trim();
        }

        int beginIndex = 0;
        if (assignmentIndex != -1) {
            beginIndex = assignmentIndex + 1;
        }
        int endIndex = assemblyCode.length();

        if(jumpIndex != -1) {
            endIndex = jumpIndex;
        }
        comp = assemblyCode.substring(beginIndex, endIndex).trim();

        machineCode = "111" + compCodes.get(comp) + destCodes.get(dest) + jumpCodes.get(jump);
    }

    public String toString() {
        return "C-instruction [" +
                "Dest = " + dest +
                ", Comp = " + comp +
                ", Jump = " + jump +
                ", machineCode = " + machineCode + "]";
    }

}
