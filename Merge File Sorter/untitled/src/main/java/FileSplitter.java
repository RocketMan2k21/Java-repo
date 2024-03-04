

import utils.MergeSort;
import utils.MyLogger;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class FileSplitter {
    public static final String PROJECT_FOLDER = System.getProperty("user.dir");
    public static final int SIZEOF_INT = 4;
    private final Logger logger = new MyLogger(this.getClass().getName()).getLogger();
    public void readChunks(String inputPath, final int chunkSize) throws IOException {

        int fileSize =  (int) Files.size(Path.of(inputPath));


        FileChannel channel = null;

        try {
            // Open file in read mode
            RandomAccessFile reader = new RandomAccessFile(inputPath, "r");
            channel = reader.getChannel();

            ByteBuffer buff = ByteBuffer.allocate(chunkSize);

            int fileIndex = 1;
            int remainBytes = fileSize;// remain bytes after reading n chunks with chunkSize
            while(channel.read(buff) > 0 && chunkSize*fileIndex <= fileSize ) {
                buff.flip();

                int[] record = new int[buff.capacity()/ SIZEOF_INT];

                bytesToInt(buff, record);
                MergeSort.sort(record);
                Files.write(getChunkFileName(inputPath, fileIndex), intToByteArray(record));

                logger.info(buff.capacity() +" bytes  was read");
                fileIndex++;

                buff.clear();

                remainBytes -= buff.capacity();

                buff = ByteBuffer.allocate(chunkSize);

            }

            if(remainBytes > 0){
                buff = ByteBuffer.allocate(remainBytes);
                buff.flip();
                channel.read(buff);

                int[] record = new int[buff.capacity()/ SIZEOF_INT];
                bytesToInt(buff, record);

                Files.write(getChunkFileName(inputPath, fileIndex), intToByteArray(record));

                logger.info(fileIndex + " run file was created");
                logger.info(buff.capacity() +" bytes  was read");

                buff.clear();

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (channel != null) {
                    channel.close();
                }
            } catch (IOException e) {
                // Handle exception while closing the channel (optional)
            }
        }



    }

    protected void bytesToInt(ByteBuffer buff, int[] record) {
        while(buff.hasRemaining()){
            record[buff.position()/SIZEOF_INT] = buff.getInt();
        }
    }

    protected byte[] intToByteArray(int[] integers) {

        ByteBuffer buff = ByteBuffer.allocate(integers.length*SIZEOF_INT);

        for (int integer : integers) {
            buff.putInt(integer);

        }

        return buff.array();
    }

    protected Path getChunkFileName(String inputPath, int index) throws IOException {
        return getChunkFolder(inputPath).resolve(index + ".dat");
    }

    public Path getChunkFolder(String inputPath) throws IOException {
        Path parent = Path.of(inputPath).getParent();


        Path chunkFolder;
        if (parent != null) {
            chunkFolder = parent.resolve("chunks");

        } else {
                // Handle the case where inputPath doesn't have a parent
            chunkFolder = Path.of(PROJECT_FOLDER).resolve("chunks");

        }
        if(!new File(chunkFolder.toUri()).exists())
            Files.createDirectories(chunkFolder);
        return  chunkFolder;
    }
}

