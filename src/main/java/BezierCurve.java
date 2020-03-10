import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BezierCurve extends Curve {

    public BezierCurve(Color colour, List<Vector3> points) {
        super(colour, points, true);
        fillCurve();
    }

    private void fillCurve() {

        curve = new ArrayList<Vector3>();
        for (double u = 0; u <= 1; u += stepsize) {
            curve.add(getPointOnCurve(u));
        }
    }

    public Vector3 getPointOnCurve(double u) {
        int n = points.size();
        Vector3 result = new Vector3(0, 0, 0);
        for (int i = 0; i < n; i++) {
            Vector3 partial = points.get(i).copy().mult(pascalsTriangle.get(n - 1)[i] * Math.pow(u, i) * Math.pow((1 - u), n - 1 - i));
            result = result.add(partial);
        }
        return result;
    }

    public Vector3 getTangentAtPoint(double u) {
        int n = points.size();
        Vector3 result = new Vector3(0, 0, 0);
        for (int i = 0; i < n - 1; i++) {
            Vector3 partial = points.get(i + 1).sub(points.get(i)).mult(n).mult(pascalsTriangle.get(n - 2)[i] * Math.pow(u, i) * Math.pow((1 - u), n - 1 - i));
            result = result.add(partial);
        }
        return result;
    }
}
