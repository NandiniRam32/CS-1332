import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("The data is empty.");
            }
            add(element);
        }
        //throw IllegalException if an element in data is null
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        if (size == 0) {
            root = new AVLNode<T>(data);
            recalculate(root);
            size = 1;
        } else {
            root = addHelper(data, root);
        }
    }

    /**Helper method for add(T data)
     * @param data the data to add
     * @param current the node we are currently checking
     * @return the new root
    */
    private AVLNode<T> addHelper(T data, AVLNode<T> current) {
        if (current == null) {
            size++;
            return (new AVLNode<T>(data));
        }
        if (data.compareTo(current.getData()) < 0) {
            current.setLeft(addHelper(data, current.getLeft()));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(addHelper(data, current.getRight()));
        }
        recalculate(current);
        return (rotate(current));
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        AVLNode<T> node = new AVLNode<T>(null);
        root = removeHelper(data, root, node);
        size--;
        return (node.getData());
    }

    /**
     * Helper method for remove(T data)
     * 
     * @param data the data to remove
     * @param current the current node we are checking
     * @param node the extra node
     * @return the node we want to remove
     * @throws java.util.NoSuchElementException if data was not found
    */
    private AVLNode<T> removeHelper(T data, AVLNode<T> current, AVLNode<T> node) {
        if (current == null) {
            throw new NoSuchElementException("Data was not found.");
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(removeHelper(data, current.getLeft(), node));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(removeHelper(data, current.getRight(), node));
        } else if (current.getData().compareTo(data) == 0) {
            node.setData(current.getData());
            //Case 1: node has no children --> remove it
            if (current.getLeft() == null && current.getRight() == null) {
                current = null;
                return (null);
            }
            //Case 2: node has one child --> replace it with its child
            if (current.getLeft() == null && current.getRight() != null) {
                return (current.getRight());
            } else if (current.getLeft() != null && current.getRight() == null) {
                return (current.getLeft());
            }
            //Case 3: node has two children --> replace the node with its predecessor
            if (current.getLeft() != null && current.getRight() != null) {
                AVLNode<T> temp = new AVLNode<>(current.getData());
                current.setLeft(predecessor(current.getLeft(), temp));
                current.setData(temp.getData());
            }
        }
        recalculate(current);
        return (rotate(current));
    }

    /**
     * Finds the predecessor of a certain node
     * @param current the node we are currently checking
     * @param temp the node we are replacing the data for
     * @return the predecessor of the first parameter
    */
    private AVLNode<T> predecessor(AVLNode<T> current, AVLNode<T> temp) {
        if (current.getRight() == null) {
            temp.setData(current.getData());
            return (current.getLeft());
        }
        current.setRight(predecessor(current.getRight(), temp));
        recalculate(current);
        return (rotate(current));
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        return (getHelper(data, root));
        //throw NoSuchElementException if data is not in the tree
    }

    /** Helper method for the get(T data) method above
     * @param data the data to search for in the tree
     * @param current the current node we are checking
     * @return the data in the tree equal to the parameter
     * @throws java.util.NoSuchElementException if the data is not in the tree
    */
    private T getHelper(T data, AVLNode<T> current) {
        if (current == null) {
            throw new NoSuchElementException("Data is not in the tree.");
        } else if (current.getData().compareTo(data) > 0) {
            return (getHelper(data, current.getLeft()));
        } else if (current.getData().compareTo(data) < 0) {
            return (getHelper(data, current.getRight()));
        } else if (current.getData().equals(data)) {
            return (current.getData());
        }
        return (current.getData());
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        return (containsHelper(data, root));
    }

    /**Helper method for contains(T data), searches for data recursively
     * @param data the data to search for in the tree
     * @param current the node we are currently checking
     * @return true if the parameter is contained within the tree, false otherwise
    */
    private boolean containsHelper(T data, AVLNode<T> current) {
        if (current == null) {
            return (false);
        } else if (current.getData().compareTo(data) > 0) {
            return (containsHelper(data, current.getLeft()));
        } else if (current.getData().compareTo(data) < 0) {
            return (containsHelper(data, current.getRight()));
        } else if (current.getData().equals(data)) {
            return (true);
        }
        return (false);
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Recalculates heights and balance factors
     * @param node the node we are recalculating the height and balance factor of
    */
    private void recalculate(AVLNode<T> node) {
        int leftSideHeight = -1;
        int rightSideHeight = -1;
        if (node.getLeft() != null) {
            leftSideHeight = node.getLeft().getHeight();
        }
        if (node.getRight() != null) {
            rightSideHeight = node.getRight().getHeight();
        }
        node.setHeight((leftSideHeight > rightSideHeight ? leftSideHeight : rightSideHeight) + 1);
        node.setBalanceFactor(leftSideHeight - rightSideHeight);
    }

    /**
     * Rotates certain subtrees until the tree is balanced
     * @param node the root of the substree being rotated
     * @return the new root of the subtree
    */
    private AVLNode<T> rotate(AVLNode<T> node) {
        int balanceFactor = node.getBalanceFactor();
        if (balanceFactor > 1) {
            int leftBalanceFactor = node.getLeft().getBalanceFactor();
            if (leftBalanceFactor < 0) {
                root.setLeft(leftRotate(node.getLeft()));
            }
            return (rightRotate(node));
        } else if (balanceFactor < -1) {
            int rightBalanceFactor = node.getRight().getBalanceFactor();
            if (rightBalanceFactor > 0) {
                root.setRight(rightRotate(node.getRight()));
            }
            return (leftRotate(node));
        }
        return (node);
    }

    /**
     * Performs a left roation on a subtree, using the input node as the former root
     * @param node the previous root of the rotated subtree
     * @return the new root
    */
    private AVLNode<T> leftRotate(AVLNode<T> node) {
        AVLNode<T> right = node.getRight();
        node.setRight(right.getLeft());
        right.setLeft(node);
        recalculate(node);
        recalculate(right);
        return (right);
    }

    /**
     * Performs a right roation on a subtree, using the input node as the former root
     * @param node the previous root of the rotated subtree
     * @return the new root
    */
    private AVLNode<T> rightRotate(AVLNode<T> node) {
        AVLNode<T> left = node.getLeft();
        node.setLeft(left.getRight());
        left.setRight(node);
        recalculate(node);
        recalculate(left);
        return (left);
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * This must be done recursively.
     *
     * Your list should not have duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> deepestList = new ArrayList<T>();
        deepestList = deepestBranchesHelper(root, deepestList);
        return (deepestList);
    }

    /**
     * private recursive helper method for deepestBranches()
     * @param current the node we are currently checking
     * @param deepestList the list of data in branches of maximum depth in preorder traversal order
     * @return the list of data in branches of maximum depth in preorder traversal order
     */
    private List<T> deepestBranchesHelper(AVLNode<T> current, List<T> deepestList) {
        if (current == null) {
            return (deepestList);
        }
        deepestList.add(current.getData());
        if (current.getLeft() != null && (current.getHeight() - current.getLeft().getHeight() <= 1)) {
            deepestList = deepestBranchesHelper(current.getLeft(), deepestList);
        }
        if (current.getRight() != null && (current.getHeight() - current.getRight().getHeight() <= 1)) {
            deepestList = deepestBranchesHelper(current.getRight(), deepestList);
        }
        return (deepestList);
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * This must be done recursively.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null) {
            throw new IllegalArgumentException("data1 is null.");
        } else if (data2 == null) {
            throw new IllegalArgumentException("data2 is null.");
        } else if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("data1 > data2.");
        }
        List<T> returnList = new ArrayList<>();
        sortedInBetweenHelper(root, data1, data2, returnList);
        return (returnList);
    }

    /**
     * private recursive helper method for sortedInBetween(T data1, T data2)
     * @param current the node we are currently at
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @param returnList the sorted list of data that is > data1 and < data2
     * @return a sorted list of data that is > data1 and < data2
    */
    private List<T> sortedInBetweenHelper(AVLNode<T> current, T data1, T data2, List<T> returnList) {
        if (data1.equals(data2)) {
            return (returnList);
        }
        if (current == null) {
            return (returnList);
        }
        if (current.getData().compareTo(data1) > 0) {
            returnList = sortedInBetweenHelper(current.getLeft(), data1, data2, returnList);
        }
        if (current.getData().compareTo(data1) > 0 && current.getData().compareTo(data2) < 0) {
            returnList.add(current.getData());
        }
        if (current.getData().compareTo(data2) < 0) {
            returnList = sortedInBetweenHelper(current.getRight(), data1, data2, returnList);
        }
        return (returnList);
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
