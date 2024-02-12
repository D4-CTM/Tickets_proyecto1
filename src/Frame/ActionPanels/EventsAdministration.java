package Frame.ActionPanels;

import Frame.HudBar;
import javax.swing.*;
import AccManager.AccManager;
import EventManager.EventManager;
import Frame.ActionPanels.EventsTypePanel.MusicEvent;
import Frame.ActionPanels.EventsTypePanel.ReligousEvents;
import Frame.ActionPanels.EventsTypePanel.SportsEvent;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class EventsAdministration extends javax.swing.JPanel{
    //Tecnicamente constantes
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    //Event creation data
    private int MaxAttendees;
    //Extras usados en el panel
    private final int PanelWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int PanelHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 50;
    private final Random Randy = new Random();
    
    public EventsAdministration(AccManager AccMan, EventManager EvMan, HudBar Hud){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.setLayout(null);
        this.setBackground(null);
        this.setBounds((PanelWidth/3), 0, PanelWidth - (PanelWidth/3), PanelHeight);
        
        setCentralPanel();
    }
    //Funcion usada para mostrar los datos con cada click de boton, es un remplazo del visible (vease el hilo encargado de cambiar
    //los paneles si no lo entiende)
    public final void Reveal(boolean Reveal){
        invalidFields();
        if (Reveal){
            if (Hud.getActPanel() == 3){
                CreateEvent();
            } else setData();
        }
        setVisible(Reveal);
    }
    //Reinicia los elementos para crear un evento
    private void CreateEvent(){
        Title.setText("Ingrese el titulo del evento");
        Title.setForeground(Color.GRAY);
        Description.setText("Ingrese la descripcion del evento");
        Description.setForeground(Color.GRAY);
        Code.setText(CreateCode("D-"));
        Attendees.setForeground(java.awt.Color.GRAY);
        Attendees.setText("Gente que asistira");
        StadiumPrice.setForeground(java.awt.Color.GRAY);
        StadiumPrice.setText("Precio acordado por el estadio");
        EventType.setSelectedIndex(0);
        Fecha.setDate(Calendar.getInstance().getTime());
        
        EventoDeportivo.setVisible(false);
        EventoReligioso.setVisible(false);
        EventoMusical.setVisible(false);
    }
    //Se setean los campos de texto acordes al evento
    private void setData(){
        try {
            Title.setText(EvMan.getEvent(Hud.getCodice(), 0).getTitle());
            Title.setForeground(Color.BLACK);
            Description.setText(EvMan.getEvent(Hud.getCodice(), 0).getDesc());
            Description.setForeground(Color.BLACK);
            Fecha.setDate(EvMan.getEvent(Hud.getCodice(), 0).getRealizationDate());
            Attendees.setText(String.valueOf(EvMan.getEvent(Hud.getCodice(), 0).getAttendees()));
            Attendees.setForeground(Color.BLACK);
            StadiumPrice.setText(String.valueOf(EvMan.getEvent(Hud.getCodice(), 0).getStadiumCost()));
            StadiumPrice.setForeground(Color.BLACK);

            switch (Hud.getCodice().charAt(0)){
                case 'D' -> {
                    EventType.setSelectedIndex(0);
                    Code.setText(Hud.getCodice());
                    EventoDeportivo.setData();
                }
                case 'M' -> {
                    EventType.setSelectedIndex(1);
                    Code.setText(Hud.getCodice());
                    EventoMusical.setData();
                }
                case 'R' -> {
                    EventType.setSelectedIndex(2);
                    Code.setText(Hud.getCodice());
                    EventoReligioso.setData();
                }
            }
        } catch (Exception Ex){
            CreateEvent();
        }
    }
    //En caso de querer ver un evento hay cosas que deberan volverse invalidas
    private void invalidFields(){
        Title.setEditable(Hud.getActPanel() != 5);
        StadiumPrice.setEditable(Hud.getActPanel() != 5);
        Attendees.setEditable(Hud.getActPanel() != 5);
        Description.setEditable(Hud.getActPanel() != 5);
        EventType.setEnabled(Hud.getActPanel() == 3);
        Fecha.setEnabled(Hud.getActPanel() != 5);
        ActionBTN.setVisible(Hud.getActPanel() != 5);
        
        EventoMusical.InvalidFields();
        EventoMusical.ClearData();
        EventoDeportivo.InvalidFields();
        EventoDeportivo.ClearData();
        EventoReligioso.InvalidFields();
        EventoReligioso.ClearData();
        switch (Hud.getActPanel()){
            case 3 -> ActionBTN.setText("Crear evento");
            case 4 -> ActionBTN.setText("Editar evento");
        }
    }
    //Panel donde se pondra toda la info, en el fondo sera una imagen que vaya acorde al tipo de evento
    private void setCentralPanel(){
        InputData = new JPanel();
        
        InputData.setLayout(null);
        InputData.setBackground(java.awt.Color.LIGHT_GRAY);
        InputData.setBounds(50, 50, getWidth() - 100, getHeight() - 100);
        
        setTitleField();
        setCodeField();
        setEventDate();
        setDescField();
        
        setEventBox();
        setStadiumPrice();
        setAttendeesField();
        setActionBTN();
        
        setEventTypes();
        add(InputData);
    }
    //TXTField donde se ingresara el titulo del evento
    private void setTitleField(){
        JLabel TitleTXT = new JLabel("Ingrese el titulo del evento: ");
                
        TitleTXT.setFont(new java.awt.Font("Roboto",0,14));
        TitleTXT.setBounds(10,10,InputData.getWidth()/2 - 25,25);
        InputData.add(TitleTXT);
        Title = new JTextField();
        
        Title.setBorder(null);
        Title.setBackground(null);
        Title.setOpaque(false);
        Title.setForeground(java.awt.Color.GRAY);
        Title.setText("Ingrese el titulo del evento");
        Title.setFont(new java.awt.Font("Roboto",0,26));
        Title.setBounds(TitleTXT.getX(), TitleTXT.getY()+TitleTXT.getHeight(), TitleTXT.getWidth(), 50);
        Title.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Title.getText().equals("Ingrese el titulo del evento")){
                    Title.setText(null);
                    Title.setForeground(java.awt.Color.BLACK);
                    Title.requestFocus();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (Title.getText().isBlank() && Title.hasFocus()){
                    Title.setForeground(java.awt.Color.GRAY);
                    Title.setText("Ingrese el titulo del evento");
                } else if (!Title.getText().isBlank() && !Title.getText().equals("Ingrese el titulo del evento")){
                    Title.setForeground(java.awt.Color.BLACK);
                }
            }
        });
        Title.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if (Title.getText().equals("Ingrese el titulo del evento")){
                    Title.setText(null);
                    Title.setForeground(java.awt.Color.BLACK);
                    Title.requestFocus();
                }
            }
        });
        InputData.add(Title);
        JSeparator Divisor = new JSeparator();
        
        Divisor.setBackground(Color.BLACK);
        Divisor.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        Divisor.setBounds(Title.getX(), Title.getY() + Title.getHeight() - 5, Title.getWidth(), 10);
        InputData.add(Divisor);
    }
    //TXTField usado para mostrar el codigo de el evento
    private void setCodeField(){
        JLabel CodeTXT = new JLabel("Codigo del evento: ");
                
        CodeTXT.setFont(new java.awt.Font("Roboto",0,14));
        CodeTXT.setBounds(Title.getX(),Title.getY() + Title.getHeight() + 20, InputData.getWidth()/2 - 25,25);
        InputData.add(CodeTXT);
        Code = new JTextField();
        
        Code.setEditable(false);
        Code.setBorder(null);
        Code.setBackground(null);
        Code.setOpaque(false);
        Code.setText(CreateCode("D-"));
        Code.setForeground(java.awt.Color.BLACK);
        Code.setFont(new java.awt.Font("Roboto",0,26));
        Code.setBounds(CodeTXT.getX(), CodeTXT.getY()+CodeTXT.getHeight(), CodeTXT.getWidth(), 50);
        InputData.add(Code);
        JSeparator Divisor = new JSeparator();
        
        Divisor.setBackground(Color.BLACK);
        Divisor.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        Divisor.setBounds(Code.getX(), Code.getY() + Code.getHeight() - 5, Code.getWidth(), 10);
        InputData.add(Divisor);
    }
    //Campo donde se define la fecha del evento
    private void setEventDate(){
        JLabel DateTXT = new JLabel("Seleccione una fecha para el evento ");
                
        DateTXT.setFont(new java.awt.Font("Roboto",0,14));
        DateTXT.setBounds(Code.getX(),Code.getY() + Code.getHeight() + 20, InputData.getWidth()/2 - 25,25);
        InputData.add(DateTXT);
        Fecha = new JDateChooser(new Date());
        
        Fecha.setBounds(DateTXT.getX(), DateTXT.getY() + DateTXT.getHeight(), DateTXT.getWidth(), 50);
        Fecha.setFont(new java.awt.Font("Berlin Sans FB",0,22));
        Fecha.setDateFormatString("dd/MM/yyyy");
        Fecha.setLocale(new Locale("es"));
        InputData.add(Fecha);
    }
    //Campo usado para definir la descripcion del evento
    private void setDescField(){
        JLabel DescTXT = new JLabel("Ingrese una descripcion para el evento: ");
                
        DescTXT.setFont(new java.awt.Font("Roboto",0,14));
        DescTXT.setBounds(Fecha.getX(),Fecha.getY() + Fecha.getHeight(),InputData.getWidth()/2 - 25,25);
        InputData.add(DescTXT);
        Description = new JTextArea();
        
        Description.setBorder(null);
        Description.setLineWrap(true);
        Description.setBackground(null);
        Description.setOpaque(false);
        Description.setWrapStyleWord(true);
        Description.setForeground(java.awt.Color.GRAY);
        Description.setText("Ingrese la descripcion del evento");
        Description.setFont(new java.awt.Font("Roboto",0,26));
        Description.setBounds(DescTXT.getX(), DescTXT.getY()+DescTXT.getHeight(), DescTXT.getWidth(), 250);
        Description.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Description.getText().equals("Ingrese la descripcion del evento")){
                    Description.setText(null);
                    Description.setForeground(java.awt.Color.BLACK);
                    Description.requestFocus();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (Description.getText().isBlank() && Description.hasFocus()){
                    Description.setForeground(java.awt.Color.GRAY);
                    Description.setText("Ingrese la descripcion del evento");
                } else if (!Description.getText().isBlank() && !Description.getText().equals("Ingrese la descripcion del evento")){
                    Description.setForeground(java.awt.Color.BLACK);
                }
            }
        });
        Description.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if (Description.getText().equals("Ingrese la descripcion del evento")){
                    Description.setText(null);
                    Description.setForeground(java.awt.Color.BLACK);
                    Description.requestFocus();
                }
            }
        });
        JScrollPane Skroll = new JScrollPane(Description);
        
        Skroll.setWheelScrollingEnabled(true);
        Skroll.setBounds(Description.getBounds());
        InputData.add(Skroll);
        JSeparator DivisorBottom = new JSeparator();
        
        DivisorBottom.setOpaque(false);
        DivisorBottom.setBackground(Color.BLACK);
        DivisorBottom.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        DivisorBottom.setBounds(Description.getX(), Description.getY() + Description.getHeight(), Description.getWidth(), 10);
        InputData.add(DivisorBottom);
        JSeparator DivisorRight = new JSeparator();
        
        DivisorRight.setBackground(Color.BLACK);
        DivisorRight.setOrientation(javax.swing.SwingConstants.VERTICAL);
        DivisorRight.setBounds(Description.getX() + Description.getWidth(), Description.getY(), 10, Description.getHeight());
        InputData.add(DivisorRight);
    }
    //Elemento usado para definir el tipo de evento ha crear
    private void setEventBox(){
        EventType = new JComboBox();
                
        EventType.setBounds(Title.getX() + Title.getWidth() + 25, Title.getY(), Title.getWidth(), Title.getHeight());
        EventType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Deportivo", "Musical", "Religiosos"}));
        EventType.setFont(new java.awt.Font("Berlin Sans FB",0,22));
        
        JLabel PriceTag = new JLabel("Ingrese la cantidad acordada: ");
        
        PriceTag.setBounds(EventType.getX(), EventType.getY() + EventType.getHeight() + 5, EventType.getWidth(), 25);
        PriceTag.setFont(new java.awt.Font("Roboto",0,18));
        InputData.add(PriceTag);
        EventType.addActionListener((ActionEvent e) -> {
            switch (EventType.getSelectedIndex()){
                case 0 -> {
                    if (Hud.getActPanel() != 3){
                        EventoDeportivo.ClearData();
                        EventoMusical.setVisible(false);
                        EventoDeportivo.setVisible(true);
                        EventoReligioso.setVisible(false);
                    }
                    Code.setText(CreateCode("D-"));
                    PriceTag.setText("Ingrese el precio del estadio: ");
                    AttendeesInfo.setText("Cantidad maxima de "+(MaxAttendees=20000)+": ");
                }
                case 1 -> {
                    if (Hud.getActPanel() != 3){
                        EventoMusical.ClearData();
                        EventoMusical.setVisible(true);
                        EventoDeportivo.setVisible(false);
                        EventoReligioso.setVisible(false);
                    }
                    Code.setText(CreateCode("M-"));
                    PriceTag.setText("Sera cobrado con 30% extra: ");
                    AttendeesInfo.setText("Cantidad de personas maxima: "+(MaxAttendees=25000));
                }
                case 2 -> {
                    if (Hud.getActPanel() != 3){
                        EventoReligioso.ClearData();
                        EventoMusical.setVisible(false);
                        EventoReligioso.setVisible(true);
                        EventoDeportivo.setVisible(false);
                    }
                    Code.setText(CreateCode("R-"));
                    PriceTag.setText("Se cobraran 2000 Lps extra: ");
                    AttendeesInfo.setText("Cantidad de personas maxima: "+(MaxAttendees=30000));
                }
            }
        });
        InputData.add(EventType);
        JLabel EventTypeTXT = new JLabel("Seleccione el tipo de evento: ");
        
        EventTypeTXT.setFont(new java.awt.Font("Roboto",0,14));
        EventTypeTXT.setBounds(EventType.getX(),EventType.getY() - 25, EventType.getWidth(),25);
        InputData.add(EventTypeTXT);
    }
    //Espacio reservado para el precio del estadio
    private void setStadiumPrice(){
        StadiumPrice = new JTextField();
        
        StadiumPrice.setBorder(null);
        StadiumPrice.setBackground(null);
        StadiumPrice.setOpaque(false);
        StadiumPrice.setForeground(java.awt.Color.GRAY);
        StadiumPrice.setText("Precio acordado por el estadio");
        StadiumPrice.setFont(new java.awt.Font("Roboto",0,24));
        StadiumPrice.setBounds(EventType.getX(), EventType.getY()+EventType.getHeight() + 25,EventType.getWidth(),50);
        StadiumPrice.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (StadiumPrice.getText().equals("Precio acordado por el estadio")){
                    StadiumPrice.setText(null);
                    StadiumPrice.setForeground(java.awt.Color.BLACK);
                    StadiumPrice.requestFocus();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (StadiumPrice.getText().isBlank() && StadiumPrice.hasFocus()){
                    StadiumPrice.setForeground(java.awt.Color.GRAY);
                    StadiumPrice.setText("Precio acordado por el estadio");
                } else if (!StadiumPrice.getText().isBlank() && !StadiumPrice.getText().equals("Precio acordado por el estadio")){
                    StadiumPrice.setForeground(java.awt.Color.BLACK);
                }
            }
        });
        StadiumPrice.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                    (c == KeyEvent.VK_BACK_SPACE) ||
                    (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
                if (StadiumPrice.getText().equals("Precio acordado por el estadio")){
                    StadiumPrice.setText(null);
                    StadiumPrice.setForeground(java.awt.Color.BLACK);
                    StadiumPrice.requestFocus();
                }
            }
        });
        InputData.add(StadiumPrice);
        JSeparator Divisor = new JSeparator();
        
        Divisor.setBackground(Color.BLACK);
        Divisor.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        Divisor.setBounds(StadiumPrice.getX(), StadiumPrice.getY() + StadiumPrice.getHeight() - 5, StadiumPrice.getWidth(), 10);
        InputData.add(Divisor);
    }
    //Se setea el JTextField de la cantidad de gente que ha asistido
    private void setAttendeesField(){
        Attendees = new JTextField();
        
        Attendees.setBorder(null);
        Attendees.setBackground(null);
        Attendees.setOpaque(false);
        Attendees.setForeground(java.awt.Color.GRAY);
        Attendees.setText("Gente que asistira");
        Attendees.setFont(new java.awt.Font("Roboto",0,24));
        Attendees.setBounds(StadiumPrice.getX(), StadiumPrice.getY()+StadiumPrice.getHeight() + 25,StadiumPrice.getWidth(),50);
        Attendees.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Attendees.getText().equals("Gente que asistira")){
                    Attendees.setText(null);
                    Attendees.setForeground(java.awt.Color.BLACK);
                    Attendees.requestFocus();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (Attendees.getText().isBlank() && Attendees.hasFocus()){
                    Attendees.setForeground(java.awt.Color.GRAY);
                    Attendees.setText("Gente que asistira");
                } else if (!Attendees.getText().isBlank() && !Attendees.getText().equals("Gente que asistira")){
                    Attendees.setForeground(java.awt.Color.BLACK);
                }
            }
        });
        Attendees.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                    (c == KeyEvent.VK_BACK_SPACE) ||
                    (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
                if (Attendees.getText().equals("Gente que asistira")){
                    Attendees.setText(null);
                    Attendees.setForeground(java.awt.Color.BLACK);
                    Attendees.requestFocus();
                }
            }
        });
        InputData.add(Attendees);
        AttendeesInfo = new JLabel("Cantidad de personas maxima: 20000");
        
        AttendeesInfo.setFont(new java.awt.Font("Roboto",0,18));
        AttendeesInfo.setBounds(Attendees.getX(),Attendees.getY() - 25, Attendees.getWidth(),25); 
        InputData.add(AttendeesInfo);
        JSeparator Divisor = new JSeparator();
        
        Divisor.setBackground(Color.BLACK);
        Divisor.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        Divisor.setBounds(Attendees.getX(), Attendees.getY() + Attendees.getHeight() - 5, Attendees.getWidth(), 10);
        InputData.add(Divisor);
    }
    //Se crea el boton a partir del cual se decide que hacer
    private void setActionBTN(){
        ActionBTN = new JButton();
        
        ActionBTN.setBounds(Attendees.getX(), Description.getY() + Description.getHeight() - 50, Attendees.getWidth(), 50);
        ActionBTN.setFont(new java.awt.Font("Berlin Sans FB",0,22));
        ActionBTN.setText("Crear evento");
        ActionBTN.addActionListener((ActionEvent e) -> {
            switch (Hud.getActPanel()){
                case 3 -> AddEvent();
                case 4 -> ChangeEvent();
            }
        });
        ActionBTN.setFocusable(false);
        InputData.add(ActionBTN);
    }
    // -- DIFFERENT EVENT BTN --
    private void AddEvent(){
        if (isPossible()){
            AccMan.getCurrentAcc().AddEventCreated(this.Code.getText());
            switch (EventType.getSelectedIndex()){
                case 0 -> {
                        EvMan.AddSportEvent(Title.getText(), 
                            Code.getText(), 
                            Description.getText(), 
                            EventoDeportivo.getSport(), 
                            EventoDeportivo.getTeamName(0), 
                            EventoDeportivo.getTeamName(1),
                            Integer.parseInt(Attendees.getText()), 
                            Double.parseDouble(StadiumPrice.getText()), 
                            Fecha.getDate());
                        EvMan.getSportEvent(Code.getText(), 0).setTeam1(EventoDeportivo.getTeam1());
                        EvMan.getSportEvent(Code.getText(), 0).setTeam2(EventoDeportivo.getTeam2());
                        
                        JOptionPane.showMessageDialog(this, "Evento - "+Code.getText()+" - creado con exito");
                        CreateEvent();
                    }
                case 1 -> {
                    EvMan.AddMusicalEvent(Title.getText(), 
                            Code.getText(), 
                            Description.getText(), 
                            EventoMusical.getMusicGenre(), 
                            EventoMusical.getStaff(),  
                            Integer.parseInt(Attendees.getText()), 
                            Double.parseDouble(StadiumPrice.getText()), 
                            Fecha.getDate());
                        JOptionPane.showMessageDialog(this, "Evento - "+Code.getText()+" - creado con exito");
                        CreateEvent();
                }
                case 2 -> {
                    if (EventoReligioso.isPossible(Attendees.getText())){
                        EvMan.AddReligousEvent(Title.getText(), 
                                Code.getText(),
                                Description.getText(), 
                                (!EventoReligioso.getAlmasConvertidas().isBlank() && EventoReligioso.getAlmasConvertidas().equals("Almas convertidas"))?0:Integer.parseInt(EventoReligioso.getAlmasConvertidas()), 
                                Integer.parseInt(Attendees.getText()), 
                                Double.parseDouble(StadiumPrice.getText()), 
                                Fecha.getDate());
                        JOptionPane.showMessageDialog(this, "Evento - "+Code.getText()+" - creado con exito");
                        CreateEvent();
                    }
                }
            }
        }
    }
    
    private void ChangeEvent(){
        if (isPossible()){
            if (Fecha.getDate().after(EvMan.getEvent(Hud.getCodice(), 0).getRealizationDate()) || Fecha.getDate().equals(EvMan.getEvent(Hud.getCodice(), 0).getRealizationDate())){
                switch (EventType.getSelectedIndex()){
                    case 0 -> {
                        if (!EventoDeportivo.getTeam1().isEmpty() && !EventoDeportivo.getTeam2().isEmpty()){
                            EvMan.getSportEvent(Hud.getCodice(), 0).EditData(
                                Title.getText(), 
                                Hud.getCodice(), 
                                Description.getText(), 
                                EventoDeportivo.getSport(), 
                                EventoDeportivo.getTeamName(0), 
                                EventoDeportivo.getTeamName(1), 
                                Integer.parseInt(Attendees.getText()), 
                                Double.parseDouble(StadiumPrice.getText()), 
                                Fecha.getDate());
                            EvMan.getSportEvent(Code.getText(), 0).setTeam1(EventoDeportivo.getTeam1());
                            EvMan.getSportEvent(Code.getText(), 0).setTeam2(EventoDeportivo.getTeam2());
                            JOptionPane.showMessageDialog(this, "¡Cambios realizados con exito!");
                        } else JOptionPane.showMessageDialog(this, "Por favor ingrese un integrante en ambos equipos");
                    }
                    case 1 -> {
                        EvMan.getMusicalEvent(Hud.getCodice(), 0).EditData(
                                Title.getText(), 
                                Hud.getCodice(), 
                                Description.getText(), 
                                EventoMusical.getMusicGenre(), 
                                EventoMusical.getStaff(),  
                                Integer.parseInt(Attendees.getText()), 
                                Double.parseDouble(StadiumPrice.getText()), 
                                Fecha.getDate());
                        JOptionPane.showMessageDialog(this, "¡Cambios realizados con exito!");
                    }
                    case 2 -> {
                        if (EventoReligioso.isPossible(Attendees.getText())){
                                EvMan.getReligousEvent(Hud.getCodice(), 0).EditData(
                                    Title.getText(), 
                                    Hud.getCodice(), 
                                    Description.getText(), 
                                    (!EventoReligioso.getAlmasConvertidas().isBlank() && EventoReligioso.getAlmasConvertidas().equals("Almas convertidas"))?0:Integer.parseInt(EventoReligioso.getAlmasConvertidas()), 
                                    Integer.parseInt(Attendees.getText()), 
                                    Double.parseDouble(StadiumPrice.getText()), 
                                    Fecha.getDate());
                            JOptionPane.showMessageDialog(this, "¡Cambios realizados con exito!");
                        }
                    }
                }
            } else JOptionPane.showMessageDialog(this, "¡No puede atasar tanto la fecha!");
        }
    }
    // -- DIFFERENT EVENT BTN --
    //Se confirma la posibilidad de crear el evento
    private boolean isPossible(){
        try {
            if (!Title.getText().isBlank() && !Title.getText().equals("Ingrese el titulo del evento")){
                if (!Description.getText().isBlank() && !Description.getText().equals("Ingrese la descripcion del evento")){
                    if (!Attendees.getText().isBlank() && !Attendees.getText().equals("Gente que asistira")){
                        if (!StadiumPrice.getText().isBlank() && !StadiumPrice.getText().equals("Precio acordado por el estadio")){
                            if (Double.parseDouble(StadiumPrice.getText()) >= 1500){
                                if (MaxAttendees >= Integer.parseInt(Attendees.getText()) && Integer.parseInt(Attendees.getText()) >= 100){
                                    if (Fecha.getDate() != null){
                                        return true;
                                    } JOptionPane.showMessageDialog(this, "Por favor ingrese una fecha valida");
                                } else JOptionPane.showMessageDialog(this, "La cantidad de gente que asistira no debe superar el limite.\nPara crear un evento deben asistir minimo 100 personas.");
                            } else JOptionPane.showMessageDialog(this, "El precio minimo aceptado es de 1500 LPS. ");
                        } else JOptionPane.showMessageDialog(this, "Por favor ingrese el precio acordado por el estadio");
                    } else JOptionPane.showMessageDialog(this, "Por favor ingrese cuanta gente asistira al evento");
                } else JOptionPane.showMessageDialog(this, "Por favor ingrese una descripcion para el evento");
            } else JOptionPane.showMessageDialog(this, "Por favor ingrese el titulo del evento");
        } catch (HeadlessException | NumberFormatException Ex) {
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error leyendo uno de los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
        return false;
    }
    // -- DIFFERENT EVENT BTN --
    //Panel usado para mostrar los distintos campos que pueden ser llenados en cada evento
    private void setEventTypes(){
        EventoDeportivo = new SportsEvent(Attendees.getX(),Attendees.getY() + Attendees.getHeight() + 10,Attendees.getWidth(),ActionBTN.getY() - (Attendees.getY() + Attendees.getHeight()) - 20, AccMan, EvMan, Hud);
        
        EventoDeportivo.setBounds(EventoDeportivo.getBounds());
        EventoDeportivo.setVisible(false);
        InputData.add(EventoDeportivo);
        
        EventoMusical = new MusicEvent(Attendees.getX(),Attendees.getY() + Attendees.getHeight() + 10,Attendees.getWidth(),ActionBTN.getY() - (Attendees.getY() + Attendees.getHeight()) - 20, AccMan, EvMan, Hud);
        
        EventoMusical.setBounds(EventoMusical.getBounds());
        EventoMusical.setVisible(false);
        InputData.add(EventoMusical);
        
        EventoReligioso = new ReligousEvents(Attendees.getX(),Attendees.getY() + Attendees.getHeight() + 10,Attendees.getWidth(),ActionBTN.getY() - (Attendees.getY() + Attendees.getHeight()) - 20, AccMan, EvMan, Hud);
        
        EventoReligioso.setVisible(false);
        InputData.add(EventoReligioso);
    }
    //Funcion donde se crea el codigo usado en la creacion de eventos
    private String CreateCode(String Starter) {
        String Codice;
        do {
            Codice = CodeCreator(Starter, 0);
        } while (EvMan.isCodeValid(Codice, 0));
        return Codice;
    }
    //Se elige un caracter aleatorio
    private char getAChar(){
        return (char)(Randy.nextInt(26) + 'A');
    }
    //Se repite este codigo por 5 caracteres/numeros, usando un booleano random se determina si sera caracter o numero.
    private String CodeCreator(String Code, int CharPos){
        if (CharPos < 5){
            if (Randy.nextBoolean()){
                return CodeCreator(Code+getAChar(), CharPos+1);
            } else return CodeCreator(Code+Randy.nextInt(10), CharPos+1);
        }
        return Code;
    }
    // -- Swing elements --
    private JTextField Code, Title, StadiumPrice, Attendees;
    private ReligousEvents EventoReligioso;
    private SportsEvent EventoDeportivo;
    private MusicEvent EventoMusical;
    private JTextArea Description;
    private JLabel AttendeesInfo;
    private JComboBox EventType;
    private JDateChooser Fecha;
    private JButton ActionBTN;
    private JPanel InputData;
    // --Description Swing elements --
}
