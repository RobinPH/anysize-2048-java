package component;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Cell {
	private int value;
	private int x;
	private int y;
	
	public static final String[] color = new String[] {
			"#CDC0B4",
			"#EEE4DA",
			"#EDE0C8",
			"#F2B179",
			"#F59563",
			"#F67C5F",
			"#FE5C43",
			"#FAD177",
			"#F7D067",
			"#F6CC54",
			"#FBC52D",
			"#F36774",
			"#F14B61",
			"#EB423B",
			"#76B7DD",
			"#5EA1E5",
			"#007FC2",
	};
	
	public static final String[] fontColor = new String[] {
			null,
			"#776E65",
			"#776E65",
			"#F9F6F2",
	};
	
	public Cell(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	public static void swap(Cell c1, Cell c2) {
		int c1Value = c1.value;	
		c1.value = c2.value;
		c2.value = c1Value;
	}
	
	public void mergeTo(Cell cell) {
		this.setToEmpty();
		cell.increaseLevel();
	}
	
	public Map<String, Integer> getNeighborPosition(int xDirection, int yDirection) {
		Map<String, Integer> position = new HashMap<>();
		
		position.put("x", this.x + xDirection);
		position.put("y", this.y + yDirection);
		
		return position; 
	}
	
	public Map<String, Integer> getPosition() {
		Map<String, Integer> position = new HashMap<>();
		
		position.put("x", this.x);
		position.put("y", this.y);
		
		return position;
	}
	
	public void setToEmpty() {
		this.setValue(0);
	}
	
	public Color getColor() {
		try {
			return Color.decode(Cell.color[this.getValue()]);
		} catch (Exception e) {
			return Color.decode("#443737");
		}
	}
	
	public Color getFontColor() {
		try {
			return Color.decode(Cell.fontColor[this.getValue()]);
		} catch (Exception e) {
			return Color.decode("#F9F6F2");
		}
	}

	public void increaseLevel() {
		this.value++;
	}
	
	public boolean isEmpty() {
		return this.value == 0;
	}
	
	public String getDisplayValue() {
		return Integer.toString((int) Math.pow(2, this.value));
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Cell [value=" + value + ", x=" + x + ", y=" + y + "]";
	}
}
