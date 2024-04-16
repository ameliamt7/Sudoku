package Modelo;

import java.util.Random;
import javax.swing.JTextField;

public class Sudoku {

    private int sudoku[][];

    public Sudoku() {
        sudoku = new int[9][9];
        limpiarSudoku();
    }

    public boolean resolverSudoku() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                if (sudoku[i][j] == 0) {
                    for (int valor = 1; valor <= 9; valor++) {
                        if (validarFila(i, valor) && validarColumna(j, valor) && validarCuadrante(i, j, valor)) {
                            sudoku[i][j] = valor;
                            if (resolverSudoku()) {
                                return true;
                            }
                            sudoku[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean comprobarSudoku() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                int aux = sudoku[i][j];
                sudoku[i][j] = 0;
                if (!validarFila(i, aux) || !validarColumna(j, aux) || !validarCuadrante(i, j, aux)) {
                    sudoku[i][j]=aux;
                    return false;

                }
                sudoku[i][j]=aux;
            }
        }
        return true;
    }
