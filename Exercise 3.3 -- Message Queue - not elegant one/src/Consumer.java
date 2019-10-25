import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    private final BlockingQueue<String> queue;
    private final AtomicInteger id;
    private final AtomicBoolean flag = new AtomicBoolean(true);

    public Consumer(BlockingQueue<String> q, int n) {
		queue = q;
		id = new AtomicInteger(n);
    }
    
    public void run() {
		Message msg = null;
		AtomicInteger count = new AtomicInteger(0);

	do {
	    try {
			msg = new Message(queue.take()); // Get a message from the queue

			count.incrementAndGet();
			RandomUtils.print("Consumed " + msg.get(), id.get());
			Thread.sleep(RandomUtils.randomInteger());
			} catch (InterruptedException e) {
			e.printStackTrace();
	    }
	} while ( ! msg.get().equals("stop"));
	// Don't count the "stop" message


		RandomUtils.print("Total Messages received: " + count.decrementAndGet(), id.get());
    }
}
