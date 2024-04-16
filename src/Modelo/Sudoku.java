package Modelo;

import java.util.Random;
import javax.swing.JTextField;

public class Sudoku {

    private int sudoku[][];

    public Sudoku() {
        sudoku = new int[9][9];
        limpiarSudoku();
    }