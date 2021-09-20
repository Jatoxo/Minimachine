//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model.minilanguage;

import model.FehlerVerwaltung;
import java.util.HashSet;
import java.util.Iterator;

public class ParserEinfach extends Parser {
	private HashSet<String> variable = new HashSet();
	private int akthilfsplatz = 0;
	private int maxhilfsplatz = 0;
	private int markenNummer = 0;

	public ParserEinfach(String var1, FehlerVerwaltung var2) {
		super(var1, var2, false);
	}

	public String Parse() {
		this.Program();
		this.VariableAusgeben();
		return this.ausgabe.AssemblerGeben();
	}

	private void Program() {
		if (this.aktToken == 10) {
			this.aktToken = this.scanner.NächstesToken();
			if (this.aktToken == 2) {
				this.programmname = this.scanner.BezeichnerGeben();
				this.aktToken = this.scanner.NächstesToken();
			} else {
				this.fehler.FehlerEintragen("Bezeichner erwartet", this.scanner.PositionGeben());
			}

			if (this.aktToken == 30) {
				this.aktToken = this.scanner.NächstesToken();
			} else {
				this.fehler.FehlerEintragen("';' erwartet", this.scanner.PositionGeben());
			}

			if (this.aktToken == 13) {
				this.Variablenvereinbarung();
			}

			if (this.aktToken == 11) {
				this.aktToken = this.scanner.NächstesToken();
				this.Block();
				if (this.aktToken == 12) {
					this.aktToken = this.scanner.NächstesToken();
					if (this.aktToken == 2) {
						if (!this.programmname.equals(this.scanner.BezeichnerGeben())) {
							this.fehler.FehlerEintragen("Programmname erwartet", this.scanner.PositionGeben());
						}

						this.aktToken = this.scanner.NächstesToken();
						if (this.aktToken == 31) {
							this.aktToken = this.scanner.NächstesToken();
							if (this.aktToken != 0) {
								this.fehler.FehlerEintragen("Unzulässige Zeichen am Programmende", this.scanner.PositionGeben());
							}
						} else {
							this.fehler.FehlerEintragen("'.' erwartet", this.scanner.PositionGeben());
						}
					} else {
						this.fehler.FehlerEintragen("Bezeichner erwartet", this.scanner.PositionGeben());
					}
				} else {
					this.fehler.FehlerEintragen("'END' erwartet", this.scanner.PositionGeben());
				}
			} else {
				this.fehler.FehlerEintragen("'BEGIN' erwartet", this.scanner.PositionGeben());
			}
		} else {
			this.fehler.FehlerEintragen("'PROGRAM' erwartet", this.scanner.PositionGeben());
		}

		this.ausgabe.BefehlEintragen((String)null, "HOLD", (String)null);
	}

	private void Variablenvereinbarung() {
		this.aktToken = this.scanner.NächstesToken();
		if (this.aktToken != 2) {
			this.fehler.FehlerEintragen("Bezeichner erwartet", this.scanner.PositionGeben());
			this.SkipBisStrichpunkt();
			this.aktToken = this.scanner.NächstesToken();
		} else {
			do {
				String var1 = this.scanner.BezeichnerGeben();
				if (this.variable.contains(var1)) {
					this.fehler.FehlerEintragen("Bezeichner schon vereinbart", this.scanner.PositionGeben());
				} else {
					this.variable.add(var1);
				}

				this.aktToken = this.scanner.NächstesToken();
				if (this.aktToken == 32) {
					this.aktToken = this.scanner.NächstesToken();
				}
			} while(this.aktToken == 2);

			if (this.aktToken == 30) {
				this.aktToken = this.scanner.NächstesToken();
			} else {
				this.fehler.FehlerEintragen("';' erwartet", this.scanner.PositionGeben());
				this.SkipBisStrichpunkt();
				this.aktToken = this.scanner.NächstesToken();
			}
		}

	}

	private void Block() {
		while(!this.BlockendeTesten() && this.aktToken != 0) {
			if (this.aktToken == 2) {
				this.Zuweisung();
			} else if (this.aktToken == 14) {
				this.BedingteAnweisung();
			} else if (this.aktToken == 17) {
				this.WiederholungEingang();
			} else if (this.aktToken == 21) {
				this.WiederholungZaehl();
			} else if (this.aktToken == 19) {
				this.WiederholungEnde();
			}

			if (this.aktToken == 30) {
				this.aktToken = this.scanner.NächstesToken();
			} else if (!this.BlockendeTesten()) {
				this.fehler.FehlerEintragen("';' erwartet", this.scanner.PositionGeben());
				if (this.aktToken != 2 && this.aktToken != 14 && this.aktToken != 17 && this.aktToken != 21 && this.aktToken != 19) {
					this.aktToken = this.scanner.NächstesToken();
				}
			}
		}

	}

	private void Zuweisung() {
		String var1 = this.scanner.BezeichnerGeben();
		this.aktToken = this.scanner.NächstesToken();
		if (this.aktToken == 33) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("':=' erwartet", this.scanner.PositionGeben());
			if (this.aktToken == 39 || this.aktToken != 1) {
				this.aktToken = this.scanner.NächstesToken();
			}
		}

		Attribut var2 = this.AusdruckStrich();
		var2.Laden(this.ausgabe);
		this.ausgabe.BefehlEintragen((String)null, "STORE", var1);
	}

	private Attribut AusdruckStrich() {
		boolean var2 = false;
		if (this.aktToken == 34) {
			this.aktToken = this.scanner.NächstesToken();
		} else if (this.aktToken == 35) {
			this.aktToken = this.scanner.NächstesToken();
			var2 = true;
		}

		Object var4 = this.AusdruckPunkt();
		if (var2) {
			byte var1 = 0;
			if (var4 instanceof AttributGeladen) {
				++this.akthilfsplatz;
				if (this.akthilfsplatz > this.maxhilfsplatz) {
					this.maxhilfsplatz = this.akthilfsplatz;
				}

				this.ausgabe.BefehlEintragen((String)null, "STORE", "hi$" + this.akthilfsplatz);
				var4 = new AttributVariable("hi$" + this.akthilfsplatz);
				var1 = 1;
			}

			this.ausgabe.BefehlEintragen((String)null, "LOADI", "0");
			((Attribut)var4).Operation(this.ausgabe, "SUB");
			var4 = new AttributGeladen();
			this.akthilfsplatz -= var1;
		}

		while(this.aktToken == 34 || this.aktToken == 35) {
			String var3 = "";
			if (this.aktToken == 34) {
				var3 = "ADD";
			} else if (this.aktToken == 35) {
				var3 = "SUB";
			}

			int var6 = 0;
			if (var4 instanceof AttributGeladen) {
				++this.akthilfsplatz;
				if (this.akthilfsplatz > this.maxhilfsplatz) {
					this.maxhilfsplatz = this.akthilfsplatz;
				}

				this.ausgabe.BefehlEintragen((String)null, "STORE", "hi$" + this.akthilfsplatz);
				var4 = new AttributVariable("hi$" + this.akthilfsplatz);
				var6 = 1;
			}

			this.aktToken = this.scanner.NächstesToken();
			Object var5 = this.AusdruckPunkt();
			if (var5 instanceof AttributGeladen) {
				++this.akthilfsplatz;
				if (this.akthilfsplatz > this.maxhilfsplatz) {
					this.maxhilfsplatz = this.akthilfsplatz;
				}

				this.ausgabe.BefehlEintragen((String)null, "STORE", "hi$" + this.akthilfsplatz);
				var5 = new AttributVariable("hi$" + this.akthilfsplatz);
				++var6;
			}

			((Attribut)var4).Laden(this.ausgabe);
			((Attribut)var5).Operation(this.ausgabe, var3);
			var4 = new AttributGeladen();
			this.akthilfsplatz -= var6;
		}

		return (Attribut)var4;
	}

	private Attribut AusdruckPunkt() {
		int var1 = 0;
		Object var3;
		for(var3 = this.Faktor(); this.aktToken == 36 || this.aktToken == 37 || this.aktToken == 38; this.akthilfsplatz -= var1) {
			String var2 = "";
			if (this.aktToken == 36) {
				var2 = "MUL";
			} else if (this.aktToken == 37) {
				var2 = "DIV";
			} else if (this.aktToken == 38) {
				var2 = "MOD";
			}

			var1 = 0;
			if (var3 instanceof AttributGeladen) {
				++this.akthilfsplatz;
				if (this.akthilfsplatz > this.maxhilfsplatz) {
					this.maxhilfsplatz = this.akthilfsplatz;
				}

				this.ausgabe.BefehlEintragen((String)null, "STORE", "hi$" + this.akthilfsplatz);
				var3 = new AttributVariable("hi$" + this.akthilfsplatz);
				var1 = 1;
			}

			this.aktToken = this.scanner.NächstesToken();
			Object var4 = this.Faktor();
			if (var4 instanceof AttributGeladen) {
				++this.akthilfsplatz;
				if (this.akthilfsplatz > this.maxhilfsplatz) {
					this.maxhilfsplatz = this.akthilfsplatz;
				}

				this.ausgabe.BefehlEintragen((String)null, "STORE", "hi$" + this.akthilfsplatz);
				var4 = new AttributVariable("hi$" + this.akthilfsplatz);
				++var1;
			}

			((Attribut)var3).Laden(this.ausgabe);
			((Attribut)var4).Operation(this.ausgabe, var2);
			var3 = new AttributGeladen();
		}

		return (Attribut)var3;
	}

	private Attribut Faktor() {
		Object var1;
		if (this.aktToken == 2) {
			var1 = new AttributVariable(this.scanner.BezeichnerGeben());
			this.aktToken = this.scanner.NächstesToken();
		} else if (this.aktToken == 3) {
			var1 = new AttributKonstant(this.scanner.ZahlGeben());
			this.aktToken = this.scanner.NächstesToken();
		} else if (this.aktToken == 45) {
			this.aktToken = this.scanner.NächstesToken();
			var1 = this.AusdruckStrich();
			if (this.aktToken == 46) {
				this.aktToken = this.scanner.NächstesToken();
			} else {
				this.fehler.FehlerEintragen("')' erwartet", this.scanner.PositionGeben());
				if (!this.BlockendeTesten() && this.aktToken != 30) {
					this.aktToken = this.scanner.NächstesToken();
				}
			}
		} else {
			this.fehler.FehlerEintragen("Bezeichner, Zahl oder '(' erwartet", this.scanner.PositionGeben());
			var1 = new AttributKonstant(0);
			if (!this.BlockendeTesten()) {
				this.aktToken = this.scanner.NächstesToken();
			}
		}

		return (Attribut)var1;
	}

	private void Bedingung(int var1) {
		Object var4 = this.AusdruckStrich();
		String var3 = "";
		if (this.aktToken == 39) {
			var3 = "JMPNZ";
		} else if (this.aktToken == 40) {
			var3 = "JMPZ";
		} else if (this.aktToken == 41) {
			var3 = "JMPNN";
		} else if (this.aktToken == 42) {
			var3 = "JMPP";
		} else if (this.aktToken == 43) {
			var3 = "JMPNP";
		} else if (this.aktToken == 44) {
			var3 = "JMPN";
		} else {
			this.fehler.FehlerEintragen("'=', '<>', '>', '>=', '<' oder '<=' erwartet", this.scanner.PositionGeben());
		}

		int var2 = 0;
		if (var4 instanceof AttributGeladen) {
			++this.akthilfsplatz;
			if (this.akthilfsplatz > this.maxhilfsplatz) {
				this.maxhilfsplatz = this.akthilfsplatz;
			}

			this.ausgabe.BefehlEintragen((String)null, "STORE", "hi$" + this.akthilfsplatz);
			var4 = new AttributVariable("hi$" + this.akthilfsplatz);
			var2 = 1;
		}

		this.aktToken = this.scanner.NächstesToken();
		Object var5 = this.AusdruckStrich();
		if (var5 instanceof AttributGeladen) {
			++this.akthilfsplatz;
			if (this.akthilfsplatz > this.maxhilfsplatz) {
				this.maxhilfsplatz = this.akthilfsplatz;
			}

			this.ausgabe.BefehlEintragen((String)null, "STORE", "hi$" + this.akthilfsplatz);
			var5 = new AttributVariable("hi$" + this.akthilfsplatz);
			++var2;
		}

		((Attribut)var4).Laden(this.ausgabe);
		((Attribut)var5).Operation(this.ausgabe, "CMP");
		this.akthilfsplatz -= var2;
		this.ausgabe.BefehlEintragen((String)null, var3, "M$" + var1);
	}

	private void BedingteAnweisung() {
		int var1 = ++this.markenNummer;
		int var2 = ++this.markenNummer;
		this.aktToken = this.scanner.NächstesToken();
		this.Bedingung(var1);
		if (this.aktToken == 15) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("'THEN' erwartet", this.scanner.PositionGeben());
		}

		this.Block();
		if (this.aktToken == 16) {
			this.aktToken = this.scanner.NächstesToken();
			this.ausgabe.BefehlEintragen((String)null, "JMP", "M$" + var2);
			this.ausgabe.BefehlEintragen("M$" + var1, (String)null, (String)null);
			this.Block();
			this.ausgabe.BefehlEintragen("M$" + var2, (String)null, (String)null);
		} else {
			this.ausgabe.BefehlEintragen("M$" + var1, (String)null, (String)null);
		}

		if (this.aktToken == 12) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("'END' erwartet", this.scanner.PositionGeben());
		}

	}

	private void WiederholungEingang() {
		int var1 = ++this.markenNummer;
		int var2 = ++this.markenNummer;
		this.ausgabe.BefehlEintragen("M$" + var1, (String)null, (String)null);
		this.aktToken = this.scanner.NächstesToken();
		this.Bedingung(var2);
		if (this.aktToken == 18) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("'DO' erwartet", this.scanner.PositionGeben());
		}

		this.Block();
		this.ausgabe.BefehlEintragen((String)null, "JMP", "M$" + var1);
		this.ausgabe.BefehlEintragen("M$" + var2, (String)null, (String)null);
		if (this.aktToken == 12) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("'END' erwartet", this.scanner.PositionGeben());
		}

	}

	private void WiederholungEnde() {
		int var1 = ++this.markenNummer;
		this.ausgabe.BefehlEintragen("M$" + var1, (String)null, (String)null);
		this.aktToken = this.scanner.NächstesToken();
		this.Block();
		if (this.aktToken == 20) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("'UNTIL' erwartet", this.scanner.PositionGeben());
		}

		this.Bedingung(var1);
	}

	private void WiederholungZaehl() {
		int var1 = ++this.markenNummer;
		int var2 = ++this.markenNummer;
		++this.akthilfsplatz;
		if (this.akthilfsplatz > this.maxhilfsplatz) {
			this.maxhilfsplatz = this.akthilfsplatz;
		}

		String var6 = "hi$" + this.akthilfsplatz;
		this.aktToken = this.scanner.NächstesToken();
		String var5;
		if (this.aktToken == 2) {
			var5 = this.scanner.BezeichnerGeben();
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("Bezeichner erwartet", this.scanner.PositionGeben());
			var5 = "dummy";
		}

		if (this.aktToken == 33) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("':=' erwartet", this.scanner.PositionGeben());
			if (this.aktToken == 39 || this.aktToken != 1) {
				this.aktToken = this.scanner.NächstesToken();
			}
		}

		Attribut var7 = this.AusdruckStrich();
		var7.Laden(this.ausgabe);
		this.ausgabe.BefehlEintragen((String)null, "STORE", var5);
		if (this.aktToken == 22) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("'TO' erwartet", this.scanner.PositionGeben());
		}

		var7 = this.AusdruckStrich();
		var7.Laden(this.ausgabe);
		this.ausgabe.BefehlEintragen((String)null, "STORE", var6);
		int var3;
		if (this.aktToken == 23) {
			this.aktToken = this.scanner.NächstesToken();
			boolean var4 = false;
			if (this.aktToken == 34) {
				this.aktToken = this.scanner.NächstesToken();
			} else if (this.aktToken == 35) {
				this.aktToken = this.scanner.NächstesToken();
				var4 = true;
			}

			if (this.aktToken == 3) {
				var3 = this.scanner.ZahlGeben();
				this.aktToken = this.scanner.NächstesToken();
			} else {
				this.fehler.FehlerEintragen("Zahl erwartet", this.scanner.PositionGeben());
				var3 = 1;
			}

			if (var4) {
				var3 = -var3;
			}

			if (var3 == 0) {
				this.fehler.FehlerEintragen("Die Schrittweite darf nicht 0 sein", this.scanner.PositionGeben());
				var3 = 1;
			}
		} else {
			var3 = 1;
		}

		if (this.aktToken == 18) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("'DO' erwartet", this.scanner.PositionGeben());
		}

		this.ausgabe.BefehlEintragen("M$" + var1, (String)null, (String)null);
		this.ausgabe.BefehlEintragen((String)null, "LOAD", var5);
		this.ausgabe.BefehlEintragen((String)null, "CMP", var6);
		if (var3 > 0) {
			this.ausgabe.BefehlEintragen((String)null, "JMPP", "M$" + var2);
		} else {
			this.ausgabe.BefehlEintragen((String)null, "JMPN", "M$" + var2);
		}

		this.Block();
		this.ausgabe.BefehlEintragen((String)null, "LOAD", var5);
		this.ausgabe.BefehlEintragen((String)null, "ADDI", "" + var3);
		this.ausgabe.BefehlEintragen((String)null, "JMPV", "M$" + var2);
		this.ausgabe.BefehlEintragen((String)null, "STORE", var5);
		this.ausgabe.BefehlEintragen((String)null, "JMP", "M$" + var1);
		this.ausgabe.BefehlEintragen("M$" + var2, (String)null, (String)null);
		if (this.aktToken == 12) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("'END' erwartet", this.scanner.PositionGeben());
		}

		--this.akthilfsplatz;
	}

	private void VariableAusgeben() {
		Iterator var1 = this.variable.iterator();

		while(var1.hasNext()) {
			String var2 = (String)var1.next();
			this.ausgabe.BefehlEintragen(var2, "WORD", "0");
		}

		for(int var3 = 1; var3 <= this.maxhilfsplatz; ++var3) {
			this.ausgabe.BefehlEintragen("hi$" + var3, "WORD", "0");
		}

	}
}
