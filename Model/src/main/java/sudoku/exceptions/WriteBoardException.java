package sudoku.exceptions;

public class WriteBoardException extends SudokuBoardDaoException {
    public WriteBoardException() {
        super();
    }

    public WriteBoardException(String message, Throwable cause) {
        super(message, cause);
    }
}
