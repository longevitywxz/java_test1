package six;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TaskAssignment {
    private static int[][] costMatrix; // 成本矩阵
    private static int[] assignment; // 分配方案
    private static int[] minAssignment; // 最优分配方案
    private static int minCost; // 最小总成本
    private static boolean[] assignedTasks; // 标记任务是否已分配


     // 生成随机成本矩阵
    private static int[][] generateCostMatrix(int n) {
        int[][] matrix = new int[n][n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = random.nextInt(10) + 1; // 生成1~10的随机数
            }
        }

        return matrix;
    }

    // 分支限界法
    private static void branchAndBound(int task, int totalCost) {
        if (task == costMatrix.length) {
            if (totalCost < minCost) {
                minCost = totalCost;
                System.arraycopy(assignment, 0, minAssignment, 0, assignment.length);
            }
            return;
        }

        for (int person = 0; person < costMatrix.length; person++) {
            if (!assignedTasks[person]) {
                assignedTasks[person] = true;
                assignment[task] = person;
                branchAndBound(task + 1, totalCost + costMatrix[person][task]);
                assignedTasks[person] = false;
            }
        }
    }

    // 蛮力法
    private static void bruteForce() {
        int[] permutation = new int[costMatrix.length];
        for (int i = 0; i < costMatrix.length; i++) {
            permutation[i] = i;
        }

        int[] bestAssignment = new int[costMatrix.length];
        int bestCost = Integer.MAX_VALUE;

        do {
            int totalCost = 0;
            for (int i = 0; i < costMatrix.length; i++) {
                totalCost += costMatrix[permutation[i]][i];
            }

            if (totalCost < bestCost) {
                bestCost = totalCost;
                System.arraycopy(permutation, 0, bestAssignment, 0, permutation.length);
            }
        } while (nextPermutation(permutation));

        System.out.println("蛮力法最优分配方案: " + Arrays.toString(bestAssignment));
        System.out.println("蛮力法最小总成本: " + bestCost);
    }

    // 生成下一个排列
    private static boolean nextPermutation(int[] permutation) {
        int i = permutation.length - 2;
        while (i >= 0 && permutation[i] >= permutation[i + 1]) {
            i--;
        }

        if (i < 0) {
            return false;
        }

        int j = permutation.length - 1;
        while (permutation[j] <= permutation[i]) {
            j--;
        }

        swap(permutation, i, j);

        reverse(permutation, i + 1, permutation.length - 1);
        return true;
    }

    // 交换数组中的两个元素
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 反转数组指定范围的元素
    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入任务数目n：");
        int n = scanner.nextInt();

        costMatrix = generateCostMatrix(n); // 生成成本矩阵
        assignment = new int[n];
        minAssignment = new int[n];
        minCost = Integer.MAX_VALUE;
        assignedTasks = new boolean[n];

        long startTime = System.nanoTime();
        branchAndBound(0, 0);
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        System.out.println("分支限界最优分配方案: " + Arrays.toString(minAssignment));
        System.out.println("最小总成本: " + minCost);
        System.out.println("程序运行时间: " + executionTime + "纳秒");

        long startTimeBF = System.nanoTime();
        bruteForce();
        long endTimeBF = System.nanoTime();
        long executionTimeBF = endTimeBF - startTimeBF;

        System.out.println("蛮力法程序运行时间: " + executionTimeBF + "纳秒");

        scanner.close();
    }


}
