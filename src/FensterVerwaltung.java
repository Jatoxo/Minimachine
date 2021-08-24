//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.ArrayList;
import java.util.Iterator;

class FensterVerwaltung {
	private ArrayList<Anzeige> offen = new ArrayList();
	private Anzeige cpuanzeige;
	private Anzeige cpuanzeigeEinfach;
	private Anzeige cpuanzeigeDetail;
	private Anzeige cpuanzeigeEinfachErweitert;
	private Anzeige speicheranzeige;

	FensterVerwaltung(Anzeige cpuDisplay, Anzeige cpuDisplay2, Anzeige cpuDisplayAdvanced, Anzeige storageDisplay) {
		this.cpuanzeige = cpuDisplay;
		this.cpuanzeigeEinfach = cpuDisplay;
		this.cpuanzeigeDetail = cpuDisplay2;
		this.cpuanzeigeEinfachErweitert = cpuDisplayAdvanced;
		cpuDisplay2.Ausblenden();
		cpuDisplayAdvanced.Ausblenden();
		this.speicheranzeige = storageDisplay;
	}

	void EditorEintragen(Anzeige var1) {
		int var2;
		for(var2 = 0; var2 < this.offen.size(); ++var2) {
			var1.FenstereintragHinzufügen(var2, (Anzeige)this.offen.get(var2));
		}

		this.offen.add(var1);
		var2 = this.offen.indexOf(var1);
		Iterator var3 = this.offen.iterator();

		while(var3.hasNext()) {
			Anzeige var4 = (Anzeige)var3.next();
			var4.FenstereintragHinzufügen(var2, var1);
		}

		this.cpuanzeige.FenstereintragHinzufügen(var2, var1);
		this.speicheranzeige.FenstereintragHinzufügen(var2, var1);
	}

	void EditorAustragen(Anzeige var1) {
		int var2 = this.offen.indexOf(var1);
		this.offen.remove(var1);
		Iterator var3 = this.offen.iterator();

		while(var3.hasNext()) {
			Anzeige var4 = (Anzeige)var3.next();
			var4.FenstereintragEntfernen(var2);
		}

		this.cpuanzeige.FenstereintragEntfernen(var2);
		this.speicheranzeige.FenstereintragEntfernen(var2);
	}

	void EditorTitelÄndern(Anzeige var1) {
		int var2 = this.offen.indexOf(var1);
		Iterator var3 = this.offen.iterator();

		while(var3.hasNext()) {
			Anzeige var4 = (Anzeige)var3.next();
			var4.FenstereintragÄndern(var2, var1);
		}

		this.cpuanzeige.FenstereintragÄndern(var2, var1);
		this.speicheranzeige.FenstereintragÄndern(var2, var1);
	}

	void CpuFensterAuswählen() {
		this.cpuanzeige.Aktivieren();
	}

	void SpeicherFensterAuswählen() {
		this.speicheranzeige.Aktivieren();
	}

	void CpuAnzeigeWählen(boolean var1, boolean var2) {
		if (var1) {
			this.cpuanzeige = this.cpuanzeigeDetail;
			this.cpuanzeigeDetail.Aktivieren();
			this.cpuanzeigeEinfach.Ausblenden();
			this.cpuanzeigeEinfachErweitert.Ausblenden();
		} else {
			if (var2) {
				this.cpuanzeige = this.cpuanzeigeEinfachErweitert;
				this.cpuanzeigeEinfachErweitert.Aktivieren();
				this.cpuanzeigeEinfach.Ausblenden();
			} else {
				this.cpuanzeige = this.cpuanzeigeEinfach;
				this.cpuanzeigeEinfach.Aktivieren();
				this.cpuanzeigeEinfachErweitert.Ausblenden();
			}

			this.cpuanzeigeDetail.Ausblenden();
			((CpuAnzeige)this.cpuanzeige).erweiterungenItem.setSelected(var2);
		}

	}

	void BeendenMitteilen() {
		Iterator var1 = this.offen.iterator();

		while(var1.hasNext()) {
			Anzeige var2 = (Anzeige)var1.next();
			var2.BeendenMitteilen();
		}

	}
}
