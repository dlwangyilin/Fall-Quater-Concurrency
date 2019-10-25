public class TrafficControllerstop {
    private int left=0;
    private int right=0;
    private int waitleft = 0;
    private int waitright = 0;
    private boolean turn = true;

    public synchronized void enterLeft() throws InterruptedException {
        ++ waitleft;
        while(right>0 || (waitright>0 && turn)){
            wait();
        }
        -- waitleft;
        ++ left;

    }
    public synchronized void enterRight() throws InterruptedException {
        ++ waitright;
        while(left>0 || (waitleft>0 && turn)){
            wait();
        }
        -- waitright;
        ++ right;
    }
    public synchronized void leaveLeft() {
        -- right;
        turn = true;
        if(right==0){
            notifyAll();
        }
    }
    public synchronized void leaveRight() {
        -- left;
        turn = false;
        if(left==0){
            notifyAll();
        }
    }

}