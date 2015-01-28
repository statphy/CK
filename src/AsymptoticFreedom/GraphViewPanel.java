/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsymptoticFreedom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Geonmo
 */
public class GraphViewPanel extends ChartPanel {
    ArrayList<XYSeries> series = new ArrayList<>();
    public GraphViewPanel(JFreeChart chart){                        
        super(chart);
        bookingSeries();
        
    }
    public void bookingSeries(){        
        int quark_size = ViewPanel.quark_list.size();
        series.clear();
        for ( int i=0 ; i<quark_size ; i++) {
            for ( int j=i+1;  j<quark_size ; j++){
                String title = String.format("Potential of quarks%d-%d",i,j);            
                XYSeries s = new XYSeries(title);   
                for( int bin =0 ; bin<3000; bin++){
                   Quark quark1, quark2;
                   quark1 = ViewPanel.quark_list.get(i);
                   quark2 = ViewPanel.quark_list.get(j);
                   double value = quark1.calculatePotential(quark2, bin*0.1);                   
                   s.add( (double)bin*0.1, value);
                   //System.out.printf("%d %f\n",bin, value);
                }                
                series.add( s);                
            }
        }        
    }
    public JFreeChart getResultChart(){
        // XY시리즈 생성
        int quark_size = ViewPanel.quark_list.size();
        if ( quark_size<2) return new JFreeChart(new XYPlot());        

        // XY시리즈를 Dataset 형태로 변경
        XYSeriesCollection data = new XYSeriesCollection();
        //System.out.println(series.get(0).getY(30));
        for (XYSeries s : series) {
            data.addSeries(s);            
        }

        final JFreeChart chart = ChartFactory.createXYLineChart("Potential","Distance","Potential",data,PlotOrientation.VERTICAL,true,true,false);
        chart.setTitle("Potential of quarks"); // 차트 타이틀
        XYPlot plot = (XYPlot)chart.getPlot();
        //plot.addRangeMarker(new ValueMarker(15,Color.RED,new BasicStroke(2.0f)));
        for ( int i= 0 ; i< quark_size ; i++) {
            for ( int j=i+1 ; j<quark_size ; j++) {
                Quark quark1 = ViewPanel.quark_list.get(i);
                Quark quark2 = ViewPanel.quark_list.get(j);
                double distance = quark1.pos.distance( quark2.pos);              
                double value = quark1.calculatePotential(quark2);
                String anno_title = String.format("V_ %c-%c",quark1.color.charAt(0),quark2.color.charAt(0));
                //System.out.println(anno_title);
                XYPointerAnnotation pointer = new XYPointerAnnotation(anno_title,distance,value,3.0*Math.PI/4.0);
                plot.addAnnotation(pointer);                
            }         
        }
        plot.getRangeAxis().setRange(-500,4000);
        return chart;            
    }
    public void paintComponent(Graphics g2)
    {
        super.paintComponent(g2);
        this.setChart(getResultChart());    
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}