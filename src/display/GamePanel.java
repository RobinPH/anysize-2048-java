package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.JPanel;

import component.Cell;
import game.GameState;

public class GamePanel extends JPanel implements KeyListener {
	private static final long serialVersionUID = 6220134133434701595L;
	private final GameState state;
	private final int width;
	private final int height;
	private final int cellSize;
	private final int borderSize;
	private final int borderRadius;
	private final int scoreboardHeight = 100;
	private Graphics2D graphics;
	
	public GamePanel(GameState state) {
		this.state = state;
		this.width = this.state.getWidth();
		this.height = this.state.getHeight();
		this.cellSize = this.state.getCellSize();
		this.borderSize = (int) (this.state.getBorderSize() * ((float) cellSize / 100));
		this.borderRadius = (int) (this.state.getBorderRadius() * ((float) cellSize / 100));
	}
	
	public JPanel getJPanel() {
		setPreferredSize(new Dimension(width * cellSize + (width + 1) * borderSize,
									   height * cellSize + (height + 1) * borderSize + scoreboardHeight));
		setFocusable(true);
        requestFocus(); 
        
        this.addKeyListener(this);
        
		return this;
	}
	
	public void render() {
		this.graphics = (Graphics2D) getGraphics();
		this.setUpFont();
		
		drawBackground();
		drawScoreboard();
		
		for (Cell cell : this.state.getCells().values())
			drawCell(cell);
	}
	
	public void rerender() {
		drawBackground();
		drawScoreboard();
		
		for (Cell cell : this.state.getCells().values())
			drawCell(cell);
		
		drawGameOverScreen();
	}
	
	private void drawCell(Cell cell) {
		Map<String, Integer> position = cell.getPosition();
		int x = position.get("x");
		int y = position.get("y");
		drawCell(cell, x, y);
	}
	
	private void drawCell(Cell cell, int x, int y) {
		x *= (cellSize + borderSize);
		y *= (cellSize + borderSize);
		
		graphics.setColor(cell.getColor());
		graphics.fillRoundRect(x + borderSize,
						  y + borderSize + scoreboardHeight,
						  cellSize,
						  cellSize,
						  borderRadius,
						  borderRadius);
		
		if (cell.getValue() == 0) return;
		
		FontMetrics fm = graphics.getFontMetrics();
		String cellValue = cell.getDisplayValue();
		int stringWidth = fm.stringWidth(cellValue);
		int stringHeight = fm.getHeight();
	
		graphics.setColor(cell.getFontColor());
		graphics.drawString(cellValue,
							x + borderSize + cellSize / 2 - stringWidth / 2,
							y + borderSize +  cellSize / 2 + stringHeight / 4 + scoreboardHeight);
	}
	
	private void drawBackground() {
		graphics.setColor(Color.decode("#B9AA9D"));
		graphics.fillRect(0,
						  0,
						  width * cellSize + (width + 1) * borderSize,
						  height * cellSize + (height + 1) * borderSize + scoreboardHeight);
	}
	
	private void drawScoreboard() {
		graphics.setColor(Color.decode("#B9AA9D"));
		graphics.fillRect(0,
						  0,
						  width * cellSize + (width + 1) * borderSize,
						  scoreboardHeight);
		
		graphics.setColor(Color.decode(Cell.color[0]));
		graphics.fillRoundRect(borderSize,
							   borderSize,
							   width * cellSize + (width - 1) * borderSize,
							   cellSize - borderSize,
							   borderRadius,
							   borderRadius);
		
		FontMetrics fm = graphics.getFontMetrics();
		String score = Integer.toString(this.state.getScore());
		int stringWidth = fm.stringWidth((score));
		int stringHeight = fm.getHeight();
		
		graphics.setColor(Color.decode(Cell.fontColor[3]));

		graphics.drawString(score,
						   (width * cellSize + (width + 1) * borderSize) / 2 - stringWidth / 2,
						   scoreboardHeight / 2 + stringHeight / 3);
	}
	
	private void drawGameOverScreen() {
		if (this.state.isGameDone()) {
			FontMetrics fm = graphics.getFontMetrics();
			String gameOver = "Game Over";
			int stringWidth = fm.stringWidth((gameOver));
			int stringHeight = fm.getHeight();
			
			graphics.setColor(new Color(0f, 0f, 0f, 0.3f));
			graphics.fillRect(0,
							  0,
							  width * cellSize + (width + 1) * borderSize,
							  height * cellSize + (height + 1) * borderSize + scoreboardHeight);
			
			graphics.setColor(Color.decode(Cell.fontColor[3]));

			graphics.drawString(gameOver,
							   (width * cellSize + (width + 1) * borderSize) / 2 - stringWidth / 2,
							   (height * cellSize + (height + 1) * borderSize) / 2 + scoreboardHeight + stringHeight / 4);
		}
	}
	
	private void setUpFont() {
		try {
		    Font clearSans = Font.createFont(Font.TRUETYPE_FONT, new File("font/ClearSans-Bold.ttf")).deriveFont(48f * ((float) cellSize / 100));
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(clearSans);
		    
		    this.graphics.setFont(clearSans);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
	}

	public GameState getState() {
		return this.state;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 40:
		case 83:
			this.state.PlayerMove(0, 1, true);
			rerender();
			return;
		case 39:
		case 68:
			this.state.PlayerMove(1, 0, true);
			rerender();
			return;
		case 38:
		case 87:
			this.state.PlayerMove(0, -1, true);
			rerender();
			return;
		case 37:
		case 65:
			this.state.PlayerMove(-1, 0, true);
			rerender();
			return;
		case 32:
		case 82:
		case 10:
			this.state.restart();
			rerender();
			return;
		default:
			return;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
