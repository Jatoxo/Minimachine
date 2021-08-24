//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import model.CpuBeobachter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class CpuAnzeige2 extends Anzeige implements CpuBeobachter {
	private CpuBild bild;
	private CpuBildGroß bildgross;
	private JPanel content;
	private JCheckBoxMenuItem erweiterungenItem;

	CpuAnzeige2(KontrolleurInterface var1) {
		super(var1);
	}

	protected void OberflächeAufbauen() {
		this.fenster = new JFrame("CPU-Kontrolle");
		this.fenster.setJMenuBar(this.menüZeile);
		this.content = (JPanel)this.fenster.getContentPane();
		this.content.setLayout(new BorderLayout());
		this.bildgross = new CpuBildGroß();
		this.bildgross.setOpaque(false);
		this.bild = new CpuBild();
		this.bild.setOpaque(false);
		this.content.add(this.bild, "Center");
		JPanel var1 = new JPanel();
		var1.setLayout(new FlowLayout());
		this.content.add(var1, "South");
		JButton var2 = new JButton("Ausführen");
		var1.add(var2);
		var2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuAnzeige2.this.kontrolleur.Ausführen();
			}
		});
		var2 = new JButton("Einzelschritt");
		var1.add(var2);
		var2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuAnzeige2.this.kontrolleur.EinzelSchritt();
			}
		});
		var2 = new JButton("Mikroschritt");
		var1.add(var2);
		var2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuAnzeige2.this.kontrolleur.MikroSchritt();
			}
		});
		this.content.doLayout();
		this.fenster.setSize(600, 400);
		this.fenster.setVisible(true);
	}

	public void Befehlsmeldung(String var1, String var2, String var3, String var4, String var5, String var6, String var7, boolean var8, boolean var9, boolean var10, String var11, String var12, String var13, String[] var14, String[] var15, String[] var16, String[] var17, String[] var18, String[] var19, String var20) {
		this.bild.DatenSetzen(var1, var2, var3, var4, var5, var6, "Z:" + (var8 ? "*" : " ") + " N:" + (var9 ? "*" : " ") + " V:" + (var10 ? "*" : " "), var11, var12, var13, var7, var14, var15, var16, var17, var18, var19, this.erweiterungenItem.isSelected(), var20);
		this.bildgross.DatenSetzen(var1, var2, var3, var4, var5, var6, "Z:" + (var8 ? "*" : " ") + " N:" + (var9 ? "*" : " ") + " V:" + (var10 ? "*" : " "), var11, var12, var13, var7, var14, var15, var16, var17, var18, var19, this.erweiterungenItem.isSelected(), var20);
	}

	public void Fehlermeldung(String var1) {
		JOptionPane.showMessageDialog(this.fenster, var1, "CPU-Fehler", 0);
	}

	protected void MenüsErzeugen() {
		super.MenüsErzeugen();
		this.schließenItem.setEnabled(false);
		this.sichernItem.setEnabled(false);
		this.sichernUnterItem.setEnabled(false);
		this.druckenItem.setEnabled(false);
		JMenuItem var1 = new JMenuItem("Widerrufen", 90);
		var1.setAccelerator(KeyStroke.getKeyStroke(90, kommando));
		var1.setEnabled(false);
		this.bearbeitenMenü.add(var1);
		var1 = new JMenuItem("Wiederholen");
		var1.setAccelerator(KeyStroke.getKeyStroke(90, 64 + kommando));
		var1.setEnabled(false);
		this.bearbeitenMenü.add(var1);
		this.bearbeitenMenü.addSeparator();
		var1 = new JMenuItem("Ausschneiden", 88);
		var1.setAccelerator(KeyStroke.getKeyStroke(88, kommando));
		var1.setEnabled(false);
		this.bearbeitenMenü.add(var1);
		var1 = new JMenuItem("Kopieren", 67);
		var1.setAccelerator(KeyStroke.getKeyStroke(67, kommando));
		var1.setEnabled(false);
		this.bearbeitenMenü.add(var1);
		var1 = new JMenuItem("Einfügen", 86);
		var1.setAccelerator(KeyStroke.getKeyStroke(86, kommando));
		var1.setEnabled(false);
		this.bearbeitenMenü.add(var1);
		var1 = new JMenuItem("Alles auswählen", 65);
		var1.setAccelerator(KeyStroke.getKeyStroke(65, kommando));
		var1.setEnabled(false);
		this.bearbeitenMenü.add(var1);
		this.werkzeugMenü.addSeparator();
		var1 = new JMenuItem("Einfache Darstellung");
		var1.setAccelerator(KeyStroke.getKeyStroke(69, kommando + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuAnzeige2.this.kontrolleur.EinfacheDarstellungAnzeigen();
			}
		});
		this.werkzeugMenü.add(var1);
		var1 = new JMenuItem("Detaildarstellung");
		var1.setAccelerator(KeyStroke.getKeyStroke(68, kommando + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuAnzeige2.this.kontrolleur.DetailDarstellungAnzeigen();
			}
		});
		this.werkzeugMenü.add(var1);
		this.werkzeugMenü.addSeparator();
		var1 = new JMenuItem("Abbruchschranke setzen");
		var1.setAccelerator(KeyStroke.getKeyStroke(65, kommando + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Zeitschranke.Zeigen(CpuAnzeige2.this.kontrolleur);
			}
		});
		this.werkzeugMenü.add(var1);
		this.werkzeugMenü.addSeparator();
		var1 = new JMenuItem("CPU rücksetzen");
		var1.setAccelerator(KeyStroke.getKeyStroke(82, kommando + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuAnzeige2.this.kontrolleur.ZurückSetzen();
			}
		});
		this.werkzeugMenü.add(var1);
		this.werkzeugMenü.addSeparator();
		this.erweiterungenItem = new JCheckBoxMenuItem("Erweiterungen");
		this.erweiterungenItem.setEnabled(true);
		this.erweiterungenItem.setSelected(false);
		this.erweiterungenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuAnzeige2.this.kontrolleur.ErweiterungenEinschalten(CpuAnzeige2.this.erweiterungenItem.isSelected());
			}
		});
		this.werkzeugMenü.add(this.erweiterungenItem);
	}

	protected void DarstellungsgrößeSetzen(boolean var1) {
		if (var1) {
			this.content.remove(this.bild);
			this.content.add(this.bildgross, "Center");
			this.bildgross.invalidate();
			this.bildgross.repaint();
			this.fenster.setSize(900, 600);
		} else {
			this.content.remove(this.bildgross);
			this.content.add(this.bild, "Center");
			this.bild.invalidate();
			this.bild.repaint();
			this.fenster.setSize(600, 400);
		}

		this.content.doLayout();
		this.content.revalidate();
	}
}
