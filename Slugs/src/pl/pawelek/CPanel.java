package pl.pawelek;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CPanel extends JPanel {
	public CPanel(int width, int height) {
		this.width = width;
		this.height = height;
		setBounds((int)xcoor, (int)ycoor, width, height);
		setBackground(Color.WHITE);
		addComponents();
		startColony();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		for (int i = 0; i < width / leaf_size; i++) {
			for (int j = 0; j < height / leaf_size; j++) {
				 g2d.setColor(Color.GREEN);
				 leaf = new Rectangle2D.Double(xcoor + leaf_size*i, ycoor +leaf_size*j, leaf_size, leaf_size);
				 if(leaves[i][j].getEaten())
					 g2d.setColor(new Color(162, 110, 69, 255));
				 else if(leaves[i][j].getAmount() < 4)
					 g2d.setColor(new Color(153, 153, 0, 255));
				 g2d.draw(leaf);
				 g2d.fill(leaf);
			}
		}
		g2d.setColor(Color.BLACK);
		for(CSlug s : slugs) {
			slug_img = new Ellipse2D.Double(xcoor + s.getOccupiedLeaf().getRow()*leaf_size + slug_coor, ycoor + s.getOccupiedLeaf().getColumn()*leaf_size + slug_coor, 5, 5);
			g2d.draw(slug_img);
			g2d.fill(slug_img);
		}
	}
	
	public void addComponents() {
		leaves = new CLeaf[width/leaf_size][height/leaf_size];
		for(int i = 0; i < width/leaf_size; i++) {
			for(int j = 0; j < height/leaf_size; j++) {
				leaves[i][j] = new CLeaf(i, j);
			}
		}
				
		slugs = new ArrayList<CSlug>(amount_of_slugs);
		
		Random random = new Random();
		for(int i = 0; i < amount_of_slugs; i++)
			slugs.add(i, new CSlug(this, leaves[random.nextInt(width/leaf_size)][random.nextInt(height/leaf_size)], leaves));
	}
	
	public void startColony() {
		for(CSlug s : slugs) {
			(new Thread(s)).start();
		}
		(new Thread(new Incrementer())).start();
	}
	
	private static final int leaf_size = 20;
	private static final int slug_coor = 7;
	private final int width;
	private final int height;
	private double xcoor = 50;
	private double ycoor = 50;
	private CLeaf [][] leaves;
	private ArrayList<CSlug> slugs;
	private Rectangle2D leaf;
	private Ellipse2D slug_img;
	private int amount_of_slugs = 20;
	
	class Incrementer implements Runnable {
		public void run() {
			while(true) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					System.err.println("incrementer run()");
				}
				synchronized (CPanel.this) {
					for (int i = 0; i < width / leaf_size; i++) {
						for (int j = 0; j < height / leaf_size; j++) {
							leaves[i][j].setIncremented(true);
							leaves[i][j].setAmount(leaves[i][j].getAmount() + 1);
							leaves[i][j].setEaten(false);
							leaves[i][j].setIncremented(false);
							CPanel.this.notifyAll();
							repaint();
						}
					}
				}
			}
		}
	}
}
