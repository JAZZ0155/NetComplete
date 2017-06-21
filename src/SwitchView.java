import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SwitchView extends JFrame {

	JPanel contentPane;
	JTable table;
	JScrollPane jsp;
	Switch swi;
	MyModel model;
	
	public void flush(){
		jsp.setViewportView(table);
		this.contentPane.updateUI();
		this.requestFocus();
	}

	public SwitchView(Switch swi) {
		this.swi=swi;
		setResizable(false);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(this.swi.getName()+' '+this.swi.getServerPort());
		setBounds(this.swi.x[this.swi.index],
				this.swi.y[this.swi.index], 340, 163);
		setVisible(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		model=new MyModel(this.swi.getData(),this.swi.getColumns());
		table.setModel(model);
		jsp=new JScrollPane(table);
		contentPane.add(jsp, BorderLayout.CENTER);
	}
}
