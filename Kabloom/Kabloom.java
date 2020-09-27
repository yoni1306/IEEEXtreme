import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class Kabloom {
    private static HashMap<String, Integer> cardRankToKabloomValueMap = new HashMap<>();

    static {
        cardRankToKabloomValueMap.put("A", 20);
        cardRankToKabloomValueMap.put("2", 2);
        cardRankToKabloomValueMap.put("3", 3);
        cardRankToKabloomValueMap.put("4", 4);
        cardRankToKabloomValueMap.put("5", 5);
        cardRankToKabloomValueMap.put("6", 6);
        cardRankToKabloomValueMap.put("7", 7);
        cardRankToKabloomValueMap.put("8", 8);
        cardRankToKabloomValueMap.put("9", 9);
        cardRankToKabloomValueMap.put("T", 10);
        cardRankToKabloomValueMap.put("J", 15);
        cardRankToKabloomValueMap.put("Q", 15);
        cardRankToKabloomValueMap.put("K", 15);
        cardRankToKabloomValueMap.put("R", 50);
    }

    public static void main(String[] args) {

        // Uncomment the following line in order to simulate a specific input
        // setStandardInput();

        Scanner in = new Scanner(System.in);

        int cardsCount = in.nextInt(); // The number of test cases.

        while (cardsCount > 0) {
            in.skip("\n");

            String[] firstRow = in.nextLine().split(" ");
            String[] secondRow = in.nextLine().split(" ");

            // kabloomValuesSum[i][i] is the highest possible card values sum following Kabloom
            // game rules, using i+1 cards of both rows
            int[][] kabloomValuesSum = new int[cardsCount][cardsCount];

            // Use first cards from both rows
            kabloomValuesSum[0][0] = calcCardsKabloomValue(firstRow[0], secondRow[0]);

            // Base cases - calc best kabloom values using cards from 1st row against 1st card in 2nd row and vice versa
            for (int j = 1; j < kabloomValuesSum.length; j++) {
                kabloomValuesSum[0][j] = Math.max(calcCardsKabloomValue(firstRow[j], secondRow[0]), kabloomValuesSum[0][j - 1]);
                kabloomValuesSum[j][0] = Math.max(calcCardsKabloomValue(firstRow[0], secondRow[j]), kabloomValuesSum[j - 1][0]);
            }

            // We can either use or skip cards i and j from both rows
            // If we use both cards, then we take kabloomValuesSum[i-1][j-1]
            // If we use only card i from the first row, then we consider kabloomValuesSum[i-1][j]
            // If we use only card j from the second row, then we consider kabloomValuesSum[i][j-1]
            for (int row = 1; row < kabloomValuesSum.length; row++) {
                for (int col = 1; col < kabloomValuesSum[0].length; col++) {
                    int kabloomSumAtPoint = calcCardsKabloomValue(firstRow[col], secondRow[row]) + kabloomValuesSum[row - 1][col - 1];

                    kabloomValuesSum[row][col] = Math.max(Math.max(kabloomSumAtPoint, kabloomValuesSum[row - 1][col]), kabloomValuesSum[row][col - 1]);
                }
            }

            // Kabloom calculated value
            System.out.println(kabloomValuesSum[cardsCount - 1][cardsCount - 1]);

            cardsCount = in.nextInt();
        }
    }

    private static int calcCardsKabloomValue(String card1, String card2) {
        if (card1.equals(card2) || card2.equals("R")) {
            return 2 * cardRankToKabloomValueMap.get(card1);
        }

        if (card1.equals("R")) {
            return 2 * cardRankToKabloomValueMap.get(card2);
        }

        return 0;
    }

    private static void setStandardInput() {

        String input = "9\n" +
                "6 3 7 4 2 A K R T\n" +
                "3 5 4 7 R A Q K T\n" +
                "0";

        InputStream fakeIn = new ByteArrayInputStream(input.getBytes());

        System.setIn(fakeIn);
    }
}
