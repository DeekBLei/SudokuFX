package controller;

import database.mysql.SudokuVeldDAO;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import model.Cell;
import model.SudokuVeld;
import view.SudokuLauncher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static model.Constantes.SIZE_OF_SUDOKUFIELD;


public class LoginController {

    public ListView<SudokuVeld> sudokuViews;
    private SudokuVeldDAO sudokuVeldDAO;


    public void pickFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(SudokuLauncher.getPrimaryStage());
        List<SudokuVeld> sudokuVelden = maakSudokuOpgaveLijst(file);
        for (SudokuVeld sudokuVeld : sudokuVelden){
            sudokuVeldDAO.storeOne(sudokuVeld);
        }
       setup();
    }

    public void setup() {
        this.sudokuVeldDAO = new SudokuVeldDAO();
        this.sudokuViews.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends SudokuVeld> observableValue, SudokuVeld sudokuOpgave, SudokuVeld sudokuOpgaveNew) -> {
                    SudokuLauncher.getSceneManager().showSudokuSolver(sudokuOpgaveNew);
                }
        );
        sudokuViews.getItems().clear();
        sudokuViews.getItems().addAll(sudokuVeldDAO.getAll());

//        for (SudokuVeld sudokuOpgave : sudokuVelden) {
//            this.sudokuViews.getItems().add(sudokuOpgave);
//        }
    }


    private List<SudokuVeld> maakSudokuOpgaveLijst(File file) {
        List<SudokuVeld> sudokuOpgaves = new ArrayList<>();
        try {
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNext()) {
                String line = schoonLineOp(fileReader.nextLine());
                if (line.length() < 81) {
                    line = verwerkMeerdereRegels(fileReader, line);
                }
                int[][] sudokuOpgave = vertaalRegelNaarSudoku(line);
                sudokuOpgaves.add(vertaalMatrixNaarVeld(sudokuOpgave));
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
        }
        return sudokuOpgaves;
    }

    private String verwerkMeerdereRegels(Scanner fileReader, String line) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(line);
        for (int i = 0; i < 8; i++) {
            line = schoonLineOp(fileReader.nextLine());
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    private String schoonLineOp(String line) {
        return line.replace(" ", "").replace("\n", "");
    }

    public SudokuVeld vertaalMatrixNaarVeld(int[][] sudokuopgave) {
        SudokuVeld sudokuVeld = new SudokuVeld();
        for (int cellRow = 0; cellRow < SIZE_OF_SUDOKUFIELD; cellRow++) {
            sudokuVeld.cells.add(new ArrayList<>());
            for (int cellCol = 0; cellCol < SIZE_OF_SUDOKUFIELD; cellCol++) {
                sudokuVeld.cells.get(cellRow).add(new Cell(cellRow, cellCol, sudokuopgave[cellRow][cellCol]));
            }
        }
        return sudokuVeld;
    }

    private int[][] vertaalRegelNaarSudoku(String regel) {
        int[][] sudokuOpgave = new int[SIZE_OF_SUDOKUFIELD][SIZE_OF_SUDOKUFIELD];
        int index = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuOpgave[row][col] = (Character.getNumericValue(regel.charAt(index)));
                index++;
            }
        }
        return sudokuOpgave;
    }
}
