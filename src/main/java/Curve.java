import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Curve {

    protected static final double stepsize = 0.0001;

    public static List<int[]> pascalsTriangle = new ArrayList<int[]>();
    protected List<Vector3> points;
    protected List<Vector3> curve;
    protected PApplet canvas;
    protected Color colour;

    public List<Vector3> getPoints() {
        return points;
    }

    public void setPoints(List<Vector3> points) {
        this.points = points;
    }

    public List<Vector3> getCurve() {
        return curve;
    }

    public Curve(/*PApplet canvas, */Color colour, List<Vector3> points, boolean needsPascal) {
//        this.canvas = canvas;
        this.colour = colour;
        this.points = points;
        int n = points.size();
        if (needsPascal && pascalsTriangle.size() < n) {
            fillPascals(n);
        }
    }

    public void fillPascals(int n) {

        if (pascalsTriangle.size() == 0) {
            pascalsTriangle.add(new int[] {1});
        }
        if (pascalsTriangle.size() < n) {
            for (int i = pascalsTriangle.size(); i < n; i++) {
                pascalsTriangle.add(new int[i + 1]);
                pascalsTriangle.get(i)[0] = 1;
                for (int j = 1; j < i; j++) {
                    pascalsTriangle.get(i)[j] = pascalsTriangle.get(i - 1)[j - 1] + pascalsTriangle.get(i - 1)[j];
                }
                pascalsTriangle.get(i)[i] = 1;
            }
        }
    }

    public void draw(int xIndex, int yIndex, Graphics2D g2) {
        if (curve.size() > 1) {
            int[][] curvePoints = new int[3][curve.size()];
            curvePoints[0] = curve.stream().map(Vector3::getX).mapToInt(Number::intValue).toArray();
            curvePoints[1] = curve.stream().map(Vector3::getY).mapToInt(Number::intValue).toArray();
            curvePoints[2] = curve.stream().map(Vector3::getZ).mapToInt(Number::intValue).toArray();
            g2.setColor(colour);
            g2.drawPolyline(curvePoints[xIndex], curvePoints[yIndex], curve.size());
        }
    }
}
