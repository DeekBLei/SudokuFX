// Created by Deek
// Creation date 1/2/2022

package Service.Solver;

import Utility.CombinationHelper;
import model.Cell;
import model.Constantes;
import model.Set;
import model.SetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class NakedSetService implements ISolverService {

    private final Logger logger = LoggerFactory.getLogger(NakedSetService.class);

    public NakedSetService() {
        super();
        logger.info("New NakedSetService");
    }

    @Override
    public List<Set> getSets(List<Cell> cells, int setGrootte) {
        List<Set> nakedSets = null;
        if (isSetSmalllerThanNumberOfUnSolvedCells(setGrootte, cells)) {
            List<List<Cell>> cellCombinaties = CombinationHelper.genereerCellCombniaties(cells, setGrootte);
            nakedSets = new ArrayList<>();
            for (List<Cell> cellCombi : cellCombinaties) {
                java.util.Set<Integer> waardes = new HashSet<>();
                for (Cell cell : cellCombi) {
                    waardes.addAll(cell.getMogelijkhedenWaardes());
                }
                if (waardes.size() == setGrootte) {
                    nakedSets.add(new Set(cellCombi, waardes, SetType.NAKED_SET, cells));
                }
            }
        }
        return nakedSets;
    }

    @Override
    public SetType getType() {
        return SetType.NAKED_SET;
    }

    private boolean isSetSmalllerThanNumberOfUnSolvedCells(int setGrootte, List<Cell> cells) {
        int aantalOpgelosteCellen = cells.stream().filter(c -> c.isOpgelost()).toArray().length;
        return setGrootte < (Constantes.NUMBER_OF_FIELDS_IN_MOGELIJKHEDEN - aantalOpgelosteCellen) / 2;
    }

}
