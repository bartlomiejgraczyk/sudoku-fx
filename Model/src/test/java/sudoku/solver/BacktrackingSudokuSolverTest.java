package sudoku.solver;

import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.FieldOutOfBoundsException;
import sudoku.exceptions.InvalidFieldValueException;
import sudoku.exceptions.SudokuBoardException;
import sudoku.exceptions.SudokuContainerException;
import sudoku.gamestate.BoardType;
import sudoku.model.SudokuBoard;

class BacktrackingSudokuSolverTest {
    private final SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private final SudokuBoard boardOne = new SudokuBoard(new BacktrackingSudokuSolver());
    private final SudokuBoard boardTwo = new SudokuBoard(new BacktrackingSudokuSolver());


    @Test
    void solveTest() throws SudokuBoardException, InvalidFieldValueException, SudokuContainerException {
        sudokuBoard.solveGame();
        HashSet<Integer> set = new HashSet<>();
        for (int k = 0; k < 2; k++) {
            boolean row = k == 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    Assertions.assertFalse(set.contains(sudokuBoard.getField(row ? j : i, row ? i : j, BoardType.ORIGINAL)));
                    set.add(sudokuBoard.getField(row ? j : i, row ? i : j, BoardType.ORIGINAL));
                }
                set.clear();
            }
        }
        for (int k = 0; k < 3; k++) {
            for (int i = (k * 3); i < 3 * (k + 1); i++) {
                for (int l = 0; l < 3; l++) {
                    for (int j = (l * 3); j < 3 * (l + 1); j++) {
                        set.add(sudokuBoard.getField(j, i, BoardType.ORIGINAL));
                    }
                }
                Assertions.assertEquals(9, set.size());
                set.clear();
            }
        }

        sudokuBoard.solveGame();
        for (int i = 0; i < 9; i++) {
            Assertions.assertTrue(sudokuBoard.getColumn(i).verify());
            Assertions.assertTrue(sudokuBoard.getRow(i).verify());
        }
        for (int i = 0; i < 6; i += 3) {
            for (int j = 0; j < 6; j += 3) {
                Assertions.assertTrue(sudokuBoard.getBox(i, j).verify());
            }
        }
    }

    @Test
    void boardsDifferencesTest() throws FieldOutOfBoundsException, InvalidFieldValueException {
        boardOne.solveGame();
        boardTwo.solveGame();
        int differences = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (boardOne.getField(j, i, BoardType.ORIGINAL) != boardTwo.getField(j, i, BoardType.ORIGINAL)) {
                    differences++;
                }
            }
        }

        Assertions.assertTrue(differences > 0);
        System.out.println("Boards have " + differences + " different cells");
    }
}