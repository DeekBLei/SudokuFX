package model;

public class SudokuOpgave {
    private int[][] veld = new int[9][9];

    public int[][] getVeld() {
        return veld;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            stringBuilder.append("\n");
            for (int col = 0; col < 9; col++) {
                stringBuilder.append(" ");
                stringBuilder.append(veld[i][col] == 0 ? " " : veld[i][col]);
                stringBuilder.append(" ");
            }


        }
        stringBuilder.append("\n\n");
        return  stringBuilder.toString();
    }
}
