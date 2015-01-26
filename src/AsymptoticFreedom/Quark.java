/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsymptoticFreedom;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Geonmo
 */
public class Quark {
    String flavour;  // u : 0 , d : 1 , s :2
    String color ; // red : 0, green : 1, blue : 2
    int charge3;  // u,s : 2 d : -1
    Point pos,momentum;
    double k1;    // for Coulomb
    double k2;    // for Gluon 
    final int radius;
    Image img ; 
    
    Quark(String flavour){
        this.flavour = flavour;
        radius= 50;    
        img=null;
        momentum.x=0;
        momentum.y=0;
    }
    Quark(String flavour, String color){        
        radius= 50;        
        momentum = new Point(0,0);        
        this.flavour = flavour;
        if ( "s".equals(flavour) || "u".equals(flavour) ) {            
            this.charge3 = 2;
        }
        else if ("d".equals(flavour) ) {            
            this.charge3 = -1;            
        }       
        else if ("ubar".equals(flavour) || "sbar".equals(flavour)){
            this.charge3 = -2;
        }
        else if ("dbar".equals(flavour)){
            this.charge3 = 1;
        }
        else {
            System.out.println("Error! Given flavour is wrong type.");
        }
        this.color = color;
        img=null;
        loadImage(flavour, color);
        //momentum.x=0;
        //momentum.y=0;
    }
    public Point getLocation(){
        return pos.getLocation();        
    }
    public void translate(int x, int y){
        pos.translate(x,y);        
    }
    public int Charge(){
        return charge3;
    }
    public int Charge3(){
        return charge3;
    }
    
    public double calculatePotential( Quark other){
        
        double distance = this.pos.distance(other.pos);
        // V = -k1*q1*q2/r+k2*r
        double value = -AppletAF.getK1()*this.charge3*other.charge3/distance+AppletAF.getK2()*distance;
        //System.out.println(value);
        if ( value<0) return 0;
        return value;
    }
    public double calculatePotential( Quark other, double distance){
        double value = -AppletAF.getK1()*this.charge3*other.charge3/distance+AppletAF.getK2()*distance;
        //System.out.println(value);
        if ( value<0) return 0;
        return value;
    }
    public void loadImage(String flavour, String color) {
        String image_path = String.format("/AsymptoticFreedom/resource/%s_quark_%s.png",flavour,color);
        //String image_path = String.format("/resource/%s_quark_%s.png",flavour,color);
        //System.out.println(image_path);
        Image image = null;
        try {
            image = ImageIO.read( this.getClass().getResource(image_path));
            } catch (IOException ex) {
            Logger.getLogger(ViewPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        //System.out.println(image);        
        this.img = image.getScaledInstance(radius,radius,Image.SCALE_DEFAULT);        
    }

    public void setPos(Point pos){
        this.pos = pos;
    }
    public void setPos(int x, int y){
        this.pos = new Point(x,y);
    }
    public void setMomentumChange(Point dV){
        this.momentum.translate(dV.x,dV.y);
    }
    public void friction(){
        this.momentum.x = this.momentum.x - (int)this.momentum.x/3;
        this.momentum.y = this.momentum.y - (int)this.momentum.y/3;
        
    }
    
}
