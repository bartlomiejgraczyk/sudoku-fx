package sudoku.dao;

import sudoku.exceptions.ReadBoardException;
import sudoku.exceptions.WriteBoardException;

public interface Dao<T> {
    T read() throws ReadBoardException;

    void write(T obj) throws WriteBoardException;
}
