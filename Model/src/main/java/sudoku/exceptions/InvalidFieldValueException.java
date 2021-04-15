package sudoku.exceptions;

public class InvalidFieldValueException extends Exception {
    public InvalidFieldValueException() {
        super();
    }

    public InvalidFieldValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
