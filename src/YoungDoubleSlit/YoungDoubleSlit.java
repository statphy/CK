/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YoungDoubleSlit;
import java.applet.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
//import javax.swing.*;
/**
 *
 * @author Geonmo
 */
public class YoungDoubleSlit extends javax.swing.JApplet {
    int wavelength, slit_width, slit_distance;   
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
        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                    System.out.println("Start Timer!!");
                    timer = new javax.swing.Timer(100,new aListener());
                    isTimerOn = false;
                    //start();
                }                
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //timer.setRepeats(True);
        
        //javax.swing.Timer timer = new javax.swing.Timer(1000, this);        
    }
    public void start()
    {
        timer.start();
        isTimerOn = true;
    }
    public void stop()
    {
        timer.stop();
        isTimerOn = false;
    }
    public class aListener implements ActionListener 
    {
            public void actionPerformed(ActionEvent e) {
                System.out.println("On Timer!!");
                upperView.repaint();
                resultView.repaint();
            }
    };
    
    public class UpperViewPane extends javax.swing.JPanel{                
        ArrayList<Integer> radius    = new ArrayList<Integer>();        
        ArrayList<Integer> wavelength_array = new ArrayList<Integer>();        
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
        public void newWave()
        {
            radius.add(0);
            wavelength_array.add(wavelength);
        }
        public Color changeColor(int wave){
            Color co = Color.BLACK;
            if (wave>622 && wave<=780) co = Color.RED;
            else if( wave>597 && wave<=622) co = Color.ORANGE;
            else if( wave>577 && wave<=597) co = Color.YELLOW;
            else if( wave>492 && wave<=577) co = Color.GREEN;
            else if( wave>455 && wave<=492) co = Color.cyan;
            else if( wave<=455) co = Color.BLUE;            
            return co;
        }
        public void paintComponent(Graphics g)
        {   
            int size = radius.size();
            call= call+1;
            if ( call%(wavelength/100)==0 ) newWave();
            final int STEP = 5;
            super.paintComponent(g);
            g.setColor(Color.black);            
            g.drawRect(300,0,50,120);
            g.drawRect(300,this.getHeight()-120,50,120);
            for(int i=0 ; i< size ; i++){
                g.setColor(changeColor(wavelength_array.get(i)));                            
                radius.set(i,radius.get(i)+STEP);            
                g.drawOval(100-radius.get(i)/2,125-radius.get(i)/2,radius.get(i),radius.get(i));                
            }
            g.clearRect(351, 0, this.getWidth(),this.getHeight());
            System.out.format("%d %d\n",radius.get(0), wavelength );            
        }
        public void restart(){
            radius.clear();
            wavelength_array.clear();
            radius.add(0);
            wavelength_array.add(wavelength);
            call=0;
        }
    }
    public class ResultViewPane extends javax.swing.JPanel{
        int formula;
        ResultViewPane(){
            super();
            formula=0;
        }
        public void paintComponent(Graphics g2)
        {
            super.paintComponent(g2);
            int height = this.getHeight();
            int width = this.getWidth();
            
            g2.drawLine(0,height/5*4, width, (int)(height*0.8));
            
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
        jButton1 = new javax.swing.JButton();
        theTargetDistance = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        theEndButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        theWaveLength = new javax.swing.JSpinner();
        theRadioButton1 = new javax.swing.JRadioButton();
        theRadioButton2 = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        theDistanceSlits = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        theRadioButton3 = new javax.swing.JRadioButton();
        theSingleSlitWidth = new javax.swing.JSpinner();
        theWaveLengthSlider = new javax.swing.JSlider();
        upperView = new UpperViewPane();
        resultView = new ResultViewPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName("영의 이중슬릿"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 600));

        jInternalFrame1.setTitle("영의 이중슬릿 실험");
        jInternalFrame1.setMaximumSize(new java.awt.Dimension(800, 600));
        jInternalFrame1.setMinimumSize(new java.awt.Dimension(800, 600));
        jInternalFrame1.setPreferredSize(new java.awt.Dimension(800, 600));
        jInternalFrame1.setVisible(true);

        jButton1.setText("시작");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("표적 거리(cm)");

        theEndButton.setText("끝");
        theEndButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theEndButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("빛의 파장(nm)");

        theWaveLength.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theWaveLengthStateChanged(evt);
            }
        });

        theButtonGroup.add(theRadioButton1);
        theRadioButton1.setText("영의 이중슬릿");

        theButtonGroup.add(theRadioButton2);
        theRadioButton2.setText("전자의 이중슬릿");
        theRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theRadioButton2ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("슬릿1 열기");

        jCheckBox2.setText("슬릿2 열기");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jLabel4.setText("슬릿간 거리(mm)");

        jLabel1.setText("슬릿의 크기(mm)");

        theButtonGroup.add(theRadioButton3);
        theRadioButton3.setSelected(true);
        theRadioButton3.setText("단일 슬릿 실험");
        theRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theRadioButton3ActionPerformed(evt);
            }
        });

        theWaveLengthSlider.setMaximum(800);
        theWaveLengthSlider.setMinimum(300);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, theWaveLength, org.jdesktop.beansbinding.ELProperty.create("${value}"), theWaveLengthSlider, org.jdesktop.beansbinding.BeanProperty.create("value"));
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(theDistanceSlits, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(theTargetDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(22, 22, 22)
                                .addComponent(theWaveLength, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(theEndButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(theRadioButton2)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel1)
                                            .addComponent(theRadioButton1))
                                        .addGap(18, 18, 18)
                                        .addComponent(theSingleSlitWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jCheckBox2)
                                    .addComponent(jCheckBox1)
                                    .addComponent(theRadioButton3))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(theWaveLengthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addComponent(theRadioButton3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(theSingleSlitWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(theRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(theRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(theWaveLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(theWaveLengthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(theTargetDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(theDistanceSlits, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(17, 17, 17)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox2)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(theEndButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        javax.swing.GroupLayout resultViewLayout = new javax.swing.GroupLayout(resultView);
        resultView.setLayout(resultViewLayout);
        resultViewLayout.setHorizontalGroup(
            resultViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        resultViewLayout.setVerticalGroup(
            resultViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addComponent(upperView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if ( isTimerOn ) stop();
        else start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void theEndButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theEndButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_theEndButtonActionPerformed

    private void theRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_theRadioButton2ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void theRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theRadioButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_theRadioButton3ActionPerformed

    private void theWaveLengthSliderPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_theWaveLengthSliderPropertyChange
        // TODO add your handling code here:
        System.out.println("Found PropertyChanged.");
        String property = evt.getPropertyName();
       if ( "value".equals(property)) {
           wavelength = (int) evt.getNewValue();
       }
    }//GEN-LAST:event_theWaveLengthSliderPropertyChange

    private void theWaveLengthSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theWaveLengthSliderStateChanged
        // TODO add your handling code here:
        //System.out.println("Found state changed!");
        //System.out.println("Source : "+evt.getSource());
        wavelength = (int) theWaveLength.getValue();
    }//GEN-LAST:event_theWaveLengthSliderStateChanged

    private void theWaveLengthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_theWaveLengthStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_theWaveLengthStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel resultView;
    private javax.swing.ButtonGroup theButtonGroup;
    private javax.swing.JSpinner theDistanceSlits;
    private javax.swing.JButton theEndButton;
    private javax.swing.JRadioButton theRadioButton1;
    private javax.swing.JRadioButton theRadioButton2;
    private javax.swing.JRadioButton theRadioButton3;
    private javax.swing.JSpinner theSingleSlitWidth;
    private javax.swing.JSpinner theTargetDistance;
    private javax.swing.JSpinner theWaveLength;
    private javax.swing.JSlider theWaveLengthSlider;
    private javax.swing.JPanel upperView;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
