import java.util.*;
import java.io.*;
import java.awt.Color;
import java.awt.Graphics;

// Panel that displays results of the algorithm's sorting runs
public class ResultsPanel extends JPanel {

	private double[] randomTimes;
	private double[] fewUniqueTimes;
	private double[] reversedTimes;
	private double[] almostSortedTimes;

	private double[] bubbleSortTimes;
	private double[] heapSortTimes;
	private double[] mergeSortTimes;
	private double[] quickSortTimes;
	private double[] selectionSortTimes;

	private File bubbleTimes;
	private File heapTimes;
	private File mergeTimes;
	private File quickTimes;
	private File selectionTimes;

	private Scanner bubbleScanner;
	private Scanner heapScanner;
	private Scanner mergeScanner;
	private Scanner quickScanner;
	private Scanner selectionScanner;

	ResultsPanel() {

		randomTimes = new double[4];
		fewUniqueTimes = new double[4];
		reversedTimes = new double[4];
		almostSortedTimes = new double[4];

		bubbleSortTimes = new double[3];
		heapSortTimes = new double[3];
		mergeSortTimes = new double[3];
		quickSortTimes = new double[3];
		selectionSortTimes = new double[3];

		bubbleTimes = new File("TimeLogs/bubbleTimes.txt");
		heapTimes = new File("TimeLogs/heapTimes.txt");
		mergeTimes = new File("TimeLogs/mergeTimes.txt");
		quickTimes = new File("TimeLogs/quickTimes.txt");
		selectionTimes = new File("TimeLogs/selectionTimes.txt");

		bubbleScanner = new Scanner(bubbleTimes);
		heapScanner = new Scanner(heapTimes);
		mergeScanner = new Scanner(mergeTimes);
		quickScanner = new Scanner(quickTimes);
		selectionScanner = new Scanner(selectionTimes);


		for (int i = 0; i < bubbleSortTimes.length; i++) {
			bubbleSortTimes[i] = bubbleScanner.nextDouble();
		}

		for (int i = 0; i < heapSortTimes.length; i++) {
			heapSortTimes[i] = heapScanner.nextDouble();
		}

		for (int i = 0; i < mergeSortTimes.length; i++) {
			mergeSortTimes[i] = mergeScanner.nextDouble();
		}

		for (int i = 0; i < quickSortTimes.length; i++) {
			quickSortTimes[i] = quickScanner.nextDouble();
		}

		for (int i = 0; i < selectionSortTimes.length; i++) {
			selectionSortTimes[i] = selectionScanner.nextDouble();
		}

		randomTimes[0] = bubbleSortTimes[0];
		randomTimes[1] = heapSortTimes[0];
		randomTimes[2] = mergeSortTimes[0];
		randomTimes[3] = quickSortTimes[0];
		randomTimes[4] = selectionSortTimes[0];

		fewUniqueTimes[0] = bubbleSortTimes[1];
		fewUniqueTimes[1] = heapSortTimes[1];
		fewUniqueTimes[2] = mergeSortTimes[1];
		fewUniqueTimes[3] = quickSortTimes[1];
		fewUniqueTimes[4] = selectionSortTimes[1];

		reversedTimes[0] = bubbleSortTimes[2];
		reversedTimes[1] = heapSortTimes[2];
		reversedTimes[2] = mergeSortTimes[2];
		reversedTimes[3] = quickSortTimes[2];
		reversedTimes[4] = selectionSortTimes[2];

		almostSortedTimes[0] = bubbleSortTimes[3];
		almostSortedTimes[1] = heapSortTimes[3];
		almostSortedTimes[2] = mergeSortTimes[3];
		almostSortedTimes[3] = quickSortTimes[3];
		almostSortedTimes[4] = selectionSortTimes[3];
	}




}