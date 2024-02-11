package Frame.ActionPanels;

import Frame.HudBar;
import javax.swing.*;
import AccManager.AccManager;
import EventManager.EventManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AccountCreation extends javax.swing.JPanel{
    //Tecnicamente constantes
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    //Extras usados en el panel
    private final ArrayList<String> Names;
    private final int PanelWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int PanelHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 50;
    public AccountCreation(AccManager AccMan, EventManager EvMan, HudBar Hud){
        this.Names = new ArrayList();
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.UpdateNames();
        this.setLayout(null);
        this.setBackground(java.awt.Color.LIGHT_GRAY);
        this.setBounds(PanelWidth/3, 0, PanelWidth - (PanelWidth/3), PanelHeight);
        
        setAccountTypeBox();
        setAccountBox();
        setActionBox();
        
        setNameField();
        setUsernameField();
        setPasswordField();
        setAgeBox();
        
        setActionBTN();
    }
    //Esta funcion se encarga de volver la clase visible y reiniciar los campos de texto.
    public final void Reveal(boolean Visibility){
        if (Visibility){
            this.setVisible(Visibility);
            
            AccountSelect.setSelectedIndex(0);
            AccountType.setSelectedIndex(0);
            Action.setSelectedIndex(0);
            
            Age.setSelectedIndex(17);
            Name.setText("Ingrese su nombre");
            Name.setForeground(Color.GRAY);
            Pass.setText("Ingrese su contraseña");
            Pass.setForeground(Color.GRAY);
            Username.setText("Ingrese un nombre de usuario");
            Username.setForeground(Color.GRAY);
            
            Pass.setEchoChar((char) 0);
            ActionBTN.setText("Crear cuenta");
        } else this.setVisible(false);
    }
    //Reiniciar los textboxes
    private void restartTXTFields(){
            Name.setText("Ingrese su nombre");
            Name.setForeground(Color.GRAY);
            Pass.setText("Ingrese su contraseña");
            Pass.setForeground(Color.GRAY);
            Pass.setEchoChar((char) 0);
            Username.setText("Ingrese un nombre de usuario");
            Username.setForeground(Color.GRAY);
    }
    //ComboBox usado para elegir el tipo de cuanta deseada
    private void setAccountTypeBox(){
        this.AccountType = new JComboBox();
        
        AccountType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Administrador", "Contenido", "Limitada"}));
        AccountType.setFont(new java.awt.Font("Berlin Sans FB",0,22));
        AccountType.setBounds(10, 35, (getWidth()/4), 30);
        AccountType.setEnabled(true);
        add(AccountType);
        JLabel AccountTypeTXT = new JLabel("Tipo de cuenta: ");
        
        AccountTypeTXT.setBounds(10, 10, AccountType.getWidth(), 25);
        AccountTypeTXT.setFont(new java.awt.Font("Roboto",0,14));
        AccountTypeTXT.setForeground(Color.BLACK);
        add(AccountTypeTXT);
    }
    //ComboBox usado para elegir la accion a elegir, es decir, la de Crear, Editar y Borrar cuenta
    private void setActionBox(){
        this.Action = new JComboBox();
        
        Action.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Crear cuenta", "Editar cuenta", "Eliminar cuenta"}));
        Action.setFont(new java.awt.Font("Berlin Sans FB",0,22));
        Action.setBounds(((getWidth()/2) - (getWidth()/8)), 35, (getWidth()/4), 30);
        Action.addActionListener((ActionEvent e) -> {
            Name.setEditable((Action.getSelectedIndex() != 2));
            Pass.setEditable((Action.getSelectedIndex() != 2));
            Username.setEditable((Action.getSelectedIndex() != 2));
            AccountType.setEnabled((Action.getSelectedIndex() != 2));
            AccountSelect.setEnabled(Action.getSelectedIndex() != 0);
            ActionBTN.setText(Action.getSelectedItem().toString());
            if (Action.getSelectedIndex() == 0) restartTXTFields();
            if (Action.getSelectedIndex() == 2){
                AccountSelect.setSelectedIndex(1);
                AccountSelect.actionPerformed(e);
            }
        });
        Action.setEnabled(true);
        add(Action);
        JLabel AccountTypeTXT = new JLabel("Que desea realizar: ");
        
        AccountTypeTXT.setBounds(Action.getX(), 10, Action.getWidth(), 25);
        AccountTypeTXT.setFont(new java.awt.Font("Roboto",0,14));
        AccountTypeTXT.setForeground(Color.BLACK);
        add(AccountTypeTXT);
    }
    //ComboBox usado para elegir la cuenta ha editas/eliminar
    private void setAccountBox(){
        this.AccountSelect = new JComboBox();
        
        AccountSelect.setModel(new javax.swing.DefaultComboBoxModel<>(Names.toArray()));
        AccountSelect.setFont(new java.awt.Font("Berlin Sans FB",0,22));
        AccountSelect.setBounds(((getWidth()/2) + (getWidth()/4)) - 10, 35, (getWidth()/4), 30);
        AccountSelect.addActionListener((ActionEvent e) -> {
            switch (AccountSelect.getSelectedIndex()){
                case 0 -> restartTXTFields();
                case 1 -> AccountSelect.setSelectedIndex((AccMan.getAccountList().size() > 2)?2:0);
                default -> {
                    Name.setText(AccMan.getAccountList().get(AccountSelect.getSelectedIndex()).getName());
                    Name.setForeground(Color.BLACK);
                    Pass.setText(AccMan.getAccountList().get(AccountSelect.getSelectedIndex()).getPassword());
                    Pass.setForeground(Color.BLACK);
                    Pass.setEchoChar('\u2022');
                    Username.setText(AccMan.getAccountList().get(AccountSelect.getSelectedIndex()).getUsername());
                    Username.setForeground(Color.BLACK);
                    AccountType.setSelectedIndex(AccMan.getAccountList().get(AccountSelect.getSelectedIndex()).getAccType());
                    Age.setSelectedIndex(AccMan.getAccountList().get(AccountSelect.getSelectedIndex()).getAge() - 1);
                }
            }
        });
        AccountSelect.setEnabled(true);
        add(AccountSelect);
        JLabel AccountTypeTXT = new JLabel("Cuenta que desea modificar: ");
        
        AccountTypeTXT.setBounds(AccountSelect.getX(), 10, AccountSelect.getWidth(), 25);
        AccountTypeTXT.setFont(new java.awt.Font("Roboto",0,14));
        AccountTypeTXT.setForeground(Color.BLACK);
        add(AccountTypeTXT);
    }
    //JTextField usado para ingresar el nombre, asi como los sus elementos alrededor
    private void setNameField(){
        JLabel BoxDesc = new JLabel("Ingrese su nombre real: ");
        
        BoxDesc.setBounds(10, (AccountType.getY() + AccountType.getHeight()) + 20, getWidth() - 20, 50);
        BoxDesc.setFont(new java.awt.Font("Roboto",0,14));
        BoxDesc.setForeground(Color.black);
        add(BoxDesc);
        
        Name = new JTextField();
        
        Name.setBorder(null);
        Name.setBackground(null);
        Name.setOpaque(false);
        Name.setForeground(java.awt.Color.gray);
        Name.setFont(new java.awt.Font("Roboto",0,24));
        Name.setBounds(10, (BoxDesc.getHeight() + BoxDesc.getY()) - 15, this.getWidth() - 20, 50);
        Name.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Name.getText().equals("Ingrese su nombre")){
                    Name.setText(null);
                    Name.setForeground(java.awt.Color.BLACK);
                    Name.requestFocus();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (Name.getText().isBlank() && Name.hasFocus()){
                    Name.setForeground(java.awt.Color.GRAY);
                    Name.setText("Ingrese su nombre");
                } else if (!Name.getText().isBlank() && !Name.getText().equals("Ingrese su nombre")){
                    Name.setForeground(java.awt.Color.BLACK);
                }
            }
        });
        Name.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if (Name.getText().equals("Ingrese su nombre")){
                    Name.setText(null);
                    Name.setForeground(java.awt.Color.BLACK);
                    Name.requestFocus();
                }
            }
        });
        add(Name);
        JSeparator Divisor = new JSeparator();
        
        Divisor.setBackground(Color.BLACK);
        Divisor.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        Divisor.setBounds(Name.getX(), Name.getY() + Name.getHeight() - 5, Name.getWidth(), 10);
        add(Divisor);
    }
    //JTextField usado para ingresar el nombre de usuario, asi como los sus elementos alrededor
    private void setUsernameField(){
        JLabel BoxDesc = new JLabel("Ingrese el nombre de usuario que desea: ");
        
        BoxDesc.setBounds(10, (Name.getY() + Name.getHeight()) + 20, getWidth() - 20, 50);
        BoxDesc.setFont(new java.awt.Font("Roboto",0,14));
        BoxDesc.setForeground(Color.black);
        add(BoxDesc);
        
        Username = new JTextField();
        
        Username.setBorder(null);
        Username.setBackground(null);
        Username.setOpaque(false);
        Username.setForeground(java.awt.Color.gray);
        Username.setFont(new java.awt.Font("Roboto",0,24));
        Username.setBounds(10, (BoxDesc.getHeight() + BoxDesc.getY()) - 15, this.getWidth() - 20, 50);
        Username.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Username.getText().equals("Ingrese un nombre de usuario")){
                    Username.setText(null);
                    Username.setForeground(java.awt.Color.BLACK);
                    Username.requestFocus();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (Username.getText().isBlank() && Username.hasFocus()){
                    Username.setForeground(java.awt.Color.GRAY);
                    Username.setText("Ingrese un nombre de usuario");
                } else if (!Username.getText().isBlank() && !Username.getText().equals("Ingrese un nombre de usuario")){
                    Username.setForeground(java.awt.Color.BLACK);
                }
            }
        });
        Username.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if (Username.getText().equals("Ingrese un nombre de usuario")){
                    Username.setText(null);
                    Username.setForeground(java.awt.Color.BLACK);
                    Username.requestFocus();
                }
            }
        });
        add(Username);
        JSeparator Divisor = new JSeparator();
        
        Divisor.setBackground(Color.BLACK);
        Divisor.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        Divisor.setBounds(Username.getX(), Username.getY() + Username.getHeight() - 5, Username.getWidth(), 10);
        add(Divisor);
    }
    //JPasswordField usado para ingresar la contrasegna, asi como los sus elementos alrededor
    private void setPasswordField(){
        JLabel BoxDesc = new JLabel("Ingrese la contraseña deseada: ");
        
        BoxDesc.setBounds(10, (Username.getY() + Username.getHeight()) + 20, getWidth() - 20, 50);
        BoxDesc.setFont(new java.awt.Font("Roboto",0,14));
        BoxDesc.setForeground(Color.black);
        add(BoxDesc);
        
        Pass = new JPasswordField();
        
        Pass.setEchoChar((char) 0);
        Pass.setBorder(null);
        Pass.setBackground(null);
        Pass.setOpaque(false);
        Pass.setText("Ingrese su contraseña");
        Pass.setForeground(java.awt.Color.gray);
        Pass.setFont(new java.awt.Font("Roboto",0,24));
        Pass.setBounds(10, (BoxDesc.getHeight() + BoxDesc.getY()) - 15, this.getWidth() - 100, 50);
        Pass.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Pass.getText().equals("Ingrese su contraseña")){
                    Pass.setText(null);
                    Pass.setForeground(java.awt.Color.BLACK);
                    Pass.requestFocus();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (Pass.getText().isBlank() && Pass.hasFocus()){
                    Pass.setForeground(java.awt.Color.GRAY);
                    Pass.setText("Ingrese su contraseña");
                    Pass.setEchoChar((char) 0);
                } else if (!Pass.getText().isBlank() && !Pass.getText().equals("Ingrese su contraseña")){
                    Pass.setForeground(java.awt.Color.BLACK);
                }
            }
        });
        Pass.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if (Pass.getText().equals("Ingrese su contraseña")){
                    Pass.requestFocus();
                    Pass.setText(null);
                    Pass.setForeground(java.awt.Color.BLACK);
                }
                Pass.setEchoChar('\u2022');
            }
        });
        add(Pass);
        JSeparator Divisor = new JSeparator();
        
        Divisor.setBackground(Color.BLACK);
        Divisor.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        Divisor.setBounds(Pass.getX(), Pass.getY() + Pass.getHeight() - 5, Pass.getWidth(), 10);
        add(Divisor);
        
        JButton RevealPass = new JButton();
        
        RevealPass.setBounds(Pass.getX() + Pass.getWidth() + 25, Pass.getY(), 50, 50);
        RevealPass.addActionListener((ActionEvent e) -> {
            if (Pass.echoCharIsSet()){
                Pass.setEchoChar((char) 0);
            } else if (!Pass.echoCharIsSet()) {
                Pass.setEchoChar('\u2022');
            }
        });
        RevealPass.setFocusable(false);
        add(RevealPass);
    }
    //ComboBox usado para elegir la edad del usuario
    private void setAgeBox(){
        JLabel AgeTXT = new JLabel("Edad del usuario: ");
        
        AgeTXT.setBounds(10, (Pass.getY() + Pass.getHeight()) + 20, AccountType.getWidth(), 25);
        AgeTXT.setFont(new java.awt.Font("Roboto",0,14));
        AgeTXT.setForeground(Color.BLACK);
        add(AgeTXT);
        this.Age = new JComboBox();
        
        Age.setModel(new javax.swing.DefaultComboBoxModel<>(getAges()));
        Age.setFont(new java.awt.Font("Berlin Sans FB",0,22));
        Age.setBounds(10, (AgeTXT.getY() + AgeTXT.getHeight()) + 5, (getWidth()/4), 30);
        Age.setEnabled(true);
        add(Age);
    }
    //Boton usado para crear, eliminar o editar la cuenta
    private void setActionBTN(){
        ActionBTN = new JButton();
        
        ActionBTN.setBounds((this.getWidth() - getWidth()/4) - 10, (Age.getY()) - 20, (getWidth()/4), 50);
        ActionBTN.setFont(new java.awt.Font("Berlin Sans FB",0,22));
        ActionBTN.addActionListener((ActionEvent e) -> {
            switch (Action.getSelectedIndex()){
                case 0 -> AddAccount();
                case 1 -> {
                    if (AccountSelect.getSelectedIndex() != 0){
                       EditAccount(JOptionPane.showInputDialog("Digite su contraseña para confirmar el cambio de datos: "));
                    } else JOptionPane.showMessageDialog(this, "Seleccione la cuenta que desea editar");
                }
                case 2 -> {
                    if (AccountSelect.getSelectedIndex() != 0){
                        DeleteAccount();
                    } else JOptionPane.showMessageDialog(this, "Por favor seleccione la cuenta que desea eliminar");
                }
            }
        });
        ActionBTN.setFocusable(false);
        add(ActionBTN);
    }
    //En caso de que el boton este seteado en crear cuenta, se debe crear una, lmao
    private void AddAccount(){
        if (isActionPossible()){
            if (AccMan.isUsernameValid(Username.getText(), 0)){
               AccMan.addAccount(Name.getText(), Username.getText(), Pass.getText(), Age.getSelectedIndex()+1, AccountType.getSelectedIndex());
               JOptionPane.showMessageDialog(this, "Cuenta creada con exito");
               restartTXTFields();

               UpdateNames();
                AccountSelect.setModel(new javax.swing.DefaultComboBoxModel<>(Names.toArray()));
            } else JOptionPane.showMessageDialog(this, "El nombre de usuario que desea ya esta tomado");
        }
    }
    //La opcion de editar los datos de una cuenta
    private void EditAccount(String Pass){
        if (isActionPossible()){
            if (AccMan.isUsernameValid(Username.getText(), 0)){
                if (AccMan.getCurrentAcc().getPassword().equals(Pass)){
                    String nombre = Name.getText();
                    String Usuario = Username.getText();
                    String Contra = this.Pass.getText();
                    int Edad = Age.getSelectedIndex() + 1;
                    int Tipo = AccountType.getSelectedIndex();
                    
                    AccMan.getAccount(false, AccMan.getAccountList().get(AccountSelect.getSelectedIndex()).getUsername(), AccMan.getAccountList().get(AccountSelect.getSelectedIndex()).getPassword(), 0).changeAccInfo(nombre, Usuario, Contra, Edad, Tipo);
                } else JOptionPane.showMessageDialog(this, "Ha ingresado la contraseña incorrecta");
            } else JOptionPane.showMessageDialog(this, "El nombre de usuario que desea ya esta tomado");
        }
    }
    //La opcion de eliminar una cuenta
    private void DeleteAccount(){
        if (0 == JOptionPane.showConfirmDialog(this, (AccMan.getCurrentAcc().getUsername().equals(Username.getText()))?"Si borra se perderan todos los datos relacionados a ella y debera volver al inicio de sesion.\nDesea proseguir?":"Si borra esta cuenta todos los datos relacionados con ella seran eliminador, desea proseguir?", "Eliminar cuenta", JOptionPane.YES_NO_OPTION)){
                AccMan.removeAccount(Username.getText(), Pass.getText());
                JOptionPane.showMessageDialog(this, "Cuenta eliminada con exito");
                
                if (AccMan.getCurrentAcc().getUsername().equals(Username.getText())){
                    Hud.setActPanel(0);
                    Hud.PanelChange = true;
                }
                
                UpdateNames();
                AccountSelect.setSelectedIndex(0);
                AccountSelect.setModel(new javax.swing.DefaultComboBoxModel<>(Names.toArray()));
        }
    }
    //Se confirma que ningun campo haya quedado vacio, si es el caso se permitira la creacion de una cuenta
    private boolean isActionPossible(){
        if (!Name.getText().isBlank() && !Name.getText().equals("Ingrese su nombre")){
            if (!Username.getText().isBlank() && !Username.getText().equals("Ingrese un nombre de usuario")){
                if (!Pass.getText().isBlank() && !Pass.getText().equals("Ingrese su contraseña")){
                    return true;
                } else JOptionPane.showMessageDialog(this, "Por favor ingrese una contraseña");
            } else JOptionPane.showMessageDialog(this, "Por favor ingrese un nombre de usuario valido");
        } else JOptionPane.showMessageDialog(this, "Por favor ingrese su nombre");
        return false;
    }
    //Se setean las edad posibles a elegir
    private String[] getAges(){
        String[] Ages = new String[100];
        for (int edad = 1; edad < 100; edad++){
            Ages[edad - 1] = String.valueOf(edad);
        }
        Ages[99] = "99+";
        return Ages;
    }
    //Se consiguen los nombres de usuario de todas las cuentas
    private void UpdateNames(){
        Names.clear();
        for (int name = 0; name < AccMan.getAccountList().size(); name++){
            Names.add(name+". "+AccMan.getAccountList().get(name).getUsername());
        }
        Names.set(0, "Selector de cuentas");
    }
    // -- Swing Elements --
    private JComboBox AccountType, AccountSelect, Action, Age;
    private JTextField Name, Username;
    private JPasswordField Pass;
    private JButton ActionBTN;
    // -- Swing Elements --
}
