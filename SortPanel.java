

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

// Class that holds the seperate "graphs" of each sorting algorithm
public abstract class SortPanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	protected static final int BORDER_WIDTH = 10;
	private Dimension prefferedDimension;
	protected int size;
	protected int[] list;
	protected int sleepTime;
	private String name;
	
	// Constructor
	public SortPanel(String name, int sleepTime, int width, int height) {
		prefferedDimension = new Dimension(width, height);
		this.name = name;
		this.sleepTime = sleepTime;
		setBackground(Color.BLACK);
	}
	
	// Sets the list that will be sorted to the sorting panel
	public void setList(int[] list) {
		reset();
		this.size = list.length;
		this.list = java.util.Arrays.copyOf(list, size);
		setBackground(Color.BLACK);
	}
	
	// Gets size to make the panel
	@Override
	public Dimension getPreferredSize() {
		return prefferedDimension;
	}
	
	// Paints a graphic object to the screen
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//draw border
		g.setColor(Color.WHITE);
		g.drawRect(BORDER_WIDTH, BORDER_WIDTH, getWidth() - 2 * BORDER_WIDTH, getHeight() - 2 * BORDER_WIDTH);
		
		//draws rectangles
		Font nameFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
		FontMetrics nameFontMetrix = getFontMetrics(nameFont);		
		g.setColor(Color.BLACK);
		g.fillRect((getWidth() - nameFontMetrix.stringWidth(name)) / 2, 0, nameFontMetrix.stringWidth(name), BORDER_WIDTH + nameFontMetrix.getAscent() / 3);
		g.setColor(Color.WHITE);
		g.setFont(nameFont);
		g.drawString(name, (getWidth() - nameFontMetrix.stringWidth(name)) / 2, BORDER_WIDTH + nameFontMetrix.getAscent() / 3);

	}

	@Override
	public abstract void run();

	public abstract void reset();

}
