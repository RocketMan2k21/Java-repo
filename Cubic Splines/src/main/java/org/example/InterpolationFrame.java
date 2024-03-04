package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

public class InterpolationFrame extends JFrame {

    private CordinatePanel cordPanel;
    private GraphPanel graphPanel;
    private JButton calcButton;
    private JButton viewPolynom;
    private JButton saveBtn;
    private String splinePolynomials = "";
    private String linearFunctions = "";

    public InterpolationFrame() {

        //Встановлення менеджеру компоновки
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Додавання панелі з таблицею та кнопками до головного вікна
        cordPanel = new CordinatePanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        add(cordPanel, gbc);

        //Панель графіку
        graphPanel = new GraphPanel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        add(graphPanel, gbc);

        //Коли натиснута кнопка Розрахувати
        calcButton = cordPanel.getCalcBtn();
        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Point> points = new ArrayList<>();
                try {
                    //Скануємо таблицю
                    LoadFile input = new LoadFile();
                    points = input.readFile(cordPanel.getPathFile());
                    if (cordPanel.getButtonGroup().getLinearBtn().isSelected()) {

                        splinePolynomials = "";
                        graphPanel.getChart().updateLinChart(points); //Оновлюємо графік
                        linearFunctions = graphPanel.getChart().getLinearInt().getFunctions();//Отримуємо функції

                    } else if (cordPanel.getButtonGroup().getCubicBtn().isSelected()) {

                        linearFunctions = "";
                        graphPanel.getChart().updateCubicChart(points);//Оновлюємо графік
                        splinePolynomials = graphPanel.getChart().getSpline().getPolynomials();//Отримуємо поліноми


                    } else {
                        JOptionPane.showMessageDialog(getContentPane(), "Не обрано метод інтерполяції.", "Зауваження", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(getContentPane(), "Виникла проблема: " + ex, "Помилка", JOptionPane.ERROR_MESSAGE);
                }


            }
        });

        //Кнопка для перегляду лінійних функцій та поліномів сплайну
        viewPolynom = cordPanel.getViewPolynom();
        viewPolynom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!linearFunctions.isEmpty() || !splinePolynomials.isEmpty()) {
                    JOptionPane.showMessageDialog(getContentPane(), splinePolynomials + linearFunctions, "Поліноми", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(getContentPane(), "Не обраховано", "Помилка", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //Кнопка збереження результатів
        saveBtn = cordPanel.getSaveBtn();
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!linearFunctions.isEmpty() || !splinePolynomials.isEmpty()) {
                    try {
                        new Saving(splinePolynomials + linearFunctions);//Зберігаємо результати у файл
                    } catch (FileSystemException ex) {
                        JOptionPane.showMessageDialog(getContentPane(), "Виникла проблема: " + ex, "Помилка", JOptionPane.ERROR_MESSAGE);;
                    }
                    JOptionPane.showMessageDialog(getContentPane(), "Результати збережено успішно");
                } else {
                    JOptionPane.showMessageDialog(getContentPane(), "Немає результатів", "Помилка", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Операція на закриття вікна
        setSize(700, 500);
        setTitle("Інтерполяція");
        setVisible(true);
    }
}
