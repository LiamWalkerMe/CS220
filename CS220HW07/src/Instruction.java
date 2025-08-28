public abstract class Instruction {
    // Fields: since wer want to pass the fields
    // to the subclasses (children classes), we'll make them protected
    protected String assemblyCode;
    protected String machineCode;

    public String getAssemblyCode() {
        return assemblyCode;
    }
    
    public String getMachineCode() {
        return machineCode;
    }

}
