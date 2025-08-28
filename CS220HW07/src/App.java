import java.io.FileNotFoundException;

public class App {
    public static final String PROGRAM_NAME = "Rect";
    public static void main(String[] args) {
        try {
            AssemblyFileParser parser = new AssemblyFileParser(PROGRAM_NAME + ".asm");
            //System.out.println("First pass completed successfully ==> cleaned Assembly code:");
            System.out.println(parser);
            MachineCodeWriter.writeToBinaryFile(PROGRAM_NAME + ".hack", parser.getParsedAssemblyInsturctions());
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }    
    }
}
