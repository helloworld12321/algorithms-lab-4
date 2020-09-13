This is Cookie Vang and Joe Walbran's code for lab 4, as part of the University of Minnesota Morris's CSCI 3501 Algorithms and Computability class (Fall 2020).

How our algorithm works
=======================

We have three bins, numbered 1, 2, and 3. Every bin has the same capacity *c*. We also have a list of items to pack into those bins.

Our algorithm works by looping through the bins in the order 1, 2, 3, 1, 2, 3, 1, 2, 3…. In each iteration of the loop, we take the biggest item that fits into the current bin, remove it from the list of items, and place it into the current bin. If there's no item that will fit, we just move on to the next bin. Eventually, no item fits in any bin; at this point, we stop the loop and put the rest of the items in the bin called leftovers.

Note that our algorithm never removes items from bins; it only appends, without backtracking.
 
 
Test data and results
=====================

```
Test 1
Capacity: 5
Number of Items: 5
Input List: 3 4 2 5 1

Results: 
Bin one: [5]
Bin two: [4, 1]
Bin three: [3, 2]
Left over: []
Unused space: 0/15

Test 2
Capacity: 20 
Number of Items: 8
Input List: 12, 4, 8, 15, 9, 3, 1, 10

Results: 
Bin one: [15, 4, 1]
Bin two: [12, 8]
Bin three: [10, 9]
Left over: [3]
Unused space: 1/60

Test 3 
Capacity: 80
Number of Items: 10
Input List: 5 10 15 20 25 30 40 45 50

Results
Bin one: [50, 30]
Bin two: [45, 35]
Bin three: [40, 25, 15]
Left over: [5, 10, 20]
Unused space: 0/240
```

An example of when our algorithm isn't optimal
==============================================

Our algorithm misses the optimal solution whenever it would be better off
choosing a combination of smaller elements instead of the biggest possible
element. For example, if it can pack a 4 into the available space, it will
always prefer that to packing a 3 and a 3.

Consider the example wher the bins have capacity 6, and the items to pack are
`[4, 4, 4, 3, 3, 3, 3, 3, 3]`. For our example, the optimal solution would be

```
Bin one: [3, 3]
Bin two: [3, 3]
Bin three: [3, 3]
Left over: [4, 4, 4]
Unused space: 0/18
```

But instead, our algorithm gives these results:

```
Bin one: [4]
Bin two: [4]
Bin three: [4]
Left over: [3, 3, 3, 3, 3, 3]
Unused space: 6/18
```

Efficiency of Our Algorithm
===========================

The efficiency of our algorithm is big-O(n^2). Our while loop on line 69 will run at most (n+1) times.

To see this, note that each iteration of the while loop can have two results. Either it removes between one and three elements from the `items` list; or, it marks all of the bins as full, in which case it stops. In the
worst case, the while loop runs n times to remove all n items of the input
list, and then it runs one more time to mark all of the bins as full.

The for loop on line 70 will run at most 3(n+1) times since it cycles through three bins for each iteration of the while loop which as we said runs (n+1) times.

The `usedSpace` method on line 78 has interior loops. In the worst case, these loops run about ((summation of i from 0 to n-2: i) + 2) = ((n-2)(n-1)/2 + 2) = ((1/2)n^2 - (3/2)n + 3) times.

(The worst case is where two really big items get added to the first two bins, and then all of the rest of the items get added to the first bin. In that case, usedSpace loop runs as follows:

```
Bin one: 0 items
Bin two: 0 items
Bin three: 0 items
Bin one: 1 items
Bin two: 1 items
Bin three: 1 items
Bin three: 2 items
Bin three: 3 items
…
Bin three: n-2 items
```

For a total of ((summation of i from 0 to n-2: i) + 2) times.)

The for loop on line 82 will at the worst case run (summation of i from n to 0: i) = (n)(n+1)/2 = ((1/2)n^2 + (1/2)n) times since it goes through the list of inputs n items, and each time it runs it removes one item. (We're ignoring the case where it doesn't find an item that fits in the bin, since that
only adds at most 3n more iterations, which is negligible.)

Finally, the indexOf method on line 94 has an interior loop, which will run at most ((1/2)n^2 + (1/2)n) times, for the exact same reasons as the for loop on line 82.

So, in total, the number of loop iterations is (1/2n^2 - 3/2n + 3) from usedSpace, (1/2n^2 + 1/2n) from the for loop on line 82, and (1/2n^2 + 1/2n) from indexOf, for a total of (3/2)n^2 - (1/2)n + 3 iterations. So, the runtime is big-O((3/2)n^2 - (1/2)n + 3), which is big-O(n^2).

What's the maximal difference between our solution and the optimal one?
=======================================================================

As mentioned, our algorithm isn't optimal when it chooses a big item over
a combination of smaller items. Let's suppose there's just one bin. The worst mistake our algorithm can make is when it has k space left in a bin, and instead of choosing two elements of size k/2, it chooses one element of size (k/2 + epsilon), where epsilon is really small. In that case, the algorithm will add the big element and stop; the amount of unused space in that bin is k/2 - epsilon, or about k/2.

(To see that the algorithm never makes worse mistakes than this: if it has the option to choose a bunch of small elements or one big element, and
the big element is smaller than (k/2), the algorithm will choose the big element *and then always add more small elements on top of it.* It won't stop after it adds the big element.)

If the bins have capacity *c*, we can make each bin follow the worst case shown above:

```
Capacity: c
Number of Items: 9
Input List: (c/2 + epsilon) (c/2 + epsilon) (c/2 + epsilon) c/2 c/2 c/2 c/2 c/2 c/2

Results of our solution: 
Bin one: [c/2 + epsilon]
Bin two: [c/2 + epsilon]
Bin three: [c/2 + epsilon]
Left over: [c/2, c/2, c/2, c/2, c/2, c/2]
Unused space: (3/2)c - 3*epsilon, or about (3/2)c

Optimal solution:
Bin one: [c/2, c/2]
Bin two: [c/2, c/2]
Bin three: [c/2, c/2]
Left over: [c/2 + epsilon, c/2 + epsilon, c/2 + epsilon]
Unused space: 0
```

So, the maximum difference between our solution and the optimal solution is about (3/2)c, where c is the capacity of the bins. (Equivalently, the maximum difference between our solution and the optimal solution is up to but not including 50% of the total space.)