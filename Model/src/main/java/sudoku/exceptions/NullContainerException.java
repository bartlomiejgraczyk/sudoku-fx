package sudoku.exceptions;

public class NullContainerException extends SudokuContainerException {
    public NullContainerException() {
        super();
    }

    public NullContainerException(String message, Throwable cause) {
        super(message, cause);
    }
}
