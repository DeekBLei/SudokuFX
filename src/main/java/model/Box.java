package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Box {

    public int boxNr;
    public Cell[] cellsInBox = new Cell[9];


    public Box(int boxNr, Cell[][] cells, SudokuVeld sudokuVeld) {
        this.boxNr = boxNr;
    }

    public int[] getFrequentiePerMogelijkheid() {
        int[] frequenties = new int[10];
        for (int mogelijkheid = 1; mogelijkheid < 10; mogelijkheid++) {
            for (int i = 0; i < 9; i++) {
                frequenties[mogelijkheid] += cellsInBox[i].heeftMogelijkheid(mogelijkheid)?1:0;
            }

        }
        return frequenties;
    }

    public List<Cell> getCellListMetMogelijkheid(int waarde) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (cellsInBox[i].heeftMogelijkheid(waarde)) {
                cells.add(cellsInBox[i]);
            }
        }
        return cells;
    }


















}
