import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class BlockWriter {

    // Зберегти індекс у бінарний файл
    public static void saveIndex(DenseIndex index, String filePath){
        try{
            FileOutputStream outputStream = new FileOutputStream(filePath, true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream );
            // Write the index object to the file
            objectOutputStream.writeObject(index);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveListOfIndexes(List<DenseIndex> denseIndexList, String filename){
        try {
            clearIndexes(filename);
            createIndexFile(filename);
            FileOutputStream fileOutputStream = new FileOutputStream(filename, true);
            for(DenseIndex index: denseIndexList) {
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(index);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearIndexes(String filename) throws  IOException{
        Files.delete(Path.of(filename));
    }

    public static void createIndexFile(String filename) throws IOException{
        Files.createFile(Path.of(filename));
    }



}