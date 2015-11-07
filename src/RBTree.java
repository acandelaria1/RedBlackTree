public class RBTree {
	
	enum Color{RED, BLACK;}
	private RBNode root;
	private int count=0;
	//private RBNode nil 
	
	public static RBNode getRoot(RBTree tree){
		return tree.root;
	}
	
//	public static RBNode getRoot(RBTree tree, RBNode node){
//		RBNode currNode = node;
//		while(currNode.getParent() != null){
//			currNode = currNode.getParent();
//		}
//		return currNode;
//	}
	
	public RBNode root(){
		return this.root;
	}
	
	public int getCount(){
		return count;
	}
	
	//Print tree uses a pre-order traversal to display the contents of the tree
	public void printTree(RBNode node){
		preOrderTraversal(node);
//		System.out.println();
//		inOrderTraversal(node);
//		System.out.println();
//		postOrderTraversal(node);
	}
	
	public void preOrderTraversal(RBNode node){
		if(node != null){
			System.out.print(node + "" + node.getColor() + " ");
			preOrderTraversal(node.getLeft());
			preOrderTraversal(node.getRight());
		}
	}
	
	public void inOrderTraversal(RBNode node){
		if(node != null){
			inOrderTraversal(node.getLeft());
			System.out.print(node + " ");
			inOrderTraversal(node.getRight());
		}
	}
	
	public void postOrderTraversal(RBNode node){
		if(node != null){
			postOrderTraversal(node.getLeft());
			postOrderTraversal(node.getRight());
			System.out.print(node + " ");
		}
	}
	
	public static RBNode getSibling(RBNode node){
		if(node.getData() > node.getParent().getData()){
			return node.getParent().getLeft();
		}else return node.getParent().getRight();
	}
	
	public static RBNode getAunt(RBNode node){
		return getSibling(node.getParent());
	}
	
	public static RBNode getGrandparent(RBNode node){
		return node.getParent().getParent();
	}
	
	public static void rotateRight(RBTree tree, RBNode node){
		RBNode y = node.getLeft();
		System.out.println("GOT HERE");

		node.setLeft(y.getRight());
		if(y.getRight() != null){
			y.getRight().setParent(node);
		}
		y.setParent(node.getParent());
		if(node.getParent() == null){
			tree.root = y;
		}
		else if(node == node.getParent().right){
			node.getParent().setRight(y);
		}else{
			node.parent.left = y;
		}
		y.right = node;
		node.parent = y;
		
	}
	
	public static void rotateLeft(RBTree tree, RBNode node){
		RBNode y = node.getRight();
		node.setRight(y.getLeft());
		if(y.getLeft() != null){
			y.getLeft().setParent(node);
		}
		y.setParent(node.getParent());
		if(node.getParent() == null){
			tree.root = y;
		}
		else if(node == node.getParent().left){
			node.getParent().setLeft(y);
		}else{
			node.parent.right = y;
		}
		y.left = node;
		node.parent = y;
	}
	

	
	public static void insertFixUp(RBTree tree, RBNode node){
		RBNode currNode = node;
		RBNode y;
		while(currNode.getParent() != null && currNode.getParent().getColor() == Color.RED){
			if(currNode.getParent() == getGrandparent(currNode).getLeft()){
				y = getGrandparent(currNode).getRight();
				 if(y.getColor() == Color.RED){
					 currNode.getParent().setColor(Color.BLACK);
					 y.setColor(Color.BLACK);
					 getGrandparent(currNode).setColor(Color.RED);
					 currNode = getGrandparent(currNode);
				 }else if(currNode == currNode.getParent().getRight()){
					 currNode = currNode.getParent();
					 System.out.println("BEFORE ROTATELEFT");
					 rotateLeft(tree, currNode);
					 System.out.println("Afeter ROTATELEFT");
					 
				 }else{
					 currNode.getParent().setColor(Color.BLACK);
					 getGrandparent(currNode).setColor(Color.RED);
					 //rightRotate(getGrandparent(currNode));
				 }
				 //The outer else statement will the same but just with 
				 // right and left switched
			}else{
				//System.out.println("IN ELSE STATEMENT NOT IMPLEMENTED");
				if(currNode.getParent() == getGrandparent(currNode).getRight()){
					y = getGrandparent(currNode).getLeft();
					if(y != null && y.getColor() == Color.RED){
						currNode.getParent().setColor(Color.BLACK);
						y.setColor(Color.BLACK);
						getGrandparent(currNode).setColor(Color.RED);
						currNode = getGrandparent(currNode);
					}else if(currNode == currNode.getParent().getLeft()){
						currNode = currNode.getParent();
						rotateRight(tree, currNode);
					}else{
						currNode.getParent().setColor(Color.BLACK);
						getGrandparent(currNode).setColor(Color.RED);
						rotateLeft(tree, getGrandparent(currNode));
					}
				}
			}
		}
		//ADD CODE TO MAKE ROOT BLACK
		getRoot(tree).setColor(Color.BLACK);
		
		
	}
	public void insert(int data){
		RBNode newNode = new RBNode(data);
		RBNode parentNode = root;
		RBNode currentNode = root;
		if(root == null) root = newNode;
		else{
			while(currentNode != null){
				parentNode = currentNode;
				if( data < currentNode.getData()){
					currentNode = currentNode.getLeft();
				}else{
					currentNode = currentNode.getRight();
				}
			}//end while
			
			if(data < parentNode.getData()){
				parentNode.setLeft(newNode);
			}else{
				parentNode.setRight(newNode);
			}
			newNode.setParent(parentNode);
			newNode.setColor(Color.RED);
		}// end else
		this.count++;
		insertFixUp(this, newNode);
	}
	

	
	
		
	class RBNode{
		private Color color;
		private RBNode right, left, parent;
		private int data;
		//private boolean isLeaf;
		
		public RBNode(int data){
			this.data = data;
			this.color = Color.RED;
			//this.isLeaf = true;
		}
		
		public Color getColor(){
			return color;
		}
		
		public void setColor(Color c){
			this.color = c;
		}
		public void setParent(RBNode node){
			this.parent = node; 
		}
		
		public RBNode getParent(){
			return this.parent;
		}
		
		public void setLeft(RBNode node){
			this.left = node;
		}
		
		public void setRight(RBNode node){
			this.right = node;
		}
		
		public RBNode getLeft(){
			return this.left;
		}
		
		public RBNode getRight(){
			return this.right;
		}
		
		public void setData(int data){
			this.data = data;
		}
		
		public int getData(){
			return this.data;
		}
		
		public String toString(){
			return new String(Integer.toString(this.data));
		}
			
	}
	
	
	

	
	public static void main(String [] args){
		RBTree tree = new RBTree();
		tree.insert(1);
		tree.printTree(tree.root());
		System.out.println();
		tree.insert(2);
		tree.printTree(tree.root());
		System.out.println();
		//tree.insert(0);
		tree.printTree(tree.root());
		tree.insert(3);
		System.out.println();
		tree.printTree(tree.root());
		System.out.println();
		tree.insert(-1);
		tree.printTree(tree.root());
		
	}

}

