// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    public BSTree Insert(int address, int size, int key)
    {
        //create new_node for insertion
		BSTree new_node = new BSTree(address,size,key);
		//we are travelling to root node after than insertion will take place
		//from root
		BSTree curr = this;
		while(curr.parent!=null)
		{
			curr=curr.parent;
		}
		//actual root is right child of Sentinel node
		BSTree root = curr.right;
		BSTree y;
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
		}			
		//if key is less than node then make it left child
        else if(key < y.key)
		{			
            y.left = new_node;
			new_node.parent = y;
		}			
		//if key is greator than node then make it right child
        else if(key > y.key)
		{
            y.right = new_node;
			new_node.parent = y;
		}			
		//same with the address
		else if(address < y.address)
		{
			y.left = new_node;
			new_node.parent = y;
		}
		else if(address > y.address)
		{
			y.right = new_node;
			new_node.parent = y;
		}
        return new_node;  	
    }

    public boolean Delete(Dictionary e)
    {
		//traversal to the sentinel
        BSTree curr = this;
        while(curr.parent!=null)
		{
            curr = curr.parent;
        }
		//the right child is actual root
		BSTree root;
        root = curr.right;
        while(root != null)
		{
			//element matching 
            if(root.key == e.key && root.size == e.size && root.address == e.address)
			{
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
                        return true;
                    }
                //right child is not null
                root.right.parent = root.parent;
                if(root.parent.right == root) 
			    {
                    root.parent.right = root.right;
                    root = null;
                    return true;
                }
                    
                root.parent.left = root.right;
                root = null;
                return true;
				
                }
                //now next we consider left child is not null
				//now again two cases right is null or not null
				//first consider it to be null
                if(root.right == null)
				{
                    root.left.parent = root.parent;
                    if(root.parent.right == root)
					{
                        root.parent.right = root.left;
                        root = null;
                        return true;
                    }
                    root.parent.left = root.left;
                    root = null;
                    return true;
                }
                //if none right and left are null they both exits
				//store it in a node and assign curr.right
                BSTree t = root.right;
				//and search for the successor in the tree
                while(t.left !=null)
				{
                    t = t.left;
                }
                //finally we get the successor as t
				//now store the t (address,key,size) in to variables as store1 , store2 , store3 and delete the successor
                int store1 = t.key ;
				int store2 = t.address;
				int store3 = t.size;
                //now to delete again cases of deletion 
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
						
                        return true;
                    }
                   
                    t.right.parent = t.parent;
                    if(t.parent.right==t)
					{
                       
                        t.parent.right = t.right;
                        t = null;
                        root.key = store1;
                        root.address = store2;
                        root.size = store3;
                        return true;
                    }
                   
                    t.parent.left = t.right;
                    t = null;
                    
                    root.key = store1;
                    root.address = store2;
                    root.size = store3;
                    
                    return true;
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

    public BSTree Find(int key, boolean exact)
    {
		
        //The find method must be called from a root node so traverse to Sentinel
          BSTree curr = this;
          if(curr.key !=-1)
		  {
               while(curr.key !=-1)
			   {
				   
                    curr = curr.parent;
               }
			// right child of sentinel is actual root
            curr = curr.right;
          }
        BSTree h1 = curr;
        BSTree h2 = curr;
		// If exact is true, then performs and exact match and returns an element of the dictionary with key = k
        // and returns null if no such element exists.
        if(exact == true)
		{
            while(h1!=null)
			{
                h2 = h1;
                if(h1.key == key)
				{
                    BSTree p = h1;
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
            BSTree succ = null;
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
	public BSTree getFirst()
    { 
	    BSTree curr = this;
		//Traverse to the Sentinel node
		while(curr.parent !=null)
		{
			curr = curr.parent;
		}
		//Since right child of Sentinel node is actual root 
		//if it is null then tree is empty return null
		if(curr.right==null)
		{
			return null;
		}
		while(curr.right.left!=null)
		{
			curr.right = curr.right.left;
		}
	    return curr.right;
    }

    //The getNext() returns the next element after in the inorder traversal of the BST
	//next element also refer to the successor
    public BSTree getNext()
    {
        BSTree curr=this;
		//If right subtree of node is not NULL, then successor lies in right subtree
		if(curr.right !=null)
		{
            while(curr.right.left!=null)
			{
				curr.right = curr.right.left;
			}
			return curr;
        }
        //if right subtree of node if NULL then successsor is one of the ancestors, go to its parent
		//if parent is null then return null
        if(curr.parent==null)
		{
            return null;
        }
        while(curr.parent !=null && curr.parent.right==curr)
		{
            curr=curr.parent;
        }
        if(curr.parent == null)
		{
            return null;
        }
        if(curr.parent.right !=curr)
		{
            return curr.parent;
        }
        return null;
	}
	
    public boolean sanity()
    {
		// check 1:
	    // to check that left child of Sentinel node must be null
	    BSTree curr = this;
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



























