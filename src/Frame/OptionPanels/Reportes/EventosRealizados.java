package Frame.OptionPanels.Reportes;

import AccManager.AccManager;
import EventManager.EventManager;
import Frame.HudBar;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class EventosRealizados extends JPanel{
    //Tecnicamente constantes
    private final EventManager EvMan;
    private final AccManager AccMan;
    private final HudBar Hud;
    
    public EventosRealizados(AccManager AccMan, EventManager EvMan, HudBar Hud, int X, int Y, int Width, int Height){
        this.AccMan = AccMan;
        this.EvMan = EvMan;
        this.Hud = Hud;
        
        this.setLayout(null);
        this.setBackground(null);
        this.setBounds(X, Y, Width, Height);
        
        setInfo();
        setHowManuRealizedEventsGraph();
        setGainsFromRealizedEventsGraph();
    }
    //Se despliegan los datos de los eventos realizados
    private void setInfo(){
        Info = new JTextArea();
        
        Info.setBounds(10, 10, getWidth() - 20, getHeight()/2 - 20);
        Info.setFont(new java.awt.Font("Roboto",0,14));
        Info.setWrapStyleWord(true);
        Info.setLineWrap(true);
        Info.setEditable(false);
        JScrollPane Skroll = new JScrollPane(Info);
        
        Skroll.setWheelScrollingEnabled(true);
        Skroll.setBounds(Info.getBounds());
        add(Skroll);
    }
    //Graficos con las ganancias de los eventos realizados
    private void setHowManuRealizedEventsGraph(){
        CantChart = new ChartPanel(ChartFactory.createBarChart("Cantidad de eventos", "Tipo de evento", "Cantidad", null, PlotOrientation.VERTICAL, false, true, false));
        CantChart.setBounds(Info.getX() + 10, Info.getHeight() + 20, Info.getWidth()/2 - 20, Info.getHeight());
        add(CantChart);
    }
    //Graficos con las ganancias de los eventos realizados
    private void setGainsFromRealizedEventsGraph(){
        GainsChart = new ChartPanel(ChartFactory.createBarChart("Ganancias de eventos", "Tipo de evento", "Cantidad", null, PlotOrientation.VERTICAL, false, true, false));
        GainsChart.setBounds(getWidth()/2 + 10, Info.getHeight() + 20, Info.getWidth()/2 - 10, Info.getHeight());
        add(GainsChart);
    }
    //Se desplegan los eventos que han ocurrido en el grafico
    private void setEventsData(){
        switch (Hud.getActPanel()){
            case 6 -> Info.setText(EvMan.getEventosRealizados("CODIGO - TIPO DE EVENTO - TITULO - FECHA - PRECIO POR EL ESTADIO\n", 0));
            case 7 -> Info.setText(EvMan.getEventosFuturos("CODIGO - TIPO DE EVENTO - TITULO - FECHA - PRECIO POR EL ESTADIO\n", 0));
            case 8 -> Info.setText(EvMan.getEventosCancelados("CODIGO - TIPO DE EVENTO - TITULO - FECHA - PRECIO POR EL ESTADIO\n", 0));
        }
        
        DefaultCategoryDataset GainsSet = new DefaultCategoryDataset();
        GainsSet.addValue(EvMan.getGanancias('R'), "Ganancias de eventos", "RELIGIOSOS");
        GainsSet.addValue(EvMan.getGanancias('D'), "Ganancias de eventos", "DEPORTIVOS");
        GainsSet.addValue(EvMan.getGanancias('M'), "Ganancias de eventos", "MUSICALES");
        
        GainsChart.setChart(ChartFactory.createBarChart("Ganancias de eventos", "Tipo de evento", "Cantidad", GainsSet, PlotOrientation.VERTICAL, false, true, false));
        
        DefaultCategoryDataset CantSet = new DefaultCategoryDataset();
        CantSet.addValue(EvMan.getCantidad('R'), "Cantidad de eventos", "RELIGIOSOS");
        CantSet.addValue(EvMan.getCantidad('D'), "Cantidad de eventos", "DEPORTIVOS");
        CantSet.addValue(EvMan.getCantidad('M'), "Cantidad de eventos", "MUSICALES");
        
        CantChart.setChart(ChartFactory.createBarChart("Cantidad de eventos", "Tipo de evento", "Cantidad", CantSet, PlotOrientation.VERTICAL, false, true, false));
    }
    //Funcion usada para desplegar la data en pantalla
    public final void Reveal(boolean Reveal){
        if (Reveal){
            Info.setVisible(true);
            setEventsData();
        }
        this.setVisible(Reveal);
    }
    
    // -- SWING ELEMENTS --
    private ChartPanel CantChart, GainsChart;
    private JTextArea Info;
    // -- SWING ELEMENTS --
}
