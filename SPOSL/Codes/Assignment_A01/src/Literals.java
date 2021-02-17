public class Literals {
    String literal;
    int address,index;
    
    public Literals(String literal, int address) {
		this.literal = literal;
		this.address = address;
		index = 0;
	}

	public Literals(String literal, int address, int index) {
		this.literal = literal;
		this.address = address;
		this.index = index;
	}

	public Literals setAddress(int address) {
		this.address = address;
		return this;
	}

	public Literals setIndex(int index) {
		this.index = index;
		return this;
	}
}