# Analysis-of-Quicksort-Pivot-Selection-Methods

This program performs an analyis of the Quicksort sorting algorithm's efficiency when using various pivot selection methods. These methods are as follows: 

1. Median of 3
2. Random Element
3. Middle Element
   
The program analyzes the effieciency of the algorithm by timing the sorting function for increasing sizes of arrays, between 2^4 and 2^15 elements. The program runs the sort 3 times for each power of 2 between 2^4 and 2^15, generating a new array of 15 random intgers to sort each time. The 3 sorts are timed in milliseconds, rounded to 4 decimal places, and averaged together. This average is stored in array and displayed to the user in a table format. 

After each pivot selection algorithm has found the average times for each size, the averages for each pivot selection method are averaged together to see which pivot selection method is most efficient across various sizes of arrays. The overall averages are displayed to the user, as well as the most efficient pivot selection method. 
