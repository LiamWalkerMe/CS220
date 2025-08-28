// this class will write the assembly code into .asm file
// (it creates the .asm file)

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class CodeWriter {
    //stores the file (asm) we are writing to
    private PrintWriter fileWriter;

    // map all the VM commands to Assemvly instructions
    private static final HashMap<String, String> commandMap;
    private static final HashMap<String, String> segmentMap;

    private int labelCounter = 0;

    //to initialize the commandMap(static variable)
    // we need to use a static block
    //initializer block:

    static {
        // command map
        commandMap = new HashMap<>(9);
        commandMap.put("add", "M=D+M");
        commandMap.put("sub", "M=M-D");
        commandMap.put("and", "M=D&M");
        commandMap.put("or", "M=D|M");
        commandMap.put("neg", "M=-M");
        commandMap.put("not", "M=!M");
        commandMap.put("eq", "JEQ");
        commandMap.put("gt", "JGT");
        commandMap.put("lt", "JLT");

        // the segemntMap
        segmentMap = new HashMap<>(7);
        segmentMap.put("local", "@LCL");
        segmentMap.put("argument", "@ARG");
        segmentMap.put("this", "@THIS");
        segmentMap.put("that", "@THAT");
        segmentMap.put("temp", "@R");
        segmentMap.put("constant", null);

    }

    public CodeWriter(String fileName) throws FileNotFoundException {
        fileWriter = new PrintWriter(fileName + ".asm");
        // add the static segment to the segmentMap
        segmentMap.put("static", "@" + fileName + ".");
    }

    public void writeCode(String command, String segment, int index) {
        switch (command) {
            case "push":
                generatePushASMCode(command, segment, index);
                break;
            case "pop":
                generatePopASMCode(command, segment, index);
                break;

            case "add":
            case "sub":
            case "and":
            case "or":
                popStack();
                fileWriter.println("A=A-1");
                fileWriter.println(commandMap.get(command));
                break;

            case "neg":
            case "not":
                fileWriter.println("@SP");
                fileWriter.println("A=M-1");
                //loopup the command in the commandMap for neg or not
                fileWriter.println(commandMap.get(command));
                break;

            case "eq":
            case "gt":
            case "lt":

                popStack();
                fileWriter.println("A=A-1");
                fileWriter.println("D=M-D");
                String label = "IF" + labelCounter++;
                fileWriter.println("@" + label);
                fileWriter.println("D;" + commandMap.get(command));
                fileWriter.println("@SP");
                fileWriter.println("A=M-1");
                fileWriter.println("M=0");
                fileWriter.println("@END_" + label);
                fileWriter.println("0;JMP");
                fileWriter.println("(" + label + ")");
                fileWriter.println("@SP");
                fileWriter.println("A=M-1");
                fileWriter.println("M=-1");
                fileWriter.println("(END_" + label + ")");

                break;
        }
    }

    private void generatePushASMCode(String command, String segment, int index) {
        switch (segment) {
            case "constant":
                fileWriter.println("@" + index);
                fileWriter.println("D=A");
                break;
            case "argument":
            case "local":
            case "this":
            case "that":
                fileWriter.println("@" + index);
                fileWriter.println("D=A");
                fileWriter.println(segmentMap.get(segment));
                fileWriter.println("A=D+M");
                fileWriter.println("D=M");
                break;
            case "static":
                fileWriter.println(segmentMap.get(segment) + index); //@SimpleAdd.2
                fileWriter.println("D=M");
                break;

            case "pointer":
                if (index == 0) {
                    fileWriter.println(segmentMap.get("this"));
                } else {
                    fileWriter.println(segmentMap.get("that"));
                }
                fileWriter.println("D=M");
                break;
            case "temp":
                fileWriter.println(segmentMap.get(segment) + (index + 5));
                fileWriter.println("D=M");
                break;
        }
        pushStack();

    }

    private void generatePopASMCode(String command, String segment, int index) {
        switch (segment) {
            case "constant":
                return;
            case "argument":
            case "local":
            case "this":
            case "that":
                fileWriter.println("@" + index);
                fileWriter.println("D=A");
                fileWriter.println(segmentMap.get(segment)); // @LCL, @ARG
                fileWriter.println("D=D+M");
                fileWriter.println("@temploc");
                fileWriter.println("M=D");

                popStack(); // pop the stack

                fileWriter.println("@temploc");
                fileWriter.println("A=M");
                fileWriter.println("M=D");
                break;
            case "static":
            case "pointer":
            case "temp":
                //pop the stack first
                popStack();

                //handle static variables
                if("static".equals(segment)) {
                    fileWriter.println(segmentMap.get(segment) + index); // @SimpleAdd.2
                }
                else if ("pointer".equals(segment)) {
                    if(index == 0) {
                        fileWriter.println(segmentMap.get("this"));
                    } else if (index == 1) { // if pointer 1
                        fileWriter.println(segmentMap.get("that"));
                    }
                } else {
                    fileWriter.println(segmentMap.get(segment) + (index + 5)); // if index is 2 + R5 then index will be 7
                }
                fileWriter.println("M=D"); // stor value in correct location in memory

                break;
        }
    }

    private void popStack() {
        fileWriter.println("@SP");
        fileWriter.println("AM=M-1");
        fileWriter.println("D=M");
    }

    private void pushStack() {
        fileWriter.println("@SP");
        fileWriter.println("M=M+1");
        fileWriter.println("A=M-1");
        fileWriter.println("M=D");
    }

    public void close() {
        fileWriter.println("(INFINITE_LOOP)");
        fileWriter.println("@INFINITE_LOOP");
        fileWriter.println("0;JMP");
        fileWriter.close();

    }


}
