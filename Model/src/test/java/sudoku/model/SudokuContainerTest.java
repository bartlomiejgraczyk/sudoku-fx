package sudoku.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import sudoku.exceptions.ContainerOutOfBoundsException;
import sudoku.exceptions.FieldOutOfBoundsException;
import sudoku.exceptions.InvalidContainerLengthException;
import sudoku.exceptions.InvalidFieldValueException;
import sudoku.exceptions.NullContainerException;
import sudoku.exceptions.SudokuBoardException;
import sudoku.exceptions.SudokuContainerException;
import sudoku.gamestate.BoardType;
import sudoku.solver.BacktrackingSudokuSolver;

class SudokuContainerTest {
    private final SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void constructorTest() {
        Assertions.assertThrows(
                NullContainerException.class,
                () -> new SudokuColumn(null));

        Assertions.assertThrows(
                NullContainerException.class,
                () -> new SudokuColumn(Arrays.asList(new SudokuField(), null, null, null, null, null, null, null, null)));

        Assertions.assertThrows(
                InvalidContainerLengthException.class,
                () -> new SudokuColumn(Arrays.asList(new SudokuField[4])));
    }

    @Test
    public void verifyTest() throws SudokuContainerException, SudokuBoardException, InvalidFieldValueException {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        SudokuRow row = testBoard.getRow(0);
        SudokuColumn column = testBoard.getColumn(0);
        SudokuBox box = testBoard.getBox(0, 0);

        Assertions.assertFalse(row.verify());
        Assertions.assertFalse(column.verify());
        Assertions.assertFalse(box.verify());

        testBoard.solveGame();
        row = testBoard.getRow(0);
        column = testBoard.getColumn(0);
        box = testBoard.getBox(0, 0);

        Assertions.assertTrue(row.verify());
        Assertions.assertTrue(column.verify());
        Assertions.assertTrue(box.verify());
    }

    @Test
    public void equalsTest() throws SudokuContainerException, ContainerOutOfBoundsException, FieldOutOfBoundsException, InvalidFieldValueException {
        SudokuRow firstRow = testBoard.getRow(0);
        SudokuRow secondRow = testBoard.getRow(1);
        SudokuColumn column = testBoard.getColumn(0);

        Assertions.assertEquals(firstRow, firstRow);
        Assertions.assertNotEquals(firstRow, null);
        Assertions.assertNotEquals(firstRow, column);

        Assertions.assertEquals(firstRow, secondRow);

        testBoard.setField(0, 0, 2, BoardType.ORIGINAL);
        Assertions.assertNotEquals(firstRow, secondRow);
        testBoard.setField(0, 1, 2, BoardType.ORIGINAL);
        Assertions.assertEquals(firstRow, secondRow);
    }

    @Test
    public void hashCodeTest() throws FieldOutOfBoundsException, InvalidFieldValueException, SudokuContainerException, ContainerOutOfBoundsException {
        testBoard.setField(0, 1, 5, BoardType.ORIGINAL);

        Assertions.assertEquals(
                testBoard.getRow(0).hashCode(),
                testBoard.getRow(0).hashCode());

        Assertions.assertNotEquals(
                testBoard.getRow(0).hashCode(),
                testBoard.getRow(1).hashCode());
    }

    @Test
    public void toStringTest() throws SudokuContainerException, ContainerOutOfBoundsException {
        SudokuRow firstRow = testBoard.getRow(0);
        SudokuRow secondRow = testBoard.getRow(1);
        Assertions.assertEquals(firstRow.toString(), firstRow.toString());
        Assertions.assertNotEquals(firstRow.toString(), secondRow.toString());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException, SudokuContainerException, ContainerOutOfBoundsException, FieldOutOfBoundsException, InvalidFieldValueException {
        SudokuRow firstRow = testBoard.getRow(0);
        SudokuRow clonedFirstRow = firstRow.clone();

        int firstFieldValue = testBoard.getField(0, 0, BoardType.ORIGINAL);
        int secondFieldValue = testBoard.getField(1, 0, BoardType.ORIGINAL);

        testBoard.setField(0, 0, 1, BoardType.ORIGINAL);
        testBoard.setField(1, 0, 1, BoardType.ORIGINAL);

        Assertions.assertEquals(firstRow.verify(), clonedFirstRow.verify());

        testBoard.setField(0, 0, firstFieldValue, BoardType.ORIGINAL);
        testBoard.setField(1, 0, secondFieldValue, BoardType.ORIGINAL);

        Assertions.assertEquals(firstRow.verify(), clonedFirstRow.verify());
    }
}