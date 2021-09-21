//


package io.github.jatoxo.model;

enum MikroSchritte {
	COMPLETE,
	FETCH_OPCODE,
	FETCH_ADDRESS,
	FETCH_INDIRECT,
	DECODE,
	EXECUTE_1,
	EXECUTE_2;

	private MikroSchritte() {
	}
}
