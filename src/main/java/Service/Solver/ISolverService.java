package Service.Solver;

import model.Cell;
import model.Set;
import model.SetType;

import java.util.List;

public interface ISolverService {
    public List<Set> getSets(List<Cell> cells, int setGrootte);
    public SetType getType();
}
