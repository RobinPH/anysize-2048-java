package game;

import java.util.HashMap;
import java.util.Map;

import component.Cell;

public class GameState extends GameConfig {
	
	private Map<String, Cell> cells;
	private int occupiedCells;
	private int score = 0;
	
	public GameState(int width, int height, int cellSize) {
		super(width, height, cellSize);
		generateStartingState();
	}
	
	public GameState() {
		super();
		generateStartingState();
	}

	public boolean PlayerMove(int xDirection, int yDirection, boolean recorded) {
		for (int i = yDirection < 0 ? 0 : this.getHeight() - 1;
			 yDirection < 0 ? i < this.getHeight() : i >= 0;
			 i += yDirection < 0 ? 1 : -1) {
			
			for (int j = xDirection < 0 ? 0 : this.getWidth() - 1;
				 xDirection < 0 ? j < this.getWidth() : j >= 0;
				 j += xDirection < 0 ? 1 : -1) {
				
				Cell checkingCell = this.getCell(j, i);
				if (!checkingCell.isEmpty()) {
					boolean possibleMoveExists = CellMove(checkingCell, xDirection, yDirection, recorded);
					if (!recorded && possibleMoveExists) {
						return true;
					}
				}
				
			}
		}
		
		if (recorded) spawnCell();
		return false;
	}
	
	public boolean isGameDone() {
		if (countOccupiedCell() < this.getWidth() * this.getHeight()) return false;
		
		if (!(PlayerMove(1, 0, false)
			|| PlayerMove(-1, 0, false)
			|| PlayerMove(0, 1, false)
			|| PlayerMove(0, -1, false))) return true;
		
		return false;
	}
	
	public void restart() {
		generateStartingState();
		this.score = 0;
	}
	
	private boolean CellMove(Cell c1, int xDirection, int yDirection, boolean recorded) {
		Cell c2 = this.getCell(c1.getNeighborPosition(xDirection, yDirection));
		
		if (c2 == null) return false;
		
		if (c2.isEmpty()) {
			if (recorded) {
				Cell.swap(c1, c2);
			} else {
				return true;
			}
		} else if (c1.getValue() == c2.getValue()) {
			if (recorded) {
				c1.mergeTo(c2);
				this.score += c2.getValue() == 0 ? 0 : Math.pow(2, c2.getValue());				
			} else {
				return true;				
			}
		} else if (!recorded) {
			return false;
		}
		
		return CellMove(c2, xDirection, yDirection, recorded);
	}
	
	private void spawnCell() {
		if (countOccupiedCell() >= this.getWidth() * this.getHeight()) return;
		
		int x = (int) (Math.random() * this.getWidth());
		int y = (int) (Math.random() * this.getHeight());
		
		Cell cell = this.getCell(x, y);
		if (cell.isEmpty()) {
			cell.increaseLevel();
			if (Math.random() > 0.9) cell.increaseLevel();
			return;
		}			
		spawnCell();
	}
	
	private int countOccupiedCell() {
		this.occupiedCells = 0;
		this.cells.values().forEach((cell) -> {
			if (!cell.isEmpty()) occupiedCells++;
		});
		return this.occupiedCells;
	}
	
	// Debug
	@SuppressWarnings("unused")
	private void printCells() {
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				System.out.print(this.getCell(j, i).getDisplayValue() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private void generateStartingState() {
		Map<String, Cell> cells = new HashMap<>();

		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				cells.put(GameState.positionToKey(j, i), new Cell(j, i, 0));
			}
		}
		
		this.cells = cells;
		
		for (int i = 0; i < this.getStartingCell(); i++) spawnCell();
	}
	
	public static String positionToKey(int x, int y) {
		return Integer.toString(x) + "-" + Integer.toString(y);
	}
	
	public Cell getCell(Map<String, Integer> position) {
		return getCell(position.get("x"), position.get("y"));
	}

	public Cell getCell(int x, int y) {
		return this.cells.get(positionToKey(x, y));
	}
	
	public int getScore() {
		return this.score;
	}
	
	public Map<String, Cell> getCells() {
		return this.cells;
	}
	
	public void setCells(Map<String, Cell> cells) {
		this.cells = cells;
	}
}
