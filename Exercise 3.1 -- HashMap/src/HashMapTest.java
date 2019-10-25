import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class HashMapTest {

    private final AtomicBoolean running = new AtomicBoolean(true);
    private final Map<String, Integer> people = new ConcurrentHashMap<>();

    private void addPerson() {
	people.put(RandomUtils.randomString(), RandomUtils.randomInteger());
    }

    private synchronized void deletePeople(String pattern) {
		Vector<String> hasPattern = new Vector<>();
		for (String key : people.keySet()) {
			if (key.contains(pattern))
			hasPattern.add(key);
		}
		for (String key : hasPattern)
			people.remove(key);
    }

    private synchronized void printPeople() {
		for (HashMap.Entry<String, Integer> entry : people.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		System.out.println("-----------------------------------------");
    }

    public void run() {
		// Start printer thread
		new Thread(new Runnable () {
			public void run() {
				Thread.currentThread().setName("Printer");
				while (running.get()) {
				printPeople();
				try {
					Thread.sleep(900);
				} catch (InterruptedException e) {}
				}
			}
			}).start();

		// Start deleter thread
		new Thread(new Runnable () {
			public void run() {
				Thread.currentThread().setName("Deleter");
				while (running.get()) {
				deletePeople("a");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {}
				}
			}
			}).start();

		// This thread adds entries
		for (int i = 0; i < 100; i++) {
			addPerson();
			try {
			Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
		running.set(false);
    }

    public static void main(String[] args) {
		HashMapTest hm = new HashMapTest();
		hm.run();
    }

}
