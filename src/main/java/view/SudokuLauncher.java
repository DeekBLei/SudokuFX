package view;

import Service.Solver.BlockService;
import Service.Solver.HiddenSetService;
import Service.Solver.NakedSetService;
import Service.Solver.SolverSelectorService;
import database.mysql.DBAccess;
import javafx.application.Application;
import javafx.stage.Stage;
import model.SudokuVeld;

public class SudokuLauncher extends Application {

    private static SceneManager sceneManager = null;
    private static DBAccess dBaccess = null;
    private static Stage primaryStage = null;
    private static SolverSelectorService solverSelectorService = null;
    private static SudokuVeld sudokuVeld;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SudokuLauncher.primaryStage = primaryStage;
        primaryStage.setTitle("SudokuSolver");
        getSceneManager().showLoginScene();
        primaryStage.show();
        sudokuVeld = new SudokuVeld();

    }
    public static DBAccess getDBAcces(){
        if(dBaccess==null){
            dBaccess = new DBAccess("sudokusolver", "deek", "D33k3r@6686");
        }
       return dBaccess;
    }

    public static void setSudokuVeld(SudokuVeld sudokuVeld) {
        SudokuLauncher.sudokuVeld = sudokuVeld;
    }

    public static SudokuVeld getSudokuVeld() {
        return sudokuVeld;
    }

    public static SolverSelectorService getSolverSelectorService() {
        if (solverSelectorService == null) {
            solverSelectorService = new SolverSelectorService();
        }
        solverSelectorService.addSolverService(new NakedSetService());
        solverSelectorService.addSolverService(new BlockService());
        solverSelectorService.addSolverService(new HiddenSetService());

        return solverSelectorService;
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