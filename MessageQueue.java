import java.util.LinkedList;

/**
 * FIFO queue implementation for message handling.
 * Thread-safe implementation for concurrent access.
 */
public class MessageQueue {
    private LinkedList<String> queue;
    private int maxSize;
    
    public MessageQueue() {
        this.queue = new LinkedList<>();
        this.maxSize = 1000; // Default max size
    }
    
    public MessageQueue(int maxSize) {
        this.queue = new LinkedList<>();
        this.maxSize = maxSize;
    }
    
    /**
     * Add a message to the queue (enqueue)
     */
    public synchronized void enqueue(String message) {
        while (queue.size() >= maxSize) {
            try {
                wait(); // Wait if queue is full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        queue.addLast(message);
        notifyAll(); // Notify waiting consumers
    }
    
    /**
     * Remove and return the first message from the queue (dequeue)
     */
    public synchronized String dequeue() {
        while (queue.isEmpty()) {
            try {
                wait(); // Wait if queue is empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        String message = queue.removeFirst();
        notifyAll(); // Notify waiting producers
        return message;
    }
    
    /**
     * Peek at the first message without removing it
     */
    public synchronized String peek() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.getFirst();
    }
    
    /**
     * Check if queue is empty
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
    
    /**
     * Get current size of the queue
     */
    public synchronized int size() {
        return queue.size();
    }
    
    /**
     * Clear all messages from the queue
     */
    public synchronized void clear() {
        queue.clear();
        notifyAll();
    }
    
    /**
     * Get all messages as an array (for searching)
     */
    public synchronized String[] toArray() {
        return queue.toArray(new String[0]);
    }
}
