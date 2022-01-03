// Created by Deek
// Creation date 1/3/2022

package database.mysql;

import model.Level;
import model.SudokuVeld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.SudokuLauncher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static model.Constantes.SIZE_OF_SUDOKUFIELD;

public class SudokuVeldDAO extends AbstractDAO implements GenericDAO<SudokuVeld> {

    private final Logger logger = LoggerFactory.getLogger(SudokuVeldDAO.class);

    public SudokuVeldDAO() {
        super(SudokuLauncher.getDBAcces());

    }


    @Override
    public List<SudokuVeld> getAll() {

        String sql = "Select * from SudokuVeld;";
        List<SudokuVeld> sudokuVeldList = null;

        try {
            dbAccess.openConnection();
            setupPreparedStatement(sql);
            ResultSet result = executeSelectStatement();
            sudokuVeldList = new ArrayList<>();
            while (result.next()) {
                SudokuVeld sudokuVeld = new SudokuVeld(vertaalRegelNaarSudoku(result.getString("matrix")));
                sudokuVeld.setLevel(Level.EASY);
                sudokuVeldList.add(sudokuVeld);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbAccess.closeConnection();
        return sudokuVeldList;
    }

    @Override
    public SudokuVeld getOneById(int id) {
        return null;
    }

    @Override
    public void storeOne(SudokuVeld type) {
        String sql = "Insert into sudokuVeld (matrix, level) values(?,?);";
        try {
            dbAccess.openConnection();
            setupPreparedStatementWithKey(sql);
            preparedStatement.setString(1, type.getMatrix());
            preparedStatement.setString(2, type.getLevel()==null?"":type.getLevel().toString());
            type.setId(executeInsertStatementWithKey());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbAccess.closeConnection();

    }

    private int[][] vertaalRegelNaarSudoku(String regel) {
        int[][] sudokuOpgave = new int[SIZE_OF_SUDOKUFIELD][SIZE_OF_SUDOKUFIELD];
        int index = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuOpgave[row][col] = (Character.getNumericValue(regel.charAt(index)));
                index++;
            }
        }
        return sudokuOpgave;
    }
}