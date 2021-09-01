//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import res.R;

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

	AssemblerAnzeige(ControllerInterface var1, String var2) {
		super(var1);
		this.text.setText(var2);
	}

	protected void initLayout() {
		this.window = new JFrame("Assemblertext");
		this.initMenus();
		this.window.setJMenuBar(this.menuBar);
		this.window.setVisible(true);
		JPanel var1 = (JPanel)this.window.getContentPane();
		var1.setLayout(new BorderLayout());
		this.text = new JTextArea();
		this.text.setEditable(false);
		this.scroll = new JScrollPane(this.text, 20, 30);
		var1.add(this.scroll, "Center");
		this.window.setSize(400, 200);
		this.window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent var1) {
				AssemblerAnzeige.this.controller.SchließenAusführen(AssemblerAnzeige.this.self);
			}
		});
		this.window.setDefaultCloseOperation(2);
	}

	protected void initMenus() {
		super.initMenus();
		this.closeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				AssemblerAnzeige.this.controller.SchließenAusführen(AssemblerAnzeige.this.self);
				AssemblerAnzeige.this.window.dispose();
			}
		});
		this.saveMenuItem.setEnabled(false);
		this.saveAsMenuItem.setEnabled(false);
		this.printMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
			}
		});
		JMenuItem var1 = new JMenuItem(R.getResources().getString("edit_menu_cut"), 88);
		var1.setAccelerator(KeyStroke.getKeyStroke(88, 256));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		var1 = new JMenuItem(R.getResources().getString("edit_menu_copy"), 67);
		var1.setAccelerator(KeyStroke.getKeyStroke(67, 256));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				AssemblerAnzeige.this.text.copy();
			}
		});
		this.editMenu.add(var1);
		var1 = new JMenuItem(R.getResources().getString("edit_menu_paste"), 86);
		var1.setAccelerator(KeyStroke.getKeyStroke(86, 256));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		var1 = new JMenuItem(R.getResources().getString("edit_menu_select_all"), 65);
		var1.setAccelerator(KeyStroke.getKeyStroke(65, 256));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				AssemblerAnzeige.this.text.selectAll();
			}
		});
		this.sizeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				if (AssemblerAnzeige.this.sizeMenuItem.isSelected()) {
					AssemblerAnzeige.this.FontgrößeSetzen(24);
				} else {
					AssemblerAnzeige.this.FontgrößeSetzen(13);
				}

				AssemblerAnzeige.this.text.invalidate();
				AssemblerAnzeige.this.text.repaint();
			}
		});
	}

	protected void resetDisplaySize(boolean var1) {
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
