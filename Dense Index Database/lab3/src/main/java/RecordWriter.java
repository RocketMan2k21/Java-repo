import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class RecordWriter {
    public static void writeRecordToFile(Record record, String filePath) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath, true))) {
            // Write the Record object to the file
            objectOutputStream.writeObject(record);
            System.out.println("Record has been written to the file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeRecord(int recordReference, String filepath) {
        clearBytesAtPosition(filepath, recordReference*RecordReader.RECORD_SIZE_OF, RecordReader.RECORD_SIZE_OF);
    }

    private static void clearBytesAtPosition(String filePath, long position, int numBytes) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw")) {
            // Seek to the specified position in the file
            randomAccessFile.seek(position);

            // Write zero bytes to clear the specified number of bytes
            for (int i = 0; i < numBytes; i++) {
                randomAccessFile.writeByte(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateRecord(Record recordToEdit, int referenceToRecord, String filename) throws IOException {
        final int recPos = (referenceToRecord-1) * RecordReader.RECORD_SIZE_OF;

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filename, "rw");
             FileChannel channel = randomAccessFile.getChannel()) {

            // Seek to the specified position in the file
            channel.position(recPos);

            // Serialize the block to a byte array
            byte[] serializedRecord;
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeObject(recordToEdit);
                serializedRecord = byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Create a ByteBuffer from the byte array
            ByteBuffer recordBuffer = ByteBuffer.wrap(serializedRecord);

            // Write the buffer to the file
            channel.write(recordBuffer);
        }
    }
}
