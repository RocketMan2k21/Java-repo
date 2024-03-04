

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MergeFileSorter implements FileSorter {

    // number of elements
    private final int chunkSize;

    private final FileSplitter fileSplitter = new FileSplitter();



    public MergeFileSorter(int chunkSize) {
        this.chunkSize = chunkSize % Integer.BYTES == 0 ? chunkSize
                : chunkSize - (chunkSize % Integer.BYTES);
    }

    @Override
    public void sort(String inputPath, String outPath) {
        try {

            fileSplitter.readChunks(inputPath, chunkSize);

            mergeAndSortMergedIterator(inputPath, outPath, chunkSize);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void mergeAndSortMergedIterator(String inputPath, String outPath, int chunkSize) throws IOException {
        Path chunkFolder = fileSplitter.getChunkFolder(inputPath);

        List<Path> chunks = Files.walk(chunkFolder).filter(path -> !Files.isDirectory(path)).toList();

        Iterator<Integer> treeSetMergedIterator = new MergedIterator(chunks);

        Path path = Path.of(outPath);
        Files.write(path, new byte[0]);

        List<Integer> integers = new ArrayList<>();
        try {
            while (treeSetMergedIterator.hasNext()) {
                Integer value = treeSetMergedIterator.next();
                integers.add(value);

                if (integers.size() >= chunkSize / Integer.BYTES) {
                    Files.write(path, convertIntegersToByteArray(integers), StandardOpenOption.APPEND);
                    integers.clear();
                }
            }
            if (!integers.isEmpty()) {
                Files.write(path, convertIntegersToByteArray(integers), StandardOpenOption.APPEND);
            }

        }catch (IOException e){
            throw new RuntimeException();
        }finally {
            deleteChunks(chunks);
        }
    }

    public static byte[] convertIntegersToByteArray(List<Integer> integers) {
        ByteBuffer buffer = ByteBuffer.allocate(integers.size()*Integer.BYTES);
        for (Integer integer : integers) {
            buffer.putInt(integer);
        }
        return buffer.array();
    }



    public void deleteChunks(List<Path> files) throws IOException {
        for(Path file: files){
            Files.delete(file);
        }
    }





}