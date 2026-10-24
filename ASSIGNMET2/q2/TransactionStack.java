public class TransactionStack {
    private char[] storage;
    private int top;

    public TransactionStack(int capacity) {
        storage = new char[capacity];
        top = -1;
    }

    public void push(char c) {
        if (top < storage.length - 1) {
            storage[++top] = c;
        }
    }

    public char pop() {
        if (!isEmpty()) {
            return storage[top--];
        }
        return '\0';
    }

    public char peek() {
        if (!isEmpty()) {
            return storage[top];
        }
        return '\0';
    }

    public boolean isEmpty() {
        return top == -1;
    }
}