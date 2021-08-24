//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.PrintJob;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.undo.UndoManager;

class Editor extends Anzeige {
	private static final int umrechnung = 25410;
	private static File letzterOrdner = null;
	private JEditorPane editor;
	private JScrollPane scroll;
	private JTextArea zeilenNummern;
	private JLabel status;
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	private JFileChooser fileChooser;
	private File file;
	private UndoManager undo;
	private boolean istAssembler = true;
	private String sicherungsstand = "";

	Editor(KontrolleurInterface var1) {
		super(var1);
	}

	void ZeilenNummernSetzen(boolean var1) {
		String[] var2 = this.editor.getText().split("\n");
		String var3 = "";

		for(int var4 = 1; var4 <= var2.length; ++var4) {
			var3 = var3 + var4 + " \n";
		}

		if (var1) {
			var3 = var3 + (var2.length + 1) + " \n";
		}

		this.zeilenNummern.setText(var3);
	}

	protected void OberflächeAufbauen() {
		this.undo = new UndoManager() {
			public void undoableEditHappened(UndoableEditEvent var1) {
				super.undoableEditHappened(var1);
				Editor.this.undoItem.setEnabled(this.canUndo());
				Editor.this.redoItem.setEnabled(this.canRedo());
			}
		};
		this.fenster = new JFrame("Editor");
		this.fenster.setJMenuBar(this.menüZeile);
		JPanel var1 = (JPanel)this.fenster.getContentPane();
		var1.setLayout(new BorderLayout());
		this.editor = new JEditorPane("text/plain", (String)null) {
			public void cut() {
				super.cut();
				Editor.this.ZeilenNummernSetzen(false);
			}

			public void paste() {
				super.paste();
				Editor.this.ZeilenNummernSetzen(false);
			}
		};
		this.editor.getDocument().addUndoableEditListener(this.undo);
		this.zeilenNummern = new JTextArea("1 \n");
		this.zeilenNummern.setFont(this.editor.getFont());
		this.zeilenNummern.setBackground(new Color(255, 255, 200));
		this.zeilenNummern.setBorder(LineBorder.createGrayLineBorder());
		this.zeilenNummern.setEditable(false);
		this.editor.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent var1) {
				if (var1.getKeyChar() == '\b' || var1.getKeyChar() == 127 || var1.getKeyChar() == '\n') {
					Editor.this.ZeilenNummernSetzen(var1.getKeyChar() == '\n' && Editor.this.editor.getCaretPosition() >= Editor.this.editor.getText().length() - 1);
				}

			}
		});
		this.scroll = new JScrollPane(this.editor, 20, 30);
		this.scroll.setRowHeaderView(this.zeilenNummern);
		var1.add(this.scroll, "Center");
		this.status = new JLabel();
		this.status.setBorder(LineBorder.createGrayLineBorder());
		this.status.setBackground(Color.yellow);
		var1.add(this.status, "South");
		this.fenster.setSize(400, 200);
		this.fenster.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent var1) {
				Editor.this.SchließenAusführen(false);
			}
		});
		this.fenster.setDefaultCloseOperation(2);
		this.fileChooser = new JFileChooser();
		this.fileChooser.addChoosableFileFilter(new FileFilter() {
			public boolean accept(File var1) {
				String var2 = var1.getName();
				return var2.toLowerCase().endsWith(".mis") || var1.isDirectory();
			}

			public String getDescription() {
				return "Minimaschine Minisprache";
			}
		});
		this.fileChooser.addChoosableFileFilter(new FileFilter() {
			public boolean accept(File var1) {
				String var2 = var1.getName();
				return var2.toLowerCase().endsWith(".mia") || var1.isDirectory();
			}

			public String getDescription() {
				return "Minimaschine Assembler";
			}
		});
	}

	private void SichernAusführen(boolean var1) {
		if (this.file == null || var1) {
			if (this.file != null) {
				this.fileChooser.setSelectedFile(this.file);
			} else {
				this.fileChooser.setCurrentDirectory(letzterOrdner);
			}

			int var2 = this.fileChooser.showSaveDialog(this.fenster);
			if (var2 != 0) {
				return;
			}

			this.file = this.fileChooser.getSelectedFile();
			if (this.fileChooser.getFileFilter().getDescription().equals("Minimaschine Assembler")) {
				if (!this.file.getName().toLowerCase().endsWith(".mia")) {
					this.file = new File(this.file.getPath() + ".mia");
				}

				letzterOrdner = this.file;
			} else if (this.fileChooser.getFileFilter().getDescription().equals("Minimaschine Minisprache")) {
				if (!this.file.getName().toLowerCase().endsWith(".mis")) {
					this.file = new File(this.file.getPath() + ".mis");
				}

				letzterOrdner = this.file;
			}
		}

		try {
			FileWriter var4 = new FileWriter(this.file);
			this.editor.write(var4);
			var4.close();
			this.sicherungsstand = this.editor.getText();
			this.fenster.setTitle(this.file.getPath());
			this.kontrolleur.FensterTitelÄndernWeitergeben(this.selbst);
		} catch (Exception var3) {
			this.file = null;
		}

	}

	private void SchließenAusführen(boolean var1) {
		if (!this.sicherungsstand.equals(this.editor.getText())) {
			int var2 = JOptionPane.showConfirmDialog(this.fenster, new String[]{"Dieses Fenster enthält ungesicherte Änderungen.", "Sollen sie gesichert werden?"}, "Änderungen sichern", var1 ? 1 : 0);
			if (var2 == 0) {
				this.SichernAusführen(false);
			} else if (var2 != 1) {
				return;
			}
		}

		this.kontrolleur.SchließenAusführen(this.selbst);
		this.fenster.dispose();
	}

	void BeendenMitteilen() {
		if (!this.sicherungsstand.equals(this.editor.getText())) {
			int var1 = JOptionPane.showConfirmDialog(this.fenster, new String[]{"Dieses Fenster enthält ungesicherte Änderungen.", "Sollen sie gesichert werden?"}, "Änderungen sichern", 0);
			if (var1 == 0) {
				this.SichernAusführen(false);
			}
		}

	}

	protected void MenüsErzeugen() {
		super.MenüsErzeugen();
		this.schließenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.SchließenAusführen(true);
			}
		});
		this.sichernItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.SichernAusführen(false);
			}
		});
		this.sichernUnterItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.SichernAusführen(true);
			}
		});
		this.druckenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.DruckenAusführen();
			}
		});
		this.undoItem = new JMenuItem("Widerrufen", 90);
		this.undoItem.setAccelerator(KeyStroke.getKeyStroke(90, kommando));
		this.undoItem.setEnabled(false);
		this.undoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.undo.undo();
				Editor.this.undoItem.setEnabled(Editor.this.undo.canUndo());
				Editor.this.redoItem.setEnabled(Editor.this.undo.canRedo());
				Editor.this.ZeilenNummernSetzen(false);
			}
		});
		this.bearbeitenMenü.add(this.undoItem);
		this.redoItem = new JMenuItem("Wiederholen");
		this.redoItem.setAccelerator(KeyStroke.getKeyStroke(90, 64 + kommando));
		this.redoItem.setEnabled(false);
		this.redoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.undo.redo();
				Editor.this.undoItem.setEnabled(Editor.this.undo.canUndo());
				Editor.this.redoItem.setEnabled(Editor.this.undo.canRedo());
				Editor.this.ZeilenNummernSetzen(false);
			}
		});
		this.bearbeitenMenü.add(this.redoItem);
		this.bearbeitenMenü.addSeparator();
		JMenuItem var1 = new JMenuItem("Ausschneiden", 88);
		var1.setAccelerator(KeyStroke.getKeyStroke(88, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.editor.cut();
			}
		});
		this.bearbeitenMenü.add(var1);
		var1 = new JMenuItem("Kopieren", 67);
		var1.setAccelerator(KeyStroke.getKeyStroke(67, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.editor.copy();
			}
		});
		this.bearbeitenMenü.add(var1);
		var1 = new JMenuItem("Einfügen", 86);
		var1.setAccelerator(KeyStroke.getKeyStroke(86, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.editor.paste();
			}
		});
		this.bearbeitenMenü.add(var1);
		var1 = new JMenuItem("Alles auswählen", 65);
		var1.setAccelerator(KeyStroke.getKeyStroke(65, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.editor.selectAll();
			}
		});
		this.bearbeitenMenü.add(var1);
		String[] var2 = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		JMenu var3 = new JMenu("Fonts");
		ActionListener var4 = new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				String var2 = ((JMenuItem)var1.getSource()).getText();
				Font var3 = Editor.this.editor.getFont();
				Font var4 = new Font(var2, var3.getStyle(), var3.getSize());
				Editor.this.editor.setFont(var4);
				Editor.this.zeilenNummern.setFont(var4);
			}
		};
		String[] var5 = var2;
		int var6 = var2.length;

		for(int var7 = 0; var7 < var6; ++var7) {
			String var8 = var5[var7];
			JMenuItem var9 = new JMenuItem(var8);
			var9.addActionListener(var4);
			var3.add(var9);
		}

		this.werkzeugMenü.addSeparator();
		this.werkzeugMenü.add(var3);
		this.werkzeugMenü.addSeparator();
		var1 = new JMenuItem("Assemblieren");
		var1.setAccelerator(KeyStroke.getKeyStroke(65, kommando + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.status.setText("");
				Editor.this.kontrolleur.Assemblieren(Editor.this.editor.getText(), (Editor)Editor.this.selbst);
			}
		});
		this.werkzeugMenü.add(var1);
		this.werkzeugMenü.addSeparator();
		var1 = new JMenuItem("Übersetzen");
		var1.setAccelerator(KeyStroke.getKeyStroke(85, kommando + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.status.setText("");
				Editor.this.kontrolleur.Übersetzen(Editor.this.editor.getText(), (Editor)Editor.this.selbst);
			}
		});
		this.werkzeugMenü.add(var1);
		var1 = new JMenuItem("Assemblertext zeigen");
		var1.setAccelerator(KeyStroke.getKeyStroke(90, kommando + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.status.setText("");
				Editor.this.kontrolleur.AssemblertextZeigen(Editor.this.editor.getText(), (Editor)Editor.this.selbst);
			}
		});
		this.werkzeugMenü.add(var1);
	}

	protected void DarstellungsgrößeSetzen(boolean var1) {
		if (var1) {
			this.FontgrößeSetzen(24);
		} else {
			this.FontgrößeSetzen(13);
		}

		this.editor.invalidate();
		this.editor.repaint();
	}

	private void FontgrößeSetzen(int var1) {
		Font var2 = this.editor.getFont();
		Font var3 = new Font(var2.getName(), var2.getStyle(), var1);
		this.editor.setFont(var3);
		this.zeilenNummern.setFont(var3);
	}

	void DateiLesen() {
		this.fileChooser.setCurrentDirectory(letzterOrdner);
		int var1 = this.fileChooser.showOpenDialog(this.fenster);
		if (var1 == 0) {
			this.file = this.fileChooser.getSelectedFile();

			try {
				FileReader var2 = new FileReader(this.file);
				this.editor.read(var2, (Object)null);
				var2.close();
				String[] var3 = this.editor.getText().split("\n");
				String var4 = "";

				for(int var5 = 1; var5 <= var3.length; ++var5) {
					var4 = var4 + var5 + " \n";
				}

				this.zeilenNummern.setText(var4);
				this.sicherungsstand = this.editor.getText();
				this.fenster.setTitle(this.file.getPath());
				letzterOrdner = this.file;
			} catch (Exception var6) {
				this.file = null;
			}
		} else {
			this.file = null;
		}

		if (this.file != null) {
			this.fenster.setVisible(true);
			this.undoItem.setEnabled(false);
			this.redoItem.setEnabled(false);
			this.kontrolleur.FensterTitelÄndernWeitergeben(this.selbst);
		} else {
			this.kontrolleur.SchließenAusführen(this.selbst);
			this.fenster.dispose();
		}

	}

	void DateiLesen(String var1) {
		this.file = new File(var1);

		try {
			FileReader var2 = new FileReader(this.file);
			this.editor.read(var2, (Object)null);
			var2.close();
			this.sicherungsstand = this.editor.getText();
			this.fenster.setTitle(this.file.getPath());
		} catch (Exception var3) {
			this.file = null;
		}

		this.fenster.setVisible(true);
		this.undoItem.setEnabled(false);
		this.redoItem.setEnabled(false);
		this.kontrolleur.FensterTitelÄndernWeitergeben(this.selbst);
	}

	void FehlerAnzeigen(String var1, int var2) {
		this.status.setText(var1);
		this.editor.select(var2 - 2, var2 - 1);
	}

	private void DruckenAusführen() {
		String[] var13 = this.editor.getText().split("\n");

		int var14;
		for(var14 = 0; var14 < var13.length; ++var14) {
			while(true) {
				int var7 = var13[var14].indexOf(9);
				if (var7 < 0) {
					break;
				}

				var13[var14] = var13[var14].substring(0, var7) + "        ".substring(0, 8 - var7 % 8) + var13[var14].substring(var7 + 1);
			}
		}

		PrintJob var1 = this.fenster.getToolkit().getPrintJob(this.fenster, this.fenster.getTitle(), (Properties)null);
		Dimension var3 = var1.getPageDimension();
		int var4 = var1.getPageResolution();
		int var5 = 15000 * var4 / 25410;
		int var6 = 10000 * var4 / 25410;
		var3.width -= var5 * 2;
		var3.height -= var5 * 2;
		Font var11 = new Font("Monospaced", 0, 10);
		Font var12 = new Font("Monospaced", 0, 14);
		Graphics var2 = var1.getGraphics();
		int var8 = var2.getFontMetrics(var11).getHeight();
		int var9 = (var3.height - var6 * 2) / var8;
		int var10 = (var13.length + var9 - 1) / var9;
		this.RahmenDrucken(var2, var3, var5, var6, 1, var10, var11, var12);

		for(var14 = 0; var14 < var13.length; ++var14) {
			var2.drawString(var13[var14], var5 + var6 * 5 / 10, var5 + 2 * var6 + var14 % var9 * var8);
			if ((var14 + 1) % var9 == 0) {
				var2.dispose();
				var2 = var1.getGraphics();
				this.RahmenDrucken(var2, var3, var5, var6, (var14 + 1) / var9, var10, var11, var12);
			}
		}

		var2.dispose();
		var1.end();
	}

	private void RahmenDrucken(Graphics var1, Dimension var2, int var3, int var4, int var5, int var6, Font var7, Font var8) {
		var1.drawRoundRect(var3, var3, var2.width, var2.height, var4 * 2, var4 * 2);
		var1.drawLine(var3, var3 + var4, var3 + var2.width, var3 + var4);
		var1.drawLine(var3, var3 + var2.height - var4, var3 + var2.width, var3 + var2.height - var4);
		String var9 = this.fenster.getTitle();
		var1.setFont(var8);
		var1.drawString(var9, var3 + var2.width / 2 - var1.getFontMetrics().stringWidth(var9) / 2, var3 + var4 * 7 / 10);
		var9 = "– " + var5 + " von " + var6 + " –";
		var1.setFont(var7);
		var1.drawString(var9, var3 + var2.width / 2 - var1.getFontMetrics().stringWidth(var9) / 2, var3 + var2.height - var4 * 4 / 10);
	}
}
