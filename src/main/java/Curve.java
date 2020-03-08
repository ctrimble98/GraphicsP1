import processing.core.PApplet;
import java.util.ArrayList;
import java.util.List;

public abstract class Curve {

    protected static final double stepsize = 0.0001;

    public static List<int[]> pascalsTriangle = new ArrayList<int[]>();
    protected List<Vector3> points;
    protected List<Vector3> curve;
    protected PApplet canvas;
    protected int colour;

    public List<Vector3> getPoints() {
        return points;
    }

    public void setPoints(List<Vector3> points) {
        this.points = points;
    }

    public List<Vector3> getCurve() {
        return curve;
    }

    public Curve(PApplet canvas, int colour, List<Vector3> points, boolean needsPascal) {
        this.canvas = canvas;
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

    public void draw(int xIndex, int yIndex) {
        if (curve.size() > 1) {
            canvas.stroke(colour);
            double[] previousPoint = curve.get(0).toArray();
            for (int i = 1; i < curve.size(); i++) {
                double[] point = curve.get(i).toArray();
                canvas.line((float) previousPoint[xIndex], (float) previousPoint[yIndex], (float) point[xIndex], (float) point[yIndex]);
                previousPoint = curve.get(i - 1).toArray();
            }
        }
    }
}
