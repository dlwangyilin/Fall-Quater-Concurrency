import java.util.ArrayList;
import java.util.Scanner;

public class HelloWorld {
    
    public static void main(String args[]) throws InterruptedException {
        /*
        1. 每一个可能有exception的进程都必须被处理，像MyRunnable的异常是被try/catch处理的
        2. 在main函数中，也有sleep，所以也需要处理异常，这里是直接throws这个异常，意思就是出现了这个异常也不管它
        3. 中断进程的方法就是interrupt这个进程，在catch中接住这个异常，然后break出死循环。
         */

//        Scanner in=new Scanner(System.in); //使用Scanner类定义对象
//        System.out.println("please input a integer number");
//        int b=in.nextInt(); //接收整形数据
//        System.out.println(b);
        Runnable threadJob = new MyRunnable();    //第一步
        ArrayList<Thread> MyThreads = new ArrayList<>();
//        int index = MyThreads.size();
//        Thread thread1 = new Thread(threadJob);
//        MyThreads.add(thread1);
//        MyThreads.get(index).setName("Thread "+index);
//        MyThreads.get(index).start();
        addThread(threadJob, MyThreads);
        addThread(threadJob, MyThreads);
        Thread.sleep(5000);
        interruptThread(MyThreads,0);
        interruptThread(MyThreads,1);
        System.out.println("Horay, done");
    }

    private static void addThread(Runnable threadJob, ArrayList<Thread> MyThreads){
        int index = MyThreads.size();
        Thread thread1 = new Thread(threadJob);
        MyThreads.add(thread1);
        MyThreads.get(index).setName("Thread "+index);
        MyThreads.get(index).start();

    }

    private static void interruptThread(ArrayList<Thread> MyThreads, int index){
        MyThreads.get(index).interrupt();
    }

}
