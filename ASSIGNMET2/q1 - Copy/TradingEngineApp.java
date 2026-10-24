public class TradingEngineApp {
    public static void main(String[] args) {
        long currentTime = System.currentTimeMillis();

        // Polymorphic reference array
        TradeEvent[] eventStream = new TradeEvent[] {
            new TradeEvent("GATEWAY-01", currentTime, 150.00),
            new FIXOrderEvent("FIX-SESSION-99", currentTime, 2500.50, "ORD-88492"),
            new ReutersQuoteEvent("REUTERS-TICK", currentTime, 340.20, 340.15, 340.25)
        };

        System.out.println("--- Processing Real-Time Market Event Stream ---");
        for (TradeEvent event : eventStream) {
            // Dynamic method dispatch triggers subclass overrides
            event.dispatch();
        }
    }
}