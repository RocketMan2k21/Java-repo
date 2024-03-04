public class UniformBinarySearch {

     static int MAX_SIZE = 1000;

     static int comparisons;

     static int[] lookup_table = new int[MAX_SIZE];

    // lookup table
    // create the lookup table
    // for an array of length n
     static void create_table(int n) {
        // power and count variable
        int pow = 1;
        int co = 0;
        do {
            // multiply by 2
            pow <<= 1;

            // initialize the lookup table
            lookup_table[co] = (n + (pow >> 1)) / pow;
        } while (lookup_table[co++] != 0);
    }

    // binary search
     static int binary(DenseIndex[] arr, int key) {
        comparisons = 0;
         // mid point of the array
        int index = lookup_table[0] - 1;

        // count
        int co = 0;

        while (lookup_table[co] != 0) {
            comparisons++;
            // if the key is found

            comparisons++;
            if (key == arr[index].getKey())
                return index;

                // if key is less than the mid key

            else if (key < arr[index].getKey()) {
                comparisons++;
                index -= lookup_table[++co];
            }

            // if key is greater than the mid key

            else {
                comparisons++;
                index += lookup_table[++co];
            }
        }
        return index;
    }



}
