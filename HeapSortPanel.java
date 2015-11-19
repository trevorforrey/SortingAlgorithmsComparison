
import java.util.*;
import java.awt.Color;
import java.awt.Graphics;

// Heap Sort Panel
public class HeapSortPanel extends SortPanel {

	private static final long serialVersionUID = 1L;
	// Sets red column and green column to an unseen value
	private int redColumn = -1;
	private int greenColumn = -1;
	// Creates a new heaplist based on an array list
	private java.util.ArrayList<Integer> heapList = new java.util.ArrayList<Integer>();
//********
	private long startTime;
	private long estimatedTime;
	private double estimatedTimeInSeconds;
	
	// Heap Sort Panel Constructor
	public HeapSortPanel(String name, int sleepTime, int width, int height) {
		super(name, sleepTime, width, height);
//********
		startTime = System.nanoTime();
		System.out.println(startTime);
	}

	// Puts red and green column back to unseen values
	// Also clears the heapList
	@Override
	public void reset() {
		redColumn = -1;
		greenColumn = -1;
		heapList.clear();
	}

	// When the heap sort runs
	@Override
	public void run() {
		try {

			// Add elements to the heap
			for (int i = 0; i < list.length; i++) {
				add(list[i]);
				repaint();
				Thread.sleep(2 * sleepTime);				
			}

			// Remove elements from the heap
			greenColumn = size;
			for (int i = list.length - 1; i >= 0; i--) {
				// Removes and shows the last element in the heap structure
				removeAndShow(i);
				repaint();
				Thread.sleep(sleepTime);
			}
//********
			estimatedTime = System.nanoTime() - startTime;
			estimatedTimeInSeconds = (double)estimatedTime / 1000000000;
			System.out.print("\nTime in seconds for Heap: " + estimatedTimeInSeconds);
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

		// Draws white column (value)
		for (int i = heapList.size(); i < list.length; i++) {
			g.setColor(Color.WHITE);
			g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
			g.setColor(Color.BLACK);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);			
		}

		// Draws cyan column (value)
		for (int i = 0; i < heapList.size(); i++) {
			g.setColor(Color.CYAN);
			g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - heapList.get(i) * columnHeight - 2 * BORDER_WIDTH, columnWidth, heapList.get(i) * columnHeight);
			g.setColor(Color.BLACK);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - heapList.get(i) * columnHeight - 2 * BORDER_WIDTH, columnWidth, heapList.get(i) * columnHeight);			
		}
		
		// If the greenColumn exists, paint it green
		if(greenColumn != -1) {
			for (int i = greenColumn; i < size; i++) {
				g.setColor(Color.GREEN);
				g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
				g.setColor(Color.BLACK);
				g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);			
			}
		}

		// If the redColumn exists, paint it red
		if(redColumn != -1) {
			g.setColor(Color.RED);
			g.fillRect(2 * BORDER_WIDTH + columnWidth * redColumn, getHeight() - heapList.get(redColumn) * columnHeight - 2 * BORDER_WIDTH, columnWidth, heapList.get(redColumn) * columnHeight);
			g.setColor(Color.BLACK);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * redColumn, getHeight() - heapList.get(redColumn) * columnHeight - 2 * BORDER_WIDTH, columnWidth, heapList.get(redColumn) * columnHeight);
		}
		
	}
	

	/** Add a new object into the heap 
	 * @throws InterruptedException */
	public void add(Integer newObject) throws InterruptedException {
		heapList.add(newObject); // Append to the heap
		repaint();
		Thread.sleep(3 * sleepTime);
		int currentIndex = heapList.size() - 1; // The index of the last node
		// red column is set to the currently processed index
		redColumn = currentIndex;
		while (currentIndex > 0) {
			repaint();
			Thread.sleep(3 * sleepTime);
			int parentIndex = (currentIndex - 1) / 2;
			// Swap if the current object is greater than its parent
			if (heapList.get(currentIndex).compareTo(heapList.get(parentIndex)) > 0) {
				repaint();
				Thread.sleep(6 * sleepTime);
				Integer temp = heapList.get(currentIndex);
				heapList.set(currentIndex, heapList.get(parentIndex));
				heapList.set(parentIndex, temp);
			} else {
				break; // the tree is a heap now
			}
			currentIndex = parentIndex;
			redColumn = currentIndex;
		}
		// sets red column back to outside of the array
		redColumn = -1;
	}

	/** Remove the root from the heap 
	 * @throws InterruptedException */
	public void removeAndShow(int showIndex) throws InterruptedException {

		if (heapList.size() == 0) {
			return;
		}

		repaint();
		Thread.sleep(4 * sleepTime);
		Integer removedObject = heapList.get(0);
		// sets current max value to bottom of the heap
		heapList.set(0, heapList.get(heapList.size() - 1));
		// removes previously removed value from the heap
		heapList.remove(heapList.size() - 1);
		greenColumn--;
		// shows the value that was removed from the array
		list[showIndex] = removedObject;
		
		// Starts process of re-heaping the array from the top most element
		int currentIndex = 0;
		// While the current index isn't the bottom of the heap
		while (currentIndex < heapList.size()) {
			//find the index of left and right children of the current node
			int leftChildIndex = 2 * currentIndex + 1;
			int rightChildIndex = 2 * currentIndex + 2;

			// Find the maximum between two children
			if (leftChildIndex >= heapList.size())
				break; // The tree is a heap
			int maxIndex = leftChildIndex;
			if (rightChildIndex < heapList.size()) {
				repaint();
				Thread.sleep(4 * sleepTime);
				if (heapList.get(maxIndex).compareTo(heapList.get(rightChildIndex)) < 0) {
					maxIndex = rightChildIndex;
				}
			}

			// Swap if the current node is less than the maximum
			if (heapList.get(currentIndex).compareTo(heapList.get(maxIndex)) < 0) {
				Integer temp = heapList.get(maxIndex);
				heapList.set(maxIndex, heapList.get(currentIndex));
				heapList.set(currentIndex, temp);
				currentIndex = maxIndex;
				repaint();
				Thread.sleep(4 * sleepTime);
			} else {
				break;
			}
		}
	}

}
