//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.jatoxo.model.minilanguage;

class LokaleVariable extends Variable {
	protected int offset;

	LokaleVariable(String var1, int var2) {
		super(var1);
		this.offset = var2;
	}

	LokaleVariable(String var1, int var2, int var3) {
		super(var1, var2);
		this.offset = var3;
	}

	int OffsetGeben() {
		return this.offset;
	}
}
