package controller;

import model.Cell;
import model.SudokuVeld;
import java.io.IOException;
import java.util.Scanner;

//Een programma om sudoku's op te lossen
//P.J.Bleichrodt
public class SudokuDecodeLauncher {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        boolean keuzeValid = false;
        SudokuVeld sudokuVeld = new SudokuVeld();

        System.out.println("Wat wilt u doen?:");
        System.out.println("1.\tSudoku invoeren via console?");
        System.out.println("2.\tSudoku invoeren via tekstfile?");
        int keuze = 0;
        do{
        do {
            //String temp = scanner.next();
            //keuze = Integer.parseInt(temp);
            keuze = 2;
            if (keuze < 3 && keuze > 0) {
                keuzeValid = true;
            } else {
                System.out.println("Dat is geen geldige keuze");
            }
        } while (!keuzeValid);
        if (keuze == 1) {
            //
        } else if (keuze == 2) {
            SudokuInvoerTekstFileView sudokuInvoerTekstFileView = new SudokuInvoerTekstFileView();
            sudokuVeld = new SudokuVeld(sudokuInvoerTekstFileView.LoadText());
            if(sudokuVeld==null){
                keuzeValid = false;
            }
        }}while(!keuzeValid);



        //printMatrix(sudokuVeld.cells, 9);
        System.out.println();
        //SudokuSolver sudokuSolver = new SudokuSolver(sudokuVeld);
        //sudokuSolver.solveSudoku();

        System.out.println();

    }

    private static void printMatrix(Cell[][] sudokuOplossing, int matrixSize) {

        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col < matrixSize; col++) {
                System.out.print(sudokuOplossing[row][col].getOplossing());
            }
            System.out.println();
        }
    }


}
