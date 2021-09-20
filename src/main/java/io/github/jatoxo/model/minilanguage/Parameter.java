//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.jatoxo.model.minilanguage;

class Parameter extends LokaleVariable {
	private boolean istVarParamter;

	Parameter(String var1, boolean var2) {
		super(var1, 0);
		this.istVarParamter = var2;
	}

	Parameter(String var1, int var2, boolean var3) {
		super(var1, var2, 0);
		this.istVarParamter = var3;
	}

	boolean IstVarParam() {
		return this.istVarParamter;
	}

	void OffsetSetzen(int var1) {
		this.offset = var1;
	}
}
