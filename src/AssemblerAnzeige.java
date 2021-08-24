//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

class AssemblerAnzeige extends Anzeige {
	private JTextArea text;
	private JScrollPane scroll;

	AssemblerAnzeige(KontrolleurInterface var1, String var2) {
		super(var1);
		this.text.setText(var2);
	}

	protected void OberflächeAufbauen() {
		this.fenster = new JFrame("Assemblertext");
		this.MenüsErzeugen();
		this.fenster.setJMenuBar(this.menüZeile);
		this.fenster.setVisible(true);
		JPanel var1 = (JPanel)this.fenster.getContentPane();
		var1.setLayout(new BorderLayout());
		this.text = new JTextArea();
		this.text.setEditable(false);
		this.scroll = new JScrollPane(this.text, 20, 30);
		var1.add(this.scroll, "Center");
		this.fenster.setSize(400, 200);
		this.fenster.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent var1) {
				AssemblerAnzeige.this.kontrolleur.SchließenAusführen(AssemblerAnzeige.this.selbst);
			}
		});
		this.fenster.setDefaultCloseOperation(2);
	}

	protected void MenüsErzeugen() {
		super.MenüsErzeugen();
		this.schließenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				AssemblerAnzeige.this.kontrolleur.SchließenAusführen(AssemblerAnzeige.this.selbst);
				AssemblerAnzeige.this.fenster.dispose();
			}
		});
		this.sichernItem.setEnabled(false);
		this.sichernUnterItem.setEnabled(false);
		this.druckenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
			}
		});
		JMenuItem var1 = new JMenuItem("Ausschneiden", 88);
		var1.setAccelerator(KeyStroke.getKeyStroke(88, 256));
		var1.setEnabled(false);
		this.bearbeitenMenü.add(var1);
		var1 = new JMenuItem("Kopieren", 67);
		var1.setAccelerator(KeyStroke.getKeyStroke(67, 256));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				AssemblerAnzeige.this.text.copy();
			}
		});
		this.bearbeitenMenü.add(var1);
		var1 = new JMenuItem("Einfügen", 86);
		var1.setAccelerator(KeyStroke.getKeyStroke(86, 256));
		var1.setEnabled(false);
		this.bearbeitenMenü.add(var1);
		var1 = new JMenuItem("Alles auswählen", 65);
		var1.setAccelerator(KeyStroke.getKeyStroke(65, 256));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				AssemblerAnzeige.this.text.selectAll();
			}
		});
		this.größeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				if (AssemblerAnzeige.this.größeItem.isSelected()) {
					AssemblerAnzeige.this.FontgrößeSetzen(24);
				} else {
					AssemblerAnzeige.this.FontgrößeSetzen(13);
				}

				AssemblerAnzeige.this.text.invalidate();
				AssemblerAnzeige.this.text.repaint();
			}
		});
	}

	protected void DarstellungsgrößeSetzen(boolean var1) {
		if (var1) {
			this.FontgrößeSetzen(24);
		} else {
			this.FontgrößeSetzen(13);
		}

		this.text.invalidate();
		this.text.repaint();
	}

	private void FontgrößeSetzen(int var1) {
		Font var2 = this.text.getFont();
		this.text.setFont(new Font(var2.getName(), var2.getStyle(), var1));
	}
}
