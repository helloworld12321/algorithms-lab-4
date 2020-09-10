import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Main {
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
      usedSpace(results[0]) + usedSpace(results[1]) + usedSpace[results[2]]
      + results[1].stream().mapToInt(i -> i).sum()
      + results[2].stream().mapToInt(i -> i).sum();
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
   * @param items The items to pack.
   *
   * @return A length-four array. The first three elements are the contents of
   * bins one, two, and three, respectively. The last element is all of the
   * left-over items that we weren't able to fit into the bins.
   */
  @SuppressWarnings("unchecked")
  public static ArrayList<Integer>[] pack(int capacity, ArrayList<Integer> items) {
    ArrayList<Integer> bin1 = new ArrayList<>();
    ArrayList<Integer> bin2 = new ArrayList<>();
    ArrayList<Integer> bin3 = new ArrayList<>();
    ArrayList<Integer> leftover = new ArrayList<>();

    ArrayList<Integer>[] bins = new ArrayList[] { bin1, bin2, bin3 }; //List of bins
    boolean[] isFull = new boolean[] { false, false, false }; //Boolean values of each bin

    int binNumber = 0;
    // As long as the bins aren't all full.
    while (!(isFull[0] && isFull[1] && isFull[2])) {
      ArrayList<Integer> currentBin = bins[binNumber];
      boolean isThisBinFull = isFull[binNumber];

      // TODO

      // Move on to the next bin.
      binNumber = (binNumber + 1) % 3;
    }
    
    return new ArrayList[] { bin1, bin2, bin3, leftover };
  }

  int usedSpace(ArrayList<Integer> bin) {
    return bin.stream().mapToInt(i -> i).sum()
  }
}
