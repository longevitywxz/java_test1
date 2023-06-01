package five;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class one {
    private static int bestValue;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入背包容量：");
        int capacity = scanner.nextInt();

        System.out.print("请输入物品数量：");
        int numItems = scanner.nextInt();

        int[] weights = generateRandomWeights(numItems);
        int[] values = generateRandomValues(numItems);
        System.out.print("物品重量：");
        for(int i=0;i<weights.length;i++){System.out.print(weights[i]+" ");}
        System.out.println();
        System.out.print("物品价值：");
        for(int i=0;i<values.length;i++){System.out.print(values[i]+" ");}
        boolean[] selected = new boolean[numItems];
        bestValue = 0;

        knapsackBacktracking(capacity, weights, values, 0, 0, selected);

        System.out.println("\n最优解：");
        for (int i = 0; i < numItems; i++) {
            if (selected[i]) {
                System.out.println("物品" + (i + 1) + ": 重量 = " + weights[i] + ", 价值 = " + values[i]);
            }
        }
        System.out.println("总价值 = " + bestValue);
    }

    private static void knapsackBacktracking(int capacity, int[] weights, int[] values, int currentWeight,
                                            int currentValue, boolean[] selected) {
        if (currentWeight > capacity) {
            return;
        }

        if (currentValue > bestValue) {
            bestValue = currentValue;
        }

        for (int i = 0; i < weights.length; i++) {
            if (!selected[i]) {
                selected[i] = true;
                knapsackBacktracking(capacity, weights, values, currentWeight + weights[i],
                        currentValue + values[i], selected);
                selected[i] = false;
            }
        }
    }

    private static int[] generateRandomWeights(int numItems) {//重量赋值
        Random random = new Random();
        int[] weights = new int[numItems];
        for (int i = 0; i < numItems; i++) {
            weights[i] = random.nextInt(10) + 1;
        }
        return weights;
    }

    private static int[] generateRandomValues(int numItems) {//价值赋值
        Random random = new Random();
        int[] values = new int[numItems];
        for (int i = 0; i < numItems; i++) {
            values[i] = random.nextInt(50) + 1;
        }
        return values;
    }
}
