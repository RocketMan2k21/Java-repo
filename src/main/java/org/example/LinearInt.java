package org.example;

import java.util.*;

public class LinearInt {
    private double[] x;
    private double[] y;
    private double[] b;
    private double[] t;
    private int iterations;


    public LinearInt(ArrayList<Point> points) throws NotEnoughPointsLinearException, XcollisionException{

        iterations = 0;
        sortPoints(points);
        getPoints(points);

        int n = points.size() - 1;
        b = new double[n];
        t = new double[n];

        for(int i = 0; i < n; i++){
            b[i] = y[i+1] - y[i];//Різниця ігреків
            t[i] = x[i+1] - x[i];//Різниця іксів
            if((b[i] > Double.MAX_VALUE || t[i] > Double.MAX_VALUE)||(b[i] < -Double.MAX_VALUE || t[i] < -Double.MAX_VALUE) ){
                throw new ArithmeticException("Помилка при обрахуванні, введіть менші значення");
            }
            iterations++;
        }
    }

    public double interpolate(double xi){
        if(xi > Double.MAX_VALUE || xi < -Double.MAX_VALUE){
            throw new ArithmeticException("Помилка при обрахуванні, введіть менші значення");
        }
        int i = findIndex(xi);
        double dx = xi - x[i];
        if(dx > Double.MAX_VALUE || dx < -Double.MAX_VALUE){
            throw new ArithmeticException("Помилка при обрахуванні, введіть менші значення");
        }
        return b[i]/t[i] * dx + y[i];
    }

    private int findIndex(double xi) {
        int lo = 0;
        int hi = x.length - 1;
        while (hi - lo > 1) {
            int mid = (lo + hi) / 2;
            if (x[mid] > xi) {
                hi = mid;
            } else {
                lo = mid;
            }
        }
        return lo;
    }

    private void getPoints (ArrayList<Point> points) throws NotEnoughPointsLinearException, XcollisionException{
        int pointNum = points.size();
        x = new double[pointNum];
        y = new double[pointNum];
        if(pointNum < 2){
            throw new NotEnoughPointsLinearException();
        }
        for(int i = 0; i < pointNum; i++) {
            this.x[i] = points.get(i).getX();
            this.y[i] = points.get(i).getY();
        }

        if(hasDuplicates(x)){
            throw new XcollisionException();
        }
    }

    private static boolean hasDuplicates(double[] array) {
        Set<Double> set = new HashSet<>();

        for (double element : array) {
            if (set.contains(element)) {
                return true; // Знайдено повторювальний елемент
            }
            set.add(element);
        }

        return false; // Немає повторювальних елементів
    }

    private void sortPoints(ArrayList<Point> points){
        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                if (p1.getX() < p2.getX()) {
                    return -1;
                } else if (p1.getX() > p2.getX()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }



    public String getFunctions(){
        String funcs = "";

        for(int i = 0; i < b.length; i++){
            funcs += "y" + i + " = " + b[i]/t[i] + "(x - " + x[i] + ") + " + y[i] + "; " + "[" + x[i] + "," + x[i+1] + "]" + '\n';
        }
        funcs += "Ітерації: " + iterations + "\n";
        return funcs;
    }

}


