package view;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import sudoku.gamestate.Difficulty;
import view.exceptions.IOLanguageFileException;
import view.exceptions.IllegalDifficultyException;

public class MenuController implements Initializable {

    @FXML
    Button enLang;

    @FXML
    Button plLang;

    @FXML
    private ToggleGroup difficultyGroup;

    private ResourceBundle resourceBundle;

    @FXML
    private final EventHandler<MouseEvent> onLanguageSelect = actionEvent -> {
        String lang = ((Button) actionEvent.getSource()).getText();
        try {
            setLanguage(lang.toLowerCase());
        } catch (IOException e) {
            Main.logger.error("Could not find language file");
            throw new IOLanguageFileException(resourceBundle.getString("NoLangFile"), e);
        }
    };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        plLang.setOnMouseClicked(onLanguageSelect);
        enLang.setOnMouseClicked(onLanguageSelect);
    }

    public void startGame() throws IOException, IllegalDifficultyException {
        Main.logger.info("Starting new game");
        ResourceBundle bundle = ResourceBundle.getBundle("textGame", resourceBundle.getLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Scenes/game.fxml"), bundle);
        Difficulty difficulty;
        String level = ((RadioButton) difficultyGroup.getSelectedToggle()).getText();
        if (level.equals((resourceBundle.getString("EasyRadioButton")))) {
            difficulty = Difficulty.EASY;
        } else if (level.equals((resourceBundle.getString("NormalRadioButton")))) {
            difficulty = Difficulty.NORMAL;
        } else if (level.equals((resourceBundle.getString("HardRadioButton")))) {
            difficulty = Difficulty.HARD;
        } else {
            Main.logger.error("Invalid difficulty state");
            throw new IllegalDifficultyException(
                    bundle.getString("IllegalDifficulty") + difficultyGroup.getSelectedToggle().toString()
            );
        }
        Parent root = loader.load();
        GameController gameController = loader.getController();
        gameController.startup(difficulty);
        Main.stage.setScene(new Scene(root));
    }

    private void setLanguage(String language) throws IOException {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        resourceBundle = ResourceBundle.getBundle("textMenu", locale);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Scenes/main_menu.fxml"), resourceBundle);
        Parent root = loader.load();
        Main.stage.setScene(new Scene(root));
    }

    public void quitApplication() {
        Main.logger.info("Exiting application");
        Platform.exit();
        System.exit(0);
    }
}
