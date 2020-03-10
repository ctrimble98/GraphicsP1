import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Curves extends JFrame {

    private static final long serialVersionUID = 1L;
    private ArrayList<Vector3> points;
    private ArrayList<Curve> curves;

    private Canvas2D xyCanvas;
    private Canvas2D xzCanvas;
    private Canvas2D yzCanvas;
    private InputPanel inputs;

    public ArrayList<Vector3> getPoints() {
        return points;
    }

    public ArrayList<Curve> getCurves() {
        return curves;
    }

    public Curves() {
        super();

        points = new ArrayList<Vector3>();
        curves = new ArrayList<Curve>();

        xyCanvas = new Canvas2D(this, 0, 1);
        xzCanvas = new Canvas2D(this, 2, 0);
        yzCanvas = new Canvas2D(this, 1, 2);
        inputs = new InputPanel(this);

        this.setLayout(new GridLayout(2, 2));
        this.add(xyCanvas);
        this.add(xzCanvas);
        this.add(yzCanvas);
        this.add(inputs);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setVisible(true);

        setBackground(Color.WHITE);
    }

    public void addPoint(Vector3 p) {
        points.add(p);

        calculateCurves();
    }

    private void calculateCurves() {
        if (points.size() > 1) {
            curves.set(0, new BezierCurve(Color.red, points));
            curves.set(1, new BezierSpline(Color.blue, points));
//            curves.add(new InterpolatedSpline(Color.green, points));
            curves.set(2, new HermiteSpline(Color.magenta, points));
        }

        xyCanvas.repaint();
        xzCanvas.repaint();
        yzCanvas.repaint();
        repaint();
    }

    public void findRandomIntersection() {
        if (points.size() <= 1) {
            return;
        }

        for (Vector3 p: points) {
            p.setZ(0);
        }
        calculateCurves();

        double u = Math.random();
        Vector3 randomPoint = ((BezierCurve)curves.get(0)).getPointOnCurve(u);
        Vector3 tangent = ((BezierCurve)curves.get(0)).getTangentAtPoint(u);

        curves.add(new StraightLine(Color.black, randomPoint, new Vector3(-1*tangent.getY(), tangent.getX(), 0)));
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