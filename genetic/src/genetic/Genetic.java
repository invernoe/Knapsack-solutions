/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author MAHMOUD
 */
public class Genetic {

    private static Random rand = new Random();
    private static ArrayList<int[]> currentPopulation = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Item> items = new ArrayList<>();
        int maxWeight = 50;
        int numberOfItems = 20;

        for (int i = 0; i < numberOfItems; i++) {
            items.add(new Item(rand.nextInt(maxWeight - 1) + 1, rand.nextInt(maxWeight - 1) + 1));
        }

        for (int i = 0; i < items.size(); i++) {
            System.out.println("item number " + i + ":\nweight: " + items.get(i).getWeight() + "\tprofit: " + items.get(i).getProfit());
        }

        long startTime = System.currentTimeMillis();
        solve(items, maxWeight, numberOfItems, 20, 50, 0.15);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("elapsed time in ms: " + elapsedTime);
    }

    public static void solve(List<Item> items, int maxWeight, int individualLength,
            int populationNumber, int maxGens, double mutationProb) {
        int[] bestSolution = new int[individualLength];
        int maxProfit = -1;
        generateInitialPop(populationNumber, individualLength);

        for (int i = 0; i < maxGens; i++) {
            newGen(individualLength, mutationProb, items, maxWeight, populationNumber);
            int[] temp = getBestIndividual(individualLength, items, maxWeight);
            int tempProfit = calcFitness(items, temp, maxWeight);
            if (tempProfit > maxProfit) {
                maxProfit = tempProfit;
                bestSolution = temp;
            }
        }

        System.out.print("max profit: " + maxProfit + "\nitem subset: [");
        for (int i = 0; i < bestSolution.length - 1; i++) {
            System.out.print(bestSolution[i] + ", ");
        }
        System.out.println(bestSolution[bestSolution.length - 1] + "]");
    }

    public static void generateInitialPop(int populationNumber, int individualLength) {
        for (int i = 0; i < populationNumber; i++) {
            int[] individual = new int[individualLength];
            for (int j = 0; j < individualLength; j++) {
                individual[j] = rand.nextInt(2);
            }
            currentPopulation.add(individual);
        }
    }

    public static int[] crossOver(int[] cand1, int[] cand2, int individualLength) {
        int crossoverPoint = rand.nextInt(individualLength);
        int[] child = new int[individualLength];
        for (int i = 0; i < crossoverPoint; i++) {
            child[i] = cand1[i];
        }
        for (int i = crossoverPoint; i < individualLength; i++) {
            child[i] = cand2[i];
        }

        return child;
    }

    public static int[] mutation(int[] cand, int individualLength, double mutationProb) {
        for (int i = 0; i < individualLength; i++) {
            if (mutationProb > rand.nextDouble()) {
                if (cand[i] == 1) {
                    cand[i] = 0;
                } else {
                    cand[i] = 1;
                }
            }
        }

        return cand;
    }

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

    public static int[] getBestIndividual(int individualLength, List<Item> items, int maxWeight) {
        int bestProfit = -1;
        int[] bestIndividual = new int[individualLength];

        for (int i = 0; i < currentPopulation.size(); i++) {
            int temp = calcFitness(items, currentPopulation.get(i), maxWeight);
            if (temp > bestProfit) {
                bestProfit = temp;
                bestIndividual = currentPopulation.get(i);
            }
        }

        return bestIndividual;
    }

    public static void newGen(int individualLength, double mutationProb, List<Item> items, int maxWeight,
             int populationNumber) {
        int[] candidate1 = getBestIndividual(individualLength, items, maxWeight);
        currentPopulation.remove(candidate1);
        int[] candidate2 = getBestIndividual(individualLength, items, maxWeight);
        currentPopulation.clear();

        for (int i = 0; i < populationNumber; i++) {
            int[] temp1 = crossOver(candidate1, candidate2, individualLength);
            int[] temp2 = mutation(temp1, individualLength, mutationProb);
            currentPopulation.add(temp2);
        }
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