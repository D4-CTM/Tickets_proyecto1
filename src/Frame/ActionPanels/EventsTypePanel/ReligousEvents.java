package Frame.ActionPanels.EventsTypePanel;

import AccManager.AccManager;
import EventManager.EventManager;
import Frame.HudBar;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class ReligousEvents extends javax.swing.JPanel {
    //Tecnicamente constantes
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    
    public ReligousEvents(int X, int Y, int width, int height, AccManager AccMan, EventManager EvMan, HudBar Hud){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.setLayout(null);
        this.setBounds(X, Y, width, height);
        this.setBackground(java.awt.Color.LIGHT_GRAY);
        
        setAlmasConvertidasField();
    }
    //Text field usado para ingresar la cantidad de almas que fueron convertida durante la noche
    private void setAlmasConvertidasField(){
        JLabel TitleTXT = new JLabel("Ingrese la cantidad de almas convertidas: ");
                
        TitleTXT.setFont(new java.awt.Font("Roboto",0,14));
        TitleTXT.setBounds(0,10, getWidth(),25);
        add(TitleTXT);
        AlmasConvertidas = new JTextField();
        
        AlmasConvertidas.setBorder(null);
        AlmasConvertidas.setBackground(null);
        AlmasConvertidas.setOpaque(false);
        AlmasConvertidas.setForeground(java.awt.Color.GRAY);
        AlmasConvertidas.setText("Almas convertidas");
        AlmasConvertidas.setFont(new java.awt.Font("Roboto",0,26));
        AlmasConvertidas.setBounds(TitleTXT.getX(), TitleTXT.getY()+TitleTXT.getHeight(), TitleTXT.getWidth(), 50);
        AlmasConvertidas.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (AlmasConvertidas.getText().equals("Almas convertidas")){
                    AlmasConvertidas.setText(null);
                    AlmasConvertidas.setForeground(java.awt.Color.BLACK);
                    AlmasConvertidas.requestFocus();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (AlmasConvertidas.getText().isBlank() && AlmasConvertidas.hasFocus()){
                    AlmasConvertidas.setForeground(java.awt.Color.GRAY);
                    AlmasConvertidas.setText("Almas convertidas");
                } else if (!AlmasConvertidas.getText().isBlank() && !AlmasConvertidas.getText().equals("Almas convertidas")){
                    AlmasConvertidas.setForeground(java.awt.Color.BLACK);
                }
            }
        });
        AlmasConvertidas.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                    (c == KeyEvent.VK_BACK_SPACE) ||
                    (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
                if (AlmasConvertidas.getText().equals("Almas convertidas")){
                    AlmasConvertidas.setText(null);
                    AlmasConvertidas.setForeground(java.awt.Color.BLACK);
                    AlmasConvertidas.requestFocus();
                }
            }
        });
        add(AlmasConvertidas);
        JSeparator Divisor = new JSeparator();
        
        Divisor.setBackground(Color.BLACK);
        Divisor.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        Divisor.setBounds(AlmasConvertidas.getX(), AlmasConvertidas.getY() + AlmasConvertidas.getHeight() - 5, AlmasConvertidas.getWidth(), 10);
        add(Divisor);
    }
    //Comprueba sea posible la creacion del evento
    public boolean isPossible(String Asistiran){
        try {
            if (Integer.parseInt(AlmasConvertidas.getText()) <= Integer.parseInt(Asistiran)){
                return true;
            } else JOptionPane.showMessageDialog(this, "Hay mas almas convertidas que gente capaz de asistir al evento");
        } catch (HeadlessException | NumberFormatException Ex) {
            if (!AlmasConvertidas.getText().isBlank() && AlmasConvertidas.getText().equals("Almas convertidas")){
                return true;
            } else JOptionPane.showMessageDialog(this, "Ha ocurrido un error con la cantidad de almas convertidas", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    //Reiniciar los datos
    public void ClearData(){
        AlmasConvertidas.setText("Almas convertidas");
        AlmasConvertidas.setForeground(java.awt.Color.GRAY);
    }
    //Se setea para que almas convertidas no sea editable
    public final void InvalidFields(){
        AlmasConvertidas.setEditable(Hud.getActPanel() != 5);
    }
    //Se setean los datos
    public void setData(){
        AlmasConvertidas.setText((EvMan.getReligousEvent(Hud.getCodice(), 0).getAlmasConvertidas() != 0)?String.valueOf(EvMan.getReligousEvent(Hud.getCodice(), 0).getAlmasConvertidas()):"Almas convertidas");
        AlmasConvertidas.setBackground((EvMan.getReligousEvent(Hud.getCodice(), 0).getAlmasConvertidas() != 0)?Color.BLACK:Color.lightGray);
    }
    // -- GETTERS --
    public String getAlmasConvertidas(){
        return AlmasConvertidas.getText();
    }
    // -- GETTERS --
    // -- SWING ELEMENT --
    private JTextField AlmasConvertidas;
    // -- SWING ELEMENT --
}
