package five;
import java.util.Scanner;

public class two {
    private static final int NUM_QUESTIONS = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入目标得分（0, 10, 20, ..., 90, 100）：");
        int targetScore = scanner.nextInt();
        scanner.close();

        if (targetScore % 10 != 0 || targetScore < 0 || targetScore > 100) {
            System.out.println("无效的目标得分！");
            return;
        }

        System.out.println("得分为 " + targetScore + " 的可能答题情况：");
        backtrack(0, targetScore, new int[NUM_QUESTIONS], 10);
    }

    private static void backtrack(int questionIndex, int targetScore, int[] answers, int currentScore) {
        if (questionIndex == NUM_QUESTIONS) {
            if (currentScore == targetScore) {
                printSolution(answers);
            }
            return;
        }

        // 尝试回答问题正确
        answers[questionIndex] = 1;
        backtrack(questionIndex + 1, targetScore, answers, currentScore * 2);

        // 尝试回答问题错误
        answers[questionIndex] = 0;
        backtrack(questionIndex + 1, targetScore, answers, currentScore - (questionIndex + 1));
    }

    private static void printSolution(int[] answers) {
        for (int i = 0; i < NUM_QUESTIONS; i++) {
            System.out.print("问题 " + (i + 1) + "：");
            if (answers[i] == 1) {
                System.out.println("正确");
            } else {
                System.out.println("错误");
            }
        }
        System.out.println();
    }
}
