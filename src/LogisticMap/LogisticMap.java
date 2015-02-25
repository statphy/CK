/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogisticMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

/**
 *
 * @author Hyun-Myung Chun
 */

class Dynamics{
    
    int sample = 100000;
    int iteration = 1000;
    
    // parameterX[0][i] : x, parameter[1][i] : r;
    double parameterX[][] = new double[2][sample];
    
    public void Initialization(double minX, double maxX, double minR, double maxR){        
        for(int i=0;i<sample;i++){
            parameterX[0][i] = (maxX-minX)*Math.random()+minX;
            parameterX[1][i] = (maxR-minR)*Math.random()+minR;
        }
    }    
    
    public void Iteration(){
        for(int i=0;i<sample;i++){
            double x = parameterX[0][i];
            double r = parameterX[1][i];
            for(int j=0;j<iteration;j++){
                x = r*x*(1-x);
            }
            parameterX[0][i] = x;
        }
    }
}

public class LogisticMap extends javax.swing.JApplet {
        
    double minX=0;
    double maxX=1;
    double minR=2.8;
    double maxR=3.8;
    Dynamics dynamics;
    javax.swing.Timer timer;
    
    boolean graphOnOffSwitch = false;
    interface GRAPH {
        final int range1 = 0;
        final int range2 = 1;
        final int range3 = 2;
        final int range4 = 3;
    }
    int rangeGraphSwitch=-1;
    
    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    timer = new javax.swing.Timer(1,new aListener());
                    timer.stop();
                    
                    initComponents(); 
                    dynamics = new Dynamics();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    public class aListener implements ActionListener 
    {  
        public void actionPerformed(ActionEvent e) {
            mainPanel.removeAll();
            mainPanel.repaint();
        }
    };
    
    public class MainDisplay extends javax.swing.JPanel{
        MainDisplay(){super();}

        private class axis extends Path2D.Double{
            public axis(){
                double referenceLeft = 0.1*(double)getWidth();
                double referenceRight = 0.9*(double)getWidth();
                double referenceBottom = 0.9*(double)getHeight();
                double referenceTop = 0.1*(double)getHeight();
                
                this.moveTo(referenceLeft,referenceBottom);
                this.lineTo(referenceRight,referenceBottom); // x축
                this.moveTo(referenceLeft,referenceBottom);
                this.lineTo(referenceLeft,referenceTop); // y축
                
                double dx,dy;
                int tic,i;
                tic = (int)(referenceTop/6);
                dx = 0.8*getWidth()/20.;
                dy = 0.8*getHeight()/20.;
            
                for(i=1;i<20;i++){
                    if(i%2==0){
                        this.moveTo(referenceLeft+dx*i,referenceBottom-tic);
                        this.lineTo(referenceLeft+dx*i,referenceBottom+tic); // x축 tic (하단)
                        
                    }
                    if(i%5==0){
                        this.moveTo(referenceLeft-tic,referenceBottom-dy*i);
                        this.lineTo(referenceLeft+tic,referenceBottom-dy*i); // y축 tic
                    }       
                }
                
            }
        }
        
        public void paintComponent(Graphics g)
        {   
            super.paintComponent(g);
            
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Drawing x-axis and y-axis
            double referenceLeft = 0.1*(double)getWidth();
            double referenceRight = 0.9*(double)getWidth();
            double referenceBottom = 0.9*(double)getHeight();
            double referenceTop = 0.1*(double)getHeight();
            
            g2.setPaint(Color.black);
            g2.draw(new axis());
            
            int tic = (int)(referenceTop/6);
            double dx = 0.8*getWidth()/20.;
            double dy = 0.8*getHeight()/20.;
            String text;
            for(int i=0;i<=20;i++){
                    if(i%4==0){
                        text = String.format("%.6f",minR+(maxR-minR)/20*(double)i);
                        g2.drawString(text,(int)(referenceLeft+dx*i-4*text.length()),(int)(1.1*referenceBottom-tic));
                    }
                    if(i%5==0){
                        text = String.format("%.4f",minX+(maxX-minX)/20*(double)i);
                        g2.drawString(text,(int)(referenceLeft-tic-8*text.length()),(int)(1.01*referenceBottom-dy*i));
                    }
            }
            
            g2.setFont(new Font("TimesRoman",Font.ITALIC,18) );
            g2.drawString("r",(int)(0.91*getWidth()),(int)(0.91*getHeight()));
            g2.drawString("x*",(int)(0.1*getWidth()),(int)(0.09*getHeight()));
            
            dynamics.Initialization(minX,maxX,minR,maxR);
            dynamics.Iteration();
            
            if(graphOnOffSwitch){
                int positionX,positionR;
                int width=(int)(referenceRight-referenceLeft);
                int height=(int)(referenceTop-referenceBottom);
                int radius=1;
                for(int i=0;i<dynamics.sample;i++){
                    positionX=(int)referenceBottom+(int)(height*(dynamics.parameterX[0][i]-minX)/(maxX-minX))-radius/2;
                    positionR=(int)referenceLeft+(int)(width*(dynamics.parameterX[1][i]-minR)/(maxR-minR))-radius/2;
                    if(positionX<referenceBottom && positionX>referenceTop)g2.fillOval(positionR,positionX,radius,radius);
                }
            }
        }
    }
    
    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        mainPanel = new MainDisplay();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        controlPanel = new javax.swing.JPanel();
        exitButton = new javax.swing.JButton();
        drawButton = new javax.swing.JButton();
        rangeRText = new javax.swing.JLabel();
        minr = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        maxr = new javax.swing.JTextField();
        minrText = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        maxrText = new javax.swing.JLabel();
        noticeText = new javax.swing.JLabel();
        rangeXText = new javax.swing.JLabel();
        minx = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        maxx = new javax.swing.JTextField();
        maxxText = new javax.swing.JLabel();
        minxText = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        range1Button = new javax.swing.JButton();
        range2Button = new javax.swing.JButton();
        range3Button = new javax.swing.JButton();
        range4Button = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 69, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );

        jButton2.setText("Pause");

        jButton1.setText("Start");

        jButton5.setText("Graph 1");

        setMaximumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jInternalFrame1.setVisible(true);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/LogisticMap/LogisticMap.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel7.setText("Logistic Map :");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        drawButton.setText("Draw");
        drawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawButtonActionPerformed(evt);
            }
        });

        rangeRText.setText("Range of r :");

        minr.setText("2.8");
        minr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minrActionPerformed(evt);
            }
        });

        jLabel2.setText("~");

        maxr.setText("3.8");
        maxr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxrActionPerformed(evt);
            }
        });

        String str=minr.getText();
        minrText.setText("2.8");

        jLabel3.setText("~");

        maxrText.setText("3.8");

        noticeText.setText("Input your values and please push enter key.");

        rangeXText.setText("Range of x* :");

        minx.setText("0");
        minx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minxActionPerformed(evt);
            }
        });

        jLabel4.setText("~");

        maxx.setText("1");
        maxx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxxActionPerformed(evt);
            }
        });

        maxxText.setText("1");

        String str2=minx.getText();
        minxText.setText("0");

        jLabel5.setText("~");

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(noticeText)
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(rangeRText)
                        .addGap(18, 18, 18)
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(controlPanelLayout.createSequentialGroup()
                                .addComponent(minr, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(maxr, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(rangeXText))
                            .addGroup(controlPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(minrText, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(maxrText)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(controlPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(minxText, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(maxxText))
                            .addGroup(controlPanelLayout.createSequentialGroup()
                                .addComponent(minx, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(maxx, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(drawButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitButton)
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(drawButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exitButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, controlPanelLayout.createSequentialGroup()
                        .addComponent(noticeText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maxr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(rangeRText)
                            .addComponent(rangeXText)
                            .addComponent(minx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(maxx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(minxText, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(maxxText))
                            .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(minrText, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(maxrText)))))
                .addContainerGap())
        );

        jLabel1.setText("Self-similarity");

        range1Button.setText("Range 1");
        range1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                range1ButtonActionPerformed(evt);
            }
        });

        range2Button.setText("Range 2");
        range2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                range2ButtonActionPerformed(evt);
            }
        });

        range3Button.setText("Range 3");
        range3Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                range3ButtonActionPerformed(evt);
            }
        });

        range4Button.setText("Range 4");
        range4Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                range4ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(range1Button)
                    .addComponent(range2Button)
                    .addComponent(range3Button))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(range4Button)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(range1Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(range2Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(range3Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(range4Button)
                .addContainerGap(244, Short.MAX_VALUE))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jInternalFrame1.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void minrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minrActionPerformed
        double value;
        try{ 
            value = Double.parseDouble(minr.getText());
            if(value > maxR) minR=maxR-1;
            else minR=value;
            String str=String.valueOf(minR);
            minr.setText(str);
        }
        catch(java.lang.NumberFormatException e){// 문자 Input 에러 처리
                minr.setText("2.8");
                maxr.setText("3.8");
        }
        minrText.setText(minr.getText());
        maxrText.setText(maxr.getText());
    }//GEN-LAST:event_minrActionPerformed

    private void maxrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxrActionPerformed
        try{ 
            maxR = Double.parseDouble(maxr.getText());
        }
        catch(java.lang.NumberFormatException e){// 문자 Input 에러 처리
                minr.setText("2.8");
                maxr.setText("3.8");
        }
        minrText.setText(minr.getText());
        maxrText.setText(maxr.getText());
    }//GEN-LAST:event_maxrActionPerformed

    private void drawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawButtonActionPerformed
        graphOnOffSwitch = true;
        mainPanel.removeAll();
        mainPanel.repaint();
    }//GEN-LAST:event_drawButtonActionPerformed

    private void maxxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxxActionPerformed
        double value;
        try{ 
            value = Double.parseDouble(maxx.getText());
            if(value > minX && value <= 1) maxX=value;
            String str=String.valueOf(maxX);
            maxx.setText(str);
        }
        catch(java.lang.NumberFormatException e){// 문자 Input 에러 처리
                minx.setText("0");
                maxx.setText("1");
        }
        minxText.setText(minx.getText());
        maxxText.setText(maxx.getText());
    }//GEN-LAST:event_maxxActionPerformed

    private void minxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minxActionPerformed
        double value;
        try{ 
            value = Double.parseDouble(minx.getText());
            if(value < maxX && value >= 0) minX=value;
            String str=String.valueOf(minX);
            minx.setText(str);
        }
        catch(java.lang.NumberFormatException e){// 문자 Input 에러 처리
                minx.setText("0");
                maxx.setText("1");
        }
        minxText.setText(minx.getText());
        maxxText.setText(maxx.getText());
    }//GEN-LAST:event_minxActionPerformed

    private void range1ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_range1ButtonActionPerformed
        minX=0;
        maxX=1;
        minR=1;
        maxR=4;
        graphOnOffSwitch = true;
        mainPanel.removeAll();
        mainPanel.repaint();
    }//GEN-LAST:event_range1ButtonActionPerformed

    private void range2ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_range2ButtonActionPerformed
        minX=0.2722;
        maxX=0.7287;
        minR=3;
        maxR=3.678;
        graphOnOffSwitch = true;
        mainPanel.removeAll();
        mainPanel.repaint();
    }//GEN-LAST:event_range2ButtonActionPerformed

    private void range3ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_range3ButtonActionPerformed
        minX=0.4105;
        maxX=0.594;
        minR=3.45122;
        maxR=3.59383;
        graphOnOffSwitch = true;
        mainPanel.removeAll();
        mainPanel.repaint();
    }//GEN-LAST:event_range3ButtonActionPerformed

    private void range4ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_range4ButtonActionPerformed
        minX=0.4636;
        maxX=0.5357;
        minR=3.54416;
        maxR=3.57490;
        graphOnOffSwitch = true;
        mainPanel.removeAll();
        mainPanel.repaint();
    }//GEN-LAST:event_range4ButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JButton drawButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField maxr;
    private javax.swing.JLabel maxrText;
    private javax.swing.JTextField maxx;
    private javax.swing.JLabel maxxText;
    private javax.swing.JTextField minr;
    private javax.swing.JLabel minrText;
    private javax.swing.JTextField minx;
    private javax.swing.JLabel minxText;
    private javax.swing.JLabel noticeText;
    private javax.swing.JButton range1Button;
    private javax.swing.JButton range2Button;
    private javax.swing.JButton range3Button;
    private javax.swing.JButton range4Button;
    private javax.swing.JLabel rangeRText;
    private javax.swing.JLabel rangeXText;
    // End of variables declaration//GEN-END:variables
}