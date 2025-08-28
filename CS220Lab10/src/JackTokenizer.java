import java.io.*;
import java.util.Arrays;
import java.util.List;

public class JackTokenizer {
    private PushbackReader fileReader;
    private PrintWriter fileWriter;

    private static final List<String> KEYWORDS_LIST =
            Arrays.asList("class", "constructor", "function",
                          "method", "field", "static", "var",
                          "int", "char", "boolean", "void", "true",
                          "false", "null", "this", "let", "do",
                          "if", "else", "while", "return");

    private static final List<Character> SYMBOLS_LIST =
            Arrays.asList('{', '}', '(', ')', '[', ']',
                          '.', ',', ';', '+', '-', '*',
                          '/', '&', '|', '<', '>', '=', '~');


    public JackTokenizer(String fileName) throws FileNotFoundException {
        fileReader = new PushbackReader(new FileReader(fileName + ".jack")); //input file
        fileWriter = new PrintWriter(fileName + "T.xml"); // output file
        // Print the root element <tokens> in the .xml file
        fileWriter.println("<tokens>");

    }

    public void parseTokens() throws IOException {
        // How to read a file, one character at a time instead of a String
        int c;
        // read the file as long as there are characters to read (i.e. not = to -1)
        while( (c = fileReader.read() ) != -1) {
            //Check for comments
            if(c=='/') {
                // get the next character (to determine if it's a // or /*)
                c = fileReader.read(); // it is okay to overwrite c because we've already checked for '/'
                if(!readComment(c)) {
                    printToken("symbol", "/");
                }


            }
            // let's check for a stringConstant (e.g "CS220") starting and ending with double quotes
            else if (c == '"') {
                String stringConst = readStringConst();
                printToken("stringConstant", stringConst);
            }
            // Check for symbol token
            else if (SYMBOLS_LIST.contains((char)c)) {
                printToken("symbol", escapeXMLSymbol(c));
            }
            // Check for keyowrd or identifier (e.g class (keyword), main (identifier))
            else if (Character.isLetter(c) || c == '_') { // if the character is a letter or an underscore
                String token = readToken(c);
                if(KEYWORDS_LIST.contains(token)) {
                    printToken("keyword", token); // <keyword> class </keyword> to the .xml file
                } else {
                    printToken("identifier", token); // <identifier> main </identifier>
                }
            }
            //Check for integerConstant
            else if (Character.isDigit(c)) {
                String intConst = ReadIntConst(c);
                printToken("integerConstant", intConst); //<integerConstant> 1234 </integerConstant>
            }
        }
    }

    private String ReadIntConst(int c) throws IOException {
        StringBuilder token = new StringBuilder();
        do {
            token.append( (char) c);
        } while ( (c = fileReader.read()) != -1 && Character.isDigit(c));
        fileReader.unread(c);
        return token.toString();
    }

    private String readToken(int c) throws IOException {
        StringBuilder token = new StringBuilder(); // token = "
        do {
            token.append((char) c);
            // keep apending the token as long as the character is a letter, digit, or underscore
            // e.g. class int, var, _identifier, number 2, etc.

        } while ( (c = fileReader.read()) != -1 && (Character.isLetterOrDigit(c) || c == '_'));
        // unread the character so we can continue reading the token
        // the next tokenization process can handle it appropriately
        fileReader.unread(c);
        return token.toString();
    }

    private String escapeXMLSymbol(int c) {
        switch(c) {
            case '<': return "&lt;";
            case '>': return "&gt;";
            case '&': return "&amp";
            default: return Character.toString((char) c);

        }
    }

    private String readStringConst() throws IOException {
        StringBuilder stringConst = new StringBuilder();
        int c;

        while( (c = fileReader.read() ) != -1 && c != '"') {
            stringConst.append((char) c );
        }

        return stringConst.toString();
    }

    private void printToken(String tag, String value) {
        fileWriter.println("\t<" + tag + ">" + value + "</" + tag + ">");
    }

    private boolean readComment(int c) throws IOException {
        // c is now the next character after the first '/' in the file
        switch (c) {
            case '/': // single line coment
                // read until the end of the line
                while ( ( c = fileReader.read() ) != '\n' && c != -1)  {
                    // do nothing since we're just reading the comment we don't write
                }
                break;
            case '*': // multi line comment
                while ( ( c = fileReader.read() ) != -1)  {
                    if(c == '*') { //check if the next character is *
                        if ( (c = fileReader.read()) == '/') {
                            return true;
                        } else { // if it isn't a forward slash, unread then continue
                            fileReader.unread(c);
                        }
                    }
                    // we keep reading until we search for a */
                }
                break;
            default: // it's not a comment at all, it's a division operator so unread second character
                fileReader.unread(c);
                return false;
        }
        return true;
    }


    public void close() throws IOException {
        fileWriter.println("</tokens>");

        fileReader.close();
        fileWriter.close();
    }
}
