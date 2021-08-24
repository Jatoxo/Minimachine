//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

import java.util.ArrayList;
import java.util.Iterator;

class Speicher implements SpeicherMeldungsErzeuger {
	private short[] speicher = new short[65536];
	private int geändert;
	private ArrayList<SpeicherBeobachter> beobachter = new ArrayList();
	private static Speicher derSpeicher = new Speicher();

	static Speicher SpeicherGeben() {
		return derSpeicher;
	}

	private Speicher() {
		this.SpeicherLöschen();
		this.geändert = -1;
	}

	public void Registrieren(SpeicherBeobachter var1) {
		this.beobachter.add(var1);
	}

	public void Abmelden(SpeicherBeobachter var1) {
		this.beobachter.remove(var1);
	}

	private void Melden() {
		Iterator var1 = this.beobachter.iterator();

		while(var1.hasNext()) {
			SpeicherBeobachter var2 = (SpeicherBeobachter)var1.next();
			var2.SpeicherGeändertMelden(this.geändert);
		}

	}

	void SpeicherLöschen() {
		for(int var1 = 0; var1 < this.speicher.length; ++var1) {
			this.speicher[var1] = 0;
		}

		this.speicher[this.speicher.length - 1] = -1;
		this.speicher[this.speicher.length - 2] = -1;
		this.Melden();
	}

	void WortSetzen(int var1, int var2) {
		if (var2 > 32767) {
			var2 -= 65536;
		}

		if (var1 < 0) {
			var1 += 65536;
		}

		this.speicher[var1] = (short)var2;
		this.geändert = var1;
		this.Melden();
	}

	int WortOhneVorzeichenGeben(int var1) {
		int var2 = this.WortMitVorzeichenGeben(var1);
		if (var2 < 0) {
			var2 += 65536;
		}

		return var2;
	}

	int WortMitVorzeichenGeben(int var1) {
		if (var1 < 0) {
			var1 += 65536;
		}

		return this.speicher[var1];
	}
}
