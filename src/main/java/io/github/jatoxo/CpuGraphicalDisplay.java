package io.github.jatoxo;//


import io.github.jatoxo.model.CpuBeobachter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class CpuGraphicalDisplay extends Anzeige implements CpuBeobachter {
	private CpuGraphicalDisplayPane cpuGraphicalDisplayPane;

	private JCheckBoxMenuItem erweiterungenItem;

	CpuGraphicalDisplay(ControllerInterface controller) {
		super(controller, R.string("window_cpu_title"));

		cpuGraphicalDisplayPane = (CpuGraphicalDisplayPane) contentPane;

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				controller.BeendenAusführen();
			}
		});

		setSize(600, 400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public JPanel getContent() {
		if(contentPane == null) {
			contentPane = new CpuGraphicalDisplayPane(controller);
		}

		return contentPane;

	}

	public void Befehlsmeldung(String data, String address, String alu1, String alu2, String alu3, String accumulator, String stackPointer, boolean zFlag, boolean nFlag, boolean vFlag, String opMnemonic, String addr, String programCounter, String[] progAddr, String[] progMem, String[] dataAddr, String[] dataMem, String[] stackAddr, String[] stackMem, String microStepName) {
		cpuGraphicalDisplayPane.Befehlsmeldung(data, address, alu1, alu2, alu3, accumulator, stackPointer, zFlag, nFlag, vFlag, opMnemonic, addr, programCounter, progAddr, progMem, dataAddr, dataMem, stackAddr, stackMem, microStepName);
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
		menuItem.addActionListener(event -> controller.DetailDarstellungAnzeigen());
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
		erweiterungenItem.addActionListener(event -> {
			controller.ErweiterungenEinschalten(erweiterungenItem.isSelected());
			if(cpuGraphicalDisplayPane != null) {
				cpuGraphicalDisplayPane.setExtensionsEnabled(erweiterungenItem.isSelected());
			}
		});
		toolsMenu.add(erweiterungenItem);
	}

	protected void resetDisplaySize(boolean increasedSize) {
		cpuGraphicalDisplayPane.setIncreaseSize(increasedSize);
		setSize(increasedSize ? new Dimension(900, 600) : new Dimension(600, 400));
	}

	public static class CpuGraphicalDisplayPane extends JPanel implements CpuBeobachter {
		private ControllerInterface controller;

		private boolean extensions;

		private final CpuBild bild;
		private final CpuBildGroß bildgross;


		public CpuGraphicalDisplayPane(ControllerInterface controller) {
			super(new BorderLayout());

			this.controller = controller;
			extensions = false;


			bildgross = new CpuBildGroß();
			bildgross.setOpaque(false);

			bild = new CpuBild();
			bild.setOpaque(false);

			add(this.bild, "Center");

			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout());
			add(panel, "South");

			JButton button = new JButton(R.string("cpu_run"));
			button.addActionListener(event -> controller.Ausführen());
			panel.add(button);

			button = new JButton(R.string("cpu_step"));
			panel.add(button);
			button.addActionListener(event -> controller.EinzelSchritt());

			button = new JButton(R.string("cpu_micro_step"));
			panel.add(button);
			button.addActionListener(event -> controller.MikroSchritt());

			validate();

		}


		@Override
		public void Befehlsmeldung(String data, String address, String alu1, String alu2, String alu3, String accumulator, String stackPointer, boolean zFlag, boolean nFlag, boolean vFlag, String opMnemonic, String addr, String programCounter, String[] progAddr, String[] progMem, String[] dataAddr, String[] dataMem, String[] stackAddr, String[] stackMem, String microStepName) {
			this.bild.DatenSetzen(data, address, alu1, alu2, alu3, accumulator, "Z:" + (zFlag ? "*" : " ") + " N:" + (nFlag ? "*" : " ") + " V:" + (vFlag ? "*" : " "), opMnemonic, addr, programCounter, stackPointer, progAddr, progMem, dataAddr, dataMem, stackAddr, stackMem, extensions, microStepName);
			this.bildgross.DatenSetzen(data, address, alu1, alu2, alu3, accumulator, "Z:" + (zFlag ? "*" : " ") + " N:" + (nFlag ? "*" : " ") + " V:" + (vFlag ? "*" : " "), opMnemonic, addr, programCounter, stackPointer, progAddr, progMem, dataAddr, dataMem, stackAddr, stackMem, extensions, microStepName);
		}

		@Override
		public void Fehlermeldung(String message) {
			JOptionPane.showMessageDialog(this, message, R.string("cpu_cpu_error"), JOptionPane.ERROR_MESSAGE);
		}

		public void setIncreaseSize(boolean increased) {
			if (increased) {
				remove(bild);
				add(bildgross, "Center");
				bildgross.invalidate();
				bildgross.repaint();
				setSize(900, 600);
			} else {
				remove(bildgross);
				add(bild, "Center");
				bild.invalidate();
				bild.repaint();
				setSize(600, 400);
			}

			validate();
			revalidate();
		}


		public void setExtensionsEnabled(boolean enable) {
			extensions = enable;
		}
		public boolean isExtensionsEnabled() {
			return extensions;
		}
	}
}
