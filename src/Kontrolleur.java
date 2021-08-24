//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import model.Cpu;
import model.FehlerVerwaltung;
import model.SpeicherLesen;
import model.minilanguage.ParserEinfach;
import model.minilanguage.ParserErweitert;

class Kontrolleur implements KontrolleurInterface {
	private Cpu cpu;
	private Cpu cpuEinfach;
	private Cpu cpuDetail;
	private FensterVerwaltung verwaltung;
	private boolean erweitert;

	Kontrolleur(Cpu cpuSimple, Cpu cpuDetailed) {
		this.cpuEinfach = cpuSimple;
		this.cpuDetail = cpuDetailed;
		this.cpu = cpuDetailed;
		this.verwaltung = null;
		this.erweitert = false;
	}

	void VerwaltungSetzen(FensterVerwaltung var1) {
		this.verwaltung = var1;
		var1.CpuAnzeigeWählen(true, false);
	}

	public void CpuHexaSetzen(boolean var1) {
		this.cpuEinfach.HexaSetzen(var1);
		this.cpuDetail.HexaSetzen(var1);
	}

	public void CpuInvalidieren() {
		this.cpu.AnzeigeWiederholen();
	}

	public boolean Assemblieren(String var1, Editor var2) {
		FehlerVerwaltung var3 = new FehlerVerwaltung();
		this.cpu.Assemblieren(var1, var3);
		if (var3.FehlerAufgetreten()) {
			var2.FehlerAnzeigen(var3.FehlertextMelden(), var3.FehlerpositionMelden());
		} else {
			this.cpu.ZurückSetzen();
		}

		return !var3.FehlerAufgetreten();
	}

	public boolean Übersetzen(String var1, Editor var2) {
		FehlerVerwaltung var3 = new FehlerVerwaltung();
		this.cpu.Übersetzen(var1, var3);
		if (var3.FehlerAufgetreten()) {
			var2.FehlerAnzeigen(var3.FehlertextMelden(), var3.FehlerpositionMelden());
		} else {
			this.cpu.ZurückSetzen();
		}

		return !var3.FehlerAufgetreten();
	}

	public boolean AssemblertextZeigen(String var1, Editor var2) {
		FehlerVerwaltung var3 = new FehlerVerwaltung();
		String var5;
		if (this.erweitert) {
			var5 = (new ParserErweitert(var1, var3)).Parse();
		} else {
			var5 = (new ParserEinfach(var1, var3)).Parse();
		}

		if (var3.FehlerAufgetreten()) {
			var2.FehlerAnzeigen(var3.FehlertextMelden(), var3.FehlerpositionMelden());
		} else {
			AssemblerAnzeige var4 = new AssemblerAnzeige(this, var5);
			this.verwaltung.EditorEintragen(var4);
		}

		return !var3.FehlerAufgetreten();
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
		Editor var1 = new Editor(this);
		this.verwaltung.EditorEintragen(var1);
		var1.Aktivieren();
	}

	public void ÖffnenAusführen() {
		Editor var1 = new Editor(this);
		this.verwaltung.EditorEintragen(var1);
		var1.DateiLesen();
	}

	public void ÖffnenAusführen(String var1) {
		Editor var2 = new Editor(this);
		this.verwaltung.EditorEintragen(var2);
		var2.DateiLesen(var1);
	}

	public void SchließenAusführen(Anzeige var1) {
		this.verwaltung.EditorAustragen(var1);
	}

	public void FensterTitelÄndernWeitergeben(Anzeige var1) {
		this.verwaltung.EditorTitelÄndern(var1);
	}

	public void CpuFensterAuswählen() {
		this.verwaltung.CpuFensterAuswählen();
	}

	public void SpeicherFensterAuswählen() {
		this.verwaltung.SpeicherFensterAuswählen();
	}

	public void EinfacheDarstellungAnzeigen() {
		this.cpu.Übertragen(this.cpuEinfach);
		this.cpu = this.cpuEinfach;
		this.verwaltung.CpuAnzeigeWählen(false, this.erweitert);
	}

	public void DetailDarstellungAnzeigen() {
		this.cpu.Übertragen(this.cpuDetail);
		this.cpu = this.cpuDetail;
		this.verwaltung.CpuAnzeigeWählen(true, this.erweitert);
	}

	public void ErweiterungenEinschalten(boolean var1) {
		this.erweitert = var1;
		this.cpu.ErweitertSetzen(var1);
		this.cpu.ZurückSetzen();
		this.verwaltung.CpuAnzeigeWählen(this.cpu == this.cpuDetail, this.erweitert);
	}

	public void ZeitschrankeSetzen(int var1) {
		this.cpu.AbbruchSchrankeSetzen(var1);
	}

	public void BeendenAusführen() {
		this.verwaltung.BeendenMitteilen();
		System.exit(0);
	}
}
