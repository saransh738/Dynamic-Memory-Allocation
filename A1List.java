// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List
 {

    private A1List next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) 
    { 
        
        super(address, size, key);
    }
    
    public A1List()
    {
        super(-1,-1,-1);// This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }
    // Should insert the element in the DLL after the current node.
    // Should return the corresponding element created and inserted.
    public A1List Insert(int address, int size, int key)
    {
        //create a new node
        A1List new_node = new A1List(address,size,key);
		//if the current element is the tail Sentinel 
		if(this.next==null)
		{
			return null;
		}
        this.next.prev = new_node;
        new_node.next = this.next;
        this.next = new_node;
        new_node.prev = this;
        return new_node;
    }
	//Deletes the entry corresponding to d from the DLL.
    public boolean Delete(Dictionary d) 
    {
	// deletion is bidirectional it checks key and addresss both during deletion of a block
    // Searches for the d.key and d.address in the DLL
    // Can be called with any node in the DLL. So this function should search forward as well as backwards.
        int ke = d.key; // store d.key
        int addre = d.address; // stores d.address
        A1List curr = this; // stores current element
        A1List nexty = this.next; // stores next element
        while(curr != null)
		{
			// Deletes the element it is found in the DLL and returns true. 
            if(curr.key == ke && curr.address == addre)
			{
               curr.prev.next = curr.next;
               curr.next.prev = curr.prev;
               curr = null;
               return true;
            }
            else
			{
                curr = curr.prev;
            }
        }
        while(nexty != null)
		{
            if(nexty.key == ke && nexty.address == addre)
			{
               nexty.prev.next = nexty.next;
               nexty.next.prev = nexty.prev;
               nexty = null;
               return true;
            }
            else
			{
                nexty= nexty.next;
            }
	    }
    // Returns false if d not found in the DLL.
        return false;
    }
    // Searches for the key k in the DLL. 
    public A1List Find(int k, boolean exact)
    { 
        A1List curr = this;
        while(curr.prev!=null)
        {
            curr=curr.prev;
        }
        //If exact is true, then performs and exact match and returns 
        //an element of the dictionary with key = k
        if(exact==true)
        {
            while( curr.next!=null && curr.next.key!=k )
            {
                curr=curr.next;
            }
            if(curr.next!=null)
            {
                return curr.next;
            }
        }
        //If exact is false, performs an approximate search and
        // returns an element with key >= k in the dictionary.  
        if(exact==false)
        {
            while( curr.next!=null && curr.next.key<k )
            {
                curr=curr.next;
            } 
            if(curr.next!=null)
            {
                 return curr.next;
            }
        }
        //if no element greater than k or element not present
        return null;
    }
	// The getFirst and getNext functions are for traversal of the dictionary implemented using List. 
    // getFirst() returns the first element of the list and null if the List is empty.
    public A1List getFirst()
    {
        A1List curr = this;
        while(curr.prev!=null)
        {
             curr=curr.prev;    
        }
        //we reached the head sentinel 
        //we have to return the element after head sentinel
        //if the element after head sentinel is tail sentinel then return null
        if(curr.next.next==null)
        {       
            return null;
        }
        return curr.next;
    }
	//getNext() returns the next element of the list.
    public A1List getNext() 
    {
        A1List curr = this;
        // if node on which getNext is called is tailSentinel then it must return null
        if(curr.next==null)
        {
            return null;
         }
         // if next node is tailSentinel then it must return null  
        if(curr.next.next==null)
        { 
           return null;
        }
        return curr.next;
    }
    // Checks the sanity of the dictionary and returns true if sane, false otherwise
    public boolean sanity()
    {
		A1List curr = this;
		//For any node, which is not head or tail Sentinel if node.next.prev != node, then sanity test should fail.
		if(curr.next!=null || curr.prev!=null)
		{
			if(curr.next.prev != curr)
			{
				return false;
			}
		}
		//If the prev of the head Sentinel node or next of the tail Sentinel node is not null, then the sanity test should fail.
		if((curr.getFirst()).prev.prev!=null )
		{
			    return false;
		}
		//Detect a loop in a linked list	
		A1List slow = this.getFirst();
		A1List fast = this.getFirst();
		int flag=0;
		while(slow!=null && fast!=null && fast.next!=null)
		{
			slow = slow.next;
			fast = fast.next.next;
			if(fast.prev == slow.prev || slow == fast)
			{
				flag=1;
				break;
			}
		}
		if(flag==1)
		{
			return false;
		}
		else
		{
			//if next of tail Sentinel node is not null
			if(fast.next.next!=null)
			{
				return false;
			}	
		}
		return true;
    }
}





