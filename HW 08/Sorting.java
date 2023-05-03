import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null.");
        }
        int minIndex;
        for (int i = 0; i < arr.length; i++) {
            minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if ((comparator.compare(arr[j], (arr[minIndex]))) < 0) {
                    minIndex = j;
                }
            }
            swap(minIndex, i, arr);
        }
    }

    /**
     * Helper method to swap two index is an array
     * @param <T> data type of sort
     * @param i the first index to switch
     * @param j the second index to switch
     * @param array the array to sort
    */
    private static <T> void swap(int i, int j, T[] array) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null.");
        }
        boolean itemsSwapped = true;
        int leftSide = 0;
        int rightSide = arr.length - 1;
        int swapIndex = 0;
        while (itemsSwapped) {
            itemsSwapped = false;
            for (int i = swapIndex; i < rightSide; i++) {
                int bigger = comparator.compare(arr[i], arr[i + 1]);
                if (bigger > 0) {
                    itemsSwapped = true;
                    swap(i, i + 1, arr);
                    swapIndex = i;
                }
            }
            rightSide = swapIndex;
            if (itemsSwapped) {
                itemsSwapped = false;
                for (int i = swapIndex; i > leftSide; i--) {
                    int smaller = comparator.compare(arr[i], arr[i - 1]);
                    if (smaller < 0) {
                        itemsSwapped = true;
                        swap(i, i - 1, arr);
                        swapIndex = i;
                    }
                }
                leftSide = swapIndex;
            } else {
                break;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null.");
        }
        T[] newArray = (T[]) (new Object[arr.length]);
        int newLength = arr.length - 1;
        mergeSort(arr, newArray, 0, newLength, comparator);
    }

    /**
     * Helper method for mergeSort
     * @param <T> the data type to sort
     * @param arr the array to sort
     * @param newArray the new array created
     * @param lowSide the low side of the array
     * @param highSide the high side of the array
     * @param comparator the comparator used
     */
    private static <T> void mergeSort(T[] arr, T[] newArray, int lowSide, int highSide, Comparator<T> comparator) {
        if (lowSide < highSide) {
            int middleIndex = (lowSide + highSide) / 2;
            mergeSort(arr, newArray, lowSide, middleIndex, comparator);
            mergeSort(arr, newArray, (middleIndex + 1), highSide, comparator);
            merge(arr, newArray, lowSide, (middleIndex + 1), highSide, comparator);
        }
    }

    /**
     * Helper method for mergeSort
     * @param <T> the type being sorted
     * @param arr the array being sorted
     * @param newArray the new array created
     * @param left the left index bound to sort
     * @param right the right index bound to sort
     * @param highSide the upper bound
     * @param comparator the comparator used
     */
    private static <T> void merge(T[] arr, T[] newArray, int left, int right, int highSide, Comparator<T> comparator) {
        int length = highSide - left + 1;
        int leftSide = right - 1;
        int pointer = left;
        while (right <= highSide && left <= leftSide) {
            if (comparator.compare(arr[left], arr[right]) <= 0) {
                newArray[pointer] = arr[left];
                pointer++;
                left++;
            } else {
                newArray[pointer] = arr[right];
                pointer++;
                right++;
            }
        }
        while (left <= leftSide) {
            newArray[pointer] = arr[left];
            pointer++;
            left++;
        }
        while (right <= highSide) {
            newArray[pointer] = arr[right];
            pointer++;
            right++;
        }
        for (int i = 0; i < length; i++) {
            arr[highSide] = newArray[highSide];
            highSide--;
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("the array is null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null.");
        } else if (rand == null) {
            throw new IllegalArgumentException("Rand is null.");
        } else if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("K is not in the range of 1 to arr.length.");
        }
        return (kthSelectHelper(k, arr, comparator, 0, arr.length, rand));
    }

    /**
     * Helper method for the kthSelect method
     * @param <T> the type being sorted
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param lowSide    the low side of the array
     * @param highSide   the high side of the array
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
    */
    private static <T> T kthSelectHelper(int k, T[] arr, Comparator<T> comparator,
                                            int lowSide, int highSide, Random rand) {
        int pivotIndex = rand.nextInt(highSide - lowSide) + lowSide;
        T pivot = arr[pivotIndex];
        T temp = arr[lowSide];
        arr[lowSide] = arr[pivotIndex];
        arr[pivotIndex] = temp;
        int leftIndex = lowSide + 1;
        int rightIndex = highSide - 1;
        while (leftIndex <= rightIndex) {
            while (leftIndex <= rightIndex && comparator.compare(arr[leftIndex], pivot) <= 0) {
                leftIndex++;
            }
            while (leftIndex <= rightIndex && comparator.compare(arr[rightIndex], pivot) >= 0) {
                rightIndex--;
            }
            if (leftIndex <= rightIndex) {
                temp = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = temp;
                leftIndex++;
                rightIndex--;
            }
        }
        temp = arr[rightIndex];
        arr[rightIndex] = arr[lowSide];
        arr[lowSide] = temp;
        if (rightIndex > k - 1) {
            return (kthSelectHelper(k, arr, comparator, lowSide, rightIndex, rand));
        } else if (rightIndex < k - 1) {
            return (kthSelectHelper(k, arr, comparator, rightIndex + 1, highSide, rand));
        } else if (rightIndex == k - 1) {
            return (arr[rightIndex]);
        }
        return (null);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        }
        int[] newArray = lsd(arr);
        arr = newArray;
    }

    /**
     * Helper method for the lsdRadixSort method
     * @param arr the array to be sorted
     * @return the sorted array
     */
    private static int[] lsd(int[] arr) {
        LinkedList<Integer>[] buckets = new LinkedList[19];
        boolean keepGoing = true;
        int div = 1;
        for (LinkedList<Integer> bucket : buckets) {
            bucket = new LinkedList<>();
        }
        while (keepGoing) {
            keepGoing = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    keepGoing = true;
                }
                if (buckets[bucket % 10 + 9] == null) {
                    buckets[bucket % 10 + 9] = new LinkedList<>();
                }
                buckets[bucket % 10 + 9].add(num);
            }
            int index = 0;
            for (LinkedList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int num : bucket) {
                        arr[index++] = num;
                    }
                    bucket.clear();
                }
            }
            div *= 10;
        }
        return (arr);
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        int[] returnList = new int[data.size()];
        int value;
        PriorityQueue<Integer> heap = new PriorityQueue<>(data);
        for (int i = 0; i < data.size(); i++) {
            value = (int) heap.remove();
            returnList[i] = value;
        }
        return (returnList);
    }
}
