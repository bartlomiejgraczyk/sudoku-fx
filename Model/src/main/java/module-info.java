module Model {
    requires java.persistence;
    requires java.sql;
    requires commons.lang3;
    requires org.hibernate.orm.core;

    opens sudoku.consts;
    opens sudoku.dao;
    opens sudoku.database;
    opens sudoku.exceptions;
    opens sudoku.gamestate;
    opens sudoku.model;
    opens sudoku.solver;

    exports sudoku.consts;
    exports sudoku.dao;
    exports sudoku.database;
    exports sudoku.exceptions;
    exports sudoku.gamestate;
    exports sudoku.model;
    exports sudoku.solver;
}