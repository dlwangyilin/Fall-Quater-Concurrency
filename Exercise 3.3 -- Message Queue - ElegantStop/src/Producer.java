import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private final BlockingQueue<String> queue;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicInteger id;
    private final AtomicInteger numofConsumer;
    private final AtomicInteger numofProducer;
	private final AtomicInteger countFinished;

    public Producer(BlockingQueue<String> q, int n, AtomicInteger consumer, AtomicInteger countFinish, int producer) {
		queue = q;
		id = new AtomicInteger(n);
		numofConsumer = consumer;
		numofProducer = new AtomicInteger(producer);
		countFinished = countFinish;
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

		countFinished.incrementAndGet();
		/*
		加入这个是为了判断所有线程是不是都已经发完了message，这样就不用像下面那样sleep。
		1. 这里最重要的是让每个thread的countFinished一样，所以不能用int类型(primitive)，这样会死循环。所以要用
		   AtomicInteger，而且要传进producer中，这样每个thread都会对AtomicInteger进行操作。
		2. 有可能可以用wait(), notifyAll(), 使用这两个时需要获得当前对象的锁，所以需要synchronized.
		3. 加入numofConsumer是为了判断需要发几个stop，如果producernum也是像一开始传入的int，那会多发很多stop。
		4. 这也回答了一个疑问，Thread列表中，虽然Runable一样，但是他们的数据其实是不互通的，除非通过参数传递就去统一初始化。
		 */
		while(countFinished.get() != numofProducer.get()){

		}
		// countFinished.get() == numofProducer.get()?? -> deadlock due to wai
		// Put the stop message in the queue

		while(numofConsumer.decrementAndGet()>=0){
			/*
			不加这个sleep的时候，在3p2c的情况下，2个p发出2个stop，consumer全部停止，但剩余的那个p还在发有用的消息。
			 */
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
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
