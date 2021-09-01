//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import model.Cpu;

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
			for(int var10 = 0; var10 < args.length; ++var10) {
				controller.ÖffnenAusführen(args[var10]);
			}
		}

	}

	public static void main(String[] var0) {
		new Minimaschine(var0);
	}
}
