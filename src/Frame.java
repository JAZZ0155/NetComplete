
public class Frame implements java.io.Serializable{
	
	private String SrcMac;
	private String DstMac;
	private String Content;
	/*
	 * ����������ĸ��˿ڷ����Ķ����ڴ�������л�ı䣬�������������������
	 *��Com��Swi�е�ServerPort�ֶζ�Ӧ
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
