//


package io.github.jatoxo.model.minilanguage;

import io.github.jatoxo.model.FehlerVerwaltung;
import java.util.HashMap;
import java.util.Iterator;

public class ParserErweitert extends Parser {
	private HashMap<String, Bezeichner> globaleBezeichner = new HashMap();
	private HashMap<String, Bezeichner> lokaleBezeichner = new HashMap();
	private StackVerwaltung stack = new StackVerwaltung();
	private int markenNummer = 0;
	private String prozedurname = null;
	private boolean istFunktion = false;

	public ParserErweitert(String var1, FehlerVerwaltung var2) {
		super(var1, var2, true);
	}

	public String Parse() {
		this.Program();
		this.VariableAusgeben();
		return this.ausgabe.AssemblerGeben();
	}

	private Bezeichner BezeichnerSuchen(String var1) {
		if (this.lokaleBezeichner.containsKey(var1)) {
			return (Bezeichner)this.lokaleBezeichner.get(var1);
		} else if (this.globaleBezeichner.containsKey(var1)) {
			return (Bezeichner)this.globaleBezeichner.get(var1);
		} else {
			this.fehler.FehlerEintragen("Bezeichner nicht gefunden: " + var1, this.scanner.PositionGeben());
			return null;
		}
	}

	private void Program() {
		boolean var1 = false;
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

			while(this.aktToken == 13) {
				this.Variablenvereinbarung((StackVerwaltung)null);
			}

			for(; this.aktToken == 103 || this.aktToken == 102; this.Prozedurvereinbarung()) {
				if (!var1) {
					var1 = true;
					this.ausgabe.BefehlEintragen((String)null, "JMP", this.programmname + "$Start");
				}
			}

			if (var1) {
				this.ausgabe.BefehlEintragen(this.programmname + "$Start", (String)null, (String)null);
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

		this.ausgabe.BefehlEintragen(this.programmname + "$Ende", "HOLD", (String)null);
	}

	private void Variablenvereinbarung(StackVerwaltung var1) {
		this.aktToken = this.scanner.NächstesToken();
		if (this.aktToken != 2) {
			this.fehler.FehlerEintragen("Bezeichner erwartet", this.scanner.PositionGeben());
			this.SkipBisStrichpunkt();
			this.aktToken = this.scanner.NächstesToken();
		} else {
			while(true) {
				String var2 = this.scanner.BezeichnerGeben();
				if (var1 == null && this.globaleBezeichner.containsKey(var2) || var1 != null && this.lokaleBezeichner.containsKey(var2)) {
					this.fehler.FehlerEintragen("Bezeichner schon vereinbart", this.scanner.PositionGeben());
				}

				this.aktToken = this.scanner.NächstesToken();
				if (this.aktToken == 100) {
					this.aktToken = this.scanner.NächstesToken();
					if (this.aktToken == 3) {
						int var3 = this.scanner.ZahlGeben();
						if (var3 <= 0) {
							this.fehler.FehlerEintragen("Feldlänge muss größer 0 sein.", this.scanner.PositionGeben());
							var3 = 1;
						}

						if (var1 == null) {
							this.globaleBezeichner.put(var2, new GlobaleVariable(var2, var3));
						} else {
							this.lokaleBezeichner.put(var2, new LokaleVariable(var2, var3, var1.OffsetFürNeueVariableGeben(var3)));
						}

						this.aktToken = this.scanner.NächstesToken();
					} else {
						this.fehler.FehlerEintragen("Zahl erwartet", this.scanner.PositionGeben());
					}

					if (this.aktToken == 101) {
						this.aktToken = this.scanner.NächstesToken();
					} else {
						this.fehler.FehlerEintragen("] erwartet", this.scanner.PositionGeben());
					}
				} else if (var1 == null) {
					this.globaleBezeichner.put(var2, new GlobaleVariable(var2));
				} else {
					this.lokaleBezeichner.put(var2, new LokaleVariable(var2, var1.OffsetFürNeueVariableGeben(1)));
				}

				if (this.aktToken == 32) {
					this.aktToken = this.scanner.NächstesToken();
				}

				if (this.aktToken != 2) {
					if (this.aktToken == 30) {
						this.aktToken = this.scanner.NächstesToken();
					} else {
						this.fehler.FehlerEintragen("';' erwartet", this.scanner.PositionGeben());
						this.SkipBisStrichpunkt();
						this.aktToken = this.scanner.NächstesToken();
					}
					break;
				}
			}
		}

	}

	private int ParamterVereinbarung(Prozedur var1) {
		boolean var2 = false;
		if (this.aktToken == 13) {
			var2 = true;
			this.aktToken = this.scanner.NächstesToken();
		}

		Parameter var3;
		if (this.aktToken != 2) {
			this.fehler.FehlerEintragen("Bezeichner erwartet", this.scanner.PositionGeben());
			this.aktToken = this.scanner.NächstesToken();
			var3 = new Parameter("Dummy", var2);
		} else {
			String var4 = this.scanner.BezeichnerGeben();
			this.aktToken = this.scanner.NächstesToken();
			if (this.aktToken == 100) {
				this.aktToken = this.scanner.NächstesToken();
				int var5;
				if (this.aktToken == 3) {
					var5 = this.scanner.ZahlGeben();
					if (var5 <= 0) {
						this.fehler.FehlerEintragen("Feldlänge muss größer 0 sein.", this.scanner.PositionGeben());
						var5 = 1;
					}

					this.aktToken = this.scanner.NächstesToken();
				} else {
					this.fehler.FehlerEintragen("Zahl erwartet", this.scanner.PositionGeben());
					var5 = 1;
				}

				if (this.aktToken == 101) {
					this.aktToken = this.scanner.NächstesToken();
				} else {
					this.fehler.FehlerEintragen("] erwartet", this.scanner.PositionGeben());
				}

				var3 = new Parameter(var4, var5, var2);
			} else {
				var3 = new Parameter(var4, var2);
			}
		}

		var1.ParameterAnfügen(var3);
		if (this.aktToken == 32) {
			this.aktToken = this.scanner.NächstesToken();
			int var6 = this.ParamterVereinbarung(var1);
			var3.OffsetSetzen(var6 + 1);
			return !var2 && var3.IstFeld() ? var6 + var3.FeldlängeGeben() : var6 + 1;
		} else {
			var3.OffsetSetzen(1);
			return !var2 && var3.IstFeld() ? var3.FeldlängeGeben() : 1;
		}
	}

	private void Prozedurvereinbarung() {
		StackVerwaltung var1 = this.stack;
		this.stack = new StackVerwaltung();
		this.istFunktion = this.aktToken == 103;
		this.aktToken = this.scanner.NächstesToken();
		if (this.aktToken == 2) {
			this.prozedurname = this.scanner.BezeichnerGeben();
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("Bezeichner erwartet", this.scanner.PositionGeben());
			this.prozedurname = "Dummy";
		}

		if (this.globaleBezeichner.containsKey(this.prozedurname)) {
			this.fehler.FehlerEintragen("Bezeichner schon vereinbart", this.scanner.PositionGeben());
		}

		Prozedur var3 = new Prozedur(this.prozedurname, this.istFunktion);
		this.globaleBezeichner.put(this.prozedurname, var3);
		if (this.aktToken == 45) {
			this.aktToken = this.scanner.NächstesToken();
			if (this.aktToken != 46) {
				this.ParamterVereinbarung(var3);
			}

			if (this.aktToken == 46) {
				this.aktToken = this.scanner.NächstesToken();
			} else {
				this.fehler.FehlerEintragen("')' erwartet", this.scanner.PositionGeben());
				this.SkipBisStrichpunkt();
			}
		} else {
			this.fehler.FehlerEintragen("'(' erwartet", this.scanner.PositionGeben());
		}

		if (this.aktToken == 30) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("';' erwartet", this.scanner.PositionGeben());
		}

		Iterator var4 = var3.ParameterGeben().iterator();

		while(var4.hasNext()) {
			Bezeichner var5 = (Bezeichner)var4.next();
			this.lokaleBezeichner.put(var5.NamenGeben(), var5);
		}

		while(this.aktToken == 13) {
			this.Variablenvereinbarung(this.stack);
		}

		int var2 = -this.stack.OffsetGeben();
		if (var2 > 0) {
			this.ausgabe.BefehlEintragen(this.prozedurname, "RSV", "$" + var2);
		} else {
			this.ausgabe.BefehlEintragen(this.prozedurname, (String)null, (String)null);
		}

		if (this.aktToken == 11) {
			this.aktToken = this.scanner.NächstesToken();
			this.Block();
			if (this.aktToken == 12) {
				this.aktToken = this.scanner.NächstesToken();
				if (this.aktToken == 2) {
					if (!this.prozedurname.equals(this.scanner.BezeichnerGeben())) {
						this.fehler.FehlerEintragen("Prozedurname erwartet", this.scanner.PositionGeben());
					}

					this.aktToken = this.scanner.NächstesToken();
					if (this.aktToken == 30) {
						this.aktToken = this.scanner.NächstesToken();
					} else {
						this.fehler.FehlerEintragen("';' erwartet", this.scanner.PositionGeben());
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

		if (var2 > 0) {
			this.ausgabe.BefehlEintragen(this.prozedurname + "$Ende", "REL", "$" + var2);
			this.ausgabe.BefehlEintragen((String)null, "RTS", (String)null);
		} else {
			this.ausgabe.BefehlEintragen(this.prozedurname + "$Ende", "RTS", (String)null);
		}

		this.stack = var1;
		this.lokaleBezeichner.clear();
		this.prozedurname = null;
	}

	private void Block() {
		while(!this.BlockendeTesten() && this.aktToken != 0) {
			if (this.aktToken == 2) {
				Bezeichner var1 = this.BezeichnerSuchen(this.scanner.BezeichnerGeben());
				this.aktToken = this.scanner.NächstesToken();
				if (this.aktToken == 45) {
					this.aktToken = this.scanner.NächstesToken();
					this.Prozeduraufruf(var1);
				} else {
					this.Zuweisung(var1);
				}
			} else if (this.aktToken == 14) {
				this.BedingteAnweisung();
			} else if (this.aktToken == 17) {
				this.WiederholungEingang();
			} else if (this.aktToken == 21) {
				this.WiederholungZaehl();
			} else if (this.aktToken == 19) {
				this.WiederholungEnde();
			} else if (this.aktToken == 104) {
				this.aktToken = this.scanner.NächstesToken();
				if (this.istFunktion) {
					if (this.aktToken == 30) {
						this.fehler.FehlerEintragen("Funktionsergebnis erwartet", this.scanner.PositionGeben());
					} else {
						Ablage var2 = this.AusdruckStrich();
						var2.Laden(this.ausgabe);
					}
				} else if (this.aktToken != 30) {
					this.fehler.FehlerEintragen("Ausdruck nach RETURN nicht erlaubt", this.scanner.PositionGeben());
				}

				if (this.prozedurname == null) {
					this.ausgabe.BefehlEintragen((String)null, "JMP", this.programmname + "$Ende");
				} else {
					this.ausgabe.BefehlEintragen((String)null, "JMP", this.prozedurname + "$Ende");
				}
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

	void ParameterSetzen(Prozedur var1) {
		Iterator var4 = var1.ParameterGeben().iterator();
		Parameter var3 = var4.hasNext() ? (Parameter)var4.next() : null;

		while(true) {
			if (var3 == null) {
				this.fehler.FehlerEintragen("Mehr aktuelle als formale Paramter", this.scanner.PositionGeben());
			}

			Bezeichner var5 = this.aktToken == 2 ? this.BezeichnerSuchen(this.scanner.BezeichnerGeben()) : null;
			Ablage var2 = this.AusdruckStrich();
			if (var3 != null && var3.IstVarParam()) {
				if (!(var2 instanceof AblageGlobal) && !(var2 instanceof AblageStack)) {
					this.fehler.FehlerEintragen("Variable erwartet", this.scanner.PositionGeben());
				} else if (var3.IstFeld() && !var2.IstFeld()) {
					this.fehler.FehlerEintragen("Feld erwartet", this.scanner.PositionGeben());
				} else if (!var3.IstFeld() && var2.IstFeld()) {
					this.fehler.FehlerEintragen("Einfachen Wert erwartet", this.scanner.PositionGeben());
				} else if (var3.IstFeld() && var2.IstFeld() && var5 != null && var5 instanceof Variable && var3.FeldlängeGeben() != ((Variable)var5).FeldlängeGeben()) {
					this.fehler.FehlerEintragen("Felder haben unterschiedliche Längen", this.scanner.PositionGeben());
				}

				var2.AdresseLaden(this.ausgabe, false);
				this.ausgabe.BefehlEintragen((String)null, "PUSH", (String)null);
				this.stack.Dekrementieren();
			} else if (var3 != null && var3.IstFeld()) {
				if (!var2.IstFeld()) {
					this.fehler.FehlerEintragen("Einfachen Wert erwartet", this.scanner.PositionGeben());
				} else if (var5 != null && var5 instanceof Variable && var3.FeldlängeGeben() != ((Variable)var5).FeldlängeGeben()) {
					this.fehler.FehlerEintragen("Felder haben unterschiedliche Längen", this.scanner.PositionGeben());
				}

				this.ausgabe.BefehlEintragen((String)null, "RSV", "$" + var3.FeldlängeGeben());
				this.stack.Dekrementieren(var3.FeldlängeGeben());
				this.ausgabe.BefehlEintragen((String)null, "LOAD", "$0(SP)");
				this.ausgabe.BefehlEintragen((String)null, "PUSH", (String)null);
				new AblageStackHilf(this.stack, true, true);
				var2.AdresseLaden(this.ausgabe, false);
				this.ausgabe.BefehlEintragen((String)null, "PUSH", (String)null);
				new AblageStackHilf(this.stack, true, true);

				for(int var8 = 0; var8 < var3.FeldlängeGeben(); ++var8) {
					this.ausgabe.BefehlEintragen((String)null, "LOAD", "@0(SP)");
					this.ausgabe.BefehlEintragen((String)null, "STORE", "@1(SP)");
					if (var8 != var3.FeldlängeGeben() - 1) {
						this.ausgabe.BefehlEintragen((String)null, "LOAD", "1(SP)");
						this.ausgabe.BefehlEintragen((String)null, "ADDI", "1");
						this.ausgabe.BefehlEintragen((String)null, "STORE", "1(SP)");
						this.ausgabe.BefehlEintragen((String)null, "LOAD", "0(SP)");
						this.ausgabe.BefehlEintragen((String)null, "ADDI", "1");
						this.ausgabe.BefehlEintragen((String)null, "STORE", "0(SP)");
					}
				}

				this.ausgabe.BefehlEintragen((String)null, "REL", "$2");
				this.stack.Inkrementieren(2);
			} else {
				if (var2.IstFeld()) {
					this.fehler.FehlerEintragen("Einfachen Wert erwartet", this.scanner.PositionGeben());
				}

				var2.Laden(this.ausgabe);
				this.ausgabe.BefehlEintragen((String)null, "PUSH", (String)null);
				this.stack.Dekrementieren();
			}

			if (this.aktToken != 32) {
				if (var4.hasNext()) {
					this.fehler.FehlerEintragen("Mehr formale als aktuelle Paramter", this.scanner.PositionGeben());
				}

				return;
			}

			this.aktToken = this.scanner.NächstesToken();
			var3 = var4.hasNext() ? (Parameter)var4.next() : null;
		}
	}

	private void Prozeduraufruf(Bezeichner var1) {
		if (var1 == null) {
			this.fehler.FehlerEintragen("Unbekannte Variable", this.scanner.PositionGeben());
			var1 = new Prozedur("Dummy", false);
		} else if (!(var1 instanceof Prozedur)) {
			this.fehler.FehlerEintragen("Prozedur / Funktion erwartet", this.scanner.PositionGeben());
			var1 = new Prozedur("Dummy", false);
		}

		if (this.aktToken == 46) {
			if (((Prozedur)var1).ParameterGeben().size() != 0) {
				this.fehler.FehlerEintragen("Parameter erwartet", this.scanner.PositionGeben());
			}

			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.ParameterSetzen((Prozedur)var1);
			if (this.aktToken == 46) {
				this.aktToken = this.scanner.NächstesToken();
			} else {
				this.fehler.FehlerEintragen("')' erwartet", this.scanner.PositionGeben());
			}
		}

		this.ausgabe.BefehlEintragen((String)null, "JSR", ((Bezeichner)var1).NamenGeben());
		int var2 = ((Prozedur)var1).ParameterLängeGeben();
		if (var2 != 0) {
			this.ausgabe.BefehlEintragen((String)null, "REL", "$" + var2);
			this.stack.Inkrementieren(var2);
		}

	}

	private void Zuweisung(Bezeichner var1) {
		if (var1 == null) {
			this.fehler.FehlerEintragen("Unbekannte Variable", this.scanner.PositionGeben());
			var1 = new GlobaleVariable("Dummy", this.aktToken == 100 ? 1 : -1);
		} else if (!(var1 instanceof Variable)) {
			this.fehler.FehlerEintragen("Variable erwartet", this.scanner.PositionGeben());
			var1 = new GlobaleVariable("Dummy", this.aktToken == 100 ? 1 : -1);
		}

		Variable var4 = (Variable)var1;
		boolean var5 = false;
		Ablage var3 = Ablage.AblageFürVariableGeben(var4, this.stack);
		Ablage var2;
		if (this.aktToken == 100) {
			var5 = true;
			if (!var4.IstFeld()) {
				this.fehler.FehlerEintragen("Feldbezeichner erwartet", this.scanner.PositionGeben());
			}

			this.aktToken = this.scanner.NächstesToken();
			var2 = this.AusdruckStrich();
			var2.Laden(this.ausgabe);
			var2.AblageFreigeben(this.ausgabe);
			var3.AdresseLaden(this.ausgabe, true);
			var3 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, true);
			if (this.aktToken == 101) {
				this.aktToken = this.scanner.NächstesToken();
			} else {
				this.fehler.FehlerEintragen("']' erwartet", this.scanner.PositionGeben());
			}
		}

		if (this.aktToken == 33) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("':=' erwartet", this.scanner.PositionGeben());
			if (this.aktToken == 39 || this.aktToken != 1) {
				this.aktToken = this.scanner.NächstesToken();
			}
		}

		Variable var6 = null;
		if (this.aktToken == 2) {
			Bezeichner var8 = this.BezeichnerSuchen(this.scanner.BezeichnerGeben());
			if (var8 != null && var8 instanceof Variable) {
				var6 = (Variable)var8;
			}
		}

		var2 = this.AusdruckStrich();
		if (!var5 && var4.IstFeld()) {
			if (!var2.IstFeld()) {
				this.fehler.FehlerEintragen("Feld erwartet", this.scanner.PositionGeben());
			} else if (var4.FeldlängeGeben() != var4.FeldlängeGeben()) {
				this.fehler.FehlerEintragen("Felder müssen gleiche Länge haben", this.scanner.PositionGeben());
			}

			var2.AdresseLaden(this.ausgabe, false);
			var2 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, true);
			var3.AdresseLaden(this.ausgabe, false);
			var3 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, true);

			for(int var7 = 0; var7 < var4.FeldlängeGeben(); ++var7) {
				this.ausgabe.BefehlEintragen((String)null, "LOAD", "@1(SP)");
				this.ausgabe.BefehlEintragen((String)null, "STORE", "@0(SP)");
				if (var7 != var4.FeldlängeGeben() - 1) {
					this.ausgabe.BefehlEintragen((String)null, "LOAD", "1(SP)");
					this.ausgabe.BefehlEintragen((String)null, "ADDI", "1");
					this.ausgabe.BefehlEintragen((String)null, "STORE", "1(SP)");
					this.ausgabe.BefehlEintragen((String)null, "LOAD", "0(SP)");
					this.ausgabe.BefehlEintragen((String)null, "ADDI", "1");
					this.ausgabe.BefehlEintragen((String)null, "STORE", "0(SP)");
				}
			}

			this.ausgabe.BefehlEintragen((String)null, "REL", "$2");
			this.stack.Inkrementieren(2);
		} else {
			if (var2.IstFeld()) {
				this.fehler.FehlerEintragen("einfachen Wert erwartet", this.scanner.PositionGeben());
			}

			var2.Laden(this.ausgabe);
			var2.AblageFreigeben(this.ausgabe);
			var3.Operation(this.ausgabe, "STORE");
			var3.AblageFreigeben(this.ausgabe);
		}

	}

	private Ablage AusdruckStrich() {
		boolean var2 = false;
		if (this.aktToken == 34) {
			this.aktToken = this.scanner.NächstesToken();
		} else if (this.aktToken == 35) {
			this.aktToken = this.scanner.NächstesToken();
			var2 = true;
		}

		Object var4 = this.AusdruckPunkt();
		boolean var1;
		if (var2) {
			var1 = false;
			if (var4 instanceof AblageGeladen) {
				var4 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, false);
			}

			this.ausgabe.BefehlEintragen((String)null, "LOADI", "0");
			((Ablage)var4).Operation(this.ausgabe, "SUB");
			((Ablage)var4).AblageFreigeben(this.ausgabe);
			var4 = new AblageGeladen();
		}

		while(this.aktToken == 34 || this.aktToken == 35) {
			String var3 = "";
			if (this.aktToken == 34) {
				var3 = "ADD";
			} else if (this.aktToken == 35) {
				var3 = "SUB";
			}

			var1 = false;
			if (var4 instanceof AblageGeladen) {
				var4 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, false);
			}

			this.aktToken = this.scanner.NächstesToken();
			Ablage var5 = this.AusdruckPunkt();
			if (var5 instanceof AblageGeladen) {
				var5 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, false);
			}

			((Ablage)var4).Laden(this.ausgabe);
			var5.Operation(this.ausgabe, var3);
			var5.AblageFreigeben(this.ausgabe);
			((Ablage)var4).AblageFreigeben(this.ausgabe);
			var4 = new AblageGeladen();
		}

		return (Ablage)var4;
	}

	private Ablage AusdruckPunkt() {
		Object var2;
		for(var2 = this.Faktor(); this.aktToken == 36 || this.aktToken == 37 || this.aktToken == 38; var2 = new AblageGeladen()) {
			String var1 = "";
			if (this.aktToken == 36) {
				var1 = "MUL";
			} else if (this.aktToken == 37) {
				var1 = "DIV";
			} else if (this.aktToken == 38) {
				var1 = "MOD";
			}

			if (var2 instanceof AblageGeladen) {
				var2 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, false);
			}

			this.aktToken = this.scanner.NächstesToken();
			Ablage var3 = this.Faktor();
			if (var3 instanceof AblageGeladen) {
				var3 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, false);
			}

			((Ablage)var2).Laden(this.ausgabe);
			var3.Operation(this.ausgabe, var1);
			var3.AblageFreigeben(this.ausgabe);
			((Ablage)var2).AblageFreigeben(this.ausgabe);
		}

		return (Ablage)var2;
	}

	private Ablage Faktor() {
		Object var1;
		if (this.aktToken == 2) {
			Object var2 = this.BezeichnerSuchen(this.scanner.BezeichnerGeben());
			this.aktToken = this.scanner.NächstesToken();
			if (this.aktToken == 45) {
				this.aktToken = this.scanner.NächstesToken();
				this.Prozeduraufruf((Bezeichner)var2);
				var1 = new AblageGeladen();
			} else {
				if (!(var2 instanceof Variable)) {
					this.fehler.FehlerEintragen("Variablenbezeichner erwartet", this.scanner.PositionGeben());
					var2 = new GlobaleVariable("Dummy", this.aktToken == 100 ? 1 : -1);
				}

				var1 = Ablage.AblageFürVariableGeben((Variable)var2, this.stack);
				if (this.aktToken == 100) {
					if (!((Variable)var2).IstFeld()) {
						this.fehler.FehlerEintragen("Feldbezeichner erwartet", this.scanner.PositionGeben());
					}

					this.aktToken = this.scanner.NächstesToken();
					Ablage var3 = this.AusdruckStrich();
					var3.Laden(this.ausgabe);
					var3.AblageFreigeben(this.ausgabe);
					((Ablage)var1).AdresseLaden(this.ausgabe, true);
					var1 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, true);
					if (this.aktToken == 101) {
						this.aktToken = this.scanner.NächstesToken();
					} else {
						this.fehler.FehlerEintragen("']' erwartet", this.scanner.PositionGeben());
					}
				}
			}
		} else if (this.aktToken == 3) {
			var1 = new AblageKonstante(this.scanner.ZahlGeben());
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
			var1 = new AblageKonstante(0);
			if (!this.BlockendeTesten()) {
				this.aktToken = this.scanner.NächstesToken();
			}
		}

		return (Ablage)var1;
	}

	private void Bedingung(int var1) {
		Ablage var3 = this.AusdruckStrich();
		String var2 = "";
		if (this.aktToken == 39) {
			var2 = "JMPNZ";
		} else if (this.aktToken == 40) {
			var2 = "JMPZ";
		} else if (this.aktToken == 41) {
			var2 = "JMPNN";
		} else if (this.aktToken == 42) {
			var2 = "JMPP";
		} else if (this.aktToken == 43) {
			var2 = "JMPNP";
		} else if (this.aktToken == 44) {
			var2 = "JMPN";
		} else {
			this.fehler.FehlerEintragen("'=', '<>', '>', '>=', '<' oder '<=' erwartet", this.scanner.PositionGeben());
		}

		if (var3 instanceof AblageGeladen) {
			var3 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, false);
		}

		this.aktToken = this.scanner.NächstesToken();
		Ablage var4 = this.AusdruckStrich();
		if (var4 instanceof AblageGeladen) {
			var4 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, false);
		}

		var3.Laden(this.ausgabe);
		var4.Operation(this.ausgabe, "CMP");
		var4.AblageFreigeben(this.ausgabe);
		var3.AblageFreigeben(this.ausgabe);
		this.ausgabe.BefehlEintragen((String)null, var2, "M$" + var1);
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
		this.aktToken = this.scanner.NächstesToken();
		Object var8;
		if (this.aktToken == 2) {
			var8 = this.BezeichnerSuchen(this.scanner.BezeichnerGeben());
			if (var8 == null) {
				this.fehler.FehlerEintragen("Unbekannte Variable", this.scanner.PositionGeben());
				var8 = new GlobaleVariable("Dummy");
			} else if (!(var8 instanceof Variable)) {
				this.fehler.FehlerEintragen("Variable erwartet", this.scanner.PositionGeben());
				var8 = new GlobaleVariable("Dummy");
			}

			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("Bezeichner erwartet", this.scanner.PositionGeben());
			var8 = new GlobaleVariable("Dummy");
		}

		Ablage var5 = Ablage.AblageFürVariableGeben((Variable)var8, this.stack);
		if (this.aktToken == 33) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("':=' erwartet", this.scanner.PositionGeben());
			if (this.aktToken == 39 || this.aktToken == 1) {
				this.aktToken = this.scanner.NächstesToken();
			}
		}

		Ablage var7 = this.AusdruckStrich();
		var7.Laden(this.ausgabe);
		var5.Operation(this.ausgabe, "STORE");
		if (this.aktToken == 22) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("'TO' erwartet", this.scanner.PositionGeben());
		}

		var7 = this.AusdruckStrich();
		var7.Laden(this.ausgabe);
		Ablage var6 = Ablage.HilfsplatzAnlagen(this.ausgabe, this.stack, false);
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
		var5.Laden(this.ausgabe);
		var6.Operation(this.ausgabe, "CMP");
		if (var3 > 0) {
			this.ausgabe.BefehlEintragen((String)null, "JMPP", "M$" + var2);
		} else {
			this.ausgabe.BefehlEintragen((String)null, "JMPN", "M$" + var2);
		}

		this.Block();
		var5.Laden(this.ausgabe);
		this.ausgabe.BefehlEintragen((String)null, "ADDI", "" + var3);
		this.ausgabe.BefehlEintragen((String)null, "JMPV", "M$" + var2);
		var5.Operation(this.ausgabe, "STORE");
		this.ausgabe.BefehlEintragen((String)null, "JMP", "M$" + var1);
		this.ausgabe.BefehlEintragen("M$" + var2, (String)null, (String)null);
		if (this.aktToken == 12) {
			this.aktToken = this.scanner.NächstesToken();
		} else {
			this.fehler.FehlerEintragen("'END' erwartet", this.scanner.PositionGeben());
		}

		var6.AblageFreigeben(this.ausgabe);
	}

	private void VariableAusgeben() {
		Iterator var1 = this.globaleBezeichner.values().iterator();

		while(true) {
			Bezeichner var2;
			do {
				if (!var1.hasNext()) {
					return;
				}

				var2 = (Bezeichner)var1.next();
			} while(!(var2 instanceof GlobaleVariable));

			GlobaleVariable var3 = (GlobaleVariable)var2;
			this.ausgabe.BefehlEintragen(var3.NamenGeben(), "WORD", "0");

			for(int var4 = 2; var4 <= var3.FeldlängeGeben(); ++var4) {
				this.ausgabe.BefehlEintragen((String)null, "WORD", "0");
			}
		}
	}
}
