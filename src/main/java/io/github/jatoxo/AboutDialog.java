package io.github.jatoxo;//


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class AboutDialog {
	private JDialog dialog = new JDialog((JFrame)null);
	private static AboutDialog aboutDialog = null;

	private AboutDialog() {
		this.dialog.setTitle(R.string("aboutMM"));


		JPanel contentPane = (JPanel)this.dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setVisible(true);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(panel, "Center");
		panel.setLayout(new GridLayout(6, 1));

		JLabel label = new JLabel(R.string("appName"));
		label.setHorizontalAlignment(0);
		panel.add(label);

		label = new JLabel(R.string("version"));
		label.setHorizontalAlignment(0);
		panel.add(label);

		label = new JLabel(" ");
		panel.add(label);

		label = new JLabel(R.string("aboutDetail"));
		label.setHorizontalAlignment(0);
		panel.add(label);

		label = new JLabel("© 2009-2019 Albert Wiedemann modified by me");
		label.setHorizontalAlignment(0);
		panel.add(label);

		label = new JLabel(" ");
		panel.add(label);


		panel = new JPanel();
		panel.setVisible(true);
		contentPane.add(panel, "South");
		panel.setLayout(new FlowLayout(1));
		JButton button = new JButton("Ok");
		panel.add(button);

		button.addActionListener(actionEvent -> AboutDialog.this.dialog.setVisible(false));
		this.dialog.setModal(true);
		this.dialog.setResizable(false);
		this.dialog.pack();
		this.dialog.setLocationRelativeTo(null);
		this.dialog.setVisible(false);
	}

	static void show() {
		if (aboutDialog == null) {
			aboutDialog = new AboutDialog();
		}

		aboutDialog.dialog.setVisible(true);
	}
}
