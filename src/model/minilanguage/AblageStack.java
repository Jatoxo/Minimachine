//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model.minilanguage;

class AblageStack extends Ablage {
	protected int offset;
	private boolean indirekt;

	AblageStack(StackVerwaltung var1, int var2, boolean var3, boolean var4) {
		super(var1, var4);
		this.offset = var2;
		this.indirekt = var3;
	}

	private String AdressteilGeben() {
		return (this.indirekt ? "@" : "") + (this.offset - this.stack.OffsetGeben()) + "(SP)";
	}

	void Laden(AssemblerText var1) {
		var1.BefehlEintragen((String)null, "LOAD", this.AdressteilGeben());
	}

	void Operation(AssemblerText var1, String var2) {
		var1.BefehlEintragen((String)null, var2, this.AdressteilGeben());
	}

	void AdresseLaden(AssemblerText var1, boolean var2) {
		var1.BefehlEintragen((String)null, var2 ? "ADD" : "LOAD", (this.indirekt ? "" : "$") + (this.offset - this.stack.OffsetGeben()) + "(SP)");
	}
}
