package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CordinatePanel extends JPanel {

    private CordTable jTable;
    private RadioButtonGroup buttonGroup;
    private JButton calcBtn;
    private JButton plusBtn;
    private JButton viewPolynom;
    private JButton saveBtn;
    private String pathFile;
    private JButton readButton;

    public CordinatePanel(){

        String[] columnNames = {"№ точки", "X", "Y"};
        setBackground(Color.WHITE);

        //Ініціалізація компонентів
        jTable = new CordTable(new TableModel(columnNames, 3));
        buttonGroup = new RadioButtonGroup();
        calcBtn = new CalculateButton();
        plusBtn = new JButton("+");
        viewPolynom = new JButton("Поліноми");
        saveBtn = new JButton("Зберегти результати");
        readButton = new JButton("Відкрити файл");

        //Менеджер компоновки для панеі
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel twoBtn = new JPanel();
        twoBtn.setLayout(new BoxLayout(twoBtn, BoxLayout.X_AXIS));
        JButton clearBtn = new JButton("Очистити");

        add(new JScrollPane(jTable));
        add(Box.createRigidArea(new Dimension(0, 5)));

        twoBtn.add(plusBtn);
        twoBtn.add(Box.createRigidArea(new Dimension(50, 0)));
        twoBtn.add(clearBtn);

        twoBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(twoBtn);

        add(Box.createRigidArea(new Dimension(0, 10)));

        add(buttonGroup.getLinearBtn());
        add(buttonGroup.getCubicBtn());

        add(Box.createRigidArea(new Dimension(0, 5)));

        add(calcBtn);

        add(Box.createRigidArea(new Dimension(0, 20)));

        viewPolynom.setMaximumSize(saveBtn.getMaximumSize());
        add(viewPolynom);

        add(Box.createRigidArea(new Dimension(0, 20)));
        add(saveBtn);
        add(readButton);

        add(Box.createRigidArea(new Dimension(0, 10)));


        //Кнопка плюс під таблицею
        plusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) jTable.getModel();
                if(model.getRowCount() < 20) {
                    model.addRow(new Object[]{model.getRowCount() + 1, "", ""});
                }
            }
        });

        //Кнопка очистити
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) jTable.getModel();
                model.setRowCount(3);

                model.setValueAt(null, 0, 1);
                model.setValueAt(null, 1, 1);
                model.setValueAt(null, 2, 1);
                model.setValueAt(null, 0, 2);
                model.setValueAt(null, 1, 2);
                model.setValueAt(null, 2, 2);
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
// Open the save dialog
                chooser.showSaveDialog(null);
                int returnVal = chooser.showOpenDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    pathFile = chooser.getSelectedFile().getAbsolutePath();
                }
            }
        });


    }

    public JButton getCalcBtn() {
        return calcBtn;
    }

    public CordTable getjTable() {
        return jTable;
    }

    public RadioButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public JButton getViewPolynom() {
        return viewPolynom;
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public String getPathFile() {
        return pathFile;
    }


}
