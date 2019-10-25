public class MyRunnable implements Runnable {

    @Override
    public void run() {
        go();
        // 如果要用sleep，必须把它放在try/catch中
        try{
            Thread.sleep(2000);
        } catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }
    private void go(){
        doMore();
    }
    private void doMore(){
        System.out.println("top of the stack");
    }
}
