import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    private static int n_ids;

    public static void main(String[] args) throws IOException {
		BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

		// Get producer number
		System.out.println("Please input producer number: ");
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		String text = buffer.readLine();
		char key = text.charAt(0);
		int producernum = Character.getNumericValue(key);
		System.out.println("Your input producer number is : " + producernum);

		// Get consumer number
		System.out.println("Please input consumer number: ");
		BufferedReader buffer2 = new BufferedReader(new InputStreamReader(System.in));
		String text2 = buffer2.readLine();
		char key2 = text2.charAt(0);
		int consumernum = Character.getNumericValue(key2);
		System.out.println("Your input consumer number is : " + consumernum);

		// Create producerList, consumerList
		List<Producer> producerList = new ArrayList<>();
		for(int i=0; i<producernum; i++){
			producerList.add(new Producer(queue, n_ids++));
		}
		List<Consumer> consumerList = new ArrayList<>();
		for(int i=0; i<consumernum; i++){
			consumerList.add(new Consumer(queue, n_ids++));
		}

		//Start Threads
		for(Producer x:producerList){
			new Thread(x).start();
		}

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for(Consumer x:consumerList){
			new Thread(x).start();
		}


		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for(Producer x:producerList){
			x.stop();
		}


    }

}
