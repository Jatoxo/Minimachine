//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import res.R;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.JEditorPane;
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
import javax.swing.undo.UndoManager;

class Editor extends Anzeige {
	private static final int umrechnung = 25410;
	private static File lastFolder = null;
	private JEditorPane editor;
	private JScrollPane scroll;
	private JTextArea zeilenNummern;
	private JLabel status;
	private JMenuItem undoItem;
	private JMenuItem redoItem;

	//private JFileChooser fileChooser;
	private FileDialog fileDialog;

	private File file;
	private UndoManager undo;
	private boolean istAssembler = true;
	private String sicherungsstand = "";

	Editor(KontrolleurInterface controller) {
		super(controller);

		Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
		String path = prefs.get("lastFolder", null);
		if(path != null) {
			lastFolder = new File(prefs.get("lastFolder", null));
		}

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

	protected void initLayout() {
		this.undo = new UndoManager() {
			public void undoableEditHappened(UndoableEditEvent var1) {
				super.undoableEditHappened(var1);
				Editor.this.undoItem.setEnabled(this.canUndo());
				Editor.this.redoItem.setEnabled(this.canRedo());
			}
		};
		this.window = new JFrame(R.getResources().getString("window_editor_title"));
		this.window.setJMenuBar(this.menuBar);
		JPanel var1 = (JPanel)this.window.getContentPane();
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
		this.editor.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
				if(mouseWheelEvent.isControlDown()) {
					int scroll = mouseWheelEvent.getWheelRotation();
					setFontSize(Math.min(200, Math.max(10, editor.getFont().getSize() - 2 * scroll)));
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
		this.window.setSize(400, 200);
		this.window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent var1) {
				Editor.this.SchließenAusführen(false);
			}
		});
		this.window.setDefaultCloseOperation(2);

		//this.fileChooser = new JFileChooser();
		this.fileDialog = new FileDialog((Frame) null, R.getResources().getString("file_picker_open_title"));
		this.fileDialog.setMultipleMode(false);


		/*
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

		 */
	}

	private void SichernAusführen(boolean choosePath) {
		if (this.file == null || choosePath) {
			if (this.file != null) {
				fileDialog.setDirectory(this.file.getAbsolutePath());
				//this.fileChooser.setSelectedFile(this.file);
			} else if(lastFolder != null) {
				fileDialog.setDirectory(lastFolder.getAbsolutePath());
				//this.fileChooser.setCurrentDirectory(lastFolder);
			}

			fileDialog.setFile("");
			fileDialog.setMode(FileDialog.SAVE);

			fileDialog.setTitle(R.getResources().getString("file_picker_save_title"));
			fileDialog.setVisible(true);


			String newFile = fileDialog.getFile();

			if (newFile == null) {
				return;
			}

			if(!newFile.toLowerCase().endsWith(".mia") && !newFile.toLowerCase().endsWith(".mia")) {
				newFile += (".mia");
			}
			newFile = fileDialog.getDirectory() + newFile;
			this.file = new File(newFile);

			/*
			this.file = this.fileChooser.getSelectedFile();
			if (this.fileChooser.getFileFilter().getDescription().equals("Minimaschine Assembler")) {
				if (!this.file.getName().toLowerCase().endsWith(".mia")) {
					this.file = new File(this.file.getPath() + ".mia");
				}

				lastFolder = this.file;
			} else if (this.fileChooser.getFileFilter().getDescription().equals("Minimaschine Minisprache")) {
				if (!this.file.getName().toLowerCase().endsWith(".mis")) {
					this.file = new File(this.file.getPath() + ".mis");
				}

				lastFolder = this.file;
			}

			 */
		}

		try {
			FileWriter var4 = new FileWriter(this.file);
			this.editor.write(var4);
			var4.close();
			//displayStatusMessage(R.getResources().getString("editor_saved"));
			this.sicherungsstand = this.editor.getText();
			this.window.setTitle(this.file.getPath());
			this.controller.FensterTitelÄndernWeitergeben(this.self);
		} catch (Exception var3) {
			this.file = null;
		}

		controller.assemble(editor.getText(), this);

	}

	private void SchließenAusführen(boolean cancelButton) {
		if (!this.sicherungsstand.equals(this.editor.getText())) {
			int confirmClose = JOptionPane.showConfirmDialog(this.window, new String[]{R.getResources().getString("editor_confirm_exit_unsaved1"), R.getResources().getString("editor_confirm_exit_unsaved2")}, R.getResources().getString("editor_confirm_exit_unsaved_title"), cancelButton ? JOptionPane.YES_NO_CANCEL_OPTION : JOptionPane.YES_NO_OPTION);
			if (confirmClose == 0) {
				this.SichernAusführen(false);
			} else if (confirmClose != 1) {
				return;
			}
		}

		this.controller.SchließenAusführen(this.self);
		this.window.dispose();
	}

	void BeendenMitteilen() {
		if (!this.sicherungsstand.equals(this.editor.getText())) {
			int var1 = JOptionPane.showConfirmDialog(this.window, new String[]{R.getResources().getString("editor_confirm_exit_unsaved1"), R.getResources().getString("editor_confirm_exit_unsaved2")}, "Änderungen sichern", JOptionPane.YES_NO_OPTION);
			if (var1 == 0) {
				this.SichernAusführen(false);
			}
		}

	}

	protected void initMenus() {
		super.initMenus();
		this.closeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.SchließenAusführen(true);
			}
		});
		this.saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.SichernAusführen(false);
			}
		});
		this.saveAsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.SichernAusführen(true);
			}
		});
		this.printMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.DruckenAusführen();
			}
		});
		this.undoItem = new JMenuItem(R.getResources().getString("edit_menu_undo"), 90);
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
		this.editMenu.add(this.undoItem);
		this.redoItem = new JMenuItem(R.getResources().getString("edit_menu_redo"));
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
		this.editMenu.add(this.redoItem);
		this.editMenu.addSeparator();
		JMenuItem var1 = new JMenuItem(R.getResources().getString("edit_menu_cut"), 88);
		var1.setAccelerator(KeyStroke.getKeyStroke(88, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.editor.cut();
			}
		});
		this.editMenu.add(var1);
		var1 = new JMenuItem(R.getResources().getString("edit_menu_copy"), 67);
		var1.setAccelerator(KeyStroke.getKeyStroke(67, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.editor.copy();
			}
		});
		this.editMenu.add(var1);
		var1 = new JMenuItem(R.getResources().getString("edit_menu_paste"), 86);
		var1.setAccelerator(KeyStroke.getKeyStroke(86, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.editor.paste();
			}
		});
		this.editMenu.add(var1);
		var1 = new JMenuItem(R.getResources().getString("edit_menu_select_all"), 65);
		var1.setAccelerator(KeyStroke.getKeyStroke(65, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.editor.selectAll();
			}
		});
		this.editMenu.add(var1);
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

		this.toolsMenu.addSeparator();
		this.toolsMenu.add(var3);
		this.toolsMenu.addSeparator();
		var1 = new JMenuItem(R.getResources().getString("editor_assemble"));
		var1.setAccelerator(KeyStroke.getKeyStroke(65, kommando + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.status.setText("");
				Editor.this.controller.assemble(Editor.this.editor.getText(), (Editor)Editor.this.self);
			}
		});
		this.toolsMenu.add(var1);
		this.toolsMenu.addSeparator();
		var1 = new JMenuItem("Übersetzen");
		var1.setAccelerator(KeyStroke.getKeyStroke(85, kommando + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.status.setText("");
				Editor.this.controller.Übersetzen(Editor.this.editor.getText(), (Editor)Editor.this.self);
			}
		});
		this.toolsMenu.add(var1);
		var1 = new JMenuItem("Assemblertext zeigen");
		var1.setAccelerator(KeyStroke.getKeyStroke(90, kommando + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.status.setText("");
				Editor.this.controller.AssemblertextZeigen(Editor.this.editor.getText(), (Editor)Editor.this.self);
			}
		});
		this.toolsMenu.add(var1);


	}

	protected void resetDisplaySize(boolean var1) {
		if (var1) {
			this.setFontSize(24);
		} else {
			this.setFontSize(13);
		}

		this.editor.invalidate();
		this.editor.repaint();
	}

	private void setFontSize(int newSize) {
		Font var2 = this.editor.getFont();
		Font var3 = new Font(var2.getName(), var2.getStyle(), newSize);
		this.editor.setFont(var3);
		this.zeilenNummern.setFont(var3);
	}

	void DateiLesen() {
		//this.fileChooser.setCurrentDirectory(lastFolder);



		fileDialog.setMode(FileDialog.LOAD);
		fileDialog.setMultipleMode(false);
		fileDialog.setTitle(R.getResources().getString("file_picker_open_title"));
		if(lastFolder != null) {
			fileDialog.setDirectory(lastFolder.getAbsolutePath());
		}
		fileDialog.setFile("*.mis;*.mia");
		fileDialog.setVisible(true);
		String file = fileDialog.getFile();


		//int dialogChoice = this.fileChooser.showOpenDialog(this.window);

		if(file != null) {
			file = fileDialog.getDirectory() + file;
			System.out.println(file + " chosen.");
			this.file = new File(file);//this.fileChooser.getSelectedFile();

			try {
				FileReader fr = new FileReader(this.file);
				this.editor.read(fr, null);
				fr.close();

				String[] lines = this.editor.getText().split("\n");
				StringBuilder buildString = new StringBuilder();

				for(int i = 1; i <= lines.length; ++i) {
					buildString.append(i).append(" \n");
				}

				this.zeilenNummern.setText(buildString.toString());
				this.sicherungsstand = this.editor.getText();
				this.window.setTitle(this.file.getPath());
				lastFolder = this.file;

				Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
				prefs.put("lastFolder", lastFolder.getAbsolutePath());
				System.out.println(lastFolder.getAbsolutePath());


			} catch (Exception e) {
				this.file = null;
			}
		} else {
			this.file = null;
		}

		if (this.file != null) {
			this.window.setVisible(true);
			this.undoItem.setEnabled(false);
			this.redoItem.setEnabled(false);
			this.controller.FensterTitelÄndernWeitergeben(this.self);
		} else {
			this.controller.SchließenAusführen(this.self);
			this.window.dispose();
		}

	}

	void DateiLesen(String path) {
		this.file = new File(path);

		try {
			FileReader fr = new FileReader(this.file);
			this.editor.read(fr, (Object)null);
			fr.close();
			this.sicherungsstand = this.editor.getText();
			this.window.setTitle(this.file.getPath());
		} catch (Exception ex) {
			this.file = null;
		}

		this.window.setVisible(true);
		this.undoItem.setEnabled(false);
		this.redoItem.setEnabled(false);
		this.controller.FensterTitelÄndernWeitergeben(this.self);
	}

	void FehlerAnzeigen(String message, int position) {
		this.status.setText(message);
		this.editor.select(position - 2, position - 1);
	}
	void displayStatusMessage(String message) {
		this.status.setText(message);
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

		PrintJob printJob = this.window.getToolkit().getPrintJob(this.window, this.window.getTitle(), (Properties)null);
		if(printJob == null) {
			return;
		}
		Dimension var3 = printJob.getPageDimension();
		int var4 = printJob.getPageResolution();
		int var5 = 15000 * var4 / 25410;
		int var6 = 10000 * var4 / 25410;
		var3.width -= var5 * 2;
		var3.height -= var5 * 2;
		Font var11 = new Font("Monospaced", 0, 10);
		Font var12 = new Font("Monospaced", 0, 14);
		Graphics var2 = printJob.getGraphics();
		int var8 = var2.getFontMetrics(var11).getHeight();
		int var9 = (var3.height - var6 * 2) / var8;
		int var10 = (var13.length + var9 - 1) / var9;
		this.RahmenDrucken(var2, var3, var5, var6, 1, var10, var11, var12);

		for(var14 = 0; var14 < var13.length; ++var14) {
			var2.drawString(var13[var14], var5 + var6 * 5 / 10, var5 + 2 * var6 + var14 % var9 * var8);
			if ((var14 + 1) % var9 == 0) {
				var2.dispose();
				var2 = printJob.getGraphics();
				this.RahmenDrucken(var2, var3, var5, var6, (var14 + 1) / var9, var10, var11, var12);
			}
		}

		var2.dispose();
		printJob.end();
	}

	private void RahmenDrucken(Graphics var1, Dimension var2, int var3, int var4, int var5, int var6, Font var7, Font var8) {
		var1.drawRoundRect(var3, var3, var2.width, var2.height, var4 * 2, var4 * 2);
		var1.drawLine(var3, var3 + var4, var3 + var2.width, var3 + var4);
		var1.drawLine(var3, var3 + var2.height - var4, var3 + var2.width, var3 + var2.height - var4);
		String var9 = this.window.getTitle();
		var1.setFont(var8);
		var1.drawString(var9, var3 + var2.width / 2 - var1.getFontMetrics().stringWidth(var9) / 2, var3 + var4 * 7 / 10);
		var9 = "– " + var5 + " von " + var6 + " –";
		var1.setFont(var7);
		var1.drawString(var9, var3 + var2.width / 2 - var1.getFontMetrics().stringWidth(var9) / 2, var3 + var2.height - var4 * 4 / 10);
	}
}
