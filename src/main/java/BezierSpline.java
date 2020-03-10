import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BezierSpline extends Curve {

    private List<BezierCurve> curves;

    public BezierSpline(/*PApplet canvas, */Color colour, List<Vector3> points) {
        super(/*canvas, */colour, points, true);

        curves = new ArrayList<BezierCurve>();
        int n = points.size();

        if (n < 5) {
            curves.add(new BezierCurve(colour, points));
        } else {
            List<Vector3> currentCurve = new ArrayList<>();
            currentCurve.add(points.get(0));
            currentCurve.add(points.get(1));
            currentCurve.add(points.get(2));
            Vector3 lastAvg = points.get(2).average(points.get(3));
            currentCurve.add(lastAvg);
            curves.add(new BezierCurve(colour, currentCurve));
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

            currentCurve = new ArrayList<>();
            currentCurve.add(lastAvg);
            for (int i = n - finalCurveSize; i < n; i++) {
                currentCurve.add(points.get(i));
            }
            curves.add(new BezierCurve(colour, currentCurve));
        }







//        int extraPoints = (n - 1) % 3;
//        switch (extraPoints) {
//            case 0:
//                if (n >= 4) {
//                    getCubicCurves(0, n);
//                }
//                break;
//            case 1:
//                if (n >= 8) {
//                    getCubicCurves(0, n - 4);
//                }
//                if (n >= 5) {
//                    curves.add(new BezierCurve(/*canvas, */colour, points.subList(n - 5, n - 2)));
//                }
//                if (n >= 3) {
//                    curves.add(new BezierCurve(/*canvas, */colour, points.subList(n - 3, n)));
//                }
//                break;
//            case 2:
//                if (n >= 6) {
//                    getCubicCurves(0, n - 2);
//                }
//                if (n >= 3) {
//                    curves.add(new BezierCurve(/*canvas, */colour, points.subList(n - 3, n)));
//                }
//                break;
//        }
        fillCurve();
    }

    private void getCubicCurves(int startIndex, int endIndex) {

        for (int i = startIndex; i < endIndex - 1; i += 3) {
            List<Vector3> curvePoints = points.subList(i, i + 3);
            curvePoints.add(points.get(i + 2).add(points.get(i + 3)).mult(1/2.0));
            curves.add(new BezierCurve(/*canvas, */colour, curvePoints));
        }
    }

    private void fillCurve() {

        curve = new ArrayList<Vector3>();

        for (BezierCurve cubic: curves) {
            curve.addAll(cubic.getCurve());
        }
    }
}
