// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    
	public int Allocate(int blockSize)
    {   
	//Search in the free block dictionary to find a block of size >= blockSize using the find function
        Dictionary ele = freeBlk.Find(blockSize,false);
    //It should return -1 if memory is not avaiable. 
        if(ele == null)
        {
             return -1;
        }
	//if blocksize is less than 1 then it must return -1
		if(blockSize<1)
		{
			 return -1;
		}
	//If found, check if splitting of the block is needed.  
    //If yes, split the block and insert the two blocks into the free and allocated blocks dictionary
    //Delete the block from the free block dictionary
     
		if(ele.key==blockSize)
        {
            allocBlk.Insert(ele.address,ele.size,ele.address);
			int a = ele.address;
            freeBlk.Delete(ele);
            return a;
        }
	//If not found,insert the block into allocated blocks dictionary and remove it from free blocks dictionary
	    else
        {
            freeBlk.Insert(ele.address + blockSize,ele.size-blockSize,ele.size - blockSize);
            allocBlk.Insert(ele.address,blockSize,ele.address);
			int a = ele.address;
            freeBlk.Delete(ele);
            return a;
        }
    }
	 
	// This function defragments the free block list (dictionary)
    // All the contiguous free blocks are merged into a single large free block
	public void Defragment() 
    {
		//Create a new BST/AVT Tree indexed by address. Use AVL/BST depending on the type.
		
        AVLTree  new_tree = new AVLTree();
		
		//Traverse all the free blocks and add them to the tree indexed by address 
        //Note that the free blocks tree will be indexed by size, therefore a new tree indexed by address needs to be created
		//Find the first block in the new tree (indexed by address) and then find the next block
		
        Dictionary curr = freeBlk.getFirst();
        while(curr !=null)
		{
            new_tree.Insert(curr.address,curr.size,curr.address);
            curr = curr.getNext();
        }
        AVLTree new_tree1 = new_tree.getFirst();
        if(new_tree1 == null)
		{
            return ;
        }
        AVLTree new_tree2 = new_tree1.getNext();
        while(new_tree2 !=null)
		{
			//If the two blocks are contiguous, then 
            //Merge them into a single block
            if((new_tree1.key + new_tree1.size) == new_tree2.key)
			{
                freeBlk.Insert(new_tree1.address , new_tree1.size+new_tree2.size , new_tree1.size+new_tree2.size);
                Dictionary stor = new BSTree(new_tree1.address , new_tree1.size , new_tree1.size);
                Dictionary loopi = new BSTree(new_tree2.address , new_tree2.size , new_tree2.size);
				//Remove the free blocks from the free list and the new dictionary
                freeBlk.Delete(stor);
                freeBlk.Delete(loopi);
				new_tree2.address = new_tree1.address;
                new_tree2.size = new_tree2.size + new_tree1.size;
                new_tree2.key = new_tree2.address;
                new_tree1 = new_tree2;
                new_tree2 = new_tree1.getNext();
            }
			else
			{
                new_tree1 = new_tree2;
                new_tree2 = new_tree2.getNext();
            }
        }
        new_tree = null;
        return;
    }
}