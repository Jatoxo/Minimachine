//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import R.R;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

		label = new JLabel("AMOGUS ABIGUS ABOMINAGUS");
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
