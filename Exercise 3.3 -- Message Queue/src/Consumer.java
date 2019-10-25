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
			msg = new Message(queue.poll(5, TimeUnit.SECONDS)); // Get a message from the queue
			if(msg.get() == null){
				count.incrementAndGet();
				break;
			}
			count.incrementAndGet();
			RandomUtils.print("Consumed " + msg.get(), id.get());
			Thread.sleep(RandomUtils.randomInteger());
			} catch (InterruptedException e) {
			e.printStackTrace();
	    }
	} while ( ! msg.get().equals("stop"));
	// Don't count the "stop" message

		count.decrementAndGet();
		RandomUtils.print("Total Messages received: " + count.get(), id.get());
    }
}
