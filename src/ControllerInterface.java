//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

interface ControllerInterface {
	void CpuHexaSetzen(boolean var1);

	void CpuInvalidieren();

	boolean assemble(String assemblyText, Editor editor);

	boolean Übersetzen(String var1, Editor var2);

	boolean AssemblertextZeigen(String var1, Editor var2);

	void SpeicherLöschen();

	void Ausführen();

	void EinzelSchritt();

	void MikroSchritt();

	void ZurückSetzen();

	void NeuAusführen();

	void ÖffnenAusführen();

	void ÖffnenAusführen(String var1);

	void SchließenAusführen(Anzeige var1);

	void FensterTitelÄndernWeitergeben(Anzeige var1);

	void CpuFensterAuswählen();

	void SpeicherFensterAuswählen();

	void EinfacheDarstellungAnzeigen();

	void DetailDarstellungAnzeigen();

	void ErweiterungenEinschalten(boolean var1);

	void setTimeout(int var1);

	void BeendenAusführen();
}
