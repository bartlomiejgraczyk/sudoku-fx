package sudoku.dao;

import sudoku.model.SudokuBoard;

public class SudokuBoardDaoFactory {
    public Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public Dao<SudokuBoard> getJpaDao(Long id) {
        return new JpaSudokuBoardDao(id);
    }

    public Dao<SudokuBoard> getJpaDao(String name) {
        return new JpaSudokuBoardDao(name);
    }
}
