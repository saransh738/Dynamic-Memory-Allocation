# Dynamic-Memory-Allocation
## Problem Statement:
What is Memory Allocation? Well in simple terms, it is the reservation of portions of the Computer memory for execution of processes. Thus, this system will be running on our machines and it will give out memories to the programs as requested by them (Ever heard of malloc?) The above statement should get more clear once we delve into the details of this system. So mainly there are two types of Memory allocation:
   * Static Memory Allocation: The system knows the amount of memory required in advance. From this, it can be inferred that memory allocation that would take place while defining      an Array would be static as we specify it's size earlier.
   * Dynamic Memory Allocation: The system does not exactly know the amount of memory required. And so in this case, it would get requested for memory Dynamically. Linked Lists        creation is an example of Dynamic Memory Allocation.
Now it even happens that the program that was using this memory block realizes that it has no more work with this memory block and so it decides to release this block, enabling other programs to be able to use this block. Thus, a program can even mark a memory block as free, which then becomes available for the other programs as well. Remember, we only have a limited number of resources and if programs keep asking for memory and never free that then eventually we will run out of space and the system will crash! (Again, try linking this with the Lavatory scenario to understand these situations better).

## Approach:
We will assume the memory of the system to be an array of size M (bytes), which lies between 10 million to 100 million. Each element of this array would be addressed using its index. We take the size of one memory address as 1 Byte. The Memory Allocation System will be maintaining two data structures, one for the free blocks and one for the allocated blocks. So initially, the data structure for allocated blocks will be empty and the data structure for the free blocks will be having just one element which is the entire memory.

Here, the system will be allocating memory using a variant of First Fit and Best Fit algorithm. These variants will be called First Split Fit and Best Split Fit algorithm. Here during the allocation, these algorithms will split the found free block into two segments; one block of size k (that is requested by the program) and the other block of the remaining size. For example, if the First Fit algorithm had returned a memory block of the range 5KB → 10KB for a request of 2KB, then the semantics of the First Split Fit algorithm will be to divide that 5KB block into two segments: 5KB → 7KB and 7KB → 10KB. Now, the first segment will be returned to the program that requested for memory (and marked as occupied) and other one shall still remain free. Thus you can see that we are creating holes in our free memory (thus fragmenting it) during this Split step. Thus the First Split Fit algorithm is just having this Split step on top of the First Fit algorithm before returning the block to the requesting program (and the same for Best Split Fit algorithm).

Note that the above semantics of Splitting are to be followed for both the algorithms.

Now, if we go back to the definition of 0 → 1KB, we can see that it means all the addresses in the range (0, 1024). Similarly, 1KB → 2KB will be memory addresses (1024, 1024 + 1024). Thus we can see that this range can be specified by two integers: Starting address and Size of the block. This will the way of defining a memory block in the subsequent questions. The main functionality of the Memory Allocator will be To allocate free memory and To free allocated memory. In order to solve the issue of Fragmentation, the system will also Defragment the free memory. It basically searches for consecutive free blocks and merges them into one bigger block. To illustrate this entire process better, let us go through the following example (Allocation is via First Fit Split): Initially,
````
Free Memory Blocks:[0 → 100MB] and Allocated Memory Blocks:[]
(1) After Allocate(5KB), FMB:[5KB → 100MB] and AMB:[0KB → 5KB]
(2) After Allocate(10KB), FMB:[15KB → 100MB] and AMB:[5KB → 15KB, 0KB → 5KB]1
(3) After Allocate(15KB),FMB:[30KB→100MB] and AMB:[15KB→30KB,5KB→15KB,0KB→5KB]
(4) After Free(5K), FMB:[5KB→15KB,30KB→100MB] and AMB:[15KB→30KB,0KB→5KB]
(5) After Free(0K), FMB:[0KB→5KB,5KB→15KB,30KB→100MB] and AMB:[15KB→30KB]
(6) After Defragment(), FMB:[0KB→15KB, 30KB→100MB] and AMB:[15KB→30KB]
(7) After Allocate(12KB),FMB:[12KB→15KB,30KB→100KB] and
AMB:[0KB→12KB,15KB→30KB]
````
In the above illustration, the data structure used for the Free Memory Blocks (FMB) and the Allocated Memory Blocks (AMB) is a list. So initially, the FMB list will be having one block corresponding to the memory from 0 → 100MB, while the AMB list will be empty. Then in (1) when one program requests for 5KB of memory, the First Fit algorithm will return the only memory block in FMB and the Split part for the First Split Fit will divide it into two blocks: 0 → 5KB (to be given to the program) and 5KB → 100MB (which will be a block in the FMB list). The 5kB size block will be added to the AMB list. The same thing will follow for (2) and (3). Now, in (4), the program which held the memory block which had it’s starting address at 5K (i.e 5 × 1024) tells the Allocation system that it no longer requires this block and thus frees it. Note that here, we don’t use 5KB (it is Free(5K)) because using 5KB would technically mean that it is the size of the block. (Remember that the size of one memory address is taken as 1B here). So the Allocation system will search for a Memory block which has it’s starting address at 5K, finds the block of 5KB → 15KB, and removes it from the AMB list and adds it at the front(head) of the FMB list. Same thing will follow for (5). Now think if the program had requested Allocate(12KB) without Defragment()? Remember that the notation 5KB → 15KB is just used for easier illustration.

In (6), when the Defragment() is called, the it will go through the FMB list, detect the free blocks of 0KB → 5KB and 5KB → 15KB which are adjacent and then merges them into a singe larger free block of 0KB → 15KB. Finally in (7), the System will return the first block from FMB list which is equal to or larger than 12KB size (Since it is First Split Fit). That block will be 0KB → 15KB here and then it will Split this block as described earlier and the same arguement will follow as discussed in (1). Think of what changes will happen if we used Best Split Fit algorithm rather? Try thinking of how would you implement this FMB and AMB list along with these three functions? What if we used Binary Trees rather than lists? Will a BST be better?

We are going to use an abstract data type called Dictionary to store the free memory blocks and allocated memory blocks. The definition of this data structure is given in the file Dictionary.java. The Dictionary data structure has the following six operations defined: Insert, Delete, Find, getFirst, getNext, sanity. The exact semantics of all these operations are given in the file Dictionary.java.

## Part 1:

We make use of Doubly Linked Lists in order to implement the Dictionary data structure with the abstract classes Dictionary, List, A1List. The precise semantics of the six dictionary functions Insert, Delete, Find, getFirst, getNext, sanity is given in the file List.java.

Having completed the Doubly Linked List definition, we will now use this data structure in order to program our Dynamic Memory Allocation System. For this, we provide a class DynamicMem and its associated java stub class file DynamicMem.java, which consists of the abstract class definitions and the specifications of three functions Allocate, Free, Defragment. The Public Memory Array Memory, Free Memory Blocks (FMB) freeBlk and Allocated Memory Blocks (AMB) allocBlk are members of this class. Using your A1List implementation implemented the functions Allocate, Free in the file A1DynamicMem.java.

## Part 2:

Now we will try to make your dictionary implementation more efficient so that your dynamic memory allocator can run faster. You are provided with the abstract class definitons of the class Tree and the specifications of the six dictionary functions Insert, Delete, Find, getFirst, getNext, sanity in the file Tree.java. You are supposed to implement the six functions of the dictionary using a binary search tree in the file BSTree.java.

Implement the functions Insert, Delete, Find, getFirst, getNext, sanity of the abstract class Dictionary --> Tree using binary search trees in BSTree.java.

Implement the Defragment function in A2DynamicMem using your BSTree implementation. This function should merge all the contiguous free memory blocks into one single free memory block thereby defragmenting the memory

## Part 3:

For this part we will try to further optimize your memory allocation system’s performance using the concept of AVL trees learned in the lectures. As taught in the flipped classes, AVL trees are Balanced Binary Search Trees that have the heights of a logarithmic order. The class AVLTree is derived from the class BSTree and adds a height variable. The file AVLTree.java contains the definitions of this class. For this part, we will re-implement some of the functions of dictionary to make them time efficient. You will be evaluated on the time complexity of your algorithm as well as its correctness.

To make memory allocator efficient, implement the required functions of AVLTree.java and make your binary search trees balanced. We will only implement the functions that are needed to speed up the implementation (Insert, Delete, Find, getFirst, getNext, sanity).

## Usage:

### Using makefile

`make all`

To compile your .java files

`make clean`

To remove the generated .class files

### Using run.sh

`run.sh {input_file} {output_file}`

Example: `run.sh test.in res.out`

Both arguments are optional, inputfile is the file containing the test cases and output file is where you want the result to be written into. In the case any argument is missing, console is used for input or output.

A res_gold.out has been added which can be used to compare your results against the standard results.

### Format of test file

number of test cases

size

number of commands

command1

command2

...

size (next test case)

number of commands

command1

command2

...

One sample test file test.in is attached alongside

`Allocate Size`

`Free Address`

This is the format for commands required
