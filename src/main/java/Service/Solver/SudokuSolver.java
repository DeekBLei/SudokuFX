package Service.Solver;

import Utility.IFilter;
import model.Cell;
import model.Set;
import model.SetType;
import model.SudokuVeld;
import view.SudokuLauncher;

import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {
    private SudokuVeld sudokuVeld;
    private List<Set> hiddenSets;
    private List<Set> nakedSets;
    private List<Set> blocks;
    private SodokuFieldIterator sodokuFieldIterator;


    public SudokuSolver(SudokuVeld sudokuVeld) {
        this.sudokuVeld = sudokuVeld;
        this.hiddenSets = new ArrayList<>();
        this.nakedSets = new ArrayList<>();
        this.blocks = new ArrayList<>();
        sodokuFieldIterator = new SodokuFieldIterator(sudokuVeld);
    }

    public boolean isOplossingCorrect() {
        return checkOplossing();
    }

//    public void solveSudoku() {
//        System.out.println();
//        do {
//            verwerkOpgelosteCellen();
//            printOplossing();
//            printMogelijkeheden();
//            for (int i = 0; i < 9; i++) {
//                for (int setGrootte = 1; setGrootte < 7; setGrootte++) {
//                    solverSelectorService.getSolverService(SetType.HIDDEN_SET).getSets(sudokuVeld.geefColumn(i), setGrootte);
//                    vindHiddenSet(sudokuVeld.geefRij(i), setGrootte);
//                    vindHiddenSet(sudokuVeld.geefBox(i), setGrootte);
//                }
//            }
//            verwerkHiddenSets();
//            for (int i = 0; i < 9; i++) {
//                for (int setGrootte = 2; setGrootte < 7; setGrootte++) {
//                    getNakedSet(sudokuVeld.geefColumn(i), setGrootte);
//                    getNakedSet(sudokuVeld.geefRij(i), setGrootte);
//                    getNakedSet(sudokuVeld.geefBox(i), setGrootte);
//                }
//            }
//            verwerkNakedSets();
//        } while (!this.sudokuVeld.isOpgelost);
//        System.out.println();
//        if (checkOplossing()) {
//            System.out.println("öpgelost!");
//        } else {
//            System.out.println("Öplossing sucks");
//        }
//
//    }

    private boolean checkOplossing() {
        boolean oplossingCorrect = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 1; j < 10; j++) {
                int tempIndex = j;
                if (sudokuVeld.geefColumn(i).stream().filter(c -> c.getOplossing() == tempIndex).toArray().length > 1) {
                    oplossingCorrect = false;
                }
                if (sudokuVeld.geefRij(i).stream().filter(c -> c.getOplossing() == tempIndex).toArray().length > 1) {
                    oplossingCorrect = false;
                }
                if (sudokuVeld.geefBox(i).stream().filter(c -> c.getOplossing() == tempIndex).toArray().length > 1) {
                    oplossingCorrect = false;
                }
            }
        }
        return oplossingCorrect;
    }

    public void losNakedSinglesOp() {
        for (int cellNr = 0; cellNr < 81; cellNr++) {
            Cell cell = sudokuVeld.getCellByNumber(cellNr);
            if (cell.getAantalmogelijkheden() == 1) {
                //check welke waarde dat is
                for (int mogelijkheid = 1; mogelijkheid < 10; mogelijkheid++) {
                    if (cell.getMogelijkheden()[mogelijkheid] == 1) {
                        //ken deze waarde toe aan de oplossing
                        cell.setOplossing(mogelijkheid);
                        cell.wisMogelijkheid(mogelijkheid);
                    }
                }
            }
        }
    }

    public void verwerkOpgelosteCellen() {

        sudokuVeld.isOpgelost = true;
        for (List<Cell> rij : sudokuVeld.cells) {
            for (Cell cell : rij) {
                if (cell.isOpgelost()) {
                    wisWaardeInRij(cell);
                    wisWaardeInCol(cell);
                    wisWaardeInBox(cell);
                } else {
                    sudokuVeld.isOpgelost = false;
                }
            }
        }
    }

    public void wisWaardeInRij(Cell cell) {
        for (Cell cell2 : sudokuVeld.geefRij(cell)) {
            if (!cell2.equals(cell)) {
                cell2.wisMogelijkheid(cell.getOplossing());
            }
        }
    }

    public void wisWaardeInCol(Cell cell) {
        for (Cell cell2 : sudokuVeld.geefColumn(cell)) {
            if (!cell2.equals(cell)) {
                cell2.wisMogelijkheid(cell.getOplossing());
            }
        }
    }

    public void wisWaardeInBox(Cell cell) {
        for (Cell cell2 : sudokuVeld.geefBox(cell)) {
            if (!cell2.equals(cell)) {
                cell2.wisMogelijkheid(cell.getOplossing());
            }
        }
    }

    public void wisWaardeinCollectieCells(List<Cell> CollectieCells, List<Cell> nakedSet) {
        for (Cell cell : CollectieCells) {
            if (!nakedSet.contains(cell)) {
                for (Cell cellInNakedSet : nakedSet)
                    for (int mogelijkheid : cellInNakedSet.getMogelijkhedenWaardes()) {
                        cell.wisMogelijkheid(mogelijkheid);
                    }
            }
        }
    }

    public void wisWaardeinCellsBehalve(List<Cell> matchingSet, java.util.Set<Integer> waardesInCombi) {
        for (Cell cell : matchingSet) {
            for (int i = 1; i < 10; i++) {
                if (!waardesInCombi.contains(i)) {
                    cell.wisMogelijkheid(i);
                }
            }
        }
    }







    private void wisWaardeInCells(List<Cell> cells, int mogelijkheid, List<Cell> excludeList) {
        for (Cell cell : cells) {
            if (!excludeList.contains(cell))
                cell.wisMogelijkheid(mogelijkheid);
        }
    }







    public void verwerkHiddenSets() {
        for (Set set : hiddenSets) {
            verwerkHiddenSet(set);
        }
    }

    public void verwerkBlock(Set set) {
        set.getWaardes().stream().findAny().ifPresent(integer -> wisWaardeInCells(set.getSuperSet(), integer, set.getCellsInSet()));
    }

    public void verwerkNakedSets() {
        for (Set set : nakedSets) {
            verwerkNakedSet(set);
        }
        nakedSets.clear();
    }

    public void verwerkNakedSet(Set set) {
        wisWaardeinCollectieCells(set.getSuperSet(), set.getCellsInSet());
        for (Cell cell : set.getCellsInSet()) {
            cell.setIsDeelVanSet(false);
        }
    }

    public void verwerkHiddenSet(Set set) {
        if (set != null) {
            wisWaardeinCellsBehalve(set.getCellsInSet(), set.getWaardes());
            for (Cell cell : set.getCellsInSet()) {
                cell.setIsDeelVanSet(false);
            }
        }
    }

    public List<Set> getHiddenSets() {
        return sodokuFieldIterator.getSets(SudokuLauncher.getSolverSelectorService().getSolverService(SetType.HIDDEN_SET));
    }

    public List<Set> getBlocks() {
        return sodokuFieldIterator.getSets(SudokuLauncher.getSolverSelectorService().getSolverService(SetType.BLOCK));
    }

    private void voegSetToeAanLijst(Set set) {
        switch (set.getSetType()) {
            case HIDDEN_SET: {
                hiddenSets.add(set);
                break;
            }
            case NAKED_SET: {
                nakedSets.add(set);
                break;
            }
            case BLOCK: {
                blocks.add(set);
                break;
            }
        }
        for (Cell cell : set.getCellsInSet()) {
            cell.setIsDeelVanSet(true);
        }
    }


    public void clearHiddenSets() {
        hiddenSets.clear();
    }

    public void clearNakedSets() {
        nakedSets.clear();
    }


    public void printRij(List<Cell> rij) {
        for (Cell cell : rij) {
            System.out.print(cell.getOplossing());
        }
        System.out.println();
    }

    public void printCol(List<Cell> col) {
        for (Cell cell : col) {
            System.out.println(cell.getOplossing());
        }
    }


    public void printMogelijkeheden() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 1; k < 10; k++) {
                    System.out.print(sudokuVeld.cells.get(i).get(j).getMogelijkheden()[k]);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printOplossing() {
        System.out.println(sudokuVeld);
    }

//    public void checkOfMethodesRijBOxColumnKlopppen() {
//        printOplossing();
//        System.out.println();
//        for (int i = 0; i < 9; i++) {
//            printRij(sudokuVeld.geefRij(sudokuVeld.cells.get(i).get(0)));
//        }
//        for (int i = 0; i < 9; i++) {
//            printCol(sudokuVeld.geefColumn(sudokuVeld.cells.get(0).get(i)));
//        }
//    }


    private void voegMogelijkhedenToe(List<Integer> mogelijkhedenInCellen, List<Integer> mogelijkhedenInCell) {
        for (int mogelijkheid : mogelijkhedenInCell) {
            if (!mogelijkhedenInCellen.contains(mogelijkheid)) {
                mogelijkhedenInCellen.add(mogelijkheid);
            }
        }
    }


    private List<Integer> toList(int[] intArray) {

        List<Integer> intList = new ArrayList<>();
        for (int t : intArray) {
            intList.add(t);
        }
        return intList;
    }

    private <T> List<T> filter(IFilter<T> filter, List<T> ts) {

        List<T> filteredList = new ArrayList<>();
        for (T t : ts) {
            if (filter.filter(t)) {
                filteredList.add(t);
            }
        }
        return filteredList;
    }

    private <T> List<T> toList(T[] ts) {

        List<T> returnList = new ArrayList<>();
        for (T t : ts) {
            returnList.add(t);
        }
        return returnList;
    }


    public void vindNakedSets() {
         nakedSets= sodokuFieldIterator.getSets(SudokuLauncher.getSolverSelectorService().getSolverService(SetType.NAKED_SET));
    }
    public List<Set> getNakedSets(){
        return nakedSets;
    }

    public void vindHiddenSet() {
        hiddenSets= sodokuFieldIterator.getSets(SudokuLauncher.getSolverSelectorService().getSolverService(SetType.HIDDEN_SET));
    }
}






