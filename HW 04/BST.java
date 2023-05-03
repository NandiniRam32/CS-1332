import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter is null.");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("At least one element in data is null.");
            }
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null and cannot be added.");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size = 1;
        } else {
            binarySearch(data, root);
        }
    }

    /**
     * helper method to binary search through the BST
     * @param data
     * @param node
     */
    private void binarySearch(T data, BSTNode<T> node) {
        if (data.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTNode<>(data));
                size++;
            } else {
                binarySearch(data, node.getRight());
            }
        } else if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTNode<>(data));
                size++;
            } else {
                binarySearch(data, node.getLeft());
            }
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null and cannot be removed.");
        }
        int index = -1;
        T ret = remove(data, index, root, root);
        size--;
        return ret;
    }

    /**
     * helper remove method
     * @param data
     * @param index
     * @param node
     * @param parent
     * @return the data of the removed node
     */
    private T remove(T data, int index, BSTNode<T> node, BSTNode<T> parent) {
        if (node == null) {
            throw new NoSuchElementException("Data is not in the tree.");
        }
        if (data.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                throw new NoSuchElementException("Data is not in the tree");
            }
            return remove(data, index, node.getRight(), node);
        } else if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                throw new NoSuchElementException("Data is not in the tree");
            }
            return remove(data, index, node.getLeft(), node);
            // node.setLeft(remove(data, index, node.getLeft(), returnNode));
        } else if (data.compareTo(node.getData()) == 0) {
            T returnData = null;
            if (node.getRight() != null && node.getLeft() == null) {
                returnData = node.getData();
                node.setData(node.getRight().getData());
                node.setLeft(node.getRight().getLeft());
                node.setRight(node.getRight().getRight());
            } else if (node.getRight() == null && node.getLeft() != null) {
                returnData = node.getData();
                node.setData(node.getLeft().getData());
                node.setRight(node.getLeft().getRight());
                node.setLeft(node.getLeft().getLeft());
            } else if (node.getRight() == null && node.getLeft() == null) {
                returnData = node.getData();
                if (parent.getRight() == node) {
                    parent.setRight(null);
                } else if (parent.getLeft() == node) {
                    parent.setLeft(null);
                }
                if (node == root) {
                    root = null;
                }
            } else if (node.getRight() != null && node.getLeft() != null) {
                returnData = node.getData();
                BSTNode<T> successorNodeParent = findSuccessor(node.getRight(), null);
                if (successorNodeParent == null) {
                    node.setData(node.getRight().getData());
                    node.setRight(node.getRight().getRight());
                } else {
                    node.setData(successorNodeParent.getLeft().getData());
                    successorNodeParent.setLeft(successorNodeParent.getLeft().getRight());
                }
            }
            return returnData;
        }
        return null;
    }

    /**
     * finds the successor to the node
     * @param node the node passed in the method
     * @param parent the parent of hte node passed in
     * @return the parent of the successor
     */
    private BSTNode<T> findSuccessor(BSTNode<T> node, BSTNode<T> parent) {
        if (node.getLeft() != null) {
            return findSuccessor(node.getLeft(), node);
        } else {
            return parent;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        int index = -1;
        return (get(index, data, root));
    }

    /**
     * recursive helper method for get
     * @param index a checker value for whether the node containing the data was in the BST
     * @param data the data to find
     * @param node the current node we are on in the traversal
     * @return the data of the node we searched for
     */
    private T get(int index, T data, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Data is not in the tree.");
        }
        if (data.compareTo(node.getData()) < 0) {
            return (get(index, data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            return (get(index, data, node.getRight()));
        } else if (data.compareTo(node.getData()) == 0) {
            index = 0;
            return (node.getData());
        }
        if (index == -1) {
            throw new NoSuchElementException("Data is not in the tree.");
        }
        return (null);
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        int index = -1;
        return (contains(index, data, root));
    }

    /**
     * checks if the BST contains a node with the data
     * @param index the checker variable to make sure the node with the data is in the BST
     * @param data the data to search for
     * @param node the current node we are on in the traversal
     * @return whether or not hte BST contains the data
     */
    private boolean contains(int index, T data, BSTNode<T> node) {
        if (node == null) {
            return (false);
        }
        if (data.compareTo(node.getData()) < 0) {
            return (contains(index, data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            return (contains(index, data, node.getRight()));
        } else if (data.compareTo(node.getData()) == 0) {
            index = 0;
            return (true);
        }
        return (false);
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorderList = new ArrayList<T>();
        preorderList = preorder(preorderList, root);
        return (preorderList);
    }

    /**
     * returns a List with the preorder traversal of the BST (helper method)
     * @param preorderList the List of data in the preorder traversal order
     * @param node the current node that we are on in the traversal
     * @return the List representing the finalized preorder traversal list
     */
    private List<T> preorder(List<T> preorderList, BSTNode<T> node) {
        if (node != null) {
            preorderList.add(node.getData());
            preorder(preorderList, node.getLeft());
            preorder(preorderList, node.getRight());
        }
        return (preorderList);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorderList = new ArrayList<T>();
        inorderList = inorder(inorderList, root);
        return (inorderList);
    }

    /**
     * returns a List with the inorder traversal of the BST (helper method)
     * @param inorderList the List of data in the postorder traversal order
     * @param node the current node that we are on in the traversal
     * @return the List representing the finalized inorder traversal list
     */
    private List<T> inorder(List<T> inorderList, BSTNode<T> node) {
        if (node != null) {
            inorder(inorderList, node.getLeft());
            inorderList.add(node.getData());
            inorder(inorderList, node.getRight());
        }
        return (inorderList);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorderList = new ArrayList<T>();
        postorderList = postorder(postorderList, root);
        return (postorderList);
    }

    /**
     * returns a List with the postorder traversal of the BST (helper method)
     * @param postorderList the List of data in the postorder traversal order
     * @param node the current node that we are on in the traversal
     * @return the List representing the finalized postorder traversal list
     */
    private List<T> postorder(List<T> postorderList, BSTNode<T> node) {
        if (node != null) {
            postorder(postorderList, node.getLeft());
            postorder(postorderList, node.getRight());
            postorderList.add(node.getData());
        }
        return (postorderList);
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<BSTNode<T>> levelorderList = new LinkedList<BSTNode<T>>();
        levelorderList.add(root);
        List<T> data = (List<T>) (new ArrayList<T>());
        while (!(levelorderList.isEmpty())) {
            BSTNode<T> node = ((LinkedList<BSTNode<T>>) levelorderList).remove();
            if (node != null) {
                data.add(node.getData());
                if (node.getLeft() != null) {
                    levelorderList.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    levelorderList.add(node.getRight());
                }
            }
        }
        return (data);
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return (height(root));
    }

    /**
     * helper method that returns the height of the node we are looking for
     * @param node the node we are trying ot find the height of
     * @return an integer representing the hieght of the node
     */
    private int height(BSTNode<T> node) {
        if (node == null) {
            return (-1);
        }
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        if (leftHeight > rightHeight) {
            return (leftHeight + 1);
        } else if (rightHeight >= leftHeight) {
            return (rightHeight + 1);
        }
        return (-1);
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("Parameter is less than zero.");
        } else if (k > size) {
            throw new IllegalArgumentException("Parameter is greater than size.");
        }
        List<T> kLargestList = new LinkedList<T>();
        if (size != 0) {
            BSTNode<T> node = root;
            kLargest(k, node, kLargestList);
        }
        return kLargestList;
    }

    /**
     * helper method for the kLargest method
     * @param k the number of values in the returned ArrayList
     * @param node the node we are on in the traversal
     * @param kLargestList the list of values
     */
    private void kLargest(int k, BSTNode<T> node, List<T> kLargestList) {
        if (k < kLargestList.size()) {
            return;
        }
        if (node.getRight() != null) {
            kLargest(k, node.getRight(), kLargestList);
        }
        if (k > kLargestList.size()) {
            kLargestList.add(0, node.getData());
        }
        if (node.getLeft() != null) {
            kLargest(k, node.getLeft(), kLargestList);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
