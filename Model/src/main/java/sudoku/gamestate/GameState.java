package sudoku.gamestate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import sudoku.consts.Consts;
import sudoku.exceptions.FieldOutOfBoundsException;
import sudoku.exceptions.InvalidFieldValueException;
import sudoku.model.SudokuBoard;
import sudoku.model.SudokuField;
import sudoku.solver.BacktrackingSudokuSolver;

public class GameState {

    private final SudokuBoard board;
    private final String gameName;

    public GameState(Difficulty difficulty) throws
            FieldOutOfBoundsException, InvalidFieldValueException {
        this(difficulty, "Generic Game");
    }

    public GameState(SudokuBoard board, String gameName) {
        this.board = board;
        this.gameName = gameName;
        if (board.getInitialValues() == null) {
            prepareTable();
        }
    }

    public GameState(Difficulty difficulty, String gameName) throws
            FieldOutOfBoundsException, InvalidFieldValueException {
        board = new SudokuBoard(new BacktrackingSudokuSolver(), difficulty);
        board.solveGame();
        this.gameName = gameName;
        prepareTable();
    }

    @SuppressWarnings("unchecked")
    private void prepareTable() {
        List<SudokuField>[] userValues = new List[Consts.SIZE];
        SudokuField[] userFields;
        for (int i = 0; i < Consts.SIZE; i++) {
            userFields = new SudokuField[Consts.SIZE];
            for (int j = 0; j < Consts.SIZE; j++) {
                userFields[j] = new SudokuField();
                try {
                    userFields[j].setFieldValue(board.getField(j, i, BoardType.ORIGINAL));
                } catch (InvalidFieldValueException | FieldOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
            userValues[i] = Arrays.asList(userFields);
        }
        randomize(Arrays.asList(userValues));
    }

    private void randomize(List<List<SudokuField>> list) {
        Random random = new Random();
        int indexX = random.nextInt(9);
        int indexY = random.nextInt(9);

        for (byte i = 0; i < board.getDifficulty().getValue(); i++) {
            try {
                while (list.get(indexY).get(indexX).getFieldValue() == 0) {
                    indexX = random.nextInt(9);
                    indexY = random.nextInt(9);
                }
                list.get(indexY).get(indexX).setFieldValue(0);

            } catch (InvalidFieldValueException exception) {
                exception.printStackTrace();
            }
        }
        board.setUserBoard(list);
        generateInitialList(list);
    }

    @SuppressWarnings("unchecked")
    private void generateInitialList(List<List<SudokuField>> list) {
        int fieldCount = 81 - board.getDifficulty().getValue();
        CoordinatesWrapper<Integer>[] wrappers = new CoordinatesWrapper[fieldCount];

        int index = 0;
        int value;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                value = list.get(i).get(j).getFieldValue();
                if (value != 0) {
                    wrappers[index] = new CoordinatesWrapper<>(j, i, value);
                    index++;
                }
            }
        }
        board.setInitialValues(Arrays.asList(wrappers));
    }

    public void reset() {
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    board.setField(j, i, 0, BoardType.USER);
                }
            }
            for (CoordinatesWrapper<Integer> cw : board.getInitialValues()) {
                board.setField(cw.getCrX(), cw.getCrY(), cw.getObj(), BoardType.USER);
            }
        } catch (FieldOutOfBoundsException | InvalidFieldValueException exception) {
            exception.printStackTrace();
        }
    }

    public boolean compareFields(int x, int y) throws FieldOutOfBoundsException {
        return board.getField(x, y, BoardType.ORIGINAL) == board.getField(x, y, BoardType.USER);
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public Difficulty getDifficulty() {
        return board.getDifficulty();
    }

    public String getGameName() {
        return gameName;
    }
}
