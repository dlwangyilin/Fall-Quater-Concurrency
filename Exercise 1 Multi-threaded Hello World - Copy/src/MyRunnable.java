import java.text.SimpleDateFormat;
import java.util.Date;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        while(true){
            synchronized (this){
                printtime();
            }

            try{
                Thread.sleep(2000);
            } catch(InterruptedException ex){
                // ex.printStackTrace();
                break;
            }
        }

    }

    private void printtime() {
        String threadName = Thread.currentThread().getName();
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        System.out.print("Hello World! I'm thread ");
        System.out.print(threadName);
        System.out.print(". The time is ");
        System.out.print(sdf.format(date));
        System.out.println(".");
    }
}
