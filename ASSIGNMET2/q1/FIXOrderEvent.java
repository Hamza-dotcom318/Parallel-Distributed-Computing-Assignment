public class FIXOrderEvent extends TradeEvent {
    private String clOrdId;

    public FIXOrderEvent(String senderId, long timestamp, double basePrice, String clOrdId) {
        super(senderId, timestamp, basePrice);
        this.clOrdId = clOrdId;
    }

    @Override
    public void dispatch() {
        double transactionFee = basePrice * 0.0015; // 0.15% fee
        double finalPrice = basePrice + transactionFee;
        System.out.printf("[FIX ORDER] ClientOrdID: %s | Sender: %s | Base: $%.2f | Fee (0.15%%): $%.4f | Executed: $%.2f%n",
                clOrdId, senderId, basePrice, transactionFee, finalPrice);
    }
}