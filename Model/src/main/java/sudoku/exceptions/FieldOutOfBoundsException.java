package sudoku.exceptions;

public class FieldOutOfBoundsException extends SudokuBoardException {
    public FieldOutOfBoundsException() {
        super();
    }

    public FieldOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
