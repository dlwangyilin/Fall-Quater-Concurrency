public class Drop {
    // Message sent from producer
    // to consumer.
    private String message;
    // True if consumer should wait
    // for producer to send message,
    // false if producer should wait for
    // consumer to retrieve message.
    private boolean empty = true;

    public synchronized String take() {
        // Wait until message is
        // available.
        while (empty) {
            try {
                System.out.println("Customer is waiting...");
                wait();
            } catch (InterruptedException e) {}
        }
        // 到这就算是处理完了
        // Toggle status.
        System.out.println("customer is done");
        empty = true;
        // Notify producer that
        // status has changed.
        notifyAll();
        return message;
    }

    public synchronized void put(String message) {
        // Wait until message has
        // been retrieved.
        while (!empty) {
            System.out.println("Producer is waiting...");
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        // Toggle status.
        empty = false;
        // Store message.
        this.message = message;
        System.out.println("Producer is done");
        // Notify consumer that status
        // has changed.
        notifyAll();
    }
}