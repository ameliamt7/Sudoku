
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

public class TableroSudoku extends JPanel {

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

    private Sudoku sudoku;
    private ArrayList<JTextField> listaTxtAux;
    private ArrayList<JTextField> listaTxtGenerados;
    public JTextField txtSelected;

    public TableroSudoku() {
        iniciarComponentes();
    }

    public void iniciarComponentes() {
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
        txtForeground4 = Color.WHITE;
        sudoku = new Sudoku();
        listaTxtAux = new ArrayList<>();
        listaTxtGenerados = new ArrayList<>();
        txtSelected = new JTextField();
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

    public void generarSudoku(int nivel) {
        limpiarTxt();
        sudoku.generarSudoku(nivel);
        int[][] sudokuGenerado = sudoku.getSudoku();
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                if (sudokuGenerado[i][j] != 0) {
                    listaTxt[i][j].setText(String.valueOf(sudokuGenerado[i][j]));
                    listaTxt[i][j].setBackground(txtBackground4);
                    listaTxt[i][j].setForeground(txtForeground4);
                    listaTxtGenerados.add(listaTxt[i][j]);
                }
            }
        }
    }


    public boolean crearSudokuPersonalizado() {
        sudoku.limpiarSudoku();
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                if (!(listaTxt[i][j].getText().isEmpty())) {

                    int num = Integer.valueOf(listaTxt[i][j].getText());
                    if (sudoku.validarColumna(j, num) && sudoku.validarFila(i, num) && sudoku.validarCuadrante(i, j, num)) {
                        sudoku.getSudoku()[i][j] = num;
                        listaTxt[i][j].setBackground(txtBackground4);
                        listaTxt[i][j].setForeground(txtForeground4);
                        listaTxt[i][j].setBorder(BorderFactory.createLineBorder(panelBackground, 1));
                        listaTxtGenerados.add(listaTxt[i][j]);
                    } else {
                        listaTxtGenerados.clear();
                        JOptionPane.showMessageDialog(null, "Sudoku Incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }

                } else {
                    listaTxt[i][j].setBackground(txtBackground1);
                    listaTxt[i][j].setForeground(txtForeground1);
                    listaTxt[i][j].setBorder(BorderFactory.createLineBorder(panelBackground, 1));
                }
            }
        }
        return true;

    }

    public void limpiarTxt() {
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                listaTxt[i][j].setText("");
                listaTxt[i][j].setBackground(txtBackground1);
                listaTxt[i][j].setForeground(txtForeground1);
                listaTxt[i][j].setBorder(BorderFactory.createLineBorder(panelBackground, 1));

            }
        }
        listaTxtGenerados.clear();
    }

    public void limpiar() {
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {

                boolean b = false;
                for (JTextField txt : listaTxtGenerados) {
                    if (listaTxt[i][j] == txt) {
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    listaTxt[i][j].setText("");
                }

            }
        }
    }

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
        sudoku.setSudoku(sudo);
        if (sudoku.comprobarSudoku()) {
            JOptionPane.showMessageDialog(null, "Sudoku correcto", "Sudoku", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No hay solución", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    public void resolver(){
        sudoku.limpiarSudoku();
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                for (JTextField txt:listaTxtGenerados) {
                    if(txt==listaTxt[i][j]){
                        sudoku.getSudoku()[i][j]=Integer.parseInt(txt.getText());
                    }
                }
            }
        }

        if(sudoku.resolverSudoku()){
            for (int i = 0; i < listaTxt.length; i++) {
                for (int j = 0; j < listaTxt[0].length; j++) {
                    listaTxt[i][j].setText(String.valueOf(sudoku.getSudoku()[i][j]));
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"No hay solución","Error",JOptionPane.ERROR_MESSAGE);
        }

    }
//        public void verSolucion(){
//        sudoku.verSolucion(listaTxt);
//    }
//
//

    public Color getTxtBackground4() {
        return txtBackground4;
    }

    public void setTxtBackground4(Color txtBackground4) {
        this.txtBackground4 = txtBackground4;
    }

    public Color getTxtForeground4() {
        return txtForeground4;
    }

    public void setTxtForeground4(Color txtForeground4) {
        this.txtForeground4 = txtForeground4;
    }

    public Sudoku getSudoku() {
        return sudoku;
    }

    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    public ArrayList<JTextField> getListaTxtAux() {
        return listaTxtAux;
    }

    public void setListaTxtAux(ArrayList<JTextField> listaTxtAux) {
        this.listaTxtAux = listaTxtAux;
    }

    public JTextField[][] getListaTxt() {
        return listaTxt;
    }

    public void setListaTxt(JTextField[][] listaTxt) {
        this.listaTxt = listaTxt;
    }

    public int getTxtAncho() {
        return txtAncho;
    }

    public void setTxtAncho(int txtAncho) {
        this.txtAncho = txtAncho;
    }

    public int getTxtAltura() {
        return txtAltura;
    }

    public void setTxtAltura(int txtAltura) {
        this.txtAltura = txtAltura;
    }

    public int getTxtMargen() {
        return txtMargen;
    }

    public void setTxtMargen(int txtMargen) {
        this.txtMargen = txtMargen;
    }

    public int getTxtTamañoLetra() {
        return txtTamañoLetra;
    }

    public void setTxtTamañoLetra(int txtTamañoLetra) {
        this.txtTamañoLetra = txtTamañoLetra;
    }

    public Color getPanelBackground() {
        return panelBackground;
    }

    public void setPanelBackground(Color panelBackground) {
        this.panelBackground = panelBackground;
    }

    public Color getTxtBackground1() {
        return txtBackground1;
    }

    public void setTxtBackground1(Color txtBackground1) {
        this.txtBackground1 = txtBackground1;
    }

    public Color getTxtForeground1() {
        return txtForeground1;
    }

    public void setTxtForeground1(Color txtForeground1) {
        this.txtForeground1 = txtForeground1;
    }

    public Color getTxtBackground2() {
        return txtBackground2;
    }

    public void setTxtBackground2(Color txtBackground2) {
        this.txtBackground2 = txtBackground2;
    }

    public Color getTxtForeground2() {
        return txtForeground2;
    }

    public void setTxtForeground2(Color txtForeground2) {
        this.txtForeground2 = txtForeground2;
    }

    public Color getTxtBackground3() {
        return txtBackground3;
    }

    public void setTxtBackground3(Color txtBackground3) {
        this.txtBackground3 = txtBackground3;
    }

    public Color getTxtForeground3() {
        return txtForeground3;
    }

    public void setTxtForeground3(Color txtForeground3) {
        this.txtForeground3 = txtForeground3;
    }

}
package vista;

import java.awt.Color;
import javax.swing.BorderFactory;

public class FromNiveles extends javax.swing.JFrame {

    private TableroSudoku tableroSudoku;
    private int xPos;
    private int yPos;
    public FromNiveles(TableroSudoku tableroSudoku) {
        initComponents();
        this.tableroSudoku = tableroSudoku;
        panelFondo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        panelFondo.setBackground(new java.awt.Color(203, 102, 102));
        panelFondo.setPreferredSize(new java.awt.Dimension(300, 300));
        panelFondo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelFondoMouseDragged(evt);
            }
        });
        panelFondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelFondoMousePressed(evt);
            }
        });
        panelFondo.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SUDOKU");
        panelFondo.add(jLabel1);
        jLabel1.setBounds(80, 20, 120, 20);

        jPanel2.setBackground(new java.awt.Color(89, 43, 25));

        jLabel3.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("                FÁCIL");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelFondo.add(jPanel2);
        jPanel2.setBounds(60, 60, 180, 50);

        jPanel3.setBackground(new java.awt.Color(89, 43, 25));

        jLabel2.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("              MEDIO");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelFondo.add(jPanel3);
        jPanel3.setBounds(60, 130, 180, 50);

        jPanel4.setBackground(new java.awt.Color(89, 43, 25));

        jLabel4.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("             DIFÍCIL");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel4MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelFondo.add(jPanel4);
        jPanel4.setBounds(60, 200, 180, 50);

        jLabel5.setBackground(new java.awt.Color(89, 43, 25));
        jLabel5.setFont(new java.awt.Font("Montserrat", 0, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(89, 43, 25));
        jLabel5.setText("x");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel5MousePressed(evt);
            }
        });
        panelFondo.add(jLabel5);
        jLabel5.setBounds(270, 0, 30, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        tableroSudoku.generarSudoku(1);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel3MousePressed

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        tableroSudoku.generarSudoku(2);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MousePressed
        tableroSudoku.generarSudoku(3);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel4MousePressed

    private void panelFondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelFondoMousePressed
        xPos = evt.getX();
        yPos = evt.getY();
    }//GEN-LAST:event_panelFondoMousePressed

    private void panelFondoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelFondoMouseDragged
        this.setLocation(evt.getXOnScreen() - xPos, evt.getYOnScreen() - yPos);
    }//GEN-LAST:event_panelFondoMouseDragged

    private void jLabel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MousePressed
        this.setVisible(false);
    }//GEN-LAST:event_jLabel5MousePressed

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        jPanel2.setBackground(new Color(143, 72, 72));

    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        jPanel2.setBackground(new Color(89,43,25));
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        jPanel3.setBackground(new Color(143, 72, 72));
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        jPanel3.setBackground(new Color(89,43,25));
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        jPanel4.setBackground(new Color(143, 72, 72));
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        jPanel4.setBackground(new Color(89,43,25));
    }//GEN-LAST:event_jLabel4MouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel panelFondo;
    // End of variables declaration//GEN-END:variables
}
package vista;

import java.awt.Color;
import javax.swing.BorderFactory;
import modelo.Sudoku;

public class FormSudoku extends javax.swing.JFrame {

    public static TableroSudoku tableroSudoku;
    private TableroNumeros tableroNumeros;
    private FromNiveles fromNiveles;
    private boolean estadoCrear;

    private int xPos;
    private int yPos;

    public FormSudoku() {
        initComponents();
        iniciarComponentes();
        panelFondo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public void iniciarComponentes() {
        tableroSudoku = new TableroSudoku();
        tableroSudoku.setTxtAltura(36);
        tableroSudoku.setTxtAncho(36);
        tableroSudoku.setTxtMargen(4);
        tableroSudoku.setTxtTamañoLetra(27);

        tableroSudoku.setPanelBackground(new Color(89, 43, 25));

        tableroSudoku.setTxtBackground1(Color.WHITE);
        tableroSudoku.setTxtForeground1(new Color(153, 1, 1));
        tableroSudoku.setTxtBackground2(new Color(232, 186, 186));
        tableroSudoku.setTxtForeground2(Color.BLACK);
        tableroSudoku.setTxtBackground3(new Color(203, 102, 102));
        tableroSudoku.setTxtForeground3(Color.WHITE);
        tableroSudoku.setTxtBackground4(new Color(238, 227, 227));
        tableroSudoku.setTxtForeground4(Color.BLACK);

        panelFondo.add(tableroSudoku);
        tableroSudoku.setLocation(70, 60);
        tableroSudoku.setVisible(true);
        tableroSudoku.crearSudoku();

        tableroNumeros = new TableroNumeros();
        tableroNumeros.setTxtAncho(36);
        tableroNumeros.setTxtAltura(36);
        tableroNumeros.setTxtMargen(4);
        tableroNumeros.setTxtTamañoLetra(27);
        tableroNumeros.setPanelBackground(new Color(89, 43, 25));
        tableroNumeros.setTxtBackground1(new Color(239, 229, 216));
        tableroNumeros.setTxtForeground1(new Color(89, 43, 25));
        tableroNumeros.setTxtBackground2(new Color(143, 72, 72));
        tableroNumeros.setTxtForeground2(Color.WHITE);
        panelFondo.add(tableroNumeros);
        tableroNumeros.crearTablero();
        tableroNumeros.setLocation(20, 60);
        tableroNumeros.setVisible(true);

        tableroSudoku.generarSudoku(2);
        estadoCrear = true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelNuevaPartida = new javax.swing.JPanel();
        lblNuevaPartida = new javax.swing.JLabel();
        panelLimpiar = new javax.swing.JPanel();
        lblLimpiar = new javax.swing.JLabel();
        panelCrear = new javax.swing.JPanel();
        lblCrear = new javax.swing.JLabel();
        panelComprobar = new javax.swing.JPanel();
        lblComprobar = new javax.swing.JLabel();
        panelResolver = new javax.swing.JPanel();
        lblResolver = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        panelFondo.setBackground(new java.awt.Color(203, 102, 102));
        panelFondo.setPreferredSize(new java.awt.Dimension(540, 420));
        panelFondo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelFondoMouseDragged(evt);
            }
        });
        panelFondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelFondoMousePressed(evt);
            }
        });
        panelFondo.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SUDOKU");
        panelFondo.add(jLabel1);
        jLabel1.setBounds(20, 30, 120, 20);

        panelNuevaPartida.setBackground(new java.awt.Color(89, 43, 25));

        lblNuevaPartida.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        lblNuevaPartida.setForeground(new java.awt.Color(255, 255, 255));
        lblNuevaPartida.setText("    NUEVA PARTIDA");
        lblNuevaPartida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNuevaPartida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNuevaPartidaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblNuevaPartidaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblNuevaPartidaMousePressed(evt);
            }
        });

        javax.swing.GroupLayout panelNuevaPartidaLayout = new javax.swing.GroupLayout(panelNuevaPartida);
        panelNuevaPartida.setLayout(panelNuevaPartidaLayout);
        panelNuevaPartidaLayout.setHorizontalGroup(
                panelNuevaPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNuevaPartidaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblNuevaPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelNuevaPartidaLayout.setVerticalGroup(
                panelNuevaPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNuevaPartidaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblNuevaPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelFondo.add(panelNuevaPartida);
        panelNuevaPartida.setBounds(420, 60, 150, 40);

        panelLimpiar.setBackground(new java.awt.Color(89, 43, 25));

        lblLimpiar.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        lblLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        lblLimpiar.setText("            LIMPIAR");
        lblLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLimpiarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLimpiarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblLimpiarMousePressed(evt);
            }
        });

        javax.swing.GroupLayout panelLimpiarLayout = new javax.swing.GroupLayout(panelLimpiar);
        panelLimpiar.setLayout(panelLimpiarLayout);
        panelLimpiarLayout.setHorizontalGroup(
                panelLimpiarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLimpiarLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelLimpiarLayout.setVerticalGroup(
                panelLimpiarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLimpiarLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelFondo.add(panelLimpiar);
        panelLimpiar.setBounds(420, 120, 150, 40);

        panelCrear.setBackground(new java.awt.Color(89, 43, 25));

        lblCrear.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        lblCrear.setForeground(new java.awt.Color(255, 255, 255));
        lblCrear.setText("              CREAR");
        lblCrear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCrear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCrearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCrearMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblCrearMousePressed(evt);
            }
        });

        javax.swing.GroupLayout panelCrearLayout = new javax.swing.GroupLayout(panelCrear);
        panelCrear.setLayout(panelCrearLayout);
        panelCrearLayout.setHorizontalGroup(
                panelCrearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCrearLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelCrearLayout.setVerticalGroup(
                panelCrearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCrearLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelFondo.add(panelCrear);
        panelCrear.setBounds(420, 180, 150, 40);

        panelComprobar.setBackground(new java.awt.Color(89, 43, 25));

        lblComprobar.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        lblComprobar.setForeground(new java.awt.Color(255, 255, 255));
        lblComprobar.setText("       COMPROBAR");
        lblComprobar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblComprobar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblComprobarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblComprobarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblComprobarMousePressed(evt);
            }
        });

        javax.swing.GroupLayout panelComprobarLayout = new javax.swing.GroupLayout(panelComprobar);
        panelComprobar.setLayout(panelComprobarLayout);
        panelComprobarLayout.setHorizontalGroup(
                panelComprobarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelComprobarLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblComprobar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelComprobarLayout.setVerticalGroup(
                panelComprobarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelComprobarLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblComprobar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelFondo.add(panelComprobar);
        panelComprobar.setBounds(420, 240, 150, 40);

        panelResolver.setBackground(new java.awt.Color(89, 43, 25));

        lblResolver.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        lblResolver.setForeground(new java.awt.Color(255, 255, 255));
        lblResolver.setText("         RESOLVER");
        lblResolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblResolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblResolverMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblResolverMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblResolverMousePressed(evt);
            }
        });

        javax.swing.GroupLayout panelResolverLayout = new javax.swing.GroupLayout(panelResolver);
        panelResolver.setLayout(panelResolverLayout);
        panelResolverLayout.setHorizontalGroup(
                panelResolverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelResolverLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblResolver, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelResolverLayout.setVerticalGroup(
                panelResolverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelResolverLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblResolver, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelFondo.add(panelResolver);
        panelResolver.setBounds(420, 300, 150, 40);

        jLabel2.setBackground(new java.awt.Color(89, 43, 25));
        jLabel2.setFont(new java.awt.Font("Montserrat", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(89, 43, 25));
        jLabel2.setText("x");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        panelFondo.add(jLabel2);
        jLabel2.setBounds(550, 0, 30, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panelFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblNuevaPartidaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNuevaPartidaMousePressed
        if (fromNiveles != null) {
            fromNiveles.setVisible(true);
        } else {
            fromNiveles = new FromNiveles(tableroSudoku);
            fromNiveles.setVisible(true);
        }
    }//GEN-LAST:event_lblNuevaPartidaMousePressed

    private void lblLimpiarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimpiarMousePressed
        tableroSudoku.limpiar();
    }//GEN-LAST:event_lblLimpiarMousePressed

    private void lblCrearMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCrearMousePressed

        if (estadoCrear) {
            lblNuevaPartida.setVisible(false);
            lblLimpiar.setVisible(false);
            lblComprobar.setVisible(false);
            lblResolver.setVisible(false);
            estadoCrear = false;
            tableroSudoku.limpiarTxt();
            lblCrear.setText("              LISTO");

        } else {
            if (tableroSudoku.crearSudokuPersonalizado()) {
                lblNuevaPartida.setVisible(true);
                lblLimpiar.setVisible(true);
                lblComprobar.setVisible(true);
                lblResolver.setVisible(true);
                estadoCrear = true;
                lblCrear.setText("              CREAR");
            }

        }

    }//GEN-LAST:event_lblCrearMousePressed

    private void lblComprobarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblComprobarMousePressed
        tableroSudoku.comprobar();
    }//GEN-LAST:event_lblComprobarMousePressed

    private void panelFondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelFondoMousePressed

        xPos = evt.getX();
        yPos = evt.getY();
    }//GEN-LAST:event_panelFondoMousePressed

    private void panelFondoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelFondoMouseDragged
        this.setLocation(evt.getXOnScreen() - xPos, evt.getYOnScreen() - yPos);
    }//GEN-LAST:event_panelFondoMouseDragged

    private void lblResolverMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResolverMousePressed
        tableroSudoku.resolver();
    }//GEN-LAST:event_lblResolverMousePressed

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        System.exit(0);
    }//GEN-LAST:event_jLabel2MousePressed

    private void lblNuevaPartidaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNuevaPartidaMouseEntered
        panelNuevaPartida.setBackground(new Color(143, 72, 72));
    }//GEN-LAST:event_lblNuevaPartidaMouseEntered

    private void lblNuevaPartidaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNuevaPartidaMouseExited
        panelNuevaPartida.setBackground(new Color(89,43,25));
    }//GEN-LAST:event_lblNuevaPartidaMouseExited

    private void lblLimpiarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimpiarMouseEntered
        panelLimpiar.setBackground(new Color(143, 72, 72));
    }//GEN-LAST:event_lblLimpiarMouseEntered

    private void lblLimpiarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimpiarMouseExited
        panelLimpiar.setBackground(new Color(89,43,25));
    }//GEN-LAST:event_lblLimpiarMouseExited

    private void lblCrearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCrearMouseEntered
        panelCrear.setBackground(new Color(143, 72, 72));
    }//GEN-LAST:event_lblCrearMouseEntered

    private void lblCrearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCrearMouseExited
        panelCrear.setBackground(new Color(89,43,25));
    }//GEN-LAST:event_lblCrearMouseExited

    private void lblComprobarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblComprobarMouseEntered
        panelComprobar.setBackground(new Color(143, 72, 72));
    }//GEN-LAST:event_lblComprobarMouseEntered

    private void lblComprobarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblComprobarMouseExited
        panelComprobar.setBackground(new Color(89,43,25));
    }//GEN-LAST:event_lblComprobarMouseExited

    private void lblResolverMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResolverMouseEntered
        panelResolver.setBackground(new Color(143, 72, 72));
    }//GEN-LAST:event_lblResolverMouseEntered

    private void lblResolverMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResolverMouseExited
        panelResolver.setBackground(new Color(89,43,25));
    }//GEN-LAST:event_lblResolverMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblComprobar;
    private javax.swing.JLabel lblCrear;
    private javax.swing.JLabel lblLimpiar;
    private javax.swing.JLabel lblNuevaPartida;
    private javax.swing.JLabel lblResolver;
    private javax.swing.JPanel panelComprobar;
    private javax.swing.JPanel panelCrear;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelLimpiar;
    private javax.swing.JPanel panelNuevaPartida;
    private javax.swing.JPanel panelResolver;
    // End of variables declaration//GEN-END:variables

}

package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TableroNumeros extends JPanel{
    private int txtAncho;
    private int txtAltura;
    private int txtMargen;
    private int txtTamañoLetra;
    private Color panelBackground;
    private Color txtBackground1;
    private Color txtForeground1;
    private Color txtBackground2;
    private Color txtForeground2;
    private TableroSudoku tableroSudoku;

    public TableroNumeros(){
        iniciarComponentes();
        tableroSudoku = FormSudoku.tableroSudoku;
    }
    public void iniciarComponentes(){
        txtAncho = 30;
        txtAltura = 30;
        txtMargen = 4;
        txtTamañoLetra = 27;
        panelBackground = Color.BLACK;
        txtBackground1 = Color.WHITE;
        txtForeground1 = Color.BLACK;
        txtBackground2 = Color.WHITE;
        txtForeground2 = Color.BLACK;
    }
    public void crearTablero(){
        this.setLayout(null);
        this.setSize(txtAncho+(2*txtMargen), txtAltura*9+(4*txtMargen));
        this.setBackground(panelBackground);
        crearCamposTxt();
    }
    public void crearCamposTxt(){
        int x = txtMargen;
        int y = txtMargen;

        for (int i = 0; i < 9; i++) {
            JTextField txt = new JTextField();
            this.add(txt);
            txt.setBounds(x, y, txtAncho, txtAltura);
            txt.setBackground(txtBackground1);
            txt.setForeground(txtForeground1);
            txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
            txt.setEditable(false);
            txt.setBorder(BorderFactory.createLineBorder(panelBackground,1));
            txt.setFont(new Font("MontSerrat",Font.BOLD,txtTamañoLetra));
            txt.setText(String.valueOf(i+1));

            y+=txtAltura;
            if((i+1)%3==0){
                y+=txtMargen;
            }
            generarEventosTxt(txt);
        }
    }
    public void generarEventosTxt(JTextField txt){
        MouseListener evento = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(tableroSudoku.txtGenerado(tableroSudoku.txtSelected)){
                    return;
                }
                tableroSudoku.txtSelected.setText(txt.getText());

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                txt.setBackground(txtBackground2);
                txt.setForeground(txtForeground2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                txt.setBackground(txtBackground1);
                txt.setForeground(txtForeground1);
            }
        };
        txt.addMouseListener(evento);
    }

    public int getTxtAncho() {
        return txtAncho;
    }

    public void setTxtAncho(int txtAncho) {
        this.txtAncho = txtAncho;
    }

    public int getTxtAltura() {
        return txtAltura;
    }

    public void setTxtAltura(int txtAltura) {
        this.txtAltura = txtAltura;
    }

    public int getTxtMargen() {
        return txtMargen;
    }

    public void setTxtMargen(int txtMargen) {
        this.txtMargen = txtMargen;
    }

    public int getTxtTamañoLetra() {
        return txtTamañoLetra;
    }

    public void setTxtTamañoLetra(int txtTamañoLetra) {
        this.txtTamañoLetra = txtTamañoLetra;
    }

    public Color getPanelBackground() {
        return panelBackground;
    }

    public void setPanelBackground(Color panelBackground) {
        this.panelBackground = panelBackground;
    }

    public Color getTxtBackground1() {
        return txtBackground1;
    }

    public void setTxtBackground1(Color txtBackground1) {
        this.txtBackground1 = txtBackground1;
    }

    public Color getTxtForeground1() {
        return txtForeground1;
    }

    public void setTxtForeground1(Color txtForeground1) {
        this.txtForeground1 = txtForeground1;
    }

    public Color getTxtBackground2() {
        return txtBackground2;
    }

    public void setTxtBackground2(Color txtBackground2) {
        this.txtBackground2 = txtBackground2;
    }

    public Color getTxtForeground2() {
        return txtForeground2;
    }

    public void setTxtForeground2(Color txtForeground2) {
        this.txtForeground2 = txtForeground2;
    }


}
package modelo;

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

    public boolean validarCuadrante(int i, int j, int valor) {
        int posI = subCuadranteActual(i);
        int posJ = subCuadranteActual(j);

        for (int k = posI - 3; k < posI; k++) {
            for (int l = posJ - 3; l < posJ; l++) {
                if (sudoku[k][l] == valor) {
                    return false;
                }
            }
        }
        return true;
    }

    public void limpiarSudoku() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                sudoku[i][j] = 0;
            }
        }
    }

    public int subCuadranteActual(int pos) {
        if (pos <= 2) {
            return 3;
        } else if (pos <= 5) {
            return 6;
        } else {
            return 9;
        }
    }

    public boolean validarFila(int i, int valor) {
        for (int j = 0; j < sudoku[i].length; j++) {
            if (sudoku[i][j] == valor) {
                return false;
            }
        }
        return true;
    }

    public boolean validarColumna(int j, int valor) {
        for (int i = 0; i < sudoku.length; i++) {
            if (sudoku[i][j] == valor) {
                return false;
            }
        }
        return true;
    }

    public void mostrarSudoku() {
        resolverSudoku();
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                System.out.print(sudoku[i][j]);
                if (!(j == sudoku[0].length - 1)) {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
    }

    public void generarSudoku(int nivel) {
        limpiarSudoku();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, j, num)) {
                    sudoku[i][j] = num;
                } else {
                    j--;
                }
            }
        }

        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, j, num)) {
                    sudoku[i][j] = num;
                } else {
                    j--;
                }
            }
        }

        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, j, num)) {
                    sudoku[i][j] = num;
                } else {
                    j--;
                }
            }
        }
        resolverSudoku();

        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                int aux = j;
                int rand = random.nextInt(nivel + 1);
                j += rand;
                for (int k = aux; k < j && k < sudoku.length; k++) {
                    sudoku[i][k] = 0;
                }
            }
        }

    }

    public int[][] getSudoku() {
        return sudoku;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }
//      public boolean verSolucion(JTextField[][] listaTxt) {
//
//          for (int i = 0; i < sudoku.length; i++) {
//            for (int j = 0; j < sudoku[0].length; j++) {
//                if (sudoku[i][j] == 0) {
//                    for (int valor = 1; valor <= 9; valor++) {
//                        if (validarFila(i, valor) && validarColumna(j, valor) && validarCuadrante(i, j, valor)) {
//                            sudoku[i][j] = valor;
//                            listaTxt[i][j].setText(String.valueOf(getSudoku()[i][j]));
//                            if (verSolucion(listaTxt)) {
//                                return true;
//                            }
//                            sudoku[i][j] = 0;
//                              listaTxt[i][j].setText("");
//                        }
//                        try {
//                            Thread.sleep(10);
//                        } catch (Exception e) {
//                        }
//                    }
//                    return false;
//                }
//            }
//        }
//        return true;
//
//
//    }

}
package principal;

import modelo.Sudoku;
import vista.FormSudoku;

public class Principal {

    public static void main(String[] args) {
        FormSudoku sudoku = new FormSudoku();
        sudoku.setVisible(true);
    }
}