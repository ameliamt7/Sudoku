package vista;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Sudoku;
public class interfaz {
    private JPanel panel1;
    private JTextField[][] listaTxt;
    private int txtAncho;
    private int txtAltura;
    private int txtMargen;
    private int txtTamañoLetra;
    private Color panelBackground;
    private Color txtBackground1;
    private Color txtForeground1;
    private Color txtBackground2;
    private Color txtForeground2;
    private Color txtBackground3;
    private Color txtForeground3;
    private Color txtBackground4;
    private Color txtForeground4;
    public TableroSudoku(){

    }
    public void iniciarComponentes(){
        listaTxt = new JTextField[9][9];
        txtAncho = 35;
        txtAltura = 36;
        txtMargen = 4;
        txtTamañoLetra = 27;
        panelBackground = Color.BLACK;
        txtBackground1 = Color.WHITE;
        txtForeground1 = Color.BLACK;
        txtBackground2 = Color.WHITE;
        txtForeground2 = Color.BLACK;
        txtBackground3 = Color.WHITE;
        txtForeground3 = Color.BLACK;
        txtBackground4 = Color.RED;
        txtForeground4 = Color. WHITE

                public TableroSudoku(){

    }
        public void crearSudoku() {
            this.setLayout(null);
            this.setSize(txtAncho * 9 + (txtMargen * 4), txtAltura * 9 + (txtMargen * 4));
            this.setBackground(panelBackground);
            crearCamposTxt();
        }

        public void crearCamposTxt() {
            int x = txtMargen;
            int y = txtMargen;

            for (int i = 0; i < listaTxt.length; i++) {
                for (int j = 0; j < listaTxt[0].length; j++) {
                    JTextField txt = new JTextField();
                    this.add(txt);
                    txt.setBounds(x, y, txtAncho, txtAltura);
                    txt.setBackground(txtBackground1);
                    txt.setForeground(txtForeground1);
                    txt.setFont(new Font("Montserrat", Font.BOLD, txtTamañoLetra));
                    txt.setEditable(false);
                    txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    txt.setBorder(BorderFactory.createLineBorder(panelBackground, 1));
                    txt.setVisible(true);
                    x += txtAncho;
                    if ((j + 1) % 3 == 0) {
                        x += txtMargen;
                    }
                    listaTxt[i][j] = txt;
                    generarEventos(txt);
                }
                x = txtMargen;
                y += txtAltura;
                if ((i + 1) % 3 == 0) {
                    y += txtMargen;
                }
            }

        }

        public boolean txtGenerado(JTextField txt) {
            for (JTextField jTxt : listaTxtGenerados) {
                if (txt == jTxt) {
                    return true;
                }
            }
            return false;
        }

        public void generarEventos(JTextField txt) {
            MouseListener evento = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    pressed(txt);
                    txtSelected = txt;

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            };
            KeyListener eventoTecla = new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {

                    if (txtGenerado(txt)) {
                        return;
                    } else {
                        if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                            txt.setText("");
                        }

                        if (e.getKeyChar() >= 49 && e.getKeyChar() <= 57) {
                            txt.setText(String.valueOf(e.getKeyChar()));
                        }
                    }

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            };

            txt.addMouseListener(evento);
            txt.addKeyListener(eventoTecla);
        }

        public void pressed(JTextField txt) {

            for (JTextField jTxt : listaTxtAux) {
                jTxt.setBackground(txtBackground1);
                jTxt.setForeground(txtForeground1);
                jTxt.setBorder(BorderFactory.createLineBorder(panelBackground, 1));
            }
            listaTxtAux.clear();

            for (JTextField jTxt : listaTxtGenerados) {
                jTxt.setBackground(txtBackground4);
                jTxt.setForeground(txtForeground4);
            }

            for (int i = 0; i < listaTxt.length; i++) {
                for (int j = 0; j < listaTxt[0].length; j++) {
                    if (listaTxt[i][j] == txt) {
                        for (int k = 0; k < listaTxt.length; k++) {
                            listaTxt[k][j].setBackground(txtBackground2);
                            //listaTxt[k][j].setForeground(txtForeground2);
                            listaTxtAux.add(listaTxt[k][j]);
                        }
                        for (int k = 0; k < listaTxt[0].length; k++) {
                            listaTxt[i][k].setBackground(txtBackground2);
                            //listaTxt[i][k].setForeground(txtForeground2);
                            listaTxtAux.add(listaTxt[i][k]);

                        }
                        int posI = sudoku.subCuadranteActual(i);
                        int posJ = sudoku.subCuadranteActual(j);
                        for (int k = posI - 3; k < posI; k++) {
                            for (int l = posJ - 3; l < posJ; l++) {
                                listaTxt[k][l].setBackground(txtBackground2);
                                // listaTxt[k][l].setForeground(txtForeground2);
                                listaTxtAux.add(listaTxt[k][l]);
                            }
                        }

                        listaTxt[i][j].setBackground(txtBackground3);
                        listaTxt[i][j].setForeground(txtForeground3);
                        listaTxt[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                        return;
                    }

                }
            }

        }
}
