//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import model.AssemblerBefehle;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rsyntaxtextarea.parser.TaskTagParser;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import res.R;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

class Editor extends Anzeige {
	private static File lastFolder = null;
	private RSyntaxTextArea codeEditor;
	private RTextScrollPane codeScrollPane;
	private JLabel status;

	private FileDialog fileDialog;

	private File file;
	private String sicherungsstand = "";

	Editor(ControllerInterface controller) {
		super(controller);


		Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
		String path = prefs.get("lastFolder", null);
		if(path != null) {
			lastFolder = new File(prefs.get("lastFolder", null));
		}

	}


	protected void initLayout() {
		this.window = new JFrame(R.string("window_editor_title"));
		this.window.setJMenuBar(this.menuBar);
		JPanel contentPane = (JPanel) this.window.getContentPane();
		contentPane.setLayout(new BorderLayout());

		codeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
		codeEditor.setCodeFoldingEnabled(true);

		codeEditor.setCurrentLineHighlightColor(new Color(0, 0, 0, 15));

		AbstractTokenMakerFactory tokenMakerFactory = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
		tokenMakerFactory.putMapping("text/mimaAssembler", "model.TokenMaker");
		//FoldParserManager.get().addFoldParserMapping("text/mimaAssembler", new LinesWithContentFoldParser());
		codeEditor.setSyntaxEditingStyle("text/mimaAssembler");

		CompletionProvider provider = createCompletionProvider();
		AutoCompletion ac = new AutoCompletion(provider);

		ac.setAutoCompleteEnabled(true);
		ac.setAutoActivationEnabled(true);
		ac.setAutoCompleteSingleChoices(true);

		ac.setAutoActivationDelay(800);

		ac.install(codeEditor);

		codeEditor.addParser(new TaskTagParser());

		codeScrollPane = new RTextScrollPane(codeEditor);
		contentPane.add(codeScrollPane);

		ErrorStrip strip = new ErrorStrip(codeEditor);
		contentPane.add(strip, BorderLayout.LINE_END);


		codeEditor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent documentEvent) {

			}

			@Override
			public void removeUpdate(DocumentEvent documentEvent) {

			}

			@Override
			public void changedUpdate(DocumentEvent documentEvent) {
				codeEditor.removeAllLineHighlights();
			}
		});
		codeEditor.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
				if(mouseWheelEvent.isControlDown()) {
					int scroll = mouseWheelEvent.getWheelRotation();
					setFontSize(Math.min(200, Math.max(10, codeEditor.getFont().getSize() - 2 * scroll)));
				} else {
					codeScrollPane.dispatchEvent(mouseWheelEvent);
				}

			}
		});
		codeEditor.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent keyEvent) {}

			@Override
			public void keyPressed(KeyEvent keyEvent) {

				if(keyEvent.getKeyCode() == KeyEvent.VK_A && keyEvent.isAltDown() && keyEvent.isControlDown()) {
					Editor.this.status.setText("");
					codeEditor.removeAllLineHighlights();
					Editor.this.controller.assemble(codeEditor.getText(), (Editor) self);
				}
			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {}
		});




		this.status = new JLabel();
		this.status.setBorder(LineBorder.createGrayLineBorder());
		this.status.setBackground(Color.YELLOW);
		contentPane.add(this.status, "South");
		this.window.setSize(400, 200);
		this.window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent var1) {
				Editor.this.close(true);
			}
		});
		this.window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		//this.fileChooser = new JFileChooser();
		this.fileDialog = new FileDialog((Frame) null, R.string("file_picker_open_title"));
		this.fileDialog.setMultipleMode(false);

	}

	private CompletionProvider createCompletionProvider() {

		// A DefaultCompletionProvider is the simplest concrete implementation
		// of CompletionProvider. This provider has no understanding of
		// language semantics. It simply checks the text entered up to the
		// caret position for a match against known completions. This is all
		// that is needed in the majority of cases.
		DefaultCompletionProvider provider = new DefaultCompletionProvider();


		for(String instruction : AssemblerBefehle.getAssemblyInstructions().getMnemonics()) {

			provider.addCompletion(new BasicCompletion(provider, instruction));

		}
		provider.setAutoActivationRules(true, null);


		/*
		// Add a couple of "shorthand" completions. These completions don't
		// require the input text to be the same thing as the replacement text.
		provider.addCompletion(new ShorthandCompletion(provider, "sysout",
				"System.out.println(", "System.out.println("));
		 */
		return provider;

	}

	private void saveFile(boolean choosePath) {
		if (this.file == null || choosePath) {
			if (this.file != null) {
				fileDialog.setDirectory(this.file.getAbsolutePath());
			} else if(lastFolder != null) {
				fileDialog.setDirectory(lastFolder.getAbsolutePath());
			}

			fileDialog.setFile("");
			fileDialog.setMode(FileDialog.SAVE);

			fileDialog.setTitle(R.string("file_picker_save_title"));
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

		}

		try {
			String code = codeEditor.getText().replaceAll("\r", "");

			Files.write(Paths.get(this.file.getAbsolutePath()), code.getBytes());

			/*
			FileWriter fw = new FileWriter(this.file);
			this.codeEditor.write(fw);
			fw.close();
			 */

			displayStatusMessage(R.string("editor_saved"));
			this.sicherungsstand = this.codeEditor.getText();
			this.window.setTitle(this.file.getPath());
			this.controller.windowNameChanged(this.self);
		} catch (Exception var3) {
			this.file = null;
		}

		//controller.assemble(editor.getText(), this);

	}

	private void close(boolean cancelButton) {
		if (!this.sicherungsstand.equals(this.codeEditor.getText())) {
			int confirmClose = JOptionPane.showConfirmDialog(this.window, new String[]{R.string("editor_confirm_exit_unsaved1"), R.string("editor_confirm_exit_unsaved2")}, R.string("editor_confirm_exit_unsaved_title"), cancelButton ? JOptionPane.YES_NO_CANCEL_OPTION : JOptionPane.YES_NO_OPTION);
			if (confirmClose == 0) {
				this.saveFile(false);
			} else if (confirmClose != 1) {
				return;
			}
		}

		this.controller.SchließenAusführen(this.self);
		this.window.dispose();
	}

	void notifyClose() {
		if (!this.sicherungsstand.equals(this.codeEditor.getText())) {
			int dialog = JOptionPane.showConfirmDialog(this.window, new String[]{R.string("editor_confirm_exit_unsaved1"), R.string("editor_confirm_exit_unsaved2")}, R.string("editor_confirm_exit_unsaved_title"), JOptionPane.YES_NO_OPTION);
			if (dialog == 0) {
				this.saveFile(false);
			}
		}

	}

	protected void initMenus() {
		super.initMenus();
		this.closeMenuItem.addActionListener(event -> Editor.this.close(true));
		this.saveMenuItem.addActionListener(event -> Editor.this.saveFile(false));
		this.saveAsMenuItem.addActionListener(event -> Editor.this.saveFile(true));
		this.printMenuItem.addActionListener(event -> Editor.this.print());

		codeEditor = new RSyntaxTextArea(50, 50);

		editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.UNDO_ACTION)));
		editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.REDO_ACTION)));
		this.editMenu.addSeparator();
		editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.CUT_ACTION)));
		editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.COPY_ACTION)));
		editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.PASTE_ACTION)));
		editMenu.add(createMenuItem(RTextArea.getAction(RTextArea.SELECT_ALL_ACTION)));


		this.toolsMenu.addSeparator();

		JMenuItem menuEntry = new JMenuItem(R.string("editor_assemble"));
		menuEntry.setAccelerator(KeyStroke.getKeyStroke(65, cmdKey + 512));
		menuEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.status.setText("");
				Editor.this.controller.assemble(Editor.this.codeEditor.getText(), (Editor)Editor.this.self);
			}
		});
		this.toolsMenu.add(menuEntry);
		this.toolsMenu.addSeparator();

		menuEntry = new JMenuItem(R.string("edit_menu_translate"));
		menuEntry.setAccelerator(KeyStroke.getKeyStroke(85, cmdKey + 512));
		menuEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.status.setText("");
				Editor.this.controller.Übersetzen(codeEditor.getText(), (Editor) self);
			}
		});
		this.toolsMenu.add(menuEntry);

		menuEntry = new JMenuItem(R.string("edit_menu_show_ass"));
		menuEntry.setAccelerator(KeyStroke.getKeyStroke(90, cmdKey + 512));
		menuEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Editor.this.status.setText("");
				Editor.this.controller.AssemblertextZeigen(codeEditor.getText(), (Editor) self);
			}
		});
		this.toolsMenu.add(menuEntry);


	}

	private static JMenuItem createMenuItem(Action action) {
		JMenuItem item = new JMenuItem(action);
		item.setToolTipText(null); // Swing annoyingly adds tool tip text to the menu item
		return item;
	}

	protected void resetDisplaySize(boolean increasedSize) {
		if (increasedSize) {
			this.setFontSize(24);
		} else {
			this.setFontSize(13);
		}

		//this.editor.invalidate();
		//this.editor.repaint();
	}

	private void setFontSize(int newSize) {
		Font font = codeEditor.getFont();
		Font newFont = new Font(font.getName(), font.getStyle(), newSize);
		codeEditor.setFont(newFont);
		font = codeScrollPane.getGutter().getLineNumberFont();
		newFont = new Font(font.getName(), font.getStyle(), newSize);
		codeScrollPane.getGutter().setLineNumberFont(newFont);
	}

	void readFile() {
		//this.fileChooser.setCurrentDirectory(lastFolder);

		fileDialog.setMode(FileDialog.LOAD);
		fileDialog.setMultipleMode(false);
		fileDialog.setTitle(R.string("file_picker_open_title"));
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
			this.file = new File(file);
			try {
				//FileReader fr = new FileReader(this.file);
				//codeEditor.read(fr, null);

				//FileReader fr = new FileReader(this.file);
				String code = rf(this.file.getAbsolutePath(), Charset.defaultCharset());
				code = code.replaceAll("\r", "");
				//System.out.println(code);
				codeEditor.setText(code);
				//fr.close();


				this.sicherungsstand = codeEditor.getText();
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
			//this.undoItem.setEnabled(false);
			//this.redoItem.setEnabled(false);
			this.controller.windowNameChanged(this.self);
		} else {
			this.controller.SchließenAusführen(this.self);
			this.window.dispose();
		}

	}

	void readFile(String path) {
		this.file = new File(path);

		try {
			//FileReader fr = new FileReader(this.file);
			//codeEditor.read(fr, null);
			String code = rf(this.file.getAbsolutePath(), Charset.defaultCharset());
			code = code.replaceAll("\r", "");
			codeEditor.setText(code);
			//fr.close();
			this.sicherungsstand = codeEditor.getText();
			this.window.setTitle(this.file.getPath());
		} catch (Exception ex) {
			this.file = null;
		}

		this.window.setVisible(true);
		//this.undoItem.setEnabled(false);
		//this.redoItem.setEnabled(false);
		this.controller.windowNameChanged(this.self);
	}

	private static String rf(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	void FehlerAnzeigen(String message, int position) {
		try {
			int line = codeEditor.getLineOfOffset(position - 1);
			this.status.setText(R.string("error_line_x") + " " + (line + 1) + "; " + message);

			codeEditor.addLineHighlight(line, new Color(255, 0, 0, 40));

		} catch(BadLocationException e) {
			e.printStackTrace();
		}

		codeEditor.select(position - 2, position - 1);

	}
	void displayStatusMessage(String message) {
		this.status.setText(message);
	}

	private void print() {
		String[] lines = codeEditor.getText().split("\n");


		for(String line : lines) {
			while(true) {
				int tab = line.indexOf(9);
				if (tab < 0) {
					break;
				}

				line = line.substring(0, tab) + "        ".substring(0, 8 - tab % 8) + line.substring(tab + 1);
			}
		}

		PrintJob printJob = this.window.getToolkit().getPrintJob(this.window, this.window.getTitle(), (Properties)null);
		if(printJob == null) {
			return;
		}

		Dimension pageDimension = printJob.getPageDimension();
		int var4 = printJob.getPageResolution();
		int var5 = 15000 * var4 / 25410;
		int var6 = 10000 * var4 / 25410;
		pageDimension.width -= var5 * 2;
		pageDimension.height -= var5 * 2;
		Font var11 = new Font("Monospaced", 0, 10);
		Font var12 = new Font("Monospaced", 0, 14);
		Graphics var2 = printJob.getGraphics();
		int var8 = var2.getFontMetrics(var11).getHeight();
		int var9 = (pageDimension.height - var6 * 2) / var8;
		int var10 = (lines.length + var9 - 1) / var9;
		this.printBorder(var2, pageDimension, var5, var6, 1, var10, var11, var12);

		for(int i = 0; i < lines.length; i++) {
			var2.drawString(lines[i], var5 + var6 * 5 / 10, var5 + 2 * var6 + i % var9 * var8);
			if ((i + 1) % var9 == 0) {
				var2.dispose();
				var2 = printJob.getGraphics();
				this.printBorder(var2, pageDimension, var5, var6, (i + 1) / var9, var10, var11, var12);
			}
		}

		var2.dispose();
		printJob.end();
	}

	private void printBorder(Graphics g, Dimension dimension, int var3, int var4, int var5, int var6, Font font, Font font2) {
		g.drawRoundRect(var3, var3, dimension.width, dimension.height, var4 * 2, var4 * 2);
		g.drawLine(var3, var3 + var4, var3 + dimension.width, var3 + var4);
		g.drawLine(var3, var3 + dimension.height - var4, var3 + dimension.width, var3 + dimension.height - var4);
		String var9 = this.window.getTitle();
		g.setFont(font2);
		g.drawString(var9, var3 + dimension.width / 2 - g.getFontMetrics().stringWidth(var9) / 2, var3 + var4 * 7 / 10);
		var9 = "– " + var5 + " von " + var6 + " –";
		g.setFont(font);
		g.drawString(var9, var3 + dimension.width / 2 - g.getFontMetrics().stringWidth(var9) / 2, var3 + dimension.height - var4 * 4 / 10);
	}
}
