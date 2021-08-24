//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Font;
import java.awt.Graphics;

class CpuBildGroß extends CpuBild {
	CpuBildGroß() {
		this.breiteCpu = 570;
		this.höheCpu = 240;
		this.breiteSpeicher = 240;
		this.höheSpeicher = 370;
		this.minBreite = this.breiteCpu + this.breiteSpeicher + 40;
		this.minHöhe = this.höheSpeicher + 120;
		this.breiteKasten = 120;
		this.höheKasten = 30;
		this.xKoordinaten = new int[]{0, 60, 120, 180, 120, 90, 60};
		this.yKoordinaten = new int[]{0, 90, 90, 0, 0, 45, 0};
	}

	protected void paintComponent(Graphics var1) {
		var1.setFont(new Font(var1.getFont().getName(), var1.getFont().getStyle(), 24));
		super.paintComponent(var1);
	}
}
