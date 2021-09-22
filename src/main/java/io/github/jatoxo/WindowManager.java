package io.github.jatoxo;//


import java.util.ArrayList;
import java.util.Iterator;

class WindowManager {
	private ArrayList<Anzeige> openEditorWindows = new ArrayList<>();
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
		//cpuDisplay2.hide();
		cpuDisplayAdvanced.hideWindow();
		this.speicheranzeige = storageDisplay;
	}




	void EditorEintragen(Anzeige var1) {
		int var2;
		for(var2 = 0; var2 < this.openEditorWindows.size(); ++var2) {
			var1.addWindowMenuEntry(var2, (Anzeige)this.openEditorWindows.get(var2));
		}

		this.openEditorWindows.add(var1);
		var2 = this.openEditorWindows.indexOf(var1);
		Iterator var3 = this.openEditorWindows.iterator();

		while(var3.hasNext()) {
			Anzeige var4 = (Anzeige)var3.next();
			var4.addWindowMenuEntry(var2, var1);
		}

		this.cpuDisplay.addWindowMenuEntry(var2, var1);
		this.speicheranzeige.addWindowMenuEntry(var2, var1);
	}

	void EditorAustragen(Anzeige var1) {
		int var2 = this.openEditorWindows.indexOf(var1);
		this.openEditorWindows.remove(var1);
		Iterator var3 = this.openEditorWindows.iterator();

		while(var3.hasNext()) {
			Anzeige var4 = (Anzeige)var3.next();
			var4.removeWindowMenuEntry(var2);
		}

		this.cpuDisplay.removeWindowMenuEntry(var2);
		this.speicheranzeige.removeWindowMenuEntry(var2);
	}

	void EditorTitelÄndern(Anzeige anzeige) {
		int index = this.openEditorWindows.indexOf(anzeige);

		for(Anzeige editorWindow : this.openEditorWindows) {
			editorWindow.editWindowMenuEntry(index, anzeige);
		}

		this.cpuDisplay.editWindowMenuEntry(index, anzeige);
		this.speicheranzeige.editWindowMenuEntry(index, anzeige);
	}

	void CpuFensterAuswählen() {
		this.cpuDisplay.showWindow();
	}

	void SpeicherFensterAuswählen() {
		this.speicheranzeige.showWindow();
	}

	void setCpuDisplayMode(boolean graphical, boolean extended) {
		if (graphical) {
			this.cpuDisplay = this.cpuGraphicalDisplay;
			this.cpuGraphicalDisplay.showWindow();
			this.cpuSimpleDisplay.hideWindow();
			this.cpuSimpleExtendedDisplay.hideWindow();

		} else {
			if (extended) {
				this.cpuDisplay = this.cpuSimpleExtendedDisplay;
				this.cpuSimpleExtendedDisplay.showWindow();
				this.cpuSimpleDisplay.hideWindow();
			} else {
				this.cpuDisplay = this.cpuSimpleDisplay;
				this.cpuSimpleDisplay.showWindow();
				this.cpuSimpleExtendedDisplay.hideWindow();
			}

			this.cpuGraphicalDisplay.hideWindow();
			((CpuDisplay)this.cpuDisplay).erweiterungenItem.setSelected(extended);
		}

	}

	void BeendenMitteilen() {
		for(Anzeige display : this.openEditorWindows) {
			display.notifyClose();
		}




	}
}
