//


package io.github.jatoxo.model;

import io.github.jatoxo.model.minilanguage.ParserEinfach;
import io.github.jatoxo.model.minilanguage.ParserErweitert;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Cpu implements CpuMeldungsErzeuger {
	static final int NOOP = 0;
	static final int RESET = 1;
	static final int JSR = 5;
	static final int RTS = 6;
	static final int RSV = 7;
	static final int REL = 8;
	static final int ADD = 10;
	static final int SUB = 11;
	static final int MUL = 12;
	static final int DIV = 13;
	static final int MOD = 14;
	static final int CMP = 15;
	static final int LOAD = 20;
	static final int STORE = 21;
	static final int PUSH = 25;
	static final int POP = 26;
	static final int JGT = 30;
	static final int JGE = 31;
	static final int JLT = 32;
	static final int JLE = 33;
	static final int JEQ = 34;
	static final int JNE = 35;
	static final int JMP = 36;
	static final int JOV = 37;
	static final int AND = 40;
	static final int OR = 41;
	static final int XOR = 42;
	static final int SHL = 43;
	static final int SHR = 44;
	static final int SHRA = 45;
	static final int NOT = 46;
	static final int HALT = 99;
	static final int ILLEGAL_OP = -1;
	public static final int NO_ADDR = 0;
	public static final int ABS_ADDR = 1;
	public static final int IMM_ADDR = 2;
	public static final int IND_ADDR = 3;
	public static final int OFFSET_ADDR = 4;
	public static final int OFFSET_IND_ADDR = 5;
	public static final int OFFSET_IMM_ADDR = 6;
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
	private final String[] progadr = new String[4];
	private final String[] progmem = new String[4];
	private final String[] dataadr = new String[2];
	private final String[] datamem = new String[2];
	private final String[] stackadr = new String[3];
	private final String[] stackmem = new String[3];
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
		this.beobachter = new ArrayList<>();
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

	protected void Melden(String data, String address, String alu1, String alu2, String alu3, boolean lastOpMnemo, int var7, int var8, int var9, String microStepName) {
		this.letzterDa = true;
		this.datenWert_letzter = data;
		this.adressWert_letzter = address;
		this.alu1_letzter = alu1;
		this.alu2_letzter = alu2;
		this.alu3_letzter = alu3;
		this.mikro_letzter = microStepName;
		this.opMnemo_letzter = lastOpMnemo;

		String accumulator = "" + this.a.WertGeben();
		String addr = "" + this.adresse;
		String opcode = "" + (this.befehlscode + this.adressmodus * 256);
		if (this.hexaAnzeige) {
			data = this.HexaString(data);
			alu1 = this.HexaString(alu1);
			alu2 = this.HexaString(alu2);
			alu3 = this.HexaString(alu3);
			accumulator = this.HexaString(accumulator);
			addr = this.HexaString(addr);
			opcode = this.HexaString(opcode);
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
			listeners.Befehlsmeldung(data, address, alu1, alu2, alu3, accumulator, this.sp.WertGeben() < 0 ? "" + (this.sp.WertGeben() + Speicher.MEMORY_SIZE) : "" + this.sp.WertGeben(), this.eqflag, this.ltflag, this.ovflag, lastOpMnemo ? (this.adressmodus == 2 ? mnemonic + "I" : mnemonic) : opcode, addr, "" + this.pc.WertGeben(), this.progadr, this.progmem, this.dataadr, this.datamem, this.stackadr, this.stackmem, microStepName);
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
			case NOOP:
			case RESET:
			case NOT:
			case HALT:
				if (this.adressmodus != 0 || this.adresse != 0) {
					this.befehlscode = -1;
				}
				break;
			case 2:
			case 3:
			case 4:
			case JSR:
			case RTS:
			case RSV:
			case REL:
			case 9:
			case 16:
			case 17:
			case 18:
			case 19:
			case 22:
			case 23:
			case 24:
			case PUSH:
			case POP:
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
						case JSR:
							if (this.adressmodus == NO_ADDR) {
								this.befehlscode = ILLEGAL_OP;
							}

							return;
						case RTS:
						case PUSH:
						case POP:
							if (this.adressmodus != NO_ADDR || this.adresse != 0) {
								this.befehlscode = ILLEGAL_OP;
							}

							return;
						case RSV:
						case REL:
							if (this.adressmodus != IMM_ADDR) {
								this.befehlscode = ILLEGAL_OP;
							}

							return;
						default:
							this.befehlscode = ILLEGAL_OP;
					}
				} else {
					this.befehlscode = ILLEGAL_OP;
				}
				break;
			case ADD:
			case SUB:
			case MUL:
			case DIV:
			case MOD:
			case CMP:
			case LOAD:
			case AND:
			case OR:
			case XOR:
			case SHL:
			case SHR:
			case SHRA:
				if (this.adressmodus == NO_ADDR) {
					this.befehlscode = ILLEGAL_OP;
				}
				break;
			case STORE:
				if (this.adressmodus == NO_ADDR || this.adressmodus == IMM_ADDR || this.adressmodus == OFFSET_IMM_ADDR) {
					this.befehlscode = ILLEGAL_OP;
				}
				break;
			case JGT:
			case JGE:
			case JLT:
			case JLE:
			case JEQ:
			case JNE:
			case JMP:
			case JOV:
				if (this.adressmodus != ABS_ADDR) {
					this.befehlscode = ILLEGAL_OP;
				}
		}

	}

	public void Ausführen() {
		long timeout = System.currentTimeMillis() + this.schranke * 1000L;

		do {
			this.Schritt();
		} while(this.befehlscode != HALT && this.befehlscode != ILLEGAL_OP && timeout > System.currentTimeMillis());

		if (this.befehlscode != HALT && this.befehlscode != ILLEGAL_OP) {
			this.reportError("Programmabbruch wegen Zeitüberschreitung");
		}

	}

	public abstract void Schritt();

	public abstract void MikroSchritt();

	protected int OperandenwertGeben(int address, int addressingMode) {
		switch(addressingMode) {
			case ABS_ADDR:
				return this.speicher.WortMitVorzeichenGeben(address);
			case IMM_ADDR:
				return address;
			case IND_ADDR:
				return this.speicher.WortMitVorzeichenGeben(this.speicher.WortOhneVorzeichenGeben(address));
			case OFFSET_ADDR:
				return this.speicher.WortMitVorzeichenGeben(address + this.sp.WertGeben());
			case OFFSET_IND_ADDR:
				return this.speicher.WortMitVorzeichenGeben(this.speicher.WortOhneVorzeichenGeben(address + this.sp.WertGeben()));
			case OFFSET_IMM_ADDR:
				return address + this.sp.WertGeben();
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

		int i;
		for(i = 0; i < this.progmem.length; ++i) {
			this.progadr[i] = "";
			this.progmem[i] = "";
		}

		for(i = 0; i < this.datamem.length; ++i) {
			this.dataadr[i] = "";
			this.datamem[i] = "";
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
			return "detail".equals(type) ? new CpuDetail(Speicher.getMemory()) : null;
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
