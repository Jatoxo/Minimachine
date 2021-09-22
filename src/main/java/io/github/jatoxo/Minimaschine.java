package io.github.jatoxo;//


import io.github.jatoxo.model.Cpu;

class Minimaschine {
	private Minimaschine(String[] args) {
		Cpu cpuEinfach = Cpu.CpuErzeugen("einfach");
		Cpu cpuDetail = Cpu.CpuErzeugen("detail");

		Controller controller = new Controller(cpuEinfach, cpuDetail);

		CpuDisplay cpuDisplay = new CpuDisplay(controller);
		CpuGraphicalDisplay cpuAnzeigeDetail = new CpuGraphicalDisplay(controller);
		CpuExtendedDisplay cpuExtendedDisplay = new CpuExtendedDisplay(controller);
		SpeicherAnzeige speicherAnzeige = new SpeicherAnzeige(controller);

		WindowManager windowManagement = new WindowManager(cpuDisplay, cpuAnzeigeDetail, cpuExtendedDisplay, speicherAnzeige);
		controller.setWindowManager(windowManagement);

		cpuEinfach.Registrieren(cpuDisplay);
		cpuDetail.Registrieren(cpuAnzeigeDetail);

		cpuEinfach.Registrieren(cpuExtendedDisplay);
		cpuEinfach.addMemoryListener(speicherAnzeige);

		if (!Anzeige.IstMacOS()) {
			for(String arg : args) {
				controller.ÖffnenAusführen(arg);
			}
		}

	}

	public static void main(String[] var0) {
		new Minimaschine(var0);
	}
}
