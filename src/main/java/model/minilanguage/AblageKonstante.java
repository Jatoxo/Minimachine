//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model.minilanguage;

class AblageKonstante extends Ablage {
	private int wert;

	AblageKonstante(int var1) {
		super((StackVerwaltung)null, false);
		this.wert = var1;
	}

	void Laden(AssemblerText var1) {
		var1.BefehlEintragen((String)null, "LOADI", "" + this.wert);
	}

	void Operation(AssemblerText var1, String var2) {
		var1.BefehlEintragen((String)null, var2 + "I", "" + this.wert);
	}

	void AdresseLaden(AssemblerText var1, boolean var2) {
		var1.BefehlEintragen((String)null, "ILL", "");
	}
}
