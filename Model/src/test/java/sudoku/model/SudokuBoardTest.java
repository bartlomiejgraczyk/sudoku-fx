package sudoku.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.consts.Consts;
import sudoku.exceptions.ContainerOutOfBoundsException;
import sudoku.exceptions.FieldOutOfBoundsException;
import sudoku.exceptions.InvalidFieldValueException;
import sudoku.exceptions.SudokuBoardException;
import sudoku.exceptions.SudokuContainerException;
import sudoku.gamestate.BoardType;
import sudoku.solver.BacktrackingSudokuSolver;

class SudokuBoardTest {

    private final SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void getFieldTest() throws FieldOutOfBoundsException {
        Assertions.assertThrows(
                FieldOutOfBoundsException.class,
                () -> sudokuBoard.getField(-1, 4, BoardType.ORIGINAL));

        Assertions.assertEquals(sudokuBoard.getField(0, 0, BoardType.ORIGINAL), 0);
    }

    @Test
    public void setFieldTest() throws FieldOutOfBoundsException, InvalidFieldValueException {
        Assertions.assertThrows(
                FieldOutOfBoundsException.class,
                () -> sudokuBoard.setField(-1, 4, 1, BoardType.ORIGINAL));

        Assertions.assertThrows(
                InvalidFieldValueException.class,
                () -> sudokuBoard.setField(1, 4, -1, BoardType.ORIGINAL));

        sudokuBoard.setField(0, 0, 1, BoardType.ORIGINAL);
        Assertions.assertEquals(sudokuBoard.getField(0, 0, BoardType.ORIGINAL), 1);
    }

    @Test
    public void getColumnTest() throws ContainerOutOfBoundsException, SudokuContainerException {
        Assertions.assertThrows(
                ContainerOutOfBoundsException.class,
                () -> sudokuBoard.getColumn(-1));

        Assertions.assertThrows(
                ContainerOutOfBoundsException.class,
                () -> sudokuBoard.getColumn(9));

        Assertions.assertNotEquals(sudokuBoard.getColumn(0).values.size(), 0);

        for (SudokuField f : sudokuBoard.getColumn(0).values) {
            Assertions.assertEquals(f.getFieldValue(), 0);
        }
    }

    @Test
    public void getRowTest() throws ContainerOutOfBoundsException, SudokuContainerException {
        Assertions.assertThrows(
                ContainerOutOfBoundsException.class,
                () -> sudokuBoard.getRow(-1));

        Assertions.assertThrows(
                ContainerOutOfBoundsException.class,
                () -> sudokuBoard.getRow(9));

        for (SudokuField f : sudokuBoard.getRow(0).values) {
            Assertions.assertEquals(f.getFieldValue(), 0);
        }
    }

    @Test
    public void getBoxTest() throws SudokuBoardException, SudokuContainerException {
        Assertions.assertThrows(
                ContainerOutOfBoundsException.class,
                () -> sudokuBoard.getBox(-1, 5));

        Assertions.assertThrows(
                ContainerOutOfBoundsException.class,
                () -> sudokuBoard.getBox(5, -1));

        Assertions.assertThrows(
                ContainerOutOfBoundsException.class,
                () -> sudokuBoard.getBox(9, 4));

        Assertions.assertThrows(
                ContainerOutOfBoundsException.class,
                () -> sudokuBoard.getBox(5, 9));

        for (SudokuField f : sudokuBoard.getBox(0, 0).values) {
            Assertions.assertEquals(f.getFieldValue(), 0);
        }
    }

    @Test
    public void equalsTest() throws FieldOutOfBoundsException, InvalidFieldValueException {
        Assertions.assertNotEquals(sudokuBoard, null);
        Assertions.assertNotEquals(sudokuBoard, new SudokuField());
        Assertions.assertEquals(sudokuBoard, sudokuBoard);

        sudokuBoard.setField(0, 0, 2, BoardType.ORIGINAL);
        SudokuBoard newSudokuBoard = sudokuBoard;
        Assertions.assertEquals(sudokuBoard, newSudokuBoard);

        newSudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        Assertions.assertNotEquals(sudokuBoard, newSudokuBoard);

        for (int i = 0; i < Consts.SIZE; i++) {
            for (int j = 0; j < Consts.SIZE; j++) {
                sudokuBoard.setField(i, j, 1, BoardType.ORIGINAL);
                newSudokuBoard.setField(i, j, 1, BoardType.ORIGINAL);
            }
        }
        Assertions.assertEquals(sudokuBoard, newSudokuBoard);
    }

    @Test
    public void hashCodeTest() throws FieldOutOfBoundsException, InvalidFieldValueException {
        SudokuBoard newSudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newSudokuBoard.setField(i, j, sudokuBoard.getField(i, j, BoardType.ORIGINAL),
                        BoardType.ORIGINAL);
            }
        }

        Assertions.assertEquals(sudokuBoard.hashCode(), newSudokuBoard.hashCode());

        newSudokuBoard.setField(0, 0, 9, BoardType.ORIGINAL);
        newSudokuBoard.setField(1, 0, 9, BoardType.ORIGINAL);
        Assertions.assertNotEquals(sudokuBoard.hashCode(), newSudokuBoard.hashCode());
    }

    @Test
    public void toStringTest() {
        Assertions.assertEquals(sudokuBoard.toString(), sudokuBoard.toString());
        Assertions.assertNotEquals(
                sudokuBoard.toString(),
                new SudokuBoard(new BacktrackingSudokuSolver()).toString()
        );
    }

    @Test
    public void cloneTest() throws FieldOutOfBoundsException, InvalidFieldValueException {
        sudokuBoard.solveGame();
        SudokuBoard clonedSudokuBoard = sudokuBoard.clone();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Assertions.assertEquals(
                        sudokuBoard.getField(i, j, BoardType.ORIGINAL),
                        clonedSudokuBoard.getField(i, j, BoardType.ORIGINAL)
                );
            }
        }

        sudokuBoard.setField(0, 0, 3, BoardType.ORIGINAL);
        clonedSudokuBoard.setField(0, 0, 4, BoardType.ORIGINAL);

        Assertions.assertNotEquals(
                sudokuBoard.getField(0, 0, BoardType.ORIGINAL),
                clonedSudokuBoard.getField(0, 0, BoardType.ORIGINAL)
        );
    }
}