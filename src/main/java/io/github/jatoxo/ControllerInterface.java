package io.github.jatoxo;//


import io.github.jatoxo.model.StatusMelder;

interface ControllerInterface {
	void CpuHexaSetzen(boolean var1);

	void CpuInvalidieren();


	boolean assemble(String assemblyText, StatusMelder melder);

	boolean Übersetzen(String code, StatusMelder melder);

	boolean AssemblertextZeigen(String var1, StatusMelder melder);

	void SpeicherLöschen();

	void Ausführen();

	void EinzelSchritt();

	void MikroSchritt();

	void ZurückSetzen();

	void NeuAusführen();

	void ÖffnenAusführen();

	void ÖffnenAusführen(String var1);

	void SchließenAusführen(Anzeige var1);

	void windowNameChanged(Anzeige var1);

	void CpuFensterAuswählen();

	void SpeicherFensterAuswählen();

	void EinfacheDarstellungAnzeigen();

	void DetailDarstellungAnzeigen();

	void ErweiterungenEinschalten(boolean var1);

	void setTimeout(int var1);

	void BeendenAusführen();
}
