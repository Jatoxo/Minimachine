//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

import java.util.ArrayList;

public class FehlerVerwaltung {
	private ArrayList<String> meldungen = new ArrayList();
	private ArrayList<Integer> positionen = new ArrayList();

	public FehlerVerwaltung() {
	}

	public void FehlerEintragen(String var1, int var2) {
		this.meldungen.add(var1);
		this.positionen.add(var2);
	}

	public boolean FehlerAufgetreten() {
		return this.meldungen.size() != 0;
	}

	public String FehlertextMelden() {
		return (String)this.meldungen.get(0);
	}

	public int FehlerpositionMelden() {
		return (Integer)this.positionen.get(0);
	}
}
