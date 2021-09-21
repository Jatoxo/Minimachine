package io.github.jatoxo;//


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SetTimeoutDialog {
	private JDialog dialog;
	private static SetTimeoutDialog timeoutDialog = null;
	private ControllerInterface kontrolleur;

	private SetTimeoutDialog(ControllerInterface controller) {
		this.kontrolleur = controller;
		this.dialog = new JDialog((JFrame)null);
		this.dialog.setTitle(R.string("dialog_timeout_title"));

		JPanel contentPane = (JPanel) this.dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setVisible(true);
		panel.setLayout(new FlowLayout(0));
		panel.setBorder(new EmptyBorder(10, 20, 10, 20));
		contentPane.add(panel, "Center");

		JLabel label = new JLabel(R.string("dialog_timeout_prompt"));
		panel.add(label);
		final JTextField inputField = new JTextField();
		inputField.setColumns(3);
		inputField.setMinimumSize(new Dimension(80, 30));
		panel.add(inputField);

		panel = new JPanel();
		panel.setVisible(true);
		contentPane.add(panel, "South");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton button = new JButton("Ok");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				try {
					int var2 = Integer.parseInt(inputField.getText());
					SetTimeoutDialog.this.kontrolleur.setTimeout(var2);
					SetTimeoutDialog.this.dialog.setVisible(false);
				} catch (Exception var3) {
					inputField.selectAll();
				}

			}
		});

		this.dialog.setModal(true);
		this.dialog.setResizable(false);
		this.dialog.pack();
		this.dialog.setVisible(false);
	}

	static void show(ControllerInterface controller) {
		if (timeoutDialog == null) {
			timeoutDialog = new SetTimeoutDialog(controller);
		}
		timeoutDialog.dialog.setLocationRelativeTo(null);
		timeoutDialog.dialog.setVisible(true);
	}
}
