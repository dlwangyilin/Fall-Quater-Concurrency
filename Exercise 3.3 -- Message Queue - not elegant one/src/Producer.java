import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private final BlockingQueue<String> queue;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicInteger id;
    private final AtomicInteger numofConsumer;



    public Producer(BlockingQueue<String> q, int n, int consumer) {
		queue = q;
		id = new AtomicInteger(n);
		numofConsumer = new AtomicInteger(consumer);

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


		// countFinished.get() == numofProducer.get()?? -> deadlock due to wai
		// Put the stop message in the queue

		while(numofConsumer.decrementAndGet()>=0){
			/*
			不加这个sleep的时候，在3p2c的情况下，2个p发出2个stop，consumer全部停止，但剩余的那个p还在发有用的消息。
			 */
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Message msg = new Message("stop");
			try {
				queue.put(msg.get()); // Put this final message in the queue
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}


		RandomUtils.print("Total Messages sent: " + count.get(), id.get());
    }
}
