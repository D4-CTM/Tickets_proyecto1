package AccManager;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Account {
    //La informacion basica de cada usuario: Nombre, Usuario y contrasegna
    private String Name, Username, Password;
    //Info basica de todos los usuarios: Codigos de los eventos creados, edad y tipo de cuenta
    private final ArrayList<String> EventCodes;
    private int Age, Type;
    
    public Account(String Name, String Username, String Password, int Age, int Type){
        this.EventCodes = new ArrayList();
        this.Username = Username;
        this.Password = Password;
        this.Name = Name;
        this.Type = Type;
        this.Age = Age;
    }
    
    //Se cambia la informacion de la cuenta
    public void changeAccInfo(String Name, String Username, String Password, int Age, int Type){
        this.Username = Username;
        this.Password = Password;
        this.Name = Name;
        this.Type = Type;
        this.Age = Age;
        JOptionPane.showMessageDialog(null, "Se han cambiado con exito los datos de "+this.Username);
    }
    //Usado a la hora de crear eventos
    public void AddEventCreated(String Code){
        EventCodes.add(Code);
    }
    
    public boolean EventCreatedBy(String Code){
        return (EventCodes.contains(Code));
    }
    // -- Getters --
    public int getAccType(){
        return this.Type;
    }
    
    public ArrayList<String> getEventsCreated(){
        return this.EventCodes;
    }
    
    public String getPassword(){
        return this.Password;
    }
    
    public String getUsername(){
        return this.Username;
    }
    
    public String getName(){
        return this.Name;
    }
    
    public int getAge(){
        return this.Age;
    }
    // -- Getters --
    
}
