//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.jatoxo.model.minilanguage;

abstract class Ablage {
	protected StackVerwaltung stack;
	protected boolean istFeld;

	Ablage(StackVerwaltung var1, boolean var2) {
		this.stack = var1;
		this.istFeld = var2;
	}

	boolean IstFeld() {
		return this.istFeld;
	}

	abstract void Laden(AssemblerText var1);

	abstract void Operation(AssemblerText var1, String var2);

	abstract void AdresseLaden(AssemblerText var1, boolean var2);

	void AblageFreigeben(AssemblerText var1) {
	}

	static Ablage HilfsplatzAnlagen(AssemblerText var0, StackVerwaltung var1, boolean var2) {
		var0.BefehlEintragen((String)null, "PUSH", (String)null);
		return new AblageStackHilf(var1, var2, false);
	}

	static Ablage AblageFürVariableGeben(Variable var0, StackVerwaltung var1) {
		if (var0 instanceof Parameter) {
			return new AblageStack(var1, ((Parameter)var0).OffsetGeben(), ((Parameter)var0).IstVarParam(), var0.IstFeld());
		} else if (var0 instanceof LokaleVariable) {
			return new AblageStack(var1, ((LokaleVariable)var0).OffsetGeben(), false, var0.IstFeld());
		} else {
			return var0 instanceof GlobaleVariable ? new AblageGlobal(var0.NamenGeben(), var0.IstFeld()) : null;
		}
	}
}
