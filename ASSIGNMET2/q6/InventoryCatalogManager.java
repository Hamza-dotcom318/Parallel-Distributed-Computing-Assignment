import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InventoryCatalogManager {

    private static final int THREAD_POOL_SIZE = 10;
    private static final int OPERATIONS_PER_THREAD = 50_000;

    public void benchmarkSynchronizedMap() throws InterruptedException {
        Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
        runBenchmark("Collections.synchronizedMap", syncMap);
    }

    public void benchmarkConcurrentMap() throws InterruptedException {
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        runBenchmark("ConcurrentHashMap", concurrentMap);
    }

    private void runBenchmark(String label, Map<String, Integer> map) throws InterruptedException {
        // Pre-populate catalog
        for (int i = 0; i < 100; i++) {
            map.put("PROD-" + i, 1000);
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long start = System.currentTimeMillis();

        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            executor.submit(() -> {
                for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                    String key = "PROD-" + (j % 100);
                    Integer current = map.get(key);
                    if (current != null) {
                        map.put(key, current + 1);
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long end = System.currentTimeMillis();
        double durationSeconds = (end - start) / 1000.0;
        long totalOps = (long) THREAD_POOL_SIZE * OPERATIONS_PER_THREAD;
        double throughput = totalOps / durationSeconds;

        System.out.println("=== Benchmark Result: " + label + " ===");
        System.out.printf("Execution Time : %.3f s%n", durationSeconds);
        System.out.printf("Throughput     : %.2f ops/sec%n%n", throughput);
    }

    public static void main(String[] args) throws InterruptedException {
        InventoryCatalogManager manager = new InventoryCatalogManager();
        manager.benchmarkSynchronizedMap();
        manager.benchmarkConcurrentMap();
    }
}