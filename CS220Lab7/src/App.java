public class App {
    public static void main(String[] args) {
        //PART 1//
        String code = "  0;JMP  //unconditional jump  ";

        //indexOf to remove comments and trim white spaces
        int commentIndex = code.indexOf("//");
        if(commentIndex != -1) {
            code = code.substring(0, commentIndex);
        }
        System.out.println(code);

        //Trim the remaining code - remove leading and trailing whitespaces;
        code = code.trim();

        System.out.println(code);


        //PART 2//
        String cleanLine = "0;JMP";
        String jump = "";

        int semicolonIndex = cleanLine.indexOf(';');
        if(semicolonIndex != -1) {
            jump = cleanLine.substring(semicolonIndex + 1);
        }

        System.out.println("Jump Part: " + jump);

        //PART 3 //
        System.out.println("\nPART 3");

        String code2 = "D=M;JGT";
        String dest = "";
        String comp = "";
        String jump2 = "";

        if(code2.contains("=")) {
            String[] parts = code2.split("=");
            dest = parts[0];
            code2 = parts[1];

        }

        if(code.contains(";")) {
            String[] parts = code2.split(";");
            comp = parts[0];
            jump2 = parts[1];
        }
        else {
            comp = code2;
        }

        System.out.println(dest);
        System.out.println(comp);
        System.out.println(jump2);


    }
}
