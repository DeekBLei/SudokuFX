package controller;

import Service.Solver.SudokuSolver;
import model.Cell;
import model.Constantes;
import model.SudokuVeld;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SudokuSolverTest {

    @Test
    void isOplossingCorrect() {
        SudokuVeld sudokuVeld = new SudokuVeld();
        sudokuVeld.cells = new ArrayList<>();
        for (int i = 0; i < Constantes.SIZE_OF_SUDOKUFIELD; i++) {
            sudokuVeld.cells.add(new ArrayList<Cell>());
            for (int j = 0; j < Constantes.SIZE_OF_SUDOKUFIELD; j++) {
                Cell cell = new Cell();
                cell.setOplossing(i + 1);
                sudokuVeld.cells.get(i).add(cell);
            }
        }
        SudokuSolver sudokuSolver = new SudokuSolver(sudokuVeld);
        assertEquals(sudokuSolver.isOplossingCorrect(), false);
    }

    @Test
    void isOplossingCorrect2() {
        SudokuVeld sudokuVeld = new SudokuVeld();
        sudokuVeld.cells = new ArrayList<>();
        for (int i = 0; i < Constantes.SIZE_OF_SUDOKUFIELD; i++) {
            sudokuVeld.cells.add(new ArrayList<Cell>());
            for (int j = 0; j < Constantes.SIZE_OF_SUDOKUFIELD; j++) {
                Cell cell = new Cell();
                if (j < i*3) {
                    cell.setOplossing((Constantes.SIZE_OF_SUDOKUFIELD+1-i*3) + j);
                }else{
                    cell.setOplossing(j+1-i*3);
                }
                sudokuVeld.cells.get(i).add(cell);
            }
        }
        System.out.println(sudokuVeld);
        SudokuSolver sudokuSolver = new SudokuSolver(sudokuVeld);
        assertEquals(sudokuSolver.isOplossingCorrect(), true);
    }

    @Test
    void losNakedSinglesOp() {
    }

    @Test
    void verwerkOpgelosteCellen() {
    }

    @Test
    void wisWaardeInRij() {
    }

    @Test
    void wisWaardeInCol() {
    }

    @Test
    void wisWaardeInBox() {
    }

    @Test
    void wisWaardeinCollectieCells() {
    }

    @Test
    void wisWaardeinCellsBehalve() {
    }

    @Test
    void vindNakedSet() {
    }

    @Test
    void vindHiddenSet() {
    }

    @Test
    void tel1BijIndexenOp() {
    }

    @Test
    void verwerkHiddenSets() {
    }

    @Test
    void verwerkNakedSets() {
    }

    @Test
    void getHiddenSets() {
    }

    @Test
    void getNakedSets() {
    }

    @Test
    void clearHiddenSets() {
    }

    @Test
    void clearNakedSets() {
    }

    @Test
    void verwerkNakedSet() {
    }

    @Test
    void verwerkHiddenSet() {
    }

    @Test
    void printRij() {
    }

    @Test
    void printCol() {
    }

    @Test
    void printMogelijkeheden() {
    }

    @Test
    void printOplossing() {
    }

    @Test
    void checkOfMethodesRijBOxColumnKlopppen() {
    }
}