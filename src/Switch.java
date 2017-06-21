import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Switch implements Runnable{
	
	public static final int[] x={1100,1100};
	public static final int[] y={0,250};
	static int publicindex=0;
	int index;
	
	private String Name;						//名字
	private int ServerPort;						//服务端口
	private Vector<String> columns;
	/*
	 * String,int,Date
	 * */
	private Vector<Vector> data;
	private Object[] Inter;						//接口,4
	private int InterSize;						//接口数
	private boolean IsPostOpen;					//端口是否打开
	
	private SwitchView Sv;						//界面
	private SwitchTableThread stt;				//交换表计时器
	
	public Switch(String Name,int port){
		this.Name=Name;
		this.ServerPort=port;
		this.IsPostOpen=false;
		this.InterSize=4;
		Inter=new Object[4];
		this.index=publicindex++;
		
		columns=new Vector<String>();
		columns.add("Mac");
		columns.add("Interface");
		columns.add("Time");
		data=new Vector<Vector>();
		stt=new SwitchTableThread(this);
	}
	
	public void clockStart(){
		this.stt.start();
	}
	
	public void setInter(Object o1,Object o2,Object o3,Object o4){
		this.Inter[0]=o1;	//把0号接口作为另一个交换机的接口
		this.Inter[1]=o2;
		this.Inter[2]=o3;
		this.Inter[3]=o4;
	}
	
	public void openView(){
		if(Sv==null){
			Sv=new SwitchView(this);
			Sv.setVisible(true);
		}else{
			Sv.setVisible(true);
		}
	}
	
	//发送时，所有条件已经具备
	public void send(Frame frame,int inter) throws Exception{
		int dstserverport=this.interToPort(inter);
		Socket socket=new Socket("127.0.0.1",dstserverport);
		ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(frame);
		socket.close();
	}
	
	/*
	 * 输入端口号，输出端口对应对象(Computer/Switch)在本交换机的接口序号(Object[?])
	 * 正常情况必会找到匹配，没有匹配返回-1
	 * */
	public int portToInter(int srcServerPort){
		for(int i=0;i<4;i++){
			if(Inter[i] instanceof Computer){
				Computer c=(Computer)Inter[i];
				if(srcServerPort==c.getServerPort()){
					return i;
				}
			}else{
				Switch s=(Switch)Inter[i];
				if(srcServerPort==s.getServerPort()){
					return i;
				}
			}
		}
		return -1;
	}
	
	public int interToPort(int inter){
		Object o=this.Inter[inter];
		if(o instanceof Computer){
			Computer c=(Computer)o;
			return c.getServerPort();
		}else{
			Switch s=(Switch)o;
			return s.getServerPort();
		}
	}
	
	public void reNewTable(String mac,int inter){
		for(Vector row:data){
			String str=(String) row.get(0);
			if(str.equals(mac)){
				row.set(1, inter);
				row.set(2, new java.util.Date());
				System.out.println(this.getName()+" update table : mac="+mac+" inter="+inter);
				this.Sv.flush();
				return;
			}
		}
		Vector v=new Vector();
		v.add(mac);
		v.add(inter);
		v.add(new java.util.Date());
		this.data.add(v);
		System.out.println(this.getName()+" append table : mac="+mac+" inter="+inter);
		this.Sv.flush();
	}
	
	//转发，表中没有则返回-1,表示发送广播
	public int forward(Frame frame){
		for(int i=0;i<data.size();i++){
			String mac=data.get(i).get(0).toString();
			if(mac.equals(frame.getDstMac())){
				System.out.println(this.getName()+" forward "+ mac +" "+i);
				return Integer.parseInt(data.get(i).get(1).toString());
			}
		}
		System.out.println(this.getName()+" forward "+frame.getDstMac()+" -1");
		return -1;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.IsPostOpen=true;
		ServerSocket server = null;
		try {
			server = new ServerSocket(this.ServerPort);
		} catch (Exception e) {}
		System.out.println(this.Name+" open port : "+this.ServerPort);
		while(this.IsPostOpen){
			Socket socket = null;
			try {
				socket = server.accept();
			} catch (Exception e) {}
//			System.out.println("client "+socket.getPort()+
//					" connect to server "+server.getLocalPort());
			new SListenThread(socket,this).start();
		}
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public int getServerPort() {
		return ServerPort;
	}

	public void setServerPort(int port) {
		this.ServerPort = port;
	}

	public Object[] getInter() {
		return Inter;
	}

	public void setInter(Object[] Inter) {
		this.Inter = Inter;
	}

	public int getInterSize() {
		return InterSize;
	}

	public Vector<String> getColumns() {
		return columns;
	}

	public void setColumns(Vector<String> columns) {
		this.columns = columns;
	}

	public Vector<Vector> getData() {
		return data;
	}

	public void setData(Vector<Vector> data) {
		this.data = data;
	}

	public void setInterSize(int interSize) {
		InterSize = interSize;
	}

	public SwitchView getView() {
		return Sv;
	}

}
