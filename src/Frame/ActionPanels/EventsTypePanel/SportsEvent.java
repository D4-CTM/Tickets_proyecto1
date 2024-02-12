package Frame.ActionPanels.EventsTypePanel;

import AccManager.AccManager;
import EventManager.EventManager;
import Frame.HudBar;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class SportsEvent extends JPanel{
    //Tecnicamente constantes
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    //Usados en la creacion de eventos
    private String TeamName1 = "Equipo #1", TeamName2 = "Equipo #2";
    private enum Sports{Futbol, Tenis, Rugby, Baseball};
    private  ArrayList<String> Team1, Team2;
        
    public SportsEvent(int X, int Y, int width, int height,AccManager AccMan, EventManager EvMan, HudBar Hud){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.setLayout(null);
        this.setBounds(X, Y, width, height);
        this.setBackground(java.awt.Color.LIGHT_GRAY);
        
        Team1 = new ArrayList();
        Team2 = new ArrayList();
        
        setAddTeamMember();
        setTeamName();
        setChangeNameTeamBTN();
        setSport();
        setTeams();
        setRemoveBTN();
    }
    //set team name and team view
    private void setTeamName(){
        TeamName = new JComboBox();
                
        TeamName.setBounds(0, 20, (getWidth()/2) - 5, 25);
        TeamName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {TeamName1, TeamName2}));
        TeamName.setFont(new java.awt.Font("Berlin Sans FB",0,18));
        TeamName.addActionListener((ActionEvent e) -> {
            Teams.setText(getTeamMembers((TeamName.getSelectedIndex() == 0)?Team1:Team2,"", 0));
        });
        add(TeamName);
        JLabel TeamNameTXT = new JLabel("Seleccione un equipo: ");
        
        TeamNameTXT.setFont(new java.awt.Font("Roboto",0,14));
        TeamNameTXT.setBounds(0, 0, TeamName.getWidth(),25);
        add(TeamNameTXT);
    }
    //Cambiar nombre del equipo
    private void setChangeNameTeamBTN(){
        ChangeTeamName = new JButton();
        
        ChangeTeamName.setBounds(TeamName.getWidth() + 5, TeamName.getY(), TeamName.getWidth() - 5, TeamName.getHeight());
        ChangeTeamName.setFont(new java.awt.Font("Berlin Sans FB",0,18));
        ChangeTeamName.setText("Nombrar equipo");
        ChangeTeamName.addActionListener((ActionEvent e) -> {
            String neoTeamName = JOptionPane.showInputDialog(this,"Ingrese el nombre del equipo: ");
            try {
                if (!neoTeamName.isBlank()){
                    if (TeamName.getSelectedIndex() == 0){
                        if (!neoTeamName.equals(TeamName2)){
                            TeamName1 = neoTeamName;
                            TeamName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {TeamName1, TeamName2}));
                        } else JOptionPane.showMessageDialog(this, "El nombre que ingreso ya esta tomado");
                    } else {
                        if (!neoTeamName.equals(TeamName1)){
                            TeamName2 = neoTeamName;
                            TeamName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {TeamName1, TeamName2}));
                            TeamName.setSelectedIndex(1);
                        } else JOptionPane.showMessageDialog(this, "El nombre que ingreso ya esta tomado");
                    }
                }
            } catch (Exception Ex){ }
        });
        ChangeTeamName.setFocusable(false);
        add(ChangeTeamName);
    }
    //setea los posibles generos musicales que se pueden llegar a tocar
    private void setSport(){
        Sport = new JComboBox();
                
        Sport.setBounds(getWidth()/2, TeamName.getHeight() + 25, TeamName.getWidth(), TeamName.getHeight());
        Sport.setModel(new javax.swing.DefaultComboBoxModel<>(Sports.values()));
        Sport.setFont(new java.awt.Font("Berlin Sans FB",0,20));
        add(Sport);
        JLabel TeamNameTXT = new JLabel("Eliga el deporte ha jugar: ");
        
        TeamNameTXT.setFont(new java.awt.Font("Roboto",0,15));
        TeamNameTXT.setBounds(0, Sport.getY(),Sport.getWidth(),Sport.getHeight());
        add(TeamNameTXT);
    }
    //Boton de agnadir jugador al equipo
    private void setAddTeamMember(){
        ActionBTN = new JButton();
        
        ActionBTN.setBounds(0, getHeight() - 35, getWidth()/2 - 5, 35);
        ActionBTN.setFont(new java.awt.Font("Berlin Sans FB",0,20));
        ActionBTN.setText("Añadir jugador");
        ActionBTN.addActionListener((ActionEvent e) -> {
        try {
            String neoMember = JOptionPane.showInputDialog(this,"Ingrese el nombre del jugador: ");
                if (!neoMember.isBlank()){
                    if (TeamName.getSelectedIndex() == 0){
                        if (!Team1.contains(neoMember)){
                            Team1.add(neoMember);
                            Teams.setText(getTeamMembers(Team1,"", 0));
                        } else JOptionPane.showMessageDialog(this, "Esta persona ya forma parte del equipo!");
                    } else {
                        if (!Team2.contains(neoMember)){
                            Team2.add(neoMember);
                            Teams.setText(getTeamMembers(Team2,"", 0));
                        } else JOptionPane.showMessageDialog(this, "Esta persona ya forma parte del equipo!");
                    }
                } else JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre del jugador");
            } catch (Exception ex){
                
            }
        });
        ActionBTN.setFocusable(false);
        add(ActionBTN);
    }
    //Boton pare eliminar jugador del equipo
    private void setRemoveBTN(){
        RemoveBTN = new JButton();
        
        RemoveBTN.setBounds(ActionBTN.getWidth() + 10, getHeight() - 35, getWidth()/2 - 5, 35);
        RemoveBTN.setFont(new java.awt.Font("Berlin Sans FB",0,20));
        RemoveBTN.setText("Quitar jugador");
        RemoveBTN.addActionListener((ActionEvent e) -> {
            if (TeamName.getSelectedIndex() == 0){
                if (!Team1.isEmpty()){
                    Object Member = JOptionPane.showInputDialog(this, "Seleccione el integrante del equipo que desea eliminar", "ELIMINAR INTEGRANTE", JOptionPane.DEFAULT_OPTION, null, Team1.toArray(), 0);
                    if (Member != null){
                        Team1.remove(Member.toString());
                        Teams.setText(getTeamMembers(Team1,"", 0));
                    }
                } else JOptionPane.showMessageDialog(this, "Primero debe ingresar algun miembro del equipo");
            } else {
                if (!Team2.isEmpty()){
                    Object Member = JOptionPane.showInputDialog(this, "Seleccione el integrante del equipo que desea eliminar", "ELIMINAR INTEGRANTE", JOptionPane.DEFAULT_OPTION, null, Team2.toArray(), 0);
                    if (Member != null){
                        Team2.remove(Member.toString());
                        Teams.setText(getTeamMembers(Team2,"", 0));
                    }
                } else JOptionPane.showMessageDialog(this, "Primero debe ingresar algun miembro en el equipo");
            }
        });
        RemoveBTN.setFocusable(false);
        add(RemoveBTN);
    }
    //Text area de los integrantes del equipo
    private void setTeams(){
        Teams = new JTextArea();
        
        Teams.setEditable(false);
        Teams.setWrapStyleWord(true);
        Teams.setForeground(java.awt.Color.BLACK);
        Teams.setFont(new java.awt.Font("Roboto",0,16));
        Teams.setBounds(0,Sport.getY() + Sport.getHeight() + 10,getWidth(),ActionBTN.getY() - (Sport.getY() + Sport.getHeight()) - 20);
        JScrollPane Skroll = new JScrollPane(Teams);
        
        Skroll.setWheelScrollingEnabled(true);
        Skroll.setBounds(Teams.getBounds());
        add(Skroll);
    }
    //Se consiguen los nombres de los integrantes del equipo
    private String getTeamMembers(ArrayList<String> Teams, String Members, int Pos){
        Members = (Pos < Teams.size())?Members + Teams.get(Pos) + "\n" + getTeamMembers(Teams, Members, Pos+1):Members;
        return Members;
    }
    //Limpia los campos de este escenario
    public final void ClearData(){
        TeamName1 = "Equipo #1"; 
        TeamName2 = "Equipo #2";
        Team1.clear();
        Team2.clear();

        TeamName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {TeamName1, TeamName2}));
        Teams.setText("");
    }
    //Agrega los datos del usuario
    public final void setData(){
        setTeamMembers1();
        setTeamMembers2();

        TeamName1 = EvMan.getSportEvent(Hud.getCodice(), 0).getTeamName(0);
        TeamName2 = EvMan.getSportEvent(Hud.getCodice(), 0).getTeamName(1);
        TeamName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {TeamName1, TeamName2}));        
        TeamName.setSelectedIndex(0);
        
        Sport.setSelectedIndex(
                switch (EvMan.getSportEvent(Hud.getCodice(), 0).getSport()){
                    default -> 0;
                    case "Tenis" -> 1;
                    case "Rugby" -> 2;
                    case "Baseball" -> 3;
                }
        );
    }
    //Se setean los botones invalidos o de plano invisibles
    public final void InvalidFields(){
        Sport.setEnabled(Hud.getActPanel() != 5);
        ActionBTN.setVisible(Hud.getActPanel() != 5);
        RemoveBTN.setVisible(Hud.getActPanel() != 5);
        ChangeTeamName.setVisible(Hud.getActPanel() != 5);
    }
    // -- SETTERS --
    private void setTeamMembers1(){
        for (String TeamMember : EvMan.getSportEvent(Hud.getCodice(), 0).getPlayers(0)){
            Team1.add(TeamMember);
        }
    }
    
    private void setTeamMembers2(){
        for (String TeamMember : EvMan.getSportEvent(Hud.getCodice(), 0).getPlayers(1)){
            Team2.add(TeamMember);
        }
    }
    // -- SETTERS --
    // -- GETTERS --
    public String getTeamName(int which){
        return (which == 0)?TeamName1:TeamName2;
    }
    
    public ArrayList<String> getTeam1(){
        return Team1;
    }
    
    public ArrayList<String> getTeam2(){
        return Team2;
    }
    
    public String getSport(){
        return Sport.getSelectedItem().toString();
    }
    
    // -- GETTERS --
    // -- SWING ELEMENTS --
    private JButton ActionBTN, RemoveBTN, ChangeTeamName;
    private JComboBox TeamName, Sport;
    private JTextArea Teams;
    // -- SWING ELEMENTS
}
