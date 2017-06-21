import java.net.ServerSocket;
import java.net.Socket;

public class Computer implements Runnable{
	
	public static final int[] x={0,350,700,0,350,700};
	public static final int[] y={0,0,0,250,250,250};
	static int publicindex=0;
	int index;
	
	private String Name;		//名字
	private String Mac;			//物理地址
	private int ServerPort;		//服务端口
	private Switch Sw;			//接口
	private ComputerView Cv;	//界面
	boolean IsPortOpen;			//监听端口是否打开
	
	public Computer(String name,int port,String mac){
		this.Name=name;
		this.ServerPort=port;
		this.Mac=mac;
		this.IsPortOpen=false;
		this.index=publicindex++;
	}
	
	public void openView(){
		if(Cv==null){
			Cv=new ComputerView(this);
			Cv.setVisible(true);
		}else{
			Cv.setVisible(true);
		}
	}
	
	public boolean isDst(Frame frame) {
		if (frame.getDstMac().equals(this.getMac())) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void run(){
		// TODO Auto-generated method stub
		this.IsPortOpen=true;
		ServerSocket server = null;
		try {
			server = new ServerSocket(this.ServerPort);
		} catch (Exception e) {}
		System.out.println(this.Name+" open port : "+this.ServerPort);
		while(this.IsPortOpen){
			Socket socket = null;
			try {
				socket = server.accept();
			} catch (Exception e) {}
//			System.out.println("client "+socket.getPort()+
//					" connect to server "+server.getLocalPort());
			new CListenThread(socket,this).start();
		}
	}
	
	public void closePort(){
		this.IsPortOpen=false;
	}
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getMac() {
		return Mac;
	}

	public void setMac(String mac) {
		this.Mac = mac;
	}

	public int getServerPort() {
		return ServerPort;
	}

	public void setServerPort(int port) {
		this.ServerPort = port;
	}

	public Switch getSw() {
		return Sw;
	}

	public void setSw(Switch sw) {
		this.Sw = sw;
	}
	
	public ComputerView getCv() {
		return Cv;
	}

	public void setCv(ComputerView cv) {
		this.Cv = cv;
	}
	
}
