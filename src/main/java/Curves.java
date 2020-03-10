import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Curves extends JFrame {

    private static final long serialVersionUID = 1L;
    private ArrayList<Vector3> points;
    private ArrayList<Curve> curves;

    private Canvas2D xyCanvas;
    private Canvas2D xzCanvas;
    private Canvas2D yzCanvas;
    private InputPanel inputs;

    public Curves() {
        super();

        points = new ArrayList<Vector3>();
        curves = new ArrayList<Curve>();

        xyCanvas = new Canvas2D(points, curves, 0, 1);
        xzCanvas = new Canvas2D(points, curves, 2, 0);
        yzCanvas = new Canvas2D(points, curves, 1, 2);
        inputs = new InputPanel();

        this.setLayout(new GridLayout(2, 2));
        this.add(xyCanvas);
        this.add(xzCanvas);
        this.add(yzCanvas);
        this.add(inputs);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setVisible(true);

        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addPoint(new Vector3(e.getX(), e.getY(), 0));
                xyCanvas.repaint();
                xzCanvas.repaint();
                yzCanvas.repaint();
                repaint();
            }
        });
    }

    private void addPoint(Vector3 p) {
        points.add(p);

        if (points.size() > 1) {
            curves.clear();
            curves.add(new BezierCurve(Color.red, points));
            curves.add(new BezierSpline(Color.blue, points));
//            curves.add(new InterpolatedSpline(Color.green, points));
            curves.add(new HermiteSpline(Color.magenta, points));
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame curves = new Curves();
            }
        });
    }

}