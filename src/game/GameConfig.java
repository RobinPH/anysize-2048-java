package game;

public class GameConfig {
	private int width = 4;
	private int height = 4;
	private int cellSize = 100;
	private int borderSize = 10;
	private int borderRadius = 5;
	private int startingCell = 2;
	
	public GameConfig(int width, int height, int cellSize) {
		this.width = width;
		this.height = height;
		this.cellSize = cellSize;
	}
	
	public GameConfig() {}
	
	public int getWidth() {
		return this.width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getCellSize() {
		return this.cellSize;
	}
	
	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

	public int getBorderSize() {
		return this.borderSize;
	}

	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}

	public int getBorderRadius() {
		return this.borderRadius;
	}

	public void setBorderRadius(int borderRadius) {
		this.borderRadius = borderRadius;
	}

	public int getStartingCell() {
		if (this.startingCell > getWidth() * getHeight())
			this.setStartingCell(Math.min(this.startingCell, getWidth() * getHeight()));			

		return this.startingCell;
	}

	public void setStartingCell(int startingCell) {
		this.startingCell = startingCell;
	}
}
