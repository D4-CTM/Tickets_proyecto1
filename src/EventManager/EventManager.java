package EventManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

public final class EventManager {
    private double GananciaDeportivas, GananciasReligiosas, GananciasMusicales;
    private int CantidadDeportiva, CantidadReligiosa, CantidadMusical;
    private double GainsR = 0, GainsD = 0, GainsM = 0;
    private int CantR = 0, CantD = 0, CantM = 0;
            
    private final ArrayList<Event> EventManager;
    
    public EventManager(){
        EventManager = new ArrayList();
    }
    //Usado durante la creacion del codigo
    public boolean isCodeValid(String Code, int Pos){
        if (Pos < EventManager.size()){
            if (EventManager.get(Pos).getCode().equals(Code)){
                return true;
            } else return isCodeValid(Code,Pos + 1);
        }
        return false;
    }
    //Usado, como el nombre indica, para eliminar eventos del ArrayList
    public boolean RemoveEvent(String Code, int Pos){
        if (Pos < EventManager.size()){
            if (EventManager.get(Pos).getCode().equals(Code)){
                EventManager.get(Pos).CancelEvent();
                return true;
            } return RemoveEvent(Code, Pos + 1);
        }
        return false;
    }
    public boolean isEventCancelled(String Code, int Pos){
        if (Pos < EventManager.size()){
            if (EventManager.get(Pos).getCode().equals(Code)){
                return EventManager.get(Pos).isCancelled();
            } return isEventCancelled(Code,Pos + 1);
        }
        return false;
    }
    // -- EVENTOS DEPORTIVOS --
    public void AddSportEvent(String Title, String Code, String Desc, String Sport, String TeamName1, String TeamName2, int Attendees, double StadiumCost, Date RealizationDate) {
        EventManager.add(new SportEvent(Title, Code, Desc, Sport, TeamName1, TeamName2, Attendees, StadiumCost, RealizationDate));
    }
    
    public SportEvent getSportEvent(String Code, int Pos){
        if (Pos < EventManager.size()){
            if (EventManager.get(Pos).getCode().equals(Code)){
                return (SportEvent) EventManager.get(Pos);
            } else return getSportEvent(Code, Pos+1);
        }
        return null;
    }
    // -- EVENTOS DEPORTIVOS --
    // -- EVENTOS RELIGIOSOS --
    public void AddReligousEvent(String Title, String Code, String Desc, int AlmasConvertidas, int Attendees, double StadiumCost, Date RealizationDate) {
        EventManager.add(new ReligousEvent(Title, Code, Desc, AlmasConvertidas, Attendees, StadiumCost, RealizationDate));
    }
    
    public ReligousEvent getReligousEvent(String Code, int Pos){
        if (Pos < EventManager.size()){
            if (EventManager.get(Pos).getCode().equals(Code)){
                return (ReligousEvent) EventManager.get(Pos);
            } else return getReligousEvent(Code, Pos+1);
        }
        return null;
    }
    // -- EVENTOS RELIGIOSOS --
    // -- EVENTOS MUSICALES --
    public void AddMusicalEvent(String Title, String Code, String Desc, String MusicGenre, ArrayList<String> Staff, int Attendees, double StadiumCost, Date RealizationDate) {
        EventManager.add(new MusicalEvent(Title, Code, Desc, MusicGenre, Staff, Attendees, StadiumCost, RealizationDate));
    }
    
    public MusicalEvent getMusicalEvent(String Code, int Pos){
        if (Pos < EventManager.size()){
            if (EventManager.get(Pos).getCode().equals(Code)){
                return (MusicalEvent) EventManager.get(Pos);
            } else return getMusicalEvent(Code, Pos+1);
        }
        return null;
    }
    // -- EVENTOS MUSICALES --
    // -- EVENTS BASIC DATA --
    public Event getEvent(String Code, int Pos){
        if (Pos < EventManager.size()){
            if (EventManager.get(Pos).getCode().equals(Code)){
                return EventManager.get(Pos);
            } else return getEvent(Code, Pos+1);
        }
        return null;
    }
    
    public boolean areEventsDone(){
        for (Event Evento : EventManager){
            if (Evento.getRealizationDate().before(Calendar.getInstance().getTime()))
                return true;
        }
        return false;
    }
    
    public boolean areEventsPlaned(){
        for (Event Evento : EventManager){
            if (Evento.getRealizationDate().after(Calendar.getInstance().getTime()))
                return true;
        }
        return false;
    }
    
    public boolean areCanceledEvents(){
        for (Event Evento : EventManager){
            if (Evento.Canceled)
                return true;
        }
        return false;
    }
    
    public boolean areEventsCreatedByUser(ArrayList<String> Codigos){
        try {
            for (Event Evento : EventManager){
                if (Evento.getCode().equals(Codigos.get(0)))
                    return true;
            }
        } catch (Exception Ex){
        }
        return false;
    }
    // -- EVENTS BASIC DATA --
    // -- FUNCIONE USADA EN EL REPORTE DE EVENTOS REALIZADOS --
    public final String getEventosRealizados(String Eventi, int Pos){
        if (Pos < EventManager.size()){
            if (EventManager.get(Pos).getRealizationDate().before(Calendar.getInstance().getTime())){
                String Type = "";
                switch (EventManager.get(Pos).getCode().charAt(0)){
                    case 'R' -> {
                        GainsR += EventManager.get(Pos).StadiumCost;
                        Type = "Religioso";
                        CantR++;

                        setGananciasReligiosas(GainsR);
                        setCantidadReligiosa(CantR);
                    }
                    case 'D' -> {
                        GainsD += EventManager.get(Pos).StadiumCost;
                        Type = "Deportivo";
                        CantD++;

                        setGananciasDeportivas(GainsD);
                        setCantidadDeportiva(CantD);
                    }
                    case 'M' -> {
                        GainsM += EventManager.get(Pos).StadiumCost;
                        Type = "Musical";
                        CantM++;

                        setGananciasMusicales(GainsM);
                        setCantidadMusical(CantM);
                    }
                }
                String FechaSimp = String.valueOf(EventManager.get(Pos).getRealizationDate().getDay()) + "/"+ String.valueOf(EventManager.get(Pos).getRealizationDate().getMonth()) + "/" + String.valueOf(EventManager.get(Pos).getRealizationDate().getYear() - 100);
                Eventi += EventManager.get(Pos).getCode() + " - " + Type + " - " + EventManager.get(Pos).getTitle() + " - " + FechaSimp + " - " + EventManager.get(Pos).getStadiumCost() + "\n";
            }
            return getEventosRealizados(Eventi, Pos + 1);
        }
        GainsR = 0; GainsD = 0; GainsM = 0;
        CantR = 0; CantD = 0; CantM = 0;
        return Eventi;
    }
    // -- FUNCION USADA EN EL REPORTE DE EVENTOS FUTUROS --
    public final String getEventosFuturos(String Eventi, int Pos){
        if (Pos < EventManager.size()){
            if (EventManager.get(Pos).getRealizationDate().after(Calendar.getInstance().getTime())){
                String Type = "";
                switch (EventManager.get(Pos).getCode().charAt(0)){
                    case 'R' -> {
                        GainsR += EventManager.get(Pos).StadiumCost;
                        Type = "Religioso";
                        CantR++;

                        setGananciasReligiosas(GainsR);
                        setCantidadReligiosa(CantR);
                    }
                    case 'D' -> {
                        GainsD += EventManager.get(Pos).StadiumCost;
                        Type = "Deportivo";
                        CantD++;

                        setGananciasDeportivas(GainsD);
                        setCantidadDeportiva(CantD);
                    }
                    case 'M' -> {
                        GainsM += EventManager.get(Pos).StadiumCost;
                        Type = "Musical";
                        CantM++;

                        setGananciasMusicales(GainsM);
                        setCantidadMusical(CantM);
                    }
                }
                String FechaSimp = String.valueOf(EventManager.get(Pos).getRealizationDate().getDay()) + "/"+ String.valueOf(EventManager.get(Pos).getRealizationDate().getMonth()) + "/" + String.valueOf(EventManager.get(Pos).getRealizationDate().getYear() - 100);
                Eventi += EventManager.get(Pos).getCode() + " - " + Type + " - " + EventManager.get(Pos).getTitle() + " - " + FechaSimp + " - " + EventManager.get(Pos).getStadiumCost() + "\n";
            }
            return getEventosFuturos(Eventi, Pos + 1);
        }
        GainsR = 0; GainsD = 0; GainsM = 0;
        CantR = 0; CantD = 0; CantM = 0;
        return Eventi;
    }
    // -- FUNCION USADA EN EL REPORTE DE EVENTOS CANCELADOS --
    public final String getEventosCancelados(String Eventi, int Pos){
        if (Pos < EventManager.size()){
            if (EventManager.get(Pos).Canceled){
                String Type = "";
                switch (EventManager.get(Pos).getCode().charAt(0)){
                    case 'R' -> {
                        GainsR += EventManager.get(Pos).StadiumCost;
                        Type = "Religioso";
                        CantR++;

                        setGananciasReligiosas(GainsR);
                        setCantidadReligiosa(CantR);
                    }
                    case 'D' -> {
                        GainsD += EventManager.get(Pos).StadiumCost;
                        Type = "Deportivo";
                        CantD++;

                        setGananciasDeportivas(GainsD);
                        setCantidadDeportiva(CantD);
                    }
                    case 'M' -> {
                        GainsM += EventManager.get(Pos).StadiumCost;
                        Type = "Musical";
                        CantM++;

                        setGananciasMusicales(GainsM);
                        setCantidadMusical(CantM);
                    }
                }
                String FechaSimp = String.valueOf(EventManager.get(Pos).getRealizationDate().getDay()) + "/"+ String.valueOf(EventManager.get(Pos).getRealizationDate().getMonth()) + "/" + String.valueOf(EventManager.get(Pos).getRealizationDate().getYear() - 100);
                Eventi += EventManager.get(Pos).getCode() + " - " + Type + " - " + EventManager.get(Pos).getTitle() + " - " + FechaSimp + " - " + EventManager.get(Pos).getStadiumCost() + "\n";
            }
            return getEventosCancelados(Eventi, Pos + 1);
        }
        GainsR = 0; GainsD = 0; GainsM = 0;
        CantR = 0; CantD = 0; CantM = 0;
        return Eventi;
    }
    // -- FUNCION USADA PARA CONSEGUIR LA INFO DE LOS EVENTOS DEPENDIENDO DE LA FECHA --
    public final String getEventFilter(String Eventi, int Pos, Date Min, Date Max){
        if (Pos < EventManager.size()){
            if (EventManager.get(Pos).getRealizationDate().after(Min) && EventManager.get(Pos).getRealizationDate().before(Max)){
                String Type = "";
                switch (EventManager.get(Pos).getCode().charAt(0)){
                    case 'R' -> {
                        GainsR += EventManager.get(Pos).StadiumCost;
                        Type = "Religioso";
                        CantR++;
                    }
                    case 'D' -> {
                        GainsD += EventManager.get(Pos).StadiumCost;
                        Type = "Deportivo";
                        CantD++;
                    }
                    case 'M' -> {
                        GainsM += EventManager.get(Pos).StadiumCost;
                        Type = "Musical";
                        CantM++;
                    }
                }
                String FechaSimp = String.valueOf(EventManager.get(Pos).getRealizationDate().getDay()) + "/"+ String.valueOf(EventManager.get(Pos).getRealizationDate().getMonth()) + "/" + String.valueOf(EventManager.get(Pos).getRealizationDate().getYear() - 100);
                Eventi += EventManager.get(Pos).getCode() + " - " + Type + " - " + EventManager.get(Pos).getTitle() + " - " + FechaSimp + " - " + EventManager.get(Pos).getStadiumCost() + "\n";
            }
            return getEventFilter(Eventi, Pos + 1, Min, Max);
        }
        setGananciasReligiosas(GainsR);
        setCantidadReligiosa(CantR);
        setGananciasDeportivas(GainsD);
        setCantidadDeportiva(CantD);
        setGananciasMusicales(GainsM);
        setCantidadMusical(CantM);
        GainsR = 0; GainsD = 0; GainsM = 0;
        CantR = 0; CantD = 0; CantM = 0;
        return Eventi;
    }
    // -- FUNCION USADA PARA CONSEGUIR LOS EVENTOS CREADOS POR EL USUARIO --
    public final String getEventsCreatedBy(String Eventi, int Pos, ArrayList<String> UserCodes){
        if (Pos < EventManager.size()){
            if (UserCodes.contains(EventManager.get(Pos).getCode())){
                String Type = "";
                switch (EventManager.get(Pos).getCode().charAt(0)){
                    case 'R' -> {
                        GainsR += EventManager.get(Pos).StadiumCost;
                        Type = "Religioso";
                        CantR++;

                        setGananciasReligiosas(GainsR);
                        setCantidadReligiosa(CantR);
                    }
                    case 'D' -> {
                        GainsD += EventManager.get(Pos).StadiumCost;
                        Type = "Deportivo";
                        CantD++;

                        setGananciasDeportivas(GainsD);
                        setCantidadDeportiva(CantD);
                    }
                    case 'M' -> {
                        GainsM += EventManager.get(Pos).StadiumCost;
                        Type = "Musical";
                        CantM++;

                        setGananciasMusicales(GainsM);
                        setCantidadMusical(CantM);
                    }
                }
                String FechaSimp = String.valueOf(EventManager.get(Pos).getRealizationDate().getDay()) + "/"+ String.valueOf(EventManager.get(Pos).getRealizationDate().getMonth()) + "/" + String.valueOf(EventManager.get(Pos).getRealizationDate().getYear() - 100);
                Eventi += EventManager.get(Pos).getCode() + " - " + Type + " - " + EventManager.get(Pos).getTitle() + " - " + FechaSimp + " - " + ((EventManager.get(Pos).isCancelled())?"CANCELADO":"ACTIVO") + " - " + EventManager.get(Pos).getStadiumCost() + "\n";
            }
            return getEventsCreatedBy(Eventi, Pos + 1, UserCodes);
        }
        GainsR = 0; GainsD = 0; GainsM = 0;
        CantR = 0; CantD = 0; CantM = 0;
        return Eventi;
    }
    // -- SETTERS --
    public void setCantidadMusical(int Cant){
        this.CantidadMusical = Cant;
    }
    
    public void setCantidadReligiosa(int Cant){
        this.CantidadReligiosa = Cant;
    }
    
    public void setCantidadDeportiva(int Cant){
        this.CantidadDeportiva = Cant;
    }
    
    public void setGananciasDeportivas(double Gains){
        this.GananciaDeportivas = Gains;
    }
    
    public void setGananciasReligiosas(double Gains){
        this.GananciasReligiosas = Gains;
    }
    
    public void setGananciasMusicales(double Gains){
        this.GananciasMusicales = Gains;
    }
    // -- SETTERS --
    // -- GETTERS --
    public int getCantidad(char EventType){
        return switch (EventType){
            case 'R' -> this.CantidadReligiosa;
            case 'D' -> this.CantidadDeportiva;
            default -> this.CantidadMusical;
        };
    }
    
    public double getGanancias(char EventType){
        return switch (EventType) {
            case 'R' -> GananciasReligiosas;
            case 'D' -> GananciaDeportivas;
            default -> GananciasMusicales;
        };
    }
    // -- GETTERS --
}
