package Frame;

import javax.swing.JFrame;
import AccManager.AccManager;
import EventManager.EventManager;

public class FrameStuff extends JFrame {
    
    public FrameStuff(AccManager AccMan, EventManager EvMan, HudBar Hud){
        MainPanel GreatPanel = new MainPanel(AccMan, EvMan, Hud);
        add(GreatPanel);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(3);
        setUndecorated(true);
        setResizable(false);
        
        GreatPanel.startPanelManager();
    }
    
}
