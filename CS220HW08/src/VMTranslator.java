import java.io.FileNotFoundException;
import java.util.Scanner;

public class VMTranslator {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName;
        // reads the user input for file = "SimpleAdd.vm")
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the name of the file to translate (without extension): ");
        fileName = keyboard.nextLine(); // our fileName comes from the keyboard (user input)

        // Create a new Parser object
        try {
            Parser parser = new Parser(fileName);
            CodeWriter writer = new CodeWriter(fileName);
            // as long as there are more commands in the file
            while (parser.hasMoreCommands()) {
                // Advance one line in the .vm file
                // and divide the line into 3 parts (command, segment, and index)
                parser.advance();
                // Write the ASM code for each line of VM command (current command)
                // to the .asm file using the CodeWriter object
                writer.writeCode(parser.getCommand(), parser.getSegment(), parser.getIndex());
                //print the current command, debugging purpose

            }

            parser.close();
            writer.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }



    }
}
