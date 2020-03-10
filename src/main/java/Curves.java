import processing.core.PApplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

//public class Curves extends PApplet {
//
//    private ArrayList<Vector3> points;
//    private BezierCurve b;
//    private BezierSpline s;
//    private InterpolatedSpline iS;
//    private HermiteSpline h;
//
//    public static void main(String[] args) {
//        PApplet.main("Curves");
//    }
//
//    @Override
//    public void settings() {
//        size(800, 800);
//    }
//
//    @Override
//    public void setup() {
//        points = new ArrayList<Vector3>();
//    }
//
//    @Override
//    public void draw(){
//
//        background(220);
//        stroke(0);
//        strokeWeight(5);
//        line(0, height/2f, width, height/2f);
//        line(width/2f, 0, width/2f, height);
//
//        strokeWeight(1);
//        line(0, height/4f, width, height/4f);
//        line(width/4f, 0, width/4f, height);
//        line(0, height*3/4f, width/2f, height*3/4f);
//        line(width*3/4f, 0, width*3/4f, height/2f);
//
//
//        pushMatrix();
//        translate(width/2f, height/2f);
//        ellipseMode(CENTER);
//        for (Vector3 point: points) {
//            ellipse((float)(point.getX()), (float)(point.getY()), 5, 5);
//        }
//
//        if (points.size() > 1) {
//            b.draw(0, 1);
//            s.draw(0, 1);
//            iS.draw(0, 1);
//            h.draw(0, 1);
//        }
//
//        popMatrix();
//    }
//
//    public void mousePressed() {
//        addPoint(new Vector3(mouseX - width/2, mouseY - height/2, 0));
//    }
//
//    private void addPoint(Vector3 p) {
//        points.add(p);
//
//        if (points.size() > 1) {
//            b = new BezierCurve(this, color(255, 0, 0), points);
//            s = new BezierSpline(this, color(0, 255, 0), points);
//            iS = new InterpolatedSpline(this, color(0, 0, 255), points);
//            h = new HermiteSpline(this, color(255, 0, 255), points);
//        }
//    }
//}

public class Curves extends JPanel {

    private static final long serialVersionUID = 1L;
    private ArrayList<Vector3> points;
    private BezierCurve b;
    private BezierSpline s;
    private InterpolatedSpline iS;
    private HermiteSpline h;

    public Curves() {
        points = new ArrayList<Vector3>();
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addPoint(new Vector3(e.getX(), e.getY(), 0));
                points.forEach(System.out::println);
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.black);
        for (Vector3 point : points) {
            g2.fillOval((int)point.getX()-5, (int)point.getY()-5, 5, 5);
        }
        g2.setStroke(new BasicStroke(0.5f));
        if (points.size() > 1) {
            b.draw(0, 1, g2);
            s.draw(0, 1, g2);
            iS.draw(0, 1, g2);
            h.draw(0, 1, g2);
        }
    }

    private void addPoint(Vector3 p) {
        points.add(p);

        if (points.size() > 1) {
            b = new BezierCurve(/*this, */Color.red, points);
            s = new BezierSpline(/*this, */Color.blue, points);
            iS = new InterpolatedSpline(/*this, */Color.green, points);
            h = new HermiteSpline(/*this, */Color.magenta, points);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.add(new Curves());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 800);
                frame.setVisible(true);
            }
        });
    }

}