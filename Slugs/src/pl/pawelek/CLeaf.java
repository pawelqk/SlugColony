package pl.pawelek;

import java.util.Random;

public class CLeaf {
	public CLeaf(int row, int column) {
		this.setRow(row);
		this.setColumn(column);
		Random random = new Random();
		setAmount(random.nextInt(10) + 1);
	}
	
	public boolean getIncremented() {
		return incremented;
	}
	
	public void setIncremented(boolean incremented) {
		this.incremented = incremented;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public boolean getEaten() {
		return eaten;
	}
	
	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	private boolean eaten = false;
	private int row;
	private int column;
	private boolean incremented;
	private int amount;
}
