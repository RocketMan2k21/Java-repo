package org.example;

import javax.swing.*;
import java.awt.*;
public class CalculateButton extends JButton {
    CalculateButton(){
        setText("Розрахувати"); //текст для кнопки
        setMaximumSize(new Dimension(200, 30)); //макс розмір для кнопки
        setAlignmentX(Component.LEFT_ALIGNMENT); //вирівнювання

    }
}
