import model.SpeicherLesen;
import res.R;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

public class MemoryTracker extends JFrame {
	class TrackedVariable {
		TrackedVariable(int addr) {
			this.addr = addr;
		}

		int addr;
		String label = "";
	}

	JTable table;
	ArrayList<TrackedVariable> trackedVariables;

	public MemoryTracker() {
		super(R.getResources().getString("memory_track_window_title"));

		URL iconURL = getClass().getResource("/res/img/icon.png");
		System.out.println(iconURL);
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());


		trackedVariables = new ArrayList<>();


		TableModel model = new AbstractTableModel() {

			@Override
			public int getRowCount() {
				return trackedVariables.size();
			}

			@Override
			public int getColumnCount() {
				return 3;
			}

			@Override
			public String getColumnName(int column) {
				switch(column) {
					case 0:
						return R.getResources().getString("memory_track_table_label");
					case 1:
						return R.getResources().getString("memory_track_table_value");
					case 2:
						return R.getResources().getString("memory_track_table_address");
					default:
						return "";
				}
			}

			@Override
			public Object getValueAt(int row, int col) {
				TrackedVariable var = trackedVariables.get(row);
				switch(col) {
					case 0:
						return var.label;
					case 1:
						return SpeicherLesen.WortMitVorzeichenGeben(var.addr);
					case 2:
						return var.addr;
					default:
						return "";
				}
			}

			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				if(aValue instanceof String && ((String) aValue).trim().length() > 0){
					trackedVariables.get(rowIndex).label = (String) aValue;
				} else if(trackedVariables.get(rowIndex).label.trim().length() == 0) {
					trackedVariables.get(rowIndex).label = R.getResources().getString("memory_track_table_default_label");
				}
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex == 0;
			}




		};

		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(226, 200));
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFocusable(false);

		JPopupMenu popup = new JPopupMenu();
		JMenuItem clearItem = new JMenuItem(R.getResources().getString("clear_all"));
		clearItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				clearAll();
			}
		});
		popup.add(clearItem);
		table.setComponentPopupMenu(popup);


		//table.setFillsViewportHeight(true);

		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if(column == 0) {
					//component.setBackground(Color.LIGHT_GRAY);
				} else {
					//component.setBackground(Color.WHITE);
				}
				if(isSelected) {
				//	component.setForeground(Color.BLUE);
				} else {
				//	component.setForeground(Color.BLACK);
				}
				return component;
			}
		});

		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setComponentPopupMenu(popup);

		JPanel tableContainer = new JPanel();
		tableContainer.setLayout(new BorderLayout());
		tableContainer.setBorder(new EmptyBorder(10, 10, 0, 10));
		tableContainer.add(tableScrollPane);
		//tableContainer.setBorder(new LineBorder(Color.black, 2, true));
		add(tableContainer);

		JPanel footer = new JPanel();
		//footer.setLayout(new BoxLayout(footer, BoxLayout.LINE_AXIS));
		JButton addButton = new JButton("+");
		addButton.setPreferredSize(new Dimension(45, 30));
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				addTrackedVariable();
			}
		});


		JButton removeButton = new JButton("-");
		removeButton.setPreferredSize(new Dimension(45, 30));
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int selected = table.getSelectedRow();
				if(selected != -1) {
					trackedVariables.remove(table.getSelectedRow());
					((AbstractTableModel) table.getModel()).fireTableRowsDeleted(trackedVariables.size(), trackedVariables.size());
				}
			}
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				if(!listSelectionEvent.getValueIsAdjusting()) {
					if(table.getSelectedRow() == -1) {
						removeButton.setEnabled(false);
					} else {
						removeButton.setEnabled(true);
					}
				}

			}
		});

		footer.add(addButton);
		footer.add(removeButton);
		add(footer, BorderLayout.SOUTH);


		pack();
		setMinimumSize(getSize());

		//setResizable(false);


		//setVisible(true);
	}

	public void clearAll() {
		trackedVariables.clear();
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
	}

	public void addTrackedVariable(int address) {
		trackedVariables.add(new TrackedVariable(address));
		table.editCellAt(trackedVariables.size()-1, 0);
		table.getEditorComponent().requestFocus();
		((AbstractTableModel) table.getModel()).fireTableDataChanged();

	}
	public void addTrackedVariable() {
		int address = -1;
		while(address == -1) {
			String choice =  JOptionPane.showInputDialog(this, R.getResources().getString("memory_track_input_address_prompt"), R.getResources().getString("memory_track_input_address_title"), JOptionPane.QUESTION_MESSAGE);
			if(choice == null) {
				break;
			}
			choice = choice.trim();
			try {
				if (choice.startsWith("0x")) {
					choice = choice.substring(2);
					address = Integer.parseInt(choice, 16);
				} else {
					address = Integer.parseInt(choice);
				}
			} catch(NumberFormatException ex) {

			}
			if(address > 65535) {
				address = -1;
			}
		}
		if(address == -1) {
			return;
		}
		trackedVariables.add(new TrackedVariable(address));
		table.editCellAt(trackedVariables.size()-1, 0);
		table.getEditorComponent().requestFocus();
		((AbstractTableModel) table.getModel()).fireTableDataChanged();

	}

	public void memoryChanged() {
		((AbstractTableModel) table.getModel()).fireTableDataChanged();

	}

}
