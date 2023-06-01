package six;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class manli {
    public static void main(String[] args) {
        // Get input from the user
        int n = Integer.parseInt(args[0]); // Number of tasks

        // Generate a random cost matrix
        int[][] costMatrix = generateRandomCostMatrix(n);

        // Print the cost matrix
        System.out.println("Cost Matrix:");
        printMatrix(costMatrix);

        // Solve the task assignment problem using brute force
        long startTime = System.nanoTime();
        Result result = bruteForceTaskAssignment(costMatrix);
        long endTime = System.nanoTime();

        // Print the results
        System.out.println("Minimum Cost: " + result.minCost);
        System.out.println("Best Assignment: " + Arrays.toString(result.bestAssignment));
        System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");
    }

    // Generate a random cost matrix
    private static int[][] generateRandomCostMatrix(int n) {
        int[][] costMatrix = new int[n][n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                costMatrix[i][j] = random.nextInt(10) + 1; // Random cost between 1 and 10
            }
        }
        return costMatrix;
    }

    // Print a matrix
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    // Result class to store the minimum cost and best assignment
    static class Result {
        int minCost;
        int[] bestAssignment;

        Result(int minCost, int[] bestAssignment) {
            this.minCost = minCost;
            this.bestAssignment = bestAssignment;
        }
    }

    // Brute force method to solve the task assignment problem
    private static Result bruteForceTaskAssignment(int[][] costMatrix) {
        int n = costMatrix.length;
        int[] bestAssignment = new int[n];
        int minCost = Integer.MAX_VALUE;

        // Generate all possible permutations of task assignments
        List<int[]> permutations = new ArrayList<>();
        generatePermutations(n, new int[n], new boolean[n], permutations);

        // Iterate over each permutation and calculate the total cost
        for (int[] assignment : permutations) {
            int totalCost = 0;
            for (int i = 0; i < n; i++) {
                totalCost += costMatrix[i][assignment[i]];
            }
            if (totalCost < minCost) {
                minCost = totalCost;
                bestAssignment = assignment.clone();
            }
        }

        return new Result(minCost, bestAssignment);
    }

    // Recursive method to generate all permutations of task assignments
    private static void generatePermutations(int n, int[] assignment, boolean[] used, List<int[]> permutations) {
        if (n == 0) {
            permutations.add(assignment.clone());
            return;
        }

        for (int i = 0; i < assignment.length; i++) {
            if (!used[i]) {
                used[i] = true;
                assignment[assignment.length - n] = i;
                generatePermutations(n - 1, assignment, used, permutations);
                used[i] = false;
            }
        }
    }
}
