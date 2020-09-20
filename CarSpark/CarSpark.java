import java.util.*;

public class CarSpark {
    private static List<RentalOrder> rentalOrders;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int testCasesCount = in.nextInt();

        for (int i = 0; i < testCasesCount; i++) {
            int inputSize = in.nextInt();
            rentalOrders = new ArrayList(inputSize);

            for (int j = 0; j < inputSize; j++) {
                addRentalOrder(in.nextInt(), in.nextInt(), in.nextInt());
            }

            calcBestScheduleValue();
        }
    }

    static void calcBestScheduleValue() {
        HashMap<Integer, Integer> timesIntervalValues = new HashMap(50);

        for (int i = 0; i < 50; i++) {
            timesIntervalValues.put(i, 0);
        }

        // Sort by end time (ascending) then by start time (ascending) = ends soonest and also starts soonest
        rentalOrders.sort(Comparator.comparingInt((RentalOrder orderA) -> orderA.endTime).thenComparingInt(orderA -> orderA.startTime));
        // Calc time interval values using dynamic programming technique, where the time interval value at index i
        // represents max value schedule for all orders up until end time i
        // timesIntervalValues[endTime] = max(best_schedule_value_until_current_end_time, best_schedule_value_until_earlier_time + current_order_value)
        rentalOrders.forEach(rentalOrder -> {
            for (int k = rentalOrder.startTime; k >= 0; k--) {
                timesIntervalValues.put(rentalOrder.endTime,
                        Math.max(timesIntervalValues.get(rentalOrder.endTime),
                                timesIntervalValues.get(k) + rentalOrder.value));
            }

        });

        int highestValue = 0;
        for (int i = 0; i < timesIntervalValues.size(); i++) {
            highestValue = Math.max(highestValue, timesIntervalValues.get(i));
        }

        System.out.println(highestValue);
    }

    static void addRentalOrder(int sTime, int eTime, int value) {
        RentalOrder order = new RentalOrder();
        order.startTime = sTime;
        order.endTime = eTime;
        order.value = value;

        rentalOrders.add(order);
    }

    static class RentalOrder {
        public int startTime;
        public int endTime;
        public int value;
    }
}



