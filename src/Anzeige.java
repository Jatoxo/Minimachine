//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

/*
import com.apple.eawt.AboutHandler;
import com.apple.eawt.Application;
import com.apple.eawt.OpenFilesHandler;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.AppEvent.OpenFilesEvent;
import com.apple.eawt.AppEvent.QuitEvent;
 */
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

abstract class Anzeige {
	protected JFrame fenster;
	protected JMenuBar menüZeile;
	protected JMenu dateiMenü;
	protected JMenu bearbeitenMenü;
	protected JMenu werkzeugMenü;
	protected JMenu fensterMenü;
	protected JMenuItem schließenItem;
	protected JMenuItem sichernItem;
	protected JMenuItem sichernUnterItem;
	protected JMenuItem druckenItem;
	protected JCheckBoxMenuItem größeItem;
	protected KontrolleurInterface kontrolleur;
	protected Anzeige selbst;
	private static boolean erster = true;
	protected static boolean isMac;
	protected static int kommando;


	/*
	private void MacOSVorbereiten() {
		Application var1 = Application.getApplication();
		var1.setAboutHandler(new AboutHandler() {
			public void handleAbout(AboutEvent var1) {
				Über.Zeigen();
			}
		});
		var1.setQuitHandler(new QuitHandler() {
			public void handleQuitRequestWith(QuitEvent var1, QuitResponse var2) {
				Anzeige.this.kontrolleur.BeendenAusführen();
				var2.cancelQuit();
			}
		});
		var1.setOpenFileHandler(new OpenFilesHandler() {
			public void openFiles(OpenFilesEvent var1) {
				List var2 = var1.getFiles();
				Iterator var3 = var2.iterator();

				while(var3.hasNext()) {
					File var4 = (File)var3.next();
					Anzeige.this.kontrolleur.ÖffnenAusführen(var4.getPath());
				}

			}
		});
	}
	*/

	Anzeige(KontrolleurInterface var1) {
		this.kontrolleur = var1;
		this.selbst = this;
		if (erster) {
			erster = false;
			isMac = System.getProperty("os.name", "").startsWith("Mac");
			if (isMac) {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				//this.MacOSVorbereiten();
				kommando = 256;
			} else {
				kommando = 128;
			}

			try {
				UIManager.setLookAndFeel(new FlatLightLaf());
			} catch (Exception var3) {
			}
		}

		this.MenüsErzeugen();
		this.OberflächeAufbauen();
		this.fenster.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		if (isMac) {
		}

	}

	protected abstract void OberflächeAufbauen();

	protected void MenüsErzeugen() {

		this.menüZeile = new JMenuBar();
		this.dateiMenü = new JMenu("Ablage");
		this.menüZeile.add(this.dateiMenü);
		JMenuItem var1 = new JMenuItem("Neu", 78);
		var1.setAccelerator(KeyStroke.getKeyStroke(78, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.kontrolleur.NeuAusführen();
			}
		});
		this.dateiMenü.add(var1);
		var1 = new JMenuItem("Öffnen …", 79);
		var1.setAccelerator(KeyStroke.getKeyStroke(79, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.kontrolleur.ÖffnenAusführen();
			}
		});
		this.dateiMenü.add(var1);
		this.dateiMenü.addSeparator();
		this.schließenItem = new JMenuItem("Schließen", 87);
		this.schließenItem.setAccelerator(KeyStroke.getKeyStroke(87, kommando));
		this.dateiMenü.add(this.schließenItem);
		this.sichernItem = new JMenuItem("Sichern", 83);
		this.sichernItem.setAccelerator(KeyStroke.getKeyStroke(83, kommando));
		this.dateiMenü.add(this.sichernItem);
		this.sichernUnterItem = new JMenuItem("Sichern unter …");
		this.sichernUnterItem.setAccelerator(KeyStroke.getKeyStroke(83, kommando + 64));
		this.dateiMenü.add(this.sichernUnterItem);
		this.dateiMenü.addSeparator();
		this.druckenItem = new JMenuItem("Drucken …");
		this.druckenItem.setAccelerator(KeyStroke.getKeyStroke(80, kommando));
		this.dateiMenü.add(this.druckenItem);
		if (!isMac) {
			this.dateiMenü.addSeparator();
			var1 = new JMenuItem("Beenden", 81);
			var1.setAccelerator(KeyStroke.getKeyStroke(81, kommando));
			var1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent var1) {
					Anzeige.this.kontrolleur.BeendenAusführen();
				}
			});
			this.dateiMenü.add(var1);
		}

		this.bearbeitenMenü = new JMenu("Bearbeiten");
		this.menüZeile.add(this.bearbeitenMenü);
		this.werkzeugMenü = new JMenu("Werkzeuge");
		this.menüZeile.add(this.werkzeugMenü);
		this.größeItem = new JCheckBoxMenuItem("Große Darstellung");
		this.größeItem.setEnabled(true);
		this.größeItem.setSelected(false);
		this.größeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.DarstellungsgrößeSetzen(Anzeige.this.größeItem.isSelected());
			}
		});
		this.werkzeugMenü.add(this.größeItem);
		this.fensterMenü = new JMenu("Fenster");
		this.menüZeile.add(this.fensterMenü);
		if (!isMac) {
			var1 = new JMenuItem("Über Minimaschine");
			var1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent var1) {
					AboutDialog.Zeigen();
				}
			});
			this.fensterMenü.add(var1);
			this.fensterMenü.addSeparator();
		}

		var1 = new JMenuItem("CPU-Fenster");
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.kontrolleur.CpuFensterAuswählen();
			}
		});
		this.fensterMenü.add(var1);
		var1 = new JMenuItem("Speicher-Fenster");
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.kontrolleur.SpeicherFensterAuswählen();
			}
		});
		this.fensterMenü.add(var1);
		this.fensterMenü.addSeparator();
	}

	protected abstract void DarstellungsgrößeSetzen(boolean var1);

	private String TitelGeben() {
		return this.fenster.getTitle();
	}

	void FenstereintragHinzufügen(int var1, Anzeige var2) {
		JMenuItem var3 = new JMenuItem(var2.TitelGeben());
		var3.addActionListener(new Anzeige.FensterAktion(var2));
		this.fensterMenü.insert(var3, var1 + 3);
	}

	void FenstereintragEntfernen(int var1) {
		this.fensterMenü.remove(var1 + 3);
	}

	void FenstereintragÄndern(int var1, Anzeige var2) {
		this.fensterMenü.getItem(var1 + 3).setText(var2.TitelGeben());
	}

	void Aktivieren() {
		if (!this.fenster.isVisible()) {
			this.fenster.setVisible(true);
		}

		this.fenster.toFront();
	}

	void Ausblenden() {
		this.fenster.setVisible(false);
	}

	void BeendenMitteilen() {
	}

	static boolean IstMacOS() {
		return isMac;
	}

	class FensterAktion implements ActionListener {
		private Anzeige anzeige;

		FensterAktion(Anzeige var2) {
			this.anzeige = var2;
		}

		public void actionPerformed(ActionEvent var1) {
			this.anzeige.Aktivieren();
		}
	}
}
