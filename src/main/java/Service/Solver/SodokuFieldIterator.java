// Created by Deek
// Creation date 1/2/2022

package Service.Solver;

import model.Constantes;
import model.Set;
import model.SudokuVeld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SodokuFieldIterator {

    private final Logger logger = LoggerFactory.getLogger(SodokuFieldIterator.class);
    private SolverSelectorService solverSelectorService;
    private final SudokuVeld sudokuVeld;

    public SodokuFieldIterator(SudokuVeld sudokuVeld) {
        super();
        logger.info("New SodokuFieldIterator");
        this.sudokuVeld = sudokuVeld;
    }

    public List<Set> getSets(ISolverService solverService) {
//        ISolverService solverService = solverSelectorService.getSolverService(setType);
        List<Set> sets = new ArrayList<>();
        for (int i = 0; i < Constantes.SIZE_OF_SUDOKUFIELD; i++) {
            for (int setGrootte = 1; setGrootte < 5; setGrootte++) {
                addToList(sets, solverService.getSets(sudokuVeld.geefColumn(i), setGrootte));
                addToList(sets, solverService.getSets(sudokuVeld.geefRij(i), setGrootte));
                addToList( sets, solverService.getSets(sudokuVeld.geefBox(i), setGrootte));
            }
        }
        return sets;
    }

    private void addToList(List<Set> sets, List<Set> setsTemp) {
        if (setsTemp != null) {
            sets.addAll(setsTemp);
        }
    }

}