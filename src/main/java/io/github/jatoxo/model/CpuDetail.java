
package main.java.io.github.jatoxo.model;

import main.java.io.github.jatoxo.R;

class CpuDetail extends Cpu {
	private MikroSchritte mikroStatus;
	private int op1;
	private int op2;
	private int res;
	private int pcAlt;

	CpuDetail(Speicher var1) {
		super(var1);
		this.mikroStatus = MikroSchritte.COMPLETE;
		this.pcAlt = 0;

	}

	public void ZurückSetzen() {
		this.mikroStatus = MikroSchritte.COMPLETE;
		this.pcAlt = 0;
		super.ZurückSetzen();
	}

	public void Schritt() {
		do {
			this.MikroSchritt();
		} while(this.mikroStatus != MikroSchritte.COMPLETE);

	}

	public void MikroSchritt() {
		switch(this.mikroStatus) {
			case COMPLETE:
				this.mikroStatus = MikroSchritte.FETCH_OPCODE;
				this.Melden("", "", "", "", "", true, this.pc.WertGeben(), -1, this.sp.WertGeben(), R.string("micro_step_complete"));
				break;
			case FETCH_OPCODE:
				this.pcAlt = this.pc.WertGeben();
				int var1 = this.speicher.WortOhneVorzeichenGeben(this.pcAlt);
				this.pc.increment(1);
				this.adressmodus = var1 / 256;
				this.befehlscode = var1 % 256;
				this.mikroStatus = MikroSchritte.FETCH_ADDRESS;
				this.Melden("" + var1, "" + (this.pcAlt < 0 ? Speicher.MEMORY_SIZE + this.pcAlt : this.pcAlt), "", "", "", false, this.pcAlt, -1, this.sp.WertGeben(), R.string("micro_step_fetch_op"));
				break;
			case FETCH_ADDRESS:
				this.pcAlt = this.pc.WertGeben();
				this.adresse = this.speicher.WortMitVorzeichenGeben(this.pcAlt);
				this.pc.increment(1);
				String var3;
				if (this.adressmodus != 4 && this.adressmodus != 5) {
					if (this.adressmodus == 6) {
						var3 = "" + this.adresse;
						this.adresse += this.sp.WertGeben();
						this.adressmodus = 2;
						this.mikroStatus = MikroSchritte.DECODE;
						this.Melden("$" + this.adresse + "(SP)", "" + (this.pcAlt < 0 ? Speicher.MEMORY_SIZE + this.pcAlt : this.pcAlt), var3, "" + this.sp.WertGeben(), "" + this.adresse, false, -1, -1, -1, R.string("micro_step_fetch_addr"));
					} else {
						if (this.adressmodus == 3) {
							this.mikroStatus = MikroSchritte.FETCH_INDIRECT;
							this.adressmodus = 1;
						} else {
							this.mikroStatus = MikroSchritte.DECODE;
						}

						this.Melden("" + this.adresse, "" + (this.pcAlt < 0 ? Speicher.MEMORY_SIZE + this.pcAlt : this.pcAlt), "", "", "", false, -1, -1, -1, R.string("micro_step_fetch_addr"));
					}
				} else {
					var3 = "" + this.adresse;
					this.adresse += this.sp.WertGeben();
					if (this.adressmodus == 5) {
						this.mikroStatus = MikroSchritte.FETCH_INDIRECT;
					} else {
						this.mikroStatus = MikroSchritte.DECODE;
					}

					this.adressmodus = 1;
					this.Melden("" + this.adresse + "(SP)", "" + (this.pcAlt < 0 ? Speicher.MEMORY_SIZE + this.pcAlt : this.pcAlt), var3, "" + this.sp.WertGeben(), "" + this.adresse, false, -1, -1, -1, R.string("micro_step_fetch_addr"));
				}
				break;
			case FETCH_INDIRECT:
				this.mikroStatus = MikroSchritte.DECODE;
				int var2 = this.adresse;
				this.adresse = this.speicher.WortMitVorzeichenGeben(this.adresse);
				this.Melden("" + this.adresse, "" + var2, "", "", "", false, -1, -1, -1, R.string("micro_step_fetch_indir"));
				break;
			case DECODE:
				this.OpcodeTesten();
				this.mikroStatus = MikroSchritte.EXECUTE_1;
				this.Melden("", "", "", "", "", true, -1, -1, -1, R.string("micro_step_decode"));
				break;
			case EXECUTE_1:
				this.Excecute1();
				break;
			case EXECUTE_2:
				this.Excecute2();
		}

	}

	private void Excecute1() {
		int var1;
		switch(this.befehlscode) {
			case 0:
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "", "", "", true, -1, -1, -1, R.string("micro_step_exec_1"));
				break;
			case 1:
				this.ZurückSetzen();
				this.speicher.clearMemory();
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "", "", "", true, -1, -1, -1, R.string("micro_step_exec_1"));
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
					int var2;
					switch(this.befehlscode) {
						case 5:
							this.mikroStatus = MikroSchritte.EXECUTE_2;
							var1 = this.sp.WertGeben() < 0 ? this.sp.WertGeben() + Speicher.MEMORY_SIZE : this.sp.WertGeben();
							this.Melden("", "", "" + var1, "-1", "" + (var1 - 1), true, -1, -1, -1, R.string("micro_step_exec_1"));
							this.sp.decrement(1);
							return;
						case 6:
							this.mikroStatus = MikroSchritte.EXECUTE_2;
							var1 = this.speicher.WortMitVorzeichenGeben(this.sp.WertGeben());
							this.pc.WertSetzen(var1);
							this.Melden("" + (var1 < 0 ? var1 + 65536 : var1), "" + (this.sp.WertGeben() < 0 ? this.sp.WertGeben() + 65536 : this.sp.WertGeben()), "", "", "", true, this.pc.WertGeben(), -1, -1, R.string("micro_step_exec_1"));
							return;
						case 7:
							this.mikroStatus = MikroSchritte.COMPLETE;
							var2 = this.sp.WertGeben() < 0 ? this.sp.WertGeben() + 65536 : this.sp.WertGeben();
							this.sp.decrement(this.adresse);
							var1 = this.sp.WertGeben() < 0 ? this.sp.WertGeben() + 65536 : this.sp.WertGeben();
							this.Melden("", "", "" + var2, "" + this.adresse, "" + var1, true, -1, -1, this.sp.WertGeben(), R.string("micro_step_exec_1"));
							return;
						case 8:
							this.mikroStatus = MikroSchritte.COMPLETE;
							var2 = this.sp.WertGeben() < 0 ? this.sp.WertGeben() + 65536 : this.sp.WertGeben();
							this.sp.increment(this.adresse);
							var1 = this.sp.WertGeben() < 0 ? this.sp.WertGeben() + 65536 : this.sp.WertGeben();
							this.Melden("", "", "" + var2, "" + this.adresse, "" + var1, true, -1, -1, this.sp.WertGeben(), R.string("micro_step_exec_1"));
							return;
						case 25:
							this.mikroStatus = MikroSchritte.EXECUTE_2;
							var1 = this.sp.WertGeben() < 0 ? this.sp.WertGeben() + 65536 : this.sp.WertGeben();
							this.Melden("", "", "" + var1, "-1", "" + (var1 - 1), true, -1, -1, -1, R.string("micro_step_exec_1"));
							this.sp.decrement(1);
							return;
						case 26:
							this.mikroStatus = MikroSchritte.EXECUTE_2;
							this.a.WertSetzen(this.speicher.WortMitVorzeichenGeben(this.sp.WertGeben()));
							this.ovflag = false;
							var1 = this.a.WertGeben();
							this.ltflag = var1 < 0;
							this.eqflag = var1 == 0;
							var1 = this.sp.WertGeben() < 0 ? this.sp.WertGeben() + 65536 : this.sp.WertGeben();
							this.Melden("" + this.a.WertGeben(), "" + var1, "", "", "", true, -1, -1, this.sp.WertGeben(), R.string("micro_step_exec_1"));
							return;
						default:
							this.mikroStatus = MikroSchritte.COMPLETE;
							this.Melden("", "", "", "", "", false, -1, -1, -1, R.string("micro_step_exec_1"));
							this.reportError(R.string("cpu_illegal_opcode"));
							this.befehlscode = -1;
					}
				} else {
					this.mikroStatus = MikroSchritte.COMPLETE;
					this.Melden("", "", "", "", "", false, -1, -1, -1, R.string("micro_step_exec_1"));
					this.reportError(R.string("cpu_illegal_opcode"));
					this.befehlscode = -1;
				}
				break;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
			case 45:
				this.op1 = this.a.WertGeben();
				this.op2 = this.OperandenwertGeben(this.adresse, this.adressmodus);
				this.mikroStatus = MikroSchritte.EXECUTE_2;
				if (this.adressmodus == 1) {
					this.Melden("" + this.op2, "" + (this.adresse < 0 ? 65536 + this.adresse : this.adresse), "" + this.op1, "" + this.op2, "", true, -1, this.adresse, -1, R.string("micro_step_exec_1"));
				} else {
					this.Melden("", "", "" + this.op1, "" + this.op2, "", true, -1, -1, -1, R.string("micro_step_exec_1"));
				}
				break;
			case 20:
				this.a.WertSetzen(this.OperandenwertGeben(this.adresse, this.adressmodus));
				this.ovflag = false;
				var1 = this.a.WertGeben();
				this.ltflag = var1 < 0;
				this.eqflag = var1 == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				if (this.adressmodus == 1) {
					this.Melden("" + var1, "" + (this.adresse < 0 ? 65536 + this.adresse : this.adresse), "", "", "", true, -1, this.adresse, -1, R.string("micro_step_exec_1"));
				} else {
					this.Melden("", "", "", "", "", true, -1, -1, -1, R.string("micro_step_exec_1"));
				}
				break;
			case 21:
				this.speicher.WortSetzen(this.adresse, this.a.WertGeben());
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("" + this.a.WertGeben(), "" + (this.adresse < 0 ? 65536 + this.adresse : this.adresse), "", "", "", true, -1, this.adresse, -1, R.string("micro_step_exec_1"));
				break;
			case 30:
				if (!this.ltflag && !this.eqflag) {
					this.pc.WertSetzen(this.adresse);
				}

				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "", "", "", true, this.pc.WertGeben(), -1, -1, R.string("micro_step_exec_1"));
				break;
			case 31:
				if (!this.ltflag) {
					this.pc.WertSetzen(this.adresse);
				}

				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "", "", "", true, this.pc.WertGeben(), -1, -1, R.string("micro_step_exec_1"));
				break;
			case 32:
				if (this.ltflag) {
					this.pc.WertSetzen(this.adresse);
				}

				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "", "", "", true, this.pc.WertGeben(), -1, -1, R.string("micro_step_exec_1"));
				break;
			case 33:
				if (this.ltflag || this.eqflag) {
					this.pc.WertSetzen(this.adresse);
				}

				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "", "", "", true, this.pc.WertGeben(), -1, -1, R.string("micro_step_exec_1"));
				break;
			case 34:
				if (this.eqflag) {
					this.pc.WertSetzen(this.adresse);
				}

				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "", "", "", true, this.pc.WertGeben(), -1, -1, R.string("micro_step_exec_1"));
				break;
			case 35:
				if (!this.eqflag) {
					this.pc.WertSetzen(this.adresse);
				}

				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "", "", "", true, this.pc.WertGeben(), -1, -1, R.string("micro_step_exec_1"));
				break;
			case 36:
				this.pc.WertSetzen(this.adresse);
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "", "", "", true, this.pc.WertGeben(), -1, -1, R.string("micro_step_exec_1"));
				break;
			case 37:
				if (this.ovflag) {
					this.pc.WertSetzen(this.adresse);
				}

				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "", "", "", true, this.pc.WertGeben(), -1, -1, R.string("micro_step_exec_1"));
				break;
			case 46:
				this.op1 = this.a.WertGeben();
				this.mikroStatus = MikroSchritte.EXECUTE_2;
				this.Melden("", "", "" + this.op1, "", "", true, -1, -1, -1, R.string("micro_step_exec_1"));
				break;
			case 99:
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "", "", "", true, -1, -1, -1, R.string("micro_step_exec_1"));
		}

	}

	private void Excecute2() {
		switch(this.befehlscode) {
			case 5:
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.speicher.WortSetzen(this.sp.WertGeben(), this.pc.WertGeben());
				this.res = this.pc.WertGeben() < 0 ? this.pc.WertGeben() + 65536 : this.pc.WertGeben();
				this.pc.WertSetzen(this.adresse);
				this.Melden("" + this.res, "" + (this.sp.WertGeben() < 0 ? this.sp.WertGeben() + 65536 : this.sp.WertGeben()), "", "", "", true, this.pc.WertGeben(), -1, -1, R.string("micro_step_exec_2"));
				break;
			case 6:
			case 26:
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.res = this.sp.WertGeben() < 0 ? this.sp.WertGeben() + 65536 : this.sp.WertGeben();
				this.Melden("", "", "" + this.res, "1", "" + (this.res + 1), true, -1, -1, -1, R.string("micro_step_exec_2"));
				this.sp.increment(1);
			case 7:
			case 8:
			case 9:
			case 16:
			case 17:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
			case 24:
			case 27:
			case 28:
			case 29:
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			case 36:
			case 37:
			case 38:
			case 39:
			default:
				break;
			case 10:
				this.res = this.op1 + this.op2;
				this.ovflag = this.res > 32767 || this.res < -32768;
				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 11:
				this.res = this.op1 - this.op2;
				this.ovflag = this.res > 32767 || this.res < -32768;
				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 12:
				this.res = this.op1 * this.op2;
				this.ovflag = this.res > 32767 || this.res < -32768;
				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 13:
				if (this.op2 == 0) {
					this.reportError("Division durch 0");
					this.res = this.op1;
					this.ovflag = true;
				} else {
					this.res = this.op1 / this.op2;
					this.ovflag = this.res > 32767 || this.res < -32768;
				}

				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 14:
				if (this.op2 == 0) {
					this.reportError("Division durch 0");
					this.res = this.op1;
					this.ovflag = true;
				} else {
					this.res = this.op1 % this.op2;
					this.ovflag = this.res > 32767 || this.res < -32768;
				}

				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 15:
				this.ovflag = false;
				this.ltflag = this.op1 < this.op2;
				this.eqflag = this.op1 == this.op2;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "", true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 25:
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.res = this.sp.WertGeben();
				this.Melden("" + this.a.WertGeben(), "" + (this.res < 0 ? 65536 + this.res : this.res), "", "", "", true, -1, -1, this.res, R.string("micro_step_exec_2"));
				this.speicher.WortSetzen(this.res, this.a.WertGeben());
				break;
			case 40:
				this.res = this.op1 & this.op2;
				this.ovflag = false;
				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 41:
				this.res = this.op1 | this.op2;
				this.ovflag = false;
				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 42:
				this.res = this.op1 ^ this.op2;
				this.ovflag = false;
				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 43:
				this.res = this.op1 << this.op2;
				this.ovflag = false;
				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 44:
				this.res = (this.op1 & '\uffff') >> this.op2;
				this.ovflag = false;
				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 45:
				this.res = this.op1 >> this.op2;
				this.ovflag = false;
				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "" + this.op2, "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
				break;
			case 46:
				this.res = ~this.op1;
				this.ovflag = false;
				this.a.WertSetzen(this.res);
				this.res = this.a.WertGeben();
				this.ltflag = this.res < 0;
				this.eqflag = this.res == 0;
				this.mikroStatus = MikroSchritte.COMPLETE;
				this.Melden("", "", "" + this.op1, "", "" + this.res, true, -1, -1, -1, R.string("micro_step_exec_2"));
		}

	}

	public void Übertragen(Cpu var1) {
		if (this != var1) {
			while(this.mikroStatus != MikroSchritte.COMPLETE) {
				this.MikroSchritt();
			}
		}

		super.Übertragen(var1);
	}
}
