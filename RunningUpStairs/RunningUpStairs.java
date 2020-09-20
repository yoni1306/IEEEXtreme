import java.math.BigInteger;
import java.util.Scanner;

public class RunningUpStairs {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int testCasesCount = in.nextInt();

        int maxStairs = 0;
        int[] stairs = new int[testCasesCount];

        for (int i = 0; i < testCasesCount; i++) {
            stairs[i] = in.nextInt();
            maxStairs = Math.max(maxStairs, stairs[i]);
        }

        BigInteger[] waysCounter = new BigInteger[maxStairs + 1];

        for (int j = 0; j < waysCounter.length; j++) {
            waysCounter[j] = BigInteger.ZERO;
        }

        waysCounter[0] = BigInteger.ONE;
        waysCounter[1] = BigInteger.ONE;
        waysCounter[2] = BigInteger.valueOf(2);

        initWaysCounter(maxStairs, waysCounter);

        for (int j = 0; j < stairs.length; j++) {
            System.out.println(waysCounter[stairs[j]]);
        }
    }

    private static void initWaysCounter(int n, BigInteger[] waysCounter) {
        for (int i = 3; i <= n; i++) {
            waysCounter[i] = waysCounter[i - 1].add(waysCounter[i - 2]);
        }
    }
}
