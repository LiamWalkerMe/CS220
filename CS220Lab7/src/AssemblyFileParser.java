import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssemblyFileParser {
    private List<String> cleanAssemblyCode;
    private Scanner fileReader;

    // Constructor
    public AssemblyFileParser(String fileName) throws FileNotFoundException {
        cleanAssemblyCode = new ArrayList<>();
        File assemblyFile = new File(fileName);

        // if the file does not exist or if the file is empty
        //print an error message and exit the program
        if(!assemblyFile.exists() || assemblyFile.length() == 0) {
            throw new FileNotFoundException(fileName + " does not exist or is empty");
        }
        // let's connect the fileReaderScanner to the fileName
        fileReader = new Scanner(assemblyFile);
        makeFirstPass();
    }
    private void makeFirstPass() {
        // loop through the assembly file line by line
        String rawLine, cleanLine;
            while(fileReader.hasNextLine()) {
                rawLine = fileReader.nextLine();
                cleanLine = clean(rawLine);
                //if the cleanline is not empty add it to the cleanAssemblyCode (our field) list
                if(!cleanLine.isEmpty()) {
                    cleanAssemblyCode.add(cleanLine);
                }
            }
    }

    //Methods
    public String clean(String rawLine) {
        // code can have different types of whitespace
        // spaces, tabs, newline, carriage returns

        // 1) start by replacing all whitespace with a single space
        // for example: rawLine = "             D = M              // D=firstnumber"
        String cleanLine = rawLine.replaceAll("\\s+", " ").trim();


        // 2) remove any comments
        int commentIndex = cleanLine.indexOf("//");
        if(commentIndex != -1) {
            cleanLine = cleanLine.substring(0, commentIndex).trim();
        }
        return cleanLine;

    }

    @Override
    public String toString() {
        //loop through each line of the cleanAssemblyCode list
        // use enhance for loop
        StringBuilder output = new StringBuilder();
        for (String line : cleanAssemblyCode) {
           output.append(line).append("\n");
        }
        return output.toString();
    }
}
