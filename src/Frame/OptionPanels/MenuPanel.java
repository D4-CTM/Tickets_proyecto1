package Frame.OptionPanels;

import EventManager.EventManager;
import AccManager.AccManager;
import java.awt.Color;
import javax.swing.*;
import Frame.HudBar;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends javax.swing.JPanel{
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    
    private final int FrameWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int FrameHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 50;
    public MenuPanel(AccManager AccMan, EventManager EvMan, HudBar Hud){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.setLayout(null);
        this.setBackground(null);
        this.setBounds(0, 0, FrameWidth , FrameHeight);
        setMenuOptions();
        BTNPanel();
        setLogo();
    }
    //Funcion usada para desplegar datos cada que se inicia sesion en una cuenta 
    public final void Reveal(boolean Reveal){
        if (Reveal){
            switch (Hud.getActPanel()){
                case 0 -> AccountManager.doClick();
                case 97 -> EventCreator.doClick();
                case 98 -> Report.doClick();
            }
            this.setVisible(true);
        } else this.setVisible(false);
    }
    //Menu de opciones, lugar donde se elegira la accion a realizar
    private void setMenuOptions(){
        Options = new JPanel();
        
        Options.setLayout(null);
        Options.setBackground(new Color(46, 70, 188));
        Options.setBounds(0, 0, FrameWidth/3,FrameHeight);
        add(Options);
    }
    //Logo del programa, se vera en la cima
    private void setLogo(){
        JLabel Logo = new JLabel();
        //Width of 376 && Height of 100
        Logo.setBounds(25, 25, Options.getWidth()-50, 100);
        Options.add(Logo);
    }
    //Panel de los botones principales
    private void BTNPanel(){
        EventsPanel = new JPanel();
        
        EventsPanel.setLayout(null);
        EventsPanel.setBackground(new Color(23, 47, 161));
        EventsPanel.setBounds(60, 0, Options.getWidth() - 60, FrameHeight - 125);        
        // EventsPanel Width and Height
        // Width: 366
        // Height: 545
        Options.add(EventsPanel);
        setOptions();
        
        setEvCreBTN();
        setAccMan();
        setReport();
        setLogOut();
    }
    //Panel de opcion de 'Crear evento'
    private void setOptions(){
        this.EventOptions = new EventOptions(AccMan, EvMan, Hud, EventsPanel.getWidth(), EventsPanel.getHeight());
        
        EventOptions.setBounds(EventOptions.getBounds());
        EventOptions.setVisible(false);
        EventsPanel.add(EventOptions);
        
        this.Reports = new ReportOptions(AccMan, EvMan, Hud, EventsPanel.getWidth(), EventsPanel.getHeight());
        
        Reports.setBounds(Reports.getBounds());
        Reports.setVisible(false);
        EventsPanel.add(Reports);
        
        this.AccountInfo = new JTextArea();
        
        AccountInfo.setEditable(false);
        AccountInfo.setLineWrap(true);
        AccountInfo.setWrapStyleWord(true);
        AccountInfo.setForeground(Color.WHITE);
        AccountInfo.setBackground(new Color(23, 47, 161));
        AccountInfo.setFont(new java.awt.Font("Roboto",0,24));
        AccountInfo.setBounds(10, (EventsPanel.getHeight()/2) - 200, EventsPanel.getWidth() - 20, ((EventsPanel.getHeight()/2)-175)*6);
        AccountInfo.setText("""
                            <=== Permisos de cuenta ===>
                            
                            - Las cuentas de contenido pueden revisar los reportes asi como crear y ver eventos.
                            
                            - Las cuentas limitadas solo seran capaces de ver eventos y revisar los reportes.
                            
                            - Las cuentas de administrador son capaces de hacer todo lo anterior mas la administracion de usuarios.
                            """);
        EventsPanel.add(AccountInfo);
    }
    //BTN creador de evento
    private void setEvCreBTN(){
        EventCreator = new JButton();
        
        EventCreator.setBounds(10, (EventsPanel.getHeight()/2)-80, 40, 40);
        EventCreator.setBackground(new Color(46, 70, 188));
        EventCreator.setFocusable(false);
        EventCreator.setBorder(null);
        EventCreator.setIcon(new ImageIcon(getClass().getResource("EventCreation1.png")));
        EventCreator.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                EventCreator.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                EventCreator.setBackground(new Color(26, 48, 155));
            }
        });
        
        EventCreator.addActionListener((ActionEvent e) -> {
            EventCreator.setIcon(new ImageIcon(getClass().getResource("EventCreation2.png")));
            AccountManager.setIcon(new ImageIcon(getClass().getResource("AccountAdmin1.png")));
            Report.setIcon(new ImageIcon(getClass().getResource("Reportes1.png")));
            AccountInfo.setVisible(false);
            EventOptions.setVisible(true);
            Reports.setVisible(false);
            Hud.setActPanel(99);
            Hud.PanelChange = true;
        });
        
        Options.add(EventCreator);
        
    }
    //BTN administrador de cuenta
    private void setAccMan(){
        AccountManager = new JButton();
        
        AccountManager.setBounds(10, (EventsPanel.getHeight()/2)-20, 40, 40);
        AccountManager.setBackground(new Color(46, 70, 188));
        AccountManager.setFocusable(false);
        AccountManager.setBorder(null);
        AccountManager.setIcon(new ImageIcon(getClass().getResource("AccountAdmin1.png")));
        AccountManager.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                AccountManager.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                AccountManager.setBackground(new Color(26, 48, 155));
            }
        });
        
        AccountManager.addActionListener((ActionEvent e) -> {
            EventCreator.setIcon(new ImageIcon(getClass().getResource("EventCreation1.png")));
            Report.setIcon(new ImageIcon(getClass().getResource("Reportes1.png")));
            if (AccMan.getCurrentAcc().getAccType() == 0){
                AccountManager.setIcon(new ImageIcon(getClass().getResource("AccountAdmin2.png")));
                EventOptions.setVisible(false);
                AccountInfo.setVisible(true);
                Reports.setVisible(false);

                Hud.setActPanel(2);
                Hud.PanelChange = true;
            } else JOptionPane.showMessageDialog(this, "Su cuenta no tiene permitido administrar usuarios");
        });
        
        Options.add(AccountManager);
        
    }
    //BTN reporte
    private void setReport(){
        Report = new JButton();
        
        Report.setBounds(10, (EventsPanel.getHeight()/2)+40, 40, 40);
        Report.setBackground(new Color(46, 70, 188));
        Report.setFocusable(false);
        Report.setBorder(null);
        Report.setIcon(new ImageIcon(getClass().getResource("Reportes1.png")));
        Report.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                Report.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                Report.setBackground(new Color(26, 48, 155));
            }
        });
        
        Report.addActionListener((ActionEvent e) -> {
            EventCreator.setIcon(new ImageIcon(getClass().getResource("EventCreation1.png")));
            AccountManager.setIcon(new ImageIcon(getClass().getResource("AccountAdmin1.png")));
            Report.setIcon(new ImageIcon(getClass().getResource("Reportes2.png")));
       
            EventOptions.setVisible(false);
            AccountInfo.setVisible(false);
            Reports.setVisible(true);

            Hud.setActPanel(99);
            Hud.PanelChange = true;
        });
        
        Options.add(Report);
    }
    //BTN cerrar sesion
    private void setLogOut(){
        JButton LogOut = new JButton();
        
        LogOut.setBounds(EventsPanel.getX() + 10, FrameHeight - 100, EventsPanel.getWidth() -20, 50);
        LogOut.setFont(new java.awt.Font("Roboto",0,24));
        LogOut.setBackground(new Color(58, 89, 234));
        LogOut.setForeground(Color.WHITE);
        LogOut.setText("Cerrar Sesión");
        LogOut.setFocusable(false);
        LogOut.setBorder(null);
        LogOut.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                LogOut.setBackground(new Color(58, 89, 234));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                LogOut.setBackground(new Color(26, 48, 155));
            }
        });
        
        LogOut.addActionListener((ActionEvent e) -> {
            EventOptions.setVisible(false);
            AccountInfo.setVisible(true);
            Reports.setVisible(false);
            AccMan.getAccount(true, "", "", 0);
            Hud.setActPanel(0);
            Hud.PanelChange = true;
        });
        
        Options.add(LogOut);
    }
    //<-- Swing Elements -->
    private JButton AccountManager, Report, EventCreator;
    private JPanel Options, EventsPanel;
    private EventOptions EventOptions;
    private ReportOptions Reports;
    private JTextArea AccountInfo;
    //<-- Swing Elements -->
}