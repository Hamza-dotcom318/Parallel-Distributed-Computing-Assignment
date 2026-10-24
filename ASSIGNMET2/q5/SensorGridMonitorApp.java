import java.util.Random;

public class SensorGridMonitorApp {
    public static void main(String[] args) throws InterruptedException {
        int totalElements = 50_000;
        double[] telemetryData = new double[totalElements];
        Random r = new Random(101);

        for (int i = 0; i < totalElements; i++) {
            telemetryData[i] = -50.0 + r.nextDouble() * 200.0;
        }

        int threads = 4;
        int chunkSize = totalElements / threads;

        double[] minResults = new double[threads];
        double[] maxResults = new double[threads];
        Thread[] minWorkers = new Thread[threads];
        Thread[] maxWorkers = new Thread[threads];

        long startTime = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            final int index = i;
            int start = i * chunkSize;
            int end = (i == threads - 1) ? totalElements : start + chunkSize;

            minWorkers[i] = new Thread(() -> {
                ParallelMinSearch searcher = new ParallelMinSearch(telemetryData, start, end);
                minResults[index] = searcher.processGrid();
            });

            maxWorkers[i] = new Thread(() -> {
                ParallelMaxSearch searcher = new ParallelMaxSearch(telemetryData, start, end);
                maxResults[index] = searcher.processGrid();
            });

            minWorkers[i].start();
            maxWorkers[i].start();
        }

        for (int i = 0; i < threads; i++) {
            minWorkers[i].join();
            maxWorkers[i].join();
        }

        double globalMin = Double.MAX_VALUE;
        double globalMax = -Double.MAX_VALUE;

        for (int i = 0; i < threads; i++) {
            if (minResults[i] < globalMin) globalMin = minResults[i];
            if (maxResults[i] > globalMax) globalMax = maxResults[i];
        }

        long elapsedTime = System.nanoTime() - startTime;

        System.out.printf("Global Minimum Temperature: %.2f°C%n", globalMin);
        System.out.printf("Global Maximum Vibration Peak: %.2f Hz%n", globalMax);
        System.out.printf("Parallel Grid Reduction Elapsed Time: %.3f ms%n", elapsedTime / 1e6);
    }
}