package EventManager;

import java.util.ArrayList;
import java.util.Date;

public final class MusicalEvent extends Event{
    private ArrayList<String> Staff;
    private String MusicGenre;
    
    public MusicalEvent(String Title, String Code, String Desc, String MusicGenre, ArrayList<String> Staff, int Attendees, double StadiumCost, Date RealizationDate) {
        super(Title, Code, Desc, Attendees, StadiumCost + (StadiumCost*0.3), RealizationDate);
        this.MusicGenre = MusicGenre;
        this.Staff = new ArrayList();
        setStaff(Staff);
    }
    
    public void EditData(String Title, String Code, String Desc, String MusicGenre, ArrayList<String> Staff, int Attendees, double StadiumCost, Date RealizationDate){
        super.EditData(Title, Code, Desc, Attendees, StadiumCost, RealizationDate);
        this.MusicGenre = MusicGenre;
        setStaff(Staff);
    }
    // -- SETTERS --
    public void setStaff(ArrayList<String> Staff){
        this.Staff.clear();
        for (String neoStaff : Staff){
            this.Staff.add(neoStaff);
        }
    }
    // -- SETTERS --
    // -- GETTERS --
    public ArrayList<String> getStaff(){
        return this.Staff;
    }
    
    public String getMusicGenre(){
        return this.MusicGenre;
    }
    // -- GETTERS --
}
