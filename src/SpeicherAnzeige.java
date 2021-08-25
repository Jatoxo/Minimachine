//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import model.AssemblerBefehle;
import model.SpeicherBeobachter;
import model.SpeicherLesen;
import res.R;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

class SpeicherAnzeige extends Anzeige implements SpeicherBeobachter {
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

	SpeicherAnzeige(KontrolleurInterface var1) {
		super(var1);
	}

	protected void initLayout() {
		this.window = new JFrame(R.getResources().getString("window_memory_title"));
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

			public Object getValueAt(int var1, int var2) {
				if (var2 == 0) {
					return new Integer(var1 * 10);
				} else {
					int var3 = var1 * 10 + (var2 - 1);
					if (var3 < 65536) {
						if (SpeicherAnzeige.this.opcodeDarstellung && var2 % 2 != 0) {
							int var8 = SpeicherLesen.WortOhneVorzeichenGeben(var3);
							int var6 = var8 / 256;
							String var7 = AssemblerBefehle.AssemblerbefehleGeben().MnemonicGeben(var8 % 256);
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
							return new Integer(SpeicherLesen.WortMitVorzeichenGeben(var3));
						}
					} else {
						return "";
					}
				}
			}

			public boolean isCellEditable(int var1, int var2) {
				return SpeicherAnzeige.this.editierbar && var2 != 0;
			}

			public void setValueAt(Object var1, int var2, int var3) {
				if (var1 instanceof String) {
					try {
						String var5 = (String)var1;
						int var4;
						if (SpeicherAnzeige.this.hexaDarstellung) {
							if (var5.startsWith("0x")) {
								var5 = var5.substring(2);
							}

							var4 = Integer.parseInt(var5, 16);
						} else {
							var4 = Integer.parseInt(var5);
						}

						if (-32768 <= var4 && var4 <= 65535) {
							SpeicherAnzeige.this.geändert = -1;
							SpeicherLesen.WortSetzen(var2 * 10 + (var3 - 1), var4);
							SpeicherAnzeige.this.geändert = -1;
						}
					} catch (Exception var6) {
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

			public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
				Component var7 = super.getTableCellRendererComponent(var1, var2, var3, var4, var5, var6);
				if (this.f == null) {
					this.f = var7.getFont();
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
		this.table.getTableHeader().setFont(this.table.getTableHeader().getFont().deriveFont(1));
		this.scrollpane = new JScrollPane(this.table);
		this.tableGross = new JTable(this.dataModel);
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
		JMenuItem var1 = new JMenuItem("Widerrufen", 90);
		var1.setAccelerator(KeyStroke.getKeyStroke(90, kommando));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		var1 = new JMenuItem("Wiederholen");
		var1.setAccelerator(KeyStroke.getKeyStroke(90, 64 + kommando));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		this.editMenu.addSeparator();
		var1 = new JMenuItem("Ausschneiden", 88);
		var1.setAccelerator(KeyStroke.getKeyStroke(88, kommando));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		var1 = new JMenuItem("Kopieren", 67);
		var1.setAccelerator(KeyStroke.getKeyStroke(67, kommando));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		var1 = new JMenuItem("Einfügen", 86);
		var1.setAccelerator(KeyStroke.getKeyStroke(86, kommando));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		var1 = new JMenuItem("Alles auswählen", 65);
		var1.setAccelerator(KeyStroke.getKeyStroke(65, kommando));
		var1.setEnabled(false);
		this.editMenu.add(var1);
		this.toolsMenu.addSeparator();
		var1 = new JMenuItem("Speicher löschen", 65);
		var1.setEnabled(true);
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				SpeicherAnzeige.this.controller.SpeicherLöschen();
			}
		});
		this.toolsMenu.add(var1);
		this.toolsMenu.addSeparator();
		this.hexaItem = new JCheckBoxMenuItem("Darstellung hexadezimal");
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
		this.opcodeItem = new JCheckBoxMenuItem("Opcodes anzeigen");
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
		this.editItem = new JCheckBoxMenuItem("Speicher editieren");
		this.editItem.setEnabled(true);
		this.editItem.setSelected(false);
		this.editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				SpeicherAnzeige.this.editierbar = SpeicherAnzeige.this.editItem.isSelected();
			}
		});
		this.toolsMenu.add(this.editItem);
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

	public void SpeicherGeändertMelden(int var1) {
		this.geändert = var1;
		this.scrollpane.invalidate();
		this.scrollpane.repaint();
		this.scrollpaneGross.invalidate();
		this.scrollpaneGross.repaint();
	}
}
