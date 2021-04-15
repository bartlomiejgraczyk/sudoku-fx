package view.exceptions;

public class IllegalDifficultyException extends RuntimeException {
    public IllegalDifficultyException() {
        super();
    }

    public IllegalDifficultyException(String message) {
        super(message);
    }
}
