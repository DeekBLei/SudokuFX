package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cell {
    public int box;
    public int row;
    public int col;
    public int cellnummer;
    private boolean isDeelVanSet;

    private final int[] MOGELIJKHEDEN = new int[10];
    private int oplossing;
    private boolean opgelost = false;

    public boolean isOpgelost() {
        return opgelost;
    }


    public Cell(int row, int col, int oplossing) {
        this.row = row;
        this.col = col;
        this.box = (((row / 3) * 3) + col / 3);
        this.cellnummer = this.row * 9 + this.col;
        if (oplossing != 0) {
            opgelost = true;
            setOplossing(oplossing);
        } else {
            initializeMogelijkHeden();
        }
    }


    public Cell() {
    }

    public void wisMogelijkheid(int waarde) {
        this.MOGELIJKHEDEN[waarde] = 0;
    }


    public boolean getIsDeelVanSet() {
        return isDeelVanSet;
    }

    public void setIsDeelVanSet(Boolean isDeelVanSet) {
        this.isDeelVanSet = isDeelVanSet;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Cell) {
            return this.row == ((Cell) other).row && this.col == ((Cell) other).col;
        }
        return false;
    }

    public String getFootprint() {
        String footPrint = "";
        for (int i = 1; i < 10; i++) {
            footPrint += MOGELIJKHEDEN[i];
        }
        return footPrint;
    }

    public boolean compareFootPrint(Cell other) {
        return this.getFootprint().equals(other.getFootprint());
    }


    public int getAantalmogelijkheden() {
        int aantalMogelijkheden = 0;
        for (int i = 1; i < 10; i++) {
            if (MOGELIJKHEDEN[i] == 1) {
                aantalMogelijkheden++;
            }
        }
        return aantalMogelijkheden;
    }

    public void setOplossing(int oplossing) {
        if (oplossing != 0) {
            opgelost = true;
            this.oplossing = oplossing;
            wisMogelijkheden();
        }
    }

    public int getOplossing() {
        return oplossing;
    }


    public int[] getMogelijkheden() {
        return MOGELIJKHEDEN;
    }

    public void initializeMogelijkHeden() {
        for (int waarde = 1; waarde < 10; waarde++) {
            MOGELIJKHEDEN[waarde] = 1;
        }
    }

    public void wisMogelijkheden() {
        for (int i = 0; i < 10; i++) {

            MOGELIJKHEDEN[i] = 0;
        }
    }


    public boolean heeftMogelijkheid(int mogelijkheid) {
        return MOGELIJKHEDEN[mogelijkheid] == 1;
    }

    public void wisWaardenBehalve(int[] excludeWaardes) {
        List<Integer> excludeWaardeL = new ArrayList<>();
        for (int excludeWaarde : excludeWaardes) {
            excludeWaardeL.add(excludeWaarde);
        }
        for (int waarde = 1; waarde < 10; waarde++) {
            if (!excludeWaardeL.contains(waarde)) {
                this.wisMogelijkheid(waarde);
            }

        }
    }

    public List<Integer> getMogelijkhedenWaardes() {
        List<Integer> waardesinCell = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            if (heeftMogelijkheid(i)) {
                waardesinCell.add(i);
            }
        }
        return waardesinCell;
    }
}
