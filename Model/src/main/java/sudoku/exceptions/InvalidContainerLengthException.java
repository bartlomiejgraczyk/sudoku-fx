package sudoku.exceptions;

public class InvalidContainerLengthException extends SudokuContainerException {
    public InvalidContainerLengthException() {
        super();
    }

    public InvalidContainerLengthException(String message, Throwable cause) {
        super(message, cause);
    }
}
