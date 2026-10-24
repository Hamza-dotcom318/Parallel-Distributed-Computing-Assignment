public class CacheLocalityBenchmark {

    private static final int N = 2000;
    private static final double[][] matrix = new double[N][N];
    private static final int ITERATIONS = 10;

    public static double traverseRowMajor() {
        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public static double traverseColumnMajor() {
        double sum = 0.0;
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        // Populate array
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = i + j;
            }
        }

        // Warm-up runs to allow JIT optimization
        for (int i = 0; i < 3; i++) {
            traverseRowMajor();
            traverseColumnMajor();
        }

        // Benchmark Row-Major Traversal
        long startRow = System.nanoTime();
        for (int k = 0; k < ITERATIONS; k++) {
            traverseRowMajor();
        }
        long endRow = System.nanoTime();
        double avgRowMs = ((endRow - startRow) / (double) ITERATIONS) / 1e6;

        // Benchmark Column-Major Traversal
        long startCol = System.nanoTime();
        for (int k = 0; k < ITERATIONS; k++) {
            traverseColumnMajor();
        }
        long endCol = System.nanoTime();
        double avgColMs = ((endCol - startCol) / (double) ITERATIONS) / 1e6;

        double speedup = avgColMs / avgRowMs;

        System.out.println("=================================================");
        System.out.println(" HARDWARE CACHE LOCALITY PROFILING REPORT");
        System.out.println("=================================================");
        System.out.printf("Row-Major Average Time   : %.4f ms%n", avgRowMs);
        System.out.printf("Column-Major Average Time: %.4f ms%n", avgColMs);
        System.out.printf("Speedup Ratio (Row/Col)  : %.2fx faster%n", speedup);
        System.out.println("=================================================");
    }
}