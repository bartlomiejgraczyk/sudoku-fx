package view;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;
import sudoku.exceptions.FieldOutOfBoundsException;
import sudoku.exceptions.InvalidFieldValueException;
import sudoku.gamestate.BoardType;
import sudoku.gamestate.Difficulty;
import sudoku.gamestate.GameState;
import sudoku.model.SudokuBoard;

public class GameController implements Initializable {

    @FXML
    GridPane gridPane;

    @FXML
    TextField difficulty;

    @FXML
    TextField gameName;

    @FXML
    TextField elapsedTime;

    @FXML
    TextField authors;

    private int i = 0;

    private ResourceBundle resourceBundle;

    private GameState gameState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        addCheckingToFields();
        authorsDisplay();
    }

    public void startup(Difficulty difficulty) {
        Main.logger.info("Created new game, difficulty: " + difficulty.toString());
        try {
            gameState = new GameState(difficulty);
        } catch (FieldOutOfBoundsException | InvalidFieldValueException e) {
            Main.logger.error("Error creating new GameState - invalid parameters");
            e.printStackTrace();
        }
        resetGUI(difficulty);
        timerDisplay();
    }

    private void resetGUI(Difficulty difficulty) {
        displayGame();
        resetStyle();
        this.difficulty.setText(resourceBundle.getString("DifficultyLabel").concat(difficulty.name()));
        gameName.setText(resourceBundle.getString("GameNameLabel").concat(gameState.getGameName()));
    }

    @FXML
    private void resetGame() {
        gameState.reset();
        resetGUI(gameState.getDifficulty());
    }

    GameState getGameState() {
        return gameState;
    }

    void updateGameState(SudokuBoard sudokuBoard, String name) {
        this.gameState = new GameState(sudokuBoard, name);
        i = 0;
        resetGUI(gameState.getDifficulty());
    }

    public void quitGameMode() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("textMenu", resourceBundle.getLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Scenes/main_menu.fxml"), bundle);
        Parent root = loader.load();
        Main.stage.setScene(new Scene(root));
        Main.logger.info("Game closed");
    }

    void displayGame() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    setFieldValue(j, i, String.valueOf(gameState.getBoard().getField(j, i, BoardType.USER)));
                } catch (FieldOutOfBoundsException e) {
                    Main.logger.error("Field indexes are wrong");
                    e.printStackTrace();
                }
            }
        }
    }

    private void addCheckingToFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setFieldValidator(j, i, onFieldInput);
            }
        }
    }

    public void newGame() {
        resetStyle();
        try {
            gameState = new GameState(gameState.getDifficulty(), "Sudoku");
        } catch (FieldOutOfBoundsException | InvalidFieldValueException e) {
            Main.logger.error("Error creating new GameState - invalid parameters");
            e.printStackTrace();
        }
        Main.logger.info("Created new game");
        displayGame();
    }

    private final EventHandler<KeyEvent> onFieldInput = keyEvent -> {
        String input = keyEvent.getCharacter();
        TextField field = (TextField) keyEvent.getTarget();
        if (!input.matches("[1-9]") && !input.equals("") || field.getText().length() > 1) {
            if (input.matches("[1-9]")) {
                field.setText(input);
                updateFields();
            } else {
                field.setText("");
            }
        } else {
            updateFields();
        }
    };

    public void verify() throws FieldOutOfBoundsException, InvalidFieldValueException {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!getField(j, i).getText().equals("")) {
                    gameState.getBoard().setField(j, i, Integer.parseInt(getField(j, i).getText()), BoardType.USER);
                }
                getField(j, i).setStyle("-fx-text-fill: black;");
                if (!getField(j, i).getText().equals("") && !gameState.compareFields(j, i)) {
                    getField(j, i).setStyle("-fx-text-fill: red;");
                }
            }
        }
    }

    public void solve() throws FieldOutOfBoundsException {
        SudokuBoard board = gameState.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                getField(j, i).setStyle("-fx-text-fill: black;");
                getField(j, i).setText(Integer.toString(board.getField(j, i, BoardType.ORIGINAL)));
            }
        }
        Main.logger.info("Performed solve operation");
    }

    public void resetStyle() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                getField(j, i).setStyle("-fx-text-fill: black;");
            }
        }
    }

    public void openSaveBoardMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle("textSave", resourceBundle.getLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Scenes/save_menu.fxml"), bundle);

        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            SaveController saveController = loader.getController();
            saveController.startup(this);
            stage.setScene(new Scene(root));
            stage.setTitle("Menu");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void openLoadBoardMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle("textLoad", resourceBundle.getLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Scenes/load_menu.fxml"), bundle);

        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            LoadController loadController = loader.getController();
            loadController.startup(this);
            stage.setScene(new Scene(root));
            stage.setTitle("Menu");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void updateFields() {
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {

                    String fieldValue = getField(j, i).getText();
                    int boardIntValue = gameState.getBoard().getField(j, i, BoardType.USER);
                    String boardValue = Integer.toString(boardIntValue);

                    if (!fieldValue.equals("") && !fieldValue.equals(boardValue)) {
                        int parsedValue = Integer.parseInt(fieldValue);
                        gameState.getBoard().setField(j, i, parsedValue, BoardType.USER);
                        return;
                    }
                }
            }
        } catch (FieldOutOfBoundsException | InvalidFieldValueException exception) {
            exception.printStackTrace();
        }
    }

    private TextField getField(int x, int y) {
        ObservableList<Node> subGrids = gridPane.getChildren();
        ObservableList<Node> boxFields = ((GridPane) subGrids.get(((y / 3) * 3) + (x / 3))).getChildren();
        return (TextField) (boxFields.get(((y % 3) * 3) + (x % 3)));
    }

    private void setFieldValue(int x, int y, String value) {
        getField(x, y).setText(!value.equals("0") ? value : "");
    }

    private void setFieldValidator(int x, int y, EventHandler<KeyEvent> validator) {
        getField(x, y).setOnKeyTyped(validator);
    }

    private void authorsDisplay() {
        ResourceBundle authorsList = ResourceBundle.getBundle("view.Authors", Locale.getDefault());
        authors.setText(resourceBundle.getString("AuthorsLabel").concat(
                authorsList.getObject("1") + " & " + authorsList.getObject("2")));
    }

    private void timerDisplay() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(
                        () -> elapsedTime.setText(resourceBundle.getString("TimeLabel") + i++)
                );
            }
        }, 0, 1000);
    }
}