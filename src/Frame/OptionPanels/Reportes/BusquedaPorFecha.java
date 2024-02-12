package Frame.OptionPanels.Reportes;

import AccManager.AccManager;
import EventManager.EventManager;
import Frame.HudBar;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Locale;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BusquedaPorFecha extends JPanel{
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    
    public BusquedaPorFecha(AccManager AccMan, EventManager EvMan, HudBar Hud, int X, int Y, int Width, int Height){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.setLayout(null);
        this.setBackground(null);
        this.setBounds(X, Y, Width, Height);
        
        setMinDate();
        setMaxDate();
        setFilterBTN();
        
        setInfo();
        setGainsEventsGraph();
        setCantFilterEventsGraph();
    }
    //La fecha minima para buscar el evento
    private void setMinDate(){
        JLabel FechaMinTXT = new JLabel("Fecha de busqueda minima: ");
        FechaMinTXT.setFont(new java.awt.Font("Berlin Sans FB",0,14));
        FechaMinTXT.setBounds(10, 10, getWidth()/3 - 20, 25);
        
        add(FechaMinTXT);
        FechaMin = new JDateChooser();
        
        FechaMin.setFont(new java.awt.Font("Berlin Sans FB",0,20));
        FechaMin.setBounds(FechaMinTXT.getX(), FechaMinTXT.getHeight() + 10, getWidth()/3 - 20, 25);
        FechaMin.setDateFormatString("dd/MM/yyyy");
        FechaMin.setLocale(new Locale("es"));

        add(FechaMin);
    }
    //La fecha maxima para buscar la fecha
    private void setMaxDate(){
        JLabel FechaMaxTXT = new JLabel("Fecha de busqueda maxima: ");
        FechaMaxTXT.setFont(new java.awt.Font("Berlin Sans FB",0,14));
        FechaMaxTXT.setBounds(FechaMin.getX() + FechaMin.getWidth() + 20, 10, getWidth()/3 - 20, 25);
        
        add(FechaMaxTXT);
        FechaMax = new JDateChooser();
        
        FechaMax.setFont(new java.awt.Font("Berlin Sans FB",0,20));
        FechaMax.setBounds(FechaMaxTXT.getX(), FechaMaxTXT.getHeight() + 10, getWidth()/3 - 20, 25);
        FechaMax.setDateFormatString("dd/MM/yyyy");
        FechaMax.setLocale(new Locale("es"));

        add(FechaMax);
    }
    //Filtrar info a partir de las fechas
    private void setFilterBTN(){
        JButton Filtrar = new JButton();
        
        Filtrar.setBounds(FechaMax.getX() + FechaMax.getWidth() + 20, 10, FechaMax.getWidth(), 50);
        Filtrar.setFont(new java.awt.Font("Berlin Sans FB",0,22));
        Filtrar.setText("Filtrar datos");
        Filtrar.addActionListener((ActionEvent e) -> {
            try {
                if (FechaMin.getDate().before(FechaMax.getDate())){
                    if (FechaMax.getDate().after(FechaMin.getDate())){
                        setData();
                    } else JOptionPane.showMessageDialog(this, "La fecha maxima no puede ser menor a la minima");
                } else JOptionPane.showMessageDialog(this, "La fecha minima no puede ser mayor a la maxima");
            } catch (Exception Ex){
                JOptionPane.showMessageDialog(this, "Ha ocurrido un error leyendo las fechas", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(Filtrar);
    }
    //Info de los eventos
    private void setInfo(){
        Info = new JTextArea();
        
        Info.setBounds(10, FechaMin.getY() + FechaMin.getHeight() + 10, getWidth() - 20, getHeight()/2 - (FechaMin.getY() + FechaMin.getHeight() + 20));
        Info.setFont(new java.awt.Font("Roboto",0,14));
        Info.setWrapStyleWord(true);
        Info.setLineWrap(true);
        Info.setEditable(false);
        JScrollPane Skroll = new JScrollPane(Info);
        
        Skroll.setWheelScrollingEnabled(true);
        Skroll.setBounds(Info.getBounds());
        add(Skroll);
    }
    //Se consigue la data de los eventos 
    private void setData(){
        Info.setText(EvMan.getEventFilter("CODIGO - TIPO DE EVENTO - TITULO - FECHA D/M/YY - PRECIO POR EL ESTADIO\n", 0, FechaMin.getDate(), FechaMax.getDate()));
        GainsChart.setVisible(false);
        CantChart.setVisible(false);
        
        DefaultCategoryDataset GainsSet = new DefaultCategoryDataset();
        GainsSet.addValue(EvMan.getGanancias('R'), "Ganancias de eventos", "RELIGIOSOS");
        GainsSet.addValue(EvMan.getGanancias('D'), "Ganancias de eventos", "DEPORTIVOS");
        GainsSet.addValue(EvMan.getGanancias('M'), "Ganancias de eventos", "MUSICALES");
        
        GainsChart.setChart(ChartFactory.createBarChart("Ganancias de eventos", "Tipo de evento", "Cantidad", GainsSet, PlotOrientation.VERTICAL, false, true, false));
        GainsChart.setVisible(EvMan.getGanancias('R') > 0 || EvMan.getGanancias('D') > 0 || EvMan.getGanancias('M') > 0);
        
        DefaultCategoryDataset CantSet = new DefaultCategoryDataset();
        CantSet.addValue(EvMan.getCantidad('R'), "Cantidad de eventos", "RELIGIOSOS");
        CantSet.addValue(EvMan.getCantidad('D'), "Cantidad de eventos", "DEPORTIVOS");
        CantSet.addValue(EvMan.getCantidad('M'), "Cantidad de eventos", "MUSICALES");
        
        CantChart.setChart(ChartFactory.createBarChart("Cantidad de eventos", "Tipo de evento", "Cantidad", CantSet, PlotOrientation.VERTICAL, false, true, false));
        CantChart.setVisible(EvMan.getCantidad('R') > 0 || EvMan.getCantidad('D') > 0 || EvMan.getCantidad('M') > 0);
    }
    //Graficos con las ganancias de los eventos realizados
    private void setCantFilterEventsGraph(){
        CantChart = new ChartPanel(ChartFactory.createBarChart("Cantidad de eventos", "Tipo de evento", "Cantidad", null, PlotOrientation.VERTICAL, false, true, false));
        CantChart.setBounds(Info.getX() + 10, Info.getY() + Info.getHeight() + 20, Info.getWidth()/2 - 20, Info.getHeight()  + (FechaMin.getY() + FechaMin.getHeight()));
        add(CantChart);
    }
    //Graficos con las ganancias de los eventos realizados
    private void setGainsEventsGraph(){
        GainsChart = new ChartPanel(ChartFactory.createBarChart("Ganancias de eventos", "Tipo de evento", "Cantidad", null, PlotOrientation.VERTICAL, false, true, false));
        GainsChart.setBounds(getWidth()/2 + 10, Info.getY() + Info.getHeight() + 20, Info.getWidth()/2 - 10, Info.getHeight() + (FechaMin.getY() + FechaMin.getHeight()));
        add(GainsChart);
    }
    //Se setean los campos visibles o invisibles, dependiendo de el campo
    public final void Reveal(boolean Reveal){
        if (Reveal){
            Info.setText("CODIGO - TIPO DE EVENTO - TITULO - FECHA D/M/YY - PRECIO POR EL ESTADIO");
            GainsChart.setVisible(false);
            CantChart.setVisible(false);
            FechaMin.setDate(null);
            FechaMax.setDate(null);
        }
        setVisible(Reveal);
    }
    // -- SWING ELEMENTS --
    private ChartPanel CantChart, GainsChart;
    private JDateChooser FechaMin, FechaMax;
    private JTextArea Info;
    // -- SWING ELEMENTS --
}
