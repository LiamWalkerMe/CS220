import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class MachineCodeWriter {

    public static void writeToBinaryFile(String fileName, List<Instruction> parsedAssemblyInstructions) throws FileNotFoundException {
        // loop through the list of instructions (A and C instructions)
        // and write the machine code to a file
        PrintWriter fileWriter = new PrintWriter(fileName);

        // loop through each instruction
        int lastIndex = parsedAssemblyInstructions.size() - 1;
        int currentIndex = 0;

        for (Instruction instruction : parsedAssemblyInstructions) {
            fileWriter.print(instruction.getMachineCode());
            if (currentIndex < lastIndex) {
                fileWriter.print("\n");
            }
            currentIndex++;
        }

        // close the file (saves it too)
        fileWriter.close();
    }
}
