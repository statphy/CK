/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projectile;

import java.awt.Color;
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

class Particle{
    
    double mass;
    double positionX;
    double positionY;
    double velocityX;
    double velocityY;
    double speed = Math.sqrt(velocityX*velocityX + velocityY*velocityY);
    
}

class Dynamics extends Particle{
    
    static final double GRAVITATIONAL_CONSTANT=9.8;
    
    double dt = 0.01;
    
    double positionXArray[] = new double[10000];
    double positionYArray[] = new double[10000];
    double velocityXArray[] = new double[10000];
    double velocityYArray[] = new double[10000];
    
    double velocityXTemp;
    double velocityYTemp;
    
    public void TimeEvolution(Particle particle, Boolean linearSwitch, double linearFrictionCoeff,
                                                 Boolean quadraticSwitch, double quadraticFrictionCoeff){
        particle.positionX += particle.velocityX * dt;
        particle.positionY += particle.velocityY * dt;
        velocityXTemp = particle.velocityX;
        velocityYTemp = particle.velocityY - GRAVITATIONAL_CONSTANT*dt;
        
        // 저항의 일차항에 의한 속도감소
        if(linearSwitch){
            velocityXTemp -= linearFrictionCoeff*particle.velocityX*dt;
            velocityYTemp -= linearFrictionCoeff*particle.velocityY*dt;
        }
        
        // 저항의 이차항에 의한 속도감소
        if(quadraticSwitch){
            if(velocityXTemp>0){
                velocityXTemp -= quadraticFrictionCoeff*particle.velocityX*particle.velocityX*dt;
            }
            else velocityXTemp += quadraticFrictionCoeff*particle.velocityX*particle.velocityX*dt;
            
            if(velocityYTemp>0){
                velocityYTemp -= quadraticFrictionCoeff*particle.velocityY*particle.velocityY*dt;
            }
            else velocityYTemp += quadraticFrictionCoeff*particle.velocityY*particle.velocityY*dt;
        }
        particle.velocityX = velocityXTemp;
        particle.velocityY = velocityYTemp;
    }
    
}

public class Projectile extends javax.swing.JApplet {

    static final double GRAVITATIONAL_CONSTANT=9.8;
    static final double SPEED_MAX=100;
    
    boolean linearSwitch = false;
    boolean quadraticSwitch = false;
    interface GRAPH {
        final int xtgraph = 0;
        final int ytgraph = 1;
        final int vxtgraph = 2;
        final int vytgraph = 3;
    }
    int graphSwitch=-1; 
    
    double massOfParticle;
    double initialAngle;
    double initialSpeed;
    double linearFrictionCoeff;
    double quadraticFrictionCoeff;
    
    ArrayList<Double> positionXArray = new ArrayList<>();
    ArrayList<Double> positionYArray = new ArrayList<>();
    ArrayList<Double> velocityXArray = new ArrayList<>();
    ArrayList<Double> velocityYArray = new ArrayList<>();

    Particle particle;
    Dynamics dynamics;
    javax.swing.Timer timer;
    /**
     * Initializes the applet Projectile
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
            java.util.logging.Logger.getLogger(Projectile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Projectile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Projectile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Projectile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents(); 
                    timer = new javax.swing.Timer(1,new aListener());
                    timer.stop();
                    particle = new Particle();
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
        
        public void paintComponent(Graphics g)
        {   
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            
            double referencePositionX = 0.1*(double)getWidth();
            double referencePositionY = 0.9*(double)getHeight(); // initial position in main panel
            
            double maximumDisplacementX = SPEED_MAX*SPEED_MAX/GRAVITATIONAL_CONSTANT;    
            double maximumDisplacementY = SPEED_MAX*SPEED_MAX/(2*GRAVITATIONAL_CONSTANT);
            // to rescale distance
            Ellipse2D.Double object
                = new Ellipse2D.Double(referencePositionX+(particle.positionX/maximumDisplacementX)*(double)getWidth()*0.8,
                                       referencePositionY-(particle.positionY/maximumDisplacementY)*(double)getHeight()*0.8,
                                       15,15);
                g2.setColor(Color.black);
                g2.fill(object);
                
            positionXArray.add(particle.positionX);
            positionYArray.add(particle.positionY);
            velocityXArray.add(particle.velocityX);
            velocityYArray.add(particle.velocityY);
            
            for(int i=0;i<positionXArray.size();i += 100){
                object
                = new Ellipse2D.Double(referencePositionX+(positionXArray.get(i)/maximumDisplacementX)*(double)getWidth()*0.8+5,
                                       referencePositionY-(positionYArray.get(i)/maximumDisplacementY)*(double)getHeight()*0.8+5,
                                       5,5);
                g2.setColor(Color.blue);
                g2.draw(object);
            }
            
            if(particle.positionY < 0) timer.stop();
            dynamics.TimeEvolution(particle,linearSwitch,linearFrictionCoeff,quadraticSwitch,quadraticFrictionCoeff);
        }
    }
     
    public class GraphDisplay extends javax.swing.JPanel{                
        GraphDisplay(){super();}
        
        
        private class axis extends Path2D.Double{
            public axis(){
                
                double MIN_X = 0.1*(double)getWidth();
                double MAX_X = 0.9*(double)getWidth();
                double MIN_Y = 0.9*(double)getHeight();
                double MAX_Y = 0.1*(double)getHeight();
                
                this.moveTo(MIN_X,MIN_Y);
                this.lineTo(MAX_X,MIN_Y); // x축
                this.moveTo(MIN_X,MIN_Y);
                this.lineTo(MIN_X,MAX_Y); // y축
                
                double dx,dy;
                int tic,i;
                tic = (int)(MAX_Y/6);
                dx = 0.8*getWidth()/10.;
                dy = 0.8*getHeight()/10.;
            
                for(i=1;i<10;i++){
                    this.moveTo(MIN_X+dx*i,MIN_Y-tic);
                    this.lineTo(MIN_X+dx*i,MIN_Y+tic); // x축 tic
                    if(i%2==0){
                        this.moveTo(MIN_X-tic,MIN_Y-dy*i);
                        this.lineTo(MIN_X+tic,MIN_Y-dy*i); // y축 tic
                    }
                }
            }
        }
        
        private class noFriction extends Path2D.Double{
            public noFriction(){
                
                double MIN_X = 0.1*(double)getWidth();
                double MAX_X = 0.9*(double)getWidth();
                double MIN_Y = 0.9*(double)getHeight();
                double MAX_Y = 0.1*(double)getHeight();
                
                final double dt = (MAX_X-MIN_X)/(double)positionXArray.size();
                switch(graphSwitch){
                    case GRAPH.xtgraph :
                        this.moveTo(0.1*(double)getWidth(),0.9*(double)getHeight()); // 원점
                        for(int i=0;i<positionXArray.size();i++){
                            if(i%20>=10){ //dashed line
                                this.moveTo(MIN_X+(double)i*dt,
                                            MIN_Y+i*dt*(MAX_Y-MIN_Y)/(MAX_X-MIN_X));
                            }
                            else this.lineTo(MIN_X+(double)i*dt,
                                             MIN_Y+i*dt*(MAX_Y-MIN_Y)/(MAX_X-MIN_X));
                        }
                        break;
                    case GRAPH.ytgraph :
                        this.moveTo(0.1*(double)getWidth(),0.9*(double)getHeight()); // 원점
                        for(int i=0;i<positionYArray.size();i++)
                            this.lineTo(MIN_X+(double)i*dt,
                                        MIN_Y+positionYArray.get(i)*(MAX_Y-MIN_Y)/positionYArray.get(positionYArray.size()/2-1));
                        break;
                    case GRAPH.vxtgraph :
                        this.moveTo(0.1*(double)getWidth(),0.9*(double)getHeight()); // 원점
                        for(int i=0;i<velocityXArray.size();i++)
                            this.lineTo(MIN_X*(double)getWidth()+(double)i*dt,
                                        MIN_Y+velocityXArray.get(i)*(MAX_Y-MIN_Y)/velocityXArray.get(0));
                        break;
                    case GRAPH.vytgraph :
                        this.moveTo(0.1*(double)getWidth(),0.9*(double)getHeight()); // 원점
                        for(int i=0;i<velocityYArray.size();i++)
                            this.lineTo(MIN_X+(double)i*dt,
                                        MIN_Y+velocityYArray.get(i)*(MAX_Y-MIN_Y)/velocityYArray.get(0));
                        break;
                    }
                }
            }
        
        private class withFriction extends Path2D.Double{
            public withFriction(){
                double MIN_X = 0.1*(double)getWidth();
                double MAX_X = 0.9*(double)getWidth();
                double MIN_Y = 0.9*(double)getHeight();
                double MAX_Y = 0.1*(double)getHeight();
                
                final double dt = (MAX_X-MIN_X)/(double)positionXArray.size();
                switch(graphSwitch){
                    case GRAPH.xtgraph :
                        this.moveTo(0.1*(double)getWidth(),0.9*(double)getHeight()); // 원점
                        for(int i=0;i<positionXArray.size();i++)
                            this.lineTo(MIN_X+(double)i*dt,
                                        MIN_Y+positionXArray.get(i)*(MAX_Y-MIN_Y)/positionXArray.get(positionXArray.size()-1));
                        break;
                    case GRAPH.ytgraph :
                        this.moveTo(0.1*(double)getWidth(),0.9*(double)getHeight()); // 원점
                        for(int i=0;i<positionYArray.size();i++)
                            this.lineTo(MIN_X+(double)i*dt,
                                        MIN_Y+positionYArray.get(i)*(MAX_Y-MIN_Y)/positionYArray.get(positionYArray.size()/2-1));
                        break;
                    case GRAPH.vxtgraph :
                        this.moveTo(0.1*(double)getWidth(),0.9*(double)getHeight()); // 원점
                        for(int i=0;i<velocityXArray.size();i++)
                            this.lineTo(MIN_X*(double)getWidth()+(double)i*dt,
                                        MIN_Y+velocityXArray.get(i)*(MAX_Y-MIN_Y)/velocityXArray.get(0));
                        break;
                    case GRAPH.vytgraph :
                        this.moveTo(0.1*(double)getWidth(),0.9*(double)getHeight()); // 원점
                        for(int i=0;i<velocityYArray.size();i++)
                            this.lineTo(MIN_X+(double)i*dt,
                                        MIN_Y+velocityYArray.get(i)*(MAX_Y-MIN_Y)/velocityYArray.get(0));
                        break;
                    }
                }
            }
        // class for the trial function

        public void paintComponent(Graphics g)
        {   
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            
            ArrayList<Double> tempArray = new ArrayList<>();
            
            // Drawing x-axis and y-axis
            g2.setPaint(Color.black);
            g2.draw(new axis());
            
            // Axis Label
            switch(graphSwitch){
                    case GRAPH.xtgraph :
                        g2.drawString("t",(int)(0.91*getWidth()),(int)(0.91*getHeight()));
                        g2.drawString("x",(int)(0.08*getWidth()),(int)(0.11*getHeight()));
                        tempArray = positionXArray;
                        break;
                    case GRAPH.ytgraph :
                        g2.drawString("t",(int)(0.91*getWidth()),(int)(0.91*getHeight()));
                        g2.drawString("y",(int)(0.08*getWidth()),(int)(0.11*getHeight()));
                        tempArray = positionYArray;
                        break;
                    case GRAPH.vxtgraph :
                        g2.drawString("t",(int)(0.91*getWidth()),(int)(0.91*getHeight()));
                        g2.drawString("Vx",(int)(0.07*getWidth()),(int)(0.11*getHeight()));
                        tempArray = velocityXArray;
                        break;
                    case GRAPH.vytgraph :
                        g2.drawString("t",(int)(0.91*getWidth()),(int)(0.91*getHeight()));
                        g2.drawString("Vy",(int)(0.07*getWidth()),(int)(0.11*getHeight()));
                        tempArray = velocityYArray;
                        break;    
            }
            
            double dx,dy;
            int xtic,ytic,i,step;
            String text;
            xtic = (int)(0.06*getHeight());
            ytic = (int)(0.03*getWidth());
            dx = 0.8*getWidth()/10.;
            dy = 0.8*getHeight()/10.;
            step = 0;
            for(i=0;i<tempArray.size();i += tempArray.size()/10 + 1){
                g2.drawString(String.format("%.2f",i*0.01),(int)(0.08*getWidth()+dx*step),(int)(0.9*getHeight()+ xtic));
                if(step%2==0){
                    text = String.format("%.2f",tempArray.get(i));
                    g2.drawString(text,(int)(0.1*getWidth()-ytic-5*text.length()),(int)(0.91*getHeight()-dy*step));
                }
                step ++;
            }
           
           g2.setPaint(Color.blue);
            g2.draw(new noFriction()); // reference
            
            g2.setPaint(Color.red);
            g2.draw(new withFriction());
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        parameterPanel = new javax.swing.JPanel();
        linearFrictionCheckBox = new javax.swing.JCheckBox();
        quadraticFrictionCheckBox = new javax.swing.JCheckBox();
        massOfParticleLabel = new javax.swing.JLabel();
        massOfParticleSlider = new javax.swing.JSlider();
        massOfParticleInput = new javax.swing.JSpinner();
        initalAngleLabel = new javax.swing.JLabel();
        initialAngleSlider = new javax.swing.JSlider();
        initialAngleInput = new javax.swing.JSpinner();
        intialSpeedLabel = new javax.swing.JLabel();
        initialSpeedSlider = new javax.swing.JSlider();
        initialSpeedInput = new javax.swing.JSpinner();
        linearFrictionLabel = new javax.swing.JLabel();
        linearFrictionSlider = new javax.swing.JSlider();
        quadraticFrictionLabel = new javax.swing.JLabel();
        quadraticFrictionSlider = new javax.swing.JSlider();
        pauseButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        linearFrictionInput = new javax.swing.JTextField();
        quadraticFrictionInput = new javax.swing.JTextField();
        mainPanel = new MainDisplay();
        buttonPanel = new javax.swing.JPanel();
        ytGraphButton = new javax.swing.JButton();
        vxtGraphButton = new javax.swing.JButton();
        vytGraphButton = new javax.swing.JButton();
        xtGraphButton = new javax.swing.JButton();
        graphPanel = new GraphDisplay();
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

        linearFrictionCheckBox.setText("Friction (linear)");
        linearFrictionCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                linearFrictionCheckBoxStateChanged(evt);
            }
        });
        linearFrictionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linearFrictionCheckBoxActionPerformed(evt);
            }
        });

        quadraticFrictionCheckBox.setText("Friction (quadratic)");
        quadraticFrictionCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                quadraticFrictionCheckBoxStateChanged(evt);
            }
        });
        quadraticFrictionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quadraticFrictionCheckBoxActionPerformed(evt);
            }
        });

        massOfParticleLabel.setText("Mass");

        massOfParticleSlider.setMinimum(1);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, massOfParticleInput, org.jdesktop.beansbinding.ELProperty.create("${value}"), massOfParticleSlider, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        massOfParticleSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                massOfParticleSliderStateChanged(evt);
            }
        });

        massOfParticleInput.setValue(50);
        massOfParticleInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                massOfParticleInputStateChanged(evt);
            }
        });

        initalAngleLabel.setText("Intial Angle");

        initialAngleSlider.setMaximum(90);
        initialAngleSlider.setMinimum(1);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, initialAngleInput, org.jdesktop.beansbinding.ELProperty.create("${value}"), initialAngleSlider, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        initialAngleSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                initialAngleSliderStateChanged(evt);
            }
        });

        initialAngleInput.setValue(45);

        intialSpeedLabel.setText("Intial Speed");

        initialSpeedSlider.setMinimum(1);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, initialSpeedInput, org.jdesktop.beansbinding.ELProperty.create("${value}"), initialSpeedSlider, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        initialSpeedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                initialSpeedSliderStateChanged(evt);
            }
        });

        initialSpeedInput.setValue(50);

        linearFrictionLabel.setText("Friction coefficient 1");

        linearFrictionSlider.setValue(0);
        linearFrictionSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                linearFrictionSliderStateChanged(evt);
            }
        });

        quadraticFrictionLabel.setText("Friction coefficient 2");

        quadraticFrictionSlider.setMaximum(10);
        quadraticFrictionSlider.setValue(0);
        quadraticFrictionSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                quadraticFrictionSliderStateChanged(evt);
            }
        });

        pauseButton.setText("Exit");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("(kg)");

        jLabel7.setText("(deg)");

        jLabel8.setText("(m/s)");

        linearFrictionInput.setText("0");
        linearFrictionInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linearFrictionInputActionPerformed(evt);
            }
        });

        quadraticFrictionInput.setText("0");
        quadraticFrictionInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quadraticFrictionInputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout parameterPanelLayout = new javax.swing.GroupLayout(parameterPanel);
        parameterPanel.setLayout(parameterPanelLayout);
        parameterPanelLayout.setHorizontalGroup(
            parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, parameterPanelLayout.createSequentialGroup()
                .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(parameterPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(quadraticFrictionSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(parameterPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(initialAngleSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(parameterPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(massOfParticleLabel)
                            .addComponent(initalAngleLabel)
                            .addComponent(intialSpeedLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(massOfParticleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(initialAngleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(initialSpeedInput, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parameterPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(linearFrictionSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parameterPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(initialSpeedSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parameterPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parameterPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(massOfParticleSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parameterPanelLayout.createSequentialGroup()
                        .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(parameterPanelLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(quadraticFrictionLabel))
                            .addGroup(parameterPanelLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(linearFrictionLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(linearFrictionInput, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(quadraticFrictionInput, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 29, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parameterPanelLayout.createSequentialGroup()
                        .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parameterPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(quadraticFrictionCheckBox))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parameterPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(linearFrictionCheckBox)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        parameterPanelLayout.setVerticalGroup(
            parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, parameterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(massOfParticleLabel)
                    .addComponent(massOfParticleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(massOfParticleSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(initalAngleLabel)
                    .addComponent(initialAngleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(initialAngleSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intialSpeedLabel)
                    .addComponent(initialSpeedInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(initialSpeedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(linearFrictionCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(linearFrictionLabel)
                    .addComponent(linearFrictionInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(linearFrictionSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quadraticFrictionCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quadraticFrictionLabel)
                    .addComponent(quadraticFrictionInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quadraticFrictionSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(pauseButton)))
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        ytGraphButton.setText("y-t graph");
        ytGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ytGraphButtonActionPerformed(evt);
            }
        });

        vxtGraphButton.setText("Vx-t graph");
        vxtGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vxtGraphButtonActionPerformed(evt);
            }
        });

        vytGraphButton.setText("Vy-t graph");
        vytGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vytGraphButtonActionPerformed(evt);
            }
        });

        xtGraphButton.setText("x-t graph");
        xtGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xtGraphButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xtGraphButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ytGraphButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vxtGraphButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vytGraphButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xtGraphButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ytGraphButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vxtGraphButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vytGraphButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        graphPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parameterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(parameterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void linearFrictionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linearFrictionCheckBoxActionPerformed
        if(linearSwitch){
            linearSwitch = false;
            linearFrictionCoeff = 0;
            linearFrictionSlider.setValue(0);
            linearFrictionInput.setText("0");
        }
        else{
            linearSwitch = true;
            linearFrictionCoeff = 0.01;
            linearFrictionSlider.setValue(1);
            linearFrictionInput.setText("0.01");
        }
    }//GEN-LAST:event_linearFrictionCheckBoxActionPerformed

    private void massOfParticleInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_massOfParticleInputStateChanged

    }//GEN-LAST:event_massOfParticleInputStateChanged

    private void massOfParticleSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_massOfParticleSliderStateChanged
            massOfParticle = massOfParticleSlider.getValue();
    }//GEN-LAST:event_massOfParticleSliderStateChanged

    private void initialAngleSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_initialAngleSliderStateChanged
            initialAngle = initialAngleSlider.getValue();
    }//GEN-LAST:event_initialAngleSliderStateChanged

    private void initialSpeedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_initialSpeedSliderStateChanged
            initialSpeed = initialSpeedSlider.getValue();
    }//GEN-LAST:event_initialSpeedSliderStateChanged

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        startButton.setText("Restart");
        particle = new Particle();
        dynamics = new Dynamics();
        
        positionXArray = new ArrayList<>();
        positionYArray = new ArrayList<>();
        velocityXArray = new ArrayList<>();
        velocityYArray = new ArrayList<>();
        
        particle.mass = massOfParticleSlider.getValue();
        initialAngle = initialAngleSlider.getValue();
        initialSpeed = initialSpeedSlider.getValue();

        particle.velocityX = initialSpeed * Math.cos(Math.toRadians(initialAngle));
        particle.velocityY = initialSpeed * Math.sin(Math.toRadians(initialAngle));
        
        timer.start();
    }//GEN-LAST:event_startButtonActionPerformed

    private void linearFrictionCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_linearFrictionCheckBoxStateChanged

    }//GEN-LAST:event_linearFrictionCheckBoxStateChanged

    private void quadraticFrictionCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_quadraticFrictionCheckBoxStateChanged

    }//GEN-LAST:event_quadraticFrictionCheckBoxStateChanged

    private void quadraticFrictionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quadraticFrictionCheckBoxActionPerformed
        if(quadraticSwitch){
            quadraticSwitch = false;
            quadraticFrictionCoeff = 0;
            quadraticFrictionSlider.setValue(0);
            quadraticFrictionInput.setText("0");
        }
        else{
            quadraticSwitch = true;
            quadraticFrictionCoeff = 0.001;
            quadraticFrictionSlider.setValue(1);
            quadraticFrictionInput.setText("0.001");
        }
    }//GEN-LAST:event_quadraticFrictionCheckBoxActionPerformed

    private void linearFrictionSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_linearFrictionSliderStateChanged
        if(linearSwitch){
            linearFrictionInput.setText(""+(double)linearFrictionSlider.getValue()/100.);
            linearFrictionCoeff = (double)linearFrictionSlider.getValue()/100.;
        }
    }//GEN-LAST:event_linearFrictionSliderStateChanged

    private void quadraticFrictionSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_quadraticFrictionSliderStateChanged
            if(quadraticSwitch){
                quadraticFrictionInput.setText(""+(double)quadraticFrictionSlider.getValue()/1000.);
                quadraticFrictionCoeff = (double)quadraticFrictionSlider.getValue()/1000.;
            }
    }//GEN-LAST:event_quadraticFrictionSliderStateChanged

    private void linearFrictionInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linearFrictionInputActionPerformed
        // text box에서 바로 입력을 받았을때 최대 최소에 대한 제한조건을 준다.
        try{
            double value = 100*Double.parseDouble(linearFrictionInput.getText());
            if(value<=100){
                linearFrictionSlider.setValue((int)(value));
            }else{
                linearFrictionSlider.setValue(100);
                linearFrictionInput.setText("1.00");
            }
        }catch(java.lang.NumberFormatException e){// 문자 Input 에러 처리
            linearFrictionSlider.setValue(50);
            linearFrictionInput.setText("0.5");
        }                                           
    }//GEN-LAST:event_linearFrictionInputActionPerformed

    private void quadraticFrictionInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quadraticFrictionInputActionPerformed
        // text box에서 바로 입력을 받았을때 최대 최소에 대한 제한조건을 준다.
        try{
            double value = 1000*Double.parseDouble(quadraticFrictionInput.getText());
            if(value<=10){
                quadraticFrictionSlider.setValue((int)(value));
            }else{
                quadraticFrictionSlider.setValue(10);
                quadraticFrictionInput.setText("0.01");
            }
        }catch(java.lang.NumberFormatException e){// 문자 Input 에러 처리
            quadraticFrictionSlider.setValue(5);
            quadraticFrictionInput.setText("0.005");
        }                                           
    }//GEN-LAST:event_quadraticFrictionInputActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_pauseButtonActionPerformed

    private void xtGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xtGraphButtonActionPerformed
        graphSwitch = GRAPH.xtgraph;
        graphPanel.removeAll();
        graphPanel.repaint();
    }//GEN-LAST:event_xtGraphButtonActionPerformed

    private void ytGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ytGraphButtonActionPerformed
        graphSwitch = GRAPH.ytgraph;
        graphPanel.removeAll();
        graphPanel.repaint();
    }//GEN-LAST:event_ytGraphButtonActionPerformed

    private void vxtGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vxtGraphButtonActionPerformed
        graphSwitch = GRAPH.vxtgraph;
        graphPanel.removeAll();
        graphPanel.repaint();
    }//GEN-LAST:event_vxtGraphButtonActionPerformed

    private void vytGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vytGraphButtonActionPerformed
        graphSwitch = GRAPH.vytgraph;
        graphPanel.removeAll();
        graphPanel.repaint();
    }//GEN-LAST:event_vytGraphButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JLabel initalAngleLabel;
    private javax.swing.JSpinner initialAngleInput;
    private javax.swing.JSlider initialAngleSlider;
    private javax.swing.JSpinner initialSpeedInput;
    private javax.swing.JSlider initialSpeedSlider;
    private javax.swing.JLabel intialSpeedLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JCheckBox linearFrictionCheckBox;
    private javax.swing.JTextField linearFrictionInput;
    private javax.swing.JLabel linearFrictionLabel;
    private javax.swing.JSlider linearFrictionSlider;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JSpinner massOfParticleInput;
    private javax.swing.JLabel massOfParticleLabel;
    private javax.swing.JSlider massOfParticleSlider;
    private javax.swing.JPanel parameterPanel;
    private javax.swing.JButton pauseButton;
    private javax.swing.JCheckBox quadraticFrictionCheckBox;
    private javax.swing.JTextField quadraticFrictionInput;
    private javax.swing.JLabel quadraticFrictionLabel;
    private javax.swing.JSlider quadraticFrictionSlider;
    private javax.swing.JButton startButton;
    private javax.swing.JButton vxtGraphButton;
    private javax.swing.JButton vytGraphButton;
    private javax.swing.JButton xtGraphButton;
    private javax.swing.JButton ytGraphButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}