package sudoku.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sudoku.exceptions.ReadBoardException;
import sudoku.exceptions.WriteBoardException;
import sudoku.model.SudokuBoard;


public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private final String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws ReadBoardException {
        SudokuBoard sudokuBoard;
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            sudokuBoard = (SudokuBoard) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            throw new ReadBoardException("PLACEHOLDER", exception);
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) throws WriteBoardException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
        } catch (IOException exception) {
            throw new WriteBoardException("PLACEHOLDER", exception);
        }
    }


    @Override
    public void close() {
        //There is nothing to be released in this class
    }

    //Uncommenting causes checkstyle error
    /*@Override
    public void finalize() throws Throwable {
        //Deprecated since Java 9
        //There is nothing to be released in this class
        super.finalize();
    }*/
}
