// Created by Deek
// Creation date 1/2/2022

package Service.Solver;

import model.SetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SolverSelectorService {

    private final Logger logger = LoggerFactory.getLogger(SolverSelectorService.class);
    private List<ISolverService> solverServiceList;

    public SolverSelectorService() {
        super();
        logger.info("New SolverSelectorService");
        solverServiceList = new ArrayList<>();
    }

    public SolverSelectorService addSolverService(ISolverService solverService) {
        solverServiceList.add(solverService);
        return this;
    }

    public ISolverService getSolverService(SetType setType) {
        return solverServiceList.stream().filter(s -> s.getType().equals(setType)).findFirst().orElse(null);
    }
}