package sudoku.exceptions;

public class SudokuBoardException extends Exception {
    public SudokuBoardException() {
        super();
    }

    public SudokuBoardException(String message, Throwable cause) {
        super(message, cause);
    }
}
