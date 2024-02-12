package Frame;

import Frame.OptionPanels.LogInPanel;
import Frame.OptionPanels.MenuPanel;
import javax.swing.JPanel;
import AccManager.AccManager;
import EventManager.EventManager;
import Frame.ActionPanels.AccountCreation;
import Frame.ActionPanels.EventsAdministration;
import Frame.OptionPanels.Reportes.ReportsPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MainPanel extends javax.swing.JPanel implements Runnable{
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    
    private Thread PanelManager;
    public MainPanel(AccManager AccMan, EventManager EvMan, HudBar Hud){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.setLayout(null);
        
        setHudBar();
        setMainPanel();
        setMenuPanels();
        
        Hud.startTimer();
    }
    //Setea la barra que se puede observar en la cima del panel
    private void setHudBar(){
        Hud.setBounds(0,0,java.awt.Toolkit.getDefaultToolkit().getScreenSize().width, 50);
        add(Hud);
    }
    
    //El panel donde se despliega todo
    private void setMainPanel(){
        Canva = new JPanel();
        
        Canva.setBounds(0, 50, java.awt.Toolkit.getDefaultToolkit().getScreenSize().width, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 50);
        Canva.setBackground(java.awt.Color.lightGray);
        Canva.setLayout(null);
        add(Canva);
    }

    // -- Hilo encargado (y sus funciones relacionadas encargadas) de mostrar paneles --
    public void startPanelManager(){
        PanelManager = new Thread(this);
        PanelManager.start();
    }
    
    private void setVisible(){
        LogIn.setVisible(Hud.LogIn);
        MenuPanel.Reveal(Hud.MenuPanel);
        AccCreate.Reveal(Hud.AccCreation);
        EventAdmin.Reveal(Hud.EventAdmin);
        Reportes.Reveal(Hud.Reportes);
    }
    
    @Override
    public void run() {
        while (this != null){
            if (Hud.PanelChange){
                Hud.PanelChange = false;
                Hud.setPanels();
                setVisible();                
            }
            this.repaint();
        }
    }
    // -- Hilo encargado (y sus funciones relacionadas encargadas) de mostrar paneles --
    
    //Se setean todos los paneles usados durante el proyecto
    private void setMenuPanels(){
        //Panel de log in, basicamente el log in de inicio
        LogIn = new LogInPanel(AccMan, EvMan, Hud);

        LogIn.setBounds(LogIn.getBounds());
        Canva.add(LogIn);
        //Panel donde se elige la accion a realizar
        MenuPanel = new MenuPanel(AccMan, EvMan, Hud);

        MenuPanel.setBounds(MenuPanel.getBounds());
        Canva.add(MenuPanel);
        //Panel donde se crea el usuario
        AccCreate = new AccountCreation(AccMan, EvMan, Hud);
        
        AccCreate.setBounds(AccCreate.getBounds());
        MenuPanel.add(AccCreate);
        //Panel usado para crear y administrar los eventos
        EventAdmin = new EventsAdministration(AccMan, EvMan, Hud);
        
        EventAdmin.setBounds(AccCreate.getBounds());
        MenuPanel.add(EventAdmin);
        //Panel donde se muestra la info de reportes
        Reportes = new ReportsPanel(AccMan, EvMan, Hud);
        
        Reportes.setBounds(Reportes.getBounds());
        MenuPanel.add(Reportes);
    }
    // <-- Swing Elements -->
    private JPanel Canva;
    private LogInPanel LogIn;
    private MenuPanel MenuPanel;
    private ReportsPanel Reportes;
    private AccountCreation AccCreate;
    private EventsAdministration EventAdmin;
    // <-- Swing Elements -->
}
