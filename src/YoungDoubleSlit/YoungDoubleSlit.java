/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YoungDoubleSlit;
import java.lang.Math;

import java.applet.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;

import java.util.ArrayList;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.renderer.category.BarRenderer;
//import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
//import org.jfree.chart.renderer.category.StandardBarPainter;
//import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.ui.GradientPaintTransformType;
//import org.jfree.ui.HorizontalAlignment;
//import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.ui.TextAnchor;
//import javax.swing.*;
/**
 *
 * @author Geonmo
 */
public class YoungDoubleSlit extends javax.swing.JApplet {
    int wavelength, slit_width, slit_distance,bin_size;   
    javax.swing.Timer timer ;
    boolean isTimerOn;    
    
    /**
     * Initializes the applet YoungDoubleSlit
     */

    @Override
    
    public void init() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(YoungDoubleSlit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YoungDoubleSlit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YoungDoubleSlit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YoungDoubleSlit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        wavelength =300;
        slit_width = 0;
        slit_distance= 0;        
        bin_size = 300;
        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();                    
                    isTimerOn = false;   
                    timer = null;
                }                
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }           
    }
    public void timerStart()
    {   if ( timer == null ){
            System.out.println("Start Timer!!");                    
            timer = new javax.swing.Timer(100,new aListener()); 
            timer.stop();
        }
        timer.start();
        isTimerOn = true;
    }
    public void timerStop()
    {
        timer.stop();
        isTimerOn = false;
    }
    public class aListener implements ActionListener 
    {       
                public void actionPerformed(ActionEvent e) {                    
                    if ( isTimerOn ) {
                        //System.out.println("On Timer!!");
                        //System.out.println( (int) theWaveLengthSlider.getValue()) ;
                        upperView.repaint();
                        resultView.repaint();
                    }
                    else {
                        System.out.println("Already Stop Timer!!");
                    }
                }
            //}
    };
    
    public class UpperViewPane extends javax.swing.JPanel{                
        ArrayList<Integer> radius    = new ArrayList<>();        
        ArrayList<Integer> wavelength_array = new ArrayList<>();        
        
        ArrayList<Integer> slit_radius    = new ArrayList<>();        
        ArrayList<Integer> slit_wavelength_array = new ArrayList<>();      
        
        int call;
        //int current_wavelength;
        boolean single_slit;
        boolean double_slit;
        UpperViewPane(){
            super();
            radius.add(0);
            call = 0;
            wavelength_array.add(wavelength);
            //current_wavelength = 300;            
        }
        public ArrayList<Integer> getRadius(){
            return radius;
        }
        public ArrayList<Integer> getWavelengths(){
            return wavelength_array;
        }
        public ArrayList<Integer> getSlitRadius(){
            return slit_radius;
        }
        public ArrayList<Integer> getSlitWavelengths(){
            return slit_wavelength_array;
        }
        public void newWave()
        {
            radius.add(0);
            wavelength_array.add(wavelength);
        }
        public Color changeColor(int wave){
            Color co = Color.BLACK;
            if (wave>622 ) co = Color.RED;
            else if( wave>597 && wave<=622) co = Color.ORANGE;
            else if( wave>577 && wave<=597) co = Color.YELLOW;
            else if( wave>492 && wave<=577) co = Color.GREEN;
            else if( wave>455 && wave<=492) co = Color.cyan;
            else if( wave>350 && wave<=455) co = Color.BLUE;            
            else if( wave<=350) co = Color.BLACK;            
            return co;
        }
        public void removeOvalWithRadius(){
            if ( radius.get(0)>(int)(this.getWidth()*0.5+100 ) ) {
                radius.remove(0);
                wavelength_array.remove(0);
            }
            return ;
        }
        
        public void paintComponent(Graphics g)                
        {   
            int height = this.getHeight();
            int width = this.getWidth();
            final int STEP = 5;            
            
            super.paintComponent(g);
            g.setColor(Color.black);            
            g.drawRect((int)(width*0.5),0,50,120);
            g.drawRect((int)(width*0.5),height-120,50,120);
            //g.clearRect(351, 0, this.getWidth(),this.getHeight());
            if ( !isTimerOn ) return;
            call= call+1;
            int ch = wavelength/100;
            if ( ch != 0 ) {
                if ( call%ch ==0 ) newWave();
            }
            
            removeOvalWithRadius();
            int size = radius.size();
            for(int i=0 ; i< size ; i++){
                g.setColor(changeColor(wavelength_array.get(i)));                            
                radius.set(i,radius.get(i)+STEP);            
                g.drawOval(100-radius.get(i)/2,(int)(height/2)-radius.get(i)/2,radius.get(i),radius.get(i));                
            }
            
            //System.out.format("%d %d\n",radius.get(0), wavelength );            
        }
        public void restart(){
            radius.clear();
            wavelength_array.clear();
            radius.add(0);
            wavelength_array.add(wavelength);
            call=0;
        }

    }

    public JFreeChart getResultChart(){
            DefaultCategoryDataset theAmp = new DefaultCategoryDataset();  
            Integer radius_size = ((UpperViewPane)upperView).getRadius().size();
            Integer wavelength_value = ((UpperViewPane)upperView).getWavelengths().get(0);
            //Integer bin_size = bin100;
            Double bin_width = 30.0/bin_size;
            
            for( int i=0 ; i< bin_size ; i++){
                double theta = Math.toRadians(-15.0 + bin_width*i);
                //double alpha = Math.PI* slit_width/wavelength_value*Math.sin(theta);
                //double beta  = Math.PI* slit_distance/wavelength_value*Math.sin(theta);
                double alpha = Math.PI* slit_width*Math.sin(theta);
                double beta  = Math.PI* slit_distance*Math.sin(theta);
                double amplitude = Math.cos(beta)*Math.cos(beta)*(Math.sin(alpha)/alpha)*(Math.sin(alpha)/alpha);
                theAmp.addValue(amplitude,"Amplitude",Double.toString(theta));
                /**
                System.out.println(slit_width);
                System.out.println(slit_distance);
                System.out.println(alpha);
                System.out.format("alpha : "+alpha+" bin : "+theta+"  value : "+amplitude+"\n");
                */
            }
            final LineAndShapeRenderer rend = new LineAndShapeRenderer();
            final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
            final ItemLabelPosition p_center = new ItemLabelPosition(
                ItemLabelAnchor.CENTER, TextAnchor.CENTER
            );
            final ItemLabelPosition p_below = new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT
            );
            Font f = new Font("Gulim", Font.BOLD, 14);
            Font axisF = new Font("Gulim", Font.PLAIN, 14);
                    rend.setBaseItemLabelGenerator(generator);
                    rend.setBaseItemLabelsVisible(false);
                    rend.setBaseShapesVisible(false);
                    rend.setDrawOutlines(true);
                    rend.setUseFillPaint(true);
                    rend.setBaseFillPaint(Color.WHITE);
                    rend.setBaseItemLabelFont(f);
                    rend.setBasePositiveItemLabelPosition(p_below);
                    rend.setSeriesPaint(0,new Color(219,121,22));
                    rend.setSeriesStroke(0,new BasicStroke(
                            2.0f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND,
                            3.0f)
                    );
            final CategoryPlot plot = new CategoryPlot();
            plot.setDataset(theAmp);
            plot.setRenderer(rend);
            plot.setOrientation(PlotOrientation.VERTICAL);             // 그래프 표시 방향
            plot.setRangeGridlinesVisible(true);                       // X축 가이드 라인 표시여부
            plot.setDomainGridlinesVisible(true);                      // Y축 가이드 라인 표시여부
            // 렌더링 순서 정의 : dataset 등록 순서대로 렌더링 ( 즉, 먼저 등록한게 아래로 깔림 )
            plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);      

            // X축 세팅
            plot.setDomainAxis(new CategoryAxis());       // X축 종류 설정
            plot.getDomainAxis().setTickLabelFont(axisF); // X축 눈금라벨 폰트 조정
            plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);       // 카테고리 라벨 위치 조정

            // Y축 세팅
            plot.setRangeAxis(new NumberAxis());                 // Y축 종류 설정
            plot.getRangeAxis().setTickLabelFont(axisF);  // Y축 눈금라벨 폰트 조정      

            // 세팅된 plot을 바탕으로 chart 생성
            final JFreeChart chart = new JFreeChart(plot);

            chart.setTitle("Amplitude of light"); // 차트 타이틀

//        TextTitle copyright = new TextTitle("JFreeChart WaferMapPlot", new Font("SansSerif", Font.PLAIN, 9));

//        copyright.setHorizontalAlignment(HorizontalAlignment.RIGHT);

//        chart.addSubtitle(copyright);  // 차트 서브 타이틀
          
        return chart;
            
    }
        
    
    
    
    public class ResultViewPane extends ChartPanel{        
        ResultViewPane(JFreeChart chart){                        
            super(chart);
        }

        public void paintComponent(Graphics g2)
        {
            super.paintComponent(g2);
            int height = this.getHeight();
            int width = this.getWidth();
            if ( isTimerOn ) {
                this.setChart(getResultChart());
            }
            
            //g2.drawLine(0,height/5*4, width, (int)(height*0.8));
            
        }
        
        /**
        public void repaint()
        {
            
        }
        */        
    }
    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        theButtonGroup = new javax.swing.ButtonGroup();
        jFileChooser1 = new javax.swing.JFileChooser();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        StartButton = new javax.swing.JButton();
        theEndButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        theWaveLength = new javax.swing.JSpinner();
        theRadioButton1 = new javax.swing.JRadioButton();
        theRadioButton2 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        theDistanceSlits = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        theSingleSlitRadioButton = new javax.swing.JRadioButton();
        theSlitWidth = new javax.swing.JSpinner();
        theWaveLengthSlider = new javax.swing.JSlider();
        ClearButton = new javax.swing.JButton();
        theWidthSlider = new javax.swing.JSlider();
        theDistanceSlider = new javax.swing.JSlider();
        upperView = new UpperViewPane();
        resultView = new ResultViewPane(getResultChart());
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName("영의 이중슬릿"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 600));

        jInternalFrame1.setTitle("Young's double slit experiment");
        jInternalFrame1.setMaximumSize(new java.awt.Dimension(800, 600));
        jInternalFrame1.setMinimumSize(new java.awt.Dimension(800, 600));
        jInternalFrame1.setPreferredSize(new java.awt.Dimension(800, 600));
        jInternalFrame1.setVisible(true);

        StartButton.setText("Start");
        StartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartButtonActionPerformed(evt);
            }
        });

        theEndButton.setText("Quit");
        theEndButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theEndButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("WaveLength(nm)");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theWaveLengthSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), theWaveLength, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theWaveLength.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theWaveLengthStateChanged(evt);
            }
        });

        theButtonGroup.add(theRadioButton1);
        theRadioButton1.setText("Double Slit Experiment");
        theRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theRadioButton1ActionPerformed(evt);
            }
        });

        theButtonGroup.add(theRadioButton2);
        theRadioButton2.setText("Double slit using electron.");
        theRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theRadioButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Slit Distance");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theDistanceSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), theDistanceSlits, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theDistanceSlits.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theDistanceSlitsStateChanged(evt);
            }
        });

        jLabel1.setText("Slit width");

        theButtonGroup.add(theSingleSlitRadioButton);
        theSingleSlitRadioButton.setSelected(true);
        theSingleSlitRadioButton.setText("Single Slit( d=0 )");
        theSingleSlitRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theSingleSlitRadioButtonStateChanged(evt);
            }
        });
        theSingleSlitRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theSingleSlitRadioButtonActionPerformed(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theWidthSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), theSlitWidth, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theSlitWidth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theSlitWidthStateChanged(evt);
            }
        });

        theWaveLengthSlider.setMaximum(800);
        theWaveLengthSlider.setMinimum(300);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theWaveLength, org.jdesktop.beansbinding.ELProperty.create("${value}"), theWaveLengthSlider, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theWaveLengthSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theWaveLengthSliderStateChanged(evt);
            }
        });
        theWaveLengthSlider.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                theWaveLengthSliderPropertyChange(evt);
            }
        });

        ClearButton.setText("Clear");
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });

        theWidthSlider.setMaximum(50);
        theWidthSlider.setMinimum(1);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theSlitWidth, org.jdesktop.beansbinding.ELProperty.create("${value}"), theWidthSlider, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theWidthSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theWidthSliderStateChanged(evt);
            }
        });
        theWidthSlider.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                theWidthSliderPropertyChange(evt);
            }
        });

        theDistanceSlider.setMaximum(300);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theDistanceSlits, org.jdesktop.beansbinding.ELProperty.create("${value}"), theDistanceSlider, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        theDistanceSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theDistanceSliderStateChanged(evt);
            }
        });
        theDistanceSlider.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                theDistanceSliderPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(226, 226, 226))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(theDistanceSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                    .addComponent(theWidthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(theSlitWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(theWaveLength, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(theDistanceSlits, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(StartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(theEndButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(theRadioButton1)
                            .addComponent(theSingleSlitRadioButton)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(theRadioButton2))
                            .addComponent(theWaveLengthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(theSingleSlitRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(theRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(theRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(theWidthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(theSlitWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(theDistanceSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(theWaveLength, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(theWaveLengthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(StartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(theEndButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(theDistanceSlits, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        upperView.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout upperViewLayout = new javax.swing.GroupLayout(upperView);
        upperView.setLayout(upperViewLayout);
        upperViewLayout.setHorizontalGroup(
            upperViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );
        upperViewLayout.setVerticalGroup(
            upperViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        resultView.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        resultView.setPreferredSize(new java.awt.Dimension(566, 243));

        javax.swing.GroupLayout resultViewLayout = new javax.swing.GroupLayout(resultView);
        resultView.setLayout(resultViewLayout);
        resultViewLayout.setHorizontalGroup(
            resultViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        resultViewLayout.setVerticalGroup(
            resultViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 239, Short.MAX_VALUE)
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jInternalFrame1.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(upperView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addComponent(upperView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void theDistanceSliderPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_theDistanceSliderPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_theDistanceSliderPropertyChange

    private void theDistanceSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theDistanceSliderStateChanged
        // TODO add your handling code here:
        slit_distance = (int) theDistanceSlider.getValue();
        theDistanceSlits.setValue(slit_distance);
    }//GEN-LAST:event_theDistanceSliderStateChanged

    private void theWidthSliderPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_theWidthSliderPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_theWidthSliderPropertyChange

    private void theWidthSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theWidthSliderStateChanged
        // TODO add your handling code here:
        slit_width = (int)theWidthSlider.getValue();
        theSlitWidth.setValue(slit_width);
    }//GEN-LAST:event_theWidthSliderStateChanged

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearButtonActionPerformed
        // 선언은 JPanel로 되어 있기 때문에 사용하려면 형변환 필요.
        ((UpperViewPane)upperView).restart();// TODO add your handling code here:
    }//GEN-LAST:event_ClearButtonActionPerformed

    private void theWaveLengthSliderPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_theWaveLengthSliderPropertyChange
        // TODO add your handling code here:
        //System.out.println("Found PropertyChanged.");
        String property = evt.getPropertyName();
        if ( "value".equals(property)) {
            wavelength = (int) evt.getNewValue();
        }
    }//GEN-LAST:event_theWaveLengthSliderPropertyChange

    private void theWaveLengthSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theWaveLengthSliderStateChanged
        // TODO add your handling code here:
        //System.out.println("Found state changed!");
        //System.out.println("Source : "+evt.getSource());
        wavelength = (int) theWaveLengthSlider.getValue();
        theWaveLength.setValue((int)theWaveLengthSlider.getValue());
    }//GEN-LAST:event_theWaveLengthSliderStateChanged

    private void theSlitWidthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theSlitWidthStateChanged
        slit_width = (int) theSlitWidth.getValue();  // TODO add your handling code here:
        theWidthSlider.setValue(slit_width);
    }//GEN-LAST:event_theSlitWidthStateChanged

    private void theSingleSlitRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theSingleSlitRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_theSingleSlitRadioButtonActionPerformed

    private void theDistanceSlitsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theDistanceSlitsStateChanged
        // TODO add your handling code here:
        slit_distance = (int) theDistanceSlits.getValue();
        theDistanceSlider.setValue(slit_distance);

    }//GEN-LAST:event_theDistanceSlitsStateChanged

    private void theRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_theRadioButton2ActionPerformed

    private void theRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_theRadioButton1ActionPerformed

    private void theWaveLengthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theWaveLengthStateChanged
        // TODO add your handling code here:
        //System.out.println("Source : "+evt.getSource());
        wavelength = (int) theWaveLength.getValue();
        theWaveLengthSlider.setValue((int)theWaveLength.getValue());
    }//GEN-LAST:event_theWaveLengthStateChanged

    private void theEndButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theEndButtonActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        //dispose();
        System.exit(0);
    }//GEN-LAST:event_theEndButtonActionPerformed

    private void StartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartButtonActionPerformed
        // TODO add your handling code here:
        if ( isTimerOn ) timerStop();
        else timerStart();
    }//GEN-LAST:event_StartButtonActionPerformed

    private void theSingleSlitRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theSingleSlitRadioButtonStateChanged
        // TODO add your handling code here:
        System.out.println(theSingleSlitRadioButton.getSelectedIcon());
    }//GEN-LAST:event_theSingleSlitRadioButtonStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ClearButton;
    private javax.swing.JButton StartButton;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel resultView;
    private javax.swing.ButtonGroup theButtonGroup;
    private javax.swing.JSlider theDistanceSlider;
    private javax.swing.JSpinner theDistanceSlits;
    private javax.swing.JButton theEndButton;
    private javax.swing.JRadioButton theRadioButton1;
    private javax.swing.JRadioButton theRadioButton2;
    private javax.swing.JRadioButton theSingleSlitRadioButton;
    private javax.swing.JSpinner theSlitWidth;
    private javax.swing.JSpinner theWaveLength;
    private javax.swing.JSlider theWaveLengthSlider;
    private javax.swing.JSlider theWidthSlider;
    private javax.swing.JPanel upperView;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
