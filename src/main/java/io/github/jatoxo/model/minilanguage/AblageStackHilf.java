//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.jatoxo.model.minilanguage;

class AblageStackHilf extends AblageStack {
	AblageStackHilf(StackVerwaltung var1, boolean var2, boolean var3) {
		super(var1, var1.OffsetFürNeueVariableGeben(1), var2, var3);
	}

	void AblageFreigeben(AssemblerText var1) {
		var1.BefehlEintragen((String)null, "REL", "$1");
		this.stack.Inkrementieren();
	}
}
