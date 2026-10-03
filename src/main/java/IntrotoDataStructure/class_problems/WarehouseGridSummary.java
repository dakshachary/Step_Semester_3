public class WarehouseGridSummary {
    static String warehouseSummary(int[][] grid) {
        int total = 0;
        int maxValue = -1;
        int maxRow = -1;
        int maxCol = -1;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                total += grid[r][c];
                if (grid[r][c] > maxValue) {
                    maxValue = grid[r][c];
                    maxRow = r;
                    maxCol = c;
                }
            }
        }
        return "(" + total + ", (" + maxRow + ", " + maxCol + "))";
    }

    public static void main(String[] args) {
        int[][] grid = {{4, 9, 2}, {7, 1, 6}, {3, 12, 5}};
        System.out.println(warehouseSummary(grid));
    }
}
