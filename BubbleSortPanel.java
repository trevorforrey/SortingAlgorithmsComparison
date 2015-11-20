
import java.io.*;
import java.awt.Color;
import java.awt.Graphics;

public class BubbleSortPanel extends SortPanel {
	private static final long serialVersionUID = 1L;
	private int redColumn = -1;
	private int greenColumn = -1;

	//For timing how long the algorithm takes
	private long startTime;
	private long estimatedTime;
	private double estimatedTimeInSeconds;

	// For writing to the time log file
	private FileWriter file;
	private PrintWriter writer;
	
	public BubbleSortPanel(String name, int sleepTime, int width, int height) {
		super(name, sleepTime, width, height);

		try {
			file = new FileWriter("TimeLogs/bubbleTimes.txt");
			writer = new PrintWriter(file);
		} catch (IOException e) {
			System.out.println("ERROR");
		}

	}

	@Override
	public void reset() {
		redColumn = -1;
		greenColumn = -1;		
	}

	@Override
	public void run() {
		try {

//******
			startTime = System.nanoTime();

			boolean needNextPass = true;
			for (int k = 1; k < list.length && needNextPass; k++) {
				needNextPass = false;
				for (int i = 0; i < list.length - k; i++) {
					redColumn = i;
					repaint();
					Thread.sleep(4 * sleepTime);
					if (list[i] > list[i + 1]) {
						redColumn = i + 1;
						int temp = list[i];
						list[i] = list[i + 1];
						list[i + 1] = temp;
						repaint();
						Thread.sleep(4 * sleepTime);
						needNextPass = true;
					}
				}
				greenColumn = size - k;
			}
			greenColumn = 0;
			redColumn = -1;
//******
			estimatedTime = System.nanoTime() - startTime;
			estimatedTimeInSeconds = (double)estimatedTime / 1000000000;
			System.out.print("\nTime in seconds for Bubble: " + estimatedTimeInSeconds);
			writer.println("Bubble time: " + estimatedTimeInSeconds);
			writer.flush();
			startTime = System.nanoTime();
		} catch (InterruptedException e) {
		}
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int columnWidth = (getWidth() - 4 * BORDER_WIDTH) / size;
		int columnHeight = (getHeight() - 4 * BORDER_WIDTH) / size;
		for (int i = 0; i < (greenColumn == -1 ? list.length : greenColumn); i++) {
			g.setColor(Color.WHITE);
			g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
			g.setColor(Color.BLACK);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);			
		}
		if(greenColumn != -1) {
			for (int i = greenColumn; i < list.length; i++) {
				g.setColor(Color.GREEN);
				g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
				g.setColor(Color.BLACK);
				g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);			
			}
		}
		if(redColumn != -1) {
			g.setColor(Color.RED);
			g.fillRect(2 * BORDER_WIDTH + columnWidth * redColumn, getHeight() - list[redColumn] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[redColumn] * columnHeight);
			g.setColor(Color.BLACK);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * redColumn, getHeight() - list[redColumn] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[redColumn] * columnHeight);
		}
	}

}
