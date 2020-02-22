package com.company;

import java.util.HashMap;

public class RandomSudokuGenerator {

    private int[][] randomSudoku;

    public RandomSudokuGenerator() {
        randomSudoku = new int[9][9];

        putRandomDigitIntoRandomPlace();


        SudokuSolver sudokuSolver = new SudokuSolver(randomSudoku);
        sudokuSolver.solveSudoku();
        randomSudoku = sudokuSolver.getSolvedSudoku();

        removeDigits(56);

//        System.out.println("Ready sudoku");
//        display();
    }

    public void putRandomDigitIntoRandomPlace(){

        int randomRow = (int) (Math.random() * 9);
        int randomColumn = (int) (Math.random() * 9);
        int randomDigit = (int) (Math.random() * 9) + 1;

        randomSudoku[randomRow][randomColumn] = randomDigit;
    }

    public void removeDigits(int numberOfDigitsToRemove){

        int counter = 0;

        while(counter < numberOfDigitsToRemove){
            int randomRow = (int) (Math.random() * 9);
            int randomCol = (int) (Math.random() * 9);

            if (randomSudoku[randomRow][randomCol] == 0){
                continue;
            }

            randomSudoku[randomRow][randomCol] = 0;
            counter++;
//            System.out.println(counter);
        }

    }

    public int[][] getRandomSudoku() {
        return randomSudoku;
    }

    public void display() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(" " + randomSudoku[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
