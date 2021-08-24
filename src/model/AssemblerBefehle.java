//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

import java.util.HashMap;

public class AssemblerBefehle {
	private HashMap<String, Integer> befehlstabelle = new HashMap(80);
	private HashMap<Integer, String> reversetabelle = new HashMap(40);
	private static AssemblerBefehle dasObjekt = new AssemblerBefehle();

	public static AssemblerBefehle AssemblerbefehleGeben() {
		return dasObjekt;
	}

	private AssemblerBefehle() {
		this.befehlstabelle.put("NOOP", 0);
		this.befehlstabelle.put("HOLD", 99);
		this.befehlstabelle.put("RESET", 1);
		this.befehlstabelle.put("PUSH", 25);
		this.befehlstabelle.put("POP", 26);
		this.befehlstabelle.put("RSV", 7);
		this.befehlstabelle.put("REL", 8);
		this.befehlstabelle.put("JSR", 5);
		this.befehlstabelle.put("RTS", 6);
		this.befehlstabelle.put("NOT", 46);
		this.befehlstabelle.put("ADD", 10);
		this.befehlstabelle.put("SUB", 11);
		this.befehlstabelle.put("MUL", 12);
		this.befehlstabelle.put("DIV", 13);
		this.befehlstabelle.put("MOD", 14);
		this.befehlstabelle.put("CMP", 15);
		this.befehlstabelle.put("AND", 40);
		this.befehlstabelle.put("OR", 41);
		this.befehlstabelle.put("XOR", 42);
		this.befehlstabelle.put("SHL", 43);
		this.befehlstabelle.put("SHR", 44);
		this.befehlstabelle.put("SHRA", 45);
		this.befehlstabelle.put("LOAD", 20);
		this.befehlstabelle.put("STORE", 21);
		this.befehlstabelle.put("JGT", 30);
		this.befehlstabelle.put("JGE", 31);
		this.befehlstabelle.put("JLT", 32);
		this.befehlstabelle.put("JLE", 33);
		this.befehlstabelle.put("JEQ", 34);
		this.befehlstabelle.put("JNE", 35);
		this.befehlstabelle.put("JOV", 37);
		this.befehlstabelle.put("JMPP", 30);
		this.befehlstabelle.put("JMPNN", 31);
		this.befehlstabelle.put("JMPN", 32);
		this.befehlstabelle.put("JMPNP", 33);
		this.befehlstabelle.put("JMPZ", 34);
		this.befehlstabelle.put("JMPNZ", 35);
		this.befehlstabelle.put("JMP", 36);
		this.befehlstabelle.put("JMPV", 37);
		this.befehlstabelle.put("ADDI", 310);
		this.befehlstabelle.put("SUBI", 311);
		this.befehlstabelle.put("MULI", 312);
		this.befehlstabelle.put("DIVI", 313);
		this.befehlstabelle.put("MODI", 314);
		this.befehlstabelle.put("CMPI", 315);
		this.befehlstabelle.put("ANDI", 340);
		this.befehlstabelle.put("ORI", 341);
		this.befehlstabelle.put("XORI", 342);
		this.befehlstabelle.put("SHLI", 343);
		this.befehlstabelle.put("SHRI", 344);
		this.befehlstabelle.put("SHRAI", 345);
		this.befehlstabelle.put("LOADI", 320);
		this.befehlstabelle.put("CALL", 5);
		this.befehlstabelle.put("RETURN", 6);
		this.befehlstabelle.put("WORD", -256);
		this.befehlstabelle.put("noop", 0);
		this.befehlstabelle.put("hold", 99);
		this.befehlstabelle.put("reset", 1);
		this.befehlstabelle.put("push", 25);
		this.befehlstabelle.put("pop", 26);
		this.befehlstabelle.put("rsv", 7);
		this.befehlstabelle.put("rel", 8);
		this.befehlstabelle.put("jsr", 5);
		this.befehlstabelle.put("rts", 6);
		this.befehlstabelle.put("not", 46);
		this.befehlstabelle.put("add", 10);
		this.befehlstabelle.put("sub", 11);
		this.befehlstabelle.put("mul", 12);
		this.befehlstabelle.put("div", 13);
		this.befehlstabelle.put("mod", 14);
		this.befehlstabelle.put("cmp", 15);
		this.befehlstabelle.put("and", 40);
		this.befehlstabelle.put("or", 41);
		this.befehlstabelle.put("xor", 42);
		this.befehlstabelle.put("shl", 43);
		this.befehlstabelle.put("shr", 44);
		this.befehlstabelle.put("shra", 45);
		this.befehlstabelle.put("load", 20);
		this.befehlstabelle.put("store", 21);
		this.befehlstabelle.put("jgt", 30);
		this.befehlstabelle.put("jge", 31);
		this.befehlstabelle.put("jlt", 32);
		this.befehlstabelle.put("jle", 33);
		this.befehlstabelle.put("jeq", 34);
		this.befehlstabelle.put("jne", 35);
		this.befehlstabelle.put("jov", 37);
		this.befehlstabelle.put("jmpp", 30);
		this.befehlstabelle.put("jmpnn", 31);
		this.befehlstabelle.put("jmpn", 32);
		this.befehlstabelle.put("jmpnp", 33);
		this.befehlstabelle.put("jmpz", 34);
		this.befehlstabelle.put("jmpnz", 35);
		this.befehlstabelle.put("jmp", 36);
		this.befehlstabelle.put("jmpv", 37);
		this.befehlstabelle.put("addi", 310);
		this.befehlstabelle.put("subi", 311);
		this.befehlstabelle.put("muli", 312);
		this.befehlstabelle.put("divi", 313);
		this.befehlstabelle.put("modi", 314);
		this.befehlstabelle.put("cmpi", 315);
		this.befehlstabelle.put("andi", 340);
		this.befehlstabelle.put("ori", 341);
		this.befehlstabelle.put("xori", 342);
		this.befehlstabelle.put("shli", 343);
		this.befehlstabelle.put("shri", 344);
		this.befehlstabelle.put("shrai", 345);
		this.befehlstabelle.put("loadi", 320);
		this.befehlstabelle.put("word", -256);
		this.befehlstabelle.put("call", 5);
		this.befehlstabelle.put("return", 6);
		this.reversetabelle.put(0, "NOOP");
		this.reversetabelle.put(99, "HOLD");
		this.reversetabelle.put(1, "RESET");
		this.reversetabelle.put(25, "PUSH");
		this.reversetabelle.put(26, "POP");
		this.reversetabelle.put(7, "RSV");
		this.reversetabelle.put(8, "REL");
		this.reversetabelle.put(5, "JSR");
		this.reversetabelle.put(6, "RTS");
		this.reversetabelle.put(46, "NOT");
		this.reversetabelle.put(10, "ADD");
		this.reversetabelle.put(11, "SUB");
		this.reversetabelle.put(12, "MUL");
		this.reversetabelle.put(13, "DIV");
		this.reversetabelle.put(14, "MOD");
		this.reversetabelle.put(15, "CMP");
		this.reversetabelle.put(40, "AND");
		this.reversetabelle.put(41, "OR");
		this.reversetabelle.put(42, "XOR");
		this.reversetabelle.put(43, "SHL");
		this.reversetabelle.put(44, "SHR");
		this.reversetabelle.put(45, "SHRA");
		this.reversetabelle.put(20, "LOAD");
		this.reversetabelle.put(21, "STORE");
		this.reversetabelle.put(30, "JMPP");
		this.reversetabelle.put(31, "JMPNN");
		this.reversetabelle.put(32, "JMPN");
		this.reversetabelle.put(33, "JMPNP");
		this.reversetabelle.put(34, "JMPZ");
		this.reversetabelle.put(35, "JMPNZ");
		this.reversetabelle.put(36, "JMP");
		this.reversetabelle.put(37, "JMPV");
	}

	boolean BezeichnerTesten(String var1) {
		return this.befehlstabelle.containsKey(var1);
	}

	int OpcodeGeben(String var1) {
		return (Integer)this.befehlstabelle.get(var1);
	}

	public String MnemonicGeben(int var1) {
		if (var1 == -1) {
			return "";
		} else {
			return this.reversetabelle.containsKey(var1) ? (String)this.reversetabelle.get(var1) : "---";
		}
	}
}
