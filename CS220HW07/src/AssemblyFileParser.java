import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AssemblyFileParser {
    private List<Instruction> parsedAssemblyInsturctions;
    private List<String> cleanAssemblyCode;
    private Scanner fileReader;

    // Constructor
    public AssemblyFileParser(String fileName) throws FileNotFoundException {
        cleanAssemblyCode = new ArrayList<>();
        parsedAssemblyInsturctions = new ArrayList<>();
        File assemlyFile = new File(fileName);

        // if the file does not exist or if the file is empty
        // print an error message and exit the program
        if (!assemlyFile.exists() || assemlyFile.length() == 0)
            throw new FileNotFoundException(fileName + " does not exist or is empty");
        
        // let's connect the fileReader Scanner to the fileName
        fileReader = new Scanner(assemlyFile);

        makeFirstPass(); //resolbed all labels
        makeSecondPass(); //resolve all variables and creates the A and C insctructions

        fileReader.close();
    }

    public List<Instruction> getParsedAssemblyInsturctions() {
        return  parsedAssemblyInsturctions;
    }

    // Method
    private void makeFirstPass() {
        // loop through the assembly file line by line
        String rawLine, cleanLine;
        int romAddress = 0;
        while (fileReader.hasNextLine()) {
            // read the next line
            rawLine = fileReader.nextLine();
            // clean the line
            cleanLine = clean(rawLine);
            // if the cleanLine is not empty, add it to the cleanAssemblyCode (our field) list
            if (!cleanLine.isEmpty())
                //Check to see if we have a label e.g. (OUTPUT_FIRST)
                if(cleanLine.startsWith("(") && cleanLine.endsWith(")")) {
                    insertLabelInSymbolTable(cleanLine, romAddress);
                } else {
                    cleanAssemblyCode.add(cleanLine);
                    // increment the ROM address
                    romAddress++;
                }

        }

    }

    private void insertLabelInSymbolTable(String cleanLine, int romAddress) {
        // remove the partentheses from the label
        String label = cleanLine.substring(1, cleanLine.length() - 1);
        // add the label to the SymbolTable e.g. (OUTPUT_FIRST, 10)
        SymbolTable.add(label, romAddress);

    }

    private void makeSecondPass() {
        int variableAddress = 16;

        // second pass resolves all variables into numbers
        // e.g @R15 -> @15
        // @sum -> 16

        for(String code: cleanAssemblyCode) {
            // Determine if the code is an A-instruction or C instruction
            if(code.startsWith("@")) {
                int address;
                try {
                    address = Integer.parseInt(code.substring(1));

                }
                catch (NumberFormatException e) {
                    //NumberFormatExcpetion means address was not a number
                    String symbol = code.substring(1);
                    if(SymbolTable.contains(symbol)) {
                        address = SymbolTable.getAddress(symbol);
                    } else {
                        SymbolTable.add(symbol, variableAddress);
                        address = variableAddress++;
                    }
                }

                parsedAssemblyInsturctions.add(new AInstruction("@" + address));
            } else {
                parsedAssemblyInsturctions.add(new CInstruction(code));
            }


        }
    }

    public String toString() {
        // loop through each line of the cleanAssemblyCode list
        // use enhanced for loop (for-each loop)
        StringBuilder output = new StringBuilder();
        for (String line: cleanAssemblyCode)
            output.append(line).append("\n");
        
        return output.toString();
    }

    public String clean(String rawLine) {
        // code can have different types of whitespace
        // spaces, tabs, newline, carriage returns

        // 1) start by replacing all whitespace with a single space
        // for example: rawLine = "       D=M           // D= first number     "
        String cleanLine = rawLine.replaceAll("\\s+", " ").trim();

        //2) remove any comments
        int commentIndex = cleanLine.indexOf("//");
        if (commentIndex != -1)
            cleanLine = cleanLine.substring(0, commentIndex).trim();
        
        return cleanLine;

    }

}
