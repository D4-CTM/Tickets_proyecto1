package Frame.OptionPanels;

import AccManager.AccManager;
import EventManager.EventManager;
import Frame.HudBar;
import Frame.OptionPanels.Reportes.ReportsPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ReportOptions extends javax.swing.JPanel{
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    
    public ReportOptions(AccManager AccMan, EventManager EvMan, HudBar Hud, int Width, int Height){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.setLayout(null);
        this.setBackground(new Color(23, 47, 161));
        this.setBounds(0, 0,Width,Height);

        setListarEventosRealizadosBTN();
        setListaEventosCanceladosBTN();
        setListaEventosFuturosBTN();
        setBusquedaxFechaBTN();
        setPerfilBTN();
        Description();
        setReportes();
    }
    
    private void Description(){
        JLabel ReportsTXT = new JLabel();
        
        ReportsTXT.setIcon(new ImageIcon(getClass().getResource("ReportsIcon.png")));
        ReportsTXT.setBounds(10, (getHeight()/2)-250, getWidth() - 20, 50);
        ReportsTXT.setFont(new java.awt.Font("Roboto",0,24));
        ReportsTXT.setForeground(Color.WHITE);
        add(ReportsTXT);
    }
    
    private void setListarEventosRealizadosBTN(){
        JButton EventosRealizados = new JButton();
        
        EventosRealizados.setBounds(10, (getHeight()/2) - 175, getWidth() - 20, 50);
        EventosRealizados.setFont(new java.awt.Font("Berlin Sans FB",0,24));
        EventosRealizados.setBackground(new Color(46, 70, 188));
        EventosRealizados.setForeground(Color.WHITE);
        EventosRealizados.setFocusable(false);
        EventosRealizados.setBorder(null);
        EventosRealizados.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                EventosRealizados.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                EventosRealizados.setBackground(new Color(26, 48, 155));
            }
        });
        
        EventosRealizados.addActionListener((ActionEvent e) -> {
            if (EvMan.areEventsDone()){
                Hud.setActPanel(6);
                Hud.PanelChange = true;
            } else JOptionPane.showMessageDialog(null, "¡No han ocurrido eventos!");
        });
        EventosRealizados.setText("Ver eventos realizados");
        add(EventosRealizados);
    }
    
    private void setListaEventosFuturosBTN(){
        JButton EventosFuturos = new JButton();
        
        EventosFuturos.setBounds(10, (getHeight()/2) - 100, getWidth() - 20, 50);
        EventosFuturos.setFont(new java.awt.Font("Berlin Sans FB",0,24));
        EventosFuturos.setBackground(new Color(46, 70, 188));
        EventosFuturos.setForeground(Color.WHITE);
        EventosFuturos.setFocusable(false);
        EventosFuturos.setBorder(null);
        EventosFuturos.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                EventosFuturos.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                EventosFuturos.setBackground(new Color(26, 48, 155));
            }
        });
        
        EventosFuturos.addActionListener((ActionEvent e) -> {
            if (EvMan.areEventsPlaned()){
                Hud.setActPanel(7);
                Hud.PanelChange = true;
            } else JOptionPane.showMessageDialog(null, "¡No se han creado eventos!");
        });
        EventosFuturos.setText("Ver eventos futuros");
        add(EventosFuturos);
    }
    
    private void setListaEventosCanceladosBTN(){
        JButton EventosCancelados = new JButton();
        
        EventosCancelados.setBounds(10, (getHeight()/2) - 25, getWidth() - 20, 50);
        EventosCancelados.setFont(new java.awt.Font("Berlin Sans FB",0,24));
        EventosCancelados.setBackground(new Color(46, 70, 188));
        EventosCancelados.setForeground(Color.WHITE);
        EventosCancelados.setFocusable(false);
        EventosCancelados.setBorder(null);
        EventosCancelados.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                EventosCancelados.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                EventosCancelados.setBackground(new Color(26, 48, 155));
            }
        });
        
        EventosCancelados.addActionListener((ActionEvent e) -> {
            if (EvMan.areCanceledEvents()){
                Hud.setActPanel(8);
                Hud.PanelChange = true;
            } else JOptionPane.showMessageDialog(null, "¡No se ha cancelado evento alguno!");
        });
        EventosCancelados.setText("Ver eventos cancelados");
        add(EventosCancelados);
    }

    private void setBusquedaxFechaBTN(){
        JButton BusqudaxFecha = new JButton();
        
        BusqudaxFecha.setBounds(10, (getHeight()/2) + 50, getWidth() - 20, 50);
        BusqudaxFecha.setFont(new java.awt.Font("Berlin Sans FB",0,24));
        BusqudaxFecha.setBackground(new Color(46, 70, 188));
        BusqudaxFecha.setForeground(Color.WHITE);
        BusqudaxFecha.setFocusable(false);
        BusqudaxFecha.setBorder(null);
        BusqudaxFecha.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                BusqudaxFecha.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                BusqudaxFecha.setBackground(new Color(26, 48, 155));
            }
        });
        
        BusqudaxFecha.addActionListener((ActionEvent e) -> {
            if (EvMan.areEventsDone() || EvMan.areEventsPlaned()){
                Hud.setActPanel(9);
                Hud.PanelChange = true;
            } else JOptionPane.showMessageDialog(null, "¡No se han creado eventos!");
        });
        BusqudaxFecha.setText("Dinero generado");
        add(BusqudaxFecha);
    }
    
    private void setPerfilBTN(){
        JButton EventosDe = new JButton();
        
        EventosDe.setBounds(10, (getHeight()/2) + 125, getWidth() - 20, 50);
        EventosDe.setFont(new java.awt.Font("Berlin Sans FB",0,24));
        EventosDe.setBackground(new Color(46, 70, 188));
        EventosDe.setForeground(Color.WHITE);
        EventosDe.setFocusable(false);
        EventosDe.setBorder(null);
        EventosDe.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                EventosDe.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                EventosDe.setBackground(new Color(26, 48, 155));
            }
        });
        
        EventosDe.addActionListener((ActionEvent e) -> {
            if (AccMan.getCurrentAcc().getAccType() != 2){
                if (EvMan.areEventsCreatedByUser(AccMan.getCurrentAcc().getEventsCreated())){
                    Hud.setActPanel(10);
                    Hud.PanelChange = true;
                } else JOptionPane.showMessageDialog(null, "¡"+AccMan.getCurrentAcc().getName()+" no ha creado algun evento!");
            } else JOptionPane.showMessageDialog(null, "¡"+AccMan.getCurrentAcc().getName()+" no ha creado algun evento!");
        });
        EventosDe.setText("Eventos realizados por usuario");
        add(EventosDe);
    }
    //Se setea el panel donde se vuelve visible la wea de historia
    private void setReportes(){
        Reportes = new ReportsPanel(AccMan, EvMan, Hud);
        
        Reportes.setBounds(Reportes.getBounds());
        add(Reportes);
    }
    // -- SWING ELEMENTS --
    private ReportsPanel Reportes;
    // -- SWING ELEMENTS --
}
