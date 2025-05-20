package Number;

// phep nhan ma tran
public class MatrixMultiplication {

    public static void main(String[] args) {
        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[][] matrix2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        if(matrix1[0].length != matrix2[0].length){
            System.out.println("Matrix multipulation is not possible");
        }else {
            int row = matrix1.length;;
            int columns = matrix2[0].length;
            int[][] result = new int[row][columns];


            for (int i = 0; i< row; i++){
                for (int j = 0; j < columns;j++){
                    for (int k = 0; k < matrix2.length; k++) {
                        result[i][j] += matrix1[i][k] * matrix2[k][j];
                    }
                }
            }
            // Step 6: Print the result matrix
            System.out.println("Multiplication of the matrices:");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < columns; j++) {
                    System.out.print(result[i][j] + " ");
                }
                System.out.println();
            }
        }

    }



}
