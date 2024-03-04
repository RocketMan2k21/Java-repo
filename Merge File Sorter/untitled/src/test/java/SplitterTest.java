
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SplitterTest {
    @Test
    @DisplayName("Big file was splitted into chunks")
    public void split() throws IOException {

        String dummyFile = "dummyFile.dat";
        long fileSize = 1024L;
        int chunkSize = 256;
        int min = 0;
        int max = 1000;

        FileGenerator.generateRandomFile(dummyFile, fileSize, min, max);
        FileSplitter fileSplitter = new FileSplitter();
        fileSplitter.readChunks(dummyFile, chunkSize);

        List<Path> chunks = Files.walk(fileSplitter.getChunkFolder(dummyFile)).filter(path -> !Files.isDirectory(path)).toList();

        long sizeOfChunks = 0;
        for(Path chunk: chunks){
            sizeOfChunks += Files.size(chunk);
            Files.delete(chunk);
        }

        Assertions.assertEquals(Files.size(Path.of(dummyFile)), sizeOfChunks);

        Files.delete(Path.of(dummyFile));

    }

    @Test
    @DisplayName("Chunk bigger than generated file")
    public void splitChunkBigger() throws IOException {

        String dummyFile = "dummyFile.dat";
        long fileSize = 1024L;
        int chunkSize = 2569999;
        int min = 0;
        int max = 1000;

        FileGenerator.generateRandomFile(dummyFile, fileSize, min, max);
        FileSplitter fileSplitter = new FileSplitter();
        fileSplitter.readChunks(dummyFile, chunkSize);

        List<Path> chunks = Files.walk(fileSplitter.getChunkFolder(dummyFile)).filter(path -> !Files.isDirectory(path)).toList();

        long sizeOfChunks = 0;
        for(Path chunk: chunks){
            sizeOfChunks += Files.size(chunk);
            Files.delete(chunk);
        }

        Assertions.assertEquals(Files.size(Path.of(dummyFile)), sizeOfChunks);

        Files.delete(Path.of(dummyFile));

    }

    @Test
    @DisplayName("Chunk is below zero")
    public void splitNullChunk() throws IOException {

        String dummyFile = "dummyFile.dat";
        long fileSize = 1024L;
        int chunkSize = -123;
        int min = 0;
        int max = 1000;

        FileGenerator.generateRandomFile(dummyFile, fileSize, min, max);
        FileSplitter fileSplitter = new FileSplitter();
        fileSplitter.readChunks(dummyFile, chunkSize);

        List<Path> chunks = Files.walk(fileSplitter.getChunkFolder(dummyFile)).filter(path -> !Files.isDirectory(path)).toList();

        long sizeOfChunks = 0;
        for(Path chunk: chunks){
            sizeOfChunks += Files.size(chunk);
            Files.delete(chunk);
        }

        Assertions.assertEquals(Files.size(Path.of(dummyFile)), sizeOfChunks);

        Files.delete(Path.of(dummyFile));

    }

    @Test
    @DisplayName("Chunk is zero")
    public void splitZeroChunk() throws IOException {

        String dummyFile = "dummyFile.dat";
        long fileSize = 1024L;
        int chunkSize = 0;
        int min = 0;
        int max = 1000;

        FileGenerator.generateRandomFile(dummyFile, fileSize, min, max);
        FileSplitter fileSplitter = new FileSplitter();
        fileSplitter.readChunks(dummyFile, chunkSize);

        List<Path> chunks = Files.walk(fileSplitter.getChunkFolder(dummyFile)).filter(path -> !Files.isDirectory(path)).toList();

        long sizeOfChunks = 0;
        for(Path chunk: chunks){
            sizeOfChunks += Files.size(chunk);
            Files.delete(chunk);
        }

        Assertions.assertEquals(Files.size(Path.of(dummyFile)), sizeOfChunks);

        Files.delete(Path.of(dummyFile));

    }

    @Test
    @DisplayName("Chunk with modulus")
    public void splitModulusChunk() throws IOException {

        String dummyFile = "dummyFile.dat";
        long fileSize = 1024L;
        int chunkSize = 123;
        int min = 0;
        int max = 1000;

        FileGenerator.generateRandomFile(dummyFile, fileSize, min, max);
        FileSplitter fileSplitter = new FileSplitter();
        fileSplitter.readChunks(dummyFile, chunkSize);

        List<Path> chunks = Files.walk(fileSplitter.getChunkFolder(dummyFile)).filter(path -> !Files.isDirectory(path)).toList();

        long sizeOfChunks = 0;
        for(Path chunk: chunks){
            sizeOfChunks += Files.size(chunk);
            Files.delete(chunk);
        }

        Assertions.assertEquals(Files.size(Path.of(dummyFile)), sizeOfChunks);

        Files.delete(Path.of(dummyFile));

    }
    @Test
    @DisplayName("Chunk size is smaller than integer size, but greaten than zero")
    public void smallChunk() throws IOException {

        String dummyFile = "dummyFile.dat";
        long fileSize = 1024L;
        int chunkSize = 1;
        int min = 0;
        int max = 1000;

        FileGenerator.generateRandomFile(dummyFile, fileSize, min, max);
        FileSplitter fileSplitter = new FileSplitter();
        fileSplitter.readChunks(dummyFile, chunkSize);

        List<Path> chunks = Files.walk(fileSplitter.getChunkFolder(dummyFile)).filter(path -> !Files.isDirectory(path)).toList();

        long sizeOfChunks = 0;
        for(Path chunk: chunks){
            sizeOfChunks += Files.size(chunk);
            Files.delete(chunk);
        }

        Assertions.assertEquals(Files.size(Path.of(dummyFile)), sizeOfChunks);

        Files.delete(Path.of(dummyFile));

    }



}
