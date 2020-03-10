import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Curves3D extends JPanel {

    private static final long serialVersionUID = 1L;
    private ArrayList<Vector3> points;
    private ArrayList<Curve> curves;

    public Curves3D() {
        points = new ArrayList<Vector3>();
        curves = new ArrayList<Curve>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addPoint(new Vector3(e.getX(), e.getY(), 0));
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
        int r = 5;
        for (Vector3 point : points) {
            g2.fillOval((int)(point.getX() - r/2.0), (int)(point.getY() - r/2.0), r, r);
        }
        g2.setStroke(new BasicStroke(0.5f));
        for (Curve c: curves) {
            c.draw(0, 1, g2);
        }
    }

    private void addPoint(Vector3 p) {
        points.add(p);

        if (points.size() > 1) {
            curves = new ArrayList<Curve>();
            curves.add(new BezierCurve(Color.red, points));
            curves.add(new BezierSpline(Color.blue, points));
            curves.add(new InterpolatedSpline(Color.green, points));
            curves.add(new HermiteSpline(Color.magenta, points));
        }
    }

    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                JFrame frame = new JFrame();
//                JSplitPane splitPane = new JSplitPane();
//                JPanel curves = new Curves();
//                JPanel
//                frame.add();
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setSize(800, 800);
//                frame.setVisible(true);
//            }
//        });
    }

}