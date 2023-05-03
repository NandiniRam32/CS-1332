import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Nandini Ramakrishnan
 * @version 1.0
 * @userid nramakri6
 * @GTID 903805663
 *
 * Collaborators:
 *
 * Resources:
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is less than zero.");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Index is greater than size.");
        } else if (data == null) {
            throw new IllegalArgumentException("Data is null and cannot be added to the LinkedList.");
        }
        if (size == 0) {
            head = new CircularSinglyLinkedListNode<T>(data);
            head.setNext(head);
            size++;
            return;
        }
        if (index == 0) {
            T oldHeadData = head.getData();
            CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<T>(oldHeadData, head.getNext());
        	head.setData(data);
        	head.setNext(temp);
            size++;
        } else {
        	CircularSinglyLinkedListNode<T> thisNode = head;
	        for (int i = 0; i < index - 1; i++) {
	        	thisNode = thisNode.getNext();
	        }
	        thisNode.setNext(new CircularSinglyLinkedListNode<T>(data, thisNode.getNext()));
            size++;
        }
    }


    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index entered is less than zero.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index entered is greater than or equal to size (too big).");
        }
        T removedData = null;
        if (index == 0) {
            CircularSinglyLinkedListNode<T> thisNode = head;
            removedData = (T) head.getData();
            for (int i = 0; i < size - 1; i++) {
                thisNode = thisNode.getNext();
            }
            thisNode.setNext(head.getNext());
            head = head.getNext();
        } else {
        	CircularSinglyLinkedListNode<T> thisNode = head;
	        for (int i = 0; i < index - 1; i++) {
	        	thisNode = thisNode.getNext(); 
	        }
	        removedData = thisNode.getNext().getData();
	        thisNode.setNext((thisNode.getNext()).getNext());
        }
        size--;        
        return (removedData);
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        return (removeAtIndex(0));
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        return (removeAtIndex(size - 1));
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index entered is less than zero.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index entered is greater than or equal to size (too big).");
        }
        CircularSinglyLinkedListNode<T> thisNode = head;
        for (int i = 0; i < size; i++) {
        	if (index == i) {
        		return thisNode.getData();
        	}
        	thisNode = thisNode.getNext();        	
        }
        return null;
    }


    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        int finalIndex = -1;
        if (data == null) {
            throw new IllegalArgumentException("The data entered is null.");
        }
        CircularSinglyLinkedListNode<T> thisNode = head;
        for (int i = 0; i < size; i++) {
            if (thisNode.getData().equals(data)) {
                finalIndex = i;
            }
            thisNode = thisNode.getNext();
        }
        if (finalIndex != -1) {
            return (removeAtIndex(finalIndex));
        }
        throw new NoSuchElementException("The data entered was not found in the LinkedList.");
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] newArray = (T[]) (new Object[size]);
        CircularSinglyLinkedListNode<T> thisNode = head;
        for (int i = 0; i < size; i++) {
            newArray[i] = (T) thisNode.getData();
            thisNode = thisNode.getNext();
        }
        return newArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
