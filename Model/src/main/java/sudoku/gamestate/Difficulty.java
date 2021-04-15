package sudoku.gamestate;

public enum Difficulty {
    EASY(60),
    NORMAL(70),
    HARD(75);

    private final int value;

    Difficulty(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
