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
import java.awt.event.KeyEvent;
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

	protected ControllerInterface controller;
	protected Anzeige self;
	private static boolean erster = true;
	protected static boolean isMac;
	protected static int cmdKey;


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

	Anzeige(ControllerInterface controller) {
		this.controller = controller;
		this.self = this;
		if (erster) {
			erster = false;
			isMac = System.getProperty("os.name", "").startsWith("Mac");
			if (isMac) {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				//this.MacOSVorbereiten(); //Fuck mac users lmao
				cmdKey = 256;
			} else {
				cmdKey = 128;
			}

			try {
				UIManager.setLookAndFeel(new FlatLightLaf());
			} catch (Exception ex) {
				ex.printStackTrace();
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
		this.fileMenu = new JMenu(R.string("file_menu"));
		this.menuBar.add(this.fileMenu);
		JMenuItem menu = new JMenuItem(R.string("file_menu_new"), 78);
		menu.setAccelerator(KeyStroke.getKeyStroke(78, cmdKey));
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.controller.NeuAusführen();
			}
		});
		this.fileMenu.add(menu);
		menu = new JMenuItem(R.string("file_menu_open"), 79);
		menu.setAccelerator(KeyStroke.getKeyStroke(79, cmdKey));
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.controller.ÖffnenAusführen();
			}
		});

		this.fileMenu.add(menu);

		this.fileMenu.addSeparator();

		this.closeMenuItem = new JMenuItem(R.string("file_menu_close"), 87);
		this.closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(87, cmdKey));
		this.fileMenu.add(this.closeMenuItem);

		this.saveMenuItem = new JMenuItem(R.string("file_menu_save"), 83);
		this.saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(83, cmdKey));
		this.fileMenu.add(this.saveMenuItem);

		this.saveAsMenuItem = new JMenuItem(R.string("file_menu_save_as"));
		this.saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(83, cmdKey + 64));
		this.fileMenu.add(this.saveAsMenuItem);

		this.fileMenu.addSeparator();

		this.printMenuItem = new JMenuItem(R.string("file_menu_print"));
		this.printMenuItem.setAccelerator(KeyStroke.getKeyStroke('P', cmdKey));
		this.fileMenu.add(this.printMenuItem);
		if (!isMac) {
			this.fileMenu.addSeparator();
			menu = new JMenuItem(R.string("file_menu_quit"), 81);
			menu.setAccelerator(KeyStroke.getKeyStroke(81, cmdKey));
			menu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent var1) {
					Anzeige.this.controller.BeendenAusführen();
				}
			});
			this.fileMenu.add(menu);
		}

		this.editMenu = new JMenu(R.string("edit_menu"));
		this.menuBar.add(this.editMenu);
		this.toolsMenu = new JMenu(R.string("tools_menu"));
		this.menuBar.add(this.toolsMenu);
		this.sizeMenuItem = new JCheckBoxMenuItem(R.string("tools_menu_increase_size"));
		this.sizeMenuItem.setEnabled(true);
		this.sizeMenuItem.setSelected(false);
		this.sizeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.resetDisplaySize(Anzeige.this.sizeMenuItem.isSelected());
			}
		});
		this.toolsMenu.add(this.sizeMenuItem);
		this.windowsMenu = new JMenu(R.string("windows_menu"));
		JMenuItem helpMenuItem = new JMenuItem(R.string("windows_menu_open_doc"), KeyEvent.VK_F1);
		helpMenuItem.setAccelerator(KeyStroke.getKeyStroke("F1"));

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
			menu = new JMenuItem(R.string("windows_menu_about"));
			menu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent var1) {
					AboutDialog.show();
				}
			});
			this.windowsMenu.add(menu);
			this.windowsMenu.addSeparator();
		}

		menu = new JMenuItem(R.string("windows_menu_cpu"));
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.controller.CpuFensterAuswählen();
			}
		});
		this.windowsMenu.add(menu);
		menu = new JMenuItem(R.string("windows_menu_memory"));
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent var1) {
				Anzeige.this.controller.SpeicherFensterAuswählen();
			}
		});
		this.windowsMenu.add(menu);
		this.windowsMenu.addSeparator();
	}

	protected abstract void resetDisplaySize(boolean var1);

	private String TitelGeben() {
		return this.window.getTitle();
	}

	void addWindowMenuEntry(int pos, Anzeige display) {
		JMenuItem windowMenuEntry = new JMenuItem(display.TitelGeben());
		windowMenuEntry.addActionListener(new WindowMenuAction(display));
		this.windowsMenu.insert(windowMenuEntry, pos + 3);
	}

	void removeWindowMenuEntry(int pos) {
		this.windowsMenu.remove(pos + 3);
	}

	void editWindowMenuEntry(int var1, Anzeige var2) {
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

	void notifyClose() {
	}

	static boolean IstMacOS() {
		return isMac;
	}


	/***
	 *  Used for the window menu. Called when clicking the displays corresponding menu entry.
	 */
	class WindowMenuAction implements ActionListener {
		private Anzeige anzeige;


		WindowMenuAction(Anzeige var2) {
			this.anzeige = var2;
		}

		public void actionPerformed(ActionEvent var1) {
			this.anzeige.show();
		}
	}
}
