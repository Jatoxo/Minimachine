package io.github.jatoxo;

import io.github.jatoxo.model.Cpu;
import io.github.jatoxo.model.FehlerVerwaltung;
import io.github.jatoxo.model.SpeicherLesen;
import io.github.jatoxo.model.StatusMelder;
import io.github.jatoxo.model.minilanguage.ParserEinfach;
import io.github.jatoxo.model.minilanguage.ParserErweitert;

class Controller implements ControllerInterface {
	private Cpu cpu;
	private Cpu cpuSimple;
	private Cpu cpuDetail;
	private WindowManager windowManager;
	private boolean erweitert;

	Controller(Cpu cpuSimple, Cpu cpuDetail) {
		this.cpuSimple = cpuSimple;
		this.cpuDetail = cpuDetail;
		this.cpu = cpuDetail;
		this.windowManager = null;
		this.erweitert = false;
	}

	void setWindowManager(WindowManager manager) {
		this.windowManager = manager;

		manager.setCpuDisplayMode(true, false);
	}

	public void CpuHexaSetzen(boolean var1) {
		this.cpuSimple.HexaSetzen(var1);
		this.cpuDetail.HexaSetzen(var1);
	}

	public void CpuInvalidieren() {
		this.cpu.AnzeigeWiederholen();
	}

	public boolean assemble(String assemblyText, StatusMelder melder) {
		FehlerVerwaltung fehlerVerwaltung = new FehlerVerwaltung();

		this.cpu.assemble(assemblyText, fehlerVerwaltung);

		if (fehlerVerwaltung.FehlerAufgetreten()) {
			melder.displayError(fehlerVerwaltung.FehlertextMelden(), fehlerVerwaltung.FehlerpositionMelden());
		} else {
			melder.displayStatusMessage(R.string("editor_assembly_success"));
			this.cpu.ZurückSetzen();
		}

		return !fehlerVerwaltung.FehlerAufgetreten();
	}

	public boolean Übersetzen(String code, StatusMelder melder) {
		FehlerVerwaltung fehlerVerwaltung = new FehlerVerwaltung();
		this.cpu.Übersetzen(code, fehlerVerwaltung);
		if (fehlerVerwaltung.FehlerAufgetreten()) {
			melder.displayError(fehlerVerwaltung.FehlertextMelden(), fehlerVerwaltung.FehlerpositionMelden());
		} else {
			this.cpu.ZurückSetzen();
		}

		return !fehlerVerwaltung.FehlerAufgetreten();
	}

	public boolean AssemblertextZeigen(String source, StatusMelder melder) {
		FehlerVerwaltung fehlerVerwaltung = new FehlerVerwaltung();
		String var5;
		if (this.erweitert) {
			var5 = (new ParserErweitert(source, fehlerVerwaltung)).Parse();
		} else {
			var5 = (new ParserEinfach(source, fehlerVerwaltung)).Parse();
		}

		if (fehlerVerwaltung.FehlerAufgetreten()) {
			melder.displayError(fehlerVerwaltung.FehlertextMelden(), fehlerVerwaltung.FehlerpositionMelden());
		} else {
			AssemblerAnzeige var4 = new AssemblerAnzeige(this, var5);
			this.windowManager.EditorEintragen(var4);
		}

		return !fehlerVerwaltung.FehlerAufgetreten();
	}

	public void SpeicherLöschen() {
		SpeicherLesen.SpeicherLöschen();
	}

	public void Ausführen() {
		this.cpu.Ausführen();
	}

	public void EinzelSchritt() {
		this.cpu.Schritt();
	}

	public void MikroSchritt() {
		this.cpu.MikroSchritt();
	}

	public void ZurückSetzen() {
		this.cpu.ZurückSetzen();
	}

	public void NeuAusführen() {
		Editor editor = new Editor(this);
		this.windowManager.EditorEintragen(editor);
		editor.showWindow();
	}

	public void ÖffnenAusführen() {
		Editor editor = new Editor(this);
		this.windowManager.EditorEintragen(editor);
		editor.readFile();
	}

	public void ÖffnenAusführen(String path) {
		Editor editor = new Editor(this);
		this.windowManager.EditorEintragen(editor);
		editor.readFile(path);
		editor.showWindow();
	}

	public void SchließenAusführen(Anzeige var1) {
		this.windowManager.EditorAustragen(var1);
	}

	public void windowNameChanged(Anzeige anzeige) {
		this.windowManager.EditorTitelÄndern(anzeige);
	}

	public void CpuFensterAuswählen() {
		this.windowManager.CpuFensterAuswählen();
	}

	public void SpeicherFensterAuswählen() {
		this.windowManager.SpeicherFensterAuswählen();
	}

	public void EinfacheDarstellungAnzeigen() {
		this.cpu.Übertragen(this.cpuSimple);
		this.cpu = this.cpuSimple;
		this.windowManager.setCpuDisplayMode(false, this.erweitert);
	}

	public void DetailDarstellungAnzeigen() {
		this.cpu.Übertragen(this.cpuDetail);
		this.cpu = this.cpuDetail;
		this.windowManager.setCpuDisplayMode(true, this.erweitert);
	}

	public void ErweiterungenEinschalten(boolean var1) {
		this.erweitert = var1;
		this.cpu.ErweitertSetzen(var1);
		this.cpu.ZurückSetzen();
		this.windowManager.setCpuDisplayMode(this.cpu == this.cpuDetail, this.erweitert);
	}

	public void setTimeout(int var1) {
		this.cpu.setTimeout(var1);
	}

	public void BeendenAusführen() {
		this.windowManager.BeendenMitteilen();
		System.exit(0);
	}
}
