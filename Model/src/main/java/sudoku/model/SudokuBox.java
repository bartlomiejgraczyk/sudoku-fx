package sudoku.model;

import java.util.List;
import sudoku.exceptions.SudokuContainerException;

public class SudokuBox extends SudokuContainer {

    public SudokuBox(List<SudokuField> values) throws SudokuContainerException {
        super(values);
    }

    @Override
    protected SudokuBox clone() throws CloneNotSupportedException {
        return (SudokuBox) super.clone();
    }
}
