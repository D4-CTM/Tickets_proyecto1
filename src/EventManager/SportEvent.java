package EventManager;

import java.util.ArrayList;
import java.util.Date;

public final class SportEvent extends Event {
    private final ArrayList<String> Players1, Players2;
    private String Sport, TeamName1, TeamName2;

    public SportEvent(String Title, String Code, String Desc, String Sport, String TeamName1, String TeamName2, int Attendees, double StadiumCost, Date RealizationDate) {
        super(Title, Code, Desc, Attendees, StadiumCost, RealizationDate);
        Players1 = new ArrayList();
        Players2 = new ArrayList();
        this.TeamName1 = TeamName1;
        this.TeamName2 = TeamName2;
        this.Sport = Sport;
    }

    //como fue dicho antes, se edita la data general, ahora tambien se edita la info del evento
    public void EditData(String Title, String Code, String Desc, String Sport, String TeamName1, String TeamName2, int Attendees, double StadiumCost, Date RealizationDate){
        super.EditData(Title, Code, Desc, Attendees, StadiumCost, RealizationDate);
        this.TeamName1 = TeamName1;
        this.TeamName2 = TeamName2;
        this.Sport = Sport;
    }
    // -- SETTERS --
    public void setTeam1(ArrayList<String> Team1){
        Players1.clear();
        for (String Player : Team1){
            Players1.add(Player);
        }
    }
    
    public void setTeam2(ArrayList<String> Team1){
        Players2.clear();
        for (String Player : Team1){
            Players2.add(Player);
        }
    }
    // -- SETTERS --
    // -- GETTERS --
    public ArrayList<String> getPlayers(int which){
        return (which == 0)?Players1:Players2;
    }
    
    public String getTeamName(int which){
        return (which == 0)?TeamName1:TeamName2;
    }
    
    public String getSport(){
        return this.Sport;
    }
    // -- GETTERS --
}
