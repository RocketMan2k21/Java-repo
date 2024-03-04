import java.io.Serializable;
import java.util.*;

public class DenseIndex implements Serializable {

    public final static int blockSize = 200;
    private int key;
    private int referenceToRecord;

    public DenseIndex(int key, int referenceToRecord) {
        this.key = key;
        this.referenceToRecord = referenceToRecord;

    }

    public int getKey() {
        return key;
    }

    public int getReferenceToRecord() {
        return referenceToRecord;
    }

    public static int getBlockKey(int key){
        return key % blockSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DenseIndex that = (DenseIndex) o;
        return key == that.key;
    }

    public static DenseIndex getDenseIndexFromList(List<DenseIndex> indexes, int key){
        sortIndexes(indexes);
        DenseIndex[] array = indexes.toArray(new DenseIndex[0]);
        int arrayLength = array.length;
        int denseIndex_index = performBinarySearch(arrayLength, array, key);
        // Create the lookup table
        if(denseIndex_index > -1 && indexes.get(denseIndex_index).key == key)
            return indexes.get(denseIndex_index);
        throw new RuntimeException("No such index");

    }

    private static int performBinarySearch(int arrayLength, DenseIndex[] denseIndexes, int keyToSearch) {
        UniformBinarySearch.create_table(arrayLength);
        return UniformBinarySearch.binary(denseIndexes, keyToSearch);
    }

    private static void sortIndexes(List<DenseIndex> indexes){
        indexes.sort(Comparator.nullsLast(Comparator.comparingInt(DenseIndex::getKey)));
    }


}


