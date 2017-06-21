
public class Controller {
	Computer[] computers;
	Switch[] switchs;
	public static final int CNUM=6,SNUM=2;
	public static final String[] CNAME={"A1","A2","A3","B1","B2","B3"};
	public static final String[] SNAME={"A","B"};
	public static final int[] CPORT={10000,10001,10002,20000,20001,20002};
	public static final int[] SPORT={30000,30001};
	public static final String[] CMAC={"01-00-00-00-00-00",
			"02-00-00-00-00-00","03-00-00-00-00-00",
			"07-00-00-00-00-00","08-00-00-00-00-00","09-00-00-00-00-00"};
	
	public Controller(){
		
		//基本属性初始化
		computers=new Computer[CNUM];
		switchs=new Switch[SNUM];
		for(int i=0;i<CNUM;i++){
			computers[i]=new Computer(CNAME[i],CPORT[i],CMAC[i]);
		}
		for(int i=0;i<SNUM;i++){
			switchs[i]=new Switch(SNAME[i], SPORT[i]);
		}
		
		//图结构初始化
		computers[0].setSw(switchs[0]);
		computers[1].setSw(switchs[0]);
		computers[2].setSw(switchs[0]);
		computers[3].setSw(switchs[1]);
		computers[4].setSw(switchs[1]);
		computers[5].setSw(switchs[1]);
		switchs[0].setInter(switchs[1], 
				computers[0], computers[1], computers[2]);
		switchs[1].setInter(switchs[0], 
				computers[3], computers[4], computers[5]);
		
		for(int i=0;i<SNUM;i++){
			new Thread(this.switchs[i]).start();
			this.switchs[i].openView();
			switchs[i].clockStart();
		}
		for(int i=0;i<CNUM;i++){
			new Thread(this.computers[i]).start();
			this.computers[i].openView();
		}
	}

}
