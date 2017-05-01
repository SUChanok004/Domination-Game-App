/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import model.Edge;
import model.Vertex1;
import model.Vertex2;

/**
 *
 * @author Mr.Chanok Pathompatai
 */
public class Frame3 extends JFrame {

    ArrayList<String> history = new ArrayList<>();
    String[] matrix;
    ArrayList<Vertex2> vs;
    ArrayList<Edge> edges;

    Panel2 panel2;
    Panel3 panel3;
    int game;

    JButton dominator;
    JButton staller;
    JLabel total;

    int count = 0;
    int player = 1;

    ActionListener2 listener;

    final int widthV = 50;
    final int heightV = 50;

    Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

    int target;

    class ActionListener2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Vertex2 v = (Vertex2) e.getSource();
            String playerName = "Staller";
            if (player == 1) {
                playerName = "Dominator";
            }
            int n = JOptionPane.showConfirmDialog(null,
                    "You are " + playerName + ".\nDo you want to play vertex " + v.getName() + " ?",
                    "Question", JOptionPane.YES_NO_CANCEL_OPTION);
            if (n == 0) {
                if (!isSaturated(v)) {
                    history.add(v.getName());
                    dominate(v);
                    ++count;
                    if (player == 1) {
                        player = 0;
                    } else {
                        player = 1;
                    }

                    total.setText(Integer.toString(count));
                    if (game == 0) {
                        dominator.setEnabled(true);
                        staller.setEnabled(false);
                    } else if (player == 1) {
                        dominator.setEnabled(true);
                        staller.setEnabled(false);
                    } else {
                        dominator.setEnabled(false);
                        staller.setEnabled(true);
                    }
                    repaint();

                    if (isGameOver()) {
                        Object[] options = {"YES",
                            "NO, I want to view grap",
                            "No, I want to play new graph"};
                        String gameEnd = "You Win!!!!!!!!!!";
                        if( game == 0 ) {
                            if( count > target ) {
                                gameEnd = "You Lose!!!!!!!!!";
                            }
                        } else if( count < target ) {
                            gameEnd = "Dominator Win!!!!!!!!!!!";
                        } else if( count > target ) {
                            gameEnd = "Staller Win!!!!!!!!!!!";
                        } else {
                            gameEnd = "Draw!!!!!!!!!!!!";
                        }
                        int x = JOptionPane.showOptionDialog(null,
                                gameEnd + "\nDo you want to restart the game?",
                                "Select", JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        switch (x) {
                            case 0:
                                Frame1.frame1.frame2.newFrame3();
                                break;
                            case 1:
                                dispose();
                                break;
                            default:
                                Frame1.frame1.frame2.dispose();
                                dispose();
                                break;
                        }
                    }
                }
            }
        }

        public void dominate(Vertex2 v) {
            String s = v.getS();
            for (int i = 0; i < s.length(); ++i) {
                if (s.charAt(i) == '1') {
                    vs.get(i).setStatus(1);
                }
            }
            v.setStatus(2);
        }

        private boolean isSaturated(Vertex2 v) {
            String s = v.getS();
            for (int i = 0; i < s.length(); ++i) {
                if ((s.charAt(i) == '1'
                        && vs.get(i).getStatus() == 0)
                        || v.getStatus() == 0) {
                    return false;
                }
            }
            return true;
        }

        private boolean isGameOver() {
            int count = 0;
            for (Vertex2 v : vs) {
                if (isSaturated(v)) {
                    v.setStatus(2);
                } else {
                    ++count;
                }
            }
            return count == 0;
        }
    }

    private class Panel2 extends JPanel {

        void prepare() {
            setLayout(null);
            Rectangle rectangle = new Rectangle(10, 70, 500, 430);
            setBounds(rectangle);
            setBackground(Color.WHITE);

            setBorder(border);

            listener = new ActionListener2();
            for (Vertex2 v : vs) {
                Point location = v.getLocation();
                Dimension d = new Dimension(widthV, heightV);
                Rectangle r = new Rectangle(location, d);
                v.setBounds(r);
                v.addActionListener(listener);
                add(v);
            }

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

    private class Panel3 extends JPanel {

        class Vertex {

            int name;
            int[] n;
            boolean dominated;

            public Vertex(int name, String s) {
                this.name = name;
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = 0; i < s.length(); ++i) {
                    if (s.charAt(i) == '1') {
                        list.add(i);
                    }
                }
                this.n = list.stream().mapToInt(i -> i).toArray();
                this.dominated = false;
            }

            public Vertex(Vertex v) {
                this.name = v.name;
                this.n = v.n;
                this.dominated = v.dominated;
            }
        }

        ArrayList<Integer> canplay(ArrayList<Vertex> g) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (Vertex v : g) {
                if (v.dominated == false) {
                    temp.add(v.name);
                } else {
                    int[] n = v.n;
                    for (Integer i : n) {
                        if (g.get(i).dominated == false) {
                            temp.add(v.name);
                            break;
                        }
                    }
                }
            }
            return temp;
        }

        int gamma(ArrayList<Vertex> g) {
            ArrayList<Integer> canplay = canplay(g);
            if (canplay.isEmpty() == true) {
                return 0;
            }
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < canplay.size(); ++i) {
                ArrayList<Vertex> g2 = play(canplay.get(i), g);
                int x = gamma(g2);
                if (x < min) {
                    min = x;
                }
            }
            return 1 + min;
        }

        int gammaD(ArrayList<Vertex> g) {
            ArrayList<Integer> canplay = canplay(g);
            if (canplay.isEmpty() == true) {
                return 0;
            }
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < canplay.size(); ++i) {
                ArrayList<Vertex> g2 = play(canplay.get(i), g);
                int x = gammaS(g2);
                if (x < min) {
                    min = x;
                }
            }
            return 1 + min;
        }

        int gammaS(ArrayList<Vertex> g) {
            ArrayList<Integer> canplay = canplay(g);
            if (canplay.isEmpty() == true) {
                return 0;
            }
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < canplay.size(); ++i) {
                ArrayList<Vertex> g2 = play(canplay.get(i), g);
                int x = gammaD(g2);
                if (x > max) {
                    max = x;
                }
            }
            return 1 + max;
        }

        ArrayList<Vertex> play(int i, ArrayList<Vertex> tmp) {
            ArrayList<Vertex> g = copy(tmp);
            g.get(i).dominated = true;
            for (Integer x : g.get(i).n) {
                g.get(x).dominated = true;
            }
            return g;
        }

        ArrayList<Vertex> copy(ArrayList<Vertex> g) {
            ArrayList<Vertex> g2 = new ArrayList<>();
            for (Vertex v : g) {
                g2.add(new Vertex(v));
            }
            return g2;
        }

        JLabel targetLabel;

        void calculateTarget() {
            ArrayList<Vertex> graph = new ArrayList<>();
            for (int i = 0; i < matrix.length; ++i) {
                Vertex v = new Vertex(i, matrix[i]);
                graph.add(v);
            }
            switch (game) {
                case 0:
                    target = gamma(graph);
                    break;
                case 1:
                    target = gammaD(graph);
                    break;
                case 2:
                    target = gammaS(graph);
                    break;
                default:
                    break;
            }
        }

        void prepare() {
            calculateTarget();
            setLayout(null);
            Rectangle rectangle = new Rectangle(530, 10, 180, 490);
            setBounds(rectangle);
            setBackground(this.getBackground());

            Rectangle r = new Rectangle(10, 10, 160, 470);
            targetLabel = new JLabel();
            add(targetLabel);
            targetLabel.setBounds(r);
            Font font = new Font(null, Font.BOLD, 40);
            targetLabel.setFont(font);
            targetLabel.setText("<html>TARGET"
                    + "<center>"
                    + target
                    + "</center>"
                    + "</html>");
            targetLabel.setHorizontalAlignment(SwingConstants.CENTER);
            targetLabel.setVerticalAlignment(SwingConstants.BOTTOM);

            setBorder(border);
        }

    }

    private void prepare() {
        setSize(730, 530);
        setLocationRelativeTo(null);
        setLayout(null);

        panel3 = new Panel3();
        add(panel3);
        panel3.prepare();

        panel2 = new Panel2();
        add(panel2);
        panel2.prepare();

        Rectangle rd = new Rectangle(10, 10, 200, 60);
        dominator = new JButton("DOMINATOR");
        add(dominator);
        dominator.setBounds(rd);

        Rectangle rl = new Rectangle(220, 10, 80, 60);
        total = new JLabel(Integer.toString(count));
        add(total);
        total.setBounds(rl);
        total.setBorder(border);
        total.setHorizontalAlignment(SwingConstants.CENTER);

        Rectangle rs = new Rectangle(310, 10, 200, 60);
        staller = new JButton("STALLER");
        add(staller);
        staller.setBounds(rs);

        if (game == 0) {
            dominator.setEnabled(true);
            staller.setEnabled(false);
        } else if (player == 1) {
            dominator.setEnabled(true);
            staller.setEnabled(false);
        } else {
            dominator.setEnabled(false);
            staller.setEnabled(true);
        }
        setVisible(true);
        setResizable(false);
    }

    public Frame3(ArrayList<Vertex1> vs1, ArrayList<Edge> es1, String[] s, int game) {
        vs = new ArrayList<>();
        for (Vertex1 v : vs1) {
            Vertex2 tmp = new Vertex2(v);
            vs.add(tmp);
        }
        this.edges = es1;
        this.matrix = s;
        this.game = game;
        if (game == 2) {
            player = 0;
        }
        prepare();
    }

}
