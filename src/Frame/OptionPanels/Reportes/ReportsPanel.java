package Frame.OptionPanels.Reportes;

import AccManager.AccManager;
import EventManager.EventManager;
import Frame.HudBar;

public class ReportsPanel extends javax.swing.JPanel{
    //Tecnicamente constantes
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    //Event creation data
    //Extras usados en el panel
    private final int PanelWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int PanelHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 50;
    
    public ReportsPanel(AccManager AccMan, EventManager EvMan, HudBar Hud){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.setLayout(null);
        this.setBackground(null);
        this.setBounds((PanelWidth/3), 0, PanelWidth - (PanelWidth/3), PanelHeight);
        
        setInfo();
    }
    //Se setean los distintos paneles que despliegan la informacion requerida
    private void setInfo(){
        Realizados = new ReportesDeEventos(AccMan, EvMan, Hud, 50, 50, getWidth() - 100, getHeight() - 100);
        
        Realizados.setBackground(java.awt.Color.LIGHT_GRAY);
        Realizados.setBounds(Realizados.getBounds());
        add(Realizados);
        
        Buscar = new BusquedaPorFecha(AccMan, EvMan, Hud, 50, 50, getWidth() - 100, getHeight() - 100);
        
        Buscar.setBackground(java.awt.Color.LIGHT_GRAY);
        Buscar.setBounds(Buscar.getBounds());
        add(Buscar);
    }
    //Destaca los paneles que deben desplegarse y tal
    public final void Reveal(boolean Reveal){
        this.setVisible(Reveal || Hud.getActPanel() == 9);
        Buscar.Reveal(Hud.getActPanel() == 9);
        Realizados.Reveal(Reveal);
    }
    // -- SWING ELEMENTS --
    private ReportesDeEventos Realizados;
    private BusquedaPorFecha Buscar;
    // -- SWING ELEMENTS
}
