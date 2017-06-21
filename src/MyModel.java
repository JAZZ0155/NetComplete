import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class MyModel implements TableModel {
	
	Vector<Vector> data;
	Vector<String> columns;

	public MyModel(Vector<Vector> data,Vector<String> columns){
		this.data=data;
		this.columns=columns;
	}
	
	Class[] columnTypes = new Class[] {
			String.class, Integer.class, String.class
		};
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columns.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return columns.get(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return columnTypes[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return data.get(rowIndex).get(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		data.get(rowIndex).set(columnIndex, aValue);
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

}
