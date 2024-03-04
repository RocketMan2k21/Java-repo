
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneratorTest {
    @Test
    @DisplayName("File was generated")
    void generateRandomFile() throws IOException {
        String filePath = "testFile.dat";
        long fileSizeBytes = 1024; // Example: 1 KB
        int min = 1;
        int max = 100;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }

    @Test
    @DisplayName("File size is 0")
    void generateZeroFile() throws IOException {
        String filePath = "testFile.dat";
        long fileSizeBytes = 0; // Example: 1 KB
        int min = 1;
        int max = 100;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }

    @Test
    @DisplayName("File size is less than one int size but greater than zero")
    void generateSmall() throws IOException {
        String filePath = "testFile.dat";
        long fileSizeBytes = 1; // Example: 1 KB
        int min = 1;
        int max = 100;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }

    @Test
    @DisplayName("File size is less than zero")
    void generateNullFile() throws IOException {
        String filePath = "testFile.dat";
        long fileSizeBytes = -1; // Example: 1 KB
        int min = 1;
        int max = 100;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }

    @Test
    @DisplayName("File size is greater than limit")
    void generateBigFile() throws IOException {
        String filePath = "testFile.dat";
        long fileSizeBytes = 999999999999999999L; // Example: 1 KB
        int min = 1;
        int max = 100;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }

    @Test
    @DisplayName("File size with remainder")
    void remainder() throws IOException {
        String filePath = "testFile.dat";
        long fileSizeBytes = 999L; // Example: 1 KB
        int min = 1;
        int max = 100;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }

    @Test
    @DisplayName("Negative numbers")
    void generatFileWithNegative() throws IOException {
        String filePath = "testFile.dat";
        long fileSizeBytes = 1024; // Example: 1 KB
        int min = -100;
        int max = 100;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }

    @Test
    @DisplayName("Only negative")
   public void onlyNegative() throws IOException {
        String filePath = "testFile.dat";
        long fileSizeBytes = 1024; // Example: 1 KB
        int min = -100;
        int max = -999;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }

    @Test
    @DisplayName("Boundary values")
    void boundary() throws IOException {
        String filePath = "testFile.dat";
        long fileSizeBytes = 1024; // Example: 1 KB
        int min = 0;
        int max = 100;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }

    @Test
    @DisplayName("Wrong file name")
    void wrongName() throws IOException {
        String filePath = "0598hij/$%%.fsdt5ee[9u";
        long fileSizeBytes = 1024; // Example: 1 KB
        int min = 0;
        int max = 100;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }

    @Test
    @DisplayName("max = min")
    void match() throws IOException {
        String filePath = "testFile.dat";
        long fileSizeBytes = 1024; // Example: 1 KB
        int min = 0;
        int max = 0;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }

    @Test
    @DisplayName("max < min")
    void max() throws IOException {
        String filePath = "testFile.dat";
        long fileSizeBytes = 1024; // Example: 1 KB
        int min = 255;
        int max = 100;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, fileSizeBytes, min, max);

        // Check if the file was created
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() >= fileSizeBytes);

        // Clean up: Delete the test file
        file.delete();
    }



}
