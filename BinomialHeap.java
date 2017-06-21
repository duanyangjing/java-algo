import java.lang.Math;

public class BinomialHeap {
	
	class BinomialTreeNode {
		int key;
		BinomialTreeNode leftChild;
		BinomialTreeNode rightSib;
		BinomialTreeNode parent;
		int degree;

		public BinomialTreeNode(int key) {
			this.key = key;
			leftChild = null;
			rightSib = null;
			parent = null;
			degree = 0;
		}

		/**
		 * Merge two binomial trees b(k-1) to generate a new binomial tree b(k).
		 * @param node, the other binomial tree with the same degree as current one, should throw exception otherwise
		 * @return a new binomial tree of degree k.
		 */
		public BinomialTreeNode merge(BinomialTreeNode node) {
			// TODO: might be better to throw exception
			if (node == null) return null;
			if (this.degree != node.degree) return null;

			BinomialTreeNode iter1 = this;
			BinomialTreeNode iter2 = node.leftChild;
			while (iter1.leftChild != null) {
				connect(iter1, iter2);
				iter1 = iter1.leftChild;
				iter2 = iter2.leftChild;
			}

			this.parent = node;
			node.leftChild = this;
			node.degree++;

			return node;
		}

		/**
		 * connect two root list n1 and n2
		 * @param n1, start of root list on the left
		 * @param n2, start of root list on the right
		 */
		private void connect(BinomialTreeNode n1, BinomialTreeNode n2) {
			while (n1.rightSib != null) {
				n1 = n1.rightSib;
			}
			n1.rightSib = n2;
		}


	}

	BinomialTreeNode root;
	int size;

	public BinomialHeap() {
		this.root = null;
		this.size = 0;
	}

    /**
     * Constructor to initialize a binomial heap. It traverses all the right
     * sibling of node passed in to calculate size.
     * @param root, binomialTreeNode as the head of the root list in the heap.
     */
	public BinomialHeap(BinomialTreeNode root) {
	    this.root = root;
	    int size = 0;
	    while (root != null) {
	        size += (int)Math.pow(2, (double)root.degree);
	        root = root.rightSib;
        }
        this.size = size;
    }

	public boolean isEmpty() {
		return root == null;
	}

	public BinomialHeap insert(int key) {
	    BinomialTreeNode node = new BinomialTreeNode(key);
	    BinomialHeap heap = new BinomialHeap(node);
		return union(heap);
	}

	public int findMin() {
		// TODO: might use exceptions instead
		if (this.root == null) return -1;

		BinomialTreeNode curr = root;
		int min = Integer.MAX_VALUE;
		while (curr != null) {
		    if (curr.key < min) min = curr.key;
		    curr = curr.rightSib;
        }

        return min;
	}

	public int extractMin() {
		// TODO: might use exceptions instead
		if (this.root == null) return -1;

        BinomialTreeNode curr = root;
        int min = Integer.MAX_VALUE;
        BinomialTreeNode minNode = null;
        BinomialTreeNode prev = null;
        while (curr != null) {
            if (curr.key < min) {
                min = curr.key;
                minNode = curr;
            }
            prev = curr;
            curr = curr.rightSib;
        }

		// now prev is the node before minNode
		prev.rightSib = minNode.rightSib;
        BinomialHeap minChildren = new BinomialHeap(minNode.leftChild);
        // TODO: need to union the children heap with this one

        return min;
	}

	public BinomialHeap union(BinomialHeap heap) {
		return null;
	}

	public BinomialHeap decreaseKey(int key, int newKey) {
		return null;
	}

	public BinomialHeap delete(int key) {
		return null;
	}
}