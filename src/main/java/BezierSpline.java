import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to produce a cubic Bezier spline between a set of points. Possibly finishes with a quadratic curve
 */
public class BezierSpline extends Curve {

    private List<BezierCurve> curves;

    public BezierSpline(/*PApplet canvas, */Color colour, List<Vector3> points) {
        super(colour, points, true);

        curves = new ArrayList<BezierCurve>();

        getCurves();
        fillCurve();
    }

    private void getCurves() {

        int n = points.size();
        if (n < 5) {
            // Add the single curve
            curves.add(new BezierCurve(colour, points));
        } else {

            // Add the first curve (p0 -> p2.5)
            List<Vector3> currentCurve = new ArrayList<>();
            currentCurve.add(points.get(0));
            currentCurve.add(points.get(1));
            currentCurve.add(points.get(2));
            Vector3 lastAvg = points.get(2).average(points.get(3));
            currentCurve.add(lastAvg);
            curves.add(new BezierCurve(colour, currentCurve));

            // Add the cubic curves with added points at both ends
            int finalCurveSize = n % 2 == 0 ? 3: 2;
            for (int i = 3; i < n - finalCurveSize; i += 2) {
                currentCurve = new ArrayList<>();
                currentCurve.add(lastAvg);
                currentCurve.add(points.get(i));
                currentCurve.add(points.get(i + 1));
                lastAvg = points.get(i + 1).average(points.get(i + 2));
                currentCurve.add(lastAvg);
                curves.add(new BezierCurve(colour, currentCurve));
            }

            // Add the final curve (Either quadratic or cubic)
            currentCurve = new ArrayList<>();
            currentCurve.add(lastAvg);
            for (int i = n - finalCurveSize; i < n; i++) {
                currentCurve.add(points.get(i));
            }
            curves.add(new BezierCurve(colour, currentCurve));
        }
    }

    private void fillCurve() {

        curve = new ArrayList<Vector3>();
        for (BezierCurve cubic: curves) {
            curve.addAll(cubic.getCurve());
        }
    }
}
