import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DatabaseTest {

    @Test
    @DisplayName("Searching for record")
    public void searchRecord() throws IOException {
        DenseIndexDatabase database = new DenseIndexDatabase("test_index.bin", "test_data.bin");
        database.clearDatabase();
        Record record1 = new Record(15, 15);
        Record record2 = new Record(20, 60);
        Record record3 = new Record(22, 21);
        database.insertRecord(record1);
        database.insertRecord(record2);
        database.insertRecord(record3);

        Assertions.assertEquals(60, database.getRecordByKey(20).getValue());
    }

    @Test
    @DisplayName("Deleting a record")
    public void deleteRecord() throws IOException {
        DenseIndexDatabase database = new DenseIndexDatabase("test_index.bin", "test_data.bin");
        database.clearDatabase();
        Record record1 = new Record(99, 111);
        database.insertRecord(record1);
        Assertions.assertEquals(99, database.deleteRecordByKey(99).getKey());
    }

    @Test
    @DisplayName("Editing a record")
    public void editRecord() throws IOException {
        DenseIndexDatabase database = new DenseIndexDatabase("test_index.bin", "test_data.bin");
        database.clearDatabase();
        Record record1 = new Record(111, 222);
        database.insertRecord(record1);
        Assertions.assertEquals(333, database.editRecordByKey(111, 333).getValue());
    }
}
