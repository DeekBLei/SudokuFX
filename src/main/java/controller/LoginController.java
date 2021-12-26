package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.SudokuOpgave;
import model.SudokuVeld;
import view.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LoginController {

    public ListView<SudokuOpgave> sudokuOpgaves;

   public void pickFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
        List<SudokuOpgave> sudokuOpgaves = maakSudokuOpgaveLijst(file);
        for (SudokuOpgave sudokuOpgave : sudokuOpgaves) {
            this.sudokuOpgaves.getItems().add(sudokuOpgave);
        }

    }

    public void setup() {
        this.sudokuOpgaves.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends SudokuOpgave> observableValue, SudokuOpgave sudokuOpgave, SudokuOpgave sudokuOpgaveNew) -> {
                    Main.getSceneManager().showSudokuSolver(sudokuOpgaveNew);
                }
        );
    }


    private List<SudokuOpgave> maakSudokuOpgaveLijst(File file) {
        List<SudokuOpgave> sudokuOpgaves = new ArrayList<>();
        try {
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNext()) {
                String line = schoonLineOp(fileReader.nextLine());
                if (line.length() < 81) {
                  line = verwerkMeerdereRegels(fileReader, line);
                }
                sudokuOpgaves.add(vertaalRegelNaarSudoku(line));
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
        }
        return sudokuOpgaves;
    }

    private String verwerkMeerdereRegels(Scanner fileReader, String line){
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

    private SudokuOpgave vertaalRegelNaarSudoku(String regel) {
        SudokuOpgave sudokuOpgave = new SudokuOpgave();
        int index = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuOpgave.getVeld()[row][col] = (Character.getNumericValue(regel.charAt(index)));
                index++;
            }
        }
        return sudokuOpgave;
    }
}
