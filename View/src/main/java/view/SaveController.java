package view;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.dao.Dao;
import sudoku.dao.SudokuBoardDaoFactory;
import sudoku.database.Board;
import sudoku.database.DatabaseManager;
import sudoku.exceptions.WriteBoardException;
import sudoku.model.SudokuBoard;

public class SaveController implements Initializable {
    private GameController gameController;

    @FXML
    private TextField name;

    @FXML
    private TextField status;

    @FXML
    private ListView<String> listView;

    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void startup(GameController gameController) {
        this.gameController = gameController;
    }

    @FXML
    private void saveBoardToFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(Main.stage);
        if (file != null) {
            Dao<SudokuBoard> fileSudokuBoardDao = new SudokuBoardDaoFactory().getFileDao(file.getAbsolutePath());
            try {
                fileSudokuBoardDao.write(gameController.getGameState().getBoard());
                status.setText(resourceBundle.getString("saved_file"));
            } catch (WriteBoardException e) {
                Main.logger.error("Could not save board to selected file");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void displayDatabaseEntries() {
        DatabaseManager databaseManager = new DatabaseManager();
        List<Board> boards = databaseManager.getAllEntries();
        databaseManager.releaseResources();
        ObservableList<String> results = FXCollections.observableArrayList();

        for (Board board : boards) {
            results.add(board.getId().toString() + " " + board.getName() + " " + board.getDifficulty());
        }
        listView.setItems(results);

        if (results.isEmpty()) {
            status.setText(resourceBundle.getString("no_results"));
        } else {
            status.setText(resourceBundle.getString("finished"));
        }
    }

    @FXML
    private void deleteAllRecords() {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.removeAll();
        databaseManager.releaseResources();
        displayDatabaseEntries();
    }

    @FXML
    private void deleteNamedRecord() {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.removeData(name.getText());
        databaseManager.releaseResources();
        displayDatabaseEntries();
    }

    @FXML
    private void saveToDatabase() {
        if (!name.getText().equals("")) {
            DatabaseManager databaseManager = new DatabaseManager();

            if (!databaseManager.checkIfNameExists(name.getText())) {
                Dao<SudokuBoard> sudokuBoardDao = new SudokuBoardDaoFactory().getJpaDao(name.getText());

                try {
                    sudokuBoardDao.write(gameController.getGameState().getBoard());
                    displayDatabaseEntries();
                    status.setText(resourceBundle.getString("saved_database"));
                } catch (WriteBoardException exception) {
                    exception.printStackTrace();
                }
            } else {
                status.setText(resourceBundle.getString("board_exists"));
            }
        }
    }

    @FXML
    private void returnToGame(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage thisStage = (Stage) source.getScene().getWindow();
        thisStage.close();
    }
}
