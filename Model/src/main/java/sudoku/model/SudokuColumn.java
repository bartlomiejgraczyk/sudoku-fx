package sudoku.model;

import java.util.List;
import sudoku.exceptions.SudokuContainerException;

public class SudokuColumn extends SudokuContainer {
    public SudokuColumn(List<SudokuField> values) throws SudokuContainerException {
        super(values);
    }

    @Override
    protected SudokuColumn clone() throws CloneNotSupportedException {
        return (SudokuColumn) super.clone();
    }
}
