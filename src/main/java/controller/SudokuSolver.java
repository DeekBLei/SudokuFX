package controller;

import model.*;
import model.Set;

import java.util.*;
import java.util.stream.Collectors;

public class SudokuSolver {
    private SudokuVeld sudokuVeld;
    private List<Set> hiddenSets;
    private List<Set> nakedSets;
    private List<Set> blocks;


    public SudokuSolver(SudokuVeld sudokuVeld) {
        this.sudokuVeld = sudokuVeld;
        this.hiddenSets = new ArrayList<>();
        this.nakedSets = new ArrayList<>();
        this.blocks = new ArrayList<>();
    }

    public boolean isOplossingCorrect() {
        return checkOplossing();
    }

    public void solveSudoku() {
        System.out.println();
        do {
            verwerkOpgelosteCellen();
            printOplossing();
            printMogelijkeheden();
            for (int i = 0; i < 9; i++) {
                for (int setGrootte = 1; setGrootte < 7; setGrootte++) {
                    vindHiddenSet(sudokuVeld.geefColumn(i), setGrootte);
                    vindHiddenSet(sudokuVeld.geefRij(i), setGrootte);
                    vindHiddenSet(sudokuVeld.geefBox(i), setGrootte);
                }
            }
            verwerkHiddenSets();
            for (int i = 0; i < 9; i++) {
                for (int setGrootte = 2; setGrootte < 7; setGrootte++) {
                    getNakedSet(sudokuVeld.geefColumn(i), setGrootte);
                    getNakedSet(sudokuVeld.geefRij(i), setGrootte);
                    getNakedSet(sudokuVeld.geefBox(i), setGrootte);
                }
            }
            verwerkNakedSets();
        } while (!this.sudokuVeld.isOpgelost);
        System.out.println();
        if (checkOplossing()) {
            System.out.println("öpgelost!");
        } else {
            System.out.println("Öplossing sucks");
        }

    }

    private boolean checkOplossing() {
        boolean oplossingCorrect = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 1; j < 10; j++) {
                int tempIndex = j;
                if (sudokuVeld.geefColumn(i).stream().filter(c -> c.getOplossing() == tempIndex).toArray().length != 1) {
                    oplossingCorrect = false;
                }
                if (sudokuVeld.geefRij(i).stream().filter(c -> c.getOplossing() == tempIndex).toArray().length != 1) {
                    oplossingCorrect = false;
                }
                if (sudokuVeld.geefBox(i).stream().filter(c -> c.getOplossing() == tempIndex).toArray().length != 1) {
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

    private void vindBlock(List<Cell> cells, int setGrootte) {
        List<Cell>[] mogelijkheidFrequentieList = vulmogelijkheidFrequentieArray(cells);
        for (int mogelijkheid = 1; mogelijkheid < mogelijkheidFrequentieList.length; mogelijkheid++) {
            List<Cell> blockCandidate = mogelijkheidFrequentieList[mogelijkheid];
            if (blockCandidate.size() == setGrootte) {
                checkCandidateIsBlock(blockCandidate, mogelijkheid);
            }
        }
    }

    private void checkCandidateIsBlock(List<Cell> blockCandidate, int mogelijkheid) {
        java.util.Set<Integer> rowNumbers = blockCandidate.stream().map(b -> b.row).collect(Collectors.toSet());
        java.util.Set<Integer> colNumbers = blockCandidate.stream().map(b -> b.col).collect(Collectors.toSet());
        //LijstSize gelijk aan 1 betekend dat de cellen op 1 lijn liggen.
        if (colNumbers.size() == 1) {
            List<Cell> lineOfCells = sudokuVeld.geefColumn(blockCandidate.get(0).col);
            saveSet(lineOfCells, blockCandidate, mogelijkheid);
        }
        if (rowNumbers.size() == 1) {
            List<Cell> lineOfCells = sudokuVeld.geefRij(blockCandidate.get(0).row);
            saveSet(lineOfCells, blockCandidate, mogelijkheid);
        }
    }

    private void saveSet(List<Cell> lineOfCells, List<Cell> blockSet, int mogelijkheid) {
        java.util.Set<Integer> mogelijkheden = new HashSet<>();
        mogelijkheden.add(mogelijkheid);
        voegSetToeAanLijst(new Set(blockSet, mogelijkheden, SetType.BLOCK, lineOfCells));
    }

    private void wisWaardeInCells(List<Cell> cells, int mogelijkheid, List<Cell> excludeList ){
        for(Cell cell : cells){
            if(!excludeList.contains(cell))
            cell.wisMogelijkheid(mogelijkheid);
        }
    }
    private void getNakedSet(List<Cell> cells, int setGrootte) {
        if (isSetSmalllerThanNumberOfUnSolvedCells(setGrootte, cells)) {
            List<List<Cell>> cellCombinaties = genereerCellCombniaties(cells, setGrootte);
            for (List<Cell> cellCombi : cellCombinaties) {
                java.util.Set<Integer> waardes = new HashSet<>();
                for (Cell cell : cellCombi) {
                    waardes.addAll(cell.getMogelijkhedenWaardes());
                    if (waardes.size() == setGrootte) {
                        voegSetToeAanLijst(new Set(cellCombi, waardes, SetType.NAKED_SET, cells));
                    }
                }
            }
        }
    }

    private boolean isSetSmalllerThanNumberOfUnSolvedCells(int setGrootte, List<Cell> cells) {
        int aantalOpgelosteCellen = cells.stream().filter(c -> c.isOpgelost()).toArray().length;
        return setGrootte < (Constantes.NUMBER_OF_FIELDS_IN_MOGELIJKHEDEN - aantalOpgelosteCellen) / 2;
    }


    private List<Cell>[] vulmogelijkheidFrequentieArray(List<Cell> cells) {
        List<Cell>[] mogelijkheidFrequentie = new ArrayList[10];
        for (int mogelijkheid = 1; mogelijkheid < 10; mogelijkheid++) {
            int tempMogelijkheid = mogelijkheid;
            List<Cell> filteredPerMogelijkheid = (cells.stream().filter(c -> c.heeftMogelijkheid(tempMogelijkheid)).collect(Collectors.toList()));
            mogelijkheidFrequentie[mogelijkheid] = filteredPerMogelijkheid;
        }
        return mogelijkheidFrequentie;
    }

    public void vindHiddenSet(List<Cell> cells, int setGrootte) {
        //per mogelijkheid(=indexWaarde) een list van Cells die deze mogelijkehid hebben
        List<Cell>[] mogelijkheidFrequentie = vulmogelijkheidFrequentieArray(cells);
        //maak indexSets(groottte set == setGrootte) van de mogelijkheden
        //array.length == 10 omdat nul geen mogelijkheid is. Dus bij alle combis 1 bij de index optellen
        List<java.util.Set<Integer>> combis = genereerAlleIndexCombis(mogelijkheidFrequentie.length - 1, setGrootte);
        tel1BijIndexenOp(combis);
        for (java.util.Set<Integer> waardeCombi : combis) {
            List<Cell> totaalCellenInCombi = new ArrayList<>();
            boolean alleSetsInCombiNotNull = true;
            for (int i : waardeCombi) {
                //Check of de frequentie groter is dan 0, zo niet is de combi niet legaal
                if (mogelijkheidFrequentie[i] != null && mogelijkheidFrequentie[i].size() > 0) {
                    for (Cell cell : mogelijkheidFrequentie[i]) {//TODO klopt nog niet!!!!!volgens mij niet bij naked single
                        // als er een cell al in een andere set zit is deze set niet legaal. Eerst de kleinere set verwerken.
                        if (!totaalCellenInCombi.contains(cell) && !cell.isOpgelost()
                                && !cell.getIsDeelVanSet()) {
                            totaalCellenInCombi.add(cell);
                        } else {
                            alleSetsInCombiNotNull = false;
                        }
                    }
                } else {
                    alleSetsInCombiNotNull = false;
                }
            }
            if (totaalCellenInCombi.size() == setGrootte && alleSetsInCombiNotNull) {
                voegSetToeAanLijst(new Set(totaalCellenInCombi, waardeCombi, SetType.HIDDEN_SET, cells));
                //   wisWaardeinCellsBehalve(totaalCellenInCombi, waardeCombi);
            }
        }
    }

    public void tel1BijIndexenOp(List<java.util.Set<Integer>> combis) {

        for (java.util.Set<Integer> combi : combis) {
            java.util.Set<Integer> allesPlus1 = new HashSet<>();
            for (Integer i : combi) {
                allesPlus1.add(++i);
            }
            combi.clear();
            combi.addAll(allesPlus1);
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
        wisWaardeinCellsBehalve(set.getCellsInSet(), set.getWaardes());
        for (Cell cell : set.getCellsInSet()) {
            cell.setIsDeelVanSet(false);
        }
    }

    public List<Set> getHiddenSets() {
        for (int i = 0; i < Constantes.SIZE_OF_SUDOKUFIELD; i++) {
            for (int setGrootte = 2; setGrootte < 5; setGrootte++) {
                vindHiddenSet(sudokuVeld.geefColumn(i), setGrootte);
                vindHiddenSet(sudokuVeld.geefRij(i), setGrootte);
                vindHiddenSet(sudokuVeld.geefBox(i), setGrootte);
            }
        }
        return hiddenSets;
    }

    public List<Set> getBlocks() {
        for (int i = 0; i < Constantes.SIZE_OF_SUDOKUFIELD; i++) {
            for (int setGrootte = 2; setGrootte < 4; setGrootte++) {
                vindBlock(sudokuVeld.geefBox(i), setGrootte);
            }
        }
        return blocks;
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

    public List<Set> getNakedSets() {
        for (int i = 0; i < Constantes.SIZE_OF_SUDOKUFIELD; i++) {
            for (int setGrootte = 2; setGrootte < 5; setGrootte++) {
                getNakedSet(sudokuVeld.geefColumn(i), setGrootte);
                getNakedSet(sudokuVeld.geefRij(i), setGrootte);
                getNakedSet(sudokuVeld.geefBox(i), setGrootte);
            }
        }
        return nakedSets;
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

    public void checkOfMethodesRijBOxColumnKlopppen() {
        printOplossing();
        System.out.println();
        for (int i = 0; i < 9; i++) {
            printRij(sudokuVeld.geefRij(sudokuVeld.cells.get(i).get(0)));
        }
        for (int i = 0; i < 9; i++) {
            printCol(sudokuVeld.geefColumn(sudokuVeld.cells.get(0).get(i)));
        }
    }

    private List<List<Cell>> genereerCellCombniaties(List<Cell> cells, int setGrootte) {
        List<java.util.Set<Integer>> combinaties = genereerAlleIndexCombis(cells.size(), setGrootte);
        List<List<Cell>> cellCombinaties = new ArrayList<>();
        for (java.util.Set<Integer> indexCombi : combinaties) {
            List<Cell> cellCombinatie = genereerCellCombinatie(indexCombi, cells);
            if (cellCombinatie != null) {
                cellCombinaties.add(cellCombinatie);
            }
        }
        return cellCombinaties;
    }

    private List<Cell> genereerCellCombinatie(java.util.Set<Integer> indexcombi, List<Cell> cells) {
        List<Cell> cellCombinatie = new ArrayList<>();
        for (int i : indexcombi) {
            if (cells.get(i).isOpgelost()) {
                cellCombinatie =null;
                break;
            }
            cellCombinatie.add(cells.get(i));
        }
        return cellCombinatie;
    }


    private static List<java.util.Set<Integer>> genereerAlleIndexCombis(int verzamelingGrootte, int setGrootte) {
        List<java.util.Set<Integer>> combinaties = new ArrayList<>();
        permuteCombis(combinaties, new ArrayList<>(), verzamelingGrootte, setGrootte);
        return combinaties;
    }

    private static void permuteCombis(List<java.util.Set<Integer>> combis, List<Integer> soFar,
                                      int verzamelingGrootte, int setGrootte) {
        if (soFar.size() == setGrootte) {
            java.util.Set<Integer> temp = new HashSet<>(soFar);
            combis.add(temp);
            soFar.clear();
        } else {
            for (int i = soFar.size() == 0 ? 0 : soFar.get(soFar.size() - 1) + 1; i < verzamelingGrootte; i++) {
                List<Integer> next = new ArrayList<>(soFar);
                next.add(i);
                permuteCombis(combis, next, verzamelingGrootte, setGrootte);
            }
        }
    }

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


}






