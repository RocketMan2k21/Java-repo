
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    static Logger log =  Logger.getLogger("LOGGER");



    public static void main(String[] args) throws IOException {

        String filePath = "E:\\K Way Merge Test\\random.dat";
        String resultPath = "E:\\K Way Merge Test\\result.dat";
        Scanner scanner = new Scanner(System.in);


        System.out.println("K-Way External algorithm. Type the size of file to generate (MB).");
        final long generateSize = scanner.nextLong()*1024*1024;
        System.out.println("Type chunk size(available memory for sorting) (MB)");
        final int chunkSize = scanner.nextInt()*1024*1024;

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateRandomFile(filePath, generateSize, 1, Integer.MAX_VALUE);

        Instant start = Instant.now();
        MergeFileSorter fileSorter = new MergeFileSorter(chunkSize);
        fileSorter.sort(filePath, resultPath);
        Instant end = Instant.now();

        
        log.info(filePath + " size: " + Files.size(Path.of(filePath)) + " B" );
        log.info(resultPath + " size: " + Files.size(Path.of(resultPath)) + " B" );
        log.info("Execution time: "  + (double)Duration.between(start, end).toMillis()/1000 + " sec.");



    }
}
