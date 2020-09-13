import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Main {
  /**
   * @author Cookie Vang and Joe Walbran
   */
  public static void main(String[] args) {
    System.out.println(
      "Please enter, space separated: the bins' capacity, the number of items,"
      + " and the size of each item.");
    System.out.println("Input: ");
    Scanner scanner = new Scanner(System.in);
    int capacity = scanner.nextInt();
    int numberOfItems = scanner.nextInt();
    ArrayList<Integer> items = new ArrayList<>();
    for (int i=0; i < numberOfItems; i++){
      items.add(scanner.nextInt());      
    }
    ArrayList<Integer>[] results = pack(capacity, items);
    int totalSpace = capacity * 3;
    int totalUsedSpace = //Summing total bin space
      usedSpace(results[0]) + usedSpace(results[1]) + usedSpace(results[2]);
    int totalUnusedSpace = totalSpace - totalUsedSpace;
    System.out.println("Bin one: " + results[0]);
    System.out.println("Bin two: " + results[1]);
    System.out.println("Bin three: " + results[2]);
    System.out.println("Left over: " + results[3]);
    System.out.println("Unused space: " + totalUnusedSpace + "/" + totalSpace);
  }


  /**
   * Try to efficiently pack items of different sizes into three bins, each of
   * which has the same size
   *
   * @param capacity The size of every bin.
   *
   * @param items The items to pack. (An ArrayList of **positive** integers.)
   *
   * @return A length-four array. The first three elements are the contents of
   * bins one, two, and three, respectively. The last element is all of the
   * left-over items that we weren't able to fit into the bins.
   *
   * @author Cookie Vang and Joe Walbran
   */
  @SuppressWarnings("unchecked")
  public static ArrayList<Integer>[] pack(int capacity, ArrayList<Integer> items) {
    // Make a local copy of the items ArrayList.
    items = (ArrayList<Integer>)items.clone();

    ArrayList<Integer> bin1 = new ArrayList<>();
    ArrayList<Integer> bin2 = new ArrayList<>();
    ArrayList<Integer> bin3 = new ArrayList<>();
    ArrayList<Integer> leftover = new ArrayList<>();

    // List of bins.
    ArrayList<Integer>[] bins = new ArrayList[] { bin1, bin2, bin3 }; 

    // Boolean values of each bin. (Here, "full" means that there isn't enough
    // space in the bin to put any more items. It doesn't necessarily mean that
    // the bin is at 100% space usage; just that we can't make the space usage
    // go any higher.)
    boolean[] isFull = new boolean[] { false, false, false }; 

    int binNumber = 0;
    // As long as the bins aren't all full.
    while (!(isFull[0] && isFull[1] && isFull[2])) {
      for (binNumber = 0; binNumber < 3; binNumber++) {
        if (isFull[binNumber]) {
          // We've already filled up this bin; just move to the next
          // bin.
          continue;
        }

        ArrayList<Integer> currentBin = bins[binNumber];
        int currentBinEmptySpace = capacity - usedSpace(currentBin);

        // Dummy starter value (guaranteed to be smaller than all items.)
        int largestItem = -1;
        for (int item : items) { //Ignores the index and looks at only the value at that index
          //Looks for the largest valued item in the remaining array list
          if (item <= currentBinEmptySpace && largestItem < item) { 
            largestItem = item;
          }
        }

        if (largestItem == -1) {
          // There were no items that worked; item <= currentBinEmptySpace
          // was always false.
          isFull[binNumber] = true;
        } else {
          items.remove(items.indexOf(largestItem));
          currentBin.add(largestItem);
        }
      }
    }
    //The while loop runs n amount of times (for the elements in the array)
    //Inside the while loop, the for loop will also run an n amount of times
    //So the run time would be n^2

    // All of the values remaining in the items list go into "leftover".
    leftover.addAll(items);

    return new ArrayList[] { bin1, bin2, bin3, leftover };
  }

  /**
   * @author Joe Walbran
   */
  private static int usedSpace(ArrayList<Integer> bin) {
    return bin.stream().mapToInt(i -> i).sum();
  }
}
