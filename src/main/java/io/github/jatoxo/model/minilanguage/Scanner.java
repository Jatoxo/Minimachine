//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package main.java.io.github.jatoxo.model.minilanguage;

class Scanner {
	static final int eof = 0;
	static final int illegal = 1;
	static final int bezeichner = 2;
	static final int zahl = 3;
	static final int program_token = 10;
	static final int begin_token = 11;
	static final int end_token = 12;
	static final int var_token = 13;
	static final int if_token = 14;
	static final int then_token = 15;
	static final int else_token = 16;
	static final int while_token = 17;
	static final int do_token = 18;
	static final int repeat_token = 19;
	static final int until_token = 20;
	static final int for_token = 21;
	static final int to_token = 22;
	static final int by_token = 23;
	static final int strichpunkt = 30;
	static final int punkt = 31;
	static final int komma = 32;
	static final int zuweisung = 33;
	static final int plus = 34;
	static final int minus = 35;
	static final int mal = 36;
	static final int geteilt = 37;
	static final int rest = 38;
	static final int gleich = 39;
	static final int ungleich = 40;
	static final int kleiner = 41;
	static final int kleinergleich = 42;
	static final int größer = 43;
	static final int größergleich = 44;
	static final int klammerauf = 45;
	static final int klammerzu = 46;
	static final int eckigeauf = 100;
	static final int eckigezu = 101;
	static final int proc_token = 102;
	static final int func_token = 103;
	static final int return_token = 104;
	private char[] quelle;
	private int pos;
	private int zeile;
	private char ch;
	private int zahlenwert;
	private String name;
	private AssemblerText ausgabe;
	private boolean erweitert;

	Scanner(String var1, boolean var2, AssemblerText var3) {
		this.quelle = var1.toCharArray();
		this.pos = 0;
		this.zeile = 1;
		this.erweitert = var2;
		this.ausgabe = var3;
		this.NächstesZeichen();
	}

	private void NächstesZeichen() {
		if (this.pos < this.quelle.length) {
			this.ch = this.quelle[this.pos++];
			if (this.ch == '\n') {
				++this.zeile;
				this.ausgabe.BefehlEintragen("#Zeile " + this.zeile, (String)null, (String)null);
			}
		} else {
			this.ch = 0;
		}

	}

	private int Bezeichner() {
		int var1 = this.pos - 1;

		int var2;
		for(var2 = 0; 'a' <= this.ch && this.ch <= 'z' || 'A' <= this.ch && this.ch <= 'Z' || '0' <= this.ch && this.ch <= '9' || this.ch == '_' || this.ch == '$'; ++var2) {
			this.NächstesZeichen();
		}

		this.name = new String(this.quelle, var1, var2);
		TextToken var3 = TextToken.TokenlisteGeben();
		if (var3.BezeichnerTesten(this.name, this.erweitert)) {
			return var3.TokenwertGeben(this.name);
		} else {
			return 2;
		}
	}

	private void Kommentar() {
		while(true) {
			this.NächstesZeichen();
			if (this.ch == '*') {
				this.NächstesZeichen();
				if (this.ch == ')') {
					this.NächstesZeichen();
					return;
				}
			}
		}
	}

	private void Zahl() {
		this.zahlenwert = 0;

		while('0' <= this.ch && this.ch <= '9') {
			this.zahlenwert = this.zahlenwert * 10 + Character.digit(this.ch, 10);
			this.NächstesZeichen();
		}

	}

	int NächstesToken() {
		while(this.ch == ' ' || this.ch == '\t' || this.ch == '\r' || this.ch == '\n') {
			this.NächstesZeichen();
		}

		if (this.ch == 0) {
			this.NächstesZeichen();
			return 0;
		} else if (this.ch == ';') {
			this.NächstesZeichen();
			return 30;
		} else if (this.ch == '.') {
			this.NächstesZeichen();
			return 31;
		} else if (this.ch == ',') {
			this.NächstesZeichen();
			return 32;
		} else if (this.ch == ':') {
			this.NächstesZeichen();
			if (this.ch == '=') {
				this.NächstesZeichen();
				return 33;
			} else {
				return 1;
			}
		} else if (this.ch == '+') {
			this.NächstesZeichen();
			return 34;
		} else if (this.ch == '-') {
			this.NächstesZeichen();
			return 35;
		} else if (this.ch == '*') {
			this.NächstesZeichen();
			return 36;
		} else if (this.ch == '/') {
			this.NächstesZeichen();
			return 37;
		} else if (this.ch == '%') {
			this.NächstesZeichen();
			return 38;
		} else if (this.ch == '=') {
			this.NächstesZeichen();
			return 39;
		} else if (this.ch == '<') {
			this.NächstesZeichen();
			if (this.ch == '=') {
				this.NächstesZeichen();
				return 42;
			} else if (this.ch == '>') {
				this.NächstesZeichen();
				return 40;
			} else {
				return 41;
			}
		} else if (this.ch == '>') {
			this.NächstesZeichen();
			if (this.ch == '=') {
				this.NächstesZeichen();
				return 44;
			} else {
				return 43;
			}
		} else if (this.ch == '(') {
			this.NächstesZeichen();
			if (this.ch == '*') {
				this.Kommentar();
				return this.NächstesToken();
			} else {
				return 45;
			}
		} else if (this.ch == ')') {
			this.NächstesZeichen();
			return 46;
		} else if (this.ch == '[') {
			this.NächstesZeichen();
			return this.erweitert ? 100 : 1;
		} else if (this.ch == ']') {
			this.NächstesZeichen();
			return this.erweitert ? 101 : 1;
		} else if ('0' <= this.ch && this.ch <= '9') {
			this.Zahl();
			return 3;
		} else if (('a' > this.ch || this.ch > 'z') && ('A' > this.ch || this.ch > 'Z') && this.ch != '_' && this.ch != '$') {
			return 1;
		} else {
			return this.Bezeichner();
		}
	}

	String BezeichnerGeben() {
		return this.name;
	}

	int ZahlGeben() {
		return this.zahlenwert;
	}

	int PositionGeben() {
		return this.pos;
	}

	int ZeileGeben() {
		return this.zeile;
	}
}
