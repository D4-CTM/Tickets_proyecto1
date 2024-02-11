package Frame.OptionPanels;

import Frame.HudBar;
import javax.swing.JButton;
import AccManager.AccManager;
import EventManager.EventManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class EventOptions extends javax.swing.JPanel{
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    
    public EventOptions(AccManager AccMan, EventManager EvMan, HudBar Hud, int Width, int Height){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.setLayout(null);
        this.setBackground(new Color(23, 47, 161));
        this.setBounds(0, 0,Width,Height);
        
        setRemoveEvent();
        setWatchEvent();
        setEditEvent();
        setCreateBTN();
        Description();
    }
    //Nombre/Descripcion del panel
    private void Description(){
        JLabel ReportsTXT = new JLabel("<=== Administrar eventos ===>");
        
        ReportsTXT.setBounds(10, (getHeight()/2)-225, getWidth() - 20, 50);
        ReportsTXT.setFont(new java.awt.Font("Roboto",0,24));
        ReportsTXT.setForeground(Color.WHITE);
        add(ReportsTXT);
    }
    //Crear boton de "Creacion de evento"
    private void setCreateBTN(){
        JButton CreateEvent = new JButton();
        
        CreateEvent.setBounds(10, (getHeight()/2) - 150, getWidth() - 20, 50);
        CreateEvent.setFont(new java.awt.Font("Berlin Sans FB",0,24));
        CreateEvent.setBackground(new Color(46, 70, 188));
        CreateEvent.setForeground(Color.WHITE);
        CreateEvent.setFocusable(false);
        CreateEvent.setBorder(null);
        CreateEvent.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                CreateEvent.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                CreateEvent.setBackground(new Color(26, 48, 155));
            }
        });
        
        CreateEvent.addActionListener((ActionEvent e) -> {
            if (AccMan.getCurrentAcc().getAccType() != 2){
                Hud.setActPanel(3);
                Hud.PanelChange = true;
            } else JOptionPane.showMessageDialog(null, "¡Esta cuenta no tiene acceso a la creación de eventos!");
        });
        CreateEvent.setText("Crear evento");
        add(CreateEvent);
    }
    //Create boton de eliminar evento
    private void setRemoveEvent(){
        JButton RemoveEvent = new JButton();
        
        RemoveEvent.setBounds(10, (getHeight()/2) - 75, getWidth() - 20, 50);
        RemoveEvent.setFont(new java.awt.Font("Berlin Sans FB",0,24));
        RemoveEvent.setBackground(new Color(46, 70, 188));
        RemoveEvent.setForeground(Color.WHITE);
        RemoveEvent.setFocusable(false);
        RemoveEvent.setBorder(null);
        RemoveEvent.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                RemoveEvent.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                RemoveEvent.setBackground(new Color(26, 48, 155));
            }
        });
        
        RemoveEvent.addActionListener((ActionEvent e) -> {
            Hud.setActPanel(99);
            Hud.PanelChange = true;
            
            if (AccMan.getCurrentAcc().getAccType() != 2){
                String Codice = JOptionPane.showInputDialog(null, "Ingrese el codigo del evento que desea eliminar");
                try {
                    if (!Codice.isEmpty()){
                        if (AccMan.getCurrentAcc().EventCreatedBy(Codice)){
                            if (AccMan.getCurrentAcc().EventCreatedBy(Codice)){
                                if (EvMan.isCodeValid(Codice, 0)){
                                    if (!EvMan.isEventCancelled(Codice, 0)){
                                        if (isOnedayBefore(Codice) && Codice.charAt(0) != 'R'){
                                            if (JOptionPane.showInternalConfirmDialog(null, 
                                                    "Si desea eliminar este evento debera abonar: "+EvMan.getEvent(Codice, 0).getStadiumCost()/2,
                                                    "ELIMINAR EVENTO", 
                                                    JOptionPane.YES_NO_CANCEL_OPTION, 
                                                    JOptionPane.INFORMATION_MESSAGE) == 0){
                                                EvMan.getEvent(Codice, 0).setCancelledPrice();
                                                JOptionPane.showMessageDialog(null, EvMan.RemoveEvent(Codice, 0)?"El evento ha sido eliminado exitosamente":"El evento que intenta eliminar no existe");
                                            }
                                        } else if (JOptionPane.showInternalConfirmDialog(null, 
                                                    "Si desea eliminar este evento debera abonar: "+EvMan.getEvent(Codice, 0).getStadiumCost()/4,                                                    "ELIMINAR EVENTO", 
                                                    JOptionPane.YES_NO_CANCEL_OPTION, 
                                                    JOptionPane.INFORMATION_MESSAGE) == 0){
                                            JOptionPane.showMessageDialog(null, EvMan.RemoveEvent(Codice, 0)?"El evento ha sido eliminado exitosamente":"El evento que intenta eliminar no existe");
                                        }
                                    } else JOptionPane.showMessageDialog(null, "El evento que busca ya ha sido eliminado");
                                } else JOptionPane.showMessageDialog(null, "El evento que busca eliminar no existe");
                            } else JOptionPane.showMessageDialog(null, "Solo el creador del evento puede eliminarlo");
                        } else JOptionPane.showMessageDialog(null, "¡Solo el creador del evento puede eliminarlo!");
                    } else JOptionPane.showMessageDialog(null, "Por favor ingrese un codigo correcto");
                } catch (Exception ex){ }
            } else JOptionPane.showMessageDialog(null, "¡Esta cuenta no tiene permitido eliminar eventos!");
        });
        RemoveEvent.setText("Eliminar evento");
        add(RemoveEvent);
    }
    //Se consigue si la fecha es un dia antes del dia del evento
    private boolean isOnedayBefore(String Codice){
        Date FechaEvento = EvMan.getEvent(Codice, 0).getRealizationDate();
        Calendar Hoy = Calendar.getInstance();
        if (FechaEvento.getMonth() == Hoy.getTime().getMonth() && FechaEvento.getYear() == Hoy.getTime().getYear()){
            if ((FechaEvento.getDate() - 1) == Hoy.getTime().getDate()) return true;
        }
        return false;
    }
    //Create edit event button
    private void setEditEvent(){
        JButton EditBTN = new JButton();
        
        EditBTN.setBounds(10, (getHeight()/2) + 25, getWidth() - 20, 50);
        EditBTN.setFont(new java.awt.Font("Berlin Sans FB",0,24));
        EditBTN.setBackground(new Color(46, 70, 188));
        EditBTN.setForeground(Color.WHITE);
        EditBTN.setFocusable(false);
        EditBTN.setBorder(null);
        EditBTN.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                EditBTN.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                EditBTN.setBackground(new Color(26, 48, 155));
            }
        });
        
        EditBTN.addActionListener((ActionEvent e) -> {
            if (AccMan.getCurrentAcc().getAccType() != 2){
                String Codice = JOptionPane.showInputDialog(null, "Ingrese el codigo del evento que desea editar");
                try {
                    if (!Codice.isEmpty()){
                        if (EvMan.isCodeValid(Codice, 0)){
                            if (!EvMan.isEventCancelled(Codice, 0)){
                                    Hud.setActPanel(4);
                                    Hud.setCodice(Codice);
                                    Hud.PanelChange = true;
                            } else JOptionPane.showMessageDialog(null, "El evento que busca ha sido eliminado");
                        } else JOptionPane.showMessageDialog(null, "El evento que busca no existe");
                    } else JOptionPane.showMessageDialog(null, "Por favor ingrese un codigo correcto");
                } catch (Exception ex){ }
            } else JOptionPane.showMessageDialog(null, "¡Esta cuenta no tiene acceso a la edición de eventos!");
        });
        EditBTN.setText("Editar evento");
        add(EditBTN);
    }
    //boton de 'ver evento'
    private void setWatchEvent(){
        JButton VerEvento = new JButton();
        
        VerEvento.setBounds(10, (getHeight()/2) + 100, getWidth() - 20, 50);
        VerEvento.setFont(new java.awt.Font("Berlin Sans FB",0,24));
        VerEvento.setBackground(new Color(46, 70, 188));
        VerEvento.setForeground(Color.WHITE);
        VerEvento.setFocusable(false);
        VerEvento.setBorder(null);
        VerEvento.addMouseListener(new MouseAdapter(){ 
            @Override
            public void mouseExited(MouseEvent e){
                VerEvento.setBackground(new Color(46, 70, 188));
            }
            @Override
            public void mouseEntered(MouseEvent e){
                VerEvento.setBackground(new Color(26, 48, 155));
            }
        });
        
        VerEvento.addActionListener((ActionEvent e) -> {
            String Codice = JOptionPane.showInputDialog(null, "Ingrese el codigo del evento que desea editar");
            try {
                if (!Codice.isEmpty()){
                    if (EvMan.isCodeValid(Codice, 0)){
                        if (!EvMan.isEventCancelled(Codice, 0)){
                            Hud.setActPanel(5);
                            Hud.setCodice(Codice);
                            Hud.PanelChange = true;
                        } else JOptionPane.showMessageDialog(null, "El evento que busca ha sido eliminado");
                    } else JOptionPane.showMessageDialog(null, "El evento que busca no existe");
                } else JOptionPane.showMessageDialog(null, "Por favor ingrese un codigo correcto");
            } catch (Exception ex){ }
        });
        VerEvento.setText("Ver evento");
        add(VerEvento);
    }
    
}
