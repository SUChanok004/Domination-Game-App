/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Mr.Chanok Pathompatai
 */
public class Frame1 extends JFrame {

    static Frame1 frame1;
    
    private TextArea matrix;
    private JButton draw;
    private ActionListener listener;
    public Frame2 frame2;
    public Frame3 frame3;
    
    private void prepare() {
        setTitle("Domination Game page INPUT AJENCY MATRIX");
        setSize(420, 550);
        setLocationRelativeTo(null);
        setLayout(null);

        Rectangle areaField = new Rectangle(10, 10, 400, 400);
        Rectangle buttonField = new Rectangle(10, 420, 400, 100);

        matrix = new TextArea(15, 15);
        add(matrix);
        matrix.setBounds(areaField);
        matrix.setBackground(Color.WHITE);

        draw = new JButton("DRAW");
        add(draw);
        draw.setBounds(buttonField);

        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = matrix.getText();
                
                s = s.replaceAll("\n", "");
                s = s.replaceAll("\\s", "");
                String t = s.replaceAll("0", "");
                t = t.replaceAll("1", "");
                double order = Math.sqrt(s.length());
                if( s.length() == 0 ||Math.floor(order) != Math.ceil(order) || t.length() != 0 ) {
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                } else {
                    if( frame2 != null ) {
                        frame2.dispose();
                    }
                    if( frame3 != null ) {
                        frame3.dispose();
                    }
                    frame2 = new Frame2(s, (int) order);
                }
            }
        };

        draw.addActionListener(listener);

        setDefaultCloseOperation(3);
    }

    private void showFrame() {

        prepare();

        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        frame1 = new Frame1();
        frame1.showFrame();
    }

}
