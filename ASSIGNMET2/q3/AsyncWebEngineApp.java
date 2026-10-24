public class AsyncWebEngineApp {
    public static void main(String[] args) {
        System.out.println("Default JVM Main Thread: " + Thread.currentThread().getName());

        // Instantiate task objects
        TelemetryThread telemetryWorker = new TelemetryThread();
        telemetryWorker.setName("Telemetry-Worker");

        LogProcessorTask logTask = new LogProcessorTask();
        Thread logWorker = new Thread(logTask, "Log-Worker");

        // Display states before launch
        System.out.println("Pre-start state [" + telemetryWorker.getName() + "]: " + telemetryWorker.getState());
        System.out.println("Pre-start state [" + logWorker.getName() + "]: " + logWorker.getState());

        // Launch concurrent threads
        telemetryWorker.start();
        logWorker.start();

        // Display states immediately after start
        System.out.println("Post-start state [" + telemetryWorker.getName() + "]: " + telemetryWorker.getState());
        System.out.println("Post-start state [" + logWorker.getName() + "]: " + logWorker.getState());
    }
}