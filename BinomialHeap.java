import java.util.*;

public class BinomialHeap {
	
	class BinomialTreeNode {
		int key;
		BinomialTreeNode leftChild;
		BinomialTreeNode rightSib;
		BinomialTreeNode parent;
		int degree;

		public BinomialTreeNode(int key) {
			leftChild = null;
			rightSib = null;
			parent = null;
			degree = 0;
		}


	}

	BinomialTreeNode root;
	int size;

	public BinomialHeap() {
		this.root = null;
		this.size = 0;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public BinomialHeap insert(int key) {
		return null;
	}

	public int findMin() {
		return 0;
	}

	public int extractMin() {
		return 0;
	}

	public BinomialHeap union(BinomialHeap heap1, BinomialHeap heap2) {
		return null;
	}

	public BinomialHeap decreaseKey(int key, int newKey) {
		return null;
	}

	public BinomialHeap delete(int key) {
		return null;
	}
}