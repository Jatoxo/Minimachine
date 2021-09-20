//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model.minilanguage;

class AttributVariable extends Attribut {
	private String name;

	AttributVariable(String var1) {
		this.name = var1;
	}

	void Laden(AssemblerText var1) {
		var1.BefehlEintragen((String)null, "LOAD", this.name);
	}

	void Operation(AssemblerText var1, String var2) {
		var1.BefehlEintragen((String)null, var2, this.name);
	}
}
