//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package main.java.io.github.jatoxo.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AssemblerBefehle {
	public HashMap<String, Integer> instructionMap = new HashMap<>(80);
	public HashMap<Integer, String> reverseInstructionMap = new HashMap<>(40);
	private static AssemblerBefehle dasObjekt = new AssemblerBefehle();

	public static AssemblerBefehle getAssemblyInstructions() {
		return dasObjekt;
	}

	private AssemblerBefehle() {
		this.instructionMap.put("NOOP", 0);
		this.instructionMap.put("HOLD", 99);
		this.instructionMap.put("RESET", 1);
		this.instructionMap.put("PUSH", 25);
		this.instructionMap.put("POP", 26);
		this.instructionMap.put("RSV", 7);
		this.instructionMap.put("REL", 8);
		this.instructionMap.put("JSR", 5);
		this.instructionMap.put("RTS", 6);
		this.instructionMap.put("NOT", 46);
		this.instructionMap.put("ADD", 10);
		this.instructionMap.put("SUB", 11);
		this.instructionMap.put("MUL", 12);
		this.instructionMap.put("DIV", 13);
		this.instructionMap.put("MOD", 14);
		this.instructionMap.put("CMP", 15);
		this.instructionMap.put("AND", 40);
		this.instructionMap.put("OR", 41);
		this.instructionMap.put("XOR", 42);
		this.instructionMap.put("SHL", 43);
		this.instructionMap.put("SHR", 44);
		this.instructionMap.put("SHRA", 45);
		this.instructionMap.put("LOAD", 20);
		this.instructionMap.put("STORE", 21);
		this.instructionMap.put("JGT", 30);
		this.instructionMap.put("JGE", 31);
		this.instructionMap.put("JLT", 32);
		this.instructionMap.put("JLE", 33);
		this.instructionMap.put("JEQ", 34);
		this.instructionMap.put("JNE", 35);
		this.instructionMap.put("JOV", 37);
		this.instructionMap.put("JMPP", 30);
		this.instructionMap.put("JMPNN", 31);
		this.instructionMap.put("JMPN", 32);
		this.instructionMap.put("JMPNP", 33);
		this.instructionMap.put("JMPZ", 34);
		this.instructionMap.put("JMPNZ", 35);
		this.instructionMap.put("JMP", 36);
		this.instructionMap.put("JMPV", 37);
		this.instructionMap.put("ADDI", 310);
		this.instructionMap.put("SUBI", 311);
		this.instructionMap.put("MULI", 312);
		this.instructionMap.put("DIVI", 313);
		this.instructionMap.put("MODI", 314);
		this.instructionMap.put("CMPI", 315);
		this.instructionMap.put("ANDI", 340);
		this.instructionMap.put("ORI", 341);
		this.instructionMap.put("XORI", 342);
		this.instructionMap.put("SHLI", 343);
		this.instructionMap.put("SHRI", 344);
		this.instructionMap.put("SHRAI", 345);
		this.instructionMap.put("LOADI", 320);
		this.instructionMap.put("CALL", 5);
		this.instructionMap.put("RETURN", 6);
		this.instructionMap.put("WORD", -256);
		this.instructionMap.put("noop", 0);
		this.instructionMap.put("hold", 99);
		this.instructionMap.put("reset", 1);
		this.instructionMap.put("push", 25);
		this.instructionMap.put("pop", 26);
		this.instructionMap.put("rsv", 7);
		this.instructionMap.put("rel", 8);
		this.instructionMap.put("jsr", 5);
		this.instructionMap.put("rts", 6);
		this.instructionMap.put("not", 46);
		this.instructionMap.put("add", 10);
		this.instructionMap.put("sub", 11);
		this.instructionMap.put("mul", 12);
		this.instructionMap.put("div", 13);
		this.instructionMap.put("mod", 14);
		this.instructionMap.put("cmp", 15);
		this.instructionMap.put("and", 40);
		this.instructionMap.put("or", 41);
		this.instructionMap.put("xor", 42);
		this.instructionMap.put("shl", 43);
		this.instructionMap.put("shr", 44);
		this.instructionMap.put("shra", 45);
		this.instructionMap.put("load", 20);
		this.instructionMap.put("store", 21);
		this.instructionMap.put("jgt", 30);
		this.instructionMap.put("jge", 31);
		this.instructionMap.put("jlt", 32);
		this.instructionMap.put("jle", 33);
		this.instructionMap.put("jeq", 34);
		this.instructionMap.put("jne", 35);
		this.instructionMap.put("jov", 37);
		this.instructionMap.put("jmpp", 30);
		this.instructionMap.put("jmpnn", 31);
		this.instructionMap.put("jmpn", 32);
		this.instructionMap.put("jmpnp", 33);
		this.instructionMap.put("jmpz", 34);
		this.instructionMap.put("jmpnz", 35);
		this.instructionMap.put("jmp", 36);
		this.instructionMap.put("jmpv", 37);
		this.instructionMap.put("addi", 310);
		this.instructionMap.put("subi", 311);
		this.instructionMap.put("muli", 312);
		this.instructionMap.put("divi", 313);
		this.instructionMap.put("modi", 314);
		this.instructionMap.put("cmpi", 315);
		this.instructionMap.put("andi", 340);
		this.instructionMap.put("ori", 341);
		this.instructionMap.put("xori", 342);
		this.instructionMap.put("shli", 343);
		this.instructionMap.put("shri", 344);
		this.instructionMap.put("shrai", 345);
		this.instructionMap.put("loadi", 320);
		this.instructionMap.put("word", -256);
		this.instructionMap.put("call", 5);
		this.instructionMap.put("return", 6);

		this.reverseInstructionMap.put(0, "NOOP");
		this.reverseInstructionMap.put(99, "HOLD");
		this.reverseInstructionMap.put(1, "RESET");
		this.reverseInstructionMap.put(25, "PUSH");
		this.reverseInstructionMap.put(26, "POP");
		this.reverseInstructionMap.put(7, "RSV");
		this.reverseInstructionMap.put(8, "REL");
		this.reverseInstructionMap.put(5, "JSR");
		this.reverseInstructionMap.put(6, "RTS");
		this.reverseInstructionMap.put(46, "NOT");
		this.reverseInstructionMap.put(10, "ADD");
		this.reverseInstructionMap.put(11, "SUB");
		this.reverseInstructionMap.put(12, "MUL");
		this.reverseInstructionMap.put(13, "DIV");
		this.reverseInstructionMap.put(14, "MOD");
		this.reverseInstructionMap.put(15, "CMP");
		this.reverseInstructionMap.put(40, "AND");
		this.reverseInstructionMap.put(41, "OR");
		this.reverseInstructionMap.put(42, "XOR");
		this.reverseInstructionMap.put(43, "SHL");
		this.reverseInstructionMap.put(44, "SHR");
		this.reverseInstructionMap.put(45, "SHRA");
		this.reverseInstructionMap.put(20, "LOAD");
		this.reverseInstructionMap.put(21, "STORE");
		this.reverseInstructionMap.put(30, "JMPP");
		this.reverseInstructionMap.put(31, "JMPNN");
		this.reverseInstructionMap.put(32, "JMPN");
		this.reverseInstructionMap.put(33, "JMPNP");
		this.reverseInstructionMap.put(34, "JMPZ");
		this.reverseInstructionMap.put(35, "JMPNZ");
		this.reverseInstructionMap.put(36, "JMP");
		this.reverseInstructionMap.put(37, "JMPV");
	}

	boolean hasInstruction(String instructionName) {
		return this.instructionMap.containsKey(instructionName);
	}

	int getOpcode(String instructionName) {
		return this.instructionMap.get(instructionName);
	}

	public String getMnemonic(int opcode) {
		if (opcode == -1) {
			return "";
		} else {
			return this.reverseInstructionMap.getOrDefault(opcode, "---");
		}
	}

	public List<String> getMnemonics() {
		List<String> mnemonics = new LinkedList<>();

		for(String mnemonic : instructionMap.keySet()) {

			if(mnemonic.contains(mnemonic.toUpperCase())) {
				mnemonics.add(mnemonic);
			}
		}

		return mnemonics;
	}
}
