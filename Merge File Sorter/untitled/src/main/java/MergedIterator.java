import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class MergedIterator implements Iterator<Integer> {
    private final List<Iterator<Integer>> iterators;

    private final Integer[] currentData;

    public MergedIterator(List<Path> paths) {
        iterators = paths.stream().map(this::streamLines).map(Stream::iterator).toList();
        currentData = iterators.stream().map(Iterator::next).toArray(Integer[]::new);
    }

    private Stream<Integer> streamLines(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            return bytesToIntList(bytes).stream();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean hasNext() {
        return iterators.stream().anyMatch(Iterator::hasNext) || HasDataAnyValue();
    }

    public boolean HasDataAnyValue(){
        for(Integer i: currentData){
            if(i != null)
                return true;
        }
        return false;
    }

    @Override
    public Integer next() {
        int minValue = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < currentData.length; i++) {
            if (currentData[i] != null && (currentData[i].compareTo(minValue) == -1)) {
                minValue = currentData[i];
                minIndex = i;
            }
        }

        Iterator<Integer> minIterator = iterators.get(minIndex);
        currentData[minIndex] = minIterator.hasNext() ? minIterator.next() : null;

        return minValue;
    }

    private List<Integer> bytesToIntList(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        List<Integer> integers = new ArrayList<>();

        while (buffer.hasRemaining()) {
            integers.add(buffer.getInt());
        }

        return integers;
    }


}