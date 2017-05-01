package frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import model.Edge;
import model.Vertex1;

/**
 *
 * @author Mr.Chanok Pathompatai
 */
public class Frame2 extends JFrame {

    String matrix[];
    int order;
    ArrayList<Vertex1> vs;
    ArrayList<Edge> edges;

    JButton play;
    ActionListener listener;

    Panel1 panel1;
    MouseAdapter2 adapter2 = new MouseAdapter2();

    final int widthV = 50;
    final int heightV = 50;

    int n;

    private void prepare() {
        setSize(520, 530);
        setLocationRelativeTo(null);
        setLayout(null);

        panel1 = new Panel1();
        add(panel1);
        panel1.prepare();

        play = new JButton("PLAY");
        add(play);
        play.setBounds(10, 450, 500, 50);

        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] options = {"Gamma(G)",
                    "DS(G)", "SD(G)"};

                n = JOptionPane.showOptionDialog(null,
                        "What the game you want to play?",
                        "Select Game", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                if (n != -1) {
                    newFrame3();
                }

            }
        };

        play.addActionListener(listener);

        setVisible(true);
        setResizable(false);
    }

    void newFrame3() {
        if (Frame1.frame1.frame3 != null) {
            Frame1.frame1.frame3.dispose();
        }
        Frame1.frame1.frame3 = new Frame3(vs, edges, matrix, n);
    }

    private void createEdge() {
        edges = new ArrayList<>(order * order);
        for (int i = 0; i < order; ++i) {
            for (int j = i + 1; j < order; ++j) {
                if (matrix[i].charAt(j) == '1') {
                    Edge e = new Edge(vs.get(i), vs.get(j));
                    edges.add(e);
                }
            }
        }
    }

    public Frame2(String matrix, int order) {
        this.order = order;
        this.matrix = new String[order];
        vs = new ArrayList<>(order);
        for (int i = 0; i < order * order; i += order) {
            this.matrix[i / order] = matrix.substring(i, i + order);
            vs.add(new Vertex1(Integer.toString(i / order), this.matrix[i / order]));
        }
        createEdge();
        prepare();
    }

    class MouseAdapter2 extends MouseInputAdapter {

        Point location;
        MouseEvent pressed;

        public void mousePressed(MouseEvent me) {
            pressed = me;
        }

        public void mouseDragged(MouseEvent me) {
            Component component = me.getComponent();
            location = component.getLocation(location);

            int x = location.x - pressed.getX() + me.getX();
            int y = location.y - pressed.getY() + me.getY();

            if (x < (int) panel1.getX() - widthV + 40) {
                x = (int) panel1.getX() - widthV + 40;
            }
            if (x > (int) (panel1.getWidth()) - widthV) {
                x = (int) (panel1.getWidth()) - widthV;
            }
            if (y < (int) panel1.getY() - heightV / 2 + 15) {
                y = (int) panel1.getY() - heightV / 2 + 15;
            }
            if (y > (int) panel1.getHeight() - heightV) {
                y = (int) panel1.getHeight() - heightV;
            }
            component.setLocation(x, y);
            repaint();
        }

    }

    class Panel1 extends JPanel {

        void prepare() {
            setLayout(null);
            Rectangle rectangle = new Rectangle(10, 10, 500, 430);
            setBounds(rectangle);
            setBackground(Color.WHITE);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
            setBorder(border);
            double degree = 360.0 / order;
            for (int i = 0; i < order; ++i) {
                double radians = Math.toRadians(degree * i);
                vs.get(i).addMouseListener(adapter2);
                vs.get(i).addMouseMotionListener(adapter2);
                int xy[] = cal(radians);
                Rectangle r = new Rectangle(250 - xy[0], 250 - xy[1], widthV, heightV);
                vs.get(i).setBounds(r);
                add(vs.get(i));
            }
        }

        private int[] cal(double radians) {
            int[] result = new int[2];
            double ans = Math.cos(radians) * 100;
            if (ans > 0) {
                result[0] = (int) ((ans + 0.5));
            } else {
                result[0] = (int) (ans - 0.5);
            }
            ans = Math.sin(radians) * 100;
            if (ans > 0) {
                result[1] = (int) (ans + 0.5);
            } else {
                result[1] = (int) (ans - 0.5);
            }
            return result;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setColor(Color.blue);
            BasicStroke bs1 = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            g2d.setStroke(bs1);

            for (Edge e : edges) {
                Point2D v1 = e.getV1().getLocation();
                Point2D v2 = e.getV2().getLocation();

                Point2D start = new Point((int) (v1.getX() + widthV / 2), (int) (v1.getY() + heightV / 2));
                Point2D end = new Point((int) (v2.getX() + widthV / 2), (int) (v2.getY() + heightV / 2));

                g2d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
            }
        }

    }

}
