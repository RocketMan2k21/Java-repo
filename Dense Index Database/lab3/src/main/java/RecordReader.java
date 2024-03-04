import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class RecordReader {
    public static final int RECORD_SIZE_OF = 49;
    public static Record readRecord(int recordNumber, String filename) throws IOException {
        Record record = null;
        final int recPos = (recordNumber-1) * RECORD_SIZE_OF;
        FileInputStream inputStream = new FileInputStream(filename);
        if(inputStream.skip(recPos) == recPos){
            try(ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)){
                record = (Record) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return record;
        }
        return null;
    }

    public static int getAmountOfRecords(String fileName) throws IOException {
        return Files.exists(Path.of(fileName)) ? (int)Files.size(Path.of(fileName))/RECORD_SIZE_OF : 0;
    }
}
