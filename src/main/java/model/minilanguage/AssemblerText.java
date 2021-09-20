//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model.minilanguage;

class AssemblerText {
	private String ausgabe = "";

	AssemblerText() {
	}

	void BefehlEintragen(String var1, String var2, String var3) {
		boolean var5 = false;
		String var4;
		if (var1 != null) {
			if (var1.startsWith("#")) {
				var4 = var1;
				var5 = true;
			} else {
				var4 = var1 + ":";
			}
		} else {
			var4 = "";
		}

		if (!var5) {
			var4 = var4 + "\t";
		}

		if (var2 != null) {
			var4 = var4 + var2;
			if (var3 != null) {
				var4 = var4 + "\t" + var3;
			}
		}

		var4 = var4 + "\n";
		this.ausgabe = this.ausgabe + var4;
	}

	String AssemblerGeben() {
		return this.ausgabe;
	}
}
