package vista;

public class TableroSudoku {
    public void comprobar() {
        int sudo[][] = new int[9][9];
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                if (listaTxt[i][j].getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Sudoku incompleto", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    sudo[i][j] = Integer.parseInt(listaTxt[i][j].getText());
                }
            }
        }
}
 sudoku.setSudoku(sudo);
        if (sudoku.comprobarSudoku()) {
        JOptionPane.showMessageDialog(null, "Sudoku correcto", "Sudoku", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(null, "No hay solución", "Error", JOptionPane.ERROR_MESSAGE);
    }

}