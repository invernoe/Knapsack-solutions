package bruteforce;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author MAHMOUD
 */
public class BruteForce {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Item> items = new ArrayList<>();
        Random rand = new Random();
        int maxWeight = 50;
        
        for (int i = 0; i < 20; i++) {
            items.add(new Item(rand.nextInt(maxWeight - 1) + 1,rand.nextInt(maxWeight - 1) + 1));
        }
        
        long startTime = System.currentTimeMillis();
        solve(items, maxWeight);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("elapsed time in ms: " + elapsedTime);
    }

    public static void solve(List<Item> items, int maxWeight) {
        int permutations = (int) Math.pow(2, items.size());
        int max = 0;
        int maxIndex = 0;

        for (int i = 0; i < permutations; i++) {
            int[] itemsTaken = createItemsArray(items.size(), i);
            int currentProfit = calcFitness(items, itemsTaken, maxWeight);

            if (currentProfit > max) {
                max = currentProfit;
                maxIndex = i;
            }
        }

        System.out.print("max profit: " + max + "\nitem subset: [");
        int[] maxSubset = createItemsArray(items.size(), maxIndex);
        for (int i = 0; i < maxSubset.length - 1; i++) {
            System.out.print(maxSubset[i] + ", ");
        }
        System.out.println(maxSubset[maxSubset.length - 1] + "]");
    }

    /**
     *
     * @param items
     * @param itemsTaken
     * @param maxWeight
     * @return
     */
    public static int calcFitness(List<Item> items, int[] itemsTaken, int maxWeight) {
        int totalProfit = 0;
        int totalWeight = 0;

        for (int i = 0; i < itemsTaken.length; i++) {
            if (itemsTaken[i] == 1) {
                totalProfit += items.get(i).getProfit();
            }
        }

        for (int i = 0; i < itemsTaken.length; i++) {
            if (itemsTaken[i] == 1) {
                totalWeight += items.get(i).getWeight();
            }
        }

        if (totalWeight > maxWeight) {
            return -1;
        }

        return totalProfit;
    }
    
    public static int[] createItemsArray(int arraySize, int n) {
        int[] returnArray = new int[arraySize];
        String s = Integer.toBinaryString(n);
        
        int k = 0;
        for (int i = arraySize - 1; i >= arraySize - s.length(); i--) {
            returnArray[i] = Character.getNumericValue(s.charAt(s.length()- k - 1));
            k++;
        }
        
        for (int i = 0; i < arraySize - s.length(); i++) {
            returnArray[i] = 0;
        }

        return returnArray;
    }
}

    class Item {

        private int mWeight;
        private int mProfit;

        Item(int weight, int profit) {
            mWeight = weight;
            mProfit = profit;
        }

        public int getProfit() {
            return mProfit;
        }

        public int getWeight() {
            return mWeight;
        }
    }