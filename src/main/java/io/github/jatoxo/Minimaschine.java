package io.github.jatoxo;//


import com.formdev.flatlaf.FlatLightLaf;
import io.github.jatoxo.model.Cpu;
import io.github.jatoxo.model.MemoryListener;

import javax.swing.*;
import java.awt.*;

class Minimaschine {
	private Minimaschine(String[] args) {

		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (Exception ex) {
			ex.printStackTrace();
		}


		Cpu cpuEinfach = Cpu.CpuErzeugen("einfach");
		Cpu cpuDetail = Cpu.CpuErzeugen("detail");

		Controller controller = new Controller(cpuEinfach, cpuDetail);

		CpuDisplay cpuDisplay = new CpuDisplay(controller);
		CpuGraphicalDisplay cpuAnzeigeDetail = new CpuGraphicalDisplay(controller);
		CpuExtendedDisplay cpuExtendedDisplay = new CpuExtendedDisplay(controller);
		SpeicherAnzeige speicherAnzeige = new SpeicherAnzeige(controller);
		UnifiedView unifiedView = new UnifiedView(controller, (CpuDisplay.CpuDisplayPane) cpuDisplay.getContent(), (CpuExtendedDisplay.CpuExtendedDisplayPane) cpuExtendedDisplay.getContent(), (CpuGraphicalDisplay.CpuGraphicalDisplayPane) cpuAnzeigeDetail.getContent(), (SpeicherAnzeige.SpeicherAnzeigePane) speicherAnzeige.getContent());

		WindowManager windowManagement = new WindowManager(cpuDisplay, cpuAnzeigeDetail, cpuExtendedDisplay, speicherAnzeige, unifiedView);
		controller.setWindowManager(windowManagement);

		cpuEinfach.Registrieren(cpuDisplay);
		cpuDetail.Registrieren(cpuAnzeigeDetail);

		cpuEinfach.Registrieren(cpuExtendedDisplay);
		cpuEinfach.addMemoryListener(speicherAnzeige);
		cpuEinfach.addMemoryListener((MemoryListener) speicherAnzeige.getContentPane());

		if (!Anzeige.IstMacOS()) {
			for(String arg : args) {
				controller.ÖffnenAusführen(arg);
			}
		}


	}

	public static void main(String[] args) {
		new Minimaschine(args);
	}
}
