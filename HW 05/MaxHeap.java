import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Nandini
 * @version 1.0
 * @userid nramakri6
 * @GTID 903805663
 *
 * Collaborators:
 *
 * Resources:
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data entered is null.");
        }
        backingArray = (T[]) (new Comparable[2 * data.size() + 1]);
        size = data.size();
    //    while (backingArray.length < (2 * data.size() + 1)) {
     //       backingArray = (T[]) (new Comparable[backingArray.length * 2]);
     //   }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("At least 1 item in data was null.");
            }
            backingArray[i + 1] = data.get(i);
           // if (i > 1 && backingArray[i / 2].compareTo(backingArray[i]) < 0) {
           //     switchValues(i, i / 2);
          //      i = i / 2;
          //  }
        }
        int i = size / 2;
        while (i > 0) {
            heapify(i);
            i--;
        }
       // heapify();
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data entered is null.");
        }
        T[] newBackingArray = backingArray;
        if (size + 1 >= backingArray.length) {
            newBackingArray = (T[]) (new Comparable[backingArray.length * 2]);
            for (int i = 0; i < backingArray.length; i++) {
                newBackingArray[i] = backingArray[i];
            }
        }
        backingArray = newBackingArray;
        backingArray[size + 1] = data;
        int i = size + 1;
        while (i > 1 && backingArray[i / 2].compareTo(backingArray[i]) < 0) {
            switchValues(i, i / 2);
            i = i / 2;
        }
        size = size + 1;
    }

    /**
     * Switches the values of two items in the backing array
     * @param i index of one of the items to switch
     * @param j index of the other item to switch
     */
    private void switchValues(int i, int j) {
        T temp = backingArray[i];
        backingArray[i] = backingArray[j];
        backingArray[j] = temp;
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("The heap is empty.");
        }
        T returnData = backingArray[1];
        backingArray[1] = null;
        switchValues(1, size);
        size--;
        int i = size / 2;
        while (i > 0) {
            heapify(i);
            i--;
        }
        return (returnData);
    }

    /**
     * Rearranges the data in the heap so that all parent-child relationships are correct
     * @param i integer representing the starting index
     */
    private void heapify(int i) {
        /**int i = size - 1;
        //int i = size;
        //int j = 2;
        while (i > 1 && backingArray[i / 2].compareTo(backingArray[i]) > 0) {
            switchValues(i, i / 2);
            i = i - 1;
        }*/
        int trigger = 0;
        int j = i * 2;
        while (trigger == 0 && i * 2  <= size) {
            j = i * 2;
            if (size >= i * 2 + 1) {
                if (backingArray[i * 2].compareTo(backingArray[i * 2 + 1]) < 0) {
                    j++;
                }
            }
            if (backingArray[i].compareTo(backingArray[j]) < 0) {
                switchValues(i, j);
                i = j;
            } else {
                trigger = 1;
            }
        }
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("The heap is empty.");
        }
        return (backingArray[1]);
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
