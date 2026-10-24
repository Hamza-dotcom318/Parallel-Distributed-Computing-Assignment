public class PacketSymmetryAuditor {

    public static boolean verifySymmetry(String payload) {
        int len = payload.length();
        TransactionStack stack = new TransactionStack(len);
        RequestQueue queue = new RequestQueue(len);

        // Stage 1: Simultaneous push and enqueue
        for (int i = 0; i < len; i++) {
            char ch = payload.charAt(i);
            stack.push(ch);
            queue.enqueue(ch);
        }

        // Stage 2: Concurrent comparison
        while (!stack.isEmpty() && !queue.isEmpty()) {
            if (stack.pop() != queue.dequeue()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] packets = { "RADAR", "0x99FF990x", "PAYLOAD123", "AMOREROMA" };

        for (String packet : packets) {
            boolean isSymmetrical = verifySymmetry(packet);
            System.out.printf("Packet: %-15s | Bi-Directional Symmetry Verified: %b%n",
                    "\"" + packet + "\"", isSymmetrical);
        }
    }
}