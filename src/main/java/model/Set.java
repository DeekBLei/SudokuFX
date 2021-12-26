package model;

import java.util.List;

public class Set {
    private List<Cell> cellsInSet;
    private int setGrootte;
    private List<Integer> waardes;
    private SetType setType;


    private List<Cell> superSet;

    public Set(List<Cell> cellsInSet, int setGrootte, List<Integer> waardes, SetType setType, List<Cell> superSet) {
        this.cellsInSet = cellsInSet;
        this.setGrootte = setGrootte;
        this.waardes = waardes;
        this.setType = setType;
        this.superSet = superSet;
    }

    public List<Cell> getCellsInSet() {
        return cellsInSet;
    }


    public int getSetGrootte() {
        return setGrootte;
    }

    public SetType getSetType() {
        return setType;
    }

    public List<Integer> getWaardes() {
        return waardes;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(setType.toString()).append(" ").append(setGrootte).append(" in ");
        for (Cell cell : cellsInSet) {
            stringBuilder.append("cell").append(cell.cellnummer).append(" (")
                    .append(cell.row).append(",").append(cell.col).append("), ");
        }

        return stringBuilder.toString();
    }

    public List<Cell> getSuperSet() {
        return superSet;
    }


}
