package main.java.io.github.jatoxo;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import main.java.io.github.jatoxo.model.CpuBeobachter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class CpuGraphicalDisplay extends Anzeige implements CpuBeobachter {
	private CpuBild bild;
	private CpuBildGroß bildgross;
	private JPanel content;
	private JCheckBoxMenuItem erweiterungenItem;

	CpuGraphicalDisplay(ControllerInterface var1) {
		super(var1);
	}

	protected void initLayout() {
		this.window = new JFrame(R.string("window_cpu_title"));
		this.window.setJMenuBar(this.menuBar);
		this.content = (JPanel)this.window.getContentPane();
		this.content.setLayout(new BorderLayout());
		this.bildgross = new CpuBildGroß();
		this.bildgross.setOpaque(false);
		this.bild = new CpuBild();
		this.bild.setOpaque(false);
		this.content.add(this.bild, "Center");
		JPanel var1 = new JPanel();
		var1.setLayout(new FlowLayout());
		this.content.add(var1, "South");
		JButton var2 = new JButton(R.string("cpu_run"));
		var1.add(var2);
		var2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuGraphicalDisplay.this.controller.Ausführen();
			}
		});
		var2 = new JButton(R.string("cpu_step"));
		var1.add(var2);
		var2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuGraphicalDisplay.this.controller.EinzelSchritt();
			}
		});
		var2 = new JButton(R.string("cpu_micro_step"));
		var1.add(var2);
		var2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuGraphicalDisplay.this.controller.MikroSchritt();
			}
		});
		this.content.doLayout();


		this.window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent var1) {
				controller.BeendenAusführen();
			}
		});
		this.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.window.setSize(600, 400);
		this.window.setLocationRelativeTo(null);
		this.window.setVisible(true);
	}

	public void Befehlsmeldung(String var1, String var2, String var3, String var4, String var5, String var6, String var7, boolean var8, boolean var9, boolean var10, String var11, String var12, String var13, String[] var14, String[] var15, String[] var16, String[] var17, String[] var18, String[] var19, String var20) {
		this.bild.DatenSetzen(var1, var2, var3, var4, var5, var6, "Z:" + (var8 ? "*" : " ") + " N:" + (var9 ? "*" : " ") + " V:" + (var10 ? "*" : " "), var11, var12, var13, var7, var14, var15, var16, var17, var18, var19, this.erweiterungenItem.isSelected(), var20);
		this.bildgross.DatenSetzen(var1, var2, var3, var4, var5, var6, "Z:" + (var8 ? "*" : " ") + " N:" + (var9 ? "*" : " ") + " V:" + (var10 ? "*" : " "), var11, var12, var13, var7, var14, var15, var16, var17, var18, var19, this.erweiterungenItem.isSelected(), var20);
	}

	public void Fehlermeldung(String var1) {
		JOptionPane.showMessageDialog(this.window, var1, "CPU-Fehler", 0);
	}

	protected void initMenus() {
		super.initMenus();
		this.closeMenuItem.setEnabled(false);
		this.saveMenuItem.setEnabled(false);
		this.saveAsMenuItem.setEnabled(false);
		this.printMenuItem.setEnabled(false);
		JMenuItem var1 = new JMenuItem(R.string("edit_menu_undo"), 90);
		var1.setAccelerator(KeyStroke.getKeyStroke(90, cmdKey));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		var1 = new JMenuItem(R.string("edit_menu_redo"));
		var1.setAccelerator(KeyStroke.getKeyStroke(90, 64 + cmdKey));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		this.editMenu.addSeparator();
		var1 = new JMenuItem(R.string("edit_menu_cut"), 88);
		var1.setAccelerator(KeyStroke.getKeyStroke(88, cmdKey));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		var1 = new JMenuItem(R.string("edit_menu_copy"), 67);
		var1.setAccelerator(KeyStroke.getKeyStroke(67, cmdKey));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		var1 = new JMenuItem(R.string("edit_menu_paste"), 86);
		var1.setAccelerator(KeyStroke.getKeyStroke(86, cmdKey));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		var1 = new JMenuItem(R.string("edit_menu_select_all"), 65);
		var1.setAccelerator(KeyStroke.getKeyStroke(65, cmdKey));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		this.toolsMenu.addSeparator();
		var1 = new JMenuItem(R.string("tools_menu_simple_view"));
		var1.setAccelerator(KeyStroke.getKeyStroke(69, cmdKey + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuGraphicalDisplay.this.controller.EinfacheDarstellungAnzeigen();
			}
		});
		this.toolsMenu.add(var1);
		var1 = new JMenuItem(R.string("tools_menu_graphical_view"));
		var1.setAccelerator(KeyStroke.getKeyStroke(68, cmdKey + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuGraphicalDisplay.this.controller.DetailDarstellungAnzeigen();
			}
		});
		this.toolsMenu.add(var1);
		this.toolsMenu.addSeparator();
		var1 = new JMenuItem(R.string("tools_menu_set_timeout"));
		var1.setAccelerator(KeyStroke.getKeyStroke(65, cmdKey + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				SetTimeoutDialog.show(CpuGraphicalDisplay.this.controller);
			}
		});
		this.toolsMenu.add(var1);
		this.toolsMenu.addSeparator();
		var1 = new JMenuItem(R.string("tools_menu_reset_cpu"));
		var1.setAccelerator(KeyStroke.getKeyStroke(82, cmdKey + 512));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuGraphicalDisplay.this.controller.ZurückSetzen();
			}
		});
		this.toolsMenu.add(var1);
		this.toolsMenu.addSeparator();
		this.erweiterungenItem = new JCheckBoxMenuItem(R.string("tools_menu_extended"));
		this.erweiterungenItem.setEnabled(true);
		this.erweiterungenItem.setSelected(false);
		this.erweiterungenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				CpuGraphicalDisplay.this.controller.ErweiterungenEinschalten(CpuGraphicalDisplay.this.erweiterungenItem.isSelected());
			}
		});
		this.toolsMenu.add(this.erweiterungenItem);
	}

	protected void resetDisplaySize(boolean var1) {
		if (var1) {
			this.content.remove(this.bild);
			this.content.add(this.bildgross, "Center");
			this.bildgross.invalidate();
			this.bildgross.repaint();
			this.window.setSize(900, 600);
		} else {
			this.content.remove(this.bildgross);
			this.content.add(this.bild, "Center");
			this.bild.invalidate();
			this.bild.repaint();
			this.window.setSize(600, 400);
		}

		this.content.doLayout();
		this.content.revalidate();
	}
}
