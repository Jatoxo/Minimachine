package io.github.jatoxo;//


import io.github.jatoxo.model.AssemblerBefehle;
import io.github.jatoxo.model.MemoryListener;
import io.github.jatoxo.model.SpeicherLesen;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

class SpeicherAnzeige extends Anzeige implements MemoryListener {
	private SpeicherAnzeigePane speicherAnzeigePane;

	MemoryTracker memoryTracker;

	private JCheckBoxMenuItem editItem;
	private JCheckBoxMenuItem hexaItem;
	private JCheckBoxMenuItem opcodeItem;


	SpeicherAnzeige(ControllerInterface controller) {
		super(controller, R.string("window_memory_title"));

		memoryTracker = new MemoryTracker();

		setSize(new Dimension(800, 500));
		setLocation(600, 50);
		setVisible(true);
	}

	public JPanel getContent() {
		if (speicherAnzeigePane == null) {
			speicherAnzeigePane = new SpeicherAnzeigePane(controller);
		}

		return speicherAnzeigePane;
	}

	protected void initMenus() {
		super.initMenus();

		closeMenuItem.setEnabled(false);
		saveMenuItem.setEnabled(false);
		saveAsMenuItem.setEnabled(false);
		printMenuItem.setEnabled(false);

		editMenu.setVisible(false);

		toolsMenu.addSeparator();

		hexaItem = new JCheckBoxMenuItem(R.string("memory_display_hex"));
		hexaItem.setEnabled(true);
		hexaItem.setSelected(false);
		hexaItem.addActionListener(event -> speicherAnzeigePane.setUseHex(hexaItem.isSelected()));
		toolsMenu.add(hexaItem);

		opcodeItem = new JCheckBoxMenuItem(R.string("memory_display_op"));
		opcodeItem.setEnabled(true);
		opcodeItem.setSelected(false);
		opcodeItem.addActionListener(event -> speicherAnzeigePane.setDisplayOpcodes(opcodeItem.isSelected()));

		toolsMenu.add(opcodeItem);
		toolsMenu.addSeparator();
		editItem = new JCheckBoxMenuItem(R.string("memory_edit"));
		editItem.setEnabled(true);
		editItem.setSelected(false);
		editItem.addActionListener(event -> speicherAnzeigePane.setEditable(editItem.isSelected()));
		toolsMenu.add(editItem);

		JMenuItem memoryTrackerItem = new JMenuItem(R.string("memory_track_window_title"));
		memoryTrackerItem.addActionListener(actionEvent -> memoryTracker.setVisible(true));
		windowsMenu.add(memoryTrackerItem);
	}

	protected void resetDisplaySize(boolean increasedSize) {
		speicherAnzeigePane.setIncreaseSize(increasedSize);
	}

	public void memoryChanged(int address) {
		memoryTracker.memoryChanged();

	}

	public static class SpeicherAnzeigePane extends JPanel implements MemoryListener {
		private final ControllerInterface controller;

		private TableModel dataModel;
		private JTable table;
		private JTable tableGross;
		private JScrollPane scrollpane;
		private JScrollPane scrollpaneGross;
		private int changed = -1;
		private boolean editierbar = false;
		private boolean hexaDarstellung = false;
		private boolean opcodeDarstellung = false;
		//MemoryTracker memoryTracker;

		public SpeicherAnzeigePane(ControllerInterface controller) {
			super(new BorderLayout());

			this.controller = controller;

			//memoryTracker = new MemoryTracker();

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
							if (opcodeDarstellung && col % 2 != 0) {
								int var8 = SpeicherLesen.WortOhneVorzeichenGeben(var3);
								int var6 = var8 / 256;
								String var7 = AssemblerBefehle.getAssemblyInstructions().getMnemonic(var8 % 256);
								if (var6 == 2) {
									var7 = var7 + "I";
								} else if (var6 == 3) {
									var7 = var7 + " @";
								}

								return var7;
							} else if (hexaDarstellung) {
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
					return editierbar && col != 0;
				}

				public void setValueAt(Object val, int row, int col) {
					if (val instanceof String) {
						try {
							String value = (String) val;
							int number;
							if (hexaDarstellung) {
								if (value.startsWith("0x")) {
									value = value.substring(2);
								}

								number = Integer.parseInt(value, 16);
							} else {
								number = Integer.parseInt(value);
							}

							if (-32768 <= number && number <= 65535) {
								changed = -1;
								SpeicherLesen.WortSetzen(row * 10 + (col - 1), number);
								changed = -1;
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

				}
			};
			this.table = new JTable(this.dataModel);

			int i;
			for(i = 0; i < 11; i++) {
				this.table.getColumnModel().getColumn(i).setPreferredWidth(50);
			}

			this.table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				private Font f = null;
				private Font fbold = null;

				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					if (this.f == null) {
						this.f = component.getFont();
						this.fbold = this.f.deriveFont(Font.BOLD);
					}

					if (column == 0) {
						component.setBackground(Color.gray);
						component.setFont(this.fbold);
					} else {
						component.setBackground(Color.lightGray);
						component.setFont(this.f);
						int index = row * 10 + (column - 1);
						if(index == changed) {
							component.setForeground(Color.red);
						} else {
							component.setForeground(Color.black);
						}
					}

					return component;
				}
			});

			this.table.getTableHeader().setReorderingAllowed(false);
			this.table.getTableHeader().setFont(this.table.getTableHeader().getFont().deriveFont(Font.BOLD));
			this.scrollpane = new JScrollPane(this.table);
			this.tableGross = new JTable(this.dataModel);
			this.table.getTableHeader().setReorderingAllowed(false);
			this.tableGross.setRowHeight(30);

			for(i = 0; i < 11; ++i) {
				this.tableGross.getColumnModel().getColumn(i).setPreferredWidth(100);
			}

			this.tableGross.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				private Font f = null;
				private Font fbold = null;

				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					if (this.f == null) {
						this.f = component.getFont();
						this.f = new Font(this.f.getName(), this.f.getStyle(), 24);
						this.fbold = this.f.deriveFont(Font.BOLD);
					}

					if (column == 0) {
						component.setBackground(Color.gray);
						component.setFont(this.fbold);
					} else {
						component.setBackground(Color.lightGray);
						component.setFont(this.f);
						if (row * 10 + (column - 1) == changed) {
							component.setForeground(Color.red);
						} else {
							component.setForeground(Color.black);
						}
					}

					return component;
				}
			});
			Font headerFont = this.tableGross.getTableHeader().getFont();
			this.tableGross.getTableHeader().setFont(new Font(headerFont.getName(), Font.BOLD, 24));
			this.scrollpaneGross = new JScrollPane(this.tableGross);
			add(this.scrollpane, "Center");

		}

		public void setIncreaseSize(boolean increased) {
			if (increased) {
				remove(scrollpane);
				add(scrollpaneGross, "Center");
				scrollpaneGross.invalidate();
				scrollpaneGross.repaint();
			} else {
				remove(scrollpaneGross);
				add(scrollpane, "Center");
				scrollpane.invalidate();
				scrollpane.repaint();
			}

			revalidate();
		}

		public void setUseHex(boolean hexEnabled) {
			hexaDarstellung = hexEnabled;
			layoutUpdated();
			controller.CpuHexaSetzen(hexEnabled);
			controller.CpuInvalidieren();
		}
		public void setDisplayOpcodes(boolean displayOpcodes) {
			opcodeDarstellung = displayOpcodes;
			layoutUpdated();

		}
		public void setEditable(boolean editable) {
			this.editierbar = editable;
		}

		private void layoutUpdated() {
			scrollpane.invalidate();
			scrollpane.repaint();
			scrollpaneGross.invalidate();
			scrollpaneGross.repaint();
		}

		@Override
		public void memoryChanged(int address) {
			this.changed = address;
			this.scrollpane.invalidate();
			this.scrollpane.repaint();
			this.scrollpaneGross.invalidate();
			this.scrollpaneGross.repaint();
		}
	}
}
