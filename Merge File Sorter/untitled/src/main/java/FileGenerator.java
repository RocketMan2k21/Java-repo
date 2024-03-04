


import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.SplittableRandom;
import java.util.logging.Logger;

public class FileGenerator {

    // Logger
    public static void generateRandomFile(String filePath, final long fileSizeBytes, final int min, final int max ) throws IOException{
        Logger logger = Logger.getLogger("Generator");
        logger.info("Generating a file...");
        long length = fileSizeBytes;
        long totalLength = length;
        while(length > Integer.MAX_VALUE/2)
            length /= 2;

        FileChannel channel = null;

        try (FileOutputStream fos = new FileOutputStream(filePath);
            ) {
            channel = fos.getChannel();
            while (totalLength >= Integer.BYTES) {
                int numberLength = (int) (length/Integer.BYTES);
                ByteBuffer buffer = ByteBuffer.allocate(((int) length));

                for (int i = 0; i<numberLength; i++) {
                    int randomInt = min + (int)(Math.random() * ((max - min) + min));
                    buffer.putInt(randomInt);
                }

                buffer.flip(); // Prepare for writing

                channel.write(buffer); // Write to file
                buffer.clear();

                totalLength-=length;


            }
        }catch (IOException e){
            throw new RuntimeException();
        }finally {
            channel.close();
        }

        logger.info("File is generated successfully");
    }


}
