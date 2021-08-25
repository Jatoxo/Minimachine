//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import res.R;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class AboutDialog {
	private JDialog dialog = new JDialog((JFrame)null);
	private static AboutDialog aboutDialog = null;

	private AboutDialog() {
		this.dialog.setTitle(R.getResources().getString("aboutMM"));

		JPanel contentPane = (JPanel)this.dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setVisible(true);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(panel, "Center");
		panel.setLayout(new GridLayout(6, 1));

		JLabel label = new JLabel(R.getResources().getString("appName"));
		label.setHorizontalAlignment(0);
		panel.add(label);

		label = new JLabel(R.getResources().getString("version"));
		label.setHorizontalAlignment(0);
		panel.add(label);

		label = new JLabel(" ");
		panel.add(label);

		label = new JLabel(R.getResources().getString("aboutDetail"));
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
		this.dialog.setVisible(false);
	}

	static void show() {
		if (aboutDialog == null) {
			aboutDialog = new AboutDialog();
		}

		aboutDialog.dialog.setVisible(true);
	}
}
