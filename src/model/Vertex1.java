
package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;

/**
 *
 * @author Mr.Chanok Pathompatai
 */
public final class Vertex1 extends JButton {
    
    String s;
    
    public Vertex1(String name , String s) {
        super(name);
        this.setName(name);
        this.s = s;
        prepare();
    }

    void prepare() {
        setBackground(null);
        setFocusable(false);

        Dimension size = getPreferredSize();
        size.width = Math.max(size.width, size.height);
        size.height = size.width;
        setPreferredSize(size);

        setContentAreaFilled(false);
    }
    
    protected void paintComponent(Graphics g) {
//        g.setColor(Color.blue);
        Color color = getBackground();
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
    
}
