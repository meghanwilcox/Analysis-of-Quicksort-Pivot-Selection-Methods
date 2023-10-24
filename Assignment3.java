
package assignment3;
/* 
Author: Meghan Wilcox
Class: CSC 330
Assignment 3: Divide and Conquer Algorithms
Description: This program implements a quicksort algorithm to sort an array of random integers. 
             The quicksort algorithm will include implementations for 3 pivot picking strategies:
                1. median of 3
                2. middle element
                3. random element
             The program uses an array of 15 random integers for testing. 
             The program prints out an array each time after partioning is done, but before the recursive calls to Quicksort. 
             The program times each of the three implementations of quicksort for arrays of increasing size. 
             Finally, the program plots a simple graph comparing the performance of the 3 implementations of quicksort. 
*/

import java.math.*;

public class Assignment3 {
    
    //constants for the types of pivot selection methods and their integer values
    public static final int MEDIAN_OF_3 = 1;
    public static final int MIDDLE_ELEMENT = 2;
    public static final int RANDOM = 3;    
    
    /*quickSort: main quicksort method, sorts based on specified pivot selection via integer input*/
    public static int quickSort(int[] arr, int low, int high, int pivotChoice, boolean printYOrN, int counter){
        if (low < high) {
            if(printYOrN){
                printArray(arr);
                System.out.println();
            }
            counter+= 1;
            int pivotIndex = selectPivot(arr, low, high, pivotChoice);
                       
            counter = quickSort(arr, low, pivotIndex - 1, pivotChoice, printYOrN, counter);
            counter = quickSort(arr, pivotIndex + 1, high, pivotChoice, printYOrN, counter);
        }
        return counter;
    }
    
    /*medianOf3Partition: selects the pivot as the median of the lowest, highest, and middle elements, helper method for the quicksort method*/
    public static int medianOf3Partition(int[] arr, int low, int high) {
        // Find the median of the first, middle, and last elements
        int mid = (low + high) / 2;
        int medianIndex;

        if ((arr[low] <= arr[mid] && arr[mid] <= arr[high]) || (arr[high] <= arr[mid] && arr[mid] <= arr[low])) {
            medianIndex = mid;
        } else if ((arr[mid] <= arr[low] && arr[low] <= arr[high]) || (arr[high] <= arr[low] && arr[low] <= arr[mid])) {
            medianIndex = low;
        } else {
            medianIndex = high;
        }

        // Swap the median element with the last element to use it as the pivot
        swap(arr, medianIndex, high);

        int pivot = arr[high];

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        // Swap the pivot back to its correct position
        swap(arr, i + 1, high);
        return i + 1;
    }
    
    /*middlePartition: selects the pivot to be the middle element of the array, helper method for the quicksort method*/
    public static int middlePartition(int[] arr, int low, int high) {
        // Choose the middle element as the pivot
        int mid = (low + high) / 2;
        int pivot = arr[mid];

        // Swap the pivot with the last element to simplify the algorithm
        swap(arr, mid, high);

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        // Swap the pivot back to its correct position
        swap(arr, i + 1, high);
        return i + 1;
    }
    
    /*randomPartition: selects the pivot randomly, helper method for quicksort*/
    public static int randomPartition(int[] arr, int low, int high) {
        // Generate a random index within the range [low, high]
        int randomIndex = (int) (Math.random() * (high - low + 1)) + low;

        // Swap the random element with the last element to use it as the pivot
        swap(arr, randomIndex, high);

        int pivot = arr[high];

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        // Swap the pivot back to its correct position
        swap(arr, i + 1, high);
        return i + 1;
    }
    
    /*swap: a helper method for the quicksort method, swaps 2 values*/
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /*generateRandomArray: a method to generate an array of random integers of a specified size*/
    public static int[] generateRandomArray(int size){
        //define the minimum and maximum values for array elements
        int min = -100;
        int max = 100;
        
        //create an array of the specified size
        int[] randArr = new int[size];
        
        //generate the randome elemetns and place them in the array
        for(int i = 0; i < size; i++){
            randArr[i] = (int)(Math.random() * (max - min + 1) + min);
        }
        
        //return the randomly generated array
        return randArr;
    }
    
    /*selectPivot: chooses the appropriate pivot selection method for the quicksort algorithm to use, based off and integer input*/
    private static int selectPivot(int[] array, int low, int high, int strategy){
        //select the appropriate pivot strategy for the quicksort algorithm
        switch(strategy){
            case MEDIAN_OF_3 -> {
                return medianOf3Partition(array, low, high);
            }
            case MIDDLE_ELEMENT -> {
                return middlePartition(array, low, high);
            }
            case RANDOM -> {
                return randomPartition(array, low, high);
            }
            default -> 
                throw new AssertionError("Invalid Pivot Strategy");
        }
    }
    
    /*printArray: prints the array, formatted with brackets and commas*/
    public static void printArray(int[] array){
        System.out.print("[");
        for (int i = 0; i < array.length - 1; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.print(array[array.length - 1]);
        System.out.print("]");
    }
    
    /*calculateAverageTime: takes a size and type of pivot method as inputs, runs three quicksorts and and averages them*/
    public static double calculateAverageTime(int size, int typeOfPivot){
        //time 3 iterations of the sorting algorithm
        double time = 0;
        for(int i = 0; i < 3; i++){
            //generate a random array of the specified size
            int[] array = generateRandomArray(size);
            //get the start and end time, calculate the elapsed time
            long startTime = System.nanoTime();
            quickSort(array, 0, array.length - 1, typeOfPivot, false, 0);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            //convert to milliseconds
            double milliseconds = (double) elapsedTime / 1000000.0;
            time = time + milliseconds;
        }
        
        //calculate average for all 3 iterations
        time = time / 3;
        
        //trim to 4 decimal points
        BigDecimal formattedTime = new BigDecimal(time);
        formattedTime = formattedTime.setScale(4, RoundingMode.HALF_UP);
        time = formattedTime.doubleValue();
        
        //return the average time to sort
        return time;
    }
    
    /*MAIN METHOD*/
    public static void main(String[] args) {
        //opening comments
        System.out.println("RUN:");
        System.out.println("Quicksort:");
        
        //set the condditonal for printing arrays in the quicksort to true
        boolean print = true;
        
        //generate a random array, sort using the middle element pivot selection method
        System.out.println("Middle Element Pivot:");
        //generate a random array of 15 ints
        int[] middleArray = generateRandomArray(15);
        //sort the array using the middle element pivot selection method
        quickSort(middleArray, 0, middleArray.length - 1, MIDDLE_ELEMENT, print, 0);
        
        //generate a random array, sort using the random pivot selection method
        System.out.println("Random Pivot:");
        //generate a random array of 15 ints
        int[] randomArray = generateRandomArray(15);
        //sort the array using the random pivot selection method
        quickSort(randomArray, 0, randomArray.length - 1, RANDOM, print, 0);
        
        //generate a random array, sort using the median of 3 pivot selection method
        System.out.println("Median of 3 Pivot:");
        //generate a random array of 15 ints
        int[] medianOf3Array = generateRandomArray(15);
        //sort the array using the median of 3 pivot selection method
        quickSort(medianOf3Array, 0, medianOf3Array.length - 1, MEDIAN_OF_3, print, 0);
        System.out.println();
        
        System.out.println("***Quick Sort Benchmark***");
        System.out.println("***Milliseconds***");
        System.out.println();
        
        //calculate the average time for the middle pivot selections, store the times in an array
        double[] middleAverages = new double[12];
        int j = 0;
        for(int i = 4; i < 16; i++){
            double time = calculateAverageTime((int)Math.pow(2, i), MIDDLE_ELEMENT);
            middleAverages[j] = time;
            j++;
        }
        
        //calculate the average time for the median of 3 pivot selections, store the times in an array
        double[] medianOf3Averages = new double[12];
        int k = 0;
        for(int i = 4; i < 16; i++){
            double time = calculateAverageTime((int)Math.pow(2, i), MEDIAN_OF_3);
            medianOf3Averages[k] = time;
            k++;
        }
        
        //calculate the average time for the random pivot selections, store the times in an array
        double[] randomAverages = new double[12];
        int m = 0;
        for(int i = 4; i < 16; i++){
            double time = calculateAverageTime((int)Math.pow(2, i), RANDOM);
            randomAverages[m] = time;
            m++;
        }
        
        //print the averages in a table like format
        int N = 4;
        System.out.println("N      Median of 3    Random      Middle Element");
        for(int i = 0; i < 12; i++){
            System.out.println((int)Math.pow(2, N) + "      " + medianOf3Averages[i] + "        " + randomAverages[i] + "        " + middleAverages[i]);
            N++;
        }    
        
        //average the averages collected proviosuly to find the average runtime for the median of 3 pivot selection method
        double totalAverageMedianOf3 = 0;
        for(int i = 0; i < medianOf3Averages.length; i++){
            totalAverageMedianOf3 = totalAverageMedianOf3 + medianOf3Averages[i];
        }    
        totalAverageMedianOf3 = totalAverageMedianOf3 / 12;
        //format to 4 decimal places
        BigDecimal formattedMedianOf3 = new BigDecimal(totalAverageMedianOf3);
        formattedMedianOf3 = formattedMedianOf3.setScale(4, RoundingMode.HALF_UP);
        totalAverageMedianOf3 = formattedMedianOf3.doubleValue();
        
        //average the averages collected proviosuly to find the average runtime for the middle element pivot selection method
        double totalAverageMiddle = 0;
        for(int i = 0; i < middleAverages.length; i++){
            totalAverageMiddle = totalAverageMiddle + middleAverages[i];
        }    
        totalAverageMiddle = totalAverageMiddle / 12;
        //format to 4 decimal places
        BigDecimal formattedMiddle = new BigDecimal(totalAverageMiddle);
        formattedMiddle = formattedMiddle.setScale(4, RoundingMode.HALF_UP);
        totalAverageMiddle = formattedMiddle.doubleValue();
          
        //average the averages collected proviosuly to find the average runtime for the random pivot selection method
        double totalAverageRandom = 0;
        for(int i = 0; i < randomAverages.length; i++){
            totalAverageRandom = totalAverageRandom + randomAverages[i];
        }    
        totalAverageRandom = totalAverageRandom / 12;
        //format to 4 decimal places
        BigDecimal formattedRandom = new BigDecimal(totalAverageRandom);
        formattedRandom = formattedRandom.setScale(4, RoundingMode.HALF_UP);
        totalAverageRandom = formattedRandom.doubleValue();
        
        //display the averages in a formatted way in the table 
        System.out.println("Average:   " + totalAverageMedianOf3 + "        " + totalAverageRandom + "        " + totalAverageMiddle);
        System.out.println();
        
        //based on the minimum average, output the most efficient pivot selection method
        String min = "";
        if(Math.min(Math.min(totalAverageRandom, totalAverageMiddle), totalAverageMedianOf3) == totalAverageRandom){
            min = " Random ";
        }
        else if(Math.min(Math.min(totalAverageRandom, totalAverageMiddle), totalAverageMedianOf3) == totalAverageMiddle){
            min = " Middle ";
        }
        else{
            min = " Median of 3 ";
        }
        System.out.println("Fastest Quicksort on average:" + min + "pivot.");
    }
}
    

