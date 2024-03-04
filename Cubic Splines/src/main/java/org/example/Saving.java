package org.example;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;

//Збереження результатів
public class Saving {
    public Saving(String data) throws FileSystemException {
        try {
            String filePath = "results.txt";//Назва файлу
            FileWriter fileWriter = new FileWriter(filePath, true);//спосіб запису: append
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(data + '\n');

            bufferedWriter.close();
        } catch (IOException e) {
            throw new FileSystemException("Сталася помилка при збереженні файлу: " + e.getMessage());
        }
    }
}
