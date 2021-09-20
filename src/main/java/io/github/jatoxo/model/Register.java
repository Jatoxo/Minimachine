//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.jatoxo.model;

class Register {
	private int wert = 0;


	Register() {
	}

	int WertGeben() {
		return this.wert;
	}

	void WertSetzen(int var1) {
		while(var1 >= 32768) {
			var1 -= 65536;
		}

		while(var1 < -32768) {
			var1 += 65536;
		}

		this.wert = var1;
	}

	void increment(int var1) {
		this.wert += var1;
		if (this.wert > 32767) {
			this.wert -= 65536;
		}

	}

	void decrement(int var1) {
		this.wert -= var1;
		if (this.wert < -32268) {
			this.wert += 65536;
		}

	}
}
