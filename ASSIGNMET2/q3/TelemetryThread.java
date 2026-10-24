public class TelemetryThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.printf("[%s] Update %d: CPU Load = %.2f%%, Memory Free = %d MB%n",
                    Thread.currentThread().getName(), i,
                    20 + Math.random() * 30, (long)(500 + Math.random() * 200));
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("TelemetryThread interrupted.");
            }
        }
    }
}