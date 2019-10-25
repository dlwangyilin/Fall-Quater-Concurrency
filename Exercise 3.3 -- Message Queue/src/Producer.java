import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private final BlockingQueue<String> queue;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicInteger id;

    public Producer(BlockingQueue<String> q, int n) {
		queue = q;
		id = new AtomicInteger(n);
    }

    public void stop() {
		running.set(false);
    }

    public void run() {
		AtomicInteger count = new AtomicInteger(0);
		while (running.get()) {
			int n = RandomUtils.randomInteger();
			try {
			Thread.sleep(n);
			Message msg = new Message("message-" + n);
			queue.put(msg.get()); // Put the message in the queue
			count.incrementAndGet();
			RandomUtils.print("Produced " + msg.get(), id.get());
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		}
		// Put the stop message in the queue
		Message msg = new Message("stop");
		try {
			queue.put(msg.get()); // Put this final message in the queue
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		RandomUtils.print("Total Messages sent: " + count.get(), id.get());
		}
}
