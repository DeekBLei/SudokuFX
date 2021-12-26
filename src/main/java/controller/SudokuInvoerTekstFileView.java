package controller;

import java.io.*;
import java.util.Scanner;

public class SudokuInvoerTekstFileView {

    public int[][] LoadText() throws IOException {
        int[][] sudokuOpgave = new int[9][9];
        Scanner scanner = new Scanner(System.in);
        String path = "Y:\\MakeItWork\\Sudoku\\sudoku4.txt";


        System.out.println("Voer het pad incl filenaam in: ");
        //path = scanner.next();
        String everything="";
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path));
            try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);

                line = br.readLine();
            }
            everything = sb.toString();
        } catch (Exception exception) {
            System.out.println("kan de file niet vinden");
        } finally {
            br.close();
        }
        }catch(Exception exception){
            System.out.println("File bestaat niet");
        }


if(!everything.isEmpty()){
        int index = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuOpgave[row][col] = Character.getNumericValue(everything.charAt(index));
                index++;
            }
        }}

        return sudokuOpgave;
    }
}
