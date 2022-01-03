// Created by Deek
// Creation date 1/2/2022

package Utility;

import model.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MogelijkheidFrequentie {

    private final Logger logger = LoggerFactory.getLogger(MogelijkheidFrequentie.class);

    public MogelijkheidFrequentie() {
        super();
        logger.info("New MogelijkheidFrequentie");
    }
    public static List<Cell>[] vulmogelijkheidFrequentieArray(List<Cell> cells) {
        List<Cell>[] mogelijkheidFrequentie = new ArrayList[10];
        for (int mogelijkheid = 1; mogelijkheid < 10; mogelijkheid++) {
            int tempMogelijkheid = mogelijkheid;
            List<Cell> filteredPerMogelijkheid = (cells.stream().filter(c -> c.heeftMogelijkheid(tempMogelijkheid)).collect(Collectors.toList()));
            mogelijkheidFrequentie[mogelijkheid] = filteredPerMogelijkheid;
        }
        return mogelijkheidFrequentie;
    }
}