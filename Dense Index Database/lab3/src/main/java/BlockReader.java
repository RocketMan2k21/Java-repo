import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;



public class BlockReader {

    public static List<DenseIndex> readDenseIndexListFromFile(String filename) {
        List<DenseIndex> denseIndexList = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(filename); ) {

            while (fileInputStream.available() > 0) {
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    DenseIndex denseIndex = (DenseIndex) objectInputStream.readObject();
                    denseIndexList.add(denseIndex);

                } catch (ClassNotFoundException | FileNotFoundException e) {
                    e.printStackTrace();  // Handle the exception according to your needs
                }
            }

        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception according to your needs
        }

        return denseIndexList;
    }

    public static long getFileSize(String filename) throws IOException {
        return Files.exists(Path.of(filename)) ? Files.size(Path.of(filename)) : 0;
    }

}
