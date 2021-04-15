package sudoku.model;

import java.util.List;
import sudoku.exceptions.SudokuContainerException;

public class SudokuRow extends SudokuContainer {

    public SudokuRow(List<SudokuField> values) throws SudokuContainerException {
        super(values);
    }

    @Override
    protected SudokuRow clone() throws CloneNotSupportedException {
        return (SudokuRow) super.clone();
    }
}
