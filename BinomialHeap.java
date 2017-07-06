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
			this.leftChild = null;
			this.rightSib = null;
			this.parent = null;
			this.degree = 0;
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
		 * connect two roots list n1 and n2
		 * @param n1, start of roots list on the left
		 * @param n2, start of roots list on the right
		 */
		private void connect(BinomialTreeNode n1, BinomialTreeNode n2) {
			while (n1.rightSib != null) {
				n1 = n1.rightSib;
			}
			n1.rightSib = n2;
		}
	}

	// head of root list. Root list is ordered from low degree root to high degree root
	BinomialTreeNode roots;
	int size;

	public BinomialHeap() {
		this.roots = null;
		this.size = 0;
	}

    /**
     * Constructor to initialize a binomial heap. It traverses all the right
     * sibling of node passed in to calculate size.
     * @param roots, binomialTreeNode as the head of the roots list in the heap.
     */
	public BinomialHeap(BinomialTreeNode roots) {
	    this.roots = roots;
	    int size = 0;
	    while (roots != null) {
	        size += (int)Math.pow(2, (double)roots.degree);
	        roots = roots.rightSib;
        }
        this.size = size;
    }

	public boolean isEmpty() {
		return roots == null;
	}

	public BinomialHeap insert(int key) {
	    BinomialTreeNode node = new BinomialTreeNode(key);
	    BinomialHeap heap = new BinomialHeap(node);
		return union(heap);
	}

	public int findMin() {
		// TODO: might use exceptions instead
		if (this.roots == null) return -1;

		BinomialTreeNode curr = roots;
		int min = Integer.MAX_VALUE;
		while (curr != null) {
		    if (curr.key < min) min = curr.key;
		    curr = curr.rightSib;
        }

        return min;
	}

	public int extractMin() {
		// TODO: might use exceptions instead
		if (this.roots == null) return -1;

        BinomialTreeNode curr = roots;
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

	/**
	 * Helper function to union two binomial heap given the head of roots list
	 * @param root1 head of roots list of first binomial heap
	 * @param root2 head of roots list of second binomial heap
	 * @return the new roots with root1 root2 union together similar to binary addition
	 */
	private BinomialTreeNode union(BinomialTreeNode root1, BinomialTreeNode root2) {
		BinomialTreeNode n1 = root1;
		BinomialTreeNode n2 = root2;
		BinomialTreeNode dummy = new BinomialTreeNode(-1);
		BinomialTreeNode curr = dummy;
		BinomialTreeNode carry = null;
		// will exit while loop when either n1 or n2 reaches its end
		while (n1 != null && n2 != null) {
			int deg1 = n1.degree;
			int deg2 = n2.degree;
			if (carry == null) {
				if (deg1 == deg2) {
					carry = n1.merge(n2);
				} else if (deg1 < deg2) {
					curr.rightSib = n1;
					curr = curr.rightSib;
				} else {
					curr.rightSib = n2;
					curr = curr.rightSib;
				}
			} else {
				// if there are two Bk and a carry Bk, then merge any two of the three will work.
				if (deg1 == deg2) {
					curr.rightSib = carry;
					carry = n1.merge(n2);
				// only one Bk and a carry Bk, need to merge the two, no node at the current bit
				} else if (deg1 < deg2) {
					carry = n1.merge(carry);
				} else {
					carry = n2.merge(carry);
				}
			}

			n1 = n1.rightSib;
			n2 = n2.rightSib;
		}

		// At this point if there is carry, then since one of the root list has reaches end, each of the rest addition
		// will produce carry all through the end.
		// If there is no carry, then each of the rest addition won't produce any carry.
		while (n1 != null) {
			if (carry == null) {
				curr.rightSib = n1;
				curr = curr.rightSib;
			} else {
				carry = n1.merge(carry);
			}
			n1 = n1.rightSib;
		}

		while (n2 != null) {
			if (carry == null) {
				curr.rightSib = n2;
				curr = curr.rightSib;
			} else {
				carry = n2.merge(carry);
			}
			n2 = n2.rightSib;
		}

		curr.rightSib = carry;

		return dummy.rightSib;
	}

	public BinomialHeap union(BinomialHeap heap) {
		return new BinomialHeap(union(this.roots, heap.roots));
	}

	/**
	 * Decrease key value for a node
	 * @param node
	 * @param newKey, new decreased key for the node, must be less than original key
	 * @return new heap after performing decrease key
	 */
	public BinomialHeap decreaseKey(BinomialTreeNode node, int newKey) {
		// TODO:might be better to throw exceptions
		if (newKey > node.key) return this;
		node.key = newKey;
		while (node.parent != null && node.key < node.parent.key) {
			BinomialTreeNode tempParent = node.parent;
			BinomialTreeNode tempRight = node.rightSib;
			BinomialTreeNode tempChild = node.leftChild;
			int tempDeg = node.degree;

			node.rightSib = node.parent.rightSib;
			node.parent.rightSib = tempRight;

			node.degree = node.parent.degree;
			node.parent.degree = tempDeg;

			node.leftChild.parent = node.parent;
			tempParent.parent.leftChild = node;

			node.parent = tempParent.parent;
			tempParent.leftChild = tempChild;

			node.leftChild = tempParent;
			tempParent.parent = node;
		}

		return this;
	}

	/**
	 * Delete a node from the heap
	 * @param node, the node to be deleted
	 * @return new heap after deletion
	 */
	public BinomialHeap delete(BinomialTreeNode node) {
		this.decreaseKey(node, Integer.MIN_VALUE);
		this.extractMin();
		return this;
	}
}