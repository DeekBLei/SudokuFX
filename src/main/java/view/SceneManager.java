package view;

import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.SudokuOpgave;
import model.SudokuVeld;


import java.io.File;
import java.io.IOException;

public class SceneManager {

    private Stage primaryStage;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Laadt een scene
    public FXMLLoader getScene(String fxml) {
        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            scene = new Scene(root);
            primaryStage.setScene(scene);
            return loader;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }


    public void showLoginScene() {

        FXMLLoader loader = getScene("/view/fxml/login.fxml");
        LoginController loginController = loader.getController();
        loginController.setup();
//        LoginController controller = loader.getController();
//        controller.setup();
    }


    public void showSudokuSolver(SudokuOpgave sudokuOpgave) {
        FXMLLoader loader = getScene("/view/fxml/SudokuSolverScherm.fxml");
        SudokuSolverController controller = loader.getController();
        primaryStage.setFullScreen(true);

        controller.setup(sudokuOpgave);
    }


}
