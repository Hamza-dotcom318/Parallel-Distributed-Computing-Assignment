import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        int datasetSize = 10_000;
        double[] portfolio = new double[datasetSize];
        Random rand = new Random(42);

        for (int i = 0; i < datasetSize; i++) {
            portfolio[i] = 100 + rand.nextDouble() * 1900;
        }

        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.nanoTime();
        ParallelRiskCalculator riskTask = new ParallelRiskCalculator(portfolio, 0, portfolio.length);
        double totalRiskScore = pool.invoke(riskTask);
        long parallelDuration = System.nanoTime() - startTime;

        PortfolioThresholdTask stressTestTask = new PortfolioThresholdTask(portfolio, 0, portfolio.length, 1500.0);
        pool.invoke(stressTestTask);

        System.out.printf("Total Cumulative Portfolio Risk Score: $%.2f%n", totalRiskScore);
        System.out.printf("Fork/Join Parallel Execution Time: %.3f ms%n", parallelDuration / 1e6);
    }
}