package EventManager;

import java.util.Date;

public final class ReligousEvent extends Event{
    private int AlmasConvertidas;

    public ReligousEvent(String Title, String Code, String Desc, int AlmasConvertidas, int Attendees, double StadiumCost, Date RealizationDate) {
        super(Title, Code, Desc, Attendees, StadiumCost + 2000, RealizationDate);
        this.AlmasConvertidas = AlmasConvertidas;
    }
    
    public void EditData(String Title, String Code, String Desc, int AlmasConvertidas, int Attendees, double StadiumCost, Date RealizationDate){
        super.EditData(Title, Code, Desc, Attendees, StadiumCost, RealizationDate);
        this.AlmasConvertidas = AlmasConvertidas;
    }
    
    // -- GETTERS --
    public int getAlmasConvertidas(){
        return this.AlmasConvertidas;
    }
    // -- GETTERS --
}
