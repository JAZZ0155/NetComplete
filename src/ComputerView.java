import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ComputerView extends JFrame implements ActionListener{
	
	Computer com;
	
	JTextArea jta;
	JTextField jtf1,jtf2;
	JScrollPane jsp;
	JButton jb;
	
	public ComputerView(Computer c){
		
		this.com=c;
		setResizable(false);
		setTitle(this.com.getName()+' '+this.com.getServerPort()+' '+this.com.getMac());
		setBounds(this.com.x[this.com.index],
				this.com.y[this.com.index], 300, 250);
		setVisible(false);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		jta = new JTextArea();
		//jta.setBounds(10, 10, 264, 128);
		jta.setLineWrap(true);
		jta.setEditable(false);
		//getContentPane().add(jta);
		
		jsp=new JScrollPane(jta);
		jsp.setBounds(10, 10, 264, 128);
		jsp.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(jsp);
		
		jtf1 = new JTextField();
		jtf1.setBounds(10, 147, 264, 20);
		getContentPane().add(jtf1);
		
		jb = new JButton("È·¶¨");
		jb.setBounds(10, 177, 93, 23);
		jb.addActionListener(this);
		getContentPane().add(jb);
		
		jtf2 = new JTextField();
		jtf2.setBounds(113, 178, 161, 22);
		getContentPane().add(jtf2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String content=this.jtf1.getText();
		String dst=this.jtf2.getText();
		String src=this.com.getMac();
		Frame frame=new Frame(src,dst,content,this.com.getServerPort());
		this.jta.append(" to "+dst+" : "+content+"\r\n");
		this.jtf1.setText("");
		
		Socket socket = null;

		try {
			socket=new Socket("127.0.0.1",this.com.getSw().getServerPort());
			ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(frame);
		}catch(Exception ex){
			
		}finally{
			try{
				socket.close();
			}catch(Exception ex){}
		}

	}
	
}
