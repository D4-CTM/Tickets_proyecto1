package AccManager;

import java.util.ArrayList;

public class AccManager {
    //ArrayList de cuentas creadas
    private final ArrayList<Account> Cuentas = new ArrayList();
    //La cuenta con la cual se logea
    private Account CurrentAcc;
    
    public AccManager(){
        Cuentas.add(new Account("Gæst", "Gæst", "", 20, 2));
        Cuentas.add(new Account("Josh Delcid", "Admin", "Supersecreto", 20, 0));
    }
    //Se consigue la array de elementos, usado en el combobox de Account creation
    public final ArrayList<Account> getAccountList(){
        return this.Cuentas;
    }
    //Se agnaden cuentas al arraylist de estas
    public void addAccount(String Name, String Username, String Password, int Age, int Type){
        Cuentas.add(new Account(Name, Username, Password, Age, Type));
    }
    //Se solicita el usuario y la contrasegna para eliminar la cuenta
    public void removeAccount(String Username, String Password){
        Cuentas.remove(getAccount(false, Username, Password, 0));
    }
    //Se verifica que el nombre de usuario sea unico
    public boolean isUsernameValid(String Username, int Pos){
        if (Pos < Cuentas.size()){
            if (Cuentas.get(Pos).getUsername().equals(Username)){
                return false;
            } return isUsernameValid(Username, Pos+1);
        }
        return true;
    }
    //Se busca una cuenta en especifico, esto es lo que se suele usar cuando se crean nuevas cuentas
    public Account getAccount(boolean ChangeAcc, String Username, String Password, int pos){
        if (pos < Cuentas.size()){
            if (Cuentas.get(pos).getUsername().equals(Username) && Cuentas.get(pos).getPassword().equals(Password)){
                 if (ChangeAcc) CurrentAcc = Cuentas.get(pos);
                return Cuentas.get(pos);
            } else return getAccount(ChangeAcc, Username,Password, pos + 1);
        }
        return null;
    }
    //Se consigue la cuenta con la uno se logeo
    public Account getCurrentAcc(){
        return this.CurrentAcc;
    }
    
}
