package sudoku.exceptions;

public class ReadBoardException extends SudokuBoardDaoException {
    public ReadBoardException() {
        super();
    }

    public ReadBoardException(String message, Throwable cause) {
        super(message, cause);
    }
}
