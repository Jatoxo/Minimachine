//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

class Scanner {
	static final int eof = 0;
	static final int illegal = 1;
	static final int bezeichner = 2;
	static final int zahl = 3;
	static final int doppelpunkt = 4;
	static final int trennung = 5;
	static final int plus = 6;
	static final int minus = 7;
	static final int raute = 8;
	static final int klammerauf = 9;
	static final int klammerzu = 10;
	static final int at = 11;
	private char[] quelle;
	private int pos;
	private char ch;
	private int zahlenwert;
	private String name;

	Scanner(String var1) {
		this.quelle = var1.toCharArray();
		this.pos = 0;
		this.NächstesZeichen();
	}

	private void NächstesZeichen() {
		if (this.pos < this.quelle.length) {
			this.ch = this.quelle[this.pos++];
		} else {
			this.ch = 0;
		}

	}

	private void Bezeichner() {
		int var1 = this.pos - 1;

		int var2;
		for(var2 = 0; 'a' <= this.ch && this.ch <= 'z' || 'A' <= this.ch && this.ch <= 'Z' || '0' <= this.ch && this.ch <= '9' || this.ch == '_' || this.ch == '$'; ++var2) {
			this.NächstesZeichen();
		}

		this.name = new String(this.quelle, var1, var2);
	}

	private void Zahl() {
		this.zahlenwert = 0;

		while('0' <= this.ch && this.ch <= '9') {
			this.zahlenwert = this.zahlenwert * 10 + Character.digit(this.ch, 10);
			this.NächstesZeichen();
		}

	}

	private boolean HexZahl() {
		this.zahlenwert = 0;
		if (('0' > this.ch || this.ch > '9') && ('A' > this.ch || this.ch > 'F') && ('a' > this.ch || this.ch > 'f')) {
			return false;
		} else {
			while('0' <= this.ch && this.ch <= '9' || 'A' <= this.ch && this.ch <= 'F' || 'a' <= this.ch && this.ch <= 'f') {
				this.zahlenwert = this.zahlenwert * 16 + Character.digit(this.ch, 16);
				this.NächstesZeichen();
			}

			return true;
		}
	}

	int NächstesToken() {
		while(this.ch == ' ' || this.ch == '\t') {
			this.NächstesZeichen();
		}

		if (this.ch == '#') {
			this.NächstesZeichen();

			while(this.ch != '\r' && this.ch != '\n' && this.ch != 0) {
				this.NächstesZeichen();
			}
		}

		if (this.ch == 0) {
			this.NächstesZeichen();
			return 0;
		} else if (this.ch == ':') {
			this.NächstesZeichen();
			return 4;
		} else if (this.ch == '(') {
			this.NächstesZeichen();
			return 9;
		} else if (this.ch == ')') {
			this.NächstesZeichen();
			return 10;
		} else if (this.ch == '@') {
			this.NächstesZeichen();
			return 11;
		} else if (this.ch == '\r') {
			this.NächstesZeichen();
			if (this.ch == '\n') {
				this.NächstesZeichen();
			}

			return 5;
		} else if (this.ch == '\n') {
			this.NächstesZeichen();
			return 5;
		} else if (this.ch == '+') {
			this.NächstesZeichen();
			return 6;
		} else if (this.ch == '-') {
			this.NächstesZeichen();
			return 7;
		} else if (this.ch == '$') {
			this.NächstesZeichen();
			return 8;
		} else if ('1' <= this.ch && this.ch <= '9') {
			this.Zahl();
			return 3;
		} else if ('0' == this.ch) {
			this.NächstesZeichen();
			if (Character.toLowerCase(this.ch) == 'x') {
				this.NächstesZeichen();
				if (!this.HexZahl()) {
					return 1;
				}
			} else {
				this.Zahl();
			}

			return 3;
		} else if (('a' > this.ch || this.ch > 'z') && ('A' > this.ch || this.ch > 'Z') && this.ch != '_' && this.ch != '$') {
			this.NächstesZeichen();
			return 1;
		} else {
			this.Bezeichner();
			return 2;
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
}
