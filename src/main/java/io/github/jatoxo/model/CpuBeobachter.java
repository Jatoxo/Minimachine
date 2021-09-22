//


package io.github.jatoxo.model;

public interface CpuBeobachter {
	/**
	 * A report with all the relevant information about the current instruction
	 *
	 * @param data The value on the data bus
	 * @param address The value on the address bus
	 * @param alu1 First operand in the ALU
	 * @param alu2 Second operand in the ALU
	 * @param alu3 Result in the ALU
	 * @param accumulator Value of the accumulator
	 * @param stackPointer Value of the stack pointer
	 * @param zFlag Value of the zero flag
	 * @param nFlag Value of the negative flag
	 * @param vFlag Value of the overflow flag
	 * @param opMnemonic The mnemonic of the opcode
	 * @param addr The address given to the instruction
	 * @param programCounter The Value of the program counter
	 * @param progAddr
	 * @param progMem
	 * @param dataAddr
	 * @param dataMem
	 * @param stackAddr
	 * @param stackMem
	 * @param microStepName The name of the current micro step
	 */
	void Befehlsmeldung(String data, String address, String alu1, String alu2, String alu3, String accumulator, String stackPointer, boolean zFlag, boolean nFlag, boolean vFlag, String opMnemonic, String addr, String programCounter, String[] progAddr, String[] progMem, String[] dataAddr, String[] dataMem, String[] stackAddr, String[] stackMem, String microStepName);

	void Fehlermeldung(String message);
}
