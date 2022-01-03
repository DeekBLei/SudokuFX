package database.mysql;

import model.SudokuVeld;

import java.util.List;

public interface GenericDAO<T> {
     List<SudokuVeld> getAll();
     T getOneById(int id);
     void storeOne(T type);
}
