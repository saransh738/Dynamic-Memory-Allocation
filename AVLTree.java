 // Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree()
	{ 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) 
	{ 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
	// To calculate height of the subtree rooted at node N
	public int height(AVLTree N)
	{ 
        if (N == null) 
		{
            return 0;
		}			
		//recursively calculate the height
        return 1 + max(height(N.left),height(N.right));
    } 
	
	//To calculate maximum of two numbers
	public int max(int a, int b)
	{ 
        if(a>=b)
		{
			return a;
		}
		else
		{
			return b;
		}
    }
	
	//code for the right rotation
	public  AVLTree RIGHTROTATE(AVLTree N)
    { 
	    AVLTree E = N.left;								
        AVLTree y = N.parent;
        N.left = E.right;
        if(E.right != null)
		{
            E.right.parent = N;
        }
        if(y.left == N)
		{
            y.left = E ;
        }
        else if(y.right == N)
		{
            y.right = E ;
        }
		//swapping the contents 
        E.parent = N.parent;
        N.parent = E;
        E.right = N;
        return E ;
    } 
  
    //code for the left rotation 
    public AVLTree LEFTROTATE(AVLTree N)
	{ 
	    AVLTree E = N.right;
        AVLTree y = N.parent;
        N.right = E.left;
        if(E.left !=null)
		{
            E.left.parent = N;
        }
        if(y.left == N)
		{
            y.left = E;
        }
        else if(y.right == N)
		{
            y.right = E;
        }
		//swapping the contents
        E.parent = N.parent;
        N.parent = E;
        E.left = N;
        return E;
    }
	
	//to get balance factor
	public int getBalance(AVLTree N) 
	{ 
        return height(N.left) - height(N.right); 
    } 
	
	//for checking balance
	public void CHECKBALANCE(AVLTree n)
	{
        if( getBalance(n)>1 || getBalance(n)<-1)
		{
            REBALANCE(n);
        }
        if(n.parent == null)
		{
            return;
        }
        if(n.parent.key == -1 && n.parent.address == -1)
		{
           return;
        }
        CHECKBALANCE(n.parent);
    }
 
    //for rebalancing
    public AVLTree REBALANCE(AVLTree n)
	{
		if(getBalance(n)>1)
	    {
            if(getBalance(n.left)>0)
			{
                n = RIGHTROTATE(n);
            }
            else
			{
                n.left = LEFTROTATE(n.left);
                n = RIGHTROTATE(n);
            }
        }
        else
		{
            if(getBalance(n.right)<0)
			{
                n = LEFTROTATE(n);
            }
            else
			{
               n.right = RIGHTROTATE(n.right);
               n = LEFTROTATE(n);
            }
        }
        if(n.parent == null)
		{
            return n;
        }
		return n;
	}
	
	public AVLTree Insert(int address, int size, int key) 
    { 
        AVLTree new_node = new AVLTree(address,size,key);
		//we are travelling to root node after than insertion will take place
		//from root
		AVLTree curr = this;
		while(curr.parent!=null)
		{
			curr=curr.parent;
		}
		//actual root is right child of Sentinel node
		AVLTree root = curr.right;
		AVLTree y;
		y = null;
		//inialize root with null
		while (root != null) 
		{ 
	 
            y = root; 
			//Compare the key if less than root go left
            if (key < root.key) 
			{
                root = root.left;
			}				
			//Compare the key if greator than root go right
            else if(key > root.key)
			{
                root = root.right;
			}				
			//if keys are same then check the address
			else if(address < root.address)
			{
				root = root.left;
			}
			else if(address > root.address)
			{
				root = root.right;
			}
        } 
		//if y is null that means the tree was inially empty so make new_node as root
        if (y == null)
		{			
            curr.right = new_node;
			new_node.parent = curr;
			CHECKBALANCE(new_node);
		}			
		//if key is less than node then make it left child
        else if(key < y.key)
		{			
            y.left = new_node;
			new_node.parent = y;
			CHECKBALANCE(new_node);
		}			
		//if key is greator than node then make it right child
        else if(key > y.key)
		{
            y.right = new_node;
			new_node.parent = y;
			CHECKBALANCE(new_node);
		}			
		//same with the address
		else if(address < y.address)
		{
			y.left = new_node;
			new_node.parent = y;
			CHECKBALANCE(new_node);
		}
		else if(address > y.address)
		{
			y.right = new_node;
			new_node.parent = y;
			CHECKBALANCE(new_node);
		}
		return new_node;  	   
    }

    public boolean Delete(Dictionary e)
    {
		//traversal to the sentinel
        AVLTree curr = this;
        while(curr.parent != null)
		{
            curr = curr.parent;
        }
		//the right child is actual root
		AVLTree root;
        root = curr.right;
        while(root != null)
		{
			//element matching 
            if(root.key == e.key && root.size == e.size && root.address == e.address)
			{
				AVLTree next = root.parent;
				//first consider left child of root is null
                if(root.left == null)
				{
					//having right child also null thus child deleted has null values 
                    if(root.right == null)
					{
                        if(root.parent.right == root)
						{
                            root.parent.right = null;
                        }
                        else
						{
                           root.parent.left = null;
                        }
                        root = null;
                        CHECKBALANCE(next);
                        return true;
                    }
					//if right child is not null
                    else
					{
                        root.right.parent = root.parent;
                        if(root.parent.right == root)
						{
                            root.parent.right = root.right;
                        }
                        else
						{
                            root.parent.left = root.right;
                        }
                        root = null;
                        CHECKBALANCE(next);
                        return true;
                    }
                }
                //now next we consider left child is not null
				//now again two cases right is null or not null
				//first consider it to be null
                else if(root.right == null)
				{
                    root.left.parent = root.parent;
                    if(root.parent.right == root)
					{
                        root.parent.right = root.left;
                    }
                    else
					{
                        root.parent.left = root.left;
                    }
                    root = null;
                    CHECKBALANCE(next);
                    return true;
                }
                else
				{
                    //if none right and left are null they both exits
				    //store it in a node and assign root.right
                    AVLTree t = root.right;
					//and search for the successor in the tree
                    while(t.left != null)
					{
                        t = t.left;
                    }
                    //finally we get the successor as t
				    //now store the t (address,key,size) in to variables as store1 , store2 , store3 and delete the successor
                    int store1 = t.key ;
					int store2 = t.address; 
				    int	store3 = t.size;
                    next = t.parent;
                    if(t.left == null)
					{
                        if(t.right == null)
						{
                            if(t.parent.right == t)
							{
                                t.parent.right = null;
                            }
                            else
							{
                               t.parent.left = null;
                            }
                            t = null;
                            root.key = store1;
                            root.address = store2;
                            root.size = store3;
                            CHECKBALANCE(next);
                            return true;
                        }
                        
                        t.right.parent = t.parent;
                        if(t.parent.right == t)
						{
                            t.parent.right = t.right;
                        }
                        else
						{
                           t.parent.left = t.right;
                        }
                        t = null;
                       
                        root.key = store1;
                        root.address = store2;
                        root.size = store3;
                       
                        CHECKBALANCE(next);
                        return true;
                    }
                }

            }
            
            if(root.key <= e.key)
			{
                root = root.right;
            }
            else
			{
                root = root.left;
            }
        }
        return false;
             
	}


	 public AVLTree Find(int key, boolean exact)
    {
		
        //The find method must be called from a root node so traverse to Sentinel
          AVLTree curr = this;
          if(curr.key !=-1)
		  {
               while(curr.key !=-1)
			   {
				   
                    curr = curr.parent;
               }
			   
			// right child of sentinel is actual root
            curr = curr.right;
          }
        AVLTree h1 = curr;
        AVLTree h2 = curr;
		// If exact is true, then performs and exact match and returns an element of the dictionary with key = k
        // and returns null if no such element exists.
        if(exact == true)
		{
            while(h1!=null)
			{
                h2 = h1;
                if(h1.key == key)
				{
                    AVLTree p = h1;
					//if key is same then we check address but we have to return with smaller address
					//so go on left side
                    while(h1.left != null && h1.left.key == key)
					{
                        
                            p = h1.left;
                            h1 = h1.left;    
                       
                    }
                    return p;
                }
				//if key is less than root.key then go left
                else if(h1.key>key)
				{
                    h1 = h1.left;
                }
				//if key is greator than root.key then go right
                else
				{
                    h1 = h1.right;
                }
            }
        }
		// If exact is false, performs an approximate search and.
        // returns the element with SMALLEST key such that key >= k in the tree.Returns null in case no such element found.
		// it same as return the successor of k in the tree
        else
		{
            AVLTree succ = null;
            while(h1!=null)
			{
                h2 = h1;
                if(h1.key>=key)
				{
                    succ = h1;
                    h1 = h1.left;
                }
                else
				{
                    h1 = h1.right;
                }
            }
            return succ;
        }
        return null;
    }
	 
	// The getFirst() returns the first (smallest) element of the BST subtree and null if the subtree is empty
	public AVLTree getFirst()
    { 
	    AVLTree curr = this;
		//Traverse to the Sentinel node
		while(curr.parent !=null)
		{
			curr = curr.parent;
		}
		//Since right child of Sentinel node is actual root 
		//if it is null then tree is empty return null
        AVLTree root = curr.right;
		if(root == null)
		{
			return null;
		}
		while(root.left!=null)
		{
			root = root.left;
		}
	    return root;
    }

    //The getNext() returns the next element after in the inorder traversal of the BST
	//next element also refer to the successor
    public AVLTree getNext()
    {
        AVLTree root = this;
		
		//If right subtree of node is not NULL, then successor lies in right subtree
		if(root.right !=null)
		{
			root = root.right;
            while(root.left!=null)
			{
				root = root.left;
			}
			return root;
        }
        //if right subtree of node if NULL then successsor is one of the ancestors, go to its parent
		//if parent is null then return null
        if(root.parent==null)
		{
            return null;
        }
        while(root.parent !=null && root.parent.right == root)
		{
            root = root.parent;
        }
        if(root.parent == null)
		{
            return null;
        }
        if(root.parent.right != root)
		{
            return root.parent;
        }
        return null;
	}
	
    public boolean sanity()
    {
		// check 1:
	    // to check that left child of Sentinel node must be null
	    AVLTree curr = this;
		//Traverse to the Sentinel node
		while(curr.parent !=null)
		{
			curr = curr.parent;
		}
		//Since left child of Sentinel node must be null 
		//if it is not null then impropertree 
		if(curr.left!=null)
		{
			return false;
		}
		return true;
	}
}





