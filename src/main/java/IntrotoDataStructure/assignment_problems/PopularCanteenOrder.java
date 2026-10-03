import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PopularCanteenOrder {
    static Map.Entry<String, Integer> mostPopular(String[] orders) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String item : orders) {
            counts.merge(item, 1, Integer::sum);
        }
        String bestItem = null;
        int bestCount = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > bestCount) {
                bestCount = entry.getValue();
                bestItem = entry.getKey();
            }
        }
        return new AbstractMap.SimpleEntry<>(bestItem, bestCount);
    }

    static String format(Map.Entry<String, Integer> result) {
        return "(\"" + result.getKey() + "\", " + result.getValue() + ")";
    }

    public static void main(String[] args) {
        String[] first = {"dosa", "idli", "vada", "dosa", "idli", "dosa", "tea"};
        String[] second = {"tea", "coffee", "coffee", "tea"};
        System.out.println(format(mostPopular(first)));
        System.out.println(format(mostPopular(second)));
    }
}
