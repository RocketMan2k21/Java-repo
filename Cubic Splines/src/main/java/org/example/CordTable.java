package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;


public class CordTable extends JTable {
    private JTextField xField;
    private JTextField yField;

    public CordTable(DefaultTableModel tableModel){

        super(tableModel);
        setAlignmentX(Component.LEFT_ALIGNMENT);

        getTableHeader().setReorderingAllowed(false);
        //Поле для вводу даних
        xField = new JTextField();
        yField = new JTextField();


    }

    //Сканування таблиці з точками
    public ArrayList<Point> scanFields() {
        int rowCount = getRowCount(); // к-сть рядків
        ArrayList<Point> points= new ArrayList<>();
        for(int row = 0; row < rowCount; row++)
            if(getValueAt(row, 1) != null && getValueAt(row, 2) != null){ //якщо рядок з координатою не пустий то записуємо в масив
                Object valueX = getValueAt(row, 1);
                Object valueY = getValueAt(row, 2);
                if(!valueX.toString().isEmpty() && !valueY.toString().isEmpty()) {
                    if((Double.valueOf(valueX.toString()) > Double.MAX_VALUE || Double.valueOf(valueY.toString()) > Double.MAX_VALUE) ||
                            (Double.valueOf(valueX.toString()) < -Double.MAX_VALUE || Double.valueOf(valueY.toString()) < -Double.MAX_VALUE)){
                        throw new ArithmeticException("Значення не визначене, введіть менше число");
                    }
                    else {
                        points.add(new Point(Double.valueOf(valueX.toString()), Double.valueOf(valueY.toString())));
                    }

                }

            }

        return points;
    }


}



