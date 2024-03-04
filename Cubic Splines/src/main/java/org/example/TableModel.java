package org.example;
import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {

    public TableModel(String[] columnNames, int rowCount){
        super(columnNames, rowCount);

        setValueAt(1, 0, 0);
        setValueAt(2, 1, 0);
        setValueAt(3, 2, 0);
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return column != 0;
    }


}
