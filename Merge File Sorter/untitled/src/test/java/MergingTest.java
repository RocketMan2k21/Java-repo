import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MergingTest {

    @Test
    @DisplayName("Chunks merged")
    public void merge() throws IOException {

        String dummyFile = "dummyFile.dat";
        String dummyFileOut = "outFile.dat";
        long fileSize = 1021L;
        int chunkSize = 120;
        int min = -100;
        int max = 100;

        FileGenerator.generateRandomFile(dummyFile, fileSize, min, max);
        FileSplitter fileSplitter = new FileSplitter();
        fileSplitter.readChunks(dummyFile, chunkSize);

        MergeFileSorter fileSorter = new MergeFileSorter(chunkSize);
        fileSorter.sort(dummyFile, dummyFileOut);

        List<Path> chunks = Files.walk(fileSplitter.getChunkFolder(dummyFile)).filter(path -> !Files.isDirectory(path)).toList();


        for(Path chunk: chunks){
            Files.delete(chunk);
        }

        Files.delete(Path.of(dummyFile));
        Files.delete(Path.of(dummyFileOut));


    }



}
