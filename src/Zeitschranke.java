//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Zeitschranke {
	private JDialog dialog;
	private static Zeitschranke schranke = null;
	private KontrolleurInterface kontrolleur;

	private Zeitschranke(KontrolleurInterface var1) {
		this.kontrolleur = var1;
		this.dialog = new JDialog((JFrame)null);
		this.dialog.setTitle("Prozessorzeitschranke");
		JPanel var2 = (JPanel)this.dialog.getContentPane();
		var2.setLayout(new BorderLayout());
		JPanel var6 = new JPanel();
		var6.setVisible(true);
		var2.add(var6, "Center");
		var6.setLayout(new FlowLayout(0));
		JLabel var3 = new JLabel("Geben Sie die Zeitschranke in Sekunden ein");
		var6.add(var3);
		final JTextField var4 = new JTextField("     ");
		var4.setMinimumSize(new Dimension(80, 30));
		var6.add(var4);
		var6 = new JPanel();
		var6.setVisible(true);
		var2.add(var6, "South");
		var6.setLayout(new FlowLayout(1));
		JButton var5 = new JButton("Ok");
		var6.add(var5);
		var5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				try {
					int var2 = Integer.parseInt(var4.getText());
					Zeitschranke.this.kontrolleur.ZeitschrankeSetzen(var2);
					Zeitschranke.this.dialog.setVisible(false);
				} catch (Exception var3) {
					var4.selectAll();
				}

			}
		});
		this.dialog.setModal(true);
		this.dialog.setResizable(false);
		this.dialog.pack();
		this.dialog.setVisible(false);
	}

	static void Zeigen(KontrolleurInterface var0) {
		if (schranke == null) {
			schranke = new Zeitschranke(var0);
		}

		schranke.dialog.setVisible(true);
	}
}
