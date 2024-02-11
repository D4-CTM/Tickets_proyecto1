package EventManager;

import java.util.Date;

public abstract class Event {
    protected String Title, Code, Desc;
    protected Date RealizationDate;
    protected double StadiumCost;
    protected boolean Canceled;
    protected int Attendees;

    public Event(String Title, String Code, String Desc, int Attendees, double StadiumCost, Date RealizationDate){
        this.RealizationDate = RealizationDate;
        this.StadiumCost = StadiumCost;
        this.Attendees = Attendees;
        this.Title = Title;
        this.Code = Code;
        this.Desc = Desc;
        Canceled = false;
    }
    //Se edita la data, luego se agnadiran mas cosas, de ahi el protected
    protected void EditData(String Title, String Code, String Desc, int Attendees, double StadiumCost, Date RealizationDate){
        this.RealizationDate = RealizationDate;
        this.StadiumCost = StadiumCost;
        this.Attendees = Attendees;
        this.Title = Title;
        this.Code = Code;
        this.Desc = Desc;
    }
    // -- SETTERS --
    public void setCancelledPrice(){
        this.Canceled = true;
        this.StadiumCost = this.StadiumCost/2;
    }
    
    public void CancelEvent(){
        this.Canceled = true;
        this.StadiumCost = StadiumCost/4;
    }
    // -- SETTERS --
    // -- GETTERS --
    public Date getRealizationDate(){
        return RealizationDate;
    }
    
    public double getStadiumCost(){
        return StadiumCost;
    }
    
    public boolean isCancelled(){
        return Canceled;
    }
    
    public String getTitle(){
        return Title;
    }
    
    public String getCode(){
        return Code;
    }
    
    public String getDesc(){
        return Desc;
    }
    
    public int getAttendees(){
        return Attendees;
    }
    // -- GETTERS --
}