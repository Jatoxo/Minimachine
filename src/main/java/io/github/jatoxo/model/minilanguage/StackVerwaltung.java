//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.jatoxo.model.minilanguage;

class StackVerwaltung {
	private int offset = 0;

	StackVerwaltung() {
	}

	void Dekrementieren() {
		--this.offset;
	}

	void Inkrementieren() {
		++this.offset;
	}

	void Dekrementieren(int var1) {
		this.offset -= var1;
	}

	void Inkrementieren(int var1) {
		this.offset += var1;
	}

	int OffsetGeben() {
		return this.offset;
	}

	int OffsetFürNeueVariableGeben(int var1) {
		this.offset -= var1;
		return this.offset;
	}
}
