// Created by Deek
// Creation date 1/2/2022

package Service.Solver;

import Utility.CombinationHelper;
import Utility.MogelijkheidFrequentie;
import model.Cell;
import model.Set;
import model.SetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HiddenSetService implements ISolverService {

    private final Logger logger = LoggerFactory.getLogger(HiddenSetService.class);

    public HiddenSetService() {
        super();
        logger.info("New HiddenSetService");
    }

    @Override
    public List<Set> getSets(List<Cell> cells, int setGrootte) {
        List<Set> sets = new ArrayList<>();
        //per mogelijkheid(=indexWaarde) een list van Cells die deze mogelijkehid hebben
        List<Cell>[] mogelijkheidFrequentie = MogelijkheidFrequentie.vulmogelijkheidFrequentieArray(cells);
        //maak indexSets(groottte set == setGrootte) van de mogelijkheden
        //array.length == 10 omdat nul geen mogelijkheid is. Dus bij alle combis 1 bij de index optellen
        List<java.util.Set<Integer>> combis = CombinationHelper.genereerAlleIndexCombis(mogelijkheidFrequentie.length - 1, setGrootte);
        combis.forEach(l -> l.stream().map(i -> i = i + 1));
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
                sets.add(new Set(totaalCellenInCombi, waardeCombi, SetType.HIDDEN_SET, cells));
            }
        }
        return sets;
    }

    @Override
    public SetType getType() {
        return SetType.HIDDEN_SET;
    }
}