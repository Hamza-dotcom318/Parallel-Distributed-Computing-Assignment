public class RequestQueue {
    private char[] storage;
    private int front;
    private int rear;
    private int size;

    public RequestQueue(int capacity) {
        storage = new char[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(char c) {
        if (size < storage.length) {
            rear = (rear + 1) % storage.length;
            storage[rear] = c;
            size++;
        }
    }

    public char dequeue() {
        if (!isEmpty()) {
            char val = storage[front];
            front = (front + 1) % storage.length;
            size--;
            return val;
        }
        return '\0';
    }

    public boolean isEmpty() {
        return size == 0;
    }
}