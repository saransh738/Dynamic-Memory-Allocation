// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem 
{
      
    public A1DynamicMem() 
    {
        super();
    }

    public A1DynamicMem(int size) 
    {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) 
    {
        super(size, dict_type);
    }

    public void Defragment() 
    {
		
        return ;
    }

    //In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    //Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).
    //This function should allocate a contiguous array of size blockSize and return the first address.
    public int Allocate(int blockSize)
    {   
	//Search in the free block dictionary to find a block of size >= blockSize using the find function
        Dictionary ele = freeBlk.Find(blockSize,false);
    //It should return -1 if memory is not avaiable. 
        if(ele==null)
        {
             return -1;
        }
	//if blocksize is less than 1 then it must return -1
		if(blockSize<1)
		{
			 return -1;
		}
	//If found, check if splitting of the block is needed.  
    // If yes, split the block and insert the two blocks into the free and allocated blocks dictionary
    // Delete the block from the free block dictionary
     
		if(ele.key==blockSize)
        {
            allocBlk.Insert(ele.address,ele.size,ele.address);
            freeBlk.Delete(ele);
            return ele.address;
        }
	//If not found,insert the block into allocated blocks dictionary and remove it from free blocks dictionary
	    else
        {
            freeBlk.Insert(ele.address + blockSize,ele.size-blockSize,ele.size-blockSize);
            allocBlk.Insert(ele.address,blockSize,ele.address);
            freeBlk.Delete(ele);
            return ele.address;
        }
    }
    //This function should free the memory block starting at the startAddr
	//return 1 if successful in freeing a block else return 0
    public int Free(int startAddress) 
    {  
        Dictionary ele = allocBlk.Find(startAddress,true);
        if(ele==null)
        {
        	return -1;
        }
		//Add the block to free blocks list (dictionary)
		//Delete the bock from the allocated blocks list (dictionary)

	    else
        {
            freeBlk.Insert(startAddress,ele.size,ele.size);
            allocBlk.Delete(ele);
            return 0;
        }
    }
}




