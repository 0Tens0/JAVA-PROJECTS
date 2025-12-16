/**
 * FIFO queue implementation for message handling.
 * Thread-safe implementation for concurrent access.
 */
public class MessageQueue {
    private Node head; // Head of the linked list
    private Node tail; // Tail of the linked list
    private int size; // Current size of the queue
    private int maxSize; // Maximum size of the queue

    // Node class representing each element in the linked list
    private static class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
            this.next = null;
        }
    }

    public MessageQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.maxSize = 1000; // Default max size
    }

    public MessageQueue(int maxSize) {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.maxSize = maxSize;
    }

    /**
     * Add a message to the queue (enqueue)
     */
    public synchronized void enqueue(String message) {
        while (size >= maxSize) {
            try {
                wait(); // Wait if queue is full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        Node newNode = new Node(message);
        if (tail == null) {
            // Queue is empty
            head = newNode;
            tail = newNode;
        } else {
            // Append the new node to the end
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        notifyAll(); // Notify waiting consumers
    }

    /**
     * Remove and return the first message from the queue (dequeue)
     */
    public synchronized String dequeue() {
        while (size == 0) {
            try {
                wait(); // Wait if queue is empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        String message = head.data;
        head = head.next;
        if (head == null) {
            tail = null; // Queue is now empty
        }
        size--;
        notifyAll(); // Notify waiting producers
        return message;
    }

    /**
     * Peek at the first message without removing it
     */
    public synchronized String peek() {
        if (size == 0) {
            return null;
        }
        return head.data;
    }

    /**
     * Check if queue is empty
     */
    public synchronized boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get current size of the queue
     */
    public synchronized int size() {
        return size;
    }

    /**
     * Clear all messages from the queue
     */
    public synchronized void clear() {
        head = null;
        tail = null;
        size = 0;
        notifyAll();
    }

    /**
     * Get all messages as an array (for searching)
     */
    public synchronized String[] toArray() {
        String[] array = new String[size];
        Node current = head;
        int index = 0;
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }
}
