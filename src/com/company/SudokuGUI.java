package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SudokuGUI extends JFrame implements ActionListener {

    ArrayList<JTextField> inputFields = new ArrayList<>();
    ArrayList<JTextField> outputFields = new ArrayList<>();
    JButton solve, clear, randomize;
    JLabel info;

    public SudokuGUI() throws HeadlessException {

        //global setup

        setSize(750,310);
        setTitle("Sudoku Solver");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);

        //create 81 new INPUT fields

        for (int i = 0; i < 81; i++){
            inputFields.add(new JTextField());
        }

        //setup INPUT fields

        int currentTextFieldIndex = 0;

        for (int y = 10; y <= 250; y+=30){
            for (int x = 10; x <= 250; x+=30){
                inputFields.get(currentTextFieldIndex).setBounds(x, y, 30, 30);
                inputFields.get(currentTextFieldIndex).setDocument(new JTextFieldLimit(1));
                inputFields.get(currentTextFieldIndex).addKeyListener(preventLetters(info, inputFields.get(currentTextFieldIndex)));
                inputFields.get(currentTextFieldIndex).setTransferHandler(null);
                currentTextFieldIndex++;
            }
        }

        //create 81 new OUTPUT fields

        for (int i = 0; i < 81; i++){
            outputFields.add(new JTextField());
        }

        //setup OUTPUT fields

        currentTextFieldIndex = 0;

        for (int y = 10; y <= 250; y+=30){
            for (int x = 470; x <= 720; x+=30){
                outputFields.get(currentTextFieldIndex).setBounds(x, y, 30, 30);
                outputFields.get(currentTextFieldIndex).setEditable(false);
                currentTextFieldIndex++;
            }
        }

        //create and setup buttons

        solve = new JButton("Solve");
        solve.setBounds(290, 100, 170, 50);
        solve.addActionListener(this);


        clear = new JButton("Clear");
        clear.setBounds(290, 160, 170, 50);
        clear.addActionListener(this);

        randomize = new JButton("Random");
        randomize.setBounds(290, 220, 170, 50);
        randomize.addActionListener(this);

        //create and setup info label

        info = new JLabel("Welcome to Sudoku Solver!");
        info.setBounds(290, 10, 170, 110);

        //add everything but textfields to JFrame

        add(solve);
        add(clear);
        add(randomize);
        add(info);

        //add output fields to JFrame

        for (JTextField outputField : outputFields){
            add(outputField);
        }

        //add input fields to JFrame

        for (JTextField inputField : inputFields){
            add(inputField);
        }

        //set default sudoku

        setDefaultGrid();

        //final setup (just because of logical order)

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource() == solve){

            //read given sudoku and make it ready to be solved

            int[][] sudokuGridToSolve = new int[9][9];

            int currentArrayIndex = 0;

            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    if (inputFields.get(currentArrayIndex).getText().equals("")){
                        sudokuGridToSolve[i][j] = 0;
                    } else {
                        sudokuGridToSolve[i][j] = Integer.parseInt(inputFields.get(currentArrayIndex).getText());
                    }
                    currentArrayIndex++;
                }
            }

            //solving sudoku starts here

            SudokuSolver sudokuSolver = new SudokuSolver(sudokuGridToSolve);

            //code below outputs solution to textfields
            //IF sudoku is not solvable display info ELSE print solution

            if (!(sudokuSolver.solveSudoku())){

                clearOutput();

                info.setText("Sudoku is not solvable");
                info.setForeground(Color.BLUE);
            } else {
                currentArrayIndex = 0;
                for (int i = 0; i < 9; i++){
                    for (int j = 0; j < 9; j++){
                        outputFields.get(currentArrayIndex).setText(Integer.toString(sudokuSolver.getSolvedSudoku()[i][j]));
                        currentArrayIndex++;
                    }
                }

                //set generated answer to green

                for (int i = 0; i < inputFields.size(); i++){
                    if (inputFields.get(i).getText().equals("0") || inputFields.get(i).getText().equals("")){
                        outputFields.get(i).setForeground(Color.GREEN);
//                        outputFields.get(i).setFont(outputFields.get(i).getFont().deriveFont(Font.BOLD, 14f));
                    }
                }

                info.setText("Solved!");
                info.setForeground(Color.GREEN);
            }

        } else if (actionEvent.getSource() == clear){

            for (JTextField inputField : inputFields){
                inputField.setText("");
            }

            clearOutput();

            info.setText("Sudoku cleared!");
            info.setForeground(Color.GREEN);

            for (JTextField outputfield : outputFields){
                outputfield.setForeground(Color.BLACK);
            }

        } else if (actionEvent.getSource() == randomize){
//            setDefaultGrid();
            randomize();
            clearOutput();

            for (JTextField outputfield : outputFields){
                outputfield.setForeground(Color.BLACK);
            }

            info.setText("Sudoku randomized");
            info.setForeground(Color.BLACK);
        }
    }

    public KeyAdapter preventLetters(JLabel info, JTextField inputField){
        KeyAdapter action = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '1') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        };
        return action;
    }

    public void setDefaultGrid(){

        int[][] gridToSolve = new int[][] {
                {0, 4, 0, 0, 0, 1, 0, 9, 0},
                {7, 0, 0, 0, 9, 2, 0, 0, 3},
                {0, 0, 3, 0, 0, 0, 8, 0, 0},
                {5, 8, 0, 9, 0, 4, 0, 0, 0},
                {0, 9, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 2, 0, 6, 0, 7, 5},
                {0, 0, 9, 0, 0, 0, 3, 0, 0},
                {1, 0, 0, 5, 2, 0, 0, 0, 4},
                {0, 2, 0, 3, 0, 0, 0, 6, 0}};

        int currentTextFieldIndex = 0;

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (gridToSolve[i][j] == 0){
                    inputFields.get(currentTextFieldIndex).setText("");
                } else {
                    String valToPass = Integer.toString(gridToSolve[i][j]);
                    inputFields.get(currentTextFieldIndex).setText(valToPass);
                }
                currentTextFieldIndex++;
            }
        }
    }

    public void clearOutput(){
        for (JTextField outputField : outputFields){
            outputField.setText("");
        }
    }

    public void randomize(){

        RandomSudokuGenerator randomSudokuGenerator = new RandomSudokuGenerator();

        int[][] gridToSolve = randomSudokuGenerator.getRandomSudoku();

        int currentTextFieldIndex = 0;

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (gridToSolve[i][j] == 0){
                    inputFields.get(currentTextFieldIndex).setText("");
                } else {
                    String valToPass = Integer.toString(gridToSolve[i][j]);
                    inputFields.get(currentTextFieldIndex).setText(valToPass);
                }
                currentTextFieldIndex++;
            }
        }
    }
}
