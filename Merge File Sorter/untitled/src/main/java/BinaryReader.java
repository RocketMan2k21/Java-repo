import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class BinaryReader{
    public static void read(String filePath){

        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            int count = 0;
            while (count < 30) {
                try {
                    int number = dis.readInt();
                    System.out.println(number);
                    count++;
                } catch (IOException e) {
                    break;
                }
            }
            System.out.println(" ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
