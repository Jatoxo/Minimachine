package io.github.jatoxo;//


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;

class AssemblerAnzeige extends Anzeige {
	private AssemblerAnzeigePane assemblerAnzeigePane;


	AssemblerAnzeige(ControllerInterface controller, String code) {
		super(controller, R.string("window_assembly_title"));

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent var1) {
				AssemblerAnzeige.this.controller.SchließenAusführen(AssemblerAnzeige.this.self);
			}
		});

		assemblerAnzeigePane.setCode(code);

		setVisible(true);
		setSize(400, 200);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}

	public JPanel getContent() {
		if(assemblerAnzeigePane == null) {
			assemblerAnzeigePane = new AssemblerAnzeigePane(controller);
		}

		return assemblerAnzeigePane;
	}


	protected void initMenus() {
		super.initMenus();

		this.closeMenuItem.addActionListener(event -> {
			controller.SchließenAusführen(AssemblerAnzeige.this.self);
			dispose();
		});

		this.saveMenuItem.setEnabled(false);
		this.saveAsMenuItem.setEnabled(false);

		this.printMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				//TODO: Make this do something?
			}
		});

		JMenuItem menuItem = new JMenuItem(R.string("edit_menu_cut"), KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, cmdKey));
		menuItem.setEnabled(false);
		this.editMenu.add(menuItem);
		menuItem = new JMenuItem(R.string("edit_menu_copy"), KeyEvent.VK_C);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, cmdKey));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				assemblerAnzeigePane.text.copy();
			}
		});
		this.editMenu.add(menuItem);
		menuItem = new JMenuItem(R.string("edit_menu_paste"), KeyEvent.VK_V);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, cmdKey));
		menuItem.setEnabled(false);
		this.editMenu.add(menuItem);
		menuItem = new JMenuItem(R.string("edit_menu_select_all"), KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, cmdKey));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				assemblerAnzeigePane.text.selectAll();
			}
		});
		this.sizeMenuItem.addActionListener(event -> assemblerAnzeigePane.setIncreasedSize(sizeMenuItem.isSelected()));
	}

	protected void resetDisplaySize(boolean increasedSize) {
		assemblerAnzeigePane.setIncreasedSize(increasedSize);
	}


	public static class AssemblerAnzeigePane extends JPanel {
		private final ControllerInterface controller;

		private final JTextArea text;
		private final JScrollPane scroll;


		public AssemblerAnzeigePane(ControllerInterface controller) {
			super(new BorderLayout());

			this.controller = controller;

			this.text = new JTextArea();
			this.text.setEditable(false);

			this.scroll = new JScrollPane(this.text, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			add(this.scroll, "Center");
		}

		public void setCode(String code) {
			text.setText(code);
		}

		public void setIncreasedSize(boolean increased) {
			updateFontSize(increased ? 24 : 13);

			this.text.invalidate();
			this.text.repaint();
		}

		private void updateFontSize(int newSize) {
			Font font = this.text.getFont();
			this.text.setFont(new Font(font.getName(), font.getStyle(), newSize));
		}
	}
}
