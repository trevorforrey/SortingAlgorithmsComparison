

import javax.swing.*;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Visualization and Comparison of Sorting Algorithms
public class Main extends JApplet {

	private static final long serialVersionUID = 1L;
	// Creates array for each sorting algorithm panel
	private SortPanel[] sortPanels = new SortPanel[5];
	// Sets size of the list that will be ordered
	private static int size = 100;
	private int sleepTime = 2;
	private String animationName = "";

	// Main Constructor
	public Main() {
		// Creates layout for the sort panels to go into
		setLayout(new GridLayout(1, 1, 0, 0));
		// Sets values for panels that will hold sorting algorithm animations
		SortPanelsHolder sortPanelHolder = new SortPanelsHolder();
		sortPanelHolder.setLayout(new  GridLayout(0, 3, 0, 0));
		sortPanelHolder.setBackground(Color.BLACK);
		sortPanelHolder.setForeground(Color.BLACK);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width / 3;
		int height = screenSize.height / 3;
		// Puts every sorting panel into the array of panels that will be displayed
		sortPanels[0] = new SelectionSortPanel(" Selection Sort ", sleepTime, width, height);
		sortPanels[1] = new MergeSortPanel(" Merge Sort ", sleepTime, width, height);
		sortPanels[2] = new QuickSortPanel(" Quick Sort ", sleepTime, width, height);
		sortPanels[3] = new HeapSortPanel(" Heap Sort ", sleepTime, width, height);
		sortPanels[4] = new BubbleSortPanel(" Bubble Sort ", sleepTime, width, height);
		

		// adds every panel in the array to the sort panel holder and sets their visibility to false
		for (int i = 0; i < sortPanels.length; i++) {
			sortPanels[i].setVisible(false);
			sortPanelHolder.add(sortPanels[i]);				
		}
		// adds sort panel holder to the JApplet Main
		add(sortPanelHolder);
	}
	
	// Sorts Panels Holder Class
	class SortPanelsHolder extends JPanel {
		private static final long serialVersionUID = 1L;

		// Paints the Sort Panels
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.WHITE);
			Font animationNameFont = new Font(Font.MONOSPACED, Font.BOLD, 150);
			FontMetrics animationNameFontFontMetrix = getFontMetrics(animationNameFont);
			g.setFont(animationNameFont);
			int x = (getWidth() - animationNameFontFontMetrix.stringWidth(animationName)) / 2;
			int y = (getHeight() - animationNameFontFontMetrix.getLeading()) / 2;
			g.drawString(animationName, x, y);
		}
	}
	
	// Method that starts a series of animations (E.g. "random", "reversed")
	public void beginAnimation(String animationName, int[] list) {
		try {
			
			this.animationName = animationName;
			// Prints title screen
			repaint();
			// Pauses the thread for 2000 miliseconds
			Thread.sleep(2000);

			// removes title screen 
			this.animationName = "";
			repaint();

			// Put lists in each sort panel and make the panels visible
			for (int i = 0; i < sortPanels.length; i++) {
				sortPanels[i].setList(list);
				sortPanels[i].setVisible(true);
			}
			// Pauses the thread for 1000 miliseconds
			Thread.sleep(1000);

			// Executes each sort panel
			ExecutorService executor = Executors.newFixedThreadPool(sortPanels.length);
			for (int i = 0; i < sortPanels.length; i++) {
				executor.execute(sortPanels[i]);
			}
			executor.shutdown();

			// While the thread is still running, pause it for 100 miliseconds
			while(!executor.isTerminated()) {
				Thread.sleep(100);
			}
			// Pauses the thread for 1000 miliseconds
			Thread.sleep(1000);

			// Hides all sorting panels (gets ready for the next type of data to sort)
			for (int i = 0; i < sortPanels.length; i++) {
				sortPanels[i].setVisible(false);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Program that runs the Main class
	public static void main(String[] args) {

		// Sets up frame that will hold all Sorting Panels
		JFrame frame = new JFrame("Sorting Algorithm Animations");
		Main main = new Main();
		frame.add(main);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// Initializes the size of the array that will be sorted
		int[] list = new int[size];
		
		// Creates a list 1 - 99
		for (int i = 0; i < list.length; i++) {
			list[i] = i + 1;
		}


		// RANDOM LIST
		// Shuffles the numbers in the array
		for (int i = 0; i < list.length; i++) {
			int index = (int) (Math.random() * list.length);
			int temp = list[i];
			list[i] = list[index];
			list[index] = temp;
		}
		main.beginAnimation("Random", list);
		

		// UNIQUE LIST
		for (int i = 0; i < list.length; i++) {
			list[i] = (1 + i / (size / 4) ) * (size / 4);
		}
		for (int i = 0; i < list.length; i++) {
			int index = (int) (Math.random() * list.length);
			int temp = list[i];
			list[i] = list[index];
			list[index] = temp;
		}
		// Starts animation of sort panels going through a unique list
		main.beginAnimation("Few Unique", list);

		

		// REVERSED LIST
		for (int i = 0; i < list.length; i++) {
			list[i] = size - i;
		}
		// Starts animation of sort panels going through a reversed list
		main.beginAnimation("Reversed", list);
		
		
		// ALMOST SORTED LIST
		for (int i = 0; i < list.length / 2; i++) {
			list[i] = i + 1;
		}
		for (int i = list.length / 2; i < list.length; i++) {
			list[i] = i + 2;
		}
		list[list.length - 1] = list.length / 2 + 1;
		// Starts animation of sort panels going through an almost sorted list
		main.beginAnimation("Almost Sorted", list);
	}
}