package Frame;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Calendar;
import AccManager.AccManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class HudBar extends JPanel implements Runnable{
    //El reloj que se muestra en la cima del panel
    private final JLabel Timer = new JLabel("HH:MM:SS");
    //Es usado para desplegar correctamenete el nombre en la esquina superior derecha
    private final AccManager Acc;
    //Encargado de encontrar la hora actual
    private Calendar Watch;
    //String usado a la hora de mostrar y editar eventos
    private String Codice;
    
    //El label que, de hecho, muestra el nombre y usuario
    private JLabel NameNUser;
    //Panel en el cual se esta en dicho momento
    private int ActPanel = 0;
    //booleanos encargados de mostrar los paneles
    public boolean PanelChange = true;
    public boolean LogIn, MenuPanel, AccCreation, EventAdmin, Reportes;
    public HudBar(AccManager Acc){
        int Width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;

        this.Acc = Acc;
        this.setBackground(java.awt.Color.DARK_GRAY);
        this.setBounds(0,0,Width, 50);
        this.setLayout(null);

        Timer.setFont(new java.awt.Font("Berlin Sans FB",0,26));
        Timer.setBounds((Width/3), 10, Width/3, 40);
        Timer.setHorizontalAlignment(0);
        Timer.setVerticalAlignment(1);
        Timer.setForeground(Color.WHITE);
        NameDisplay();
        setButtons();

        add(Timer);
        setTime();
    }
    //Funcion encargada de encontrar y setear el label del nombre
    private void NameDisplay(){
        NameNUser = new JLabel();
        
        NameNUser.setFont(new java.awt.Font("Berlin Sans FB",0,26));
        NameNUser.setBounds(5,5,getWidth()/3 + getWidth()/4, 40);
        NameNUser.setForeground(Color.WHITE);
        Timer.setHorizontalAlignment(0);
        Timer.setVerticalAlignment(1);
        add(NameNUser);
    }
    //simplemente setea el boton para salir del programa
    private void setButtons(){
        JButton Exit = new JButton();
        
        Exit.setBounds(this.getWidth()-45, 5, 40, 40); 
        Exit.setBackground(null);
        Exit.setBorder(null);
        Exit.setFocusable(false);
        Exit.setFont(new java.awt.Font("Berlin Sans FB",0,26));
        Exit.setForeground(Color.WHITE);
        Exit.setText("X");
        
        Exit.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                Exit.setBackground(Color.red);
            }
            
            @Override
            public void mouseExited(MouseEvent e){
                Exit.setBackground(null);
            }
        });
        
        Exit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        
        add(Exit);
    }
    
    // -- SETTERS/GETTERS --
    public void setCodice(String code){
        Codice = code;
    }
    
    public String getCodice(){
        return Codice;
    }
    // -- SETTERS/GETTERS --
    
    //funcion encargada de encontrar el tiempo, luego en el hilo este se actualiza en tiempo +o- real
    private void setTime(){
        Watch = Calendar.getInstance();
        Timer.setText(String.valueOf(Watch.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(Watch.get(Calendar.MINUTE)+":"+String.valueOf(Watch.get(Calendar.SECOND))));
    }
    // -- Hilo y sus funciones relacionadas --
    public final void startTimer(){
        Thread Starter = new Thread(this);
        Starter.start();
    }
    
    @Override
    public void run() {
        double LastFrame = 0;
        while (this != null) {
            if ((System.nanoTime() - LastFrame) >= 1000000000/60){
                setTime();
                NameNUser.setText((ActPanel == 0)?"Nombre - Usuario":Acc.getCurrentAcc().getName() + " - " + Acc.getCurrentAcc().getUsername());
            }
        }
    }
    //Hilo y sus funciones relacionadas
    public int setActPanel(int ActPanel){
        return this.ActPanel = ActPanel;
    }
    
    public int getActPanel(){
        return this.ActPanel;
    }
    
    /*
    * El Hudbar, entre otras cosas, es encargado de mostrar los paneles, este guarda en su interior los booleanos que hacen posible
    * el correcto cambio de paneles, estos son esos booleanos:
    * index - Nombre          =   rol
    *************************************************************************************************************
    * <---------------------------------------------- MENU OPTIONS --------------------------------------------->
    *  0    - LogIn           =   booleano encargado de mostrar el panel inicial, el de log in.
    *  1    - MenuPanel       =   Panel de Menu, se elige la creacion de evento, administrador de usuarios y asi.
    * <---------------------------------------- ACCOUNTS MANAGER OPTIONS --------------------------------------->
    *  2    - Account creator =   Panel donde se especifican los datos necesarios para la creacion de una cuenta
    * <----------------------------------------- EVENT MANAGER OPTIONS ----------------------------------------->
    *  3    - Event creator   =   Panel donde, como el nombre indica, se crean eventos, lmao.
    *  4    - Editar evento   =   Se reusa el panel de creacion de evento, se cambian los valores y se alteran 
    *                             algunos elementos para quedar acorde a las necesidades de la opcion, por ejemplo
    *                             el elector de tipo de evento, el deporte y el monto acordado con el estadio son
    *                             practicamente intocables una vez creado el evento.
    *  5    - ver evento      =   Simplemente se desplegan los datos en pantalla reusando el panel de creacion, se 
    *                             podra editar NADA y al darle en el boton se pide al usuario que ingrese el codigo
    *                             del evento que desea.
    * <----------------------------------------------- REPORTES ------------------------------------------------>
    *  6    - Eventos Real.   =   Se muestran los eventos que se han realizados, se usan graficos para desplegar
    *                             en pantalla los distintos tipos de eventos y el dinero generado en cada uno
    *  7    - Eventos Futu.   =   Todos los eventos que aun no se han realizado se despliegan en este panel
    *  8    - Eventos Cancel. =   Se Busca en el array de eventos todos aquellos cuyo boolean de Cancelado 
    *                             indique 'true'.
    *  9    - Ingreso x fecha =   Se muestra con un grafico todos los ingresos, tanto los de los eventos no
    *                             cancelados como los que aun no se han realizado.
    *  10   - User data       =   Se muestran todos los eventos del usuario.
    * <--------------------------------------------- EXTRA OPTIONS --------------------------------------------->
    *  97   - Content start   =   Las cuentas de contenido encuentran su nicho con la creacion de eventos, por lo
    *                             cual su inicio de pantalla debe ser distinto al de un admin, estando enfocado a
    *                             eso, es decir, inicio en -Event manager-
    *  98   - limited start   =   Las cuentas limitadas cuentan con menos opciones, por ejemplo, el uso principal
    *                             de estas se puede ver en reportes (la unica accion que pueden realizar, aparte
    *                             ver eventos) por lo cual el inico de estos debe ser en -Reportes-.
    *  99   - Clear screeen   =   No es un panel en especifico, por la forma en la que funciona el codigo si se
    *                             si se ingresa un index que ningun panel posee entonces se setean como invisible,
    *                             dicho de otra forma, se limpia la pantalla.
    */
    public void setPanels(){
        LogIn = (ActPanel == 0);
        MenuPanel = (ActPanel >= 1);
        Reportes = (ActPanel >= 6 && ActPanel <= 10);
        AccCreation = (ActPanel == 1 || ActPanel == 2);
        EventAdmin = ((ActPanel >= 3 && ActPanel <= 5) || ActPanel == 97);
    }
    
}
