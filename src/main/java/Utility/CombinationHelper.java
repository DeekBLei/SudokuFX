// Created by Deek
// Creation date 1/2/2022

package Utility;

import model.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CombinationHelper {

    private final Logger logger = LoggerFactory.getLogger(CombinationHelper.class);

    public CombinationHelper() {
        super();
        logger.info("New CombinationHelper");
    }
   public static List<List<Cell>> genereerCellCombniaties(List<Cell> cells, int setGrootte) {
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

    private static List<Cell> genereerCellCombinatie(java.util.Set<Integer> indexcombi, List<Cell> cells) {
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


    public static List<java.util.Set<Integer>> genereerAlleIndexCombis(int verzamelingGrootte, int setGrootte) {
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
}