package tickets;

import AccManager.AccManager;
import EventManager.EventManager;
import Frame.FrameStuff;
import Frame.HudBar;

public class Tickets {

    public static void main(String[] args) {
        EventManager EvMan = new EventManager();
        AccManager AccMan = new AccManager();
        HudBar Hud = new HudBar(AccMan);
        
        new FrameStuff(AccMan, EvMan, Hud).setVisible(true);
    }
    
}
