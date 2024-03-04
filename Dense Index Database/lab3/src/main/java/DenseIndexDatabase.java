import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DenseIndexDatabase {
    private static final int MAX_KEY = 999;
    private static final int MAX_VALUE = 999;
    private String indexFileName = "index.bin";
    private String dataFileName = "data.bin";



    public DenseIndexDatabase(String indexFileName, String dataFileName) {
        this.indexFileName = indexFileName;
        this.dataFileName = dataFileName;
    }

    public DenseIndexDatabase() {
    }

    public void insertRecord(Record record) throws IOException {
        List<DenseIndex> indexList = BlockReader.readDenseIndexListFromFile(indexFileName);
        DenseIndex index = new DenseIndex(record.getKey(), getRecordNumber());
        try {
            if(!indexList.contains(index)) {
                saveIndex(index);
                saveRecord(record);
            }else
                throw new RuntimeException("already exists");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Record getRecordByKey(int key) {
        try {
            DenseIndex index = (DenseIndex.getDenseIndexFromList(getIndexesFromFile(), key));
                if (index != null) {
                    return loadRecordFromIndex(index);
                }
        } catch (IOException e) {
            throw new RuntimeException("Record with key " + key + "is not found");
        }
        return null;
    }

    public Record deleteRecordByKey(int key) throws IOException {
        Record recordToDelete = getRecordByKey(key);
        List<DenseIndex> indexList = getIndexesFromFile();
        if (recordToDelete != null) {
            DenseIndex indexByKey = DenseIndex.getDenseIndexFromList(indexList, key);
            removeRecord(indexByKey.getReferenceToRecord());
            indexList.remove(indexByKey);
            BlockWriter.saveListOfIndexes(indexList, indexFileName);
            System.out.println("Record was deleted from " + dataFileName);
            return recordToDelete;
        }
        return null;
    }

    private void removeRecord(int recordReference) {
        RecordWriter.removeRecord(recordReference, dataFileName);
    }



    public Record editRecordByKey(int key, int newValue) throws IOException {
        Record recordToEdit = getRecordByKey(key);

        if (recordToEdit != null) {
            recordToEdit.setValue(newValue);
            DenseIndex index = (DenseIndex.getDenseIndexFromList(getIndexesFromFile(), key));

            // Update the record in the data file
            RecordWriter.updateRecord(recordToEdit, index.getReferenceToRecord(), dataFileName);

            System.out.println("Record with key " + key + " edited successfully.");
            return recordToEdit;
        } else {
            throw  new RuntimeException("Record with key " + key + " not found.");
        }
    }

    private Record loadRecordFromIndex(DenseIndex index) throws IOException {
        return RecordReader.readRecord(index.getReferenceToRecord(), dataFileName);
    }

    private void saveIndex(DenseIndex index) throws IOException {
        BlockWriter.saveIndex(index, indexFileName);
    }

    private int getRecordNumber() throws IOException {
        return RecordReader.getAmountOfRecords(dataFileName) + 1;
    }

    private List<DenseIndex> getIndexesFromFile() throws IOException{
        if(!Files.exists(Path.of(indexFileName)))
            Files.createFile(Path.of(indexFileName));
        return BlockReader.readDenseIndexListFromFile(indexFileName);

    }

    private void saveRecord(Record record) {
        RecordWriter.writeRecordToFile(record, dataFileName);
    }

    public int fillDatabase(final int numberOfRecords) throws IOException {
        clearDatabase();
        Random random = new Random();
        Set<Integer> usedKeys = new HashSet<>();
        for (int i = 0; i < numberOfRecords; ++i) {
            int key;
            do {
                key = random.nextInt(MAX_KEY) + 1;
            } while (usedKeys.contains(key));

            int value = random.nextInt(MAX_VALUE) + 1;
            Record record = new Record(key, value);
            insertRecord(record);
            usedKeys.add(key);
        }

        return searchTest(15);
    }

    private int searchTest(int n) {
        int comparisons = 0;
        for(int i = 0; i < n; i++){
            int key = new Random().nextInt(MAX_KEY) + 1;
            getRecordByKey(key);
            comparisons+=UniformBinarySearch.comparisons;
        }
        return comparisons/n;
    }


    public void clearDatabase() throws IOException {
        if (Files.exists(Path.of(indexFileName)) && Files.exists(Path.of(dataFileName))) {
            Files.delete(Path.of(indexFileName));
            Files.delete(Path.of(dataFileName));
            System.out.println("Successfully deleted database files");
            Files.createFile(Path.of(indexFileName));
            Files.createFile(Path.of(dataFileName));
        } else {
            System.out.println("Unable to delete data files ):");
        }

    }
}
