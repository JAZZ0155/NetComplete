import java.util.Date;
import java.util.Vector;

public class SwitchTableThread extends Thread {
	
	public static final int THRESHOLD=60;
	Switch swi;
	Vector<Vector> data;
	boolean isOpen;
	
	public SwitchTableThread(Switch swi){
		this.swi=swi;
		this.data=swi.getData();
		this.isOpen=false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Æô¶¯");
		this.isOpen=true;
		Date now,temp;
		while(this.isOpen){
			now=new Date();
			for(int i=0;i<data.size();i++){
				temp=(Date) data.get(i).get(2);
				long interval = (now.getTime() - temp.getTime())/1000;
				if(interval>this.THRESHOLD){
					System.out.println(this.swi.getName()+
							" remove "+data.get(i).get(0).toString());
					data.remove(i--);
					this.swi.getView().flush();
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
