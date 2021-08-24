//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model.minilanguage;

abstract class Bezeichner {
	private String name;

	Bezeichner(String var1) {
		this.name = var1;
	}

	String NamenGeben() {
		return this.name;
	}
}
