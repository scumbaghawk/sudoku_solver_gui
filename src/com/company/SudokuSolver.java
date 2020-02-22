package com.company;

public class SudokuSolver{

    private int[][] gridToSolve;
    // easier to understand than typing zero as empty field everytime
    private final int EMPTY = 0;
    // makes solver capable of solving sudoku bigger than 9x9 changing one final variable
    private final int SIZE = 9;

    public SudokuSolver(int[][] gridToSolve) {
        this.gridToSolve = gridToSolve;
    }

    // sudoku solving method
    public boolean solveSudoku(){

        // iterate through rows
        for (int row = 0; row < SIZE; row++){
            // iterate through coulumns
            for (int col = 0; col < SIZE; col++){
                // check if there is an empty position
                if (gridToSolve[row][col] == EMPTY){
                    // iterate through 1-9 and try to put a number
                    for (int number = 1; number <= SIZE; number++){
                        // check if number can be possibly put into empty space
                        if (isPossibleToTry(row, col, number)){
                            // if it is probably a correct number put it into grid
                            gridToSolve[row][col] = number;
                            // start recursion (try to put next number in next empty space)
                            if (solveSudoku()){
                                return true;
                            } else {
                                // put empty at the position where number was put incorrectly
                                gridToSolve[row][col] = EMPTY;
                            }
                        }
                    }
                    return false;
                }
            }
        }

        // if method comes to this point it means sudoku is solved
        //double check grid after solving
        if (!doublecheck()){
            return false;
        }

        //if it is ok return true as solved sudoku
        return true;
    }

    // check if number is already in row
    private boolean isInRow(int row, int number){
        for (int i = 0; i < SIZE; i++){
            if (gridToSolve[row][i] == number) return true;
        }
        return false;
    }

    // check if number is already in column
    private boolean isInCol(int col, int number){
        for (int i = 0; i < SIZE; i++) {
            if (gridToSolve[i][col] == number) return true;
        }
        return false;
    }

    // check if number is already in subgrid
    private boolean isInSubgrid(int row, int col, int number){
        int subgridRow = row - row % 3;
        int subgridCol = col - col % 3;

        for (int i = subgridRow; i < subgridRow + 3; i++){
            for (int j = subgridCol; j < subgridCol + 3; j++){
                if (gridToSolve[i][j] == number) return true;
            }
        }
        return false;
    }

    // combine all conditions
    private boolean isPossibleToTry(int row, int col, int number){
        return !isInRow(row, number) && !isInCol(col, number) && !isInSubgrid(row, col, number);
    }

    public int[][] getSolvedSudoku() {
        return gridToSolve;
    }

    private boolean doublecheck(){

        // check for rows
        for(int row = 0; row < SIZE; row++){
            for(int col = 0; col < 8; col++){
                for(int col2 = col + 1; col2 < SIZE; col2++){
                    // if row fails return false
                    if(gridToSolve[row][col]==gridToSolve[row][col2]) return false;
                }
            }
        }

        // check for columns
        for(int col = 0; col < SIZE; col++){
            for(int row = 0; row < 8; row++){
                for(int row2 = row + 1; row2 < SIZE; row2++){
                    // if column fails return false
                    if(gridToSolve[row][col]==gridToSolve[row2][col]) return false;
                }
            }
        }

        // check for subgrid
        for(int row = 0; row < SIZE; row += 3){
            for(int col = 0; col < SIZE; col += 3){
                // row, col is start of the 3 by 3 grid
                for(int pos = 0; pos < 8; pos++){
                    for(int pos2 = pos + 1; pos2 < SIZE; pos2++){
                        // if subgrid fails return false
                        if(gridToSolve[row + pos%3][col + pos/3] == gridToSolve[row + pos2%3][col + pos2/3]) return false;
                    }
                }
            }
        }

        // if solved sudoku passess all test then return true
        return true;
    }

    public void display() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + gridToSolve[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}