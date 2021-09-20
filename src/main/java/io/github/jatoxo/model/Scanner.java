//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.jatoxo.model;

class Scanner {
	static final int EOF = 0;
	static final int ILLEGAL = 1;
	static final int LABEL = 2;
	static final int NUMBER = 3;
	static final int COLON = 4;
	static final int WHITESPACE = 5;
	static final int PLUS = 6;
	static final int MINUS = 7;
	static final int HASHTAG = 8;
	static final int OPEN_BRACKET = 9;
	static final int CLOSE_BRACKET = 10;
	static final int AT_SYMBOL = 11;

	private char[] source;
	private int pos;
	private char ch;
	private int zahlenwert;
	private String name;

	Scanner(String var1) {
		this.source = var1.toCharArray();
		this.pos = 0;
		this.nextCharacter();
	}

	private void nextCharacter() {
		if (this.pos < this.source.length) {
			this.ch = this.source[this.pos++];
		} else {
			this.ch = 0;
		}

	}

	private void Bezeichner() {
		int var1 = this.pos - 1;

		int var2;
		for(var2 = 0; 'a' <= this.ch && this.ch <= 'z' || 'A' <= this.ch && this.ch <= 'Z' || '0' <= this.ch && this.ch <= '9' || this.ch == '_' || this.ch == '$'; ++var2) {
			this.nextCharacter();
		}

		this.name = new String(this.source, var1, var2);
	}

	private void Zahl() {
		this.zahlenwert = 0;

		while('0' <= this.ch && this.ch <= '9') {
			this.zahlenwert = this.zahlenwert * 10 + Character.digit(this.ch, 10);
			this.nextCharacter();
		}

	}

	private boolean HexZahl() {
		this.zahlenwert = 0;
		if (('0' > this.ch || this.ch > '9') && ('A' > this.ch || this.ch > 'F') && ('a' > this.ch || this.ch > 'f')) {
			return false;
		} else {
			while('0' <= this.ch && this.ch <= '9' || 'A' <= this.ch && this.ch <= 'F' || 'a' <= this.ch && this.ch <= 'f') {
				this.zahlenwert = this.zahlenwert * 16 + Character.digit(this.ch, 16);
				this.nextCharacter();
			}

			return true;
		}
	}

	int nextToken() {
		while(this.ch == ' ' || this.ch == '\t') {
			this.nextCharacter();
		}

		if (this.ch == '#') {
			this.nextCharacter();

			while(this.ch != '\r' && this.ch != '\n' && this.ch != 0) {
				this.nextCharacter();
			}
		}

		if (this.ch == 0) {
			this.nextCharacter();
			return EOF;
		} else if (this.ch == ':') {
			this.nextCharacter();
			return COLON;
		} else if (this.ch == '(') {
			this.nextCharacter();
			return OPEN_BRACKET;
		} else if (this.ch == ')') {
			this.nextCharacter();
			return CLOSE_BRACKET;
		} else if (this.ch == '@') {
			this.nextCharacter();
			return AT_SYMBOL;
		} else if (this.ch == '\r') {
			this.nextCharacter();
			if (this.ch == '\n') {
				this.nextCharacter();
			}

			return WHITESPACE;
		} else if (this.ch == '\n') {
			this.nextCharacter();
			return WHITESPACE;
		} else if (this.ch == '+') {
			this.nextCharacter();
			return PLUS;
		} else if (this.ch == '-') {
			this.nextCharacter();
			return MINUS;
		} else if (this.ch == '$') {
			this.nextCharacter();
			return HASHTAG;
		} else if ('1' <= this.ch && this.ch <= '9') {
			this.Zahl();
			return NUMBER;
		} else if ('0' == this.ch) {
			this.nextCharacter();
			if (Character.toLowerCase(this.ch) == 'x') {
				this.nextCharacter();
				if (!this.HexZahl()) {
					return ILLEGAL;
				}
			} else {
				this.Zahl();
			}

			return NUMBER;
		} else if (('a' > this.ch || this.ch > 'z') && ('A' > this.ch || this.ch > 'Z') && this.ch != '_' && this.ch != '$') {
			this.nextCharacter();
			return ILLEGAL;
		} else {
			this.Bezeichner();
			return LABEL;
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
