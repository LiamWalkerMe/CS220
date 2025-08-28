import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    private String command;
    private String segment;
    private int index;
    private final Scanner fileScanner;

    // Constructor
    public Parser(String fileName) throws FileNotFoundException {
        fileScanner = new Scanner(new File(fileName + ".vm"));
    }

    public void close() {
        fileScanner.close();
    }

    public String getCommand() {
        return command;
    }

    public String getSegment() {
        return segment;
    }

    public int getIndex() {
        return index;
    }

    public boolean hasMoreCommands() {
        return fileScanner.hasNextLine(); // return true if there's line in the file
    }

    // ex1: "push local 2" -> command = "push", segment = "local", index = 2
    // ex2: "add" -> command = "add", segemnt = "", index = -1;
    // ex3: // a comment -> command="", segment = "", index = -1

    public void advance() {
        resetCommand();
        // getCleanLine will remove comments and leading or trailing whitespaces
        String cleanLine = getCleanLine(fileScanner.nextLine());
        String[] parts = cleanLine.split(" "); //split line by space
        command = parts[0];
        if(parts.length == 3 ) { // e.g "push local 2", but not "add"
            segment = parts[1];
            // if we say:int index = parts[2]
            // convert the 3rd part of array 'parts' to an integer
            index = Integer.parseInt(parts[2]);
        }


    }

    // reset the command, segment, and index every time we call advance()
    private void resetCommand() {
        command = "";
        segment = "";
        index = -1;
    }


    private String getCleanLine(String rawLine) {
        String cleanLine = rawLine;
        int commentIndex = cleanLine.indexOf("//");

        if(commentIndex != -1) {
            cleanLine = cleanLine.substring(0, commentIndex).trim();
        }
        return cleanLine;

    }

}
