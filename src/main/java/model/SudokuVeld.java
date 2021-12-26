package model;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuVeld {
    private static final int SUDOKU_VELDGROOTTE = 9;
    public List<List<Cell>> cells = new ArrayList<>();


    public boolean isOpgelost = false;
    public Box[] boxes = new Box[9];

    public SudokuVeld(int[][] sudokuopgave) {
        for (int cellRow = 0; cellRow < SUDOKU_VELDGROOTTE; cellRow++) {
            cells.add(new ArrayList<>());
            for (int cellCol = 0; cellCol < SUDOKU_VELDGROOTTE; cellCol++) {
                cells.get(cellRow).add(new Cell(cellRow, cellCol, sudokuopgave[cellRow][cellCol]));
            }
        }

    }

    public SudokuVeld() {

    }

    public Cell getCellByNumber(int cellNummer){
        int row = cellNummer/9;
        int col = cellNummer-(row*9);
        return cells.get(row).get(col);
    }
    public List<Cell> geefRij(Cell cell) {
        return cells.get(cell.row);
    }

    public List<Cell> geefRij(int rij) {
        return cells.get(rij);
    }

    public List<Cell> geefColumn(Cell cell) {
        List<Cell> column = new ArrayList<>();
        for (List<Cell> row : cells) {
            column.add(row.get(cell.col));
        }
        return column;
    }

    public List<Cell> geefColumn(int col) {
        List<Cell> column = new ArrayList<>();
        for (List<Cell> row : cells) {
            column.add(row.get(col));
        }
        return column;
    }

    public List<Cell> geefBox(Cell cell) {
        int boxRow = (cell.row / 3);
        int boxCol = (cell.col / 3);
        return returnBox(boxRow, boxCol);
    }

    public List<Cell> geefBox(int boxNr) {
        int boxRow = (boxNr / 3);
        int boxCol = (boxNr - (boxRow * 3));
        return returnBox(boxRow, boxCol);
    }

    public List<Cell> returnBox(int boxRow, int boxCol) {
        List<Cell> cellsInBox = new ArrayList<>();
        for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
            for (int col = boxCol * 3; col < boxCol * 3 + 3; col++) {
                cellsInBox.add(cells.get(row).get(col));
            }

        }
        return cellsInBox;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<Cell> row : cells) {
            stringBuilder.append("\n");
            for (Cell cell : row) {
                stringBuilder.append(" ");
                stringBuilder.append(cell.isOpgelost() ? cell.getOplossing() : " ");
                stringBuilder.append(" ");
            }


        }
        stringBuilder.append("\n\n");
        return stringBuilder.toString();
    }

}


