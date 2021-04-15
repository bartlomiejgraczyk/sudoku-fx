package sudoku.consts;

public class Consts {

    private Consts() {
    }

    public static final int UNINITIALIZED = 0;
    public static final int MAX_VALUE = 9;
    public static final int BOX_SIZE = 3;
    public static final int SIZE = 9;

    public static final String ROW_OUT_OF_BOUNDS = "Array row index out of range (0-8) - ";
    public static final String COL_OUT_OF_BOUNDS = "Array column index out of range (0-8) - ";
    public static final String INVALID_LENGTH = "Array doesn't have required number of elements";
    public static final String INVALID_VALUE = "Value can't be less than 0 and more than 9";
    public static final String OUT_OF_BOUNDS = "One of provided indexes (x or y) is invalid";
    public static final String NULL_ELEMENT = "Array element cannot be null";
    public static final String NULL_ARRAY = "Provided array is null";
}
