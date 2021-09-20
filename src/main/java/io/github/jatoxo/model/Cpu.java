//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package main.java.io.github.jatoxo.model;

import main.java.io.github.jatoxo.model.minilanguage.ParserEinfach;
import main.java.io.github.jatoxo.model.minilanguage.ParserErweitert;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Cpu implements CpuMeldungsErzeuger {
	static final int noop = 0;
	static final int reset = 1;
	static final int jsr = 5;
	static final int rts = 6;
	static final int rsv = 7;
	static final int rel = 8;
	static final int add = 10;
	static final int sub = 11;
	static final int mul = 12;
	static final int div = 13;
	static final int mod = 14;
	static final int cmp = 15;
	static final int load = 20;
	static final int store = 21;
	static final int push = 25;
	static final int pop = 26;
	static final int jgt = 30;
	static final int jge = 31;
	static final int jlt = 32;
	static final int jle = 33;
	static final int jeq = 34;
	static final int jne = 35;
	static final int jmp = 36;
	static final int jov = 37;
	static final int and = 40;
	static final int or = 41;
	static final int xor = 42;
	static final int shl = 43;
	static final int shr = 44;
	static final int shra = 45;
	static final int not = 46;
	static final int halt = 99;
	static final int ill = -1;
	public static final int keineadr = 0;
	public static final int absadr = 1;
	public static final int immedadr = 2;
	public static final int indadr = 3;
	public static final int offsetadr = 4;
	public static final int offsetindadr = 5;
	public static final int offsetimmedadr = 6;
	protected Register pc;
	protected Register a;
	protected Register sp;
	protected boolean ltflag;
	protected boolean eqflag;
	protected boolean ovflag;
	protected Speicher speicher;
	protected AssemblerBefehle mnemos;
	private ArrayList<CpuBeobachter> beobachter;
	protected int befehlscode;
	protected int adressmodus;
	protected int adresse;
	private String[] progadr = new String[4];
	private String[] progmem = new String[4];
	private String[] dataadr = new String[2];
	private String[] datamem = new String[2];
	private String[] stackadr = new String[3];
	private String[] stackmem = new String[3];
	private int progAdrAlt;
	private int dataAdrAlt;
	private int stackAdrAlt;
	private long schranke;
	private boolean hexaAnzeige;
	private String datenWert_letzter;
	private String adressWert_letzter;
	private String alu1_letzter;
	private String alu2_letzter;
	private String alu3_letzter;
	private String mikro_letzter;
	private boolean opMnemo_letzter;
	private boolean letzterDa;
	protected boolean erweitert;

	Cpu(Speicher storage) {
		this.speicher = storage;
		this.pc = new Register();
		this.a = new Register();
		this.sp = new Register();
		this.sp.WertSetzen(-2);
		this.mnemos = AssemblerBefehle.getAssemblyInstructions();
		this.beobachter = new ArrayList();
		this.ZurückSetzen();
		this.schranke = 5L;
		this.hexaAnzeige = false;
		this.letzterDa = false;
		this.erweitert = true;
	}

	public void Registrieren(CpuBeobachter var1) {
		this.beobachter.add(var1);
	}

	public void Abmelden(CpuBeobachter var1) {
		this.beobachter.remove(var1);
	}

	public void HexaSetzen(boolean var1) {
		this.hexaAnzeige = var1;
	}

	public void ErweitertSetzen(boolean var1) {
		this.erweitert = var1;
	}

	private String HexaString(String decimalString) {
		if (decimalString.length() > 0 && !" ".equals(decimalString)) {
			int num = Integer.parseInt(decimalString);
			if (num < 0) {
				num += Speicher.WORD_SIZE;
			}

			decimalString = "0000" + Integer.toHexString(num);
			decimalString = decimalString.substring(decimalString.length() - 4);
		}

		return decimalString;
	}

	protected void Melden(String data, String address, String alu1, String alu2, String alu3, boolean var6, int var7, int var8, int var9, String microStepName) {
		this.letzterDa = true;
		this.datenWert_letzter = data;
		this.adressWert_letzter = address;
		this.alu1_letzter = alu1;
		this.alu2_letzter = alu2;
		this.alu3_letzter = alu3;
		this.mikro_letzter = microStepName;
		this.opMnemo_letzter = var6;

		String var11 = "" + this.a.WertGeben();
		String var12 = "" + this.adresse;
		String var13 = "" + (this.befehlscode + this.adressmodus * 256);
		if (this.hexaAnzeige) {
			data = this.HexaString(data);
			alu1 = this.HexaString(alu1);
			alu2 = this.HexaString(alu2);
			alu3 = this.HexaString(alu3);
			var11 = this.HexaString(var11);
			var12 = this.HexaString(var12);
			var13 = this.HexaString(var13);
			if (address.length() > 0 && !" ".equals(address)) {
				address = address + " [" + this.HexaString(address) + "]";
			}
		}

		if (var7 != -1) {
			this.progAdrAlt = var7;
		}

		if (var8 != -1) {
			this.dataAdrAlt = var8;
		}

		if (var9 != -1) {
			this.stackAdrAlt = var9;
		}


		for(int i = 0; i < this.progmem.length; i++) {
			this.progadr[i] = "" + (this.progAdrAlt + i < 0 ? Speicher.MEMORY_SIZE + this.progAdrAlt + i : this.progAdrAlt + i);
			if (this.hexaAnzeige) {
				this.progmem[i] = "0000" + Integer.toHexString(this.speicher.WortOhneVorzeichenGeben(this.progAdrAlt + i));
				this.progmem[i] = this.progmem[i].substring(this.progmem[i].length() - 4);
			} else {
				this.progmem[i] = "" + this.speicher.WortMitVorzeichenGeben(this.progAdrAlt + i);
			}
		}

		for(int i = 0; i < this.datamem.length; i++) {
			this.dataadr[i] = "" + (this.dataAdrAlt + i < 0 ? Speicher.MEMORY_SIZE + this.dataAdrAlt + i : this.dataAdrAlt + i);
			if (this.hexaAnzeige) {
				this.datamem[i] = "0000" + Integer.toHexString(this.speicher.WortOhneVorzeichenGeben(this.dataAdrAlt + i));
				this.datamem[i] = this.datamem[i].substring(this.datamem[i].length() - 4);
			} else {
				this.datamem[i] = "" + this.speicher.WortMitVorzeichenGeben(this.dataAdrAlt + i);
			}
		}

		for(int i = 0; i < this.stackmem.length; ++i) {
			this.stackadr[i] = "" + (this.stackAdrAlt + i < 0 ? Speicher.MEMORY_SIZE + this.stackAdrAlt + i : this.stackAdrAlt + i);
			if (this.hexaAnzeige) {
				this.stackmem[i] = "0000" + Integer.toHexString(this.speicher.WortOhneVorzeichenGeben(this.stackAdrAlt + i));
				this.stackmem[i] = this.stackmem[i].substring(this.stackmem[i].length() - 4);
			} else {
				this.stackmem[i] = "" + this.speicher.WortMitVorzeichenGeben(this.stackAdrAlt + i);
			}
		}

		String mnemonic = this.mnemos.getMnemonic(this.befehlscode);

		for(CpuBeobachter listeners : this.beobachter) {
			listeners.Befehlsmeldung(data, address, alu1, alu2, alu3, var11, this.sp.WertGeben() < 0 ? "" + (this.sp.WertGeben() + Speicher.MEMORY_SIZE) : "" + this.sp.WertGeben(), this.eqflag, this.ltflag, this.ovflag, var6 ? (this.adressmodus == 2 ? mnemonic + "I" : mnemonic) : var13, var12, "" + this.pc.WertGeben(), this.progadr, this.progmem, this.dataadr, this.datamem, this.stackadr, this.stackmem, microStepName);
		}

	}

	public void AnzeigeWiederholen() {
		if (this.letzterDa) {
			this.Melden(this.datenWert_letzter, this.adressWert_letzter, this.alu1_letzter, this.alu2_letzter, this.alu3_letzter, this.opMnemo_letzter, -1, -1, -1, this.mikro_letzter);
		}

	}

	protected void reportError(String message) {
		Iterator listeners = this.beobachter.iterator();

		while(listeners.hasNext()) {
			CpuBeobachter listener = (CpuBeobachter) listeners.next();
			listener.Fehlermeldung(message);
		}

	}

	protected void OpcodeTesten() {
		switch(this.befehlscode) {
			case 0:
			case 1:
			case 46:
			case 99:
				if (this.adressmodus != 0 || this.adresse != 0) {
					this.befehlscode = -1;
				}
				break;
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 16:
			case 17:
			case 18:
			case 19:
			case 22:
			case 23:
			case 24:
			case 25:
			case 26:
			case 27:
			case 28:
			case 29:
			case 38:
			case 39:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70:
			case 71:
			case 72:
			case 73:
			case 74:
			case 75:
			case 76:
			case 77:
			case 78:
			case 79:
			case 80:
			case 81:
			case 82:
			case 83:
			case 84:
			case 85:
			case 86:
			case 87:
			case 88:
			case 89:
			case 90:
			case 91:
			case 92:
			case 93:
			case 94:
			case 95:
			case 96:
			case 97:
			case 98:
			default:
				if (this.erweitert) {
					switch(this.befehlscode) {
						case 5:
							if (this.adressmodus == 0) {
								this.befehlscode = -1;
							}

							return;
						case 6:
						case 25:
						case 26:
							if (this.adressmodus != 0 || this.adresse != 0) {
								this.befehlscode = -1;
							}

							return;
						case 7:
						case 8:
							if (this.adressmodus != 2) {
								this.befehlscode = -1;
							}

							return;
						default:
							this.befehlscode = -1;
					}
				} else {
					this.befehlscode = -1;
				}
				break;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 20:
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
			case 45:
				if (this.adressmodus == 0) {
					this.befehlscode = -1;
				}
				break;
			case 21:
				if (this.adressmodus == 0 || this.adressmodus == 2 || this.adressmodus == 6) {
					this.befehlscode = -1;
				}
				break;
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			case 36:
			case 37:
				if (this.adressmodus != 1) {
					this.befehlscode = -1;
				}
		}

	}

	public void Ausführen() {
		long var1 = System.currentTimeMillis() + this.schranke * 1000L;

		do {
			this.Schritt();
		} while(this.befehlscode != 99 && this.befehlscode != -1 && var1 > System.currentTimeMillis());

		if (this.befehlscode != 99 && this.befehlscode != -1) {
			this.reportError("Programmabbruch wegen Zeitüberschreitung");
		}

	}

	public abstract void Schritt();

	public abstract void MikroSchritt();

	protected int OperandenwertGeben(int var1, int var2) {
		switch(var2) {
			case 1:
				return this.speicher.WortMitVorzeichenGeben(var1);
			case 2:
				return var1;
			case 3:
				return this.speicher.WortMitVorzeichenGeben(this.speicher.WortOhneVorzeichenGeben(var1));
			case 4:
				return this.speicher.WortMitVorzeichenGeben(var1 + this.sp.WertGeben());
			case 5:
				return this.speicher.WortMitVorzeichenGeben(this.speicher.WortOhneVorzeichenGeben(var1 + this.sp.WertGeben()));
			case 6:
				return var1 + this.sp.WertGeben();
			default:
				return 0;
		}
	}

	public void ZurückSetzen() {
		this.pc.WertSetzen(0);
		this.a.WertSetzen(0);
		this.sp.WertSetzen(-2);
		this.ltflag = false;
		this.eqflag = false;
		this.ovflag = false;
		this.befehlscode = -1;
		this.adressmodus = 0;
		this.adresse = 0;

		int var1;
		for(var1 = 0; var1 < this.progmem.length; ++var1) {
			this.progadr[var1] = "";
			this.progmem[var1] = "";
		}

		for(var1 = 0; var1 < this.datamem.length; ++var1) {
			this.dataadr[var1] = "";
			this.datamem[var1] = "";
		}

		this.progAdrAlt = 0;
		this.dataAdrAlt = 0;
		this.stackAdrAlt = -2;
		this.Melden("", "", "", "", "", true, -1, -1, -1, "");
	}

	public void Übertragen(Cpu var1) {
		if (this != var1) {
			var1.pc.WertSetzen(this.pc.WertGeben());
			var1.a.WertSetzen(this.a.WertGeben());
			var1.sp.WertSetzen(this.sp.WertGeben());
			var1.ltflag = this.ltflag;
			var1.eqflag = this.eqflag;
			var1.ovflag = this.ovflag;
			var1.befehlscode = this.befehlscode;
			var1.adressmodus = this.adressmodus;
			var1.adresse = this.adresse;
			var1.progAdrAlt = this.progAdrAlt;
			var1.dataAdrAlt = this.dataAdrAlt;
			var1.stackAdrAlt = this.stackAdrAlt;
			var1.erweitert = this.erweitert;
		}

	}

	public static Cpu CpuErzeugen(String type) {
		if ("einfach".equals(type)) {
			return new CpuEinfach(Speicher.getMemory());
		} else {
			return "detail" == type ? new CpuDetail(Speicher.getMemory()) : null;
		}
	}

	public void assemble(String var1, FehlerVerwaltung var2) {
		(new Parser(new Scanner(var1), this.speicher, var2, this.erweitert)).parse();
	}

	public void Übersetzen(String var1, FehlerVerwaltung var2) {
		String var3;
		if (this.erweitert) {
			var3 = (new ParserErweitert(var1, var2)).Parse();
		} else {
			var3 = (new ParserEinfach(var1, var2)).Parse();
		}

		if (!var2.FehlerAufgetreten()) {
			this.speicher.clearMemory();
			(new Parser(new Scanner(var3), this.speicher, var2, this.erweitert)).parse();
		}

	}

	public void setTimeout(int seconds) {
		this.schranke = (long)seconds;
	}

	public void addMemoryListener(MemoryListener memoryListener) {
		this.speicher.Registrieren(memoryListener);
	}
}
