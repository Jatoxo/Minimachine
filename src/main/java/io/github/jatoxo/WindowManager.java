package io.github.jatoxo;//


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

class WindowManager {
	private final ArrayList<Anzeige> openEditorWindows = new ArrayList<>();
	private Anzeige cpuDisplay;
	private Anzeige cpuSimpleDisplay;
	private Anzeige cpuGraphicalDisplay;
	private Anzeige cpuSimpleExtendedDisplay;
	private Anzeige speicheranzeige;
	private UnifiedView unifiedView;
	private boolean unified;

	WindowManager(Anzeige cpuDisplay, Anzeige cpuDisplay2, Anzeige cpuDisplayAdvanced, Anzeige storageDisplay, UnifiedView unifiedView) {
		this.cpuDisplay = cpuDisplay;
		this.cpuSimpleDisplay = cpuDisplay;
		this.cpuGraphicalDisplay = cpuDisplay2;
		this.cpuSimpleExtendedDisplay = cpuDisplayAdvanced;
		this.unifiedView = unifiedView;
		//cpuDisplay2.hide();
		cpuDisplayAdvanced.hideWindow();
		this.speicheranzeige = storageDisplay;
	}


	void setUnified(boolean unified) {
		if(unified) {
			cpuSimpleDisplay.dispose();
			cpuSimpleExtendedDisplay.dispose();
			cpuGraphicalDisplay.dispose();
			speicheranzeige.dispose();

			List<Editor.EditorPane> editorPanes = new ArrayList<>();
			for(Anzeige editor : openEditorWindows) {
				editorPanes.add((Editor.EditorPane) editor.contentPane);

				editor.dispose();
			}
			unifiedView.activate(cpuDisplay.getContent(), editorPanes);

		} else {
			unifiedView.dispose();

			cpuSimpleDisplay.setContentPane(cpuSimpleDisplay.contentPane);

			cpuSimpleExtendedDisplay.setContentPane(cpuSimpleExtendedDisplay.contentPane);

			cpuGraphicalDisplay.setContentPane(cpuGraphicalDisplay.contentPane);
			cpuGraphicalDisplay.pack();

			cpuDisplay.showWindow();

			speicheranzeige.setContentPane(speicheranzeige.contentPane);
			speicheranzeige.showWindow();


			for(Anzeige editor : openEditorWindows) {
				editor.setContentPane(editor.contentPane);
				editor.showWindow();
			}
		}

		this.unified = unified;
	}

	void EditorEintragen(Anzeige editor) {
		int index;
		for(index = 0; index < this.openEditorWindows.size(); ++index) {
			editor.addWindowMenuEntry(index, (Anzeige)this.openEditorWindows.get(index));
		}

		this.openEditorWindows.add(editor);
		index = this.openEditorWindows.indexOf(editor);

		for(Anzeige openEditors : this.openEditorWindows) {
			openEditors.addWindowMenuEntry(index, editor);
		}

		this.cpuDisplay.addWindowMenuEntry(index, editor);
		this.speicheranzeige.addWindowMenuEntry(index, editor);
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
