package sudoku.gamestate;

import java.io.Serializable;

public class CoordinatesWrapper<T> implements Serializable {
    private final int crX;
    private final int crY;
    private final T obj;

    CoordinatesWrapper(int crX, int crY, T obj) {
        this.crX = crX;
        this.crY = crY;
        this.obj = obj;
    }

    public int getCrX() {
        return crX;
    }

    public int getCrY() {
        return crY;
    }

    public T getObj() {
        return obj;
    }
}
