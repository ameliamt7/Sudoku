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
    } public void iniciarComponentes(){
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