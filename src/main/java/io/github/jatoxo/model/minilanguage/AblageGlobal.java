//


package io.github.jatoxo.model.minilanguage;

class AblageGlobal extends Ablage {
	private String name;

	AblageGlobal(String var1, boolean var2) {
		super((StackVerwaltung)null, var2);
		this.name = var1;
	}

	void Laden(AssemblerText var1) {
		var1.BefehlEintragen((String)null, "LOAD", this.name);
	}

	void Operation(AssemblerText var1, String var2) {
		var1.BefehlEintragen((String)null, var2, this.name);
	}

	void AdresseLaden(AssemblerText var1, boolean var2) {
		var1.BefehlEintragen((String)null, var2 ? "ADD" : "LOAD", "$" + this.name);
	}
}
