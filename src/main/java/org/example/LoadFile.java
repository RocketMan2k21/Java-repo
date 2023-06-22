package org.example;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadFile {
    public ArrayList<Point> readFile(String filename) throws FileNotFoundException {
        ArrayList<Point> points = new ArrayList<>();
        File myFile = new File(filename);
        Scanner myScanner = new Scanner(myFile);
        while (myScanner.hasNextLine()){
            String[] pointS = myScanner.nextLine().split(" ");
            Point point = new Point(Double.valueOf(pointS[0]), Double.valueOf(pointS[1]));
            points.add(point);
        }
        myScanner.close();
        return points;
    }
}
