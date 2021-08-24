//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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

class Über {
	private JDialog dialog = new JDialog((JFrame)null);
	private static Über über = null;

	private Über() {
		this.dialog.setTitle("Über Minimaschine");
		JPanel var1 = (JPanel)this.dialog.getContentPane();
		var1.setLayout(new BorderLayout());
		JPanel var4 = new JPanel();
		var4.setVisible(true);
		var1.add(var4, "Center");
		var4.setLayout(new GridLayout(6, 1));
		JLabel var2 = new JLabel("Minimaschine");
		var2.setHorizontalAlignment(0);
		var4.add(var2);
		var2 = new JLabel("V2.0");
		var2.setHorizontalAlignment(0);
		var4.add(var2);
		var2 = new JLabel(" ");
		var4.add(var2);
		var2 = new JLabel("Ein Emulationsprogramm für eine einfache CPU  ");
		var2.setHorizontalAlignment(0);
		var4.add(var2);
		var2 = new JLabel("© 2009-2019 Albert Wiedemann");
		var2.setHorizontalAlignment(0);
		var4.add(var2);
		var2 = new JLabel(" ");
		var4.add(var2);
		var4 = new JPanel();
		var4.setVisible(true);
		var1.add(var4, "South");
		var4.setLayout(new FlowLayout(1));
		JButton var3 = new JButton("Ok");
		var4.add(var3);
		var3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Über.this.dialog.setVisible(false);
			}
		});
		this.dialog.setModal(true);
		this.dialog.setResizable(false);
		this.dialog.pack();
		this.dialog.setVisible(false);
	}

	static void Zeigen() {
		if (über == null) {
			über = new Über();
		}

		über.dialog.setVisible(true);
	}
}
