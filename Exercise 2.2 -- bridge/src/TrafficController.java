public class TrafficController {
    private int left=0;
    private int right=0;

    public synchronized void enterLeft() throws InterruptedException {
        while(right>0){
            wait();
        }
        ++ left;
    }
    public synchronized void enterRight() throws InterruptedException {
        while(left>0){
            wait();
        }
        ++ right;
    }
    public synchronized void leaveLeft() {
        -- right;
        if(right==0){
            notifyAll();
        }
    }
    public synchronized void leaveRight() {
        -- left;
        if(left==0){
            notifyAll();
        }
    }

}