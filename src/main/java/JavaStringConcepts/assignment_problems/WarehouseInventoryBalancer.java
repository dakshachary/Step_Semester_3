public class WarehouseInventoryBalancer {

    static void analyzeInventory(int[] sectionA, int[] sectionB) {
        int totalA = 0;
        for (int value : sectionA) {
            totalA += value;
        }

        int totalB = 0;
        for (int value : sectionB) {
            totalB += value;
        }

        String status = (totalA == totalB) ? "Balanced" : "Not Balanced";

        int highestQuantity = sectionA[0];
        String highestSection = "Section A";
        int highestIndex = 0;

        for (int i = 0; i < sectionA.length; i++) {
            if (sectionA[i] > highestQuantity) {
                highestQuantity = sectionA[i];
                highestSection = "Section A";
                highestIndex = i;
            }
        }

        for (int i = 0; i < sectionB.length; i++) {
            if (sectionB[i] > highestQuantity) {
                highestQuantity = sectionB[i];
                highestSection = "Section B";
                highestIndex = i;
            }
        }

        System.out.println("Section A Total: " + totalA + " | Section B Total: " + totalB + " | Status: " + status + " | Highest Quantity: " + highestQuantity + " (" + highestSection + ", Item " + (highestIndex + 1) + ")");
    }

    public static void main(String[] args) {
        int[] sectionA = {20, 15, 30};
        int[] sectionB = {25, 10, 30};
        analyzeInventory(sectionA, sectionB);
    }
}
