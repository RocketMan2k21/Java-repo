
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTest {
    @Test
    @DisplayName("Binary Search")
    public void binarySearch(){
        List<DenseIndex> indexList = new ArrayList<>();
        indexList.add(new DenseIndex(1, 2));
        indexList.add(null);
        indexList.add(new DenseIndex(2, 3));
        indexList.add(new DenseIndex(4, 5));
        indexList.add(null);
        indexList.add(new DenseIndex(6, 7));

        Assertions.assertEquals(4, DenseIndex.getDenseIndexFromList(indexList, 4).getKey());

    }
}
