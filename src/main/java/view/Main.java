package view;

import database.mysql.DBAccess;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static SceneManager sceneManager = null;
    private static DBAccess dBaccess = null;
    private static Stage primaryStage = null;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("SudokuSolver");
        getSceneManager().showLoginScene();
        primaryStage.show();
    }



    public static SceneManager getSceneManager() {
        if (sceneManager == null) {
            sceneManager = new SceneManager(primaryStage);
        }
        return sceneManager;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }


}