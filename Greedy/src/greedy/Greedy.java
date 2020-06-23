package greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author MAHMOUD
 */
public class Greedy {

    public static void main(String[] args) {
        ArrayList<Item> items = new ArrayList<>();
        Random rand = new Random();
        int maxWeight = 80;
        
        for (int i = 0; i < 30; i++) {
            items.add(new Item(rand.nextInt(maxWeight - 1) + 1,rand.nextInt(maxWeight - 1) + 1));
        }
        
        long startTime = System.currentTimeMillis();
        int maxValue = solve(items, maxWeight);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        
        for (int i = 0; i < items.size(); i++) {
            System.out.println("item number " + i + ":\nweight: " + items.get(i).getWeight() + "\tprofit: " + items.get(i).getProfit());
        }
        
        System.out.println("max value obtained: " + maxValue);
        System.out.println("elapsed time in ms: " + elapsedTime);
    }

    public static int solve(List<Item> items, int maxWeight) {
        Collections.sort(items);
        int currentWeight = 0;
        int currentProfit = 0;
        for (int i = 0; i < items.size(); i++) {
            currentWeight += items.get(i).getWeight();
            
            if(currentWeight <= maxWeight)
                currentProfit += items.get(i).getProfit();
            else
                break;
        }
        
        return currentProfit;
    }

}

class Item implements Comparable<Item> {

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

    @Override
    public int compareTo(Item o) {
        if (this.mProfit / this.mWeight < (o.getProfit() / o.getWeight())) {
            return -1;
        } else if (this.mProfit / this.mWeight > (o.getProfit() / o.getWeight())) {
            return 1;
        } else {
            return 0;
        }
    }
}
