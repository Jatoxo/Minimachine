package io.github.jatoxo;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import io.github.jatoxo.model.AssemblerBefehle;
import io.github.jatoxo.model.MemoryListener;
import io.github.jatoxo.model.SpeicherLesen;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SpeicherAnzeige extends Anzeige implements MemoryListener {
	private JScrollPane scroll;
	private TableModel dataModel;
	private JTable table;
	private JTable tableGross;
	private JScrollPane scrollpane;
	private JScrollPane scrollpaneGross;
	private int geändert = -1;
	private boolean editierbar = false;
	private JCheckBoxMenuItem editItem;
	private JCheckBoxMenuItem hexaItem;
	private JCheckBoxMenuItem opcodeItem;
	private boolean hexaDarstellung = false;
	private boolean opcodeDarstellung = false;
	private JPanel content;
	MemoryTracker memoryTracker;

	SpeicherAnzeige(ControllerInterface controller) {
		super(controller);
	}

	protected void initLayout() {
		memoryTracker = new MemoryTracker();
		this.window = new JFrame(R.string("window_memory_title"));
		this.window.setJMenuBar(this.menuBar);
		this.window.setVisible(true);
		this.content = (JPanel)this.window.getContentPane();
		this.content.setLayout(new BorderLayout());
		this.dataModel = new AbstractTableModel() {
			public String getColumnName(int var1) {
				return var1 == 0 ? "" : "" + (var1 - 1);
			}

			public int getColumnCount() {
				return 11;
			}

			public int getRowCount() {
				return 6555;
			}

			public Object getValueAt(int row, int col) {
				if (col == 0) {
					return row * 10;
				} else {
					int var3 = row * 10 + (col - 1);
					if (var3 < 65536) {
						if (SpeicherAnzeige.this.opcodeDarstellung && col % 2 != 0) {
							int var8 = SpeicherLesen.WortOhneVorzeichenGeben(var3);
							int var6 = var8 / 256;
							String var7 = AssemblerBefehle.getAssemblyInstructions().getMnemonic(var8 % 256);
							if (var6 == 2) {
								var7 = var7 + "I";
							} else if (var6 == 3) {
								var7 = var7 + " @";
							}

							return var7;
						} else if (SpeicherAnzeige.this.hexaDarstellung) {
							String var4 = "0000" + Integer.toHexString(SpeicherLesen.WortOhneVorzeichenGeben(var3)).toUpperCase();
							return var4.substring(var4.length() - 4);
						} else {
							return SpeicherLesen.WortMitVorzeichenGeben(var3);
						}
					} else {
						return "";
					}
				}
			}

			public boolean isCellEditable(int row, int col) {
				return SpeicherAnzeige.this.editierbar && col != 0;
			}

			public void setValueAt(Object val, int row, int col) {
				if (val instanceof String) {
					try {
						String value = (String) val;
						int number;
						if (SpeicherAnzeige.this.hexaDarstellung) {
							if (value.startsWith("0x")) {
								value = value.substring(2);
							}

							number = Integer.parseInt(value, 16);
						} else {
							number = Integer.parseInt(value);
						}

						if (-32768 <= number && number <= 65535) {
							SpeicherAnzeige.this.geändert = -1;
							SpeicherLesen.WortSetzen(row * 10 + (col - 1), number);
							SpeicherAnzeige.this.geändert = -1;
						}
					} catch (Exception ex) {
					}
				}

			}
		};
		this.table = new JTable(this.dataModel);

		int var1;
		for(var1 = 0; var1 < 11; ++var1) {
			this.table.getColumnModel().getColumn(var1).setPreferredWidth(50);
		}

		this.table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private Font f = null;
			private Font fbold = null;

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (this.f == null) {
					this.f = component.getFont();
					this.fbold = this.f.deriveFont(1);
				}

				if (column == 0) {
					component.setBackground(Color.gray);
					component.setFont(this.fbold);
				} else {
					component.setBackground(Color.lightGray);
					component.setFont(this.f);
					int index = row * 10 + (column - 1);
					if(index == SpeicherAnzeige.this.geändert) {
						component.setForeground(Color.red);
					} else if(false) {

					}else {
						component.setForeground(Color.black);
					}
				}

				return component;
			}
		});

		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getTableHeader().setFont(this.table.getTableHeader().getFont().deriveFont(1));
		this.scrollpane = new JScrollPane(this.table);
		this.tableGross = new JTable(this.dataModel);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.tableGross.setRowHeight(30);

		for(var1 = 0; var1 < 11; ++var1) {
			this.tableGross.getColumnModel().getColumn(var1).setPreferredWidth(100);
		}

		this.tableGross.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private Font f = null;
			private Font fbold = null;

			public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
				Component var7 = super.getTableCellRendererComponent(var1, var2, var3, var4, var5, var6);
				if (this.f == null) {
					this.f = var7.getFont();
					this.f = new Font(this.f.getName(), this.f.getStyle(), 24);
					this.fbold = this.f.deriveFont(1);
				}

				if (var6 == 0) {
					var7.setBackground(Color.gray);
					var7.setFont(this.fbold);
				} else {
					var7.setBackground(Color.lightGray);
					var7.setFont(this.f);
					if (var5 * 10 + (var6 - 1) == SpeicherAnzeige.this.geändert) {
						var7.setForeground(Color.red);
					} else {
						var7.setForeground(Color.black);
					}
				}

				return var7;
			}
		});
		Font var2 = this.tableGross.getTableHeader().getFont();
		this.tableGross.getTableHeader().setFont(new Font(var2.getName(), 1, 24));
		this.scrollpaneGross = new JScrollPane(this.tableGross);
		this.content.add(this.scrollpane, "Center");
		this.window.setSize(new Dimension(800, 500));
		this.window.setLocation(600, 50);
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
		var1 = new JMenuItem(R.string("memory_clear_memory"), 65);
		var1.setEnabled(true);
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				SpeicherAnzeige.this.controller.SpeicherLöschen();
			}
		});
		this.toolsMenu.add(var1);
		this.toolsMenu.addSeparator();
		this.hexaItem = new JCheckBoxMenuItem(R.string("memory_display_hex"));
		this.hexaItem.setEnabled(true);
		this.hexaItem.setSelected(false);
		this.hexaItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				SpeicherAnzeige.this.hexaDarstellung = SpeicherAnzeige.this.hexaItem.isSelected();
				SpeicherAnzeige.this.scrollpane.invalidate();
				SpeicherAnzeige.this.scrollpane.repaint();
				SpeicherAnzeige.this.scrollpaneGross.invalidate();
				SpeicherAnzeige.this.scrollpaneGross.repaint();
				SpeicherAnzeige.this.controller.CpuHexaSetzen(SpeicherAnzeige.this.hexaDarstellung);
				SpeicherAnzeige.this.controller.CpuInvalidieren();
			}
		});
		this.opcodeItem = new JCheckBoxMenuItem(R.string("memory_display_op"));
		this.opcodeItem.setEnabled(true);
		this.opcodeItem.setSelected(false);
		this.opcodeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				SpeicherAnzeige.this.opcodeDarstellung = SpeicherAnzeige.this.opcodeItem.isSelected();
				SpeicherAnzeige.this.scrollpane.invalidate();
				SpeicherAnzeige.this.scrollpane.repaint();
				SpeicherAnzeige.this.scrollpaneGross.invalidate();
				SpeicherAnzeige.this.scrollpaneGross.repaint();
			}
		});
		this.toolsMenu.add(this.hexaItem);
		this.toolsMenu.add(this.opcodeItem);
		this.toolsMenu.addSeparator();
		this.editItem = new JCheckBoxMenuItem(R.string("memory_edit"));
		this.editItem.setEnabled(true);
		this.editItem.setSelected(false);
		this.editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				SpeicherAnzeige.this.editierbar = SpeicherAnzeige.this.editItem.isSelected();
			}
		});
		this.toolsMenu.add(this.editItem);

		JMenuItem memoryTrackerItem = new JMenuItem(R.string("memory_track_window_title"));
		memoryTrackerItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				memoryTracker.setVisible(true);
			}
		});
		this.windowsMenu.add(memoryTrackerItem);
	}

	protected void resetDisplaySize(boolean var1) {
		if (var1) {
			this.content.remove(this.scrollpane);
			this.content.add(this.scrollpaneGross, "Center");
			this.scrollpaneGross.invalidate();
			this.scrollpaneGross.repaint();
		} else {
			this.content.remove(this.scrollpaneGross);
			this.content.add(this.scrollpane, "Center");
			this.scrollpane.invalidate();
			this.scrollpane.repaint();
		}

		this.content.revalidate();
	}

	public void memoryChanged(int var1) {
		memoryTracker.memoryChanged();
		this.geändert = var1;
		this.scrollpane.invalidate();
		this.scrollpane.repaint();
		this.scrollpaneGross.invalidate();
		this.scrollpaneGross.repaint();
	}
}
