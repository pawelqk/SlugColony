package pl.pawelek;

import javax.swing.JFrame;

public class CFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public CFrame() {
		super("Slugs");
		setBounds(10,10,600,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		addComponents();
	}
	
	public void addComponents() {
		CPanel panel = new CPanel(500,400);
		add(panel);
	}
	
}
