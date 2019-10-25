public class ThreadTestDrive {
    public static void main(String[] args){
        /*
        多线程的操作步骤:
        0. 编写一个 implements Runable的类, 如 MyRunnable. 这个类需要override public voir run()这个函数.
        1. 建立Runnable的对象threadJob
        2. 建立Thread对象myThread，并传入刚刚Runable的对象threadJob
        3. 启动Thread. myThread.start(). 这会开始执行run()这个函数
         */

        Runnable threadJob = new MyRunnable();    //第一步
        Thread myThread = new Thread(threadJob);    //第二步
        myThread.start();    //第三步
        System.out.println("back in the main");

    }
}
