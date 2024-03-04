package utils;

public class InputValidator {

    static final long MAX_GENERATION_SIZE = 32*1024*1024*1024;

    public static long validateChunk(long chunkSize){
        final long freeMemory = Runtime.getRuntime().freeMemory();
        if(chunkSize <= 4)
            throw new RuntimeException("Invalid chunk size, must be greater than 4 bytes");
        if(chunkSize > freeMemory)
            throw new RuntimeException("Run out of free memory, set smaller chunk");
        return chunkSize;
    }

    public static long validateGenerationSize(long size) {

        if (size < 4) {
            throw new RuntimeException("Too small value, must be greater than 4 bytes");
        }

        if (size > MAX_GENERATION_SIZE) {
            throw new RuntimeException("Too big generation size, must be no greater than 32gb");
        }
        return size;
    }






}
