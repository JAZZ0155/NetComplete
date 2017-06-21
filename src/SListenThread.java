import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SListenThread extends Thread{
	Socket socket;
	Switch swi;
	
	public SListenThread(Socket socket,Switch swi){
		this.socket=socket;
		this.swi=swi;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectInputStream ois;
		Object obj;
		Frame frame;
		int srcinter=-1;
		int dstinter=-2;
		
		try{
			ois=new ObjectInputStream
					(new BufferedInputStream(socket.getInputStream()));
			if((obj=ois.readObject()) != null){
				frame=(Frame)obj;
				//得到接口号
				srcinter=this.swi.portToInter(frame.getSrcServerPort());
				if(srcinter==-1){
					System.out.println("交换机 "+this.swi.getName()+" 遇到一个奇怪的连接");
					try{
						socket.close();
					}catch(Exception e){}
					return;
				}
				
				//更新交换表
				this.swi.reNewTable(frame.getSrcMac(), srcinter);
				//确定发送接口
				dstinter=this.swi.forward(frame);
				
				if(dstinter==srcinter){	//丢弃该帧
					return;
				}
				frame.setSrcServerPort(this.swi.getServerPort());
				if(dstinter==-1){	//广播
					for(int i=0;i<this.swi.getInterSize();i++){
						if(i!=srcinter){
							this.swi.send(frame,i);
						}
					}
				}else{	//单个转发
					this.swi.send(frame, dstinter);
				}
			}
			 
		}catch(Exception e){
			
		}finally{
//			System.out.println("client "+socket.getPort()+
//					" disconnect with "+"server "+socket.getLocalPort());
			try{
				socket.close();
			}catch(Exception e){}
		}

	}
}
