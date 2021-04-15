package sudoku.exceptions;

public class ContainerOutOfBoundsException extends SudokuBoardException {
    public ContainerOutOfBoundsException() {
        super();
    }

    public ContainerOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
