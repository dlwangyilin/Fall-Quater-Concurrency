import java.util.concurrent.*;

public class Main3 {
    private static void nap(int millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void addProc(HighLevelDisplay d, Semaphore sem) throws InterruptedException {
    // Add a sequence of addRow operations with short random naps.
       for(int i=0; i < 20; i++) {
           sem.acquire();
           d.addRow("FlightAAA" + i);
           d.addRow("FlightAAA" + i);
           sem.release();
           nap(50);
       }

    }

    private static void deleteProc(HighLevelDisplay d, Semaphore sem) throws InterruptedException {
    // Add a sequence of deletions of row 0 with short random naps.
        for(int i=0; i < 20; i++) {
            sem.acquire();
            d.deleteRow(0);

            sem.release();
            nap(200);
        }

    }

    public static void main(String [] args) {
        final HighLevelDisplay d = new JDisplay2();
        final Semaphore sem = new Semaphore(1);

        new Thread () {
            public void run() {
                try {
                    addProc(d,sem);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        new Thread () {
            public void run() {
                try {
                    deleteProc(d,sem);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}