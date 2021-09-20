package main.java.io.github.jatoxo;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import javax.swing.*;
import java.awt.*;

class CpuBild extends JComponent {
	protected int breiteCpu = 380;
	protected int höheCpu = 180;
	protected int breiteSpeicher = 160;
	protected int höheSpeicher = 240;
	protected int breiteKasten;
	protected int höheKasten;
	protected int minBreite;
	protected int minHöhe;
	protected int[] xKoordinaten;
	protected int[] yKoordinaten;
	protected boolean erweitert;
	private String datenWert;
	private String adressWert;
	private String alu1;
	private String alu2;
	private String alu3;
	private String ac;
	private String sr;
	private String ir1;
	private String ir2;
	private String pc;
	private String sp;
	private String[] progadr = new String[]{"", "", "", ""};
	private String[] progmem = new String[]{"", "", "", ""};
	private String[] dataadr = new String[]{"", ""};
	private String[] datamem = new String[]{"", ""};
	private String[] stackadr = new String[]{"", "", ""};
	private String[] stackmem = new String[]{"", "", ""};
	private String mikroschritt = "mikro";

	CpuBild() {
		this.minBreite = this.breiteCpu + this.breiteSpeicher + 40;
		this.minHöhe = this.höheSpeicher + 120;
		this.breiteKasten = 80;
		this.höheKasten = 20;
		this.xKoordinaten = new int[]{0, 40, 80, 120, 80, 60, 40};
		this.yKoordinaten = new int[]{0, 60, 60, 0, 0, 30, 0};
		this.datenWert = "";
		this.adressWert = "";
		this.alu1 = "0";
		this.alu2 = "0";
		this.alu3 = "0";
		this.ac = "0";
		this.sr = "Z:  N:  V:";
		this.ir1 = "0";
		this.ir2 = "0";
		this.pc = "0";
		this.sp = "0";
		this.erweitert = false;
	}

	void DatenSetzen(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String[] var12, String[] var13, String[] var14, String[] var15, String[] var16, String[] var17, boolean var18, String var19) {
		this.datenWert = var1;
		this.adressWert = var2;
		this.alu1 = var3;
		this.alu2 = var4;
		this.alu3 = var5;
		this.ac = var6;
		this.sr = var7;
		this.ir1 = var8;
		this.ir2 = var9;
		this.pc = var10;
		this.sp = var11;
		this.progadr = var12;
		this.progmem = var13;
		this.dataadr = var14;
		this.datamem = var15;
		this.stackadr = var16;
		this.stackmem = var17;
		this.erweitert = var18;
		this.mikroschritt = var19;
		this.invalidate();
		this.repaint();
	}

	public Dimension getMinimumSize() {
		return new Dimension(this.minBreite, this.minHöhe);
	}

	public Dimension getPreferredSize() {
		return new Dimension(this.minBreite + 10, this.minHöhe + 20);
	}

	protected void paintComponent(Graphics var1) {
		int var2 = this.getWidth();
		int var3 = this.getHeight();
		if (this.isOpaque()) {
			var1.setColor(this.getBackground());
			var1.fillRect(0, 0, var2, var3);
			var1.setColor(this.getForeground());
		}

		var1.setColor(Color.red);
		var1.drawLine(10, this.höheKasten + 10, var2 - 10, this.höheKasten + 10);
		var1.drawLine(10, this.höheKasten + 11, var2 - 10, this.höheKasten + 11);
		var1.drawString(R.string("cpu_data_bus"), 15, this.höheKasten);
		var1.drawLine(190, this.höheKasten + 10, 190, var3 / 2 - this.höheCpu / 2 - 1);
		var1.drawLine(191, this.höheKasten + 10, 191, var3 / 2 - this.höheCpu / 2 - 1);
		var1.drawLine(var2 - 90, this.höheKasten + 10, var2 - 90, var3 / 2 - this.höheSpeicher / 2 - 1);
		var1.drawLine(var2 - 89, this.höheKasten + 10, var2 - 89, var3 / 2 - this.höheSpeicher / 2 - 1);
		if (!"".equals(this.datenWert)) {
			var1.drawString(this.datenWert, this.breiteCpu - 10, this.höheKasten);
		}

		var1.setColor(Color.black);
		if (!this.mikroschritt.equals("")) {
			var1.drawString(R.string("cpu_micro_step_label") + ": " + this.mikroschritt, 200, (this.höheKasten + var3 / 2 - this.höheCpu / 2) / 2 + 10);
		}

		var1.drawRect(10, var3 / 2 - this.höheCpu / 2, this.breiteCpu, this.höheCpu);
		var1.drawRect(20, var3 / 2 - this.höheCpu / 2 + this.höheKasten + 10, this.breiteKasten, this.höheKasten);
		var1.drawString(this.ac, 25, var3 / 2 - this.höheCpu / 2 + this.höheKasten * 2 + 5);
		var1.drawRect(this.breiteCpu - 2 * this.breiteKasten, var3 / 2 - this.höheCpu / 2 + this.höheKasten + 10, this.breiteKasten, this.höheKasten);
		if (this.ir1.length() > 0 && this.ir1.charAt(0) >= '0' && this.ir1.charAt(0) <= '9') {
			var1.drawString(this.ir1, this.breiteCpu - 2 * this.breiteKasten + 5, var3 / 2 - this.höheCpu / 2 + this.höheKasten * 2 + 5);
		} else {
			var1.setColor(new Color(255, 127, 0));
			var1.drawString(this.ir1, this.breiteCpu - 2 * this.breiteKasten + 5, var3 / 2 - this.höheCpu / 2 + this.höheKasten * 2 + 5);
			var1.setColor(Color.black);
		}

		var1.drawRect(this.breiteCpu - this.breiteKasten, var3 / 2 - this.höheCpu / 2 + this.höheKasten + 10, this.breiteKasten, this.höheKasten);
		var1.drawString(this.ir2, this.breiteCpu - this.breiteKasten + 5, var3 / 2 - this.höheCpu / 2 + this.höheKasten * 2 + 5);
		var1.drawRect(this.breiteCpu - 2 * this.breiteKasten, var3 / 2 - this.höheCpu / 2 + 3 * this.höheKasten + 30, this.breiteKasten, this.höheKasten);
		var1.drawString(this.pc, this.breiteCpu - 2 * this.breiteKasten + 5, var3 / 2 - this.höheCpu / 2 + 4 * this.höheKasten + 25);
		if (this.erweitert) {
			var1.drawRect(this.breiteCpu - 2 * this.breiteKasten, var3 / 2 - this.höheCpu / 2 + 5 * this.höheKasten + 50, this.breiteKasten, this.höheKasten);
			var1.drawString(this.sp, this.breiteCpu - 2 * this.breiteKasten + 5, var3 / 2 - this.höheCpu / 2 + 6 * this.höheKasten + 45);
		}

		Polygon var4 = new Polygon(this.xKoordinaten, this.yKoordinaten, this.xKoordinaten.length);
		var4.translate(40, var3 / 2 - this.höheCpu / 2 + 2 * this.höheKasten + 20);
		var1.drawPolygon(var4);
		var1.drawRect(20, var3 / 2 + this.höheCpu / 2 - (this.höheKasten + 10), this.breiteKasten, this.höheKasten);
		var1.drawString(this.sr, 25, var3 / 2 + this.höheCpu / 2 - 15);
		var1.setColor(new Color(255, 0, 255));
		var1.drawString(this.alu1, 40 + this.xKoordinaten[1] / 4, var3 / 2 - this.höheCpu / 2 + 3 * this.höheKasten + 15);
		var1.drawString(this.alu2, 40 + this.xKoordinaten[4], var3 / 2 - this.höheCpu / 2 + 3 * this.höheKasten + 15);
		var1.drawString(this.alu3, 40 + this.xKoordinaten[1], var3 / 2 - this.höheCpu / 2 + 2 * this.höheKasten + this.yKoordinaten[1] + 15);
		var1.setColor(Color.black);
		var1.drawRect(var2 - (this.breiteSpeicher + 10), var3 / 2 - this.höheSpeicher / 2, this.breiteSpeicher, this.höheSpeicher);

		int var5;
		for(var5 = 0; var5 < this.progmem.length; ++var5) {
			var1.drawRect(var2 - (this.breiteKasten + 20), var3 / 2 - this.höheSpeicher / 2 + 5 + var5 * this.höheKasten, this.breiteKasten, this.höheKasten);
			var1.drawString(this.progmem[var5], var2 - (this.breiteKasten + 15), var3 / 2 - this.höheSpeicher / 2 + this.höheKasten + var5 * this.höheKasten);
		}

		if (this.erweitert) {
			for(var5 = 0; var5 < this.datamem.length; ++var5) {
				var1.drawRect(var2 - (this.breiteKasten + 20), var3 / 2 + this.höheKasten * (this.progmem.length - this.stackmem.length) / 2 - this.höheKasten * this.datamem.length / 2 + var5 * this.höheKasten, this.breiteKasten, this.höheKasten);
				var1.drawString(this.datamem[var5], var2 - (this.breiteKasten + 15), var3 / 2 + this.höheKasten * (this.progmem.length - this.stackmem.length) / 2 - this.höheKasten * this.datamem.length / 2 + this.höheKasten - 5 + var5 * this.höheKasten);
			}

			var1.drawString(". . .", var2 - (this.breiteKasten + 10), var3 / 2 + this.höheKasten * (this.progmem.length - this.stackmem.length) / 4 - this.höheSpeicher / 4 + 5 + (this.progmem.length * this.höheKasten - this.höheKasten * this.datamem.length / 2) / 2);
			var1.drawString(". . .", var2 - (this.breiteKasten + 10), var3 / 2 + this.höheSpeicher / 4 + this.höheKasten * (this.progmem.length - this.stackmem.length) / 4 + this.höheKasten * this.datamem.length / 4 - this.höheKasten * this.stackmem.length / 2);

			for(var5 = 0; var5 < this.stackmem.length; ++var5) {
				var1.drawRect(var2 - (this.breiteKasten + 20), var3 / 2 + this.höheSpeicher / 2 - this.höheKasten * this.stackmem.length - 5 + var5 * this.höheKasten, this.breiteKasten, this.höheKasten);
				var1.drawString(this.stackmem[var5], var2 - (this.breiteKasten + 15), var3 / 2 + this.höheSpeicher / 2 - this.höheKasten * this.stackmem.length + this.höheKasten - 10 + var5 * this.höheKasten);
			}
		} else {
			for(var5 = 0; var5 < this.datamem.length; ++var5) {
				var1.drawRect(var2 - (this.breiteKasten + 20), var3 / 2 + this.höheSpeicher / 2 - this.höheKasten * this.datamem.length - 5 + var5 * this.höheKasten, this.breiteKasten, this.höheKasten);
				var1.drawString(this.datamem[var5], var2 - (this.breiteKasten + 15), var3 / 2 + this.höheSpeicher / 2 - this.höheKasten * this.datamem.length + this.höheKasten - 10 + var5 * this.höheKasten);
			}

			var1.drawString(". . .", var2 - (this.breiteKasten + 10), var3 / 2 + this.höheKasten);
		}

		var1.setColor(Color.blue);
		var1.drawLine(10, var3 - 30, var2 - 10, var3 - 30);
		var1.drawLine(10, var3 - 29, var2 - 10, var3 - 29);
		var1.drawString(R.string("cpu_address_bus"), 15, var3 - 40);
		if (!"".equals(this.adressWert)) {
			var1.drawString(this.adressWert, this.breiteCpu - 10, var3 - 40);
		}

		var1.drawLine(190, var3 - 30, 190, var3 / 2 + this.höheCpu / 2 + 1);
		var1.drawLine(191, var3 - 30, 191, var3 / 2 + this.höheCpu / 2 + 1);
		var1.drawLine(var2 - 90, var3 - 30, var2 - 90, var3 / 2 + this.höheSpeicher / 2 + 1);
		var1.drawLine(var2 - 89, var3 - 30, var2 - 89, var3 / 2 + this.höheSpeicher / 2 + 1);
		var1.drawString(R.string("cpu_accumulator"), 25, var3 / 2 - this.höheCpu / 2 + this.höheKasten);
		var1.drawString(R.string("cpu_status"), 25, var3 / 2 + this.höheCpu / 2 - (this.höheKasten + 20));
		var1.drawString(R.string("cpu_instruction_reg"), this.breiteCpu - 2 * this.breiteKasten + 5, var3 / 2 - this.höheCpu / 2 + this.höheKasten);
		var1.drawString(R.string("cpu_program_counter"), this.breiteCpu - 2 * this.breiteKasten + 5, var3 / 2 - this.höheCpu / 2 + 3 * this.höheKasten + 20);
		if (this.erweitert) {
			var1.drawString(R.string("cpu_stack_pointer"), this.breiteCpu - 2 * this.breiteKasten + 5, var3 / 2 - this.höheCpu / 2 + 5 * this.höheKasten + 40);
		}

		for(var5 = 0; var5 < this.progmem.length; ++var5) {
			var1.drawString(this.progadr[var5], var2 - (this.breiteSpeicher + 5), var3 / 2 - this.höheSpeicher / 2 + this.höheKasten + var5 * this.höheKasten);
		}

		if (this.erweitert) {
			for(var5 = 0; var5 < this.datamem.length; ++var5) {
				var1.drawString(this.dataadr[var5], var2 - (this.breiteSpeicher + 5), var3 / 2 + this.höheKasten * (this.progmem.length - this.stackmem.length) / 2 - this.höheKasten * this.datamem.length / 2 + this.höheKasten - 5 + var5 * this.höheKasten);
			}

			for(var5 = 0; var5 < this.stackmem.length; ++var5) {
				var1.drawString(this.stackadr[var5], var2 - (this.breiteSpeicher + 5), var3 / 2 + this.höheSpeicher / 2 - this.höheKasten * this.stackmem.length + this.höheKasten - 10 + var5 * this.höheKasten);
			}
		} else {
			for(var5 = 0; var5 < this.datamem.length; ++var5) {
				var1.drawString(this.dataadr[var5], var2 - (this.breiteSpeicher + 5), var3 / 2 + this.höheSpeicher / 2 - this.höheKasten * this.datamem.length + this.höheKasten - 10 + var5 * this.höheKasten);
			}
		}

		var1.setColor(this.getForeground());
	}
}
