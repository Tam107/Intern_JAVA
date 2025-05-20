package NCC;

import java.util.Scanner;

public class CellReplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int T = scanner.nextInt();
        StringBuilder results = new StringBuilder();

        for (int i = 0; i< T;i++){
            int n = scanner.nextInt();
            int k = scanner.nextInt();

            // Determine the cell type at position k at time n
            char cellType = getCellType(n,k);
            results.append(cellType);

            if (i < T -1){
                results.append("\n");
            }
        }
        System.out.println(results);
        scanner.close();
    }

    public static char getCellType(int n, int k) {
        // Check if position is valid for the current time
        long totalCells = (long) Math.pow(2, n - 1); // 2^(n-1)
        if (k > totalCells || k < 1) {
            return ' '; // Position is out of bounds
        }

        // Base case: at time 1, only one cell exists, which is 'X'
        if (n == 1) {
            return 'X';
        }

        // Start with the initial cell type
        char cellType = 'X';

        // Trace backwards from time n to time 2
        long currentK = k;
        for (int time = n; time > 1; time--) {
            // Calculate the number of cells at the previous time
            long prevTimeCells = (long) Math.pow(2, time - 2);

            // If we're in the second half of cells at current time
            if (currentK > prevTimeCells) {
                // Right child: flip the cell type (X -> Y, Y -> X)
                cellType = (cellType == 'X') ? 'Y' : 'X';
                // Update position to parent's position
                currentK = currentK - prevTimeCells;
            }
            // Left child: cell type remains the same, position unchanged
        }

        return cellType;
    }
}
