public class Mnemonic {
    String instr,instClass;
    int opcode,length;
    Mnemonic(String instr,String instClass,int opcode,int length){
        this.instr = instr;
        this.instClass = instClass;
        this.opcode = opcode;
        this.length = length;
    }
    public boolean isDeclarative() {
		return instClass.equals("DL");
	}
	
	public boolean isAssemblerDirective() {
		return instClass.equals("AD");
	}
	
	public boolean isImperative() {
		return instClass.equals("IS");
	}
}