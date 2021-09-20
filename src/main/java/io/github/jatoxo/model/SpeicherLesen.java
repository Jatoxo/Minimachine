//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package main.java.io.github.jatoxo.model;

public abstract class SpeicherLesen {
	public SpeicherLesen() {
	}

	public static int WortOhneVorzeichenGeben(int var0) {
		return Speicher.getMemory().WortOhneVorzeichenGeben(var0);
	}

	public static int WortMitVorzeichenGeben(int var0) {
		return Speicher.getMemory().WortMitVorzeichenGeben(var0);
	}

	public static void WortSetzen(int var0, int var1) {
		Speicher.getMemory().WortSetzen(var0, var1);
	}

	public static void SpeicherLöschen() {
		Speicher.getMemory().clearMemory();
	}
}
