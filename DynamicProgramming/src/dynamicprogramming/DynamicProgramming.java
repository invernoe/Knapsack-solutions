package dynamicprogramming;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author MAHMOUD
 */
public class DynamicProgramming {
    private static int[][] DPsolutions;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Item> items = new ArrayList<>();
        Random rand = new Random();
        int maxWeight = 50;
        int numberOfItems = 20;
        
        for (int i = 0; i < numberOfItems; i++) {
            items.add(new Item(rand.nextInt(maxWeight - 1) + 1,rand.nextInt(maxWeight - 1) + 1));
        }
        
        DPsolutions = new int[numberOfItems + 1][maxWeight + 1];
        
        for (int i = 0; i < numberOfItems + 1; i++) {
            for (int j = 0; j < maxWeight + 1; j++) {
                DPsolutions[i][j] = -1;
            }
        }
        
        long startTime = System.currentTimeMillis();
        int maxValue = knapsackSolve(items, items.size(), maxWeight);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        
        for (int i = 0; i < items.size(); i++) {
            System.out.println("item number " + i + ":\nweight: " + items.get(i).getWeight() + "\tprofit: " + items.get(i).getProfit());
        }
        
        System.out.println("outputted DP table: ");
        for (int i = 0; i < numberOfItems + 1; i++) {
            for (int j = 0; j < maxWeight + 1; j++) {
                System.out.print(DPsolutions[i][j] + " ");
            }
            System.out.println("");
        }
        
        System.out.println("max value obtained: " + maxValue);
        System.out.println("elapsed time in ms: " + elapsedTime);
    }
    
        public static int knapsackSolve(List<Item> items, int numberOfItems, int maxWeight){
        if(maxWeight == 0 || numberOfItems == 0){
            DPsolutions[numberOfItems][maxWeight] = 0;
            return 0;
        } else if(DPsolutions[numberOfItems][maxWeight] != -1)
            return DPsolutions[numberOfItems][maxWeight];
        
        if(items.get(numberOfItems - 1).getWeight() > maxWeight){
            DPsolutions[numberOfItems][maxWeight] = knapsackSolve(items, numberOfItems - 1, maxWeight);
            return DPsolutions[numberOfItems][maxWeight];
        }
        
        int number1 = knapsackSolve(items, numberOfItems - 1, maxWeight);
        int number2 = knapsackSolve(items, numberOfItems - 1, maxWeight - items.get(numberOfItems - 1).getWeight()) + items.get(numberOfItems - 1).getProfit();
        
        if(number1 > number2){
            DPsolutions[numberOfItems][maxWeight] = number1;
            return number1;
        }
        DPsolutions[numberOfItems][maxWeight] = number2;
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