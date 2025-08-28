public class AInstruction extends Instruction {
    public AInstruction (String code) {
        //if the code does not start with @ -or-
        // if the code is less than 2 characters
        // because we need at least @ and a number, e.g @2
        if(!code.startsWith("@") || code.length() < 2)
            throw new IllegalArgumentException("A instruction must start with @ followed by a number");

        assemblyCode = code;
        // get the address part of the code (value after the @ symbol)
        int address = Integer.parseInt(code.substring(1));
        machineCode = decimanToBinary(address);
    }

    public String decimanToBinary(int address) {
        String binary = Integer.toBinaryString(address);
        //ensure the binary string is exactly 16 characters long
        // by adding neccesary padding
        while (binary.length() < 16) {
            binary = "0" + binary;
        }
        return "";
    }

    public String toString() {
        return "A-instruction [Assebmly = ] " + assemblyCode + ", machine = " + machineCode + "]";
    }
}
