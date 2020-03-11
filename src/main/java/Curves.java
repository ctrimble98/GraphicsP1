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
    private ArrayList<StraightLine> lines;

    private BezierCurve bezierCurve;
    private BezierSpline bezierSpline;
    private HermiteSpline hermiteSpline;

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
        lines = new ArrayList<StraightLine>();

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
        lines = new ArrayList<StraightLine>();

        calculateCurves();
    }

    private void calculateCurves() {
        if (points.size() > 1) {
            curves.clear();
            bezierCurve = new BezierCurve(Color.red, points);
            bezierSpline = new BezierSpline(Color.blue, points);
            hermiteSpline = new HermiteSpline(Color.magenta, points);
            curves.add(bezierCurve);
            curves.add(bezierSpline);
//            curves.add(new InterpolatedSpline(Color.green, points));
            curves.add(hermiteSpline);
            curves.addAll(lines);
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

        points.forEach(v -> v.setZ(0));

        double u = Math.random();
        Vector3 randomPoint = bezierCurve.getPointOnCurve(u);
        Vector3 tangent = bezierCurve.getTangentAtPoint(u);

        lines.add(new StraightLine(Color.black, randomPoint, new Vector3(-1*tangent.getY(), tangent.getX(), 0)));
        for (StraightLine line: lines) {
            line.addIntersections(hermiteSpline.findIntersection(line));
        }
        calculateCurves();
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