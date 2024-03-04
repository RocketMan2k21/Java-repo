import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergedIteratorTest {

    @Test
    void testHasNext() throws IOException {
        // Create temporary files with integer values
        List<Path> paths = Arrays.asList(
                Files.createTempFile("temp1", ".dat"),
                Files.createTempFile("temp2", ".dat")
        );

        // Write integer values to files
        Files.write(paths.get(0), new byte[]{0, 0, 0, 1});  // Integer 1
        Files.write(paths.get(1), new byte[]{0, 0, 0, 2});  // Integer 2

        MergedIterator mergedIterator = new MergedIterator(paths);

        assertTrue(mergedIterator.hasNext());
        assertNotNull(mergedIterator.next());
        assertTrue(mergedIterator.hasNext());
        assertNotNull(mergedIterator.next());
        assertFalse(mergedIterator.hasNext());

        paths.forEach(path -> {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testNext() throws IOException {
        // Create temporary files with integer values
        List<Path> paths = Arrays.asList(
                Files.createTempFile("temp1", ".dat"),
                Files.createTempFile("temp2", ".dat")
        );

        // Write integer values to files
        Files.write(paths.get(0), new byte[]{0, 0, 0, 1});  // Integer 1
        Files.write(paths.get(1), new byte[]{0, 0, 0, 2});  // Integer 2

        MergedIterator mergedIterator = new MergedIterator(paths);

        assertEquals(1, mergedIterator.next());
        assertEquals(2, mergedIterator.next());
        assertThrows(Exception.class, mergedIterator::next);

        paths.forEach(path -> {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
