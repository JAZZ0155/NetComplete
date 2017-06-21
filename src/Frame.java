
public class Frame implements java.io.Serializable{
	
	private String SrcMac;
	private String DstMac;
	private String Content;
	/*
	 * 用来标记是哪个端口发出的对象，在传输过程中会改变，加了这个变量纯属无奈
	 *与Com和Swi中的ServerPort字段对应
	 * */
	private int SrcServerPort;			
	
	public Frame(String src,String dst,String content,int srcserverport){
		this.SrcMac=src;
		this.DstMac=dst;
		this.Content=content;
		this.SrcServerPort=srcserverport;
	}
	
	public void show(){
		System.out.println("from "+this.getSrcMac()+
				" to "+this.getDstMac()+" : "+this.getContent());
	}
	
	public String getSrcMac() {
		return SrcMac;
	}
	public void setSrcMac(String srcMac) {
		SrcMac = srcMac;
	}
	public String getDstMac() {
		return DstMac;
	}
	public void setDstMac(String dstMac) {
		DstMac = dstMac;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public int getSrcServerPort() {
		return SrcServerPort;
	}

	public void setSrcServerPort(int srcServerPort) {
		SrcServerPort = srcServerPort;
	}
}
