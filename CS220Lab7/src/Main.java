import java.io.FileNotFoundException;

public class Main {
    public static final String PROGRAM_NAME = "Max.asm";
    public static void main(String[] args) throws FileNotFoundException {
        try {
            AssemblyFileParser parser = new AssemblyFileParser(PROGRAM_NAME);
            System.out.println("First pass completed");
            System.out.println(parser);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }


    }
}
