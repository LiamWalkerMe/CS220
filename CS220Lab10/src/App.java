import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        //Initalize Variables
        String fileName;
        Scanner keyboard = new Scanner(System.in);

        //Ask for input
        System.out.print("Please enter the name of the jack file (without extension): ");

        try {
            //Gather input
            fileName = keyboard.nextLine();

            // Create an instance of Jack Tokenizer object
            JackTokenizer jt = new JackTokenizer(fileName);

            // Parse all tokens in the Jack file
            jt.parseTokens();

            //Close the file

            jt.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
