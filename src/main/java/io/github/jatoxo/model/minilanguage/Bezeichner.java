//


package io.github.jatoxo.model.minilanguage;

abstract class Bezeichner {
	private String name;

	Bezeichner(String var1) {
		this.name = var1;
	}

	String NamenGeben() {
		return this.name;
	}
}
