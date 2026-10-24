public class LogProcessorTask implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.printf("[%s] Iteration %d: Telemetry metrics captured.%n",
                    Thread.currentThread().getName(), i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("LogProcessorTask interrupted.");
            }
        }
    }
}