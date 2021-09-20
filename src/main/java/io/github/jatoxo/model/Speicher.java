//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.jatoxo.model;

import java.util.ArrayList;
import java.util.Arrays;

class Speicher implements SpeicherMeldungsErzeuger {
	public static int MEMORY_SIZE = 1 << 16;
	public static int WORD_SIZE = 1 << 16;

	private short[] speicher = new short[MEMORY_SIZE];
	private int changed;
	private ArrayList<MemoryListener> listeners = new ArrayList<>();
	private static Speicher derSpeicher = new Speicher();

	static Speicher getMemory() {
		return derSpeicher;
	}

	private Speicher() {
		this.clearMemory();
		this.changed = -1;
	}

	public void Registrieren(MemoryListener newListeners) {
		this.listeners.add(newListeners);
	}

	public void Abmelden(MemoryListener var1) {
		this.listeners.remove(var1);
	}

	private void Melden() {

		for(MemoryListener listener : this.listeners) {
			listener.memoryChanged(this.changed);
		}

	}

	void clearMemory() {
		Arrays.fill(this.speicher, (short) 0);

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
		this.changed = var1;
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
