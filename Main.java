

import javax.swing.*;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.*;
import java.io.*;

//Visualization and Comparison of Sorting Algorithms
public class Main extends JApplet {

	private FileWriter file;
	private PrintWriter writer;

	private static final long serialVersionUID = 1L;
	// Creates array for each sorting algorithm panel
	private SortPanel[] sortPanels = new SortPanel[5];
	// Sets size of the list that will be ordered
	private static int size = 60;
	private int sleepTime = 4;
	private String animationName = "";

	// Main Constructor
	public Main() {

		System.out.println("Main constructor called");
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
		
		// Creates a list 1 - (size - 1)
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








		// All Time Data Calculations and Storage


		System.out.println("Starting Calculations called");

		double[] randomTimes = new double[5];
		double[] fewUniqueTimes = new double[5];
		double[] reversedTimes = new double[5];
		double[] almostSortedTimes = new double[5];

		double[] bubbleSortTimes = new double[4];
		double[] heapSortTimes = new double[4];
		double[] mergeSortTimes = new double[4];
		double[] quickSortTimes = new double[4];
		double[] selectionSortTimes = new double[4];

		File bubbleTimes = new File("TimeLogs/bubbleTimes.txt");
		File heapTimes = new File("TimeLogs/heapTimes.txt");
		File mergeTimes = new File("TimeLogs/mergeTimes.txt");
		File quickTimes = new File("TimeLogs/quickTimes.txt");
		File selectionTimes = new File("TimeLogs/selectionTimes.txt");


		// Puts all bubble times in the bubble array
		// Calculates the total time spent sorting
		try {

			double totalTime = 0;
			Scanner bubbleScanner = new Scanner(new File("TimeLogs/bubbleTimes.txt"));

			for (int i = 0; i < bubbleSortTimes.length; i++) {
				String dataArrangementType = bubbleScanner.next();
				bubbleSortTimes[i] = bubbleScanner.nextDouble();
				totalTime += bubbleSortTimes[i];
			}

			double average = totalTime / 4;

			FileWriter file = new FileWriter("TimeLogs/bubbleTimes.txt", true);
			PrintWriter writer = new PrintWriter(file);

			writer.println("\nAverage Time: " + average);
			writer.flush();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("bubble scanner error");
		}


		// Puts all heap times in the heap array
		// Calculates the total time spent sorting
		try {

			double totalTime = 0;
			Scanner heapScanner = new Scanner(new File("TimeLogs/heapTimes.txt"));
			for (int i = 0; i < heapSortTimes.length; i++) {
				String dataArrangementType = heapScanner.next();
				heapSortTimes[i] = heapScanner.nextDouble();
				totalTime += heapSortTimes[i];
			}

			double average = totalTime / 4;

			FileWriter file = new FileWriter("TimeLogs/heapTimes.txt", true);
			PrintWriter writer = new PrintWriter(file);

			writer.println("\nAverage Time: " + average);
			writer.flush();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("heap scanner error");
		}


		// Puts all merge times in the merge array
		// Calculates the total time spent sorting
		try{

			double totalTime = 0;
			Scanner mergeScanner = new Scanner(new File("TimeLogs/mergeTimes.txt"));
			for (int i = 0; i < mergeSortTimes.length; i++) {
				String dataArrangementType = mergeScanner.next();
				mergeSortTimes[i] = mergeScanner.nextDouble();
				totalTime += mergeSortTimes[i];
			}

			double average = totalTime / 4;

			FileWriter file = new FileWriter("TimeLogs/mergeTimes.txt", true);
			PrintWriter writer = new PrintWriter(file);

			writer.println("\nAverage Time: " + average);
			writer.flush();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("merge scanner error");
		}


		// Puts all quick times in the quick array
		// Calculates the total time spent sorting
		try{

			double totalTime = 0;
			Scanner quickScanner = new Scanner(new File("TimeLogs/quickTimes.txt"));
			for (int i = 0; i < quickSortTimes.length; i++) {
				String dataArrangementType = quickScanner.next();
				quickSortTimes[i] = quickScanner.nextDouble();
				totalTime += quickSortTimes[i];
			}

			double average = totalTime / 4;

			FileWriter file = new FileWriter("TimeLogs/quickTimes.txt", true);
			PrintWriter writer = new PrintWriter(file);

			writer.println("\nAverage Time: " + average);
			writer.flush();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("quick scanner error");
		}


		// Puts all selection times in the selection array
		// Calculates the total time spent sorting
		try{

			double totalTime = 0;
			Scanner selectionScanner = new Scanner(new File("TimeLogs/selectionTimes.txt"));
			for (int i = 0; i < selectionSortTimes.length; i++) {
				String dataArrangementType = selectionScanner.next();
				selectionSortTimes[i] = selectionScanner.nextDouble();
				totalTime += selectionSortTimes[i];
			}

			double average = totalTime / 4;

			FileWriter file = new FileWriter("TimeLogs/selectionTimes.txt", true);
			PrintWriter writer = new PrintWriter(file);

			writer.println("\nAverage Time: " + average);
			writer.flush();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("selection scanner error");
		}


		// Puts in random times of all algorithms into the randomTimes array
		randomTimes[0] = bubbleSortTimes[0];
		randomTimes[1] = heapSortTimes[0];
		randomTimes[2] = mergeSortTimes[0];
		randomTimes[3] = quickSortTimes[0];
		randomTimes[4] = selectionSortTimes[0];

		// Puts in few unique times of all algorithms into the fewUniqueTimes array
		fewUniqueTimes[0] = bubbleSortTimes[1];
		fewUniqueTimes[1] = heapSortTimes[1];
		fewUniqueTimes[2] = mergeSortTimes[1];
		fewUniqueTimes[3] = quickSortTimes[1];
		fewUniqueTimes[4] = selectionSortTimes[1];

		// Puts in reversed times of all algorithms into the reversedTimes array
		reversedTimes[0] = bubbleSortTimes[2];
		reversedTimes[1] = heapSortTimes[2];
		reversedTimes[2] = mergeSortTimes[2];
		reversedTimes[3] = quickSortTimes[2];
		reversedTimes[4] = selectionSortTimes[2];

		// Puts in almost sorted times of all algorithms into the almostSortedTimes array
		almostSortedTimes[0] = bubbleSortTimes[3];
		almostSortedTimes[1] = heapSortTimes[3];
		almostSortedTimes[2] = mergeSortTimes[3];
		almostSortedTimes[3] = quickSortTimes[3];
		almostSortedTimes[4] = selectionSortTimes[3];



		// All File Writing


		// Random Times
		try {
			FileWriter file = new FileWriter("TimeLogs/randomDataTimeLog.txt");
			PrintWriter writer = new PrintWriter(file);
			writer.println("Order - Bubble, Heap, Merge, QuickSort, Selection");

			for (int i = 0; i < randomTimes.length; i++) {
				writer.println("" + randomTimes[i]);
				writer.flush();
			}

		} catch (IOException e) {
			System.out.println("ERROR");
		}


		// Few Unique Times
		try {
			FileWriter file = new FileWriter("TimeLogs/fewUniqueTimeLog.txt");
			PrintWriter writer = new PrintWriter(file);
			writer.println("Order - Bubble, Heap, Merge, QuickSort, Selection");

			for (int i = 0; i < fewUniqueTimes.length; i++) {
				writer.println("" + fewUniqueTimes[i]);
				writer.flush();
			}

		} catch (IOException e) {
			System.out.println("ERROR");
		}


		// Almost Sorted Times
		try {
			FileWriter file = new FileWriter("TimeLogs/almostSortedTimeLog.txt");
			PrintWriter writer = new PrintWriter(file);
			writer.println("Order - Bubble, Heap, Merge, QuickSort, Selection");

			for (int i = 0; i < almostSortedTimes.length; i++) {
				writer.println("" + almostSortedTimes[i]);
				writer.flush();
			}

		} catch (IOException e) {
			System.out.println("ERROR");
		}


		// Reversed Times
		try {
			FileWriter file = new FileWriter("TimeLogs/reversedTimeLog.txt");
			PrintWriter writer = new PrintWriter(file);
			writer.println("Order - Bubble, Heap, Merge, QuickSort, Selection");

			for (int i = 0; i < reversedTimes.length; i++) {
				writer.println("" + reversedTimes[i]);
				writer.flush();
			}

		} catch (IOException e) {
			System.out.println("ERROR");
		}

	}
}