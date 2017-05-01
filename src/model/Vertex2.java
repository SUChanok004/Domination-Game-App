
package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;

/**
 *
 * @author Mr.Chanok Pathompatai
 */
public final class Vertex2 extends JButton {
    
    String s;
    int status; // 0 = undominated , 1 = dominated , 2 = saturated
    Point location;

    public Point getLocation() {
        return location;
    }
    
    
    public Vertex2( Vertex1 v1 ) {   
        super.setName(v1.getName());
        status = 0;
        s = v1.s;
        this.location = v1.getLocation();
        prepare();
    }
    
    void prepare() {
        super.setText(this.getName() );
        setBackground(null);
        setFocusable(false);
        
        Dimension size = getPreferredSize();
        size.width = Math.max(size.width, size.height);
        size.height = size.width;
        setPreferredSize(size);

        setContentAreaFilled(false);
    }
    
    public void setStatus( int status ) {
        if( this.status < status  ) {
            this.status = status;
        }
    }

    public int getStatus() {
        return status;
    }
    
    protected void paintComponent(Graphics g) {
        Color color;
        switch(status) {
            case 0 : 
                color = Color.GREEN;
                break;
            case 1:
                color = Color.RED;
                break; 
            default:
                color = Color.RED;
                setEnabled(false);
                break; 
        }
        g.setColor(color);
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        BasicStroke stroke = new BasicStroke(3);
        g2d.setStroke(stroke);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }

    Shape shape;

    public boolean contains(int x, int y) {
        if (shape == null || shape.getBounds().equals(getBounds()) == false) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }

    public String getS() {
        return s;
    }
    
}
