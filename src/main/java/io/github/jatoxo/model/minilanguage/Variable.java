//


package io.github.jatoxo.model.minilanguage;

class Variable extends Bezeichner {
	private int länge;

	Variable(String var1) {
		super(var1);
		this.länge = -1;
	}

	Variable(String var1, int var2) {
		super(var1);
		this.länge = var2;
	}

	boolean IstFeld() {
		return this.länge > -1;
	}

	int FeldlängeGeben() {
		return this.länge;
	}
}
