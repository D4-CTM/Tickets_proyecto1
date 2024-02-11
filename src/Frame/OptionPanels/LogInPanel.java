package Frame.OptionPanels;

import Frame.HudBar;
import javax.swing.*;
import AccManager.AccManager;
import java.awt.event.MouseEvent;
import EventManager.EventManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

public class LogInPanel extends javax.swing.JPanel{
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
        
    private final int FrameWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int FrameHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 50;
    public LogInPanel(AccManager AccMan, EventManager EvMan, HudBar Hud){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.setLayout(null);
        this.setBackground(java.awt.Color.LIGHT_GRAY);
        this.setBounds(0, 0, FrameWidth - FrameWidth/3, FrameHeight);
        
        setUsernameTXTField();
        setPasswordField();
        setGuestBTN();
        LogInBTN();
    }
    
    private void setUsernameTXTField(){
        Username = new JTextField();
        
        Username.setBorder(null);
        Username.setBackground(null);
        Username.setOpaque(false);
        Username.setForeground(java.awt.Color.GRAY);
        Username.setText("Ingrese su nombre de usuario");
        Username.setFont(new java.awt.Font("Roboto",0,26));
        Username.setBounds(50, this.getHeight()/2-25, this.getWidth() - 100, 50);
        Username.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Username.getText().equals("Ingrese su nombre de usuario")){
                    Username.setText(null);
                    Username.setForeground(java.awt.Color.BLACK);
                    Username.requestFocus();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (Username.getText().isBlank() && Username.hasFocus()){
                    Username.setForeground(java.awt.Color.GRAY);
                    Username.setText("Ingrese su nombre de usuario");
                } else if (!Username.getText().isBlank() && !Username.getText().equals("Ingrese su nombre de usuario")){
                    Username.setForeground(java.awt.Color.BLACK);
                }
            }
        });
        Username.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if (Username.getText().equals("Ingrese su nombre de usuario")){
                    Username.setText(null);
                    Username.setForeground(java.awt.Color.BLACK);
                    Username.requestFocus();
                }
            }
        });
        add(Username);
        JLabel UsernameTXT = new JLabel("USUARIO: ");
        
        UsernameTXT.setFont(new java.awt.Font("Roboto",0,14));
        UsernameTXT.setBounds(Username.getX(), Username.getY() - 20, Username.getWidth(), 25);
        add(UsernameTXT);
        JSeparator Divisor = new JSeparator();
        
        Divisor.setBackground(Color.BLACK);
        Divisor.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        Divisor.setBounds(Username.getX(), Username.getY() + Username.getHeight() - 5, Username.getWidth(), 10);
        add(Divisor);
    }
    
    private void setPasswordField(){
        Password = new JPasswordField();
        
        Password.setEchoChar((char) 0);
        Password.setBorder(null);
        Password.setBackground(null);
        Password.setOpaque(false);
        Password.setText("Ingrese su contraseña");
        Password.setForeground(java.awt.Color.GRAY);
        Password.setFont(new java.awt.Font("Roboto",0,24));
        Password.setBounds(50, this.getHeight()/2+75, this.getWidth() - 175, 50);
        Password.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Password.getText().equals("Ingrese su contraseña")){
                    Password.setText(null);
                    Password.requestFocus();
                    Password.setEchoChar('\u2022');
                    Password.setForeground(java.awt.Color.BLACK);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (Password.getText().isBlank() && Password.hasFocus()){
                    Password.setForeground(java.awt.Color.GRAY);
                    Password.setText("Ingrese su contraseña");
                    Password.setEchoChar((char) 0);
                }
            }
        });
        Password.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if (Password.getText().equals("Ingrese su contraseña")){
                    Password.setText(null);
                    Password.requestFocus();
                    Password.setEchoChar('\u2022');
                    Password.setForeground(java.awt.Color.BLACK);
                }
            }
        });
        add(Password);
        JLabel PasswordTXT = new JLabel("CONTRASEÑA: ");
        
        PasswordTXT.setFont(new java.awt.Font("Roboto",0,14));
        PasswordTXT.setBounds(Password.getX(), Password.getY() - 20, Password.getWidth(), 25);
        add(PasswordTXT);
        JSeparator Divisor = new JSeparator();
        
        Divisor.setBackground(Color.BLACK);
        Divisor.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        Divisor.setBounds(Password.getX(), Password.getY() + Password.getHeight() - 5, Password.getWidth(), 10);
        add(Divisor);
        JButton RevealPass = new JButton();
        
        RevealPass.setBounds(Password.getX() + Password.getWidth() + 25, Password.getY(), 50, 50);
        RevealPass.addActionListener((ActionEvent e) -> {
            if (Password.echoCharIsSet()){
                Password.setEchoChar((char) 0);
            } else if (!Password.echoCharIsSet()) {
                Password.setEchoChar('\u2022');
            }
        });
        RevealPass.setFocusable(false);
        add(RevealPass);
    }
    
    private void LogInBTN(){
        JButton LogIn = new JButton();

        LogIn.setBounds(Password.getX(), (Password.getY() + Password.getHeight()) + 25, FrameWidth/5, 50);
        LogIn.setFont(new java.awt.Font("Berlin Sans FB",0,24));
        LogIn.setForeground(new Color(222,208,185));
        LogIn.setBackground(new Color(58, 89, 234));
        LogIn.setText("Iniciar sesión");
        LogIn.setFocusable(false);
        
        LogIn.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseExited(MouseEvent e){
                LogIn.setBackground(new Color(58, 89, 234));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                LogIn.setBackground(new Color(46, 70, 188));
            }
        });
        
        LogIn.addActionListener((ActionEvent e) -> {
            if (AccMan.getAccount(true, Username.getText(), Password.getText(), 0) != null){
                Username.setForeground(java.awt.Color.GRAY);
                Username.setText("Ingrese su nombre de usuario");
                Password.setEchoChar((char) 0);
                Password.setForeground(java.awt.Color.GRAY);
                Password.setText("Ingrese su contraseña");
                switch (AccMan.getCurrentAcc().getAccType()) {
                    case 0-> Hud.setActPanel(1);
                    case 1 -> Hud.setActPanel(97);
                    case 2 -> Hud.setActPanel(98);
                }
                Hud.PanelChange = true;
                JOptionPane.showMessageDialog(this, "Bienvenido al sistema: "+AccMan.getCurrentAcc().getName());
            } else JOptionPane.showMessageDialog(this, "No se ha podido encontrar la cuenta que busca");
        });
        
        add(LogIn);
    }
    
    private void setGuestBTN(){
        JButton Guest = new JButton();

        Guest.setBounds((Username.getX() + Username.getWidth()) - (FrameWidth/5), (Password.getY() + Password.getHeight()) + 25, FrameWidth/5, 50);
        Guest.setFont(new java.awt.Font("Berlin Sans FB",0,24));
        Guest.setForeground(new Color(222,208,185));
        Guest.setBackground(new Color(58, 89, 234));
        Guest.setText("Cuenta de invitado");
        Guest.setFocusable(false);

        Guest.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseExited(MouseEvent e){
                Guest.setBackground(new Color(58, 89, 234));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                Guest.setBackground(new Color(46, 70, 188));
            }
        });
        
        Guest.addActionListener((ActionEvent e) -> {
            if (AccMan.getAccount(true, "Gæst", "", 0) != null){
                Username.setForeground(java.awt.Color.GRAY);
                Username.setText("Ingrese su nombre de usuario");
                Password.setEchoChar((char) 0);
                Password.setForeground(java.awt.Color.GRAY);
                Password.setText("Ingrese su contraseña");
                
                Hud.setActPanel(98);
                Hud.PanelChange = true;
            } else JOptionPane.showMessageDialog(this, "No se ha podido encontrar la cuenta que busca");
        });
        
        add(Guest);
    }
    
    // <-- Swing elements -->
    private JTextField Username;
    private JPasswordField Password;
    // <-- Swing Elements -->
}
