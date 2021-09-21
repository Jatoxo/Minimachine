//


package io.github.jatoxo.model.minilanguage;

class AblageGeladen extends Ablage {
	AblageGeladen() {
		super((StackVerwaltung)null, false);
	}

	void Laden(AssemblerText var1) {
	}

	void Operation(AssemblerText var1, String var2) {
		var1.BefehlEintragen((String)null, var2 + "ill", (String)null);
	}

	void AdresseLaden(AssemblerText var1, boolean var2) {
		var1.BefehlEintragen((String)null, "ill", (String)null);
	}
}
