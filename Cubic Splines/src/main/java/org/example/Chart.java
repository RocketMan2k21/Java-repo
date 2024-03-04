package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Chart {
    private DefaultXYDataset dataset;
    private JFreeChart chart;
    private CubicSplineInt spline;
    private LinearInt linearInt;
    public void updateCubicChart(ArrayList<Point> points) throws NotEnoughPointsCubicException, XcollisionException {

        //Очищення графіку
        dataset.removeSeries("Лінійні відрізки");
        dataset.removeSeries("Сплайн");
        dataset.removeSeries("Точки");


        // Створення сплайну
        spline = new CubicSplineInt(points);


        // Створення сплайнів для графіку
        double[][] splineData = new double[2][256];
        ArrayList<Double> xValues = linspace(points.get(0).getX(), points.get(points.size() - 1).getX(), 256);//Точки для проміжних значень
        ArrayList<Double> yValues = new ArrayList<>();
        for (Double x : xValues) {
            yValues.add(spline.interpolate(x));//Інтерполюємо в  проміжних значення
        }
        for (int i = 0; i < xValues.size(); i++) {
            splineData[0][i] = xValues.get(i);
            splineData[1][i] = yValues.get(i);
        }

        // Створення вузлових точок для графіку
        double[][] pointData = new double[2][points.size()];
        for (int i = 0; i < points.size(); i++) {
            pointData[0][i] = points.get(i).getX();
            pointData[1][i] = points.get(i).getY();
        }

        //Додавання даних на графік
        dataset.addSeries("Точки", pointData);
        dataset.addSeries("Сплайн", splineData);

    }

    public void updateLinChart(ArrayList<Point> points) throws NotEnoughPointsLinearException, XcollisionException{
        dataset.removeSeries("Лінійні відрізки");
        dataset.removeSeries("Сплайн");
        dataset.removeSeries("Точки");

        linearInt = new LinearInt(points);

        // Створення відрізків для графіку
        double[][] splineData = new double[2][256];
        ArrayList<Double> xValues = linspace(points.get(0).getX(), points.get(points.size() - 1).getX(), 256);//Точки для проміжних значень
        ArrayList<Double> yValues = new ArrayList<>();
        for (Double x : xValues) {
            yValues.add(linearInt.interpolate(x));//Інтерполюємо в  проміжних значення
        }
        for (int i = 0; i < xValues.size(); i++) {
            splineData[0][i] = xValues.get(i);
            splineData[1][i] = yValues.get(i);
        }

        double[][] pointData = new double[2][points.size()];
        for (int i = 0; i < points.size(); i++) {
            pointData[0][i] = points.get(i).getX();
            pointData[1][i] = points.get(i).getY();
        }


        dataset.addSeries("Точки", pointData);
        dataset.addSeries("Лінійні відрізки", splineData);

    }


    public JFreeChart initializeChart(){

        dataset = new DefaultXYDataset();

        // Створення порожнього графіка з віссю ігрек та ікс
        chart = ChartFactory.createXYLineChart(
                "Графік",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        // Форма точок
        Shape pointShape = new Ellipse2D.Double(-3, -3, 5, 5);//Задання форми для точок
        Shape pointShape1 = new Ellipse2D.Double(-6, -6, 15, 15);
        XYLineAndShapeRenderer render =new XYLineAndShapeRenderer();
        render.setSeriesShape(1, pointShape);
        render.setSeriesShape(0, pointShape1);


        render.setSeriesLinesVisible(0, false);//Лінії між точками вимкнені
        render.setSeriesLinesVisible(1, false);
        plot.setRenderer(render);

        return chart;
    }

    //Створення масиву з проміжними точками
    private static ArrayList<Double> linspace(double start, double end, int points) {
        ArrayList<Double> array = new ArrayList<>();
        if(end - start > Double.MAX_VALUE || end - start < -Double.MAX_VALUE){
            throw new ArithmeticException("Помилка при обрахуванні, введіть менші значення");
        }
        double step = (end - start) / (points - 1);
        double current = start;
        for (int i = 0; i < points; i++) {
            array.add(current);
            current += step;
        }
        return array;
    }


    public CubicSplineInt getSpline() {
        return spline;
    }

    public LinearInt getLinearInt() {
        return linearInt;
    }
}
