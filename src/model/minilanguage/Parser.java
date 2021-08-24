//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model.minilanguage;

import model.FehlerVerwaltung;

public abstract class Parser {
	protected Scanner scanner;
	protected FehlerVerwaltung fehler;
	protected int aktToken;
	protected String programmname;
	protected AssemblerText ausgabe = new AssemblerText();

	public Parser(String var1, FehlerVerwaltung var2, boolean var3) {
		this.scanner = new Scanner(var1, var3, this.ausgabe);
		this.fehler = var2;
		this.aktToken = this.scanner.NächstesToken();
		this.programmname = "";
	}

	public abstract String Parse();

	protected boolean BlockendeTesten() {
		return this.aktToken == 12 || this.aktToken == 16 || this.aktToken == 20;
	}

	protected void SkipBisStrichpunkt() {
		while(this.aktToken != 30 && this.aktToken != 30) {
			this.aktToken = this.scanner.NächstesToken();
		}

	}
}
