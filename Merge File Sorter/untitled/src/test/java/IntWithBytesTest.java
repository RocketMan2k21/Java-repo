import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class IntWithBytesTest {
    @Test
    void testBytesToInt() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(1);
        buffer.putInt(2);
        buffer.flip();

        int[] record = new int[2];

        FileSplitter fileSplitter = new FileSplitter();
        fileSplitter.bytesToInt(buffer, record);

        assertArrayEquals(new int[]{1, 2}, record);
    }

    @Test
    void testIntToByteArray() {
        int[] integers = {1, 2, 3, 4};

        FileSplitter fileSplitter = new FileSplitter();
        byte[] byteArray = fileSplitter.intToByteArray(integers);

        assertEquals(16, byteArray.length);
    }

    @Test
    void testGetChunkFileName() {
        FileSplitter fileSplitter = new FileSplitter();

        try {
            Path resultPath = fileSplitter.getChunkFileName("inputPath", 2);
            assertEquals("chunks/2.dat", resultPath.toString());
        } catch (IOException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
