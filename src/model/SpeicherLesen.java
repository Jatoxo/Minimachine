//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

public abstract class SpeicherLesen {
	public SpeicherLesen() {
	}

	public static int WortOhneVorzeichenGeben(int var0) {
		return Speicher.SpeicherGeben().WortOhneVorzeichenGeben(var0);
	}

	public static int WortMitVorzeichenGeben(int var0) {
		return Speicher.SpeicherGeben().WortMitVorzeichenGeben(var0);
	}

	public static void WortSetzen(int var0, int var1) {
		Speicher.SpeicherGeben().WortSetzen(var0, var1);
	}

	public static void SpeicherLöschen() {
		Speicher.SpeicherGeben().SpeicherLöschen();
	}
}
