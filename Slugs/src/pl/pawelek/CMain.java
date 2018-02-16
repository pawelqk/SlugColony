package pl.pawelek;

import javax.swing.SwingUtilities;

public class CMain {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CFrame();
			}
		});
	}
}
