package org.example;

import org.jfree.chart.ChartPanel;
import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {
    private Chart chart;

    //Створення панелі для графіку
    public GraphPanel(){
        setLayout(new BorderLayout());
        chart = new Chart();
        ChartPanel chartPanel = new ChartPanel(chart.initializeChart());
        add(chartPanel);
    }

    public Chart getChart() {
        return chart;
    }
}
