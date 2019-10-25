import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HelloWorld {
    public static void main(String args[]) throws IOException {
        /*
        1. 每一个可能有exception的进程都必须被处理，像MyRunnable的异常是被try/catch处理的
        2. 在main函数中，也有sleep，所以也需要处理异常，这里是直接throws这个异常，意思就是出现了这个异常也不管它
        3. 中断进程的方法就是interrupt这个进程，在catch中接住这个异常，然后break出死循环。
         */
        Runnable threadJob = new MyRunnable();
        ArrayList<Thread> MyThreads = new ArrayList<>();
        while(true) {
            System.out.println("Here are your options:"+"\n"+"\n"+"a - Create a new thread"+"\n"+"b - Stop a given thread"
                                +"\n"+"c - Stop all threads and exit this program");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please input now: ");
            String text = buffer.readLine();
            System.out.println("--> " + text);
            char key = text.charAt(0);
            if(key == 'a'){
                addThread(threadJob, MyThreads);
            }
            else if(key == 'b'){
                int target = Character.getNumericValue(text.charAt(1));
                interruptThread(MyThreads, target);
            }
            else if (key == 'c'){
                int length = MyThreads.size();
                for(int i=0; i<length; i++){
                    interruptThread(MyThreads, i);
                }
                break;
            }
        }
        System.out.println("Hooray, done");
    }

    private static void addThread(Runnable threadJob, ArrayList<Thread> MyThreads){
        MyThreads.add(new Thread(threadJob));
        int index = MyThreads.size()-1;
        MyThreads.get(index).setName("Thread "+index);
        MyThreads.get(index).start();
        System.out.println(index);
    }

    private static void interruptThread(ArrayList<Thread> MyThreads, int index){
        MyThreads.get(index).interrupt();
    }

}
