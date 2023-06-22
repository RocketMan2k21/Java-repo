package org.example;

import java.util.*;

public class CubicSplineInt {
    private double[] x;
    private double[] y;

    private double[] a;
    private double[] b;
    private double[] c;
    private double[] d;
    private int iterations;

    public CubicSplineInt(ArrayList<Point> points) throws NotEnoughPointsCubicException, XcollisionException {

        sortPoints(points);//сортування точок
        getPoints(points);//розбивка на масиви ікс та ігрек

        iterations = 0; //лічильник ітерацій

        int n = x.length - 1;

        // h значення
        double[] h = new double[n];
        for (int i = 0; i < n; i++) {
            h[i] = x[i + 1] - x[i];
            if(h[i] > Double.MAX_VALUE){
                throw new ArithmeticException("Помилка при обрахуванні, введіть менші значення");
            }
            iterations++;

        }

        // alpha значення
        double[] alpha = new double[n];
        for (int i = 1; i < n; i++) {
            double op0 = 3 / h[i];
            double op1 = y[i + 1] - y[i];
            double op2 = 3 / h[i - 1];
            double op3 = y[i] - y[i - 1];
            double op4 = op0 * op1;
            double op5 = op2 * op3;
            alpha[i] = op4 - op5;
            if(op0 > Double.MAX_VALUE || op0 < -Double.MAX_VALUE || op1 > Double.MAX_VALUE || op1 < -Double.MAX_VALUE ||
                    op2 > Double.MAX_VALUE || op2 < -Double.MAX_VALUE || op3 > Double.MAX_VALUE || op3 < -Double.MAX_VALUE ||
                    op4 > Double.MAX_VALUE || op5 > Double.MAX_VALUE || op4 < -Double.MAX_VALUE || op5 < -Double.MAX_VALUE ||
                    alpha[i] > Double.MAX_VALUE || alpha[i] < -Double.MAX_VALUE){
                throw new ArithmeticException("Помилка при обрахуванні, введіть менше значення.");
            }
            iterations++;
        }

        // Значення коефіцієнтів для тридіагональної матриці
        double[] l = new double[n + 1];
        double[] u = new double[n];
        double[] z = new double[n + 1];
        l[0] = 1;
        u[0] = 0;
        z[0] = 0;
        for (int i = 1; i < n; i++) {
            double op0 = x[i + 1] - x[i - 1];
            double op1 = 2 * op0;
            double op2 = h[i - 1] * u[i - 1];
            double op3 = op1 - op2;
            if(op0 > Double.MAX_VALUE || op0 < -Double.MAX_VALUE || op1 > Double.MAX_VALUE || op1 < -Double.MAX_VALUE ||
                    op2 > Double.MAX_VALUE || op2 < -Double.MAX_VALUE || op3 > Double.MAX_VALUE || op3 < -Double.MAX_VALUE){
                throw new ArithmeticException("Помилка при обрахуванні, введіть менше значення.");
            }
            l[i] = op3;
            u[i] = h[i] / l[i];
            if(u[i] > Double.MAX_VALUE || u[i] < -Double.MAX_VALUE){
                throw new ArithmeticException("Помилка при обрахуванні, введіть менше значення.");
            }

            double op4 = h[i - 1] * z[i - 1];
            double op5 = alpha[i] - op4;
            double op6 = op5/l[i];
            if(op4 > Double.MAX_VALUE || op4 < -Double.MAX_VALUE || op5 > Double.MAX_VALUE || op5 < -Double.MAX_VALUE ||
                    op6 > Double.MAX_VALUE || op6 < -Double.MAX_VALUE){
                throw new ArithmeticException("Помилка при обрахуванні, введіть менше значення.");
            }
            z[i] = op6;
            iterations++;
        }
        l[n] = 1;
        z[n] = 0;

        a = new double[n];
        c = new double[n + 1];
        b = new double[n];
        d = new double[n];
        c[n] = 0;

        //Прогонка матриці
        for (int j = n - 1; j >= 0; j--) {
            double op0 = u[j] * c[j + 1];
            double op1 = z[j] - op0;
            if(op0 > Double.MAX_VALUE || op0 < -Double.MAX_VALUE || op1 > Double.MAX_VALUE || op1 < -Double.MAX_VALUE){
                throw new ArithmeticException("Помилка при обрахуванні, введіть менше значення.");
            }
            c[j] = op1;

            double op2 = y[j + 1] - y[j];
            double op3 = op2/h[j];
            double op4 = c[j + 1] + 2 * c[j];
            double op5 = op4/3;
            double op6 = h[j] * op5;
            double op7 = op3 - op6;
            if(op2 > Double.MAX_VALUE || op2 < -Double.MAX_VALUE || op3 > Double.MAX_VALUE || op3 < -Double.MAX_VALUE ||
                    op4 > Double.MAX_VALUE || op4 < -Double.MAX_VALUE || op5 > Double.MAX_VALUE || op5 < -Double.MAX_VALUE ||
                    op6 > Double.MAX_VALUE || op6 < -Double.MAX_VALUE || op7 > Double.MAX_VALUE || op7 < -Double.MAX_VALUE){
                throw new ArithmeticException("Помилка при обрахуванні, введіть менше значення.");
            }
            b[j] = op7;

            double op8 = c[j + 1] - c[j];
            double op9 = 3 * h[j];
            double op10 = op8/op9;
            if(op8 > Double.MAX_VALUE || op8 < -Double.MAX_VALUE || op9 > Double.MAX_VALUE || op9 < -Double.MAX_VALUE ||
                    op10> Double.MAX_VALUE || op10 < -Double.MAX_VALUE){
                throw new ArithmeticException("Помилка при обрахуванні, введіть менше значення.");
            }
            d[j] = op10;

            iterations++;
        }

        for (int i = 0; i < n; i++) {
            a[i] = y[i];
            iterations++;
        }
    }

    //Інтерполяція в точці
    public double interpolate(double xi) {
        if(xi > Double.MAX_VALUE || xi < -Double.MAX_VALUE){
            throw new ArithmeticException("Помилка при обрахуванні, введіть менші значення");
        }
        int i = findIndex(xi);
        double dx = xi - x[i];
        if(dx > Double.MAX_VALUE || dx < -Double.MAX_VALUE){
            throw new ArithmeticException("Помилка при обрахуванні, введіть менші значення");
        }
        return a[i] + b[i] * dx + c[i] * dx * dx + d[i] * dx * dx * dx;
    }

    //функція для знаходження індексу точки на інтервалі
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

    //Алгоритм розбивки точок на масиви ікс та ігрек
    private void getPoints(ArrayList<Point> points) throws NotEnoughPointsCubicException, XcollisionException{
        int pointNum = points.size();
        x = new double[pointNum];
        y = new double[pointNum];
        if(pointNum < 3){
            throw new NotEnoughPointsCubicException();
        }
        for(int i = 0; i < pointNum; i++) {
            x[i] = points.get(i).getX();
            y[i] = points.get(i).getY();
        }

        if(hasDuplicates(x)){
            throw new XcollisionException();
        }
    }

    //Перевірка на дублікати в масиві
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

    //Сортування точок
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

    //Поліноми
    public String getPolynomials(){
        String polynomials = "";

        for(int i = 0; i < a.length; i++){
            polynomials += "S(" + i + ") " + "= " + (float)a[i] + "+" + (float)b[i] + "(x - " + x[i] + ")"
                    + "+" + (float)c[i] + "(x - " + x[i] + ")^2" + "+" + (float)d[i] + "(x - " + x[i] + ")^3; "
                    + "[" + x[i] + "," + x[i+1] + "]" + "\n";
        }
        polynomials += "Ітерації: " + iterations + '\n';
        return polynomials;
    }

}
