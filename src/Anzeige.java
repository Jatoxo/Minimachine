//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

/*
import com.apple.eawt.AboutHandler;
import com.apple.eawt.Application;
import com.apple.eawt.OpenFilesHandler;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.AppEvent.OpenFilesEvent;
import com.apple.eawt.AppEvent.QuitEvent;
 */
import com.formdev.flatlaf.FlatLightLaf;
import res.R;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.*;

abstract class Anzeige {
	protected JFrame window;
	protected JMenuBar menuBar;
	protected JMenu fileMenu;
	protected JMenu editMenu;
	protected JMenu toolsMenu;
	protected JMenu windowsMenu;
	protected JMenuItem closeMenuItem;
	protected JMenuItem saveMenuItem;
	protected JMenuItem saveAsMenuItem;
	protected JMenuItem printMenuItem;
	protected JCheckBoxMenuItem sizeMenuItem;

	protected KontrolleurInterface controller;
	protected Anzeige self;
	private static boolean erster = true;
	protected static boolean isMac;
	protected static int kommando;


	/*
	private void MacOSVorbereiten() {
		Application var1 = Application.getApplication();
		var1.setAboutHandler(new AboutHandler() {
			public void handleAbout(AboutEvent var1) {
				Über.Zeigen();
			}
		});
		var1.setQuitHandler(new QuitHandler() {
			public void handleQuitRequestWith(QuitEvent var1, QuitResponse var2) {
				Anzeige.this.kontrolleur.BeendenAusführen();
				var2.cancelQuit();
			}
		});
		var1.setOpenFileHandler(new OpenFilesHandler() {
			public void openFiles(OpenFilesEvent var1) {
				List var2 = var1.getFiles();
				Iterator var3 = var2.iterator();

				while(var3.hasNext()) {
					File var4 = (File)var3.next();
					Anzeige.this.kontrolleur.ÖffnenAusführen(var4.getPath());
				}

			}
		});
	}
	*/

	Anzeige(KontrolleurInterface controller) {
		this.controller = controller;
		this.self = this;
		if (erster) {
			erster = false;
			isMac = System.getProperty("os.name", "").startsWith("Mac");
			if (isMac) {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				//this.MacOSVorbereiten();
				kommando = 256;
			} else {
				kommando = 128;
			}

			try {
				UIManager.setLookAndFeel(new FlatLightLaf());
			} catch (Exception var3) {
			}
		}

		this.initMenus();
		this.initLayout();

		URL iconURL = getClass().getResource("/res/img/icon.png");
		System.out.println(iconURL);
		ImageIcon icon = new ImageIcon(iconURL);
		this.window.setIconImage(icon.getImage());

		//this.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		if (isMac) {
		}

	}

	protected abstract void initLayout();

	protected void initMenus() {

		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu(R.getResources().getString("file_menu"));
		this.menuBar.add(this.fileMenu);
		JMenuItem var1 = new JMenuItem(R.getResources().getString("file_menu_new"), 78);
		var1.setAccelerator(KeyStroke.getKeyStroke(78, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.controller.NeuAusführen();
			}
		});
		this.fileMenu.add(var1);
		var1 = new JMenuItem(R.getResources().getString("file_menu_open"), 79);
		var1.setAccelerator(KeyStroke.getKeyStroke(79, kommando));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.controller.ÖffnenAusführen();
			}
		});

		this.fileMenu.add(var1);

		this.fileMenu.addSeparator();

		this.closeMenuItem = new JMenuItem(R.getResources().getString("file_menu_close"), 87);
		this.closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(87, kommando));
		this.fileMenu.add(this.closeMenuItem);

		this.saveMenuItem = new JMenuItem(R.getResources().getString("file_menu_save"), 83);
		this.saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(83, kommando));
		this.fileMenu.add(this.saveMenuItem);

		this.saveAsMenuItem = new JMenuItem(R.getResources().getString("file_menu_save_as"));
		this.saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(83, kommando + 64));
		this.fileMenu.add(this.saveAsMenuItem);

		this.fileMenu.addSeparator();

		this.printMenuItem = new JMenuItem(R.getResources().getString("file_menu_print"));
		this.printMenuItem.setAccelerator(KeyStroke.getKeyStroke('P', kommando));
		this.fileMenu.add(this.printMenuItem);
		if (!isMac) {
			this.fileMenu.addSeparator();
			var1 = new JMenuItem(R.getResources().getString("file_menu_quit"), 81);
			var1.setAccelerator(KeyStroke.getKeyStroke(81, kommando));
			var1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent var1) {
					Anzeige.this.controller.BeendenAusführen();
				}
			});
			this.fileMenu.add(var1);
		}

		this.editMenu = new JMenu(R.getResources().getString("edit_menu"));
		this.menuBar.add(this.editMenu);
		this.toolsMenu = new JMenu(R.getResources().getString("tools_menu"));
		this.menuBar.add(this.toolsMenu);
		this.sizeMenuItem = new JCheckBoxMenuItem(R.getResources().getString("tools_menu_increase_size"));
		this.sizeMenuItem.setEnabled(true);
		this.sizeMenuItem.setSelected(false);
		this.sizeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.resetDisplaySize(Anzeige.this.sizeMenuItem.isSelected());
			}
		});
		this.toolsMenu.add(this.sizeMenuItem);
		this.windowsMenu = new JMenu(R.getResources().getString("windows_menu"));
		JMenuItem helpMenuItem = new JMenuItem(R.getResources().getString("windows_menu_open_doc"));
		this.windowsMenu.add(helpMenuItem);
		helpMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					try {
						Desktop.getDesktop().browse(new URI("https://jatoxo.github.io/Minimachine"));
					} catch(IOException | URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
		});
		this.menuBar.add(this.windowsMenu);
		if (!isMac) {
			var1 = new JMenuItem(R.getResources().getString("windows_menu_about"));
			var1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent var1) {
					AboutDialog.show();
				}
			});
			this.windowsMenu.add(var1);
			this.windowsMenu.addSeparator();
		}

		var1 = new JMenuItem(R.getResources().getString("windows_menu_cpu"));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.controller.CpuFensterAuswählen();
			}
		});
		this.windowsMenu.add(var1);
		var1 = new JMenuItem(R.getResources().getString("windows_menu_memory"));
		var1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.controller.SpeicherFensterAuswählen();
			}
		});
		this.windowsMenu.add(var1);
		this.windowsMenu.addSeparator();
	}

	protected abstract void resetDisplaySize(boolean var1);

	private String TitelGeben() {
		return this.window.getTitle();
	}

	void FenstereintragHinzufügen(int var1, Anzeige var2) {
		JMenuItem var3 = new JMenuItem(var2.TitelGeben());
		var3.addActionListener(new Anzeige.FensterAktion(var2));
		this.windowsMenu.insert(var3, var1 + 3);
	}

	void FenstereintragEntfernen(int var1) {
		this.windowsMenu.remove(var1 + 3);
	}

	void FenstereintragÄndern(int var1, Anzeige var2) {
		this.windowsMenu.getItem(var1 + 3).setText(var2.TitelGeben());
	}

	void show() {
		if (!this.window.isVisible()) {
			this.window.setVisible(true);
		}

		this.window.toFront();
	}

	void hide() {
		this.window.setVisible(false);
	}

	void BeendenMitteilen() {
	}

	static boolean IstMacOS() {
		return isMac;
	}

	class FensterAktion implements ActionListener {
		private Anzeige anzeige;

		FensterAktion(Anzeige var2) {
			this.anzeige = var2;
		}

		public void actionPerformed(ActionEvent var1) {
			this.anzeige.show();
		}
	}
}
