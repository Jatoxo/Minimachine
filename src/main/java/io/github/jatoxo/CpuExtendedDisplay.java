package io.github.jatoxo;//


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


class CpuExtendedDisplay extends CpuDisplay {
	private final CpuExtendedDisplayPane cpuExtendedDisplayPane;


	CpuExtendedDisplay(ControllerInterface controller) {
		super(controller);

		cpuExtendedDisplayPane = (CpuExtendedDisplayPane) contentPane;

		setSize(new Dimension(getSize().width, getSize().height + 20));
	}

	public JPanel getContent() {
		//JPanel pane = super.getContentPane();

		if(contentPane == null) {
			contentPane = new CpuExtendedDisplayPane(controller);
		}

		return contentPane;
	}

	@Override
	protected void setIncreaseSize(boolean increased) {
		cpuExtendedDisplayPane.setIncreaseSize(increased);
		setSize(increased ? new Dimension(900, 375) : new Dimension(600, 250));
	}

	public void Befehlsmeldung(String data, String address, String alu1, String alu2, String alu3, String accumulator, String stackPointer, boolean zFlag, boolean nFlag, boolean vFlag, String opMnemonic, String addr, String programCounter, String[] progAddr, String[] progMem, String[] dataAddr, String[] dataMem, String[] stackAddr, String[] stackMem, String microStepName) {
		cpuExtendedDisplayPane.Befehlsmeldung(data, address, alu1, alu2, alu3, accumulator, stackPointer, zFlag, nFlag, vFlag, opMnemonic, addr, programCounter, progAddr, progMem, dataAddr, dataMem, stackAddr, stackMem, microStepName);
	}


	public static class CpuExtendedDisplayPane extends CpuDisplayPane {
		private final JLabel labelSP;
		private final JLabel labelAdr4;
		private final JLabel labelAdr5;
		private final JLabel labelMem4;
		private final JLabel labelMem5;
		private final JLabel spLabel;

		public CpuExtendedDisplayPane(ControllerInterface controller) {
			super(controller);

			ausfuehrenButton.setLocation(100, 190);
			einzelButton.setLocation(350, 190);
			registerpanel.setSize(200, 140);

			spLabel = new JLabel();
			spLabel.setText("SP:");
			spLabel.setSize(40, 20);
			spLabel.setLocation(20, 110);
			registerpanel.add(spLabel);

			labelSP = new JLabel();
			labelSP.setText("");
			labelSP.setBorder(LineBorder.createGrayLineBorder());
			labelSP.setSize(60, 20);
			labelSP.setLocation(60, 110);
			registerpanel.add(labelSP);

			speicherpanel.setSize(200, 160);

			labelAdr4 = new JLabel();
			labelAdr4.setText("65534");
			labelAdr4.setSize(80, 20);
			labelAdr4.setLocation(20, 100);
			speicherpanel.add(labelAdr4);

			labelMem4 = new JLabel();
			labelMem4.setText("");
			labelMem4.setBorder(LineBorder.createGrayLineBorder());
			labelMem4.setSize(80, 21);
			labelMem4.setLocation(100, 100);
			speicherpanel.add(labelMem4);

			labelAdr5 = new JLabel();
			labelAdr5.setText("65535");
			labelAdr5.setSize(80, 20);
			labelAdr5.setLocation(20, 120);
			speicherpanel.add(labelAdr5);

			labelMem5 = new JLabel();
			labelMem5.setText("");
			labelMem5.setBorder(LineBorder.createGrayLineBorder());
			labelMem5.setSize(80, 20);
			labelMem5.setLocation(100, 120);
			speicherpanel.add(labelMem5);
		}

		@Override
		public void Befehlsmeldung(String data, String address, String alu1, String alu2, String alu3, String accumulator, String stackPointer, boolean zFlag, boolean nFlag, boolean vFlag, String opMnemonic, String addr, String programCounter, String[] progAddr, String[] progMem, String[] dataAddr, String[] dataMem, String[] stackAddr, String[] stackMem, String microStepName) {
			super.Befehlsmeldung(data, address, alu1, alu2, alu3, accumulator, stackPointer, zFlag, nFlag, vFlag, opMnemonic, addr, programCounter, progAddr, progMem, dataAddr, dataMem, stackAddr, stackMem, microStepName);
			this.labelSP.setText(stackPointer);
			this.labelAdr4.setText(stackAddr[0]);
			this.labelAdr5.setText(stackAddr[1]);
			this.labelMem4.setText(stackMem[0]);
			this.labelMem5.setText(stackMem[1]);
		}

		@Override
		protected void setIncreaseSize(boolean increased) {
			super.setIncreaseSize(increased);

			if(increased) {
				registerpanel.setSize(300, 210);
				registerpanel.setLocation(15, 30);
				spLabel.setFont(bigFont);
				spLabel.setSize(60, 30);
				spLabel.setLocation(30, 165);
				labelSP.setFont(bigFont);
				labelSP.setSize(90, 30);
				labelSP.setLocation(90, 165);
				speicherpanel.setSize(300, 235);
				speicherpanel.setLocation(555, 30);
				labelAdr4.setFont(bigFont);
				labelAdr4.setSize(120, 30);
				labelAdr4.setLocation(30, 150);
				labelMem4.setFont(bigFont);
				labelMem4.setSize(120, 31);
				labelMem4.setLocation(150, 150);
				labelAdr5.setFont(bigFont);
				labelAdr5.setSize(120, 30);
				labelAdr5.setLocation(30, 180);
				labelMem5.setFont(bigFont);
				labelMem5.setSize(120, 30);
				labelMem5.setLocation(150, 180);
				ausfuehrenButton.setLocation(150, 280);
				einzelButton.setLocation(525, 280);
			} else {
				this.registerpanel.setSize(200, 140);
				this.registerpanel.setLocation(10, 20);
				this.spLabel.setFont(this.defFont);
				this.spLabel.setSize(40, 20);
				this.spLabel.setLocation(20, 110);
				this.labelSP.setFont(this.defFont);
				this.labelSP.setSize(60, 20);
				this.labelSP.setLocation(60, 110);
				this.speicherpanel.setSize(200, 160);
				this.speicherpanel.setLocation(370, 20);
				this.labelAdr4.setFont(this.defFont);
				this.labelAdr4.setSize(80, 20);
				this.labelAdr4.setLocation(20, 100);
				this.labelMem4.setFont(this.defFont);
				this.labelMem4.setSize(80, 21);
				this.labelMem4.setLocation(100, 100);
				this.labelAdr5.setFont(this.defFont);
				this.labelAdr5.setSize(80, 20);
				this.labelAdr5.setLocation(20, 120);
				this.labelMem5.setFont(this.defFont);
				this.labelMem5.setSize(80, 20);
				this.labelMem5.setLocation(100, 120);
				this.ausfuehrenButton.setLocation(100, 190);
				this.einzelButton.setLocation(350, 190);
			}

		}

	}
}