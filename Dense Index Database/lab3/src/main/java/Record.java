import java.io.Serializable;

public class Record implements Serializable {

    private int key;
    private int value;

    public void setKey(int key) {
        this.key = key;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Record(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Record" + '\n'
                + "key=" + key +
                ", value=" + value;
    }
}
