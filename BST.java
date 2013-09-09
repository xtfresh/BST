import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*
 *  Montek Singh
 *  Version 1.3.3.7
 *
 */
public class BST<T extends Comparable<T>> {
	
	private Node<T> root;
	private int size = 0;

	/**
	 * Adds a data entry to the BST
	 * 
	 * null is positive infinity
	 * 
	 * @param data The data entry to add
	 */
	public void add(T data) {
        Node<T> newNode = new Node<T>(data);
        if(getRoot() == null){
            setRoot(newNode);
            size++;
            return;
        }
        addHelper(newNode, getRoot());

	}
	
	private void addHelper(Node<T> node, Node<T> current) {
        if (node.getData() != null) {
            if(current.getData() != null){
                if(node.getData().compareTo(current.getData()) < 0){
                    if(current.getLeft() == null){
                        current.setLeft(node);
                        size++;
                    }
                    else
                        addHelper(node, current.getLeft());
                }
                else if(node.getData().compareTo(current.getData()) > 0){
                    if(current.getRight() == null){
                        current.setRight(node);
                        size++;
                    }
                    else
                        addHelper(node, current.getRight());
                }
            }
            else{
                if(current.getLeft() == null){
                    current.setLeft(node);
                    size++;
                    return;
                }
                else{
                    addHelper(node, current.getLeft());
                }
            }
        }
        else{
            if(current.getRight() == null){
                current.setRight(node);
                size++;
            }
            else{
                addHelper(node, current.getRight());
            }
        }
	}

	/**
	 * Adds each data entry from the collection to this BST
	 * 
	 * @param c
	 */
	public void addAll(Collection<? extends T> c) {
		Iterator<? extends T> it = c.iterator();
		while ( it.hasNext() ){
			add(it.next());
		}
	}
	
	/**
	 * Removes a data entry from the BST
	 * 
	 * null is positive infinity
	 * 
	 * @param data The data entry to be removed
	 * @return The removed data entry (null if nothing is removed)
	 */
    public T remove(T data) {
        if(root== null)
            return null;
        Node<T> temp = getRoot();
        Node<T> stay_behind = getRoot();
        if(data == null){
            while(temp != null){
                if(temp.getData() == null){
                    if(temp == getRoot()){
                        if(temp.getLeft() == null)
                            clear();
                        else{
                            setRoot(temp.getLeft());
                            size--;
                        }
                    }
                    else{
                        if(temp.getLeft() == null){
                            stay_behind.setRight(null);
                        }
                        else{
                            stay_behind.setRight(temp.getLeft());
                            temp.setLeft(null);
                        }
                        size--;
                    }
                    return null;
                }
                else{
                    stay_behind = temp;
                    temp = temp.getRight();
                }
            }
        }
        else{
            while(temp != null){
                if(data.compareTo(temp.getData()) > 0){
                    stay_behind = temp;
                    temp = temp.getRight();
                }
                else if(data.compareTo(temp.getData()) < 0){
                    stay_behind = temp;
                    temp = temp.getLeft();
                }
                else{
                    if(temp.getLeft() == null && temp.getRight() == null){
                        if(stay_behind.getLeft() == temp)
                            stay_behind.setLeft(null);
                        else
                            stay_behind.setRight(null);
                        size--;
                    }
                    else if(temp.getLeft() == null || temp.getRight() == null){
                        if(temp.getLeft() == null){
                            if(stay_behind.getRight() == temp)
                                stay_behind.setRight(temp.getRight());
                            else
                                stay_behind.setLeft(temp.getRight());
                            temp.setRight(null);
                        }
                        else {
                            if(stay_behind.getRight() == temp)
                                stay_behind.setRight(temp.getLeft());
                            else
                                stay_behind.setLeft(temp.getLeft());
                            temp.setLeft(null);
                        }
                        size--;
                    }
                    else
                        removeHelper(stay_behind, temp);

                    return data;
                }
            }
        }
        return data;
    }

    private void removeHelper(Node<T> prevNode, Node<T> current){
        T max = current.getLeft().getData();
        Node<T> mover = current.getLeft();
        Node<T> pred = mover;
        while(mover.getRight() != null){
            pred = mover;
            mover = mover.getRight();
            max = mover.getData();
        }
        if(mover == current.getLeft()){
            current.setData(max);
            if(mover.getLeft() == null)
                current.setLeft(null);
            else{
                current.setLeft(mover.getLeft());
                mover.setLeft(null);
            }
            size--;
            return;
        }
        current.setData(max);
        if(mover.getLeft() == null)
            pred.setRight(null);
        else{
            pred.setRight(mover.getLeft());
            mover.setLeft(null);
        }
        size--;
    }



	
	/**
	 * Checks if the BST contains a data entry
	 * 
	 * null is positive infinity
	 * 
	 * @param data The data entry to be checked
	 * @return If the data entry is in the BST 
	 */
	public boolean contains(T data) {
        if(data != null) {
            Node<T> temp = root;
            while(temp != null){
                if(temp.getData() != null){
                    if(data.compareTo(temp.getData()) == 0)
                        return true;
                    else if(data.compareTo(temp.getData()) < 0)
                        temp = temp.getLeft();
                    else
                        temp = temp.getRight();
                }
                else{
                    if(temp.getLeft() != null)
                        temp = temp.getLeft();
                    else
                        break;
                }
            }
        }
        else{
            Node<T> temp = root;
            while(temp != null){
                if(temp.getData() == null)
                    return true;
                temp = temp.getRight();
            }
        }
        return false;
	}
	
	/**
	 * Finds the pre-order traversal of the BST
	 * 
	 * @return A list of the data set in the BST in pre-order
	 */
	public List<T> preOrder() {
        LinkedList<T> list = new LinkedList<T>();
        Node<T> current = root;
        if (current == null)
            return null;
        else
            preOrderHelper(current,list);
        return list;
	}
    private void preOrderHelper(Node<T> node, LinkedList<T> list){
       list.add(node.getData());
       if(node.getLeft() != null)
           preOrderHelper(node.getLeft(), list);
       if(node.getRight() != null)
           preOrderHelper(node.getRight(),list);

    }


	/**
	 * Finds the in-order traversal of the BST
	 * 
	 * @return A list of the data set in the BST in in-order
	 */
	public List<T> inOrder() {
        LinkedList<T> list = new LinkedList<T>();
        Node<T> current = root;
        if (current == null)
            return null;
        else
            inOrderHelper(current,list);
        return list;
	}

    private void inOrderHelper(Node<T> node, LinkedList<T> list){
        if(node.getLeft() != null)
            inOrderHelper(node.getLeft(), list);
        list.add(node.getData());

        if(node.getRight() != null)
            inOrderHelper(node.getRight(),list);


    }


    /**
	 * Finds the post-order traversal of the BST
	 * 
	 * @return A list of the data set in the BST in post-order
	 */
	public List<T> postOrder(){
        LinkedList<T> list = new LinkedList<T>();
        Node<T> current = root;
        if (current == null)
            return null;
        else
            postOrderHelper(current,list);
        return list;
    }

    private void postOrderHelper(Node<T> node, LinkedList<T> list){
        if(node.getLeft() != null)
            postOrderHelper(node.getLeft(), list);
        if(node.getRight() != null)
            postOrderHelper(node.getRight(),list);
        list.add(node.getData());


    }


	
	/**
	 * Checks to see if the BST is empty
	 * 
	 * @return If the BST is empty or not
	 */
	public boolean isEmpty() {
		if(root==null)
            return true;
        return false;
	}
	
	/**
	 * Clears this BST
	 */
	public void clear() {
        setRoot(null);
        size = 0;
	}
	
	/**
	 * @return the size of this BST
	 */
	public int size() {
		return size;
	}
	
	/**
	 * First clears this BST, then reconstructs the BST that is
	 * uniquely defined by the given preorder and inorder traversals
	 * 
	 * (When you finish, this BST should produce the same preorder and
	 * inorder traversals as those given)
	 * 
	 * @param preorder a preorder traversal of the BST to reconstruct
	 * @param inorder an inorder traversal of the BST to reconstruct
	 */
	public void reconstruct(List<? extends T> preorder, List<? extends T> inorder) {
        clear();
        if(preorder == null && inorder == null){
            return;
        }else if(inorder == null){
            addAll(preorder);
        }
		
	}
	
	/*
	 * The following methods are for grading, do not modify them
	 */
	
	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	public static class Node<K extends Comparable<K>> {
		
		private K data;
		private Node<K> left, right;
		
		public Node(K data) {
			setData(data);
		}

		public K getData() {
			return data;
		}

		public void setData(K data) {
			this.data = data;
		}
		
		public Node<K> getLeft() {
			return left;
		}
		
		public void setLeft(Node<K> left) {
			this.left = left;
		}
		
		public Node<K> getRight() {
			return right;
		}
		
		public void setRight(Node<K> right) {
			this.right = right;
		}
	}

}
