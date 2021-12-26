package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.*;
import view.Main;

import java.util.ArrayList;
import java.util.List;


public class SudokuSolverController {
    public GridPane sudokuVeldGridPane;


    private SudokuSolver sudokuSolver;
    private SudokuVeld sudokuVeld;
    @FXML
    private AnchorPane anchorPane;
    private List<Label> superSetMarkeeringen;
    public ComboBox<Set> hiddenSetComboBox;
    public ComboBox<Set> nakedSetComboBox;
    public ComboBox<Set> blockComboBox;


    private GridPane[] gridpanesMogelijkehedenViewArray = new GridPane[Constantes.NUMBER_OF_FIELS_IN_SUDOKUFIELD];
    private Label[][] mogelijkehedenViewArray = new Label[Constantes.NUMBER_OF_FIELS_IN_SUDOKUFIELD][Constantes.NUMBER_OF_FIELS_IN_MOGELIJKHEDEN + 1];

    private Label[] oplossingenView = new Label[Constantes.NUMBER_OF_FIELS_IN_SUDOKUFIELD];

    public void setup(SudokuOpgave sudokuOpgave) {
        this.sudokuVeld = new SudokuVeld(sudokuOpgave.getVeld());
        this.sudokuSolver = new SudokuSolver(sudokuVeld);
        this.superSetMarkeeringen = new ArrayList<>();
        this.sudokuVeldGridPane.setPrefHeight((anchorPane.getHeight() - 300));
        this.sudokuVeldGridPane.setPrefWidth((anchorPane.getHeight() - 300));

        initializeSudokuVeldView();
        sudokuSolver.verwerkOpgelosteCellen();
        updateSudokuVeldView();
    }


    public void doVerwerkNakedSingles() {

        sudokuSolver.losNakedSinglesOp();
        sudokuSolver.verwerkOpgelosteCellen();
        updateSudokuVeldView();

    }


    private void updateSudokuVeldView() {
        for (int cellNr = 0; cellNr < oplossingenView.length; cellNr++) {
            Cell cell = sudokuVeld.getCellByNumber(cellNr);
            if (cell.isOpgelost()) {
                oplossingenView[cellNr].setText(String.valueOf(cell.getOplossing()));
            }
        }
        for (int cellNr = 0; cellNr < mogelijkehedenViewArray.length; cellNr++) {

            Cell cell = sudokuVeld.getCellByNumber(cellNr);
            if (!cell.isOpgelost()) {
                for (int mogelijkheid = 1; mogelijkheid < mogelijkehedenViewArray[cellNr].length; mogelijkheid++) {
                    if (!cell.heeftMogelijkheid(mogelijkheid)) {
                        mogelijkehedenViewArray[cellNr][mogelijkheid].setText("");
                    }
                }
            } else {
                gridpanesMogelijkehedenViewArray[cellNr].setVisible(false);
            }
        }
    }


    private void initializeSudokuVeldView() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Cell cell = sudokuVeld.cells.get(row).get(col);
                //vul mogelijkheden
                GridPane mogelijkhedeGridPane = new GridPane();
                int cellViewNr = col + (row * Constantes.SIZE_OF_SUDOKUFIELD);
                mogelijkhedeGridPane.setId("gridPane" + cellViewNr);
                mogelijkhedeGridPane.addColumn(3 - mogelijkhedeGridPane.getColumnCount());
                mogelijkhedeGridPane.addRow(3 - mogelijkhedeGridPane.getColumnCount());
                mogelijkhedeGridPane.setPrefSize(sudokuVeldGridPane.getWidth() / 9, sudokuVeldGridPane.getHeight() / 9);
                for (int rowM = 0; rowM < Constantes.SIZE_OF_MOGELIJKHEDENGRID; rowM++) {
                    for (int colM = 0; colM < Constantes.SIZE_OF_MOGELIJKHEDENGRID; colM++) {
                        Label mogelijkheidView = new Label();
                        mogelijkheidView.setId("mogelijkheid" + (colM + 1 + (rowM * Constantes.SIZE_OF_MOGELIJKHEDENGRID)));
                        int mogelijkheid = colM + 1 + (rowM * Constantes.SIZE_OF_MOGELIJKHEDENGRID);
                        mogelijkheidView.setText(String.valueOf(mogelijkheid));
                        mogelijkheidView.setFont(new Font(20));
                        mogelijkheidView.setPrefSize(mogelijkhedeGridPane.getWidth(), mogelijkhedeGridPane.getHeight());
                        mogelijkehedenViewArray[cellViewNr][mogelijkheid] = mogelijkheidView;
                        mogelijkhedeGridPane.add(mogelijkheidView, colM, rowM);
                        GridPane.setHalignment(mogelijkheidView, HPos.CENTER);
                        GridPane.setValignment(mogelijkheidView, VPos.CENTER);
                    }
                }
                //maak oplossingsLabel
                Label oplossingView = new Label();
                oplossingView.setId("oplossing" + cellViewNr);
                oplossingView.setFont(new Font(50));

                sudokuVeldGridPane.add(oplossingView, col, row);
                oplossingenView[cellViewNr] = oplossingView;
                GridPane.setHalignment(oplossingView, HPos.CENTER);
                GridPane.setValignment(oplossingView, VPos.CENTER);
                sudokuVeldGridPane.add(mogelijkhedeGridPane, col, row);
                gridpanesMogelijkehedenViewArray[cellViewNr] = mogelijkhedeGridPane;
                GridPane.setHalignment(mogelijkhedeGridPane, HPos.CENTER);
                GridPane.setValignment(mogelijkhedeGridPane, VPos.CENTER);
            }
        }
    }

    public void doVindHiddenSets(ActionEvent actionEvent) {
        sudokuSolver.clearHiddenSets();
        hiddenSetComboBox.getItems().clear();
        for (int i = 0; i < Constantes.SIZE_OF_SUDOKUFIELD; i++) {
            for (int setGrootte = 1; setGrootte < 5; setGrootte++) {
                sudokuSolver.vindHiddenSet(sudokuVeld.geefColumn(i), setGrootte);
                sudokuSolver.vindHiddenSet(sudokuVeld.geefRij(i), setGrootte);
                sudokuSolver.vindHiddenSet(sudokuVeld.geefBox(i), setGrootte);
            }
        }
        for (Set set : sudokuSolver.getHiddenSets()) {
            hiddenSetComboBox.getItems().add(set);
            hiddenSetComboBox.getItems().sort((hs, hsOther) -> hs.getSetGrootte() > hsOther.getSetGrootte() ? 1 : 0);
            hiddenSetComboBox.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Set>() {
                        @Override
                        public void changed(ObservableValue<? extends Set> observableValue, Set oldSet, Set newSet) {
                            markeerSet(newSet);
                        }
                    });
        }

    }

    private void markeerSet(Set set) {
        for (Label[] mogelijkheden : mogelijkehedenViewArray) {
            for (Label mogelijkheid : mogelijkheden) {
                if (mogelijkheid != null) {
                    mogelijkheid.setTextFill(Color.BLACK);
                }
            }
        }
        //selectie is nul na verwerken hiddenset
        if (set != null) {
            for (Cell cell : set.getCellsInSet()) {
                for (int mogelijkeheid : set.getWaardes()) {
                    mogelijkehedenViewArray[cell.cellnummer][mogelijkeheid].
                            setTextFill(Color.RED);
                }
            }
            markeerRandenSet(set.getSuperSet());
        }
    }

    private void markeerRandenSet(List<Cell> superset) {
        for (GridPane mogelijkheden : gridpanesMogelijkehedenViewArray) {
            mogelijkheden.setStyle("-fx-border-visibility: none;");
        }
        for (Cell cell : superset) {
            gridpanesMogelijkehedenViewArray[cell.cellnummer].setStyle("-fx-border-color: red;");
        }
    }

    public void doMarkeerHiddenSet(ActionEvent actionEvent) {
        Set set = hiddenSetComboBox.getValue();
        markeerSet(set);
    }

    public void doVerwerkHiddenSet(ActionEvent actionEvent) {
        Set set = hiddenSetComboBox.getValue();
        sudokuSolver.verwerkHiddenSet(set);
        hiddenSetComboBox.getItems().remove(set);
        updateSudokuVeldView();
    }

    public void doGaNaarLoginScherm(ActionEvent actionEvent) {
        Main.getSceneManager().showLoginScene();
    }

    public void doVindNakedSets(ActionEvent actionEvent) {
        sudokuSolver.clearNakedSets();
        nakedSetComboBox.getItems().clear();

        for (Set set : sudokuSolver.getNakedSets()) {
            nakedSetComboBox.getItems().add(set);
            nakedSetComboBox.getItems().sort((hs, hsOther) -> hs.getSetGrootte() > hsOther.getSetGrootte() ? 1 : 0);
        }
    }
public void doVerWerkBlock(){
    if (blockComboBox.getItems().size() != 0) {
        Set set = blockComboBox.getValue();
        if (set != null) {
            sudokuSolver.verwerkBlock(set);
            blockComboBox.getItems().remove(set);
            updateSudokuVeldView();
        }
    }
}
    public void doVindBlocks() {
        for (Set set : sudokuSolver.getBlocks()) {
            blockComboBox.getItems().add(set);
            blockComboBox.getItems().sort((hs, hsOther) -> hs.getSetGrootte() > hsOther.getSetGrootte() ? 1 : 0);
        }
    }

    public void doMarkeerBlock() {
        Set set = blockComboBox.getValue();
        markeerSet(set);
    }

    public void doMarkeerNakedSet(ActionEvent actionEvent) {

        Set set = nakedSetComboBox.getValue();
        markeerSet(set);
    }

    public void doVerwerkNakedSet(ActionEvent actionEvent) {
        if (nakedSetComboBox.getItems().size() != 0) {
            Set set = nakedSetComboBox.getValue();
            if (set != null) {
                sudokuSolver.verwerkNakedSet(set);
                nakedSetComboBox.getItems().remove(set);
                updateSudokuVeldView();
            }
        }
    }

    public void doSolveSudoku(ActionEvent actionEvent) {
        sudokuSolver.solveSudoku();
    }

    public void doCheckSudoku(ActionEvent actionEvent) {
        if (sudokuSolver.isOplossingCorrect()) {
            // markeerSet();
            for (Label marker : superSetMarkeeringen) {
                marker.setVisible(true);
            }
        }
    }
}
