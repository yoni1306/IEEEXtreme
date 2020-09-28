import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BlackgatePenitentiary {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int crewSize = in.nextInt();
        TreeMap<Integer, ArrayList<String>> heightToCrewMembersName = new TreeMap<>();

        in.skip("\n");

        for (int i = 0; i < crewSize; i++) {
            String[] memberDetails = in.nextLine().split(" ");
            int memberHeight = Integer.parseInt(memberDetails[1]);

            ArrayList<String> sameHeightMembers = heightToCrewMembersName.getOrDefault(memberHeight, new ArrayList<String>());
            sameHeightMembers.add(memberDetails[0]);

            heightToCrewMembersName.put(memberHeight, sameHeightMembers);
        }

        AtomicInteger locationIndex = new AtomicInteger(1);

        heightToCrewMembersName.forEach((height, memberNames) -> {
            int currentLocation = locationIndex.get();
            int endLocation = currentLocation + memberNames.size() - 1;

            System.out.println(convertToAlphabeticString(memberNames) + " " + currentLocation + " " + endLocation);

            locationIndex.set(endLocation + 1);
        });
    }

    private static String convertToAlphabeticString(ArrayList<String> arrayList) {
        String result = "";

        Collections.sort(arrayList);

        Iterator<String> iterator = arrayList.iterator();

        while(iterator.hasNext()) {
            result += iterator.next() + " ";
        }

        return result.trim();
    }
}
