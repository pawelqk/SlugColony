package pl.pawelek;

public class CSlug implements Runnable {
	
	public CSlug(CPanel panel, CLeaf occupiedLeaf, CLeaf [][] leaves) {
		this.panel = panel;
		this.occupiedLeaf = occupiedLeaf;
		this.leaves = leaves;
	}
	
	public CLeaf getOccupiedLeaf() {
		return occupiedLeaf;
	}
	
	public boolean move() {
		boolean moved = true;
		int currentColumn = occupiedLeaf.getColumn();
		int currentRow = occupiedLeaf.getRow();
		
		CLeaf nextLeaf = occupiedLeaf;
		int biggestAmount = 0;
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if ((currentRow + i < 25) && (currentRow + i >= 0) && (currentColumn + j < 20) && (currentColumn + j) >= 0) {
					if (leaves[currentRow + i][currentColumn + j].getAmount() > biggestAmount) {
						biggestAmount = leaves[currentRow + i][currentColumn + j].getAmount();
						nextLeaf = leaves[currentRow + i][currentColumn + j];
						moved = true;
					}
				}
			}
		}
		occupiedLeaf = nextLeaf;
		return moved;
	}

	@Override
	public void run() {
		while(!dead) {
			synchronized(panel) {
				while (occupiedLeaf.getAmount() > 0) {
					try {
						Thread.sleep(40 - eating_speed);
					} catch (InterruptedException e) {
						System.err.println("SLUG RUN exc");
					}
					occupiedLeaf.setAmount(occupiedLeaf.getAmount() - 1);
					while (occupiedLeaf.getIncremented()) {
						try {
							panel.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			occupiedLeaf.setEaten(true);
			panel.repaint();
			while(!move()) {
				try {
					Thread.sleep(500 - eating_speed);
				} catch (InterruptedException e) {
					System.err.println("SLUG RUN exc");
				}
			}	
			panel.repaint();
		}
	}
	private CPanel panel;
	private int eating_speed = 30;
	private CLeaf occupiedLeaf;
	private CLeaf [][] leaves;
	private boolean dead = false;
}
