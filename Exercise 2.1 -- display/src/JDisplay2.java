import java.util.concurrent.atomic.AtomicInteger;

public class JDisplay2 implements HighLevelDisplay {

    private JDisplay d;
    private String [] text;
	private AtomicInteger usedRows = new AtomicInteger(0);

    public JDisplay2(){
		d = new JDisplay();
		text = new String [100];
		clear();
    }

    private void updateRow(int row, String str) {
		text[row] = str;
		if (row < d.getRows()) {
			for(int i=0; i < str.length(); i++)
				d.write(row,i,str.charAt(i));
			for(int i=str.length(); i < d.getCols(); i++)
				d.write(row,i,' ');
		}
    }

    private void flashRow(int row, int millisecs) {
		String txt = text[row];
		try {
			for (int i= 0; i * 200 < millisecs; i++) {
			updateRow(row,"");
			Thread.sleep(70);
			updateRow(row,txt);
			Thread.sleep(130);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    }

    public void clear() {
		for(int i=0; i < d.getRows(); i++)
			updateRow(i,"");
		usedRows.set(0);
    }

    public synchronized void addRow(String str) {
		updateRow(usedRows.get(),str);
		flashRow(usedRows.get(),1000);
		usedRows.incrementAndGet();
    }

    public synchronized void deleteRow(int row) {
		if (row < usedRows.get()) {
			for(int i = row+1; i < usedRows.get(); i++)
			updateRow(i-1,text[i]);
			usedRows.decrementAndGet();
			updateRow(usedRows.get(),"");
			if(usedRows.get() >= d.getRows())
			flashRow(d.getRows()-1,1000);
		}
    }
}