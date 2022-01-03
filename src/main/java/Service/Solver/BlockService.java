// Created by Deek
// Creation date 1/2/2022

package Service.Solver;

import Utility.MogelijkheidFrequentie;
import model.Cell;
import model.Set;
import model.SetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.SudokuLauncher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class BlockService implements ISolverService {

    private final Logger logger = LoggerFactory.getLogger(BlockService.class);

    public BlockService() {
        super();
        logger.info("New BlockService");
    }


    private void checkCandidateIsBlock(List<Cell> blockCandidate, int mogelijkheid, List<Set> sets, List<Cell> cells) {
        java.util.Set<Integer> rowNumbers = blockCandidate.stream().map(b -> b.row).collect(Collectors.toSet());
        java.util.Set<Integer> colNumbers = blockCandidate.stream().map(b -> b.col).collect(Collectors.toSet());
        //LijstSize gelijk aan 1 betekend dat de cellen op 1 lijn liggen.
        if (colNumbers.size() == 1) {
            List<Cell> superset = SudokuLauncher.getSudokuVeld().geefColumn(blockCandidate.get(0));
            saveSet(superset, blockCandidate, mogelijkheid, sets);
        }
        if (rowNumbers.size() == 1) {
            List<Cell> superset = SudokuLauncher.getSudokuVeld().geefRij(blockCandidate.get(0));
            saveSet(superset, blockCandidate, mogelijkheid, sets);
        }
    }

    private void saveSet(List<Cell> lineOfCells, List<Cell> blockSet, int mogelijkheid, List<Set> sets) {
        java.util.Set<Integer> mogelijkheden = new HashSet<>();
        mogelijkheden.add(mogelijkheid);
        sets.add(new Set(blockSet, mogelijkheden, SetType.BLOCK, lineOfCells));
    }

    @Override
    public List<Set> getSets(List<Cell> cells, int setGrootte) {
        List<Set> sets = new ArrayList<>();
        if (setGrootte > 1 && setGrootte < 4) {

            List<Cell>[] mogelijkheidFrequentieList = MogelijkheidFrequentie.vulmogelijkheidFrequentieArray(cells);
            for (int mogelijkheid = 1; mogelijkheid < mogelijkheidFrequentieList.length; mogelijkheid++) {
                List<Cell> blockCandidate = mogelijkheidFrequentieList[mogelijkheid];
                if (blockCandidate.size() == setGrootte) {
                    checkCandidateIsBlock(blockCandidate, mogelijkheid, sets, cells);
                }
            }
        }
        return sets;
    }

    @Override
    public SetType getType() {
        return SetType.BLOCK;
    }
}