//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.jatoxo.model.minilanguage;

import java.util.ArrayList;

class Prozedur extends Bezeichner {
	private boolean istFunktion;
	private ArrayList<Parameter> parameter;
	private int parameterLänge;

	Prozedur(String var1, boolean var2) {
		super(var1);
		this.istFunktion = var2;
		this.parameter = new ArrayList();
		this.parameterLänge = 0;
	}

	boolean IstFunktion() {
		return this.istFunktion;
	}

	void ParameterAnfügen(Parameter var1) {
		this.parameter.add(var1);
		this.parameterLänge += !var1.IstVarParam() && var1.IstFeld() ? var1.FeldlängeGeben() : 1;
	}

	ArrayList<Parameter> ParameterGeben() {
		return this.parameter;
	}

	int ParameterLängeGeben() {
		return this.parameterLänge;
	}
}
