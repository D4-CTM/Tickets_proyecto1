package Frame.ActionPanels.EventsTypePanel;

import AccManager.AccManager;
import EventManager.EventManager;
import Frame.HudBar;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class MusicEvent extends JPanel{
    //Tecnicamente constantes
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    //Usados en la creacion de eventos
    private enum MusicGenre{Pop, Rock, Rap, Clasica, Reggeaton, Otro};
    private final ArrayList<String> Staff;
    public MusicEvent(int X, int Y, int width, int height, AccManager AccMan, EventManager EvMan, HudBar Hud){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
                this.setLayout(null);
        this.setBounds(X, Y, width, height);
        this.setBackground(java.awt.Color.LIGHT_GRAY);
        
        Staff = new ArrayList();
        
        setMusicGenreChooser();
        setStaffTXTArea();
        setRemoveMember();
        setAddMember();
    }
    //ComboBox usado para elegir el genero musical
    private void setMusicGenreChooser(){
        Genre = new JComboBox();
        
        Genre.setBounds(0, 25, getWidth(), 35);
        Genre.setModel(new javax.swing.DefaultComboBoxModel<>(MusicGenre.values()));
        Genre.setFont(new java.awt.Font("Berlin Sans FB",0,20));
        add(Genre);
        JLabel GenreTXT = new JLabel("Que tipo de musica tocara la banda: ");

        GenreTXT.setFont(new java.awt.Font("Roboto",0,14));
        GenreTXT.setBounds(0, 0, Genre.getWidth(),25);
        add(GenreTXT);
    }
    //TextArea donde se desplega la informacion del staff y los integrantes
    private void setStaffTXTArea(){
        StaffBand = new JTextArea();
                
        StaffBand.setEditable(false);
        StaffBand.setWrapStyleWord(true);
        StaffBand.setForeground(java.awt.Color.BLACK);
        StaffBand.setFont(new java.awt.Font("Roboto",0,16));
        StaffBand.setBounds(0,Genre.getY() + Genre.getHeight() + 25,getWidth(), getHeight() - (Genre.getY() + Genre.getHeight() + 25) - 45);
        JScrollPane Skroll = new JScrollPane(StaffBand);
        
        Skroll.setWheelScrollingEnabled(true);
        Skroll.setBounds(StaffBand.getBounds());
        add(Skroll);
        JLabel StaffTXT = new JLabel("Inrantes de la banda y miembros del equipo: ");
        
        StaffTXT.setFont(new java.awt.Font("Roboto",0,14));
        StaffTXT.setBounds(0, StaffBand.getY() - 25, StaffBand.getWidth(),25);
        add(StaffTXT);
    }
    //Boton de agnadir integrante
    private void setAddMember(){
        AddBTN = new JButton();
        
        AddBTN.setBounds(0, getHeight() - 35, getWidth()/2 - 5, 35);
        AddBTN.setFont(new java.awt.Font("Berlin Sans FB",0,16));
        AddBTN.setText("Agregar integrante");
        AddBTN.addActionListener((ActionEvent e) -> {
            try {
                String neoMember = JOptionPane.showInputDialog(this,"Ingrese el nombre del integrante ");
                String Job = JOptionPane.showInputDialog(this, "Ingrese el rol que cumple esta persona");
                if (!neoMember.isBlank() && !Job.isBlank()){
                    if (!Staff.contains(neoMember + " - " + Job)){
                        Staff.add(neoMember + " - " + Job);
                        StaffBand.setText(getStaffMembers(Staff, "", 0));
                    } else JOptionPane.showMessageDialog(this, "Esta persona ya forma parte del equipo");
                } else JOptionPane.showMessageDialog(this, "Por favor no deje ninguno de estos campos en blanco");
            } catch (Exception ex){
                
            }
        });
        AddBTN.setFocusable(false);
        add(AddBTN);
    }
    //Boton usado para eliminar integrantes del staff
    private void setRemoveMember(){
        RemoveBTN = new JButton();
        
        RemoveBTN.setBounds(getWidth()/2 + 5, getHeight() - 35, getWidth()/2 - 5, 35);
        RemoveBTN.setFont(new java.awt.Font("Berlin Sans FB",0,12));
        RemoveBTN.setText("Quitar integrante");
        RemoveBTN.addActionListener((ActionEvent e) -> {
            if (!Staff.isEmpty()){
                Object Member = JOptionPane.showInputDialog(this, "Seleccione el integrante del equipo que desea eliminar", "ELIMINAR INTEGRANTE", JOptionPane.DEFAULT_OPTION, null, Staff.toArray(), 0);
                if (Member != null){
                    Staff.remove(Member.toString());
                    StaffBand.setText(getStaffMembers(Staff, "", 0));
                }
            } else JOptionPane.showMessageDialog(this, "Primero se debe ingresar un miembro en el equipo");
        });
        RemoveBTN.setFocusable(false);
        add(RemoveBTN);
    }
    //Setea el nombre de los integrantes
    private String getStaffMembers(ArrayList<String> Crew, String Members, int Pos){
        Members = (Pos < Crew.size())?Members + Crew.get(Pos) + "\n" + getStaffMembers(Crew, Members, Pos+1):Members;
        return Members;
    }
    //Se reinician los datos
    public final void ClearData(){
        Staff.clear();
        
        StaffBand.setText("");
        Genre.setSelectedIndex(0);
    }
    //Se setean los datos editables o no dependiendo del boton que fue presionado
    public final void InvalidFields(){
        Genre.setEnabled(Hud.getActPanel() != 5);
        AddBTN.setVisible(Hud.getActPanel() != 5);
        RemoveBTN.setVisible(Hud.getActPanel() != 5);
    }
    //Setea los datos
    public void setData(){
        setStaffBand();
        StaffBand.setText(getStaffMembers(Staff, "", 0));

        Genre.setSelectedIndex(
            switch (EvMan.getMusicalEvent(Hud.getCodice(), 0).getMusicGenre()){
                case "Pop" -> 0;
                case "Rock" -> 1;
                case "Rap" -> 2;
                case "Clasica" -> 3;
                case "Reggeaton" -> 4;
                default -> 6;
            }
        );
        InvalidFields();
    }
    // -- SETTERS --
    private void setStaffBand(){
        for (String Staff : EvMan.getMusicalEvent(Hud.getCodice(), 0).getStaff()){
            this.Staff.add(Staff);
        }
    }
    // -- SETTERS --
    // -- GETTERS --
    public ArrayList getStaff(){
        return Staff;
    }
    
    public String getMusicGenre(){
        return Genre.getSelectedItem().toString();
    }
    // -- GETTERS --
    // -- SWING ELEMENT --
    private JTextArea StaffBand;
    private JButton AddBTN, RemoveBTN;
    private JComboBox Genre;
    // -- SWING ELEMENT --
}