package divideandconquer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author MAHMOUD
 */
public class DivideAndConquer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Item> items = new ArrayList<>();
        Random rand = new Random();
        int maxWeight = 50;
        
        for (int i = 0; i < 140; i++) {
            items.add(new Item(rand.nextInt(maxWeight - 1) + 1,rand.nextInt(maxWeight - 1) + 1));
        }
        
        long startTime = System.currentTimeMillis();
        int maxValue = knapsackSolve(items, items.size(), maxWeight);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        
        for (int i = 0; i < items.size(); i++) {
            System.out.println("item number " + i + ":\nweight: " + items.get(i).getWeight() + "\tprofit: " + items.get(i).getProfit());
        }
        
        System.out.println("max value obtained: " + maxValue);
        System.out.println("elapsed time in ms: " + elapsedTime);
    }
    
    public static int knapsackSolve(List<Item> items, int numberOfItems, int maxWeight){
        if(maxWeight == 0)
            return 0;
        else if(numberOfItems == 0)
            return 0;
        
        if(items.get(numberOfItems - 1).getWeight() > maxWeight){
            return knapsackSolve(items, numberOfItems - 1, maxWeight);
        }
        
        int number1 = knapsackSolve(items, numberOfItems - 1, maxWeight);
        int number2 = knapsackSolve(items, numberOfItems - 1, maxWeight - items.get(numberOfItems - 1).getWeight()) + items.get(numberOfItems - 1).getProfit();
        
        if(number1 > number2)
            return number1;
        return number2;
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
