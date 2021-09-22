package io.github.jatoxo;//


import io.github.jatoxo.model.CpuBeobachter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

class CpuDisplay extends Anzeige implements CpuBeobachter {
	private final CpuDisplayPane cpuDisplayPane;

	JCheckBoxMenuItem erweiterungenItem;

	CpuDisplay(ControllerInterface controller) {
		super(controller, R.string("window_cpu_title"));
		cpuDisplayPane = (CpuDisplayPane) contentPane;

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				controller.BeendenAusführen();
			}
		});

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 250);
		setLocationRelativeTo(null);
		//this.window.setVisible(true);
		setResizable(false);
	}

	public JPanel getContent() {
		if(contentPane == null) {
			contentPane = new CpuDisplayPane(controller);
		}

		return contentPane;
	}

	protected void setIncreaseSize(boolean increased) {
		cpuDisplayPane.setIncreaseSize(increased);
		setSize(increased ? new Dimension(900, 375) : new Dimension(600, 250));
	}

	public void Befehlsmeldung(String data, String address, String alu1, String alu2, String alu3, String accumulator, String stackPointer, boolean zFlag, boolean nFlag, boolean vFlag, String opMnemonic, String addr, String programCounter, String[] progAddr, String[] progMem, String[] dataAddr, String[] dataMem, String[] stackAddr, String[] stackMem, String microStepName) {
		cpuDisplayPane.Befehlsmeldung(data, address, alu1, alu2, alu3, accumulator, stackPointer, zFlag, nFlag, vFlag, opMnemonic, addr, programCounter, progAddr, progMem, dataAddr, dataMem, stackAddr, stackMem, microStepName);
	}

	public void Fehlermeldung(String message) {
		JOptionPane.showMessageDialog(this, message, R.string("cpu_cpu_error"), JOptionPane.ERROR_MESSAGE);
	}

	protected void initMenus() {
		super.initMenus();

		closeMenuItem.setEnabled(false);
		saveMenuItem.setEnabled(false);
		saveAsMenuItem.setEnabled(false);
		printMenuItem.setEnabled(false);

		JMenuItem menuItem = new JMenuItem(R.string("edit_menu_undo"), KeyEvent.VK_Z);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, cmdKey));
		menuItem.setEnabled(false);
		editMenu.add(menuItem);

		menuItem = new JMenuItem(R.string("edit_menu_redo"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.SHIFT_DOWN_MASK + cmdKey));
		menuItem.setEnabled(false);
		editMenu.add(menuItem);

		editMenu.addSeparator();

		menuItem = new JMenuItem(R.string("edit_menu_cut"), KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, cmdKey));
		menuItem.setEnabled(false);
		editMenu.add(menuItem);

		menuItem = new JMenuItem(R.string("edit_menu_copy"), KeyEvent.VK_C);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, cmdKey));
		menuItem.setEnabled(false);
		editMenu.add(menuItem);

		menuItem = new JMenuItem(R.string("edit_menu_paste"), KeyEvent.VK_V);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, cmdKey));
		menuItem.setEnabled(false);
		editMenu.add(menuItem);

		menuItem = new JMenuItem(R.string("edit_menu_select_all"), KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, cmdKey));
		menuItem.setEnabled(false);
		editMenu.add(menuItem);

		toolsMenu.addSeparator();

		menuItem = new JMenuItem(R.string("tools_menu_simple_view"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, cmdKey + InputEvent.ALT_DOWN_MASK));
		menuItem.addActionListener(event -> controller.EinfacheDarstellungAnzeigen());
		toolsMenu.add(menuItem);

		menuItem = new JMenuItem(R.string("tools_menu_graphical_view"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, cmdKey + InputEvent.ALT_DOWN_MASK));
		menuItem.addActionListener(var1 -> controller.DetailDarstellungAnzeigen());
		toolsMenu.add(menuItem);

		toolsMenu.addSeparator();

		menuItem = new JMenuItem(R.string("tools_menu_set_timeout"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, cmdKey + InputEvent.ALT_DOWN_MASK));
		menuItem.addActionListener(event -> SetTimeoutDialog.show(controller));
		toolsMenu.add(menuItem);

		toolsMenu.addSeparator();

		menuItem = new JMenuItem(R.string("tools_menu_reset_cpu"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(82, cmdKey + InputEvent.ALT_DOWN_MASK));
		menuItem.addActionListener(event -> controller.ZurückSetzen());
		toolsMenu.add(menuItem);

		toolsMenu.addSeparator();

		erweiterungenItem = new JCheckBoxMenuItem(R.string("tools_menu_extended"));
		erweiterungenItem.setEnabled(true);
		erweiterungenItem.setSelected(false);
		erweiterungenItem.addActionListener(event -> controller.ErweiterungenEinschalten(erweiterungenItem.isSelected()));
		toolsMenu.add(erweiterungenItem);
	}

	protected void resetDisplaySize(boolean increasedSize) {
		this.setIncreaseSize(increasedSize);
	}

	public static class CpuDisplayPane extends JPanel implements CpuBeobachter {
		private ControllerInterface controller;

		private final JLabel labelA;
		private final JLabel labelPC;
		private final JLabel labelIR1;
		private final JLabel labelIR2;
		private final JLabel labelEq;
		private final JLabel labelLt;
		private final JLabel labelOv;
		private final JLabel labelAdr1;
		private final JLabel labelAdr2;
		private final JLabel labelAdr3;
		private final JLabel labelMem1;
		private final JLabel labelMem2;
		private final JLabel labelMem3;
		protected JPanel registerpanel;
		protected JPanel flagpanel;
		protected JPanel speicherpanel;
		protected Font defFont;
		protected Font bigFont;
		private final JLabel pcLabel;
		private final JLabel irLabel;
		private final JLabel acLabel;
		private final JLabel zLabel;
		private final JLabel nLabel;
		private final JLabel vLabel;
		protected JButton ausfuehrenButton;
		protected JButton einzelButton;

		public CpuDisplayPane(ControllerInterface controller) {
			super(null);

			this.controller = controller;

			defFont = getFont();
			bigFont = new Font(this.defFont.getName(), this.defFont.getStyle(), 24);

			registerpanel = new JPanel();
			registerpanel.setLayout(null);
			registerpanel.setBorder(new TitledBorder(LineBorder.createGrayLineBorder(), "Register"));
			registerpanel.setSize(200, 110);
			registerpanel.setLocation(10, 20);
			add(registerpanel);

			pcLabel = new JLabel();
			pcLabel.setText("PC:");
			pcLabel.setSize(40, 20);
			pcLabel.setLocation(20, 20);
			registerpanel.add(pcLabel);

			labelPC = new JLabel();
			labelPC.setText("");
			labelPC.setSize(60, 20);
			labelPC.setLocation(60, 20);
			labelPC.setBorder(LineBorder.createGrayLineBorder());
			registerpanel.add(labelPC);

			irLabel = new JLabel();
			irLabel.setText("IR:");
			irLabel.setSize(40, 20);
			irLabel.setLocation(20, 50);
			registerpanel.add(irLabel);

			labelIR1 = new JLabel();
			labelIR1.setText("");
			labelIR1.setSize(60, 20);
			labelIR1.setLocation(60, 50);
			labelIR1.setBorder(LineBorder.createGrayLineBorder());
			registerpanel.add(labelIR1);

			labelIR2 = new JLabel();
			labelIR2.setText("");
			labelIR2.setSize(60, 20);
			labelIR2.setLocation(119, 50);
			labelIR2.setBorder(LineBorder.createGrayLineBorder());
			registerpanel.add(labelIR2);

			acLabel = new JLabel();
			acLabel.setText("AC:");
			acLabel.setSize(40, 20);
			acLabel.setLocation(20, 80);
			registerpanel.add(acLabel);

			labelA = new JLabel();
			labelA.setText("");
			labelA.setSize(60, 20);
			labelA.setLocation(60, 80);
			labelA.setBorder(LineBorder.createGrayLineBorder());
			registerpanel.add(labelA);

			flagpanel = new JPanel();
			flagpanel.setLayout(null);
			flagpanel.setBorder(new TitledBorder(LineBorder.createGrayLineBorder(), "Flags"));
			flagpanel.setSize(140, 110);
			flagpanel.setLocation(220, 20);
			add(flagpanel);

			zLabel = new JLabel();
			zLabel.setText("Z:");
			zLabel.setSize(40, 20);
			zLabel.setLocation(20, 20);
			flagpanel.add(zLabel);

			labelEq = new JLabel();
			labelEq.setText("");
			labelEq.setHorizontalAlignment(0);
			labelEq.setSize(20, 20);
			labelEq.setLocation(60, 20);
			labelEq.setBorder(LineBorder.createGrayLineBorder());
			flagpanel.add(labelEq);

			nLabel = new JLabel();
			nLabel.setText("N:");
			nLabel.setSize(40, 20);
			nLabel.setLocation(20, 50);
			flagpanel.add(nLabel);

			labelLt = new JLabel();
			labelLt.setText("");
			labelLt.setHorizontalAlignment(0);
			labelLt.setSize(20, 20);
			labelLt.setLocation(60, 50);
			labelLt.setBorder(LineBorder.createGrayLineBorder());
			flagpanel.add(labelLt);

			vLabel = new JLabel();
			vLabel.setText("V:");
			vLabel.setSize(40, 20);
			vLabel.setLocation(20, 80);
			flagpanel.add(vLabel);

			labelOv = new JLabel();
			labelOv.setText("");
			labelOv.setHorizontalAlignment(0);
			labelOv.setSize(20, 20);
			labelOv.setLocation(60, 80);
			labelOv.setBorder(LineBorder.createGrayLineBorder());
			flagpanel.add(labelOv);

			speicherpanel = new JPanel();
			speicherpanel.setLayout(null);
			speicherpanel.setBorder(new TitledBorder(LineBorder.createGrayLineBorder(), R.string("cpu_memory_excerpt")));
			speicherpanel.setSize(200, 110);
			speicherpanel.setLocation(370, 20);
			add(this.speicherpanel);

			labelAdr1 = new JLabel();
			labelAdr1.setText("0");
			labelAdr1.setSize(80, 20);
			labelAdr1.setLocation(20, 20);
			speicherpanel.add(labelAdr1);

			labelMem1 = new JLabel();
			labelMem1.setText("");
			labelMem1.setSize(80, 21);
			labelMem1.setLocation(100, 20);
			labelMem1.setBorder(LineBorder.createGrayLineBorder());
			speicherpanel.add(labelMem1);

			labelAdr2 = new JLabel();
			labelAdr2.setText("1");
			labelAdr2.setSize(80, 20);
			labelAdr2.setLocation(20, 40);
			speicherpanel.add(labelAdr2);

			labelMem2 = new JLabel();
			labelMem2.setText("");
			labelMem2.setSize(80, 20);
			labelMem2.setLocation(100, 40);
			labelMem2.setBorder(LineBorder.createGrayLineBorder());
			speicherpanel.add(labelMem2);

			labelAdr3 = new JLabel();
			labelAdr3.setText("100");
			labelAdr3.setSize(80, 20);
			labelAdr3.setLocation(20, 70);
			speicherpanel.add(labelAdr3);

			labelMem3 = new JLabel();
			labelMem3.setText("");
			labelMem3.setSize(80, 20);
			labelMem3.setLocation(100, 70);
			labelMem3.setBorder(LineBorder.createGrayLineBorder());
			speicherpanel.add(labelMem3);

			ausfuehrenButton = new JButton("Ausführen");
			ausfuehrenButton.setSize(150, 30);
			ausfuehrenButton.setLocation(100, 150);
			add(ausfuehrenButton);
			ausfuehrenButton.addActionListener(event -> controller.Ausführen());

			einzelButton = new JButton("Einzelschritt");
			einzelButton.setSize(150, 30);
			einzelButton.setLocation(350, 150);
			add(einzelButton);
			einzelButton.addActionListener(event -> controller.EinzelSchritt());
		}

		@Override
		public void Befehlsmeldung(String data, String address, String alu1, String alu2, String alu3, String accumulator, String stackPointer, boolean zFlag, boolean nFlag, boolean vFlag, String opMnemonic, String addr, String programCounter, String[] progAddr, String[] progMem, String[] dataAddr, String[] dataMem, String[] stackAddr, String[] stackMem, String microStepName) {
			labelA.setText(accumulator);
			labelPC.setText(programCounter);
			labelIR1.setText(opMnemonic);
			labelIR2.setText(addr);
			labelEq.setText(zFlag ? "*" : " ");
			labelLt.setText(nFlag ? "*" : " ");
			labelOv.setText(vFlag ? "*" : " ");
			labelAdr1.setText(progAddr[0]);
			labelAdr2.setText(progAddr[1]);
			labelAdr3.setText(dataAddr[0]);
			labelMem1.setText(progMem[0]);
			labelMem2.setText(progMem[1]);
			labelMem3.setText(dataMem[0]);
		}

		@Override
		public void Fehlermeldung(String message) {
			JOptionPane.showMessageDialog(this, message, "CPU-Fehler", JOptionPane.ERROR_MESSAGE);
		}

		protected void setIncreaseSize(boolean increased) {

			if (increased) {

				((TitledBorder) registerpanel.getBorder()).setTitleFont(bigFont);
				registerpanel.setSize(300, 165);
				registerpanel.setLocation(15, 30);
				pcLabel.setFont(bigFont);
				pcLabel.setSize(60, 30);
				pcLabel.setLocation(30, 30);
				labelPC.setFont(bigFont);
				labelPC.setSize(90, 30);
				labelPC.setLocation(90, 30);
				irLabel.setFont(bigFont);
				irLabel.setSize(60, 30);
				irLabel.setLocation(30, 75);
				labelIR1.setFont(bigFont);
				labelIR1.setSize(90, 30);
				labelIR1.setLocation(90, 75);
				labelIR2.setFont(bigFont);
				labelIR2.setSize(90, 30);
				labelIR2.setLocation(179, 75);
				acLabel.setFont(bigFont);
				acLabel.setSize(60, 30);
				acLabel.setLocation(30, 120);
				labelA.setFont(bigFont);
				labelA.setSize(90, 30);
				labelA.setLocation(90, 120);
				((TitledBorder)flagpanel.getBorder()).setTitleFont(bigFont);
				flagpanel.setSize(210, 165);
				flagpanel.setLocation(330, 30);
				zLabel.setFont(bigFont);
				zLabel.setSize(60, 30);
				zLabel.setLocation(30, 30);
				labelEq.setFont(bigFont);
				labelEq.setSize(30, 30);
				labelEq.setLocation(90, 30);
				nLabel.setFont(bigFont);
				nLabel.setSize(60, 30);
				nLabel.setLocation(30, 75);
				labelLt.setFont(bigFont);
				labelLt.setSize(30, 30);
				labelLt.setLocation(90, 75);
				vLabel.setFont(bigFont);
				vLabel.setSize(60, 30);
				vLabel.setLocation(30, 120);
				labelOv.setFont(bigFont);
				labelOv.setSize(30, 30);
				labelOv.setLocation(90, 120);
				((TitledBorder)speicherpanel.getBorder()).setTitleFont(bigFont);
				speicherpanel.setSize(300, 165);
				speicherpanel.setLocation(555, 30);
				labelAdr1.setFont(bigFont);
				labelAdr1.setSize(120, 30);
				labelAdr1.setLocation(30, 30);
				labelMem1.setFont(bigFont);
				labelMem1.setSize(120, 31);
				labelMem1.setLocation(150, 30);
				labelAdr2.setFont(bigFont);
				labelAdr2.setSize(120, 30);
				labelAdr2.setLocation(30, 60);
				labelMem2.setFont(bigFont);
				labelMem2.setSize(120, 30);
				labelMem2.setLocation(150, 60);
				labelAdr3.setFont(bigFont);
				labelAdr3.setSize(120, 30);
				labelAdr3.setLocation(30, 105);
				labelMem3.setFont(bigFont);
				labelMem3.setSize(120, 30);
				labelMem3.setLocation(150, 105);
				ausfuehrenButton.setLocation(150, 225);
				einzelButton.setLocation(525, 225);

				//window.setSize(900, 375);
			} else {
				((TitledBorder)registerpanel.getBorder()).setTitleFont(defFont);
				registerpanel.setSize(200, 110);
				registerpanel.setLocation(10, 20);
				pcLabel.setFont(defFont);
				pcLabel.setSize(40, 20);
				pcLabel.setLocation(20, 20);
				labelPC.setFont(defFont);
				labelPC.setSize(60, 20);
				labelPC.setLocation(60, 20);
				irLabel.setFont(defFont);
				irLabel.setSize(40, 20);
				irLabel.setLocation(20, 50);
				labelIR1.setFont(defFont);
				labelIR1.setSize(60, 20);
				labelIR1.setLocation(60, 50);
				labelIR2.setFont(defFont);
				labelIR2.setSize(60, 20);
				labelIR2.setLocation(119, 50);
				acLabel.setFont(defFont);
				acLabel.setSize(40, 20);
				acLabel.setLocation(20, 80);
				labelA.setFont(defFont);
				labelA.setSize(60, 20);
				labelA.setLocation(60, 80);
				((TitledBorder)flagpanel.getBorder()).setTitleFont(defFont);
				flagpanel.setSize(140, 110);
				flagpanel.setLocation(220, 20);
				zLabel.setFont(defFont);
				zLabel.setSize(40, 20);
				zLabel.setLocation(20, 20);
				labelEq.setFont(defFont);
				labelEq.setSize(20, 20);
				labelEq.setLocation(60, 20);
				nLabel.setFont(defFont);
				nLabel.setSize(40, 20);
				nLabel.setLocation(20, 50);
				labelLt.setFont(defFont);
				labelLt.setSize(20, 20);
				labelLt.setLocation(60, 50);
				vLabel.setFont(defFont);
				vLabel.setSize(40, 20);
				vLabel.setLocation(20, 80);
				labelOv.setFont(defFont);
				labelOv.setSize(20, 20);
				labelOv.setLocation(60, 80);
				((TitledBorder)speicherpanel.getBorder()).setTitleFont(defFont);
				speicherpanel.setSize(200, 110);
				speicherpanel.setLocation(370, 20);
				labelAdr1.setFont(defFont);
				labelAdr1.setSize(80, 20);
				labelAdr1.setLocation(20, 20);
				labelMem1.setFont(defFont);
				labelMem1.setSize(80, 21);
				labelMem1.setLocation(100, 20);
				labelAdr2.setFont(defFont);
				labelAdr2.setSize(80, 20);
				labelAdr2.setLocation(20, 40);
				labelMem2.setFont(defFont);
				labelMem2.setSize(80, 20);
				labelMem2.setLocation(100, 40);
				labelAdr3.setFont(defFont);
				labelAdr3.setSize(80, 20);
				labelAdr3.setLocation(20, 70);
				labelMem3.setFont(defFont);
				labelMem3.setSize(80, 20);
				labelMem3.setLocation(100, 70);
				ausfuehrenButton.setLocation(100, 150);
				einzelButton.setLocation(350, 150);

				//window.setSize(600, 250);
			}

		}
	}
}
