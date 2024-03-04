package org.example;

import javax.swing.*;
import java.awt.*;

public class RadioButtonGroup extends ButtonGroup {
    private JRadioButton linearBtn;//кнопка методу Лін.інтер.
    private JRadioButton cubicBtn;//Кнопка кубічних сплайнів

   public RadioButtonGroup(){
       linearBtn = new JRadioButton("Лінійна інтеполяція");
       linearBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
       cubicBtn = new JRadioButton("Кубічні сплайни");
       cubicBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

       //Добавляємо кнопки в групу кнопок
       add(linearBtn);
       add(cubicBtn);

   }

   public JRadioButton getLinearBtn(){
       return linearBtn;
   }

    public JRadioButton getCubicBtn(){
        return cubicBtn;
    }
}
