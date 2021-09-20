//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

import R.R;

class CpuEinfach extends Cpu {
	CpuEinfach(Speicher storage) {
		super(storage);
	}

	public void Schritt() {
		this.befehlscode = this.speicher.WortOhneVorzeichenGeben(this.pc.WertGeben());
		this.pc.increment(1);
		this.adressmodus = this.befehlscode / 256;
		this.befehlscode %= 256;
		this.adresse = this.speicher.WortMitVorzeichenGeben(this.pc.WertGeben());
		this.OpcodeTesten();
		this.pc.increment(1);
		int var1;
		int var2;
		int var3;
		label288:
		switch(this.befehlscode) {
			case 0:
			case 99:
				break;
			case 1:
				this.ZurückSetzen();
				this.speicher.clearMemory();
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
							this.sp.decrement(1);
							this.speicher.WortSetzen(this.sp.WertGeben(), this.pc.WertGeben());
							this.pc.WertSetzen(this.adresse);
							break label288;
						case 6:
							this.pc.WertSetzen(this.speicher.WortMitVorzeichenGeben(this.sp.WertGeben()));
							this.sp.increment(1);
							break label288;
						case 7:
							this.sp.decrement(this.adresse);
							break label288;
						case 8:
							this.sp.increment(this.adresse);
							break label288;
						case 25:
							this.sp.decrement(1);
							this.speicher.WortSetzen(this.sp.WertGeben(), this.a.WertGeben());
							break label288;
						case 26:
							this.a.WertSetzen(this.speicher.WortMitVorzeichenGeben(this.sp.WertGeben()));
							this.sp.increment(1);
							this.ovflag = false;
							var3 = this.a.WertGeben();
							this.ltflag = var3 < 0;
							this.eqflag = var3 == 0;
							break label288;
						default:
							this.reportError(R.string("cpu_illegal_opcode"));
							this.befehlscode = -1;
					}
				} else {
					this.reportError(R.string("cpu_illegal_opcode"));
					this.befehlscode = -1;
				}
				break;
			case 11:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				var3 = var1 - var2;
				this.ovflag = var3 > 32767 || var3 < -32768;
				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
				break;
			case 12:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				var3 = var1 * var2;
				this.ovflag = var3 > 32767 || var3 < -32768;
				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
				break;
			case 13:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				if (var2 == 0) {
					this.reportError(R.string("cpu_zero_division"));
					var3 = var1;
					this.ovflag = true;
				} else {
					var3 = var1 / var2;
					this.ovflag = var3 > 32767 || var3 < -32768;
				}

				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
				break;
			case 14:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				if (var2 == 0) {
					this.reportError(R.string("cpu_zero_division"));
					var3 = 0;
					this.ovflag = true;
				} else {
					var3 = var1 % var2;
					this.ovflag = var3 > 32767 || var3 < -32768;
				}

				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
				break;
			case 15:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				this.ovflag = false;
				this.ltflag = var1 < var2;
				this.eqflag = var1 == var2;
				break;
			case 20:
				this.a.WertSetzen(this.OperandenwertGeben(this.adresse, this.adressmodus));
				this.ovflag = false;
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
				break;
			case 21:
				if (this.adressmodus == 3) {
					this.adresse = this.speicher.WortOhneVorzeichenGeben(this.adresse);
				} else if (this.adressmodus == 4) {
					this.adresse += this.sp.WertGeben();
				} else if (this.adressmodus == 5) {
					this.adresse = this.speicher.WortOhneVorzeichenGeben(this.adresse + this.sp.WertGeben());
				}

				this.speicher.WortSetzen(this.adresse, this.a.WertGeben());
				break;
			case 30:
				if (!this.ltflag && !this.eqflag) {
					this.pc.WertSetzen(this.adresse);
				}
				break;
			case 31:
				if (!this.ltflag) {
					this.pc.WertSetzen(this.adresse);
				}
				break;
			case 32:
				if (this.ltflag) {
					this.pc.WertSetzen(this.adresse);
				}
				break;
			case 33:
				if (this.ltflag || this.eqflag) {
					this.pc.WertSetzen(this.adresse);
				}
				break;
			case 34:
				if (this.eqflag) {
					this.pc.WertSetzen(this.adresse);
				}
				break;
			case 35:
				if (!this.eqflag) {
					this.pc.WertSetzen(this.adresse);
				}
				break;
			case 36:
				this.pc.WertSetzen(this.adresse);
				break;
			case 37:
				if (this.ovflag) {
					this.pc.WertSetzen(this.adresse);
				}
				break;
			case 40:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				var3 = var1 & var2;
				this.ovflag = false;
				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
				break;
			case 41:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				var3 = var1 | var2;
				this.ovflag = false;
				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
				break;
			case 42:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				var3 = var1 ^ var2;
				this.ovflag = false;
				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
				break;
			case 43:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				var3 = var1 << var2;
				this.ovflag = false;
				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
				break;
			case 44:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				var3 = (var1 & '\uffff') >> var2;
				this.ovflag = false;
				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
				break;
			case 45:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				var3 = var1 >> var2;
				this.ovflag = false;
				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
				break;
			case 46:
				var1 = this.a.WertGeben();
				var3 = ~var1;
				this.ovflag = false;
				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
			case 10:
				var1 = this.a.WertGeben();
				var2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				var3 = var1 + var2;
				this.ovflag = var3 > 32767 || var3 < -32768;
				this.a.WertSetzen(var3);
				var3 = this.a.WertGeben();
				this.ltflag = var3 < 0;
				this.eqflag = var3 == 0;
		}

		this.Melden("", "", "", "", "", true, this.pc.WertGeben() - 2, this.adressmodus == 1 ? this.adresse : -1, this.sp.WertGeben(), "");
	}

	public void MikroSchritt() {
	}
}
