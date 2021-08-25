//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import model.Cpu;

class Minimaschine {
	private Minimaschine(String[] args) {
		Cpu cpuEinfach = Cpu.CpuErzeugen("einfach");
		Cpu cpuDetail = Cpu.CpuErzeugen("detail");

		Kontrolleur kontrolleur = new Kontrolleur(cpuEinfach, cpuDetail);
		CpuAnzeige cpuAnzeige = new CpuAnzeige(kontrolleur);
		CpuAnzeigeDetail cpuAnzeigeDetail = new CpuAnzeigeDetail(kontrolleur);
		CpuAnzeigeErweitert cpuAnzeigeErweitert = new CpuAnzeigeErweitert(kontrolleur);
		SpeicherAnzeige speicherAnzeige = new SpeicherAnzeige(kontrolleur);

		FensterVerwaltung windowManagement = new FensterVerwaltung(cpuAnzeige, cpuAnzeigeDetail, cpuAnzeigeErweitert, speicherAnzeige);
		kontrolleur.VerwaltungSetzen(windowManagement);

		cpuEinfach.Registrieren(cpuAnzeige);
		cpuDetail.Registrieren(cpuAnzeigeDetail);

		cpuEinfach.Registrieren(cpuAnzeigeErweitert);
		cpuEinfach.SpeicherbeobachterSetzen(speicherAnzeige);

		if (!Anzeige.IstMacOS()) {
			for(int var10 = 0; var10 < args.length; ++var10) {
				kontrolleur.ÖffnenAusführen(args[var10]);
			}
		}

	}

	public static void main(String[] var0) {
		new Minimaschine(var0);
	}
}
