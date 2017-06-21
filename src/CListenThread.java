import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JTextArea;

public class CListenThread extends Thread {
	Socket socket;
	Computer com;

	public CListenThread(Socket socket, Computer com) {
		this.socket = socket;
		this.com = com;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectInputStream ois = null;
		Object obj = null;
		Frame frame = null;

		try {
			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

			if ((obj = ois.readObject()) != null) {
				frame = (Frame) obj;
				
				if(frame.getSrcServerPort()!=this.com.getSw().getServerPort()){
					System.out.println("电脑 "+this.com.getName()+" 遇到一个奇怪的连接");
					try{
						socket.close();
					}catch(Exception e){}
					return;
				}
				
				JTextArea jta = this.com.getCv().jta;
				if (this.com.isDst(frame)) {
					jta.append(" from " + frame.getSrcMac() + " : " + frame.getContent() + "\r\n");
				} else {
//					jta.append(" abort , source : " + frame.getSrcMac() + "\r\n");
				}
			}
		} catch (Exception e) {

		} finally {
//			System.out.println("client "+socket.getPort()+
//					" disconnect with "+"server "+socket.getLocalPort());
			try {
				socket.close();
			} catch (Exception e) {}
		}
	}
}
