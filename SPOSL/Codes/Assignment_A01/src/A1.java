import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class A1 {
    Hashtable<String, Mnemonic> mnemonics = new Hashtable<>();
    Hashtable<String, Integer> symtab = new Hashtable<>();
    ArrayList<Literals> littab = new ArrayList<>();
    ArrayList<String> doneSym = new ArrayList<>();
    Hashtable<String, Integer> registers = new Hashtable<>();
    Hashtable<String, Integer> conditional = new Hashtable<>();
    ArrayList<Integer> pooltab = new ArrayList<>();
    int loc_cntr = 0;
    int key = 0;
    int pooltab_pntr = 0;
    int littab_pntr = 0;
    Literals literalEntry;
    int firstLineNumber = 1;

    A1() {
        mnemonics.put("STOP", new Mnemonic("STOP", "IS", 0, 1));
        mnemonics.put("ADD", new Mnemonic("ADD", "IS", 1, 1));
        mnemonics.put("SUB", new Mnemonic("SUB", "IS", 2, 1));
        mnemonics.put("MULT", new Mnemonic("MULT", "IS", 3, 1));
        mnemonics.put("MOVER", new Mnemonic("MOVER", "IS", 4, 1));
        mnemonics.put("MOVEM", new Mnemonic("MOVEM", "IS", 5, 1));
        mnemonics.put("COMP", new Mnemonic("COMP", "IS", 6, 1));
        mnemonics.put("BC", new Mnemonic("BC", "IS", 7, 1));
        mnemonics.put("DIV", new Mnemonic("DIV", "IS", 8, 1));
        mnemonics.put("READ", new Mnemonic("READ", "IS", 9, 1));
        mnemonics.put("PRINT", new Mnemonic("PRINT", "IS", 10, 1));

        mnemonics.put("START", new Mnemonic("START", "AD", 1, 1));
        mnemonics.put("END", new Mnemonic("END", "AD", 2, 1));
        mnemonics.put("ORIGIN", new Mnemonic("ORIGIN", "AD", 3, 1));
        mnemonics.put("EQU", new Mnemonic("EQU", "AD", 4, 1));
        mnemonics.put("LTORG", new Mnemonic("LTORG", "AD", 5, 1));

        mnemonics.put("DC", new Mnemonic("DC", "DL", 1, 1));
        mnemonics.put("DS", new Mnemonic("DS", "DL", 2, 1));

        registers.put("AREG", 1);
        registers.put("BREG", 2);
        registers.put("CREG", 3);
        registers.put("DREG", 4);

        conditional.put("LT", 1);
        conditional.put("LE", 2);
        conditional.put("EQ", 3);
        conditional.put("GT", 4);
        conditional.put("GE", 5);
        conditional.put("ANY", 6);
    }

    void execute() throws Exception {
        int line = 0;
        int sym_count = 0, const_count = 0;
        String data = "";
        pooltab.add(pooltab_pntr);
        BufferedReader in = new BufferedReader(new FileReader("assembler.asm"));
        BufferedWriter out = new BufferedWriter(new FileWriter("IC.txt"));
        while ((data = in.readLine()) != null) {
            line++;
            String[] words = data.split("\\s+");
            // System.out.println((words[1]));
            if (words[0].length() > 0) {
                String label = words[0].toUpperCase();
                if (!symtab.containsKey(label)) {
                    symtab.put(label, loc_cntr);
                }
            }
            if (words[1].toUpperCase().equals("START")) {
                loc_cntr = Integer.parseInt(words[2]);
                Mnemonic mn = mnemonics.get(words[1].toUpperCase());
                out.write("---\t(" + mn.instClass + "," + mn.opcode + ")\t(C," + loc_cntr + ")\n");
                firstLineNumber = 2;
            } else if (words[1].toUpperCase().equals("ORIGIN")) {
                if (words[2].contains("+")) {
                    String[] origin = words[2].split("\\+");
                    loc_cntr = symtab.get(origin[0]) + Integer.parseInt(origin[1]);
                } else if (words[2].contains("-")) {
                    String[] origin = words[2].split("\\-");
                    loc_cntr = symtab.get(origin[0]) - Integer.parseInt(origin[1]);
                } else
                    loc_cntr = symtab.get(words[2]);
                out.append("---\t(AD,3) \t " + words[2] + "\n");
                // loc_cntr++;
                out.append("\n");
            } else if (mnemonics.get(words[1]) != null && mnemonics.get(words[1]).isDeclarative()) {
                if (words[1].equals("DS")) {
                    int size = Integer.parseInt(words[2]);
                    // sym_count++;
                    out.append("" + loc_cntr + "\t(DS," + mnemonics.get(words[1]).opcode + ")\t(C," + size + ")\n");
                    loc_cntr += size;
                } else {
                    String w2[] = words[2].split("\\'");
                    out.append("" + loc_cntr + "\t(DC," + mnemonics.get(words[1]).opcode + ")\t(C," + w2[1] + ")\n");
                    loc_cntr++;
                }
            } else if (words[1].equals("EQU")) {
                if (!symtab.contains(words[0].toUpperCase())) {
                    int address = symtab.get(words[2].toUpperCase());
                    symtab.put(words[0].toUpperCase(), address);
                    out.append("---\n");
                    loc_cntr++;
                }
            } else if (words[1].toUpperCase().equals("LTORG")) {
                int ptr = pooltab.get(pooltab_pntr);
                for (int i = ptr; i < littab_pntr; i++) {
                    literalEntry = littab.get(0);
                    String literal = littab.get(0).literal;
                    // System.out.println(literal);
                    String val[] = literal.split("\\=");
                    String value[] = val[1].split("\\'");
                    // System.out.println(value[1]);
                    littab.remove(literalEntry);
                    littab.add(new Literals(literal, loc_cntr++));
                    out.write(""+loc_cntr+"\t(DL,01)\t(C," + value[1] + ")\n");
                }
                pooltab_pntr++;
                // littab_pntr++;
                pooltab.add(littab_pntr);

            } else if (words[1].toUpperCase().equals("END")) {
                out.append("(AD,04)\n");
                int ptr = pooltab.get(pooltab_pntr);
                for (int i = ptr; i < littab_pntr; i++) {
                    literalEntry = littab.get(i);
                    String literal = littab.get(i).literal;
                    // System.out.println(literal);
                    String val[] = literal.split("\\=");
                    String value[] = val[1].split("\\'");
                    // System.out.println(value[1]);
                    littab.remove(literalEntry);
                    littab.add(new Literals(literal, loc_cntr++));
                    out.write("(DL,01)\t(C," + value[1] + ")\n");
                }
                pooltab_pntr++;
                littab_pntr++;
                if(!littab.isEmpty())
                    pooltab.add(littab_pntr);
                break;
            } else if (mnemonics.get(words[1]) != null && mnemonics.get(words[1]).isImperative()) {
                Mnemonic mn = mnemonics.get(words[1]);
                // System.out.println(words[2]);
                if (words[1].equals("STOP")) {
                    out.write("\t(IS,0)\n");
                    loc_cntr++;
                } else {
                    String[] operands = words[2].split("\\,");
                    String operand;

                    if (operands.length == 1) {
                        operand = operands[0];
                    } else {
                        operand = operands[1];
                    }
                    if (operand.contains("=")) {
                        boolean present = false;
                        int idx = 0;
                        for (int i = pooltab.get(pooltab_pntr); i < littab_pntr; i++) {
                            if (littab.get(i).literal.equals(operands[1])) {
                                present = true;
                                idx = i;
                                break;
                            }
                        }
                        if (!present) {
                            literalEntry = new Literals(operands[1], -1, littab_pntr++);
                            littab.add(literalEntry);
                        } else {
                            literalEntry = littab.get(idx);
                        }
                        out.append("" + loc_cntr + "\t(" + mn.instClass + "," + mn.opcode + ")\t" + operands[0] + " (L,"
                                + littab_pntr + ")\n");
                    } else {
                        if (operands.length > 1) {
                            if ((!conditional.containsKey(operands[1])) && (!registers.containsKey(operands[1])) && !doneSym.contains(operands[1])) {
                                doneSym.add(operands[1]);
                                sym_count++;
                            }
                        }
                        out.append("" + loc_cntr + "\t(" + mn.instClass + "," + mn.opcode + ")\t" + words[2] + "\n");
                    }
                    loc_cntr++;
                }
            } else {
                System.err.println("INVALID OPCODE! AT LINE: " + line);
                return;
            }
        }
        in.close();
        out.close();
        if (sym_count != symtab.size()) {
            System.out.println("ERROR: UNDEFINED SYMBOL");
            return;
        }
        BufferedWriter sym = new BufferedWriter(new FileWriter("Symtab.txt"));
        BufferedWriter pool = new BufferedWriter(new FileWriter("Pooltab.txt"));
        BufferedWriter lit = new BufferedWriter(new FileWriter("Littab.txt"));
        Enumeration e = symtab.elements();
        Enumeration keys = symtab.keys();
        sym.write("SYMBOL\t ADDRESS\n");
        sym.flush();
        while (e.hasMoreElements()) {
            sym.write(""+keys.nextElement()+"\t\t"+e.nextElement()+"\n");
            sym.flush();
        }
        pool.write("Index\n");
        for(int i:pooltab){
            pool.flush();
            pool.write(i + "\n");
        }
        lit.write("Literal\tAddress\n");
        for(Literals i:littab){
            // System.out.println(i.literal);
            lit.write(i.literal + "\t" + i.address + "\n");
            lit.flush();
        }
    }

    public static void main(String[] args) throws Exception {
        A1 obj = new A1();
        obj.execute();
    }
}