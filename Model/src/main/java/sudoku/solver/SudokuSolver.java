package sudoku.solver;

import java.io.Serializable;
import sudoku.exceptions.FieldOutOfBoundsException;
import sudoku.exceptions.InvalidFieldValueException;
import sudoku.model.SudokuBoard;

public interface SudokuSolver extends Serializable {
    void solve(SudokuBoard board) throws FieldOutOfBoundsException, InvalidFieldValueException;
}
