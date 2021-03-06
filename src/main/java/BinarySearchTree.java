public class BinarySearchTree<T extends Comparable<T>> {
	private BinarySearchTreeNode<T> root;
	private BinarySearchTreeNode<T> lead;
	private BinarySearchTreeNode<T> tail;
    public static void main(String[] args) {

    }

    public boolean add(T data) {
		boolean add = false;
        if (root == null) {
			root = new BinarySearchTreeNode<T>(data);
			add = true;
		} else
			add = root.add(data);
        return add;
	}

	public boolean remove(T data) {
		int originalSize=root.size();
        if (root != null)
			root = root.remove(data);

		return root.size()<originalSize;
	}

	public boolean contains(T data) {
		return root !=null && root.contains(data);
	}
	public void clear() {
        root = null;
	}

	public int size() {
		if(root==null)
            return 0;
        return root.size();
	}
	public int depth() {
		if (root == null)
			return -1;
		else
			return root.depth();
	}
	public String toString() {
		return "[" + (root == null ? "" : root.toString()) + "]";
	}
}
