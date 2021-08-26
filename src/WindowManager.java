//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.ArrayList;
import java.util.Iterator;

class WindowManager {
	private ArrayList<Anzeige> offen = new ArrayList();
	private Anzeige cpuDisplay;
	private Anzeige cpuSimpleDisplay;
	private Anzeige cpuGraphicalDisplay;
	private Anzeige cpuSimpleExtendedDisplay;
	private Anzeige speicheranzeige;

	WindowManager(Anzeige cpuDisplay, Anzeige cpuDisplay2, Anzeige cpuDisplayAdvanced, Anzeige storageDisplay) {
		this.cpuDisplay = cpuDisplay;
		this.cpuSimpleDisplay = cpuDisplay;
		this.cpuGraphicalDisplay = cpuDisplay2;
		this.cpuSimpleExtendedDisplay = cpuDisplayAdvanced;
		cpuDisplay2.hide();
		cpuDisplayAdvanced.hide();
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

		this.cpuDisplay.FenstereintragHinzufügen(var2, var1);
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

		this.cpuDisplay.FenstereintragEntfernen(var2);
		this.speicheranzeige.FenstereintragEntfernen(var2);
	}

	void EditorTitelÄndern(Anzeige var1) {
		int var2 = this.offen.indexOf(var1);
		Iterator var3 = this.offen.iterator();

		while(var3.hasNext()) {
			Anzeige var4 = (Anzeige)var3.next();
			var4.FenstereintragÄndern(var2, var1);
		}

		this.cpuDisplay.FenstereintragÄndern(var2, var1);
		this.speicheranzeige.FenstereintragÄndern(var2, var1);
	}

	void CpuFensterAuswählen() {
		this.cpuDisplay.show();
	}

	void SpeicherFensterAuswählen() {
		this.speicheranzeige.show();
	}

	void setCpuDisplayMode(boolean graphical, boolean extended) {
		if (graphical) {
			this.cpuDisplay = this.cpuGraphicalDisplay;
			this.cpuGraphicalDisplay.show();
			this.cpuSimpleDisplay.hide();
			this.cpuSimpleExtendedDisplay.hide();

		} else {
			if (extended) {
				this.cpuDisplay = this.cpuSimpleExtendedDisplay;
				this.cpuSimpleExtendedDisplay.show();
				this.cpuSimpleDisplay.hide();
			} else {
				this.cpuDisplay = this.cpuSimpleDisplay;
				this.cpuSimpleDisplay.show();
				this.cpuSimpleExtendedDisplay.hide();
			}

			this.cpuGraphicalDisplay.hide();
			((CpuDisplay)this.cpuDisplay).erweiterungenItem.setSelected(extended);
		}

	}

	void BeendenMitteilen() {
		for(Anzeige display : this.offen) {
			display.BeendenMitteilen();
		}




	}
}
